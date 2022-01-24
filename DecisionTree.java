/* Decision tree takes an initial node and builds a tree of all possible valid moves up the specified depth. This has been put in a 
    seperate class to promote cohesiveness of other classes, particularly of the ComputerPlayer class. Unfortunately this does mean the
    two are tightly coupled, so there is a trade off between the two. I decided to opt for class cohesiveness and have two seperate classes
    to make each individual class simpler.*/
public class DecisionTree{

//Fields
    /* The root node of the tree should not change once the tree is created. Setting this to be The root of the tree is private 
        and final stops it being changed or accessed by another part of the program.*/
    private final Node root;

//Constructor
    public DecisionTree(Node parent){
        this.root = parent;
                
        /* A tree is generated of all possible moves the computer could make, and all subsequent responses by the player. 
                The first parameter is the root node, the second is the depth of the tree, and the third is whether the move is by 
                the computer or the player (this will switch between the two as the tree is recursively generated.) */
        generateTree(root, 4, 'y');
    }

//Accessor
    // Return root returns the root to allow the computerplayer to safely access the tree.
    public Node returnRoot(){
        return root;
    }

    /* The method does not return anything, as each node can be reached from the root node. 
        As objects are passed by reference rather than value changes made to the tree by the method will be 
        able to be accessed by returning the root node. */
    private void generateTree(Node parent, int depth, char player){          
        
        // Each column is iterated over.
        for(int col=0; col<parent.getNodeBoard().returnWidth(); col++){
            
            // If the column is already full (the top row has a playing piece on each entry) the column is skipped.
            if(parent.getNodeBoard().returnSquare(0, col) == 'y' || parent.getNodeBoard().returnSquare(0, col) == 'r'){
                continue;
            }

            /* A new node is created, where col is the column (and therefore move) it represents. The
             node is then added to the parent node as a child. */
            Node node = new Node(col, player);
            parent.addChildNode(node);

            /* A new board is created to store the state of the board after the move represented by the node. This state of
                the parent boardArray is copied over to newBoard. The details of this are abstracted into the Board class. */
            node.getNodeBoard().copyBoard(parent.getNodeBoard());
            
            /* The move represented by the node is played on the board*/
            node.getNodeBoard().makeMove(node.getMove(), player);
        

            /* If the specified depth has not been reached and no player has reached four in a row yet, the method is called again 
                to add the next layer to each node. The depth is reduced by one each count to limit the size of the tree. */
            if (depth > 0 && node.getNodeBoard().getMaxScore(player) != 4){
                // The player char switches between y and r
                if (player == 'y'){
                    player = 'r';
                }
                else{
                    player = 'y';
                }
                generateTree(node, depth-1, player);
            }
        }
    }

    
}


