import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;


class Lab2Main
{
   /**
   ** Lab2Main is the driver class for the JHU Data Structures Lab 2 assignment.
   ** It computes a solution to the Towers of Hanoi problem, using either an
   ** iterative or recursive approach, depending on the command-line arguments
   ** supplied by the user. In addition, it provides a memoization mechanism that
   ** can be enabled for the recursive solution.
   **
   ** @author Jon Powers
   ** @version 0.1
   **
   **/

   public static void main( String[] args ){
      /**
      ** main() contains the primary logic of the Lab 2 driver. It's main
      ** responsibilty is to parse the command-line arguments, set up the
      ** environment, and call the appropriate methods to generate a solution
      ** to the Towers of Hanoi problem.
      **
      ** Usage: java Lab2Main solve_method output_file ["optimize"]
      **        solve_method: either "iterative" or "recursive"        
      **        output_file: the name of the file to write the solution to
      **        "optimize": if specified (without quotes), memoization will be enabled for recursion
      **/

      // Variables for command-line argument info      
      String method, outputFile;
      String targetTower;
      boolean optimize;      

      // Variables for information about the TOH problem
      int numDiscs;
      long numMoves;
      
      // Helpers used while solving the TOH problem
      Timer timer = new Timer("Time it");
      File tempSolutionFile;
      BetterMoveEncoder encoder = new BetterMoveEncoder(1);     
      
      // Try parsing arguments
      if(args.length < 3){
         badArguments("Wrong number of arguments");
      }      
      method = getSolveMethod(args);
      numDiscs = getNumDiscs(args);
      outputFile = getOutputFile(args);
      optimize = getOptimizeSetting(args);
     
      // Some misc variables with information about the problem
      targetTower = "C";      // Can be set up to use "B" as the targetTower as well
      numMoves = MathHelpers.exponentiate(2, numDiscs) - 1;      
      
      ////////////////////////////////////
      // Solve the Towers of Hanoi problem
      ////////////////////////////////////
      try {
         tempSolutionFile = File.createTempFile("TOHSolution", ".tmp");
         tempSolutionFile.deleteOnExit();
         
         try(FileWriter tempFileWriter = new FileWriter(tempSolutionFile)){
         
            // Start the timer
            timer.start();
            
            // Solve the problem
            if(method.equals("recursive")){
               RecursiveTowers tower = new RecursiveTowers(numDiscs, targetTower, tempFileWriter, optimize);
               if(optimize){
                  encoder = tower.solve(optimize);               
               } else {
                  tower.solve();              
               }
            } else { // method.equals("iterative");
               ImprovedIterativeTowers tower = new ImprovedIterativeTowers(numDiscs, targetTower, tempFileWriter);
               tower.solve();
            }
            
            // Stop the timer
            timer.stop();
            
         } catch (IOException except) {   // Writing to solution file
            System.out.println("Error writing to solution tempFile: " + except.getMessage());
         }
            
            // Send the program output to the provided outputFile
            try(FileWriter outputFileWriter = new FileWriter(outputFile)){
               outputFileWriter.write("***********************************\n***********************************\n");
               outputFileWriter.write("Towers of Hanoi Results\n");
               outputFileWriter.write("***********************************\n***********************************\n");
               outputFileWriter.write("Number of Discs: " + numDiscs + "\n");
               outputFileWriter.write("Method of Solving: " + method + "\n");
               if(method.equals("recursion")){
                  outputFileWriter.write("Optimization: " + (optimize ? "enabled" : "disabled") + "\n");
               }
               outputFileWriter.write("Total moves: " + numMoves +"\n");
               outputFileWriter.write("Time to solve: " + timer.getElapsedSeconds() + " seconds\n");
               outputFileWriter.write("\nSolution:\n");
               
               if(method.equals("recursive") && optimize) {
                  encoder.saveMovesToFile(outputFileWriter);
               } else {               
                  try(FileReader solutionFile = new FileReader(tempSolutionFile)){
                     int input;
                     while ((input = solutionFile.read()) != -1){
                        outputFileWriter.write(input);
                     }
                  } catch (IOException except) {   // Reading solution file
                     System.out.println("Error: There was a problem reading the solution temp file:\n\t" + except.getMessage());
                  }
               }
               
            } catch (IOException except){ //Writing output to file
               System.out.println("There was an error writing the output to " + outputFile + ":");
               System.out.println(except.getMessage());
            }

      } catch (IOException except) {   // Creating temporary file
         System.out.println("Error creating temporary file: " + except.getMessage());
      }
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
         badArguments("\"optimize\" is not a permitted output file name. Check usage.");
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