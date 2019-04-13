import java.io.*;

class FreqTableHandler
{
   /** 
   ** FreqTableHandler provides static methods to parse a file to obtain and process 
   ** a character-frequency table for use in a Huffman Encoding program.
   **
   ** @author Jon Powers
   ** @version 0.1
   **
   **/
   
   public static PriorityQueue getFrequencies(String fileName){
      /**
       ** getFrequencies() attempts to parse fileName to extract characters and their
       ** frequencies. Those values are then used to initialized HuffmanNodes, which
       ** are then placed into a PriorityQueue, which is returned.
       ** 
       ** The number of characters used in the encoding is determind by the
       ** number of lines in the frequency table (one per line).
       **
       ** Entries in the frequency table must be in the form [A-Za-Z]\D*[0-9]+ and
       ** failure to conform to this will generally cause an EncodingException to be
       ** thrown, which will result in the program exiting prematurely.
       ** Note that \D in this context does not include negated underscore.
       ** We chose to exit the program rather than simply ignore that line because
       ** it's unlikely that we'll be able to generate a correct code key if there
       ** was an error in the frequency table that prevented us from reading data
       ** on one of the keys. One way or another, this is almost certain to cause
       ** an issue later when trying to encode or decode a message. So we cut things off
       ** early rather than keep trudging down a futile path.
       **
       ** @param fileName The name of the file containing the frequencyTable.
       **
       **/
      int totalChars = getNumLines(fileName);

      HuffmanNode[] codes = parseFile(fileName);      

      return new PriorityQueue(10);
      
   }

   private static int getNumLines(String fileName){
      /**
      ** getNumLines() attempts to determine the number of lines in fileName.
      **
      ** @param fileName A String containing the name of the input file.
      ** @return int The number of lines in the file; 0 if there is an error reading the file or if the file is empty.
      **/
      
      int numLines = 0;

      try(BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))){
         String currentLineData;
         while (file.readLine() != null) {
            numLines += 1;
         }
         return numLines;
      
      //Error handling:
      } catch(FileNotFoundException except) {
         System.out.println("File (" + fileName + ") not found.");
         return 0;
      } catch(SecurityException except) {
         System.out.println("Error accessing " + fileName+ ": " + except + ". Do you have read access?");
         return 0;
      } catch(IOException except) {
         System.out.println("Error reading input file: " + except + "--no input data retrieved.");
         return 0;
      }
   }

   public static String charToString(int character){
      char[] tempChar = {(char) character};
      return new String(tempChar);
   }

   public static boolean ignoreChar(int character){
      // Whitespace is ignored
      if(character < 20 || character == 32){
         return true;
      // [A-Za-z0-9] are NOT ignored
      } else if (isLetter(character)) {
         return false;   
      } else if (isDigit(character)) {
         return false;
      } else {
         return true;
      }
   }

   public static boolean isDigit(int character){
      return character >= 48 && character <= 57;
   }
   
   public static boolean isLetter(int character){
      return (character >=65 && character <=90) || (character >= 97 && character <= 122);
   }

   public static HuffmanNode[] parseFile(String fileName) {
      /**
      **
      ** @param fileName The name of the input file.
      **
      ** @return HuffmanNode[] The array of HuffmanNodes, one for each parseable character in the frequency table.
      **/
      HuffmanNode[] codes = new HuffmanNode[getNumLines(fileName)];
      int nodeCounter = 0;

      
      try(FileReader file = new FileReader(fileName)){
         int character;
         String stringCharacter = "";
         String stringFrequency = "";
         int frequency = 0;
         
         while((character = file.read()) != -1){
            // Lines beginning with # are comments. Skip those lines
            if(character == '#'){
               while(file.read() != '\n'){/*do nothing*/}
               
            // Lines beginning with > are configuration information--for future
            // implementation
            } else if(character == '>'){
               while(file.read() != '\n'){/*do nothing*/}
            
            // If we hit a newline character, process the information
            // parsed from that line, use it to create a HuffmanNode,
            // and add the node to codes[].
            } else if(character == '\n') {
               
               // If we've parsed a line without getting data, just move on
               if(stringCharacter.equals("") && stringFrequency.equals("")){
                  continue;
               }
               
               // Error checking--Errors will throw an EncodingException. It's unlikely
               // that encoding/decoding will be successful if there's an error in the
               // file containing the information we're building the code from.

               // Frequency integer must parse successfully.
               try {
                  frequency = Integer.parseInt(stringFrequency);
               } catch (NumberFormatException except) {
                  String errorString = "Could not parse frequency integer associated with ";
                  errorString += stringCharacter + ":\n\t" + except.getMessage();
                  throw new EncodingException(errorString);
               }
               // Character identifier cannot be more than one character
               if(stringCharacter.length() != 1) {
                  String errorString = "Error parsing frequency table: Entry for \"";
                  errorString += stringCharacter + "\" contains more than one character.";
                  throw new EncodingException(errorString); 
               }
               
               // 

               codes[nodeCounter] = new HuffmanNode(stringCharacter, frequency);

               // Increment our line counter and reset the temp variables
               nodeCounter++;
               stringCharacter = "";
               stringFrequency = "";
               frequency = 0;

            // Any letters get added to the stringCharacter
            } else if(isLetter(character)) {
               stringCharacter += charToString(character);
            
            // Any digits get added to the stringFrequency
            } else if(isDigit(character)) {
               stringFrequency += charToString(character);
            
            // Anything else, skip. Doesn't technically need an else, but that's okay.
            } else {   
               continue;
            }
         }
      } catch(IOException except){
         System.out.println("There was an error reading " + fileName + ": " + except.getMessage());
         System.out.println("Please check that the file exists, that it contains data, and that you have access to it. Try again.");
      }
      
      // A file consisting only of comments would result in an array of null HuffmanNodes. 
      // If all are null, return an empty array.
      for(HuffmanNode node : codes){
         if(node != null){
            return codes;
         }
      }
      return new HuffmanNode[0];
   }

}
