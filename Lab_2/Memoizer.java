class Memoizer
{
   private String[] aToBMoves;
   private String[] bToAMoves;
   private String[] aToCMoves;
   private String[] cToAMoves;
   private String[] bToCMoves;
   private String[] cToBMoves;
   
   Memoizer(int maxSize){
      aToBMoves = new String[maxSize+1];
      bToAMoves = new String[maxSize+1];
      aToCMoves = new String[maxSize+1];
      cToAMoves = new String[maxSize+1];
      bToCMoves = new String[maxSize+1];
      cToBMoves = new String[maxSize+1];
   }
   
   public String getMoves(int discNum, String sourceTower, String endTower){
      String[] movesArray = getMovesArray(sourceTower, endTower);
      String moves = movesArray[discNum];
      
      if (moves == null){
         throw new RuntimeException("You need to run Memoizer.moveStored() before calling getMoves()!");
      } else {
         return moves;
      }
           
   }
   
   public boolean moveStored(int discNum, String startTower, String endTower) {
      String[] movesArray = getMovesArray(startTower, endTower);
      return movesArray[discNum] == null ? false : true;
   }
   
   public void setMoves(int discNum, String sourceTower, String endTower, String movesString){
      String[] movesArray = getMovesArray(sourceTower, endTower);
      movesArray[discNum] = movesString;
   }
   
   private String[] getMovesArray(String sourceTower, String endTower){
      // TO DO--Validate the passed arguments
      switch(sourceTower){
         case "A":
            return endTower.equals("B") ? aToBMoves : aToCMoves;
         case "B":
            return endTower.equals("A") ? bToAMoves : bToCMoves;
         case "C":
            return endTower.equals("A") ? cToAMoves : cToBMoves;
         default:
            return new String[0];
            // NEED ERROR HANDLING            
      }
   }

}