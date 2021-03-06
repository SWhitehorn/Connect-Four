/* ComputerPlayer is responsible for generating the computer player's move. The primary responsibility of the class is to carry out a 
    minimax algorithm to find the route through the tree that results in the best outcome for the computer after four moves (the size 
    of the tree).*/

public class ComputerPlayer implements Player{
    
    /* With the current game, the computer always goes second and takes the 'y' tokens. This is therefore set as a static field for the 
        class. Should the option be added to allow the player to select a token and whether to go first or section, this would have to 
        be changed.*/
    private static final char colour = 'y';

    // ComputerPlayer's only field is constant, so the constructor is blank.
    public ComputerPlayer(){
    }

    @Override
    public int getMove(Board originalBoard){
        
        // A root node is created for the decision tree. The node is defined in a seperate class.
        Node root = new Node(0, 'r');
        
        /* Setting the nodeBoard to the originalBoard directly would not create a new object, but a pointer to the original object. Therefore
            each value of the array in the original board is copied over to the new board array.*/
        root.getNodeBoard().copyBoard(originalBoard);


        DecisionTree tree = new DecisionTree(root);
    

        /* A move by the computer is generated by the use of the minimax algorithm. This has been abstracted to its own method. 
        The algorithm returns the move the computer should make that will result in the best leaf node of the tree (note that the tree 
        does not cover the entire game, but only up to the specified depth). The function returns a tuple with the column that the computer 
        should place its counter in. The tuple is defined as a seperate class, and the move is retrieved by calling getMove(). The move is
        then returned by the method. The principle source for the general idea of the miniMax algorithm was 
        https://medium.com/analytics-vidhya/artificial-intelligence-at-play-connect-four-minimax-algorithm-explained-3b5fc32e4a4f, but all code is my own. */
        return miniMax(tree.returnRoot(), true).getMove();
        
    }

    /* miniMax implements the mini-max algorithm to get the best move. The method takes a node as input. If the node is
        a terminal node, it simulates  
        returns a Tuple of the value of the node and the move made. It takes in the node to be explored and a boolean saying whether the value should be 
        maximised or minimised. */
    private Tuple miniMax(Node node, boolean computer){
        /* This is the base case of the method. If the end of the tree has been reached the computer evalutes the board stored by that node, setting the 
            value of the node to be the score returned.*/
        
            if (node.isLeaf()){
                int value = evaluateBoard(node.getNodeBoard(), node.getPlayer());
                node.setValue(value);
                // A tuple containing the move of the node and the score of the board with that move is returned.
                return new Tuple(node.getMove(), node.getValue());
        }

        /* If the node is not a leaf, then the score of the node is either the maximum or minimum of its childnodes (depending on whether the player is 
            maximising or minimising). This is retreieved by calling the method recursively on each child node until the leaf nodes are reached. */
        else {
            
            /* For the maximising player, the node score is highest score of the childnodes (where each childNode represents a valid move). */
            if (computer){
                /* The score is initially set at a value lower than any possible board score to ensure that at least one node will be higher. */ 
                Tuple score = new Tuple(0, -100000);
                
                /* miniMax is called on each childnode. If the returned value is higher than the score value, then score is set to the value of the node. 
                    This means that to set the value of a node the search process must first explore down to the bottom of the tree (depth-first) */
                for (Node childNode : node.getChildren()){
                    Tuple nodeScore = miniMax(childNode, !computer);
                    if (nodeScore.getValue() > score.getValue()){
                        score = nodeScore;
                    }
                }
                return score;
            }
            
            /* For the minimising player, the node score is lowest score of the childnodes (where each childnode represents a valid move). */
            else{
                Tuple score = new Tuple(0, 100000);
                computer = !computer;
                for (Node childNode : node.getChildren()){
                    Tuple nodeScore = miniMax(childNode, computer);
                    if (nodeScore.getValue() < score.getValue()){
                        score = nodeScore;
                    }
                }
                return score;
            }
        }
    }

    /* This method evaluates the board by looking at the maximum score for each player. 
        The scoring of the board uses the same method that is used to check for a win, which has been abstracted into the board class. 
        The weightings of the score are adjusted to reflect their proportional importance within the game, although the exact values are abitrary.*/ 
    private int evaluateBoard(Board board, char player){
        int weightedScore = 0;

        //getMaxScore returns the longest open sequence of characters. row.e. ryyy is a score of 3 for y, but ryyyr is closed so has a score of 1
        int score = board.getMaxScore(player);
        if (score == 1){
            weightedScore = 1;
        }
        if (score == 2){
            weightedScore = 3;
        }
        if (score == 3){
            weightedScore = 100;
        }
        if (score >= 4){
            weightedScore = 1000;
        }
        return weightedScore;
    }

    @Override
    public char returnColour(){
        return colour;
    }
}    


    