import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Lab4Main
{

   public static void main( String[] args ){
      File inputFile;
      String outputPrefix;
      boolean verbose;

      // Set up configuration variables from argument list
      // Input file name
      inputFile = getInputFile(args);
      // Verbose output selection
      verbose = verboseSelected(args);
      // Output prefix selection
      if(args.length == 3 || (args.length == 2 && !verbose)){
         outputPrefix = args[1];
      } else {
         outputPrefix = "output_";
      }
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
      usageString += "Usage: Lab4Main input_file [output_prefix]\n";
      usageString += "\tinput_file: The file containing integers to be sorted\n";
      usageString += "\toutput_prefix: Optional. Prefix to prepend to output files; defaults to \"output\"\n";
   
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
            filename += "private_last_item";
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
         for(int i=0; i<array.length - 1; i++){
            output.write(array[i] + "\n");
         }
         output.write(array[array.length - 1]);
      } catch (IOException except) {
         System.out.println("There was an error writing the output to file; please check your file name and try again");
         printUsageAndQuit();
      }

   }

   private static Timer runQSort(int numRuns, int[] array, int stopSize, int mode, String outputPrefix){
      Timer timer = new Timer(numRuns);
      int[] arrayCopy = copy(array);

      for(int i=0; i<numRuns; i++){
         arrayCopy = copy(array);
         timer.start();
         Sorter.qSort(arrayCopy, stopSize, mode);
         timer.stop();
      }

      saveToFile(getOutputFile(outputPrefix, stopSize, mode), arrayCopy);
      return timer;
   } 


}

