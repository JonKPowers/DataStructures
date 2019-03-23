import java.io.FileWriter;
import java.io.IOException;

class RecursiveTowers{
   
   private int numDiscs;
   private String startTower, targetTower, tempTower;
   private String output;
   private FileWriter outputFile;
   private int outputCounter;
   private Memoizer moveMemory;
   
   RecursiveTowers(int numDiscs, String targetTower, FileWriter outputFile){
      this.numDiscs = numDiscs;
      this.moveMemory = new Memoizer(numDiscs);
      
      this.startTower = "A";
      this.targetTower = targetTower;
      this.tempTower = targetTower.equals("C") ? "B" : "C";
      
      this.output = "";
      this.outputFile = outputFile;
      this.outputCounter = 0;
   }
   
   public void solve() throws IOException{
      moveDiscs(this.numDiscs, this.startTower, this.tempTower, this.targetTower);
      this.dumpOutput(0);
   }
   
   public String getMoveList(){
      return this.output;
   }
   
   private void checkMoveMemory(int numDiscs, String startTower, String endTower) {
   
   }

   private void moveDiscs(int numDiscs, String start, String temp, String end) throws IOException {
     
      if (numDiscs == 1){
      // Base case of 1 disc--just move it to its destination
      makeMove(numDiscs, start, end);
      } else {
         // Otherwise, move other discs out of the way, move the disc
         // to its destination, then move the other discs to their
         // final desination.

         moveDiscs(numDiscs - 1, start, end, temp);
         makeMove(numDiscs, start, end);
         moveDiscs(numDiscs - 1, temp, start, end);
      }     
      dumpOutput(100000);

   }

   private void makeMove(int numDiscs, String start, String end){
      this.output += "Move disc " + numDiscs + " from Tower " + start + " to Tower " + end + "\n";
   }
   
   private void dumpOutput(int maxBuffered)throws IOException{
      if (this.outputCounter > maxBuffered) {
         this.outputFile.write(this.output);
         this.outputCounter = 0;
         this.output = "";
      }
      
      this.outputCounter++;
   }
}
