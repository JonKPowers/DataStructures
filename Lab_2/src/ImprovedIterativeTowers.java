import java.io.FileWriter;
import java.io.IOException;

class ImprovedIterativeTowers
{
   /**
   ** ImproverIterativeTowers provides an iterative solution to the Towers
   ** of Hanoi problem. The optimum solutions to the TOH problem, iteratively
   ** viewed, always follows a simple pattern involving a cycling around each
   ** pair of towers. It starts with an exchange between the first and second towers,
   ** then between the first and last towers, and finally between the second and last
   ** towers. The exact order of the cycle varies with the number of discs being
   ** solved for and which tower you want the stack to ultimately be moved to.
   ** But once determined, the order of the cycle never changes and always
   ** results in an optimum solution.
   **
   **/

   private int towerSize;
   
   // Variables to represent the three disc stacks
   private IntStack towerA = new IntStack();
   private IntStack towerB = new IntStack();
   private IntStack towerC = new IntStack();
   private IntStack[] towers = {towerA, towerB, towerC};
   private String[] towerStrings = {"A", "B", "C"};
   
   private FileWriter outputFile;
   private long numMoves;     // # of moves required to solve the towers
   private long currentMove;  // Move we are currently on


   /******************************
   *******************************
   ***   Constructor
   ******************************
   ******************************/

   ImprovedIterativeTowers(int towerSize, String targetTower, FileWriter outputFile){
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
      
      // Initialize Tower A--Seed with the discs
      this.towerSize = towerSize;
         for(int i = towerSize; i > 0; i--){
            towerA.push(i);
         }

      // Set up move-tracking variables
      this.numMoves = this.getNumMoves(towerSize);
      this.currentMove = 1;
      
      // Adjust tower order for number of discs.
      // If number of discs is even, then Towers B and C effectively switch places
      // in order to have the discs end up on the desired tower. this.towerStrings
      // is used to convert between index positions and tower identifiers, so
      // we can simply swap the identifiers in this.towerStrings to establish the
      // correct ordering of the towers.
      if(towerSize % 2 == 0) {
         flipLastTwoTowers();
      }
      
      // The default behavior of this TOH solution is to move the discs to
      // Tower C. We can cause the discs to end up on Tower B by switching
      // the direction of movement that was determine when the IterativeTower
      // instance was initialized. Any argument passed to solve() other than
      // "B" will be ignored without any warnings being generated.
      if(targetTower.equals("B")){
         flipLastTwoTowers();         
      }
      
      // Set up output FileWriter
      this.outputFile = outputFile;
   }
    
/******************************
*******************************
***   Public Methods
******************************
******************************/    

   public void solve() throws IOException {
      /**
      ** solverTower() generates a solution to the Towers of
      ** Hanoi problem. Discs begin on Tower A and are moved to the targetTower. The
      ** problem is solved by cycling through the pairs of towers and making whatever
      ** legal move is available between them.
      **
      ** @return None Nothing is returned
      **/
      

      
      // Continue making moves until final number is reached, alternating between
      // the smallest and second smallest discs.
      
      while(this.currentMove <= this.numMoves){
         int[] moves = getMovePair();
         makeMove(moves[0], moves[1]);
         this.currentMove++;
      }
   }

   
   public long getNumMoves(){
      /**
      ** getNumMoves() does what it says.
      **
      ** @return long The number of moves required to solve the TOH problem.
      **/
      return this.numMoves;
   }

   /******************************
   *******************************
   ***   Private Methods
   ******************************
   ******************************/

   private void makeMove(int startingTower, int targetTower) throws IOException {
      /**
      ** makeMove() moves the disc on top of the startingTower to the targetTower.
      ** This is needed because findHighestTower needs to look at the disc
      ** on the top of each tower to determine what moves are legal.
      **
      ** @param startingTower The tower containing the disc to be moved.
      ** @param targetTower The tower that the disc should be moved to.
      **
      ** @return None Nothing is returned.
      **/      
      int discNum = this.towers[startingTower].peek();
      this.towers[targetTower].push(this.towers[startingTower].pop());
      this.outputFile.write("Move disc " + discNum + " from Tower " + getTowerString(startingTower) + " to Tower " + getTowerString(targetTower) + "\n");
   }
   
   private int[] getMovePair(){
      /**
      ** getMovePair() determines what two towers should have a disc moved between them.
      ** Since the pairs of towers involved in a move cycle in a three-based pattern,
      ** we can work out what the correct pair is using a modulus operation and how many
      ** moves have been made so far. Some error guarding is in place to handle very large numbers 
      ** that can occur with larger TOH problem sizes.
      **
      ** @return int[] An array with the first element being the starting tower and the second being the destination tower.
      **
      **/
      int[] moves = new int[2];
      int highestValue;
      long tempCurrentMove = this.currentMove;
      
      // Guard against loss when casting to int to get the modulus
      while(tempCurrentMove > Integer.MAX_VALUE){
         tempCurrentMove -= 2000000001;
      }
      
      switch((int) tempCurrentMove % 3){
         case 0:
            highestValue = findHighestTower(1, 2);
            moves[0] = highestValue == 1 ? 2 : 1;
            moves[1] = highestValue;
            break;
         case 1:
            highestValue = findHighestTower(0, 2);
            moves[0] = highestValue == 0 ? 2 : 0;
            moves[1] = highestValue;
            break;
         case 2:
            highestValue = findHighestTower(0, 1);
            moves[0] = highestValue == 0 ? 1 : 0;
            moves[1] = highestValue;
            break;
         default:
            throw new RuntimeException();
      }
      return moves;
   }
   
   private int findHighestTower(int tower1, int tower2){
      /**
      ** findHighestTower() determines which of two towers has the highest-numbered disc on top.
      **
      ** @param tower1 The first tower to be examined
      ** @param tower2 The second tower to be examined.
      ** @return int The integer index of the tower with the highest-numbered disc on top.
      **
      **/
      int tower1Value = towers[tower1].isEmpty() ? Integer.MAX_VALUE : towers[tower1].peek();
      int tower2Value = towers[tower2].isEmpty() ? Integer.MAX_VALUE : towers[tower2].peek();
      
      return tower1Value > tower2Value ? tower1 : tower2;
   }
   
   private void flipLastTwoTowers(){
      /**
      **
      **
      **
      **/
      towers[1] = this.towerC;
      towers[2] = this.towerB;
      
      towerStrings[1] = "C";
      towerStrings[2] = "B";
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
   
   private String getTowerString(int towerNum){
      /**
      ** getTowerString() provides a translation between the tower's index in 
      ** this.towerStrings and it's human-friendly String format. 
      **
      ** @param towerNum The tower's index number
      ** @return String The String identifier of the tower
      **
      **/
      return this.towerStrings[towerNum];
   }
}
