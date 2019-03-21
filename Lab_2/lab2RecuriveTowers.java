class lab2RecursiveTowers{
    public static void main( String[] args ){
        String startPeg, endPeg, auxPeg;
        int numDiscs;

        // Error checking that number of towers is valid
        // Error checking that file name is valid

        String output = moveDiscs(4, "A", "B", "C");
        System.out.println("Final output string:\n" + output);

    }

    public static String moveDiscs(int numDiscs, String start, String temp, String end){
        String output = "";
        
        if (numDiscs == 1){
            // Base case of 1 disc--just move it to its destination
            output += makeMove(numDiscs, start, end);
        }
        else {
            // Otherwise, move other discs out of the way, move the disc
            // to its destination, then move the other discs to their
            // final desination.

            output += moveDiscs(numDiscs - 1, start, end, temp);
            output += makeMove(numDiscs, start, end);
            output += moveDiscs(numDiscs - 1, temp, start, end);
            
            // System.out.println("Output from moveDiscs at numDiscs=" + numDiscs + ": " + output);
        }
         return output;
    }

    public static String makeMove(int numDiscs, String start, String end){
        String outputString = "Move disc " + numDiscs + " from peg " + start + " to peg " + end + ".\n";
        System.out.println(outputString);
        return outputString;
    }
}
