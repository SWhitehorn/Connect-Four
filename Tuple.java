/* The Tuple class has been implemented to give the ability to return two values at once. This is used in the minimax algorithm, where
    both the value of that path and the move should be returned. This allow the computer player to evaluate the values and then choose the 
    best move.*/

public class Tuple {
    
/*Fields
    These are private as they should not be accessed directly to ensure effective encapusaltion. 
    They also have the final keyword to ensure they are not modified once created. */
    private final int move;
    private final int value;

/* Contrustor
    Creates a new tuple out of the move and the value passed in. */
    public Tuple (int move, int value){
        this.move = move;
        this.value = value;
    }

/* Accessors
    Allow the attributes to be accesed while being private*/ 
    public int getMove(){
        return move;
    }

    public int getValue(){
        return value;
    }
}
