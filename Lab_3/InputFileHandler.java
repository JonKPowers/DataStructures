import java.io.*;


class InputFileHandler
{
   /** 
   ** InputFileHandler provides static methods to parse a file to get input data for further processing.
   **
   ** The primary method is getLinesFromFileAsStacls(), which goes through a file and returns an 
   ** array of StringStackDataPacks with the contents of each line contained in separate array elements.
   **
   ** @author Jon Powers
   ** @version 0.1
   **
   **/
   
   private static int getNumLines(File fileName){
      /**
      ** getNumLines() attempts to determine the number of lines in fileName.
      **
      ** @param fileName A String containing the name of the input file.
      ** @return int The number of lines in the file; -1 if there is an error reading the file.
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
   
   public static boolean ignoreCharacter(int character){
      /**
      ** ignoreCharacter() provides a filter for characters that we want to ignore.
      ** It generally ignores all nonprinting characters and characters outside
      ** the Latin Basic portion of the UTF code set (<20 or >126).
      **
      ** @param character Integer value of the character being evaluated.
      ** @return boolean True if the character should be skipped; otherwise false
      **/
      
      //Ignore the control characters
      if(character < 20 || character >126) return true;
      
      // Everything else is processed
      return false;
   }

   public static StringStackDataPack[] getLinesFromFileAsStacks(File fileName){
      /**
      ** getLinesFromFileAsStacks() generates an array of StringStackDataPacks, 
      ** each of which holds the contents of a line from the input file along
      ** with any provided metadata associated with that line. The line contents
      ** are contained in StringStacks that will pop off the expression the characters
      ** from left to right. 
      **
      ** @param fileName The name of the input file.
      **
      ** @return StringStackDataPack[] The array of StringStackDataPacks, one for each expression in the input file.
      **/
      StringStackDataPack[] fileData = new StringStackDataPack[getNumLines(fileName)];
      
      try(FileReader file = new FileReader(fileName)){
         int character;
         int line = 0;
         CharStack tempStack = new CharStack();
         String comments = "";
         boolean optimizeOn = false;
         boolean optimizeOff = false;
         
         
         while((character = file.read()) != -1){
            // Lines beginning with # are comments. Skip those lines
            if(character == '#'){
               while(file.read() != '\n'){}
               
            // Lines beginning with > are configuration information to be included in
            // the StringStackDataPack.   
            } else if(character == '>'){
               String configString = "";
               while((character = file.read()) != '\n'){
                  // Goofy workaround to avoid using library functions to convert char to string
                  char[] tempChar = {(char) character};
                  configString += new String(tempChar);
               }
               switch(configString.toLowerCase()){
                  case("optimizeon"): optimizeOn = true; break;
                  case("optimizeoff"): optimizeOff = true; break;
                  default: comments += configString;
               }
            
            // Ignore whitespace characters (Any UTF-8 character with unicode 
            // value under 20 as well as unicode 32, which is a space).               
            } else if(character == '\n' && !tempStack.isEmpty()) {
               fileData[line] = new StringStackDataPack();
               fileData[line].comments = comments;
               fileData[line].optimizeOn = optimizeOn;
               fileData[line].optimizeOff = optimizeOff;
               
               // Once we hit the end of a line, flush out the contents of 
               // tempStack into the stack of a StringStackDataPack
               while(!tempStack.isEmpty()){
                  // Goofy workaround to avoid using library functions to convert char to string
                  char[] tempchar = {tempStack.pop()};
                  fileData[line].stack.push(new String(tempchar));
               }
               
               // Increment our line counter and reset the temp variables
               line++;
               tempStack = new CharStack();
               comments = "";
               optimizeOn = false;
               optimizeOff = false;
            } else if(ignoreCharacter(character)){   
               continue;
               
            // Normal characters go onto tempStack until we hit a newline   
            } else if(character != '\n'){
               tempStack.push((char) character);
            }


            
         }
      } catch(IOException except){
         System.out.println("There was an error reading " + fileName + ": " + except.getMessage());
         System.out.println("Please check that the file exists, that it contains data, and that you have access to it. Try again.");
      }
      
      // A file consisting only of comments would result in an array of null StringStacks. 
      // If all are null, return an empty array.
      for(StringStackDataPack dataPack : fileData){
         if(dataPack != null){
            return fileData;
         }
      }
      return new StringStackDataPack[0];
   }

}