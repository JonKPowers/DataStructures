class PriorityQueue
{
   /**
    ** PriorityQueue implements a priority queue for HuffmanNodes.
    ** It prioritizes HuffmanNodes with the lowest values.
    **
    ** @author Jon Powers
    ** @version 0.1
    **/

   private HuffmanNode[] heap;
   private int heapSize;      // # of items in the queue;

   //////////////////////////////
   // Constructors
   //////////////////////////////

   PriorityQueue(int maxQueueSize){
      /**
       ** Constructor for a PriorityQueue containing HuffmanNodes. The size of the
       ** heap array is queueSize + 1 because index position 0 is not used because
       ** the arithmetic methods of calculating parents and children do not work
       ** with an input index of 0.
       **
       ** In additio, I've added 20 extra spaces into the heap for any enhancement
       ** characters added that need to be added to the PriorityQueue
       **
       ** @param maxQueueSize Integer specifying the maximum number of items in the priority queue.
       **/
      heap = new HuffmanNode[maxQueueSize + 1 + 20];
      heapSize = 0;
   }
   
   //////////////////////////////
   // Public Methods
   //////////////////////////////

   // ***************************
   // Getters, setters, etc.
   // ***************************

   public boolean isEmpty(){
      /**
       ** isEmpty() determines whether there are any items currently in the 
       ** PriorityQueue. 
       **
       ** @return boolean True if the PriorityQueue is empty; otherwise false
       **/

       return heapSize == 0;
   }
   
   public boolean hasMoreThanOneItem(){
      /**
      ** hasMoreThanOneItem() determines whether there is more than one 
      ** item currently in the PriorityQueue.
      **
      ** @return boolean True if there is more than one item in the PriorityQueue; otherwise false
      **/
      
      return heapSize > 1;
   }

   public int getLength(){
      /**
       ** getSize() determines the number of items currently in the PriorityQueue.
       **
       ** @return int The number of items currently in the PriorityQueue.
       **/

      return heapSize;
   }

   // ***************************
   // Priorty Queue Operations
   // ***************************

   public void push(HuffmanNode newNode){
      /**
       ** push() adds a new HuffmanNode onto the PriorityQueue. To put the
       ** new node into the correct position in the PriorityQueue, it proceeds
       ** by first adding the new node at the bottom of the underlying heap
       ** and then swapping it out with its parent until its ordering relative
       ** to its parent is not incorrect. This position is done via a call
       ** to the bubbleUp() method.
       **
       ** @param newNode The HuffmanNode to be added to the PriorityQueue.
       ** @return None Nothing is returned.
       **/
      heap[++heapSize] = newNode;
      bubbleUp(heapSize);
   }
         
   public HuffmanNode pop(){
      /**
       ** pop() pulls off and returns the item at the front of the PriorityQueue.
       ** To maintain correct ordering of the underlying heap, we take the last
       ** item in the heap array, place it at the top of the heap, and then
       ** repeatedly swap it out with the appropriate child until the item is
       ** in the correct position within the heap. This reordering is done
       ** via a call to the pushDown() method.
       **
       ** @return HuffmanNode The item at the front of the PriorityQueue.
       **
       **/
      HuffmanNode temp = heap[1];
      swap(1, heapSize--);
      pushDown(1);
      return temp;
   }

   //////////////////////////////
   // Private Methods
   //////////////////////////////

   private boolean moreThan(int i, int j){
      return !heap[i].isLessThan(heap[j]);
   }

   private void bubbleUp(int nodeIndex){
      /**
       ** bubbleUp() provide a mechanism for correcting the heap order when an
       ** out-of-order item is located towards the bottom of the heap. The
       ** item is repeatedly exchanged with its parent until it is no longer
       ** smaller than its immediate parent.
       **
       ** @param nodeIndex The array index of the item to place into the correct heap position
       **/
      
      while(nodeIndex > 1 && moreThan(nodeIndex/2, nodeIndex)){
         // Swap the node with its parent, then move up and repeat
         swap(nodeIndex, nodeIndex/2);
         nodeIndex = nodeIndex/2;
      }
   }

   private void pushDown(int nodeIndex){
      /**
      ** pushDown() provides a mechanism for correcting the heap order when an
      ** out-of-order item is located towards the top of the heap. The item is
      ** repeatedly exchanged with its smallest child until it is no longer larger
      ** than either of its children. 
      **
      ** @param nodeIndex The array index of the itme to place into the correct heap position
      **/
      while(nodeIndex * 2 <= heapSize){
         // Find the child with the smallest value
         int child = nodeIndex * 2;
         if(child < heapSize && moreThan(child, child + 1)){
            child++;
         }
         // If the current node is more than the child, swap them and
         // move down the tree; otherwise, the node is in the right 
         // place, and we're done.
         if(moreThan(nodeIndex, child)){
            swap(nodeIndex, child);
            nodeIndex = child;
         } else {
            break;
         }
      }
   }

   private void swap(int i, int j){
      /**
       ** swap() causes the items in positions i and j in the heap array to
       ** be exchanged.
       **
       ** @param i The array index of the first item to be swapped
       ** @param j The array index of the other item to be swapped
       ** @return None Nothing is returned
       **/
      HuffmanNode temp = heap[i];
      heap[i] = heap[j];
      heap[j] = temp;
   }
}

