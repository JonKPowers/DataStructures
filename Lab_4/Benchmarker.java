import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

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
      // Input variables
      File inputDir;
      File[] inputFiles;
      // Output file names, one each for sorted, reversed, random and duplicates files
      File outputFileAsc = new File("Dummy");
      File outputFileRev = new File("Dummy");
      File outputFileRan = new File("Dummy");
      File outputFileDup = new File("Dummy");

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
      if(!testType.equals("qtest") && !testType.equals("htest")){
         printUsageAndQuit("Invalid test selection. Check usage and try again");
      }
      

      // Parse and validate qTest arguments: qTest num_runs max_k mode recursive
      if(testType.equals("qtest")){
         if(args.length < 4) {
            printUsageAndQuit("Incorrect number of arguments provided: " + args.length + ". Check usage and try again");
         }
         
         try{
            numRuns = Integer.parseInt(args[1]);
         } catch (NumberFormatException except) {
            printUsageAndQuit("Invalid number of runs: " + args[1] + ". Please check usage and try again.");
         }
         try{
            maxK = Integer.parseInt(args[2]);
         } catch (NumberFormatException except) {
            printUsageAndQuit("Invalid max_k value: " + maxK + ". Please check usage and try again.");
         }

         mode = getMode(args[3]);
         useRecursive = args.length == 5 && args[4].toLowerCase().equals("recursive");

         outputFileAsc = getOutputFile("sorted", args[3],  numRuns, maxK);
         outputFileRev = getOutputFile("reversed", args[3],  numRuns, maxK);
         outputFileRan = getOutputFile("random", args[3],  numRuns, maxK);
         outputFileDup = getOutputFile("duplicates", args[3],  numRuns, maxK);

      }


      if(testType.equals("htest")){
         if(args.length != 2){
            printUsageAndQuit("Incorrect number of arguments provided: " + args.length + ". Please check usage and try again.");
         }

         try{
            numRuns = Integer.parseInt(args[1]);
         } catch (NumberFormatException except) {
            printUsageAndQuit("Invalid number of runs: " + args[1] + ". Please check usage and try again.");
         }

         outputFileAsc = getOutputFile("sorted",  numRuns);
         outputFileRev = getOutputFile("reversed",  numRuns);
         outputFileRan = getOutputFile("random",  numRuns);
         outputFileDup = getOutputFile("duplicates",  numRuns);
      }


      /////////////////////////
      // Input File Handling
      /////////////////////////

      // Check for input file directory and contents; generate list of input files
      inputDir = new File("inputFiles/newer");
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

      if(testType.equals("htest")){
         try(FileWriter outputAsc = new FileWriter(outputFileAsc);
             FileWriter outputRev = new FileWriter(outputFileRev);
             FileWriter outputRan = new FileWriter(outputFileRan);
             FileWriter outputDup = new FileWriter(outputFileDup)){

            for(File file : inputFiles){
               // Get type of file
               String fileType = file.getName().toLowerCase().substring(0,3);
               // Run sort--Get timing data
               timingData = hTest(file, numRuns);
               
               // Write timing data to appropriate file
               switch(fileType){
                  case "asc":
                     outputAsc.write(timingData);
                     break;
                  case "rev":
                     outputRev.write(timingData);
                     break;
                  case "ran":
                     outputRan.write(timingData);
                     break;
                  case "dup":
                     outputDup.write(timingData);
                     break;
                  default:
                     // Unsupported filename format--do nothing
                     System.out.println("Warning: unsupported filename format " + file.getName());
               }

            } 
         } catch (IOException except){
            printUsageAndQuit("Error writing output file(s). Please check settings and try again.");
         }
      }

      if(testType.equals("qtest")){
         // Set up context manager for output files
         try(FileWriter outputAsc = new FileWriter(outputFileAsc);
             FileWriter outputRev = new FileWriter(outputFileRev);
             FileWriter outputRan = new FileWriter(outputFileRan);
             FileWriter outputDup = new FileWriter(outputFileDup)){
         
            // Add header information for CSV files
            addCsvHeaders(outputAsc, maxK);
            addCsvHeaders(outputRev, maxK);
            addCsvHeaders(outputRan, maxK);
            addCsvHeaders(outputDup, maxK);

            // Run timing benchmarks
            for(File file : inputFiles){
               // Get type of file
               String fileType = file.getName().toLowerCase().substring(0,3);

               // Get timing data
               timingData = "";
               timingData += qTest(file, numRuns, maxK, mode, useRecursive);
               timingData += "\n";

               // Write timing data to appropriate file
               switch(fileType){
                  case "asc":
                     outputAsc.write(timingData);
                     break;
                  case "rev":
                     outputRev.write(timingData);
                     break;
                  case "ran":
                     outputRan.write(timingData);
                     break;
                  case "dup":
                     outputDup.write(timingData);
                     break;
                  default:
                     // Unsupported filename format--do nothing
                     System.out.println("Warning: unsupported filename format " + file.getName());
               }
            }
         } catch (IOException except) {
            printUsageAndQuit("Error writing output file(s). Please check settings and try again.");
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

      try {
         for(int k=0; k<=maxK; k += 50){
            for(int i=0; i<numRuns; i++){
               arrayCopy = copy(array);
               if(useRecursive){
                  timer.start();
                  Sorter.qSort(arrayCopy, k, mode);
                  timer.stop();
               } else {
                  timer.start();
                  Sorter.iQSort(arrayCopy, k, mode);
                  timer.stop();
               }
            }
            outputData += timer.getAverageTime() + ",";
            timer.reset();
         }
      } catch (StackOverflowError except) {
         printUsageAndQuit("Sorry, recursive depth too high for stack; can't complete requested operation");
      }
      return outputData.replaceAll(",$", "");
   }

   // hTest
   private static String hTest(File file, int numRuns){
      String output = "";
      Timer timer = new Timer(numRuns);
      HeapSorter sorter = new HeapSorter(timer);
      int[] array = InputFileHandler.parseFile(file);
      int[] arrayCopy;

      for(int i=0; i<numRuns; i++){
         arrayCopy = copy(array);
         sorter.sort(arrayCopy);
      }
      
      output += array.length + ",";
      output += timer.getAverageTime() + "\n";
      return output;
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

   private static File getOutputFile(String fileType, int numRuns){
      String fileName = "output/";
      fileName += "HSort_";
      fileName += "n" + numRuns + "_";
      fileName += fileType;
      fileName += ".csv";
      return new File(fileName);
   }

   private static File getOutputFile(String fileType, String pivot, int numRuns, int maxK){
      String fileName= "output/50to200000/";
      fileName+= "QSort_";
      fileName+= "n" + numRuns + "_";
      fileName+= "k" + maxK + "_";
      fileName+= pivot + "_";
      fileName+= fileType;
      fileName+= ".csv";
      return new File(fileName);
   }

   private static void addCsvHeaders(FileWriter file, int maxK){
      // Generate CSV headers
      String timingData = "n,";
      for(int i=0; i<=maxK; i += 50){
         timingData += "k=" + i + ",";
      }
      timingData = timingData.replaceAll(",$", "");
      timingData += "\n";
      
      // Write out to file
      try{
         file.write(timingData);
      } catch (IOException except) {
         printUsageAndQuit("There was a proble writing to output file(s). Please check and try again.");
      }
   }
}

