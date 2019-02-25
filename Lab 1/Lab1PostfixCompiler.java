class Lab1PostfixCompiler 
{

   public static void main( String[] args )
   {
      CharStack stack = new CharStack();
      
      char result = stack.pop();
      System.out.println(result);
      
      
      
      
      
      String inputFile;
      String[] fileLines;
      
      // One input file must be provided as the program's single argument.
      if (args.length != 1){
         System.out.println("Incorrect number of arguments (" + args.length + 
                            ") provided. Usage: Lab1PostfixCompiler <input_file.txt>.");
      }
      inputFile = args[0];
      
      // Get an array containing each line of the inputFile. If getLinesFromFile() returns a
      // zero-length array, either the file was empty or there was some sort of issue opening
      // or reading the file. In either case, we don't have any usable data from the inputFile,
      // so print a message and end the program.
      fileLines = InputFileHandler.getLinesFromFile(inputFile);
      if(fileLines.length == 0){
         System.out.println("Unable to get data from " + inputFile + ". Please check input file and try again.");
         System.exit(0);
      }
      
      
      
      
      System.out.println(fileLines[1]);
      
   
   }


}