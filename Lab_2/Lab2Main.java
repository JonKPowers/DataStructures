class Lab2Main
{

   public static void main( String[] args ){
      String method, outputFile;
      String targetTower;
      String moveList;

      int numDiscs;
      boolean optimize;     
      
      // Try parsing arguments
      if(args.length < 3){
         badArguments("Wrong number of arguments");
      }
      
      method = getSolveMethod(args);
      numDiscs = getNumDiscs(args);
      outputFile = getOutputFile(args);
      
      targetTower = "C";
      
      if(numDiscs == 1) {
         // DO SOMETHING HERE FOR EDGE CASE.
      }
      
      // Solve the Towers of Hanoi problem
      if(method.equals("recursive")){
         RecursiveTowers tower = new RecursiveTowers(numDiscs, targetTower);
         tower.solve();
         moveList = tower.getMoveList();
      } else { // method.equals("iterative");
         IterativeTowers tower = new IterativeTowers(numDiscs, targetTower);
         tower.solve();
         moveList = tower.getMoveList();
      }

      System.out.println(moveList);
   }
   
   private static void badArguments(String message){
      System.out.println("Incorrect arguments provided: " + message + ". Please check your usage and try again.");
      printUsageAndQuit();
   }
   
   private static void printUsageAndQuit(){
      System.out.println("Usage: java Lab2Main method num_discs output_file [optimize]\n");
      System.out.println("\tmethod: \"recursive\" or \"iterative\""); 
      System.out.println("\tnum_discs: Number of discs to solve for");
      System.out.println("\toutput_file: The file to write the solution moves to");
      System.out.println("\toptimize: \"optimize\" to enable recursive optimizations");
      
      System.exit(0);
   }
   
   private static String getSolveMethod(String[] args){
      String method = args[0].toLowerCase();
      if( !method.equals("recursive") && !method.equals("iterative") ){
         System.out.println("Please provide a valid method (\"recursive\" or \"iterative\" of solving TOH problem");
         printUsageAndQuit();
      }
      return method;
   }
   
   private static int getNumDiscs(String[] args){
      int numDiscs = 0;
      try {
         numDiscs = Integer.parseInt(args[1]);
         if(numDiscs < 1){
            throw new IllegalArgumentException("You must specify 1 or more discs to be solved. You specified " + numDiscs + " discs.");
         }
      } catch(NumberFormatException except) {
         System.out.println("Please provide a valid number of discs");
         printUsageAndQuit();
      } catch(IllegalArgumentException except) {
         System.out.println(except.getMessage());
         System.exit(0);
      }
      
      return numDiscs;
   }
   
   private static String getOutputFile(String[] args){
      String outputFile = args[2];
      // If "optimize" is the third argument, then they probably didn't intend that as 
      // the output file, so throw them an error and ask them to try again.
      if(outputFile.toLowerCase().equals("optimize")){
         badArguments("\"optimize\" is not a permitted output file name.");
      }
      
      return outputFile;
   }
   
   private static boolean getOptimizeSetting(String[] args){
      try{
         return args[3].toLowerCase().equals("optimize") ? true : false;
      } catch (ArrayIndexOutOfBoundsException except){
         return false;
      }
   }

}