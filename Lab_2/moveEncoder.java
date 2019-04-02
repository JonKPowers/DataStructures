import java.lang.Math;

class moveEncoder
{

   // The encoded move is an integer obtained by multiplying the
   // discNum by the tower encoding as specified below.
   //
   // Tower encoding table:
   // startTower     endTower    moveCode
   // A(0)           B(1)        3
   // B(1)           A(0)        -3
   // A(0)           C(2)        5
   // C(3)           A(0)        -5
   // B(1)           C(2)        7
   // C(2)           B(1)        -7

   public int encode(int discNum, int startTower, int endTower){
      if(discNum < 1){
         throw new IllegalArgumentException("Invalid disc number passed to moveEncoder.encode().");
      }
      if(startTower < 0 || endTower < 0){
         throw new IllegalArgumentException("Invalid start/end tower passed to moveEncoder.encode().");
      }
   
      int encodedMove;
   
      if(startTower == 0 && endTower == 1 || startTower == 1 && endTower == 0){
         encodedMove = startTower < endTower ? 3 : -3;
      } else if (startTower == 0 && endTower == 2 || startTower == 2 && endTower == 0){
         encodedMove = startTower < endTower ? 5 : -5;
      } else if (startTower == 1 && endTower == 2 || startTower == 2 && endTower == 1){
         encodedMove = startTower < endTower ? 7 : -7;
      } else {
         throw new RuntimeException("Bad start/end tower combination passed to moveEncoder.encode()");
      }
      
      return encodedMove * discNum;
   }
      
   public String decodeToString(int moveCode){
      String decodedString;
   
      int towersCode = getPrime(moveCode);
      int discNum = moveCode / towersCode;
      int[] towers = getTowers(towersCode);
      
      int startTower = towersCode > 0 ? towers[0] : towers[1];
      int endTower = towersCode > 0 ? towers[1] : towers[0];
      
      decodedString = "Move disc " + discNum + " from ";
      decodedString += towerToString(startTower) + " to " + towerToString(endTower) + ".\n";
      
      return decodedString;
   }
   
   private int getPrime(int moveCode){      
      if(Math.abs(moveCode) % 7 == 0){
         return moveCode / 7;
      } else if(Math.abs(moveCode) % 5 == 0){
         return moveCode / 5;
      } else if (Math.abs(moveCode) % 3 == 0) {
         return 3;
      } else {
         throw new IllegalArgumentException("moveCode is not divisible by 3, 5, or 7)");
      }
   }
   
   private int[] getTowers(int towersCode){
      int[] towers = new int[2];
      
      switch(Math.abs(towersCode)){
         case 3:
            towers[0] = 1;
            towers[1] = 2;
            break;
         case 5:
            towers[0] = 1;
            towers[1] = 3;
            break;
         case 7:
            towers[0] = 2;
            towers[1] = 3;
            break;
      }
      
      return towers;
   }
   
   private String towerToString(int towerCode){
      if(towerCode == 1){
         return "Tower A";
      } else if (towerCode == 2){
         return "Tower B";
      } else if (towerCode == 3){
         return "Tower C";
      } else {
         throw new IllegalArgumentException("Bad argument passed to moveEncoder.towerToString().");
      }
   }
}