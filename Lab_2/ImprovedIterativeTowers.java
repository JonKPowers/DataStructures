import java.io.FileWriter;
import java.io.IOException;

class ImprovedIterativeTowers
{
   private int towerSize;
   
   // Variables to represent the three disc stacks
   private IntStack towerA = new IntStack();
   private IntStack towerB = new IntStack();
   private IntStack towerC = new IntStack();
   private IntStack[] towers = {towerA, towerB, towerC};
   private String[] towerStrings = {"A", "B", "C"};
   
   private String output;
   private FileWriter outputFile;
   private int outputCounter;

   // State tracking variables
   private int smallDiscLoc;  // Location of the small disc
   private int discSizeFlag;  // Tracks which disc to move, big or small

   private int moveDirection; // Direction of movements
   private long numMoves;     // # of moves required to solve the towers
   private long currentMove;  // Move we are currently on
   
   private String moveList = "";

   /******************************
   *******************************
   ***   Constructors
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
      
      // Initialize Tower A
      this.towerSize = towerSize;
         for(int i = towerSize; i > 0; i--){
            towerA.push(i);
         }

      // Set up move-tracking variables
      this.numMoves = this.getNumMoves(towerSize);
      this.currentMove = 1;
      
      // Adjust tower order for number of discs
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
      
      this.output = "";
      this.outputFile = outputFile;
      this.outputCounter = 0;
   }
    
/******************************
*******************************
***   Public Methods
******************************
******************************/    

   public void solve() throws IOException {
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
      
      while(this.currentMove <= this.numMoves){
         int[] moves = getMovePair();
         makeMove(moves[0], moves[1]);
         this.currentMove++;
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
   
   public long getNumMoves(){
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
      ** It updates the appropriate tower IntStacks and [_____________________].
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
      int tower1Value = towers[tower1].isEmpty() ? Integer.MAX_VALUE : towers[tower1].peek();
      int tower2Value = towers[tower2].isEmpty() ? Integer.MAX_VALUE : towers[tower2].peek();
      
      return tower1Value > tower2Value ? tower1 : tower2;
   }
   
   private void flipLastTwoTowers(){
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
      return this.towerStrings[towerNum];
   }
}
