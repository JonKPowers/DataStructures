class BetterMemoizer
{
   /**
   ** BetterMemoizer provides a memoization implementation for use in
   ** connection with a recursive method of solving the Towers of 
   ** Hanoi problem. It stores previously calculated move sequences
   ** of a specified number of discs between different sets of towers.
   ** Each uniqe tower pair and direction of movement is stored in an
   ** array, which is indexed by the number of discs to be moved. For
   ** example, the sequence of steps to move 4 discs from Tower A to
   ** Tower C will be in index position 4 of aToCMoves.
   **
   ** @author Jon Powers
   ** @version 0.1
   **
   **/
   
   private BetterMoveEncoder[] aToBMoves;
   private BetterMoveEncoder[] bToAMoves;
   private BetterMoveEncoder[] aToCMoves;
   private BetterMoveEncoder[] cToAMoves;
   private BetterMoveEncoder[] bToCMoves;
   private BetterMoveEncoder[] cToBMoves;
   
   ///////////////////////
   // Constructor
   ///////////////////////
   
   BetterMemoizer(int maxSize){
      /** Constructor for a BetterMemoizer object. Takes in the
      ** number of discs being solved for as a parameter, and
      ** initializes BetterMoveEncoder[] arrays for each unique
      ** tower/direction combination.
      **
      ** @param maxSize The number of discs being solved for in the TOH problem.
      **/
      aToBMoves = new BetterMoveEncoder[maxSize+1];
      bToAMoves = new BetterMoveEncoder[maxSize+1];
      aToCMoves = new BetterMoveEncoder[maxSize+1];
      cToAMoves = new BetterMoveEncoder[maxSize+1];
      bToCMoves = new BetterMoveEncoder[maxSize+1];
      cToBMoves = new BetterMoveEncoder[maxSize+1];
   }

   ///////////////////////
   // Public Methods
   ///////////////////////
      
   public BetterMoveEncoder getMoves(int discNum, String sourceTower, String endTower){
      /**
      ** getMoves() returns the BetterMoveEncoder object containing the move sequence
      ** to solve for moving discNum of discs from sourceTower to endTower if it has
      ** been previously stored in the memoizer. Note that a RuntimeException will be thrown
      ** if attempting to retrieve a move sequence that has not previously been stored.
      ** Use moveStored() to determine whether the desired sequence is in the memoizer.
      **
      ** @param discNum The number of discs being moved
      ** @param sourceTower The tower the discs start on, e.g., "A", "B", or "C"
      ** @param endTower The tower the discs end of, e.g., "A", "B", or "C"
      ** @return BetterMoveEncoder The BetterMoveEncoder object containing the sequence of moves solution
      **
      **/
      BetterMoveEncoder[] movesArray = getMovesArray(sourceTower, endTower);
      BetterMoveEncoder moves = movesArray[discNum];
      
      if (moves == null){
         throw new RuntimeException("You need to run BetterMemoizer.moveStored() before calling getMoves()!");
      } else {
         return moves;
      }
           
   }
   
   public boolean moveStored(int discNum, String startTower, String endTower) {
      /**
      ** moveStored() determines whether the solution move sequence has
      ** been stored in the memoizer for the specified number of discs, discNum,
      ** from startTower to endTower. 
      **
      ** @param discNum The number of discs being moved
      ** @param sourceTower The tower the discs start on, e.g., "A", "B", or "C"
      ** @param endTower The tower the discs end of, e.g., "A", "B", or "C"
      ** @return boolean True if the move is stored in the memoizer; otherwise false      
      **/
      BetterMoveEncoder[] movesArray = getMovesArray(startTower, endTower);
      return movesArray[discNum] == null ? false : true;
   }
   
   public void setMoves(int discNum, String sourceTower, String endTower, BetterMoveEncoder moves){
      /**
      ** setMoves() stores the solution move sequence in the memoizer for the 
      ** specified number of discs, discNum, from startTower to endTower. 
      **
      ** @param discNum The number of discs being moved
      ** @param sourceTower The tower the discs start on, e.g., "A", "B", or "C"
      ** @param endTower The tower the discs end of, e.g., "A", "B", or "C"
      ** @param moves The BetterMoveEncoder object containing the solution move sequence
      ** @return None Nothing is returned  
      **/      
      BetterMoveEncoder[] movesArray = getMovesArray(sourceTower, endTower);
      movesArray[discNum] = moves;
   }

   ///////////////////////
   // Private Methods
   ///////////////////////
   
   private BetterMoveEncoder[] getMovesArray(String sourceTower, String endTower){
      /**
      ** getMovesArray() identifies and returns the array of BetterMoveEncoder objects
      ** containing moves from sourceTower to endTower, which can then be used
      ** to retrieve or store move sequences.
      **
      ** @param sourceTower The tower the discs start on, e.g., "A", "B", or "C"
      ** @param endTower The tower the discs end of, e.g., "A", "B", or "C"
      ** @return BetterMoveEncoder[] The array containing move sequences for the selected tower/direction combination
      **/
      switch(sourceTower){
         case "A":
            return endTower.equals("B") ? aToBMoves : aToCMoves;
         case "B":
            return endTower.equals("A") ? bToAMoves : bToCMoves;
         case "C":
            return endTower.equals("A") ? cToAMoves : cToBMoves;
         default:
            return new BetterMoveEncoder[0];     
      }
   }

}