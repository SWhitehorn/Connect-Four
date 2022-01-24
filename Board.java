import java.lang.Math;

/* The board class is responsible for interactions with the board. Anything involving the data of the board is contained within this 
class, so that although it a longer class than others it is well encapsulated.*/

public class Board {
    
    //Fields

    // The board field is a two dimensional array that forms the board.
    public char[][] boardArray;

    /* The board height and board with are stored as fields, so that there is a single point of reference for other methods involving these numbers. This reduces the chance
        that a value outside the bounds of the array will be accessed. As neither should be altered during the game they are constants, using the static final method. Static 
        means that they will be the same for all Board objects, while final makes them so they cannot be changed. The board width and height are set to the standard values 
        for a connect 4 board. If the player wants to adjust the size of the board, these are the only values that need to be changed. 
        Note: changing the width to be greater than 10 may cause some issues with column labels (see Display)*/ 
    private static final int BOARDWIDTH = 7;
    private static final int BOARDHEIGHT = 6;
    
    //Constructor
    public Board(){
        boardArray = new char[BOARDHEIGHT][BOARDWIDTH];
    }

    //Methods

    //Accessors
    /* BOARDHEIGHT and BOARDWIDTH are private values to stop other classes from altering them, to ensure they remain constant for the entire game. However in several areas the 
        values are needed so the accessors allow the values to be accessed while preventing modification. */ 
    public int returnHeight(){
        return BOARDHEIGHT;
    }

    public int returnWidth(){
        return BOARDWIDTH;
    }
    
    // return square allows other classes to easily check a single square on the board.
    public char returnSquare(int row, int column){
        return boardArray[row][column];
    }

    //This method takes in another board, and copies each element of the array to the current object to create a new copy.
    public void copyBoard(Board boardToCopy){
        for(int row = 0; row<BOARDHEIGHT; row++){
            for(int column = 0; column<BOARDWIDTH; column++){
                this.boardArray[row][column] = boardToCopy.boardArray[row][column];
            }
        }
    }
    public char[][] returnBoardArray(){
        return boardArray;
    }

    /* If there exists a sequence of four tokens in a row of a single type (r or y), a player has won. The method returns true to indicate this. The check is greater than or
        equal to 4 to cover the case where a player places a counter that connects two sub-lists to create a sequence greater than 4. */
    public boolean checkWin(char player){
        if (getMaxScore(player) >= 4){
            return true;
        }
        else{
            return false;
        }
    }

    /* The getMaxScore method returns the greatest sequence on the board for the given player.*/
    public int getMaxScore(char player){
        
        // Score keeps track of the largest sequence seen so far.
        int score = 0;

        /* The loop iterates over each row of the board and looks for the greatest horizontal sequence. If a row contains four in a row then no further checks are needed and the
        four is returned.*/
        for (int row=0; row<BOARDHEIGHT; row++){
            score = Math.max(checkHorizontal(player, row), score);
            if (score >= 4){
                return score;
            }
        }

        /* The loop iterates over each column of the board and looks for the greatest vertical sequence. If a column contains four in a row then no further checks are needed and the
            four is returned.*/
        for (int column=0; column<BOARDWIDTH; column++){
            score = Math.max(checkVertical(player, column), score);
            if (score >= 4){
                return score;
            }
        }

        /*The loop iterates over each column of the board and looks for the greatest diagonal sequence going right and down. The check uses downwards diagonals as the check starts from
        the top and moves down, so checking down is more efficient than checking up. */
        for (int row=0; row<BOARDWIDTH; row++){
            score = Math.max(checkDiagonalRight(player, row), score);
            if (score >= 4){
                return score;
            }
        }

        /*The loop iterates over each column of the board and looks for the greatest diagonal sequence going right and up. */
        for (int row=0; row<BOARDWIDTH; row++){
            score = Math.max(checkDiagonalLeft(player, row), score);
            if (score >= 4){
                return score;
            }
        }

        // Score is returned at the end in case there are no tokens on the board and no score is greater than 0. Under normal operation this should not happen.
        return score;        
    }

    public int checkHorizontal(char player, int row){  
        int count = 0;
        // For each row, if the next square is also the player's then one is added to the count.
        for(int col=0; col<BOARDWIDTH; col++){
            
            if(boardArray[row][col] == player){
                count = count + 1;
                
                // If the count is greater than 4 then no further checks are needed and the count is returned.
                if (count >= 4){
                    return count;
                }
            }
            // If the next square is not the player's, then the count is reset.
            else{
                count = 0;
            }
        }
        /* The maximum count is returned. The count, rather than just whether a player has won or not, is returned to allow the computer 
        player to evalutate boards before the end has been reached. */
        return count;
    }
    
    // Check vertical works the same as check horiontal, but looks up each column rather than across each row. 
    public int checkVertical(char player, int col){
        int count = 0;
        for(int row=0; row<BOARDHEIGHT; row++){
            if(boardArray[row][col] == player){
                count = count + 1;
                if (count >= 4){
                    return count;
                }
            }
            else{
                count = 0;
            }
        }
        return count;
    }

    /*Check diagonal works similarly to check horizontal and vertical. For each square, if it and the four squares above and to the 
        right are of the same type then a count of 4 is returned.*/
    public int checkDiagonalRight(char player, int row){
        int count = 0;
        for(int col=0; col<BOARDWIDTH; col++){
            for (int k=0; k<4; k++){
                try{
                    if(boardArray[row+k][col+k] == player){
                        count++;
                        if(count >= 4){
                            return count;
                        }
                    }
                    else{
                        count = 0;
                    }
                }
                /* If moving four up or to the right of the initial square moves past the array bounds, the exception is caught and the 
                    loop moves to the next square. */
                catch(ArrayIndexOutOfBoundsException e){
                    count = 0;
                    continue;
                }
            } 
        }
        return count;
    } 

    // Left works the same as right but substracting from the column number, so moves left.
    public int checkDiagonalLeft(char player, int row){
        int count = 0;
        for(int col=0; col<BOARDWIDTH; col++){
            for (int k=0; k<4; k++){
                try{
                    if(boardArray[row+k][col-k] == player){
                        count++;
                        if(count >= 4){
                            return count;
                        }
                    }
                    else{
                        count = 0;
                    }
                }
                catch(ArrayIndexOutOfBoundsException e){
                    count = 0;
                    continue;
                }
            } 
        }
        return count;
    } 


    /* Make move places a token by starting from the top row in the specified column, then moving down the rows until the first
        available slot is reached.*/
    public void makeMove(int position, char player){
		if(player == 'r'){
			for(int row=BOARDHEIGHT-1; row>=0; row--){
                if(boardArray[row][position] == 'y'){
                    continue;
                }
                else if(boardArray[row][position] != 'r'){
                    boardArray[row][position] = 'r';
                    return;
                }
			}
		}
		else{
			for(int row=BOARDHEIGHT-1; row>=0; row--){
                if(boardArray[row][position] == 'r'){
                    continue;
                }
                else if(boardArray[row][position] != 'y'){
                    boardArray[row][position] = 'y';				
                    return;
                } 
            }
		}
	}

    public boolean isBoardFull(){
        Boolean full = true;
        for (int col = 0; col<BOARDWIDTH; col++){
            if (boardArray[0][col] != 'y' && boardArray[0][col] != 'r'){
                full = false;
            }
        }
        return full;
    }
}