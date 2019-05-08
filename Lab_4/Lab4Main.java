import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Lab4Main
{

   public static void main( String[] args ){
      File inputFile;
      Pattern stemPattern = Pattern.compile("(.*)[.].*");
      Matcher findStem;
      String inputFileStem;
      String outputPrefix;
      boolean verbose;
      int[] array;
      int[] arrayCopy;
      Timer timer = new Timer(1);

      // Check that minimum number of arguments were passed:
      if(args.length < 1){
         System.out.println("Not enough arguments were passed. Check usage and try again.");
         printUsageAndQuit();
      }

      // Set up configuration variables from argument list
      // Input file name and stem
      inputFile = getInputFile(args);
      findStem = stemPattern.matcher(args[0]);
      findStem.find();
      inputFileStem = findStem.group(1);
      // Verbose output selection
      verbose = verboseSelected(args);
      // Output prefix selection
      if(args.length == 3 || (args.length == 2 && !verbose)){
         outputPrefix = args[1];
      } else {
         outputPrefix = "output_";
      }

      // Get input array
      array = InputFileHandler.parseFile(inputFile);
      

      ////////////////////
      // Run sorts
      ////////////////////

      if(verbose){
         System.out.println("File: " + inputFile.getName());
      }
      // Vanilla QuickSort
      arrayCopy = copy(array);      
      timer.start();
      Sorter.iQSort(arrayCopy, 1, 0);
      timer.stop();
      if(verbose){
         System.out.println("Plain QuickSort: " + timer.getElapsed());
      }
      saveToFile(new File(outputPrefix + inputFileStem + "_QSort.txt"), arrayCopy);

      // QuickSort with Insertion Sort at k=50
      arrayCopy = copy(array);      
      timer.start();
      Sorter.iQSort(arrayCopy, 50, 0);
      timer.stop();
      if(verbose){
         System.out.println("QuickSort with InsertionSort at k=50: " + timer.getElapsed());
      }
      saveToFile(new File(outputPrefix + inputFileStem + "_QSortK50.txt"), arrayCopy);

      // QuickSort with Insertion Sort at k=100
      arrayCopy = copy(array);      
      timer.start();
      Sorter.iQSort(arrayCopy, 100, 0);
      timer.stop();
      if(verbose){
         System.out.println("QuickSort with InsertionSort at k=100: " + timer.getElapsed());
      }
      saveToFile(new File(outputPrefix + inputFileStem + "_QSortK100.txt"), arrayCopy);

      // QuickSort with median-of-three pivot
      arrayCopy = copy(array);      
      timer.start();
      Sorter.iQSort(arrayCopy, 1, 3);
      timer.stop();
      if(verbose){
         System.out.println("QuickSort with median-of-three pivot: " + timer.getElapsed());
      }
      saveToFile(new File(outputPrefix + inputFileStem + "_QSortMedianOf3.txt"), arrayCopy);

      // HeapSort
      arrayCopy = copy(array);
      HeapSorter heapSorter = new HeapSorter(timer);
      heapSorter.sort(arrayCopy);
      if(verbose){
         System.out.println("HeapSort: " + timer.getElapsed());
      }
      saveToFile(new File(outputPrefix + inputFileStem + "_HeapSort.txt"), arrayCopy);

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
      usageString += "Usage: Lab4Main input_file [output_prefix] [verbose]\n";
      usageString += "\tinput_file: The file containing integers to be sorted\n";
      usageString += "\toutput_prefix: Optional. Prefix to prepend to output files; defaults to \"output\"\n";
      usageString += "\tverbose: Optional. If \"verbose\" is passed, print timing stats to console\n";
   
      System.out.println(usageString);
      System.exit(0); 
  }

   private static File getInputFile(String[] args){
      /**
      ** getInputFile() parses the command line arguments for the input file containing
      ** the integers to be sorted. It checks to make sure the file exists; if it doesn't, 
      ** an error message is displayed, and the program exits.
      **
      ** @param args The command line arguments passed to the program
      ** @return File A File object representing the input file
      **/
      File inputFile = new File(args[0]);
      if(!inputFile.exists()){
         System.out.println("Sorry, the input file " + args[0] + " does not exist. Please check the name and try again.");
         printUsageAndQuit();
      }
      return inputFile;
   }

   private static boolean verboseSelected( String[] args){
      /**
      ** verboseSelected() parses the command line arguments to determine whether
      ** verbose output was selected.
      **
      ** @param args The command line arguments passed to the program
      ** @return boolean True if verbose output was selected; otherwise false
      **/

      return args[args.length == 2 ? 1 : 2].toLowerCase().equals("verbose");
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

   private static File getOutputFile(String outputPrefix, int stopSize, int mode){
      String filename = outputPrefix + "QuickSort_";

      if(stopSize > 1){
         filename += "k" + stopSize + "_";
      }

      switch(mode){
         case 0:
            filename += "pivot_first_item";
            break;
         case 1:
            filename += "pivot_last_item";
            break;
         case 2:
            filename += "pivot_middle_item";
            break;
         case 3:
            filename += "pivot_median_of_3";
            break;
         case 4:
            filename += "pivot_random";
            break;
         default:
            filename += "";
      }

      filename += ".txt";
      return new File(filename);
   }

   private static void saveToFile(File file, int[] array){
      try(FileWriter output = new FileWriter(file)){
         for(int i=0; i<array.length; i++){
            output.write(array[i] + (i < array.length-1 ? "\n" : ""));
         }
         // Below line causes bizzarre output--could be fun to look into
         // output.write(array[array.length - 1]);
      } catch (IOException except) {
         System.out.println("There was an error writing the output to file; please check your file name and try again");
         printUsageAndQuit();
      }

   }
}

