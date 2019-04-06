import java.lang.Math;
import java.io.FileWriter;
import java.io.IOException;

class BetterMoveEncoder
{
   /**
   ** The BetterMoveEncoder class provides a mechanism to store move sequences
   ** and convert them to human-readable strings. It attempts to extend the range
   ** of moves that can be stored compared to full Strings. This is necessary
   ** because the large number of moves required to solve even relatively small
   ** Towers of Hanoi problems can be enormous. Using and concatenating Strings
   ** to track these move sequences is very expensive.
   **
   ** Moves are tracked using two parallel arrays, with each move being
   ** represented by the same index in each array. One array holds the
   ** disc invovled in the move, using its natural number ordering. The
   ** second array contains a move code. Each unique combination of
   ** towers is given a code, and the movement is from the "lower"
   ** tower to the "higher" tower if the code is positive and negative
   ** if the move is "downward":
   **
   ** Tower encoding table:
   ** startTower     endTower    moveCode
   ** A(0)           B(1)        3
   ** B(1)           A(0)        -3
   ** A(0)           C(2)        5
   ** C(3)           A(0)        -5
   ** B(1)           C(2)        7
   ** C(2)           B(1)        -7
   **
   ** For purposes of all public methods of this class, Tower A has the
   ** integer identifier 0, Tower B has the identifier 1, and Tower C
   ** has the identifier 2.
   **/

   private int[][] moveList;
   private int[][] discList;
   private int mainArrayIndex;
   private int subArrayIndex;
   private int getMovesMainArrayIndex = 0;
   private int getMovesSubArrayIndex = 0;
   
   final int MAX_ARRAY_SIZE = 100000000;

   /////////////////////////
   // Constructor
   /////////////////////////

   BetterMoveEncoder(int numDiscs){
      /**
      ** This constructor tries to make arrays of an appropriate size to
      ** hold the TOH solution for the numDiscs being solved for. Due to
      ** the enormous size of the TOH solutions, this approach results in
      ** Heap capacity issues when n=30 or higher. Further optimizations
      ** could extend the functionality of this, which is the major
      ** bottleneck and limiting feature of this move-storage structure.
      **/
      long totalMoves = MathHelpers.exponentiate(2, numDiscs) - 1;
      int totalArrays = (int) (totalMoves / MAX_ARRAY_SIZE) + 1;
      
      moveList = new int[totalArrays][];
      discList = new int[totalArrays][];
      
      setUpStorageArrays(totalMoves);
      
      mainArrayIndex = 0;
      subArrayIndex = 0;   
   }

   /////////////////////////
   // Public Methods
   /////////////////////////   
   
   public void addMove(int discNum, int startTower, int endTower){
      /**
      ** addMove() adds the movement of a disc from one tower to 
      ** another tower to the sequence of moves stored in the 
      ** BetterMoveEncoder. These can later be retrieved or written
      ** to a file by other methods in this class.
      **
      ** @param discNum The integer number of the disc being move
      ** @param startTower The integer identifier of the starting tower
      ** @param endTower The integer identifier of the ending tower
      ** @return None Nothing is returned
      **/
      int moveCode = getMoveCode(startTower, endTower);
      
      // Add the move to the two arrays      
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
      /**
      ** Provides the same functionality as addMove(int, int, int) but
      ** allows the startTower and endTower to be identified by a 
      ** String such as "A", "B", or "C" rather than by using the
      ** tower's integer identifier.
      **
      ** @param discNum The integer number of the disc being move
      ** @param startTower The String identifier of the starting tower
      ** @param endTower The String identifier of the ending tower
      ** @return nothing is returned     
      **/
      addMove(discNum, towerToInt(startTower), towerToInt(endTower));
   }
   
