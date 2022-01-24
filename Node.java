import java.util.ArrayList;

/* The Node class is used by the DecisionTree class to create the decision tree for the game.*/
class Node {
    
    /* Fields. 
        Each node stores the column that the counter was put into and the player who placed it, the state of the board, the nodes 
        directly below it, and the value of the board for the board for the player who placed the token. Move and player should never
        change so are marked with the keyword final. All fields are private as they should be retrieved through the accessor methods.*/
    private final int move;
    private final char player;
    private Board nodeBoard;
    private ArrayList<Node> nodes;
    private int value;

    /* Constructor class. 
        The node is initialised to store the player's move and the player's colour. */
    public Node(int move, char player){
        this.move = move;
        this.player = player;
        
        // The other variables are initialised to empty. Populating the values of the board is handled within the Board class.
        nodes = new ArrayList<Node>();
        nodeBoard = new Board();
    }

    // Accessors to allow for effective encapuslation of Node while allowing other classes to acces fields where necessary.
    public int getMove(){
        return move;
    }

    public char getPlayer(){
        return player;
    }

    public Board getNodeBoard(){
        return nodeBoard;
    }

    public int getValue(){
        return value;
    }

    public ArrayList<Node> getChildren(){
        return nodes;
    }

    // Takes a second node, and attaches it to this one as a child node.
    public void addChildNode(Node child){
        nodes.add(child);
    }

    // Allows for easy checking of whether the node has anything below it.
    public boolean isLeaf(){
        if (nodes.size() == 0){
            return true;
        }
        return false;
    }

//Modifier
    // Sets the value of the node.
    public void setValue(int value){
        this.value = value;
    }
}