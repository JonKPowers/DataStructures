import java.util.Random;

class Sorter
{

   public static void qSort(int[] array, int stopSize, int mode){
      qSort(array, 0, array.length - 1, stopSize, mode);
   }

   public static void qSort(int[] array, int startIndex, int endIndex, int stopSize, int mode){
      int sortSize = endIndex - startIndex;
      // Base case 1: start and end indices have met or crossed; return no matter what
      if(sortSize < 0){
         return;
      // Base case 2: Size of array to sort is <= our stopSize; use insertion sort to finish
      } else if(sortSize <= stopSize) {
         iSort(array, startIndex, endIndex);
         return;
      }


      // Base case 1: Quicksort the whole way. end <= start
      if(endIndex <= startIndex){
         return;   
      }
      // Base case 2: Insert sort once subarray size <= _______
      // TO DO

      // The partitioning process positions the pivot
      int pivot = partition(array, startIndex, endIndex, mode);
      // Recursively call qSort on the left and right partitions
      qSort(array, startIndex, pivot-1, stopSize, mode);
      qSort(array, pivot+1, endIndex, stopSize, mode);
   }

   private static int partition(int[] array, int startIndex, int endIndex, int mode){
      int rightPos = endIndex;
      int leftPos = startIndex + 1;

      int pivot  = getPivot(array, startIndex, endIndex, mode);

      while(true){
         // Scan from left to right until we find an item >= pivot
         while(array[leftPos] < pivot){
            leftPos++;
            if(leftPos >= endIndex) break;   // Don't go past the end of the array
         }
         // Scan from right to left until we find an item <= pivot
         while(array[rightPos] > pivot){
            rightPos--;
            if(rightPos <= startIndex) break; // Don't go past the end of the array
         }

         // Break out of the loop once rightPos and leftPos cross paths
         if(leftPos >= rightPos) break;

         // Swap the items in the leftPos and rightPos positions;
         // Then advance the position markers
         int temp = array[leftPos];
         array[leftPos] = array[rightPos];
         array[rightPos] = temp;
         leftPos++;
         rightPos--;
      }

      // Swap out the pivot in startIndex position with rightPos
      int temp = array[rightPos];
      array[rightPos] = array[startIndex];
      array[startIndex] = temp;

      // Return the index position of the pivot
      return rightPos;

   }

   private static int getPivot(int[] array, int startIndex, int endIndex, int mode){
      /**
       * getPivot() returns the value of the pivot for use in a Quick Sort. It offers
       * several options for selecting a pivot value, indicated by the mode parameter:
       *    - mode == 0: Choose the first item in the subarray; i.e., array[startIndex]
       *    - mode == 1: Choose the last item in the subarray; i.e., array[endIndex]
       *    - mode == 2: Choose the middle item in the subarray; i.e., array[(startIndex + endIndex)/2]
       *    - mode == 3: Choose median-of-three as pivot
       *    - mode == 4: Choose random pivot
       *
       * @param array The array we are selecting a pivot from
       * @param startIndex Integer index marking start of subarray to select pivot from
       * @param endIndex Integer index marking end of subarray to select pivto from
       * @param mode Integer indicating pivot-selection mode (see above)
       * @return int Integer value of the selected pivot
       **/
      int pivot;

      switch(mode){
         case 0:
            return array[startIndex];
         case 1:
            return array[endIndex];
         case 2:
            return array[(startIndex + endIndex) / 2];
         case 3:
            Random random = new Random();
            int[] threes = {array[startIndex], array[(startIndex + endIndex) /2], array[endIndex]}; 
            iSort(threes);
            return threes[1];
         case 4:
            int span = endIndex - startIndex + 1;
            return array[random.nextInt(span) + startIndex];
      }
   }


   public static void iSort(int[] array){
      iSort(array, 0, array.length - 1);
   }

   public static void iSort(int[] array, int startIndex, int endIndex){
      int j;
      int value;
      
      // Sort each item in the array from startIndex to endIndex
      // Since the first item is trivially sorted, we can skip that one.
      for(int i=startIndex+1; i<=endIndex; i++){
         value = array[i];
         j = i-1;   
         
         // If the preceding value is greater than value, move it
         // ahead one position in the array
         while(j >= startIndex && array[j]>=value){
            array[j+1] = array[j];
            j--;
         }
         // Insert value into its correct position
         array[j+1] = value;
      }
   }
}
