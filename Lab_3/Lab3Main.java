import java.io.File;


class Lab3Main
{
   
  public static void main( String[] args ){
      
      String mode;
      File encodingFile;
      File inputFile;
      String outputFile;
      
      // Check that we have the right number of arguments
      if(args.length != 4){
         System.out.println("Incorrect number of arguments provided: " + args.length);
         System.out.println("Check usage and try again.");
         printUsageAndQuit();
      }
      
      // Parse the arguments
      mode = getMode(args);
      encodingFile = getEncodingFile(args);
      inputFile = getInputFile(args);

     // Build Huffman tree
      HuffmanTree tree = new HuffmanTree(FreqTableHandler.getFrequencies(encodingFile));
     
     // Encode the input file
     if(mode.equals("encode"){
     
     }
     
     // Or decode the input file
     else{
     
     }
          
  }
  
   private static String getMode(String[] args){
      /**
      ** getMode() reads the mode provided from the command line and validates
      ** that it is a valid usage mode. If, a message and the usage guide is printed,
      ** and the program exits.
      **
      ** @param args The command line arguments passed to the program.
      ** @param String The program mode, either "encode" or "decode"
      **/
      String mode = args[0].toLowerCase();
      if(!mode.equals("encode") || !mode.equals("decode")){
         System.out.println(mode + " is not a valid mode. Please check usage and try again.");
         printUsageAndQuit();
      }
      return mode;
   }
   
   private static File getEncodingFile(String[] args){
      File encodingFile = new File(args[1]);
      if(!encodingFile.exists()){
         System.out.println("Sorry, the encoding file " + args[1] + " does not exist. Please check the name and try again.");
         printUsageAndQuit();
      }
      return encodingFile;
   }
   
   private static File getInputFile(String[] args){
      File inputFile = new File(args[2]);
      if(!inputFile.exists()){
         System.out.println("Sorry, the input file " + args[2] + " does not exist. Please check the name and try again.");
         printUsageAndQuit();
      }
      return inputFile;
   }

   public static void printUsageAndQuit(){
      /**
      ** printUsageAndQuit() is intended to be called when the arguments
      ** passed by the user cannot be parsed. It prints out a usages guide
      ** to help the user, and then causes the program to terminate.
      **
      ** @return None Nothing is returned.
      **/
      String usageString = "";
      usageString += "Usage: Lab3Main mode encoding_file input_file\n";
      usageString += "\tmode: \"encode\" or \"decode\"\n";
      usageString += "\tencoding_file: The file containing frequency data used to generate encoding/decoding key\n";
      usageString += "\tinput_file: The file containing text to be encoded or decoded\n";
      usageString += "\toutput_file: The file to write the decoded/encoded message to.\n";
   
      System.out.println(usageString);
      System.exit(0); 
  }
   
}
