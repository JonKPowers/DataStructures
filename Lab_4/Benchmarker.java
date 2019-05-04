import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Benchmarker
{

   public static void main( String[] args ){
      //////////////////////////////
      // Variables
      //////////////////////////////
      
      String testType;
      String timingData = "";
      int[] array;
      int[] arrayCopy;
      File inputDir;
      File[] inputFiles;
      String outputFileName = "";

      // qTest Variables 
      // Defaults are a single pure QuickSort run with first-item pivot
      int numRuns = 1;
      int maxK = 1;
      int mode = 0;
      boolean useRecursive = false;

      ////////////////////////////////
      // Agument Validation/Processing
      ////////////////////////////////

      // Validate argument list length
      if(args.length == 0) {
         printUsageAndQuit("Incorrect number of arguments provided: 0. Check usage and try again");
      }
      
      // Parse test type
      testType = args[0].toLowerCase();

      // Validate selected valid test
      if(!testType.equals("qtest")){
         printUsageAndQuit("Invalid test selection. Check usage and try again");
      }
      

      // Parse and validate qTest arguments: qTest num_runs max_k mode recursive
      if(testType.equals("qtest")){
         if(args.length < 4) {
            printUsageAndQuit("Incorrect number of arguments provided: " + args.length + ". Check usage and try again");
         }
         
         numRuns = Integer.parseInt(args[1]);
         maxK = Integer.parseInt(args[2]);
         mode = getMode(args[3]);
         useRecursive = args.length == 5 && args[4].toLowerCase().equals("recursive");

         // Generate output file name
         outputFileName += "QSort_";
         outputFileName += args[3] + "_";
         outputFileName += "n" + numRuns + "_";
         outputFileName += "k" + maxK;
         outputFileName += ".csv";
      }


      /////////////////////////
      // Input File Handling
      /////////////////////////

      // Check for input file directory and contents; generate list of input files
      inputDir = new File("inputFiles");
      if(!inputDir.exists()){
         printUsageAndQuit("Error: Could not find directory ./inputFiles containing input files");
      }
      // Only files with a .dat extension will be processed
      inputFiles = inputDir.listFiles(new FilenameFilter(){
         @Override
         public boolean accept(File file, String name){
            return name.endsWith(".dat");
         }
      });
      if(inputFiles.length == 0){
         printUsageAndQuit("Error: No input files found in directory ./inputFiles");
      }

      /////////////////////////
      // Fire up the test runner
      /////////////////////////

      if(testType.equals("qtest")){
         // Generate CSV headers
         timingData += "n,";
         for(int i=1; i<=maxK; i++){
            timingData += "k=" + i + ",";
         }
         timingData = timingData.replaceAll(",$", "");
         timingData += "\n";

         // Run timing benchmarks
         for(File file : inputFiles){
            timingData += qTest(file, numRuns, maxK, mode, useRecursive);
            timingData += "\n";
         }

         // Write out CSV data to file
         try(FileWriter outputFile = new FileWriter(outputFileName)){
            outputFile.write(timingData);
         } catch (IOException except) {
            printUsageAndQuit("Error writing output file. Please check settings and try again.");
         }

      }
   }


   /////////////////////////
   // Argument parsers
   /////////////////////////

   private static int getMode(String mode){
   /**   "first"  mode == 0: Choose the first item in the subarray; i.e., array[startIndex]
   *     "last"   mode == 1: Choose the last item in the subarray; i.e., array[endIndex]
   *     "mid"    mode == 2: Choose the middle item in the subarray; i.e., array[(startIndex + endIndex)/2]
   *     "median" mode == 3: Choose median-of-three as pivot
   *     "random" mode == 4: Choose random pivot
   **/
      switch(mode){
         case "first":
            return 0;
         case "last":
            return 1;
         case "mid":
            return 2;
         case "median":
            return 3;
         case "random":
            return 4;
         default:
            printUsageAndQuit("Invalid mode selected: " + mode + ". Check usage and try again");
      return 0;
      }
   } 

   /////////////////////////
   // Test Runners
   /////////////////////////

   // qTest
   private static String qTest(File file, int numRuns, int maxK, int mode, boolean useRecursive){
      Timer timer = new Timer(numRuns);
      int[] array = InputFileHandler.parseFile(file);
      int[] arrayCopy;
      String outputData = "";
      outputData += array.length + ",";

      if(useRecursive){
         try {
            for(int k=1; k<=maxK; k++){
               for(int i=0; i<numRuns; i++){
                  arrayCopy = copy(array);
                  timer.start();
                  Sorter.qSort(arrayCopy, k, mode);
                  timer.stop();
               }
            }
            outputData += timer.getAverageTime() + ",";
            timer.reset();
         } catch (StackOverflowError except) {
            printUsageAndQuit("Sorry, recursive depth too high for stack; can't complete requested operation");
         }
      } else {
         for(int k=1; k<=maxK; k++){
            for(int i=0; i<numRuns; i++){
               arrayCopy = copy(array);
               timer.start();
               Sorter.iQSort(arrayCopy, k, mode);
               timer.stop();
            }
            outputData += timer.getAverageTime() + ",";
            timer.reset();
         }
      }
      
      return outputData.replaceAll(",$", "");
   }

   /////////////////////////
   // Utilities
   /////////////////////////


   private static void printUsageAndQuit(String message){
      System.out.println("\n" + message);
      System.out.println("---------------------------------------------");
      printUsageAndQuit();
   }

   private static void printUsageAndQuit(){
      /**
      ** printUsageAndQuit() is intended to be called when the arguments
      ** passed by the user cannot be parsed. It prints out a usages guide
      ** to help the user, and then causes the program to terminate.
      **
      ** @return None Nothing is returned.
      **/
      String usageString = "";
      usageString += "Input Folder: Input files must be contained in ./inputFiles/\n\n";
      usageString += "Run QuickSort tests:\n";
      usageString += "Usage: Lab4Main qTest num_runs max_k mode [recursion]\n";
      usageString += "\tnum_runs: Number of runs to use; average time is reported\n";
      usageString += "\tmax_k: k value to run tests through; k=0 to k=n\n";
      usageString += "\tmode: Pivot selection mode.\n";
      usageString += "\t      \"first\": Use first subarray item as pivot\n";
      usageString += "\t      \"last\": Use last subarray item as pivot\n";
      usageString += "\t      \"mid\": Use middle subarray item as pivot\n";
      usageString += "\t      \"median\": Use median-of-3 pivot selection method\n";
      usageString += "\t      \"random\": Randomly select pivot\n";
      usageString += "\trecursion: Optional. If \"recursive\" is passed, a recursive QuickSort is attempted.\n";

      System.out.println(usageString);
      System.exit(0); 
   }

   private static int[] copy(int[] array){
      int[] output = new int[array.length];
      for(int i=0; i<array.length; i++){
         output[i] = array[i];
      }
      return output;
   }

   private static File getOutputFile(String outputPrefix){
      return new File(outputPrefix + "HeapSort.txt");
   }
}

