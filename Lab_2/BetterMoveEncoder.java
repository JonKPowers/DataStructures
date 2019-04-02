import java.lang.Math;
import java.io.FileWriter;
import java.io.IOException;

class BetterMoveEncoder
{

   // Tower encoding table:
   // startTower     endTower    moveCode
   // A(0)           B(1)        1
   // B(1)           A(0)        -1
   // A(0)           C(2)        2
   // C(3)           A(0)        -2
   // B(1)           C(2)        3
   // C(2)           B(1)        -3

   private int[][] moveList;
   private int[][] discList;
   private int mainArrayIndex;
   private int subArrayIndex;
   private int getMovesMainArrayIndex = 0;
   private int getMovesSubArrayIndex = 0;
   
   final int MAX_ARRAY_SIZE = 100000000;

   BetterMoveEncoder(int numDiscs){
      long totalMoves = MathHelpers.exponentiate(2, numDiscs) - 1;
      int totalArrays = (int) (totalMoves / MAX_ARRAY_SIZE) + 1;
      
      moveList = new int[totalArrays][];
      discList = new int[totalArrays][];
      
      setUpStorageArrays(totalMoves);
      
      mainArrayIndex = 0;
      subArrayIndex = 0;   
   }
   
   private void setUpStorageArrays(long numMoves){
      mainArrayIndex = 0;
      while(numMoves >= MAX_ARRAY_SIZE){
         this.moveList[mainArrayIndex] = new int[MAX_ARRAY_SIZE];
         this.discList[mainArrayIndex] = new int[MAX_ARRAY_SIZE];
         numMoves -= MAX_ARRAY_SIZE;
         mainArrayIndex++;

      }
      this.moveList[mainArrayIndex] = new int[(int) numMoves];
      this.discList[mainArrayIndex] = new int[(int) numMoves];
      
      mainArrayIndex = 0;
   }
   
   public void addMove(int discNum, int startTower, int endTower){
      int moveCode = getMoveCode(startTower, endTower);
      
      this.moveList[mainArrayIndex][subArrayIndex] = moveCode;
      this.discList[mainArrayIndex][subArrayIndex] = discNum;
      
      // Increment the subArrayIndex; adjust if the subarray is full
      if(subArrayIndex == (this.MAX_ARRAY_SIZE - 1)){
         subArrayIndex = 0;
         mainArrayIndex++;
      } else {
         subArrayIndex++;
      }
   }
   
   public void addMove(int discNum, String startTower, String endTower){
      addMove(discNum, towerToInt(startTower), towerToInt(endTower));
   }
   
   public void addMoves(BetterMoveEncoder sourceMoves){
      int[] encodedMove;
      int[] towers;
      
      encodedMove = sourceMoves.getMove();
      
      while(encodedMove.length != 0){
         towers = getTowers(encodedMove[1]);
         addMove(encodedMove[0], towers[0], towers[1]);
         encodedMove = sourceMoves.getMove();
      }
   }
   
   public int[] getMove(){
      int[] moves = new int[2];
      
      // If we've gone past the last entry, return empty array; reset counters
      if(this.getMovesMainArrayIndex == mainArrayIndex && this.getMovesSubArrayIndex == this.subArrayIndex){
         resetGetMoves();
         return new int[0];
      // Otherwise, return a move and increment the counters
      } else {
         moves[0] = this.discList[this.getMovesMainArrayIndex][this.getMovesSubArrayIndex];
         moves[1] = this.moveList[this.getMovesMainArrayIndex][this.getMovesSubArrayIndex];
         
         incrementGetMoves();
         return moves;
      }
   }
   
   public void resetGetMoves(){
      this.getMovesMainArrayIndex = 0;
      this.getMovesSubArrayIndex = 0;
   }
   
   private void incrementGetMoves(){
      if(this.getMovesSubArrayIndex == (this.MAX_ARRAY_SIZE - 1)){
         this.getMovesSubArrayIndex = 0;
         this.getMovesMainArrayIndex++;
      } else {
         this.getMovesSubArrayIndex++;
      }
   }
   
   public void saveMovesToFile(FileWriter outputFile) throws IOException {
      int mainArraySize = this.mainArrayIndex + 1;
      int lastSubArraySize = this.subArrayIndex;
      
      // Write out all but the last subarray
      for(int i=0; i<(mainArraySize - 1); i++){
         for(int j=0; j<this.MAX_ARRAY_SIZE; j++){
            outputFile.write(decodeToString(discList[i][j], moveList[i][j]));
         }
      }
      
      // Write out the last subarray
      for(int i=0; i<lastSubArraySize; i++){
         outputFile.write(decodeToString(discList[mainArraySize - 1][i], moveList[mainArraySize - 1][i]));
      }
   }

   private int getMoveCode(int startTower, int endTower){
      if(startTower < 0 || endTower < 0){
         throw new IllegalArgumentException("Invalid start/end tower passed to moveEncoder.encode().");
      }
   
      if(startTower == 0 && endTower == 1 || startTower == 1 && endTower == 0){
         return startTower < endTower ? 3 : -3;
      } else if (startTower == 0 && endTower == 2 || startTower == 2 && endTower == 0){
         return startTower < endTower ? 5 : -5;
      } else if (startTower == 1 && endTower == 2 || startTower == 2 && endTower == 1){
         return startTower < endTower ? 7 : -7;
      } else {
         throw new RuntimeException("Bad start/end tower combination passed to moveEncoder.encode()");
      }
   }
         
   private String decodeToString(int discCode, int moveCode){
      String decodedString = "";
      
      int[] towers = getTowers(moveCode);
      int startTower = towers[0];
      int endTower = towers[1];
      
      decodedString = "Move disc " + discCode + " from ";
      decodedString += towerToString(startTower) + " to " + towerToString(endTower) + "\n";
      
      return decodedString;
   }

   
   private int[] getTowers(int towersCode){
      int[] towers = new int[2];
      
      switch(Math.abs(towersCode)){
         case 3:
            towers[0] = towersCode > 0 ? 0 : 1;
            towers[1] = towersCode > 0 ? 1 : 0;
            break;
         case 5:
            towers[0] = towersCode > 0 ? 0 : 2;
            towers[1] = towersCode > 0 ? 2 : 0 ;
            break;
         case 7:
            towers[0] = towersCode > 0 ? 1 : 2;
            towers[1] = towersCode > 0 ? 2 : 1;
            break;
      }
      
      return towers;
   }
   
   private String towerToString(int towerCode){
      if(towerCode == 0){
         return "Tower A";
      } else if (towerCode == 1){
         return "Tower B";
      } else if (towerCode == 2){
         return "Tower C";
      } else {
         throw new IllegalArgumentException("Bad argument passed to moveEncoder.towerToString().");
      }
   }
   
   private int towerToInt(String towerCode){
      if(towerCode.equals("A")){
         return 0;
      } else if(towerCode.equals("B")){
         return 1;
      } else if(towerCode.equals("C")){
         return 2;
      } else{
         throw new IllegalArgumentException("Bad argument passed to moveEncoder.towerToInt().");
      }
   }
}