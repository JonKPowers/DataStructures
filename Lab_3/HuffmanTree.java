class HuffmanTree
{

   private HuffmanNode[] leafNodes;
   private HuffmanNode treeRoot;
   private HuffmanNode[] searchTree;
   private HuffmanCode[] codes;
   private HuffmanCode[] binaryCodeTree;
   
   /////////////////////////
   // Constructors
   /////////////////////////
   
   HuffmanTree(PriorityQueue queue){
      /**
      ** Constructor for HuffmanTree. The HuffmanTree must be initialized with a PriorityQueue
      ** that is seeded with the characters and frequencies to be used in the Huffman
      ** encoding. On instantiation, the Huffman tree is built, codes for each of the characters
      ** are generated, and a binary search tree is constructed for purposes of decoding.
      **
      ** @param queue A PriorityQueue containing the characters and letters to be used in the Huffman encoding.
      ** @return None Nothing is returned.
      **/
      buildTree(queue);
      this.codes = generateCodes();
      this.binaryCodeTree = buildSearchTree();
      
   }
   
   /////////////////////////
   // Public Methods
   /////////////////////////
   
   public String getCode(String searchCharacter){
      /**
      ** getCode() attempts to find the Huffman code for the provided searchCharacter.
      ** If no code is found, an empty string is returned.
      ** 
      ** @param searchCharacter A String containing the character we want to find the code for.
      ** @return String A String containing the Huffman code for searchCharacter; if not found, an empty Stringge
      **/
      int currentNode = 1;
      
      while(binaryCodeTree[currentNode] != null && !(currentNode > (binaryCodeTree.length - 1))){
         // If we have a match return the code
         if(isMatch(currentNode, searchCharacter)){
            return binaryCodeTree[currentNode].getCode();
         }
         
         // Otherwise, determine whether to go left or right down the tree and try again
         boolean goLeft = searchCharacter.compareTo(binaryCodeTree[currentNode].getCharacter()) == -1;
         currentNode = goLeft? currentNode * 2 : (currentNode * 2) + 1;
      }
      
      // If the value is not found in our code tree, return an empty String
      return "";
      
      
   
   }
   
   
   /////////////////////////
   // Private Methods
   /////////////////////////
   
   private HuffmanCode[] buildSearchTree(){
      /**
      ** buildSearchTree() constructs a binary search tree for use in decoding a message.
      ** It is built from the HuffmanCode objects in this.codes.
      **
      ** @return HuffmanCode[] A binary tree of HuffmanCode objects contained in an array.
      **/
      HuffmanCode[] tree = new HuffmanCode[(int) Math.pow(2,leafNodes.length) + 1];
      tree[1] = codes[0];
      for(int i=1; i<codes.length; i++){
         int currentNodeIndex = 1;
         int nextNodeIndex =1;
         String newCharacter = codes[i].getCharacter();
         
         do{
            currentNodeIndex = nextNodeIndex;
            if(newCharacter.compareTo(tree[currentNodeIndex].getCharacter()) == -1){
               nextNodeIndex = currentNodeIndex * 2;
            } else {
               nextNodeIndex = (currentNodeIndex * 2) + 1;
            }
         } while (tree[nextNodeIndex] != null);
         
         tree[nextNodeIndex] = codes[i];
      }
      
      return tree;
   }
   
   private HuffmanCode[] generateCodes(){
      /**
      ** generateCodes() goes through each letter (which are contained in this.leafNodes)
      ** and generates the Huffman code for that letter. The codes are stored in 
      ** HuffmanCode objects, and an array of those are returned, one for each letter.
      **
      ** @return HuffmanCode[] An array of HuffmanCode objects containing the encoded letters.
      **/
      HuffmanCode[] codes = new HuffmanCode[this.leafNodes.length];   
      
      for(int i=0; i<this.leafNodes.length; i++){
         HuffmanNode node = this.leafNodes[i];
         String code = generateCode(node);
         
         codes[i] = new HuffmanCode(node.getChars(), code);   
      }
      
      return codes;
   }
   
   private String generateCode(HuffmanNode node){
      /**
      ** generateCode() creates the HuffmanEncoded value for a character by starting
      ** at the associated leaf node and navigating its way up to the root of the 
      ** HuffmanTree, recording its path along the way.
      **
      ** @param node The HuffmanNode leaf node associated with the character we want to encode.
      ** @return String A String representation of the Huffman encoded value for the character.
      **/
      int[] code = new int[leafNodes.length];
      int counter = 0;
      String codeString = "";
      HuffmanNode tempNode = node;
      
      while(tempNode != treeRoot){
         code[counter++] = tempNode.isLeft() ? 0 : 1;
         tempNode = tempNode.getParent();
      } 
      
      for(int i=counter-1; i>=0; i--){
         codeString += code[i];
      }
      
      return codeString;
   }
   
   private void buildTree(PriorityQueue queue){
      /**
      ** buildTree() constructs a Huffman tree out of the HuffmanNodes 
      ** contained in the PriorityQueue passed to it. The leaf nodes are
      ** kept in this.leafNodes, which contain pointer references to the
      ** other parts of the tree.
      **
      ** It constructs the tree by repeatedly pulling the lowest-frequency
      ** nodes out of the PriorityQueue, constructing an internal parent node
      ** for those two nodes, and then adding the parent node to the
      ** PriorityQueue. This process continues until there is just one node
      ** left in the PriorityQueue, which is the treeRoot.
      **
      ** @param queue A PriorityQueue containing the HuffmanNodes to be used in the Huffman tree
      ** @return None Nothing is returned
      **
      **/
      
      int counter = 0;
      leafNodes = new HuffmanNode[queue.getLength()];
      
      while(queue.hasMoreThanOneItem()){
         // Take off the two lowest-frequency nodes
         HuffmanNode leftNode = queue.pop();
         HuffmanNode rightNode = queue.pop();
         
         // Add leaf nodes to leafNodes[]
         if(leftNode.isLeaf()){
            leafNodes[counter++] = leftNode;
         }
         if(rightNode.isLeaf()){
            leafNodes[counter++] = rightNode;
         }
         
         // Construct a new node with them and add it back into the PriorityQueue
         queue.push(new HuffmanNode(leftNode, rightNode));
      }
      
      // Set this.treeRoot
      this.treeRoot = queue.pop(); 
   }
   
   private boolean isMatch(int searchNode, String searchCharacter){
      /**
      ** isMatch() checks whether the character represented by the item in
      ** this.binaryCodeTree[searchNode] matches the searchCharacter. 
      ** Used during traversal of the binary tree while searching for a value.
      **
      ** @param searchNode The position index of the node to check.
      ** @param searchCharacter A String containing the character we're trying to find
      **
      ** @return boolean True if the item in this.binaryCodeTree[searchNode] matches searchCharacter; otherwise false.
      **/
      
      return binaryCodeTree[searchNode].getCharacter().equals(searchCharacter);
   }
}