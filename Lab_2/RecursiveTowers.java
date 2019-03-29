import java.io.FileWriter;
import java.io.IOException;

class RecursiveTowers{
   
   private int numDiscs;
   private String startTower, targetTower, tempTower;
   private String output;
   
   private FileWriter outputFile;
   private int outputCounter;
   private final int DUMP_LIMIT = 100000;
   
   private boolean optimize;
   private Memoizer moveMemory;
   
   RecursiveTowers(int numDiscs, String targetTower, FileWriter outputFile, boolean optimize){
      this.numDiscs = numDiscs;
      this.moveMemory = new Memoizer(numDiscs);
      
      this.startTower = "A";
      this.targetTower = targetTower;
      this.tempTower = targetTower.equals("C") ? "B" : "C";
      
      this.output = "";
      this.outputFile = outputFile;
      this.outputCounter = 0;
      
      this.optimize = optimize;
   }
   
   public void solve() throws IOException{
      moveDiscs(this.numDiscs, this.startTower, this.tempTower, this.targetTower);
      this.dumpOutput(0);
   }
   
   public String getMoveList(){
      return this.output;
   }

   private String moveDiscs(int numDiscs, String start, String temp, String end) throws IOException {
      if (this.optimize && this.moveMemory.moveStored(numDiscs, start, end)) {
         String moves = this.moveMemory.getMoves(numDiscs, start, end);
         this.outputFile.write(moves);
         return moves;
         
      } else {
         
         String outputString = "";
         if (numDiscs == 1){
            // Base case of 1 disc--just move it to its destination
            outputString += makeMove(numDiscs, start, end);
         } else {
            // Otherwise, move other discs out of the way, move the disc
            // to its destination, then move the other discs to their
            // final desination.
   
            outputString += moveDiscs(numDiscs - 1, start, end, temp);
            outputString += makeMove(numDiscs, start, end);
            outputString += moveDiscs(numDiscs - 1, temp, start, end);
         }
         
         dumpOutput(this.DUMP_LIMIT);
         if(this.optimize && !this.moveMemory.moveStored(numDiscs,start, end)){
            moveMemory.setMoves(numDiscs, start, end, outputString);
         }
         
         return outputString;
      }
   }

   private String makeMove(int numDiscs, String start, String end){
      String outputString = "Move disc " + numDiscs + " from Tower " + start + " to Tower " + end + "\n";
      this.output += outputString;
      return outputString;
   }
   
   private void dumpOutput(int maxBuffered)throws IOException{
      if (this.output.length() > maxBuffered) {
         this.outputFile.write(this.output);
         this.output = "";
      }
   }
}
