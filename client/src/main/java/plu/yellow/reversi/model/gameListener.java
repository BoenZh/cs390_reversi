package plu.yellow.reversi.model;
import plu.yellow.reversi.model.gui.GameHistoryPanel;
import plu.yellow.reversi.model.gui.GameWindow;

import java.util.ArrayList;

/**
 * Created by boen zhang on 4/9/2017.
 */
public class gameListener {
    private Board model;
    private GameHistoryPanel history;
    private GameWindow gui;


    public gameListener(GameHistoryPanel h,GameWindow m){
        history=h;
        gui=m;




    }





    private class Move {
        private int r;
        private int c;
        private int player;
        public Move(int row,int col, int player) {
            r=row;
            c=col;
            this.player = player;
        }
    }






}
