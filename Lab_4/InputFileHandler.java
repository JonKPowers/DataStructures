import java.io.*;

class InputFileHandler
{
   /** 
   ** InputFileHandler provides static methods to parse a file to obtain and process 
   ** integer array data and place it into memory.
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
         System.out.println("Error accessing " + fileName + ": " + except + ". Do you have read access?");
         return 0;
      } catch(IOException except) {
         System.out.println("Error reading input file: " + except + "--no input data retrieved.");
         return 0;
      }
   }

   public static boolean isDigit(int character){
      /**
      ** isDigit() attempts to determine whether a particular character
      ** is a numerical digit.
      **
      ** @param character Integer value of a character
      ** @return boolean True if character is a digit; otherwise, false
      **/
      return character >= 48 && character <= 57;
   }
   
   public static String charToString(int character){
      /**
      ** charToString() converts a Integer character value to a String. It's
      ** a goofy workaround to avoid using String methods.
      **
      ** @param character Integer value of the character to be converted to a String.
      ** @return String The String representation of character
      **/
      char[] tempChar = {(char) character};
      return new String(tempChar);
   }

   public static int[] parseFile(File fileName) {
      /**
      **
      ** @param fileName The name of the input file.
      **
      ** @return int[] Integer array containing the integer entries in the input file
      **/

      HuffmanNode[] codes = new HuffmanNode[getNumLines(fileName)];

      
      try(FileReader file = new FileReader(fileName)){
         int character;
         String stringNum = "";
         int num = 0;
         IntStack stack = new IntStack();
         
         while((character = file.read()) != -1){
            // Ignore anything that's not a number

            if(isDigit(character)){
               stringNum += charToString(character);
               while(isDigit(character = file.read())){
                  stringNum += charToString(character);   
               }

               try{
                  num = Integer.parseInt(stringNum);
                  stack.push(num);
               } catch (NumberFormatException except){
                // TO DO--ERROR HANDLING  
               }

               // Reset stringNum for next number
               stringNum = "";
            }         
         }
      } catch(IOException except){
         System.out.println("There was an error reading " + fileName + ": " + except.getMessage());
         System.out.println("Please check that the file exists, that it contains data, and that you have access to it. Try again.");
      }
      
      // Dump the stack to an array and return it
      return stack.dumpArray();

}
