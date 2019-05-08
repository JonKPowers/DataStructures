import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


class SortFileGenerator
{

   public static void main( String[] args ){
      for(int i=50; i<220000; i += 200){
         makeSortedFile(i);
         makeReversedFile(i);
         makeRandomFile(i);
         makeDuplicatesFile(i, 0.2);
      }
   }

   private static void makeSortedFile(int n){
      /** makeSortedFile() generates a file of random numbers in sorted order.
       * It arbitrarily sets the maximum difference between two successive
       * numbers to be 5 so that the maximum value is no larger than 5 * n,
       * which should not create any MAX_VALUE issues for purposes of the
       * Lab 4 project.
       *
       * @param outputFile A File object associated with the desired output file name
       * @param n Integer representing how many values should be placed into outputFile
       * @return None Nothing is returned; the file is written directly.
       **/
   
      File outputFile = getOutputFile("asc", n);

      // Set up random number generator 
      int currentNum = 0;
      Random random = new Random();

      // Write random numbers out to file
      try(FileWriter output = new FileWriter(outputFile)){
         for(int i=0; i<n; i++){
            currentNum += random.nextInt(4) + 1;
            output.write(currentNum + "\n");
         }
      } catch (IOException except) {
         System.out.println("There was a problem writing to output file(s). Please check and try again.");
         System.exit(0);
      }

   }

   private static void makeReversedFile(int n){
   
      File outputFile = getOutputFile("rev", n);
      IntStack stack = new IntStack(n);
      int currentNum = 0;
      Random random = new Random();

      for(int i=0; i<n; i++){
         currentNum += random.nextInt(4) + 1;
         stack.push(currentNum);
      }

      try(FileWriter output = new FileWriter(outputFile)){
         while(!stack.isEmpty()){
            output.write(stack.pop() + "\n");
         }
      } catch (IOException except) {
         System.out.println("There was a problem writing to output file(s).\n" + except.getMessage() + "\nPlease check and try again.");
         System.exit(0);
      }
   }

   private static void makeRandomFile(int n){
      File outputFile = getOutputFile("ran", n);
      Random random = new Random();
      int[] tracker = new int[n * 5];
      int tmpInt;
      int counter = 0;

      try(FileWriter output = new FileWriter(outputFile)){
         while(counter < n){
            tmpInt = random.nextInt(5*n);
            if(tracker[tmpInt] == 1) continue;
            output.write(tmpInt + "\n");
            counter++;
         }
      } catch(IOException except) {
         System.out.println("There was a problem writing to output file(s).\n" + except.getMessage() + "\nPlease check and try again.");
         System.exit(0);
      }

   }

   private static void makeDuplicatesFile(int n, double ratio){
      Random random = new Random();
      int range = (int) (n / (1 + ratio));
      File outputFile = getOutputFile("dup", n, ratio);

      int[] ints = new int[n];
      int[] counter = new int[range];
      for(int i=0; i<n; i++){
         ints[i] = random.nextInt(range);
         counter[ints[i]] += 1;
      }
      
      int dupes = 0;
      for(int item: counter){
         if(item > 1) dupes += (item - 1);
      }
      System.out.println("Duplicate file stats:\n\tSize: " + n + "\n\tRequested dupe %: " + ratio + "\n\tActual dupe %: " + ((double) dupes / n) + "\n");

      try(FileWriter output = new FileWriter(outputFile)){
         for(int item : ints){
            output.write(item + "\n");
         }
      } catch (IOException except) {
         System.out.println("There was a problem writing to output file(s).\n" + except.getMessage() + "\nPlease check and try again.");
         System.exit(0);
      }
   }

   private static File getOutputFile(String type, int n){
      String fileName = "";
      fileName += type;
      fileName += n + "_";
      fileName += new SimpleDateFormat("mmssSSS").format(new Date());
      fileName += ".dat";
      return new File("inputFiles/newerer/" + fileName);
   }

   private static File getOutputFile(String type, int n, double ratio){
      String fileName = "";
      fileName += type;
      fileName += (int)(ratio * 100) + "percent_";
      fileName += n + "_";
      fileName += new SimpleDateFormat("mmssSSS").format(new Date());
      fileName += ".dat";
      return new File("inputFiles/newerer/" + fileName);
   }

}
