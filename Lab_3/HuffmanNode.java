class HuffmanNode
{

   private int frequency;
   private String characters;
   private boolean multiChar;

   HuffmanNode(String characters, int frequency){
      this.frequency = frequency;
      this.characters = characters;
      this.multiChar= characters.length() > 1;
   }

   public int getFrequency(){
      return this.frequency;
   }

   public String getChars(){
      return this.characters;   
   }

   public boolean isMultiChar(){
      return this.multiChar;
   }

   public boolean isLessThan(HuffmanNode otherNode){
      /**
       ** isLessThan() provides a method to compare two HuffmanNodes to
       ** determine which has a "lower" value for purposes of constructing
       ** a Huffman binary tree. Nodes with a lower frequency value are ranked
       ** lower than those with higher frequency values. After that, nodes 
       ** whose character attribute is a single character will be ranked lower
       ** than those with character attributes consisting of multiple characters.
       ** Last, nodes are ranked in alphabetical order. In the case that the
       ** otherNode passed in is identical to this Node in all the above ways,
       ** isLessThan() will return false.
       **
       ** @param otherNode A HuffmanNode that the current node is to be compared to.
       ** @return boolean True if the current node is ranked "lower" than the otherNode; otherwise false.
       **/
      int result = Integer.compare(this.frequency, otherNode.getFrequency());
      if(result == 0){
         result = Boolean.compare(this.multiChar, otherNode.isMultiChar());   
      }
      if(result == 0){
         result = this.characters.compareTo(otherNode.getChars());    
      }
      return result == -1 ? true : false;
   }

   public String toString(){
      String output = "Huffman Node:";
      output += "\n\tCharacter: " + this.characters;
      output += "\n\tFrequency: " + this.frequency;
      return output;
   }

}
