/* Player is an interface, which HumanPlayer and ComputerPlayer implement. Both classes interact in the same way with the main game 
    loop, so are grouped together as Player subclasses. Each subclass implements the getMove and returnColour method seperately.*/

public interface Player {
    
    int getMove(Board board);
    
    char returnColour();
}
