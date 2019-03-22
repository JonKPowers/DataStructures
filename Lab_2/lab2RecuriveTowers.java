class RecursiveTowers{
   
   private int numDiscs;
   private String startTower, targetTower, tempTower;
   private String output;
   
   RecursiveTowers(int numDiscs, String targetTower){
      this.numDiscs = numDiscs;
      this.startTower = "A";
      this.targetTower = targetTower;
      this.tempTower = targetTower.equals("C") ? "B" : "C";
   }
   
   public void solve(){
      this.output = moveDiscs(this.numDiscs, this.startTower, this.tempTower, this.targetTower);
   }
   
   public String getMoveList(){
      return this.output;
   }

   public static String moveDiscs(int numDiscs, String start, String temp, String end){
      String output = "";
     
      if (numDiscs == 1){
      // Base case of 1 disc--just move it to its destination
      output += makeMove(numDiscs, start, end);
      } else {
         // Otherwise, move other discs out of the way, move the disc
         // to its destination, then move the other discs to their
         // final desination.

         output += moveDiscs(numDiscs - 1, start, end, temp);
         output += makeMove(numDiscs, start, end);
         output += moveDiscs(numDiscs - 1, temp, start, end);
      }
      return output;
   }

   public static String makeMove(int numDiscs, String start, String end){
      String outputString = "Move disc " + numDiscs + " from Tower " + start + " to Tower " + end + "\n";
      return outputString;
   }
}
