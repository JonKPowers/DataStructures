class Memoizer
{
   private String[] aToBMoves;
   private String[] bToAMoves;
   private String[] aToCMoves;
   private String[] cToAMoves;
   private String[] bToCMoves;
   private String[] cToBMoves;
   
   Memoizer(int maxSize){
      aToBMoves = new String[maxSize];
      bToAMoves = new String[maxSize];
      aToCMoves = new String[maxSize];
      cToAMoves = new String[maxSize];
      bToCMoves = new String[maxSize];
      cToBMoves = new String[maxSize];
   }
   
   public String getMoves(int discNum, String sourceTower, String endTower){
      String[] movesArray = getMovesArray(sourceTower, endTower);
      return movesArray[discNum] == null ? "" : movesArray[discNum];
      
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