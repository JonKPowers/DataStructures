import java.io.FileWriter;
import java.io.IOException;

class RecursiveTowers{
   /**
   ** The RecursiveTowers class implements a recursive solution to the Towers
   ** of Hanoi problem. It's basic operation follows the simple strategy moving
   ** all the discs on top of the target disc onto a temporary tower, moving the
   ** target disc onto its target tower, and then moving the discs from the
   ** temporary tower onto the target tower.
   **
   ** Memoization is enabled if this.optimize is set to true. However, the current
   ** implementation of BetterMoveEncoder and the BetterMemoizer is such that 
   ** it runs into heap-related out-of-memory issues once numDiscs==30. But up
   ** until that point, the memoization does generate significant speed performance
   ** improvements over the basic recursive approach (and the iterative approach
   ** as well).
   **
   ** When using the basic solving method, moves are written to file as they are
   ** calculated rather than being stored and then later transferred into a file.
   ** This is primarily due to the development history of this class, but the
   ** BetterMoveEncoder class has some capacity issues that make it unsuitable
   ** for large move sets at the moment. However, when memoization is enabled, 
   ** moves _are_ stored in a BetterMoveEncoder (which is the source of its 
   ** limitations), and moves are not written to file until the solution is
   ** completely computed.
   **
   ** @author Jon Powers
   ** @version 0.1
   **/
   
   private int numDiscs;
   private String startTower, targetTower, tempTower;
   private FileWriter outputFile;
   
   // Memoization-related variables
   private boolean optimize;
   private BetterMemoizer moveMemory;
   private BetterMoveEncoder[] moves;
   private int movesPosition;
   
   ////////////////////////////
   // Constructor
   ////////////////////////////
   
   RecursiveTowers(int numDiscs, String targetTower, FileWriter outputFile, boolean optimize){
      /**
      ** Constructor for a RecursiveTowers object. If memoization is enabled, a
      ** BetterMoveEncoder and a BetterMemoizer are instantiated along with some
      ** state-tracking variables. In addition, this class contains a targetTower/tempTower
      ** concept that would allow for us to place the discs on Tower B, but that
      ** option has not yet been implemented in the user-facing interface for the program.
      **
      ** @param numDiscs The number of discs to solve for
      ** @param targetTower A String containing the short-hand identifier for the tower that discs will be moved to, e.g., "B" or "C"
      ** @param outputFile A FileWriter object connected to the file to write output to
      ** @param optimize If true, then memoization is enabled; memoization is disabled by default
      **
      **/ 
      
      
      this.numDiscs = numDiscs;
      this.outputFile = outputFile;
      
      // Problem-specific tower variables      
      this.startTower = "A";
      this.targetTower = targetTower;
      this.tempTower = targetTower.equals("C") ? "B" : "C";

      
      // Set up memoization-related items if enabled
      if(optimize){
         this.optimize = optimize;
         this.moves = new BetterMoveEncoder[1000000000];
         this.moveMemory = new BetterMemoizer(numDiscs);
         this.movesPosition = 0;
      }
      
   }
   
   ////////////////////////////
   // Public Methods--Wrappers
   ////////////////////////////
   
   public void solve() throws IOException{
      /**
      ** solve() is a wrapper around moveDiscs(), which is the primary workhorse 
      ** of this class when memoization is disabled. moveDiscs() is the recursive function
      ** that actually does the work of computing a solution.
      **
      ** @return None Nothing is returned (moves are written to file as they are calculated)
      **
      **/
      moveDiscs(this.numDiscs, this.startTower, this.tempTower, this.targetTower);
   }
   
   public BetterMoveEncoder solve(boolean optimize) throws IOException{
      /**
      ** solve() is a wrapper around moveDiscs() when memoization is enable. As 
      ** with the non-memoized version, moveDiscs() is the primary workhorse 
      ** of this class when memoization is enabled. moveDiscs() is the recursive function
      ** that actually does the work of computing a solution.
      **
      ** @param optimize This should always be set to the value true.
      ** @return BetterMoveEncoder A BetterMoveEncoder object containing the solution move sequence
      **
      **/
      return moveDiscs(this.numDiscs, this.startTower, this.tempTower, this.targetTower, true);
   }

