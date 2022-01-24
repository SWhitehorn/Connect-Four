/* The display class is responsible for printing the board to the screen. This has been removed from Board.java to 
    decouple Board.java from the System out. The loose coupling makes it easier to modify the code in future, for example
    if a user wanted to add a GUI to display the board outside of the terminal. This also promotes cohesion for each class,
    as each Board is only responsible for maintaining and checking the state of the board and not printing it.*/

public class Display {

    /* The width and height should not change during the change so are marked as final.*/
    private final int width;
    private final int height;

    public Display(int width, int height){
        this.width = width;
        this.height = height;
    }

    /*printBoard has been altered to use the constant attributes of the class so the user can adjust the size of the board without having to adjust 
        how the board is printed out.*/
    public void printBoard(char[][] board){
        for(int row=0; row<height; row++){
            for(int col=0; col<width; col++){
                if(board[row][col] == 'r'){
                    System.out.print("| r ");
                }
                else if(board[row][col] == 'y'){
                    System.out.print("| y ");
                }
                else{
                    System.out.print("|   ");
                }
            }
            System.out.println("|");
        }

        /* The column numbers are printed out at the end, after the board. This has also been made dynamic, so that the correct number of columns is printed out if the player 
            varies the width of the board. Note: with widths greater than 10, the column values will start to misalign. */ 
        for (int col=0; col<width; col++){    
            System.out.print("  " + (col+1) + " ");
        }
        System.out.println();
        
    }
}


