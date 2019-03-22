class IterativeTowers
{
   private int towerSize;
   private IntStack towerA = new IntStack();
   private IntStack towerB = new IntStack();
   private IntStack towerC = new IntStack();
   private IntStack[] towers = {towerA, towerB, towerC};

   private int smallDiscLoc;
   private int discSizeFlag; // Tracks which disc to move, big or small

   private int moveDirection;
   private long numMoves;

   /******************************
   *******************************
   ***   Constructors
   ******************************
   ******************************/

   IterativeTowers(int towerSize){
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
         throw new IllegalArgumentException("The size of the IterativeTowers must be less than " + (MAX_VALUE - 1) +".");
      }
      
      // Initialize Tower A
      this.towerSize = towerSize;
         for(int i = towerSize; i > 0; i--){
            towerA.push(i);
         }

      // Set up start variables
      this.smallDiscLoc = 0;
      this.discSizeFlag = -1;    
      this.moveDirection = this.getMoveDirection(towerSize);
      this.numMoves = this.getNumMoves(towerSize);
   }
    
/******************************
*******************************
***   Public Methods
******************************
******************************/    

   public void solve(){
      /**
      ** solverTower() generates a solution to the Towers of
      ** Hanoi problem with the discs being moved to the traditional
      ** target tower, Tower C (the third tower, on the opposite side 
      ** of the peg board from the starting tower). Discs are 
      ** always initially stacked on Tower A.
      **/

      solve("C");
   }

   public void solve(String targetTower){
      int nextMove;
      while(this.numMoves > 0){
         if(this.discSizeFlag == -1){
            //Move the small disc
            nextMove = findLegalMove(this.smallDiscLoc);
            // Print/add some output. Consider whether we should write to file as we go.
            // The solution is probably too big to fit in memory for large towerSizes

            // Move the disc in the stack
            this.towers[nextMove].push( this.towers[smallDiscLoc].pop() );

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

            // Move the disc
            // PRINT/ADD OUTPUT
            this.towers[nextMove].push(this.towers[startingTower].pop());
         }

      // Housekeeping
      this.flipDiscSizeFlag();
      this.numMoves--;

      }

   // DO SOME OUTPUT.
   }

   /******************************
   *******************************
   ***   Private Methods
   ******************************
   ******************************/

   private int findLegalMove(int startingTower){
      /**
      ** findLegalMove() determines the next permissible move for the disc
      ** on top of the startingTower. 
      **
      **
      **/    
      int nextMove = (startingTower + this.moveDirection + 3) % 3; // MAYBE DONT NEED TO ADD THREE!
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
}
