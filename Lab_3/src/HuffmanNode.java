class HuffmanNode
{
   /**
   ** The HuffmanNode class provides units to represent nodes in a HuffmanTree.
   **
   ** @author Jon Powers
   ** @version 0.1
   **/

   private int frequency;
   private String characters;
   private boolean multiChar;
   private boolean isLeftChild;
   private HuffmanNode parent;
   private HuffmanNode leftChild;
   private HuffmanNode rightChild;

   HuffmanNode(String characters, int frequency){
      /**
      ** Constructor for a HuffmanNode. This constructor provides a simple leaf node,
      ** which initializes the characters and frequency fields of the HuffmanNode, and 
      ** leaves the remaining attributes null.
      **
      ** @param characters The character(s) represented by the HuffmanNode
      ** @param frequency The frequency associated with the character
      **/
      this.frequency = frequency;
      this.characters = characters;
      this.multiChar = characters.length() > 1;
      this.isLeftChild = false;
      this.parent = null;
      this.leftChild = null;
      this.rightChild = null;
   }
   
   HuffmanNode(HuffmanNode leftChild, HuffmanNode rightChild){
      /**
      ** Constructor for a HuffmanNode. This constructor is intended for use when
      ** initializing new parent nodes. The child nodes are passed in, and they are
      ** modified along with the parent's attributes to cement the relationship
      ** between the nodes.
      **
      ** @param leftChild This HuffmanNode's left child
      ** @param rightChild This HuffmanNode's right child
      **/
      this.frequency = leftChild.getFrequency() + rightChild.getFrequency();
      this.characters = leftChild.getChars() + rightChild.getChars();
      this.multiChar = characters.length() > 1;
      this.isLeftChild = false;
      this.parent = null;
      leftChild.setAsLeftChild(true);
      leftChild.setParent(this);
      this.leftChild = leftChild;
      rightChild.setParent(this);
      this.rightChild = rightChild;
   }
   
   /////////////////////////
   // Getters and setters
   /////////////////////////
   public int getFrequency(){
      return this.frequency;
   }

   public String getChars(){
      return this.characters;   
   }
   
   public HuffmanNode getParent(){
      return this.parent;
   }
   
   public HuffmanNode getLeftChild(){
      return this.leftChild;
   }
   
   public HuffmanNode getRightChild(){
      return this.rightChild;
   }
   
   public void setParent(HuffmanNode parent){
      this.parent = parent;
   }
   
   public void setAsLeftChild(boolean isLeftChild){
      this.isLeftChild = isLeftChild;
   }
   
   /////////////////////////
   // State checkers
   /////////////////////////
   
   public boolean isMultiChar(){
      /**
      ** isMultiChar() determines whether this HuffmanNode represents multiple characters.
      ** This can be used to determine whether it is a leaf node on the HuffmanTree.
      **
      ** @return boolean True if the HuffmanNode represents multiple characters; otherwise, false
      **/
      return this.multiChar;
   }
   
   public boolean isLeaf(){
      /**
      ** isLeaf() determines whether this HuffmanNode is a leaf on the Huffman tree
      **
      ** @return boolean True if the HuffmanNode is a leag node; otherwise, false
      **/
      return this.leftChild == null && this.rightChild==null;
   }
   
   public boolean isLeft(){
      /**
      ** isLeft() determines whether this HuffmanNode is the left child of its parent node (if any).
      **
      ** @return boolean True if the HuffmanNode is a left child of another node; otherwise, false
      **/
      return this.isLeftChild;
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
      return result < 0 ? true : false;
   }

   public String toString(){
      /**
      ** toString() generates a simple String representation of the HuffmanNode
      ** 
      ** @return String A String representation of the HuffmanNode
      **/
      String output = "Huffman Node:";
      output += "\n\tCharacter: " + this.characters;
      output += "\n\tFrequency: " + this.frequency;
      return output;
   }

}
