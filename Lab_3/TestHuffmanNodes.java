class TestHuffmanNodes
{

   public static void main( String[] args){
   
      // Test basic creation of class and that attributed work correctly
      HuffmanNode newNode = new HuffmanNode("A", 5);
      assert newNode.getFrequency() == 5;
      assert newNode.getChars() == "A";
      assert newNode.isMultiChar() == false; 
   }
   
}
