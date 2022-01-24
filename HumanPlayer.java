import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


/* The HumanPlayer class is a subclass of Player. It is responsible for getting the input from the player, making sure
    that it is valid, and returning it. All functionality to do with reading in input is in this class, thereby promoting encapsulation and 
    cohesion.*/
public class HumanPlayer implements Player{


//Fields
    private BufferedReader input;

    // See comments on ComputerPlayer for explanation of choice of keywords. 
    private static final char colour = 'r'; 

//Constructor
    public HumanPlayer(){

        /* Input is the BufferedReader that will read the player's input. System.in must be wrapped in InputStreamReader before it can be passed
            to the BufferedReader. */
        input = new BufferedReader(new InputStreamReader(System.in));
    }

//Methods
    /* getMove overrides the getMove in the interface, so the functionality is different to that in ComputerPlayer.*/
    @Override
    public int getMove(Board board){
        boolean validNumber = false;
        boolean goodInput = false;
        
        try{
            int move;
            String userInput;
            
            /* The use of a do while loop means the player will be prompted at least once for an input, and will be prompted again 
                until they input a valid number. */
            do{
            /* Getting user input is dealt with in a seperate method to make it easier to check for exceptions. This again uses a do while
                loop to ensure the program only continues if there are no exceptions. getUserInput will return a null string if something went wrong. */
                do{
                    userInput = getUserInput();
                    if (userInput != null){
                        goodInput = true;
                    }
                }while(!goodInput);
            
                /* parseInt will throw a NumberFormatException if the input supplied is not a number. This is caught below.*/
                move = Integer.parseInt(userInput)-1;
                
                /* The number is checked to make sure it is not outside the boundaries of the array. This could also have been accomplished
                    with a custom exception, but an if statement is simpler.*/
                if (move < 0 || move >= board.returnWidth()){
                    System.out.println("Please input a number between 1 and " + board.returnWidth());
                }
                /* The column is checked to make sure it is not already full.*/
                else if (board.boardArray[0][move] == 'r' || board.boardArray[0][move] == 'y'){
                    System.out.println("That column is already full. Try another!");
                }
                else{
                    validNumber = true;
                }
            }while(!validNumber);
            
            return move;   
        }
        /* The exception is a user inputs something other than a number is caught, and getMove is called again to prompt the user to 
            give the user another chance to input a valid number.*/
        catch(NumberFormatException e){
            System.out.println("Please input a single number.");
            return getMove(board);
        }

    }

    /*This method reads the user's input from the command line. readLine throws a checked IOException which must be caught.*/
    private String getUserInput(){
		String toReturn = null;
		try{			
			toReturn = input.readLine();
           // return toReturn;
		}
        // If there was an error with the input, the player is notified and a null string is returned.
		catch(IOException e){
			System.err.println("Something went wrong with the input. Try again.");
            //return toReturn;
		}   
        return toReturn;
	}

    @Override
    public char returnColour(){
        return colour;
    }
}