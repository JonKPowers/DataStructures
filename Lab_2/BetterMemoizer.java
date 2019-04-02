class BetterMemoizer
{
   private BetterMoveEncoder[] aToBMoves;
   private BetterMoveEncoder[] bToAMoves;
   private BetterMoveEncoder[] aToCMoves;
   private BetterMoveEncoder[] cToAMoves;
   private BetterMoveEncoder[] bToCMoves;
   private BetterMoveEncoder[] cToBMoves;
   
   BetterMemoizer(int maxSize){
      aToBMoves = new BetterMoveEncoder[maxSize+1];
      bToAMoves = new BetterMoveEncoder[maxSize+1];
      aToCMoves = new BetterMoveEncoder[maxSize+1];
      cToAMoves = new BetterMoveEncoder[maxSize+1];
      bToCMoves = new BetterMoveEncoder[maxSize+1];
      cToBMoves = new BetterMoveEncoder[maxSize+1];
   }
   
   public BetterMoveEncoder getMoves(int discNum, String sourceTower, String endTower){
      BetterMoveEncoder[] movesArray = getMovesArray(sourceTower, endTower);
      BetterMoveEncoder moves = movesArray[discNum];
      
      if (moves == null){
         throw new RuntimeException("You need to run BetterMemoizer.moveStored() before calling getMoves()!");
      } else {
         return moves;
      }
           
   }
   
   public boolean moveStored(int discNum, String startTower, String endTower) {
      BetterMoveEncoder[] movesArray = getMovesArray(startTower, endTower);
      return movesArray[discNum] == null ? false : true;
   }
   
   public void setMoves(int discNum, String sourceTower, String endTower, BetterMoveEncoder moves){
      BetterMoveEncoder[] movesArray = getMovesArray(sourceTower, endTower);
      movesArray[discNum] = moves;
   }
   
   private BetterMoveEncoder[] getMovesArray(String sourceTower, String endTower){
      // TO DO--Validate the passed arguments
      switch(sourceTower){
         case "A":
            return endTower.equals("B") ? aToBMoves : aToCMoves;
         case "B":
            return endTower.equals("A") ? bToAMoves : bToCMoves;
         case "C":
            return endTower.equals("A") ? cToAMoves : cToBMoves;
         default:
            return new BetterMoveEncoder[0];
            // NEED ERROR HANDLING            
      }
   }

}