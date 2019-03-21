class lab2RecursiveTowers{
    public static void main( String[] args ){
        String startPeg, endPeg, auxPeg;
        int numDiscs;

        // Error checking that number of towers is valid
        // Error checking that file name is valid

        moveDiscs(35, "A", "B", "C");

    }

    public static void moveDiscs(int numDiscs, String start, String temp, String end){
        if (numDiscs == 1){
            // Base case of 1 disc--just move it to its destination
            makeMove(numDiscs, start, end);
        }
        else {
            // Otherwise, move other discs out of the way, move the disc
            // to its destination, then move the other discs to their
            // final desination.
            moveDiscs(numDiscs - 1, start, end, temp);
            makeMove(numDiscs, start, end);
            moveDiscs(numDiscs - 1, temp, start, end);
        }
    }

    public static void makeMove(int numDiscs, String start, String end){
        System.out.println("Move disc " + numDiscs + " from peg " + start + " to peg " + end + ".");
    }
}
