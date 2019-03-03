import java.io.*;


class InputFileHandler
{
   /** 
   ** InputFileHandler provides static methods to parse a file to get input data for further processing.
   **
   ** The primary method is getLinesFromFile(), which goes through a file and returns a 
   ** String[] array with the contents of each line contained in separate array elements.
   **
   ** @author Jon Powers
   ** @version 0.1
   **
   **/
   
   private static int getNumLines(String fileName){
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
         return -1;
      } catch(SecurityException except) {
         System.out.println("Error accessing " + fileName+ ": " + except + ". Do you have read access?");
         return -1;
      } catch(IOException except) {
         System.out.println("Error reading input file: " + except + "--no input data retrieved.");
         return -1;
      }
      
   }

   public static String[] getLinesFromFile(String fileName){
      /**
      ** getLinesFromFile generates an array with each element corresponding to a line
      ** in fileName. Returns a zero-length array if the file is empty or an error is encountered 
      ** while attempting to open or read the file.
      **
      ** @param fileName A String containing the name of the input file
      ** @return String[] An array with each element containing the content of one line of fileName
      **/
      String[] fileLines;
      int numLines = getNumLines(fileName);
      // System.out.println(numLines + " lines in file " + fileName +"."); 
      
      // Return an empty array if the source file is empty.
      if(numLines < 1){
         return new String[0];
      }
      else{
         fileLines = new String[numLines];
      }
      
      // Otherwise, return an array with the contents of each line.
      // If an error occurs, an empty array is returned.
      try(BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))){
         for(int i=0; i<numLines; i++){
            fileLines[i] = file.readLine().trim();
         }
      } catch(IOException except){
         System.out.println("Error reading input file " + fileName + ": " + except.getMessage());
         return new String[0];
      }
      
      return fileLines;
   }

   public static StringStack[] getLinesFromFileAsStacks(String fileName){
   /**
   ** getLinesFromFileAsStacks() generates an array of StringStacks, with each
   ** StringStack containing one line of the inputfile, which will pop off from
   ** left to right. 
   **
   **/
      StringStack[] fileLineStacks = new StringStack[getNumLines(fileName)];
      
      try(FileReader file = new FileReader(fileName)){
         int character;
         int line = 0;
         CharStack tempStack = new CharStack();
         while((character = file.read()) != -1){
            if(character == '#'){
               // Lines beginning with # are comments. Skip those lines
               while(file.read() != '\n'){}
            } else if(character == '\n' && !tempStack.isEmpty()) {
               fileLineStacks[line] = new StringStack();
               while(!tempStack.isEmpty()){
                  char[] tempchar = {tempStack.pop()};
                  fileLineStacks[line].push(new String(tempchar));
               }
               line++;
               tempStack = new CharStack();
            } else if(character < 20 || character == 32) {   
               // Ignore whitespace characters (Any UTF-8 character with unicode 
               // value under 20 as well as unicode 32, which is a space).
               continue;
            } else {
               tempStack.push((char) character);
            }
         }
      } catch(IOException except){
         System.out.println("There was an error reading " + fileName + ": " + except.getMessage());
         System.out.println("Please check that the file exists, that it contains data, and that you have access to it. Try again.");
      }
      
      // A file consisting only of comments would result in an array of null StringStacks. 
      // If all are null, return an empty array.
      for(StringStack stack : fileLineStacks){
         if(stack != null){
            return fileLineStacks;
         }
      }
      return new StringStack[0];
   }

}