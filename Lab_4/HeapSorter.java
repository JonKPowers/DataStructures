class HeapSorter
{
   /**
    ** HeapSorter implements a Heap Sort for integers.
    **
    ** @author Jon Powers
    ** @version 0.1
    **/

   private int[] heap;
   private int heapSize;      // # of items in the queue;
   Timer timer;
   
   //////////////////////////////
   // Constructor
   //////////////////////////////
   
   HeapSorter(Timer timer){
      this.timer = timer;
   }

   //////////////////////////////
   // Public Methods
   //////////////////////////////

   public void sort(int[] array){
      heapSize = 0;
      heap = new int[array.length + 1];
      timer.start();
      for(int item: array){
         push(item);
      }
      for(int i=0; i<array.length; i++){
         array[i] = pop();
      }
      timer.stop();
      if(!isEmpty()){
         throw new RuntimeException("Something's not right");
      }
   }

   //////////////////////////////
   // Private Methods
   //////////////////////////////

   private void push(int value){
      /**
       ** push() adds a new integer onto the heap. To put the
       ** new integer into the correct position in the heap, it proceeds
       ** by first adding the new value at the bottom of the heap
       ** and then swapping it out with its parent until its ordering relative
       ** to its parent is not incorrect. This position is done via a call
       ** to the bubbleUp() method.
       **
       ** @param value The integer to be added to the heap
       ** @return None Nothing is returned.
       **/
      heap[++heapSize] = value;
      bubbleUp(heapSize);
   }
         
   private int pop(){
      /**
       ** pop() pulls off and returns the item at the front of the heap.
       ** To maintain correct ordering of the heap, we take the last
       ** item in the heap array, place it at the top of the heap, and then
       ** repeatedly swap it out with the appropriate child until the item is
       ** in the correct position within the heap. This reordering is done
       ** via a call to the pushDown() method.
       **
       ** @return int The item at the front of the heap.
       **
       **/
      int temp = heap[1];
      swap(1, heapSize--);
      pushDown(1);
      return temp;
   }

   private boolean isEmpty(){
      /**
       ** isEmpty() determines whether there are any items currently in the 
       ** heap. 
       **
       ** @return boolean True if the heap is empty; otherwise false
       **/

       return heapSize == 0;
   }

   private boolean moreThan(int i, int j){
      return heap[i] > heap[j];
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
      int temp = heap[i];
      heap[i] = heap[j];
      heap[j] = temp;
   }
}

