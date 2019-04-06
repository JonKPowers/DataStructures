class Lab3Main
{
   
  public static void main( String[] args ){

     // Get encoding file
     // Get encoded input file
     // Get cleartext file to encode
     
  } 

  public static void printUsageAndQuit(){
   /**
   ** printUsageAndQuit() is intended to be called when the arguments
   ** passed by the user cannot be parsed. It prints out a usages guide
   ** to help the user, and then causes the program to terminate.
   **
   ** @return None Nothing is returned.
   **/
   String usageString = ""
   usageString += "Usage: Lab3Main mode encoding_file input_file";
   usageString += "\tmode: \"encode\" or \"decode\"";
   usageString += "\tencoding_file: The file containing frequency data used to generate encoding/decoding key";
   usageString += "\tinput_file: The file containing text to be encoded or decoded";
   usageString += "output_file: The file to write the decoded/encoded message to."

    System.exit(0); 
  }
   
}
