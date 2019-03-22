class IterativeTowers
{
   private int towerSize;
   
   // Variables to represent the three disc stacks
   private IntStack towerA = new IntStack();
   private IntStack towerB = new IntStack();
   private IntStack towerC = new IntStack();
   private IntStack[] towers = {towerA, towerB, towerC};

   // State tracking variables
   private int smallDiscLoc;  // Location of the small disc
   private int discSizeFlag;  // Tracks which disc to move, big or small

   private int moveDirection; // Direction of movements
   private long numMoves;     // # of moves left to solve the towers
   
   private String moveList = "";

   /******************************
   *******************************
   ***   Constructors
   ******************************
   ******************************/

   IterativeTowers(int towerSize, String targetTower){
      /**
      ** Constructor for IterativeTower. It takes in an integer,
      ** which sets the number of discs to be used in the problem. 
      ** These are pushed onto a stack representing Tower A, 
      ** which sets up the problem's initial state. The maximum tower
      ** size is Integer.MAX_VALUE - 1, though as a practical matter
      ** the tower size must be much, much smaller than this to be solved
      ** in a reasonable amount of time.
      **
      ** @param towerSize An integer representing size of the tower, less than Integer.MAX_VALUE - 1.
      **/
           
      //Error checking
      if(towerSize >= Integer.MAX_VALUE) {
         throw new IllegalArgumentException("The size of the IterativeTowers must be less than " + (Integer.MAX_VALUE - 1) +".");
      }
      
      // Initialize Tower A
      this.towerSize = towerSize;
         for(int i = towerSize; i > 0; i--){
            towerA.push(i);
         }

      // Set up state variables
      this.smallDiscLoc = 0;
      this.discSizeFlag = -1;    
      this.moveDirection = this.getMoveDirection(towerSize);
      this.numMoves = this.getNumMoves(towerSize);
      
      // The default behavior of this TOH solution is to move the discs to
      // Tower C. We can cause the discs to end up on Tower B by switching
      // the direction of movement that was determine when the IterativeTower
      // instance was initialized. Any argument passed to solve() other than
      // "B" will be ignored without any warnings being generated.
      if(targetTower.equals("B")){
         flipMoveDirection();
      }
   }
    
/******************************
*******************************
***   Public Methods
******************************
******************************/    

   public void solve(){
      /**
      ** solverTower() generates a solution to the Towers of
      ** Hanoi problem. Discs begin on Tower A and are moved to the targetTower.
      ** The problem is solved by making moves in the this.moveDirection, with 
      ** moves alternating between the smallest disc and the second smallest
      ** disc. They are moved to the first available legal position in the
      ** this.moveDirection (the pegs are treated as circular).
      **
      ** @param targetTower The tower that the discs should be moved to, i.e., "B" or "C".
      **/
      

      
      // Continue making moves until final number is reached, alternating between
      // the smallest and second smallest discs.
      int nextMove;
      while(this.numMoves > 0){
         
         // Move smallest disc
         if(this.discSizeFlag == -1){
         
            nextMove = findLegalMove(this.smallDiscLoc);
            makeMove(this.smallDiscLoc, nextMove);

            // Housekeeping
            this.smallDiscLoc = nextMove;

         } else {
            // Move the second smallest disc
            int startingTower = findSecondSmallestDisc();

            // There will only be one legal move for the second smallest disc, and
            // we can calculate that directly. (It can't be placed on the small
            // disc that we just moved, and it can't go onto the tower that it
            // came from, so it must go on the only remaining tower.)
            nextMove = 3 - startingTower - this.smallDiscLoc;
            makeMove(startingTower, nextMove);
         }

      // Housekeeping
      this.flipDiscSizeFlag();
      this.numMoves--;

      }
   }
   
   public String getMoveList(){
      /**
      ** getMoveList() provides a String containing the moves required to
      ** solve the TOH problem. this.solve() MUST be called before calling
      ** getMoveList().
      **
      ** @return String A string containing the moves required to solve the TOH problem.
      **/
      
      return this.moveList;
   }

   /******************************
   *******************************
   ***   Private Methods
   ******************************
   ******************************/

   private void makeMove(int startingTower, int targetTower){
      /**
      ** makeMove() moves the disc on top of the startingTower to the targetTower.
      ** It updates the appropriate tower IntStacks and [_____________________].
      **
      ** @param startingTower The tower containing the disc to be moved.
      ** @param targetTower The tower that the disc should be moved to.
      **
      ** @return None Nothing is returned.
      **/      
      int discNum = this.towers[startingTower].peek();
      this.towers[targetTower].push(this.towers[startingTower].pop());
      this.moveList += "Move disc " + discNum + " from Tower " + getTowerString(startingTower) + " to Tower " + getTowerString(targetTower) + "\n";
   }

   private int findLegalMove(int startingTower){
      /**
      ** findLegalMove() determines the next permissible move for the disc
      ** on top of the startingTower. 
      **
      **
      **/    
      int nextMove = (startingTower + this.moveDirection + 3) % 3;
      if(towers[nextMove].isEmpty() || towers[startingTower].peek() < towers[nextMove].peek()){
         return nextMove;
      } else {
         nextMove = (nextMove + this.moveDirection + 3) % 3;
         if(!towers[nextMove].isEmpty() && towers[startingTower].peek() > towers[nextMove].peek()){throw new RuntimeException();}
         // MAYBE DONT NEED THIS ERROR CHECKING--POSSIBLY PULL OUT ONCE WE FEEL GOOD THAT IT WORKS
         // IN NO CASE SHOULD IT GO BACK TO THE STARTING STACK
         
         return nextMove;
      }
   }

   private int findSecondSmallestDisc(){
      /**
      ** findSecondSmallestDisc() determines which tower holds the second
      ** smallest disc.
      **
      ** @return int The index (0, 1, or 2) of the tower containing the second smallest disc.
      **/
   
      // Gather values of the discs on each tower. Empty towers are assigned 
      // Integer.MAX_VALUE so that this method will not return is as being
      // smaller than an actual disc (which may have a value of no more 
      // than Integer.MAX_VALUE - 1.
      int[] towerTops = new int[3];
      for(int i=0; i < towerTops.length; i++){
         if(i == this.smallDiscLoc){ towerTops[i] = Integer.MAX_VALUE; }
         else if (towers[i].isEmpty()) { towerTops[i] = Integer.MAX_VALUE; }
         else { towerTops[i] = towers[i].peek(); }
      }
    
      if(this.smallDiscLoc == 0) {
         return towerTops[1] < towerTops[2] ? 1 : 2; 
      } else if(this.smallDiscLoc == 1) {
         return towerTops[0] < towerTops[2] ? 0 : 2;
      } else if (this.smallDiscLoc == 2) {
         return towerTops[0] < towerTops[1] ? 0 : 1;
      } else {
         throw new RuntimeException("Something very bad happened to IterativeTowers.smallDiscLoc--it's gone!");
      }
   }

   private void flipDiscSizeFlag(){
      /**
      ** flipDiscSizeFlag() alternates this.discSizeFlag() between the values
      ** 1 and -1. If it is set to 1 at the time flipDiscSizeFlag() is called,
      ** then, its value is changed to -1, and vice versa.
      **
      ** @return None Nothing is returned.
      **/
   
       this.discSizeFlag *= -1;
   }

   private void flipMoveDirection(){
      /**
      ** flipDiscSizeFlag() reverses the direction of movement that is used
      ** to solve the TOH problem. Movement towards the right is indicated by 
      ** setting this.moveDirection to 1, and movements to the left is indicated
      ** by -1. To reverse the direction, we simply this.moveDirection *= -1.
      **
      ** @return None Nothing is returned.
      **/
   
       this.moveDirection *= -1;
   }

   private long getNumMoves(int towerSize){
      /**
      ** getNumMoves() calculates the number of moves required to solve the TOH
      ** problem given the towerSize. Using an optimum strategy, this is 2^n - 1.
      ** MathHelpers.exponentiate() is an exponentiation function that is able
      ** to handle large integer values with a level of precision not offered
      ** by java.lang.Math.pow(), which utilizes floating point numbers. This is
      ** needed due to the potentially large number of moves required to solve
      ** the Towers of Hanoi problem.
      **
      ** @param towerSize The number of discs to be solved for
      **
      ** @return int The minimum number of moves required to solve TOH for the given towerSize
      **/
      
      return MathHelpers.exponentiate(2, towerSize) - 1;
   }

   private int getMoveDirection(int towerSize){
      /**
      ** getMoveDirection() determines whether moves should be 
      ** made in the left or right direction while solving the TOH
      ** problem. If there are an even number of discs, then 
      ** moves will be made towards the right, and if there are an
      ** odd number then moves will be made to the left. Rightward 
      ** moves are indicated by a return value of 1, and leftward 
      ** moves are indicated by -1. 
      ** [______ADJUSTMENTS FOR DIFFERENT TOWER]
      **
      ** @param towerSize The number of discs to be solved for. 
      ** @param targetTower [The integer identifer for the tower that discs are being moved to.]
      **
      ** @return int The integer 1 if moves are to be made to the right; -1 if to the left.
      **
      **/

      return towerSize % 2 == 0 ? 1 : -1;
   }
   
   private String getTowerString(int towerNum){
      String towerString;
      switch(towerNum){
         case 0:
            towerString = "A";
            break;
         case 1:
            towerString = "B";
            break;
         case 2:
            towerString = "C";
            break;
         default:
            towerString = "";
      }
   return towerString;
   }
}
