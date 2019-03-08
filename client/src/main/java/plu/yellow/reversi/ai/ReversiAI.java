package plu.yellow.reversi.ai;


import plu.yellow.reversi.model.AbstractPerson;
import plu.yellow.reversi.model.Board;
import plu.yellow.reversi.model.gui.GameWindow;

/**
 * Created by Paul on 3/16/2017.
 */
public class ReversiAI extends AbstractPerson {
    /** Stores the depth of the decision tree to search down */
    private  int difficulty;
    private int[] result = new int[2];



    /**
     * Constructor initializes the AI to a specified difficulty
     * @param d The depth of the search down the minimax tree
     */
    public ReversiAI(int d) {
        difficulty = d;
        this.id = 2;
        this.a = true;
    }

    public void setDifficulty(int d) {
        difficulty=d;
    }

    public int getDifficulty() {return difficulty;}

    /**
     * Runs the minimax algorithm on a reversi board to the depth
     * specified by difficulty.
     * TODO Add alpha-beta trimming to the algorithm
     * @param b The Board object to be examined
     * @param
     */
    public int[] playBestMove(Board b, int aiID) {
        //determine which player the AI is playing for
        int otherID = 1;
       /* if (aiID == 1)
            otherID = 2;
        else if (aiID == 2)
            otherID = 1;
        else
            aiID = 0;
            */
        aiID = 2;
        //initialize vars for algorithm
        int max = -65, potentialScore;
        int bestR=0, bestC=0;
        Board aiBoard;
        //Check each possible move
        for(int r = 0; r < 8; r++)
            for(int c =0; c<8; c++) {
                if (b.checkSpace(r, c, aiID)) { //for each valid board, examine gamestate of next move
                    aiBoard = new Board(b);
                    aiBoard.placeMove(r, c, aiID);
                    //recursively look at future moves of each side, and choose best
                    potentialScore = bestPlayerMove(aiBoard, 1, aiID, otherID);
                    if (max <= potentialScore) {
                        bestR = r;
                        bestC = c;
                        max = potentialScore;
                    }
                }
            }
        //play the least potentially bad move
        b.placeMove(bestR,bestC,aiID);
        result[0] = bestR;
        result[1] = bestC;
        return result;
    }

    //Recursively finds the best AI move at a board state
    private int bestAIMove(Board ai, int iter, int aiID, int otherID) {
        if (iter > difficulty) //if max depth has been reached, return the score difference on the board
            return ai.score(aiID)-ai.score(otherID);
        int max = -64;
        //Find the best possible ai move in this board state
        Board temp = null;
        for(int r = 0; r < 8; r++)
            for(int c =0; c<8; c++) {
                if (ai.checkSpace(r, c, aiID)) {
                    temp = new Board(ai);
                    temp.placeMove(r, c, aiID);
                    max = Math.max(max, bestPlayerMove(temp, iter + 1, aiID, otherID));
                }
            }

        return max;
    }

    private int bestPlayerMove(Board p,int iter, int aiID, int otherID) {
        if (iter > difficulty) //if max depth has been reached, return the score difference on the baord
            return p.score(aiID)-p.score(otherID);
        int min = 64;
        //Find the best possible player move in this board state
        Board temp = null;
        for(int r = 0; r < 8; r++)
            for(int c =0; c<8; c++) {
                //System.out.println("Checking: " + r + "," + c + "At level " + iter);
                if (p.checkSpace(r, c, otherID)) {
                    temp = new Board(p);
                    temp.placeMove(r, c, otherID);
                    min = Math.min(min, bestAIMove(temp, iter + 1, aiID, otherID));
                }
            }

        return min;
    }

    public String toString(){
        return "AI instantiated";
    }


    //simple main method to test that AI does in fact place a piece
    public static void main(String args[]) {
        //simple test of the AI
        Board b = new Board(8);
        GameWindow g = new GameWindow(b);
        ReversiAI r = new ReversiAI(8);

        //  b.placeMove(5,3,1);
        System.out.println();
        b.display();
        r.playBestMove(b,2);
        b.display();
        // g.getBoardView().updateBoard();
    }
}

