class Sorter
{

   public static void qSort(int[] array){
      qSort(array, 0, array.length - 1);
   }

   public static void qSort(int[] array, int startIndex, int endIndex){
      // Base case 1: Quicksort the whole way. end <= start
      if(endIndex <= startIndex){
         return;   
      }
      // Base case 2: Insert sort once subarray size <= _______
      // TO DO

      // The partitioning process positions the pivot
      int pivot = partition(array, startIndex, endIndex);
      // Recursively call qSort on the left and right partitions
      qSort(array, startIndex, pivot-1);
      qSort(array, pivot+1, endIndex);
   }

   private static int partition(int[] array, int startIndex, int endIndex){
      int rightPos = endIndex;
      int leftPos = startIndex + 1;

      int pivot  = getPivot(array, startIndex, endIndex);

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

   private static int getPivot(int[] array, int startIndex, int endIndex){
      return array[startIndex];
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
