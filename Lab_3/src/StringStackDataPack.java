class StringStackDataPack
{
   /**
   ** StringStackDataPack is a helper class that acts as a container for individual
   ** test cases for the Lab1PostfixConverter. It contains a stack with the test 
   ** input, a boolean indicating whether optimization is selected in the test input
   ** file, and a String with any comments about the test case (to be used when
   ** displaying output.
   **/
   
   public String comments;
   public boolean richTextOn;
   public boolean richTextOff;
   public boolean modeEncode;
   public boolean modeDecode;
   public StringStack stack;
   
   StringStackDataPack(){
      comments = new String();
      stack = new StringStack();
   }
   
   public boolean turnRichTextOn(){
      return richTextOn == true && richTextOff == false;
   }
   
   public boolean turnRichTextOff(){
      return richTextOff == true && richTextOn == false;
   }
   
   public boolean setModeEncode(){
      return modeEncode == true && modeDecode == false;
   }
   
   public boolean setModeDecode(){
      return modeDecode == true && modeEncode == false;
   }
   
}