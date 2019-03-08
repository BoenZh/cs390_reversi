
package plu.yellow.reversi.Social;

import plu.yellow.reversi.model.Board;

import java.io.IOException;

/**
 * Created by Taylor on 4/27/2017.
 */
public class Account {
    private String username;
    private String password;
    private int rank;
    private Board board;

    public Account(String u, String p) {
        username = u;
        password = p;
    }

    public int calcRank() {
        return 0;
    }

    //board state
    public void saveBoard() throws IOException {
        board.saveGame();
    }

    public int getRank(){return rank;}

    public void setRank(int a){rank = a;}
}

