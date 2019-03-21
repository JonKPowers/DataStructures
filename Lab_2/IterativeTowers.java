class IterativeTower
{
    private int towerSize;
    private intStack towerA = new intStack();
    private intStack towerB = new intStack();
    private intStack towerC = new intStack();

    IterativeTower(int towerSize){
        /**
        ** Constructor for IterativeTower. It takes in an integer,
        ** which sets the number of discs
        ** to be used in the problem. These are pushed onto a stack
        ** representing Tower A, which sets up the problem's initial state.
        **
        ** @param towerSize An integer representing the number of discs to be used in the problem.
        **/
        this.towerSize = towerSize;
        for(int i = towerSize; i < 0; i--){
            towerA.push(i);
        }
    }

    public void solveTower(){
        /**
        ** solverTower() generates a solution to the Towers of
        ** Hanoi problem with the discs being moved to the traditional
        ** target tower, Tower C (the third tower, on the opposite side 
        ** of the peg board from the starting tower). Discs are 
        ** always initially stacked on Tower A.
        **/

        solveTower("C");
    }

    public void solveTower(String targetTower){

    }

    private int getWorkingDirection(int towerSize, String targetTower){
        /**
        ** getWorkingDirection() determines whether moves should be 
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
