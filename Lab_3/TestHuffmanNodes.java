class TestHuffmanNodes
{

   public static void main( String[] args){
   
      // Test basic creation of class and that attributed work correctly
      HuffmanNode newNode = new HuffmanNode("A", 5);
      assert newNode.getFrequency() == 5;
      assert newNode.getChars() == "A";
      assert newNode.isMultiChar() == false; 
      
      HuffmanNode smallerNode = new HuffmanNode("B", 16);
      HuffmanNode biggerNode = new HuffmanNode("I", 16);
      smallerNode.isLessThan(biggerNode);
   }
   
}
