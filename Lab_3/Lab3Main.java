import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Lab3Main
{
   
   public static void main( String[] args ){
      // Variables for command-line argument info
      String mode;
      Boolean richText;
      File encodingFile;
      File inputFile;
      String outputFile;
      HuffmanTree tree;
      StringStackDataPack[] inputData;
      
      
      // Check that we have the right number of arguments
      if(args.length < 4){
         System.out.println("Incorrect number of arguments provided: " + args.length);
         System.out.println("Check usage and try again.");
         printUsageAndQuit();
      }
      
      // Parse the arguments
      mode = getMode(args);
      encodingFile = getEncodingFile(args);
      inputFile = getInputFile(args);
      outputFile = args[3];
      richText = getRichText(args);

      // Build Huffman tree
      tree = new HuffmanTree(FreqTableHandler.getFrequencies(encodingFile));
      // Get data from input file
      inputData = InputFileHandler.getLinesFromFileAsStacks(inputFile);
     
      try(FileWriter outputWriter = new FileWriter(outputFile)){
         for(StringStackDataPack data : inputData){
            if(data == null){ // Skip any null entries that snuck into the datapack array
               continue;
            }
            
            // Apply any input-specific configuration
            mode = data.setModeEncode() ? "encode" : mode;
            mode = data.setModeDecode() ? "decode" : mode;
            richText = data.turnRichTextOn() ? true : richText;
            richText = data.turnRichTextOff() ? false : richText;
            
            
            // Setup variables and output boilerplate
            StringStack inputDataStack = data.stack;
            String inputString = data.stack.viewStack();
            outputWriter.write("*************************\n*************************\n");
            outputWriter.write("Mode: " + mode + "\n");
            outputWriter.write("Input: " + inputString + "\n");
            
            
            
            // Encode the input file
            if(mode.equals("encode")){
               try{
                  String encodedString = tree.encodeString(inputDataStack);
                  int rawSize = inputString.getBytes("UTF-8").length;
                  double compressedSize = encodedString.length() / 8.0;
                  
                  outputWriter.write("Original size: " + inputString.length() + " characters (" + rawSize + " bytes using UTF-8)\n");
                  outputWriter.write("Encoded size: " + encodedString.length() + " bits (" + compressedSize + " bytes)\n");
                  outputWriter.write("Compression: " + (compressedSize / rawSize * 100) + "%\n");
                  outputWriter.write("\nPreorder traversal: " + tree.getPreorderTraversal() + "\n");
                  outputWriter.write("\nEncoded string: " + encodedString + "\n\n\n");
               } catch (EncodingException except){
                  outputWriter.write("Error during encoding: " + except.getMessage());
               }
            }
            // Or decode the input file
            else{
               try{
                  String decodedString = tree.decode(inputDataStack);
                  int rawSize = decodedString.getBytes("UTF-8").length;
                  double compressedSize = inputString.length() / 8.0;
                  outputWriter.write("Encoded size: " + inputString.length() + " bits (" + compressedSize + " bytes)\n");
                  outputWriter.write("Decoded size: " + decodedString.length() + " characters (" + rawSize + " bytes)\n");
                  outputWriter.write("Compression: " + (compressedSize / rawSize * 100) + "%\n");
                  outputWriter.write("\nPreorder traversal: " + tree.getPreorderTraversal() + "\n");
                  outputWriter.write("\nDecoded string: " + decodedString +"\n\n\n");
               } catch (EncodingException except){
                  outputWriter.write("Error during decoding: " + except.getMessage());
               }
            }
            
         // Put mode and richText variables back to original state for next StringStackDataPack
         mode = getMode(args);
         richText = getRichText(args);
         
         }
      } catch (IOException except){
         System.out.println("There was an error writing the output to file; please check your filename and try again.");
         printUsageAndQuit();
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
      if(!mode.equals("encode") && !mode.equals("decode")){
         System.out.println(mode + " is not a valid mode. Please check usage and try again.");
         printUsageAndQuit();
      }
      return mode;
   }
   
   private static File getEncodingFile(String[] args){
      /**
      ** getEncodingFile() parses the command line arguments for the frequency table file
      ** to be used in constructing the Huffman tree and checks to make sure it exists.
      ** If the file does not exist, an error message is displayed, and the program exits.
      ** 
      ** @param args The command line arguments passed to the program
      ** @return File A File object representing the frequency table file
      **/
      File encodingFile = new File(args[1]);
      if(!encodingFile.exists()){
         System.out.println("Sorry, the encoding file " + args[1] + " does not exist. Please check the name and try again.");
         printUsageAndQuit();
      }
      return encodingFile;
   }
   
   private static File getInputFile(String[] args){
      /**
      ** getInputFile() parses the command line arguments for the input file containing
      ** the encoded text to be decoded or clear text to be encoded. It checks to make
      ** sure the file exists; if it doesn't, an error message is displayed, and the program exits.
      ** 
      ** @param args The command line arguments passed to the program
      ** @return File A File object representing the input file
      **/
      File inputFile = new File(args[2]);
      if(!inputFile.exists()){
         System.out.println("Sorry, the input file " + args[2] + " does not exist. Please check the name and try again.");
         printUsageAndQuit();
      }
      return inputFile;
   }
   
   private static boolean getRichText(String[] args){
      /**
      ** getRichText() parses the command line arguments to determine whether the rich-text enhancement
      ** has been enabled by the user at the command line. It is disabled by default.
      **
      ** @param args The command line arguments passed to the program
      ** @return boolean True is rich-text enhancements have been enabled; otherwise false
      **/
      
      // If no argument was provided, enhancements are disabled
      if(args.length < 5) return false;
      
      // Enable if passed at the command line
      if(args[4].toLowerCase().equals("enable")) return true;
      
      // Disable by default
      return false;
   
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
      usageString += "Usage: Lab3Main mode encoding_file input_file output_file\n";
      usageString += "\tmode: \"encode\" or \"decode\"\n";
      usageString += "\tencoding_file: The file containing frequency data used to generate encoding/decoding key\n";
      usageString += "\tinput_file: The file containing text to be encoded or decoded\n";
      usageString += "\toutput_file: The file to write the decoded/encoded message to.\n";
   
      System.out.println(usageString);
      System.exit(0); 
  }
   
}