   public void addMoves(BetterMoveEncoder sourceMoves){
      /**
      ** addMoves() takes in another BetterMoveEncoder object and
      ** adds the move sequence contained in the BetterMoveEncoder
      ** object to the moves stored in this current object.
      **
      ** @param sourceMoves The BetterMoveEncoder object whose moves are to be added to the current object
      ** @return None Nothing is returned
      **
      **/
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
      /**
      ** getMove() is an iterator function that will continue to return individual
      ** moves, one at a time from the current BetterMoveEncoder 
      ** object until all moves have been returned. The end of the moves is signaled
      ** by an int[] array of length zero.
      **
      ** The int[] array contains two elements: the first is the disc involved in
      ** the move; the second is the move code associated with the move.
      **
      ** @return int[] An int[] array containing the disc number and move code for the next move in this BetterMoveEncoder object
      **
      **
      **/
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

   

   
   public void saveMovesToFile(FileWriter outputFile) throws IOException {
      /**
      ** saveMovesToFile() takes in a FileWriter object and writes the
      ** moves stored in the current BetterMoveEncoder object to the file
      ** in a human-friendly format. 
      **
      ** @param outputFile A FileWriter object associated with the output file to write to
      **
      **/
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

   /////////////////////////
   // Private Methods
   /////////////////////////
      
   private void setUpStorageArrays(long numMoves){
      /**
      ** setUpStorageArrays() is used to initalize the arrays used to store
      ** disc and moveCode information for each move contained in the
      ** BetterMoveEncoder object.
      **
      ** @param numMoves The number of moves that the BetterMoveEncoder will store
      ** @return None Nothing is returned.
      **/
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

   private void incrementGetMoves(){
      /**
      ** incrementGetMoves() is used to increment the counters used
      ** to track the top of the move arrays. Multiple arrays are used
      ** to attempt to handle the potentially enormous number of moves
      ** that might have to be stored, which requires some coordination
      ** of the couters for the 1st dimension of the array and the 
      ** 2nd dimension.
      **/
      if(this.getMovesSubArrayIndex == (this.MAX_ARRAY_SIZE - 1)){
         this.getMovesSubArrayIndex = 0;
         this.getMovesMainArrayIndex++;
      } else {
         this.getMovesSubArrayIndex++;
      }
   }

   
   private void resetGetMoves(){
      /**
      ** resetGetMoves() resets the move counter used by getMove(). This
      ** is automatically triggered when the getMove() iterator exhausts
      ** all the moves contained in the current BetterMoveEncoder object.
      **
      ** @return None Nothing is returned
      **/
      this.getMovesMainArrayIndex = 0;
      this.getMovesSubArrayIndex = 0;
   }

   private int getMoveCode(int startTower, int endTower){
      /**
      **
      **
      **
      **
      **
      **
      **/
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
      /**
      ** decodeToString() translates a disc number and a moveCode into
      ** a human-readable String.
      **
      ** @param discCode The integer identifier of the disc being moved
      ** @param moveCode The moveCode describing the move being made
      ** @return String The human-friendly String describing the move
      **
      **/
      String decodedString = "";
      
      int[] towers = getTowers(moveCode);
      int startTower = towers[0];
      int endTower = towers[1];
      
      decodedString = "Move disc " + discCode + " from ";
      decodedString += towerToString(startTower) + " to " + towerToString(endTower) + "\n";
      
      return decodedString;
   }

   
   private int[] getTowers(int towersCode){
      /**
      ** getTowers() decodes a move code into the towers represented
      ** in the move and the direction of the move. It returns an integer
      ** array, with the first element being the starting tower and the 
      ** second element being the destination tower. 
      **
      ** @param towersCode The move code representing the move
      ** @return int[] An array with the starting tower as the first element and the ending tower as the second
      **/
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
      /**
      ** towerToString() provides a translation of a tower's integer
      ** identifier into a human-friendly String representation.
      **
      ** @param towerCode The tower's integer identifier
      ** @return String A human-friendly String representation of the tower identifier
      **
      **/
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
      /**
      ** towerToInt() provides a translation of a tower's short-form String
      ** identifier into its integer representation.
      **
      ** @param towerCode The tower's short-form String identifier
      ** @return int The tower's integer identifier
      **
      **/
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