   ////////////////////////////
   // Private Methods
   ////////////////////////////

   private void moveDiscs(int numDiscs, String start, String temp, String end) throws IOException { 
      /**
      ** moveDiscs(int, String, String, String) is the recursive function that calculates
      ** a solution to the Towers of Hanoi problem when memoization is disabled. Moves are
      ** written directly to disk as they are calculated via the call to makeMove().
      **
      ** @param numDiscs The number of discs to solve for
      ** @param start The tower to move the discs from
      ** @param temp The tower to use a a temporary holding point for the disks while being moved
      ** @param end The tower to move the discs to
      ** @return None Nothing is returned (moves are written directly to disc as they are calcaulted)
      **/       
      if (numDiscs == 1){
         // Base case of 1 disc--just move it to its destination
         makeMove(numDiscs, start, end);
      } else {
         // Otherwise, move other discs out of the way, move the disc
         // to its destination, then move the other discs to their
         // final desination.

         moveDiscs(numDiscs - 1, start, end, temp);
         makeMove(numDiscs, start, end);
         moveDiscs(numDiscs - 1, temp, start, end);
      }
   }
   

   
   private BetterMoveEncoder moveDiscs(int numDiscs, String start, String temp, String end, boolean optimize) throws IOException {
      /**
      ** moveDiscs(int, String, String, String, boolean) is the recursive function that calculates
      ** a solution to the Towers of Hanoi problem when memoization is enabled. Moves are
      ** stored in a BetterMoveEncoder, which is later written to disk.
      **
      ** When a call is made to moveDiscs() it looks first to the BetterMemoizer to see if 
      ** a solution to the current problem has been calculated previously. If so, it simply
      ** pulls that move sequence (stored in a BetterMoveEncoder) from the memoizer. Otherwise
      ** it calculated the solution move sequence recursively, stores it in the memoizer,
      ** and then returns the sequence.
      **
      ** @param numDiscs The number of discs to solve for
      ** @param start The tower to move the discs from
      ** @param temp The tower to use a a temporary holding point for the disks while being moved
      ** @param end The tower to move the discs to
      ** @param optimize This should always be true when memoization is enabled.
      ** @return None Nothing is returned (moves are written directly to disc as they are calcaulted)
      **/
      
      // Check the memoizer first  
      if (moveMemory.moveStored(numDiscs, start, end)) {
         BetterMoveEncoder tempMoves = moveMemory.getMoves(numDiscs, start, end);
         moves[movesPosition++] = tempMoves;
         return tempMoves;
      // If it's not there, calculate it and store it.
      } else {
         BetterMoveEncoder encoder = new BetterMoveEncoder(numDiscs);
         if (numDiscs == 1){
            // Base case of 1 disc--just move it to its destination
            encoder.addMove(numDiscs, start, end);
         } else {
            // Otherwise, move other discs out of the way, move the disc
            // to its destination, then move the other discs to their
            // final desination.
   
            encoder.addMoves(moveDiscs(numDiscs - 1, start, end, temp, true));
            encoder.addMove(numDiscs, start, end);
            encoder.addMoves(moveDiscs(numDiscs - 1, temp, start, end, true));
            
            // Add the calculated moves to the Memoizer
            moveMemory.setMoves(numDiscs, start, end, encoder);
         }
         
         return encoder;
      }
   }

   private void makeMove(int numDiscs, String start, String end) throws IOException {
      /**
      ** makeMove() writes a particular calculated move to disk when memoization is 
      ** not enabled. The information is written to disk via the this.outputFile
      ** attibute, which contains a FileWriter object.
      **
      ** @param numDisc The integer identifier of the disc being moved
      ** @param start The short-form String identifier of the starting tower, e.g., "A", "B", or "C".
      ** @param end The short-form String identifier of the destination tower.
      **/
      this.outputFile.write("Move disc " + numDiscs + " from Tower " + start + " to Tower " + end + "\n");
   }
}
