//Game handles the main game loop.
public class Game {
    
//Fields
    private Board board;
    
//Constructor
    public Game(){
        board = new Board();
        playGame();
    }

//Main game loop
    private void playGame(){
        
        printRules();
        Display display = new Display(board.returnWidth(), board.returnHeight());
        display.printBoard(board.returnBoardArray());   

        /* Both the human and the computer player implement the Player interface. This allows them both to be stored in an array 
            storing Player objects. The human player is stored at index 0, and the computer is stored at index 1.*/
        Player[] player = new Player[2];
        player[0] = new HumanPlayer();
        player[1] = new ComputerPlayer();
        
        boolean playerWin = false;
        boolean fullBoard = false;
        int position;
        int playerTurn = 0;
        
        // Game loop
        while(true){
            System.out.println("Enter a column number:");

            /* Both the humanPlayer and the computerPlayer have a getMove method which overrides the Player method. Therefore getMove can 
                be called regardless of the dynamic type. Thanks to dynamic polymorphism, the correct method for the type will be called. This 
                allows the code to be significantly reduced in size compared to having to having two completely seperate types, which would require 
                needing two seperate blocks of code.*/
            position = player[playerTurn].getMove(board);
            board.makeMove(position, player[playerTurn].returnColour());
            display.printBoard(board.returnBoardArray());
            
            /* Both players also have a method to return their colour (by default the player is red). Again, thanks to polymorphism, the same 
                method name can be called on both. */
            if (board.checkWin(player[playerTurn].returnColour()) ){
                if (player[playerTurn].returnColour() == 'r'){
                    playerWin = true;
                }
                break;
            }
            else if (board.isBoardFull()){
                fullBoard = true;
                break;
            }

            /* If no one has won, then the playerTurn value switches between 0 and 1. */
            else{
                playerTurn++;
                if (playerTurn == 2){
                    playerTurn = 0;
                }
            }    
        }

        // Print out whoever won.
        if(fullBoard){
            System.out.println("It's a draw!");
        }
        else if(playerWin){
            System.out.println("you won!");
        }
        else {
            System.out.println("The computer won!");
        }
    }
        

    private void printRules(){
        // Print out the rules. This has been abstracted into a seperate method to simplify the code above.
        System.out.println("Welcome to Connect 4");
		System.out.println("There are 2 players red and yellow");
		System.out.println("Player 1 is Red, Player 2 is Yellow");
		System.out.println("To play the game type in the number of the column you want to drop you counter in"); 
		System.out.println("A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
		System.out.println("");
    }
}

