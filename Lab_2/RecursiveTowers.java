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
   private BetterMemoizer moveMemory;
   private BetterMoveEncoder[] moves;
   private int movesPosition;
   
   RecursiveTowers(int numDiscs, String targetTower, FileWriter outputFile, boolean optimize){
      this.numDiscs = numDiscs;
      this.moveMemory = new BetterMemoizer(numDiscs);
      
      this.startTower = "A";
      this.targetTower = targetTower;
      this.tempTower = targetTower.equals("C") ? "B" : "C";
      
      this.output = "";
      this.outputFile = outputFile;
      this.outputCounter = 0;
      
      if(optimize){
         this.optimize = optimize;
         this.moves = new BetterMoveEncoder[1000000000];
         this.movesPosition = 0;
      }
      
   }
   
   public void solve() throws IOException{
      moveDiscs(this.numDiscs, this.startTower, this.tempTower, this.targetTower);
      this.dumpOutput(0);
   }
   
   public BetterMoveEncoder solve(boolean opitmize) throws IOException{
      return moveDiscs(this.numDiscs, this.startTower, this.tempTower, this.targetTower, true);
   }
   
   public String getMoveList(){
      return this.output;
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
   }
   
   private BetterMoveEncoder moveDiscs(int numDiscs, String start, String temp, String end, boolean optimize) throws IOException {
      if (moveMemory.moveStored(numDiscs, start, end)) {
         BetterMoveEncoder tempMoves = moveMemory.getMoves(numDiscs, start, end);
         moves[movesPosition++] = tempMoves;
         return tempMoves;
      } else {
         BetterMoveEncoder encoder = new BetterMoveEncoder(numDiscs);
         if (numDiscs == 1){
            // Base case of 1 disc--just move it to its destination
            encoder.addMove(numDiscs, start, end);
         } else {
            // Otherwise, move other discs out of the way, move the disc
            // to its destination, then move the other discs to their
            // final desination.
   
            encoder.addMoves(moveDiscs(numDiscs - 1, start, end, temp, true));
            encoder.addMove(numDiscs, start, end);
            encoder.addMoves(moveDiscs(numDiscs - 1, temp, start, end, true));
            
            // Add the calculated moves to the Memoizer
            moveMemory.setMoves(numDiscs, start, end, encoder);
         }
         
         return encoder;
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
