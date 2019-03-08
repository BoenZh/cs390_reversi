package plu.yellow.reversi.model;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingSource;
import org.jdesktop.swing.animation.timing.sources.SwingTimerTimingSource;
import plu.yellow.reversi.model.gui.GameWindow;

public class Main {

    public static void main(String[] args)
    {
        // Set the default timing source for animation
        TimingSource ts = new SwingTimerTimingSource();
        Animator.setDefaultTimingSource(ts);
        ts.init();

        //Create a new gameboard
        Board board = new Board(8);
        // Create the GameWindow
        GameWindow g = new GameWindow(board);
        g.getBoardView().setManager("player1", "player2");
    }
}
