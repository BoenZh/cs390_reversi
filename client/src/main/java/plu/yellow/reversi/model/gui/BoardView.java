package plu.yellow.reversi.model.gui;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;
import plu.yellow.reversi.model.GameManager;

//import sun.tools.jconsole.Worker;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static plu.yellow.reversi.model.gui.BoardView.CellColor.*;

/**
 * The JPanel containing the board and its edges.
 */
public class BoardView extends JPanel implements MouseListener {

    //Holds previous board state for undo button
    //private Board prevGameState;

    /** The size (width and height) of the board in pixels */
    private int size;

    private int cellRow, cellCol;

    //The PlayerInfoPanel in order to get data for Graphic changes
    private PlayerInfoPanel infoPanel;

    /** Used to help with animation */
    private FlipAnimator fAnimator;

    /** Used to instantiate GameManager */
    private GameWindow gui;

    /* Creates the Gamemanager class to use for AI and score stuff */
    private GameManager manager;

    /** The state of each cell on the board */
    private CellState[][] boardState;

    /*use for update gameHistory*/
    private GameHistoryPanel history;

    private boolean canPlay = false;


    /**
     * This is an internal class used to manage the animation of pieces flipping over.
     */
    private class FlipAnimator extends TimingTargetAdapter {

        private ArrayList<CellState> cells;
        private CellColor startColor;
        private CellColor endColor;
        private Animator animator;

        @Override
        public void end(Animator source) {
            for( CellState c : cells ) {
                c.cellColor = endColor;
                c.height = 1.0f;
            }
            repaint();
        }

        @Override
        public void timingEvent(Animator source, double fraction) {
            double cellChunk = 1.0 / cells.size();
            double cellChunk2 = cellChunk / 2.0;
            for(int i = 0; i < cells.size(); i++ ) {
                double cellStart = (i * cellChunk);
                double cellEnd = ((i+1) * cellChunk);
                CellState c = cells.get(i);
                if( fraction > cellStart && fraction <= cellEnd ) {
                    double h = 0.0;
                    if( fraction < cellStart + cellChunk2 ) {
                        h = 1.0 - (fraction - cellStart) / cellChunk2;
                        c.cellColor = startColor;
                    } else {
                        h = 1.0 - (cellEnd - fraction) / cellChunk2;
                        c.cellColor = endColor;
                    }
                    c.height = (float)h;
                } else if( fraction > cellEnd ) {
                    c.cellColor = endColor;
                    c.height = 1.0f;
                }
            }

            repaint();
        }

        @Override
        public void begin(Animator source) {
            for( CellState c : cells ) {
                c.cellColor = startColor;
                c.height = 1.0f;
            }
            repaint();
        }

        public FlipAnimator(int startRow, int startCol,
                            int endRow, int endCol,
                            CellColor start, CellColor end,
                            long milliseconds)
        {
            cells = new ArrayList<CellState>();
            this.startColor = start;
            this.endColor = end;

            // Gather a list of cells to animate

            if( startRow == endRow ) {
                if( startCol > endCol ) {
                    for( int col = startCol; col >= endCol; col--)
                        cells.add(boardState[startRow][col]);
                } else {
                    for( int col = startCol; col <= endCol; col++ )
                        cells.add(boardState[startRow][col]);
                }
            } else if( startCol == endCol ) {
                if( startRow > endRow ) {
                    for( int row = startRow; row >= endRow; row--)
                        cells.add(boardState[row][startCol]);
                } else {
                    for( int row = startRow; row <= endRow; row++)
                        cells.add(boardState[row][startCol]);
                }
            } else if( Math.abs(startRow - endRow) == Math.abs(startCol - endCol)) {
                if( startCol > endCol ) {
                    if( startRow > endRow ) {
                        for (int row = startRow, col = startCol; col >= endCol; col--, row--)
                            cells.add(boardState[row][col]);
                    } else {
                        for (int row = startRow, col = startCol; col >= endCol; col--, row++)
                            cells.add(boardState[row][col]);
                    }
                } else {
                    if( startRow > endRow ) {
                        for (int row = startRow, col = startCol; col <= endCol; col++, row--)
                            cells.add(boardState[row][col]);
                    } else {
                        for (int row = startRow, col = startCol; col <= endCol; col++, row++)
                            cells.add(boardState[row][col]);
                    }
                }
            } else {
                throw new IllegalArgumentException("Start/end must define a horizontal, vertical or diagonal line."
                    + "\nStart: "+startRow+","+startCol+"  End: "+endRow+","+endCol);
            }

            if( cells.size() == 0 ) {
                throw new IllegalArgumentException("No cells in given range.");
            }

            animator = new Animator.Builder()
                    .setDuration(milliseconds * cells.size(), TimeUnit.MILLISECONDS)
                    .addTarget(this).build();
        }

        public void start() {
            animator.start();
        }

    }

    public enum CellColor {
        WHITE, BLACK, EMPTY, YELLOW
    }

    private class CellState {
        private float height;  // Fractional height used for animation
        private CellColor cellColor;
        private int row, col;

        public CellState( int row, int col ) {
            cellColor = EMPTY;
            this.row = row;
            this.col = col;
            this.height = 1.0f;
        }

        public void setColor( CellColor c)
        {
            cellColor = c;
        }

        public CellColor getColor() { return cellColor; }

        public void draw(Graphics g, float cellSize) {

            if (cellColor != EMPTY) {
                float pad = cellSize * 0.1f;
                float x = col * cellSize;
                float cy = cellSize * (row  + 0.5f);
                float h = height * (cellSize - 2.0f * pad);

                if (cellColor == CellColor.BLACK)
                    g.setColor(Color.black);
                else if(cellColor == CellColor.WHITE)
                    g.setColor(Color.white);
                else
                    g.setColor(Color.yellow);

                g.fillOval(
                        Math.round(x + pad),
                        Math.round(cy - h / 2.0f),
                        Math.round(cellSize - 2 * pad),
                        Math.round(h));
            }
        }
    }

    /**
     * Constructs a new BoardView panel with the given size.
     *
     * @param size width/height in pixels
     */
    public BoardView( int size, PlayerInfoPanel p, GameWindow g)
    {
        this.gui = g;
        this.size = size;
        this.infoPanel = p;
        //this.history=h;

        this.setPreferredSize(new Dimension(500,500) );
        this.setBackground(new Color(12, 169, 18));
        boardState = new CellState[size][size];
        for( int i = 0; i < size ; i++)
            for(int j = 0; j < size; j++ )
                boardState[i][j] = new CellState(i, j);


        // Set up the initial board
        makeBoard();

        fAnimator = null;
        this.addMouseListener(this);
    }

    /**
     * Draws the board.
     *
     * @param g Graphics context
     */
    @Override
    public void paintComponent( Graphics g )
    {
        super.paintComponent(g);

        int w = this.getWidth();
        int h = this.getHeight();

        float cellSize = (float)w / size;

        for(int i = 1; i < size; i++ ) {
            int pos = Math.round( i * cellSize );
            g.drawLine(pos, 0, pos, h );
            g.drawLine(0, pos, w, pos);
        }

        for( int row = 0; row< size; row++ ) {
            for( int col = 0; col < size; col++ ) {
                boardState[row][col].draw(g, cellSize);
            }
        }
    }

    /**
     * Mouse clicked event.  Determines the cell where the mouse
     * was clicked and calls model.place move and goes through a chain of calls to update the board
     * if given space was valid and displays it to the GUI.
     *
     * @param e
     */
    public void mouseClicked(MouseEvent e) {
        if (canPlay == false) {
            startMessage();
        } else {
            int x = e.getX();
            int y = e.getY();

            int w = this.getWidth();
            int h = this.getHeight();

            float cellSize = (float) w / size;

            cellRow = (int) Math.floor(y / cellSize);
            cellCol = (int) Math.floor(x / cellSize);


            if (manager.getBoard().placeMove(cellRow, cellCol, manager.getCurrent().getId()) == 1) {
                movePlaced(manager.getBoardR(), manager.getBoardC(), manager.getCurrent().getId());
                //System.out.println(cellRow+"   "+cellCol+"  "+manager.getCurrent().getId());
                gui.getHistory().update(cellRow,cellCol,manager.getCurrent().getId());
                manager.setCurrentPlayer();
                infoPanel.setActivePlayer(manager.getCurrent().getId());

                if (manager.getCurrent().isAI()) {
                    Thread t1 = new Thread(new Worker());
                    t1.start();

                }


                if (manager.checkWin(manager.getCurrent().getId())) {
                    if (gui.getPlayerInfoPanel().getScoreIcon(manager.getCurrent().getId()).getScoreValue() >
                            gui.getPlayerInfoPanel().getScoreIcon(manager.getOther().getId()).getScoreValue())
                        manager.win(manager.getCurrent().getName());
                    else
                        manager.win(manager.getOther().getName());

                }
            }
        }
        manager.getBoard().removeAvailableMoves();
        updateBoard();
    }



    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    /**
     * Start an animation of a number of pieces being flipped over.
     *
     * @param startRow start row number
     * @param startCol start column number
     * @param endRow end row number
     * @param endCol end column number
     * @param start the color of the cells before flipping
     * @param end the color of the cells after flipping
     * @param milliseconds the time of the entire animation in milliseconds
     */
    public void animateFlipSequence(int startRow, int startCol,
                                    int endRow, int endCol,
                                    CellColor start, CellColor end,
                                    long milliseconds) {
        fAnimator = new FlipAnimator(startRow, startCol, endRow, endCol, start, end, milliseconds);
        fAnimator.start();
    }

    /**
     *  Updates urrent GUI board wiht our given Model board. If space is empty its set to empty
     *  if it correpsonds to a player it mathces given colors. Also updates current players move and whose turn
     *
     */
    public void makeBoard() {
        for(int r = 0; r < size; r++)
            for(int c = 0; c < size; c++)
                boardState[r][c].setColor(EMPTY);

        boardState[3][3].setColor(BLACK);
        boardState[3][4].setColor(WHITE);
        boardState[4][3].setColor(WHITE);
        boardState[4][4].setColor(BLACK);
        infoPanel.setScore(1,2);
        infoPanel.setScore(2,2);
        repaint();
    }

    public void movePlaced(int[] endR, int[] endC, int cp) {
        for(int i = 0; i < endR.length; i++) {
            if (cp == 1)
                animateFlipSequence(cellRow, cellCol, endR[i], endC[i], WHITE, BLACK, 150);
            else
                animateFlipSequence(cellRow, cellCol, endR[i], endC[i], BLACK, WHITE, 150);
        }


        infoPanel.setScore(1,manager.getBoard().score(1));
        infoPanel.setScore(2,manager.getBoard().score(2));
        repaint();
    }

    public void movePlaced(int startR, int startC, int[] endR, int[] endC) {
        for(int i = 0; i < endR.length; i++) {
            animateFlipSequence(startR, startC, endR[i], endC[i], BLACK, WHITE, 150);
        }
        infoPanel.setScore(1,manager.getBoard().score(1));
        infoPanel.setScore(2,manager.getBoard().score(2));
        repaint();
    }

    public void updateBoard(){
        for(int r = 0; r < size; r++)
            for(int c = 0; c < size; c++)
                if (manager.getBoard().spaceOwner(r,c) == 0)
                    boardState[r][c].setColor(EMPTY);
                else if (manager.getBoard().spaceOwner(r,c) == 2)
                    boardState[r][c].setColor(WHITE);
                else if (manager.getBoard().spaceOwner(r,c) == 1)
                    boardState[r][c].setColor(BLACK);
                else if (manager.getBoard().spaceOwner(r,c) == 3)
                    boardState[r][c].setColor(YELLOW);

        infoPanel.setScore(1,manager.getBoard().score(1));
        infoPanel.setScore(2,manager.getBoard().score(2));
        repaint();

    }

    public GameManager setManager(String name1,int diff){
        manager = new GameManager(gui,name1,diff);
        return manager;
    }

    public GameManager setManager(String name1, String name2){
        manager = new GameManager(gui,name1,name2);
        return manager;
    }

    public void setCanPlay(){canPlay = true;}

    private void startMessage(){
       // JPanel temp = gui.getBP();
       // JFrame message = new JFrame();
       // JLabel blah = new JLabel("Please load/start a game");
        // message.add(blah);
       // message.setVisible(true);
        JOptionPane.showMessageDialog(null, "Please Start a game!","Message", JOptionPane.ERROR_MESSAGE);
    }

    private class Worker implements Runnable {
        int a[];
        public void run(){
            try{
                Thread.sleep(3000);
            }
            catch(InterruptedException e){
                System.out.println("Interrupted");
            }
            a = manager.aiMakeTurn();
            movePlaced(a[0],a[1],manager.getBoardR(),manager.getBoardC());
            gui.getHistory().update(a[0],a[1],manager.getCurrent().getId());
            System.out.println(manager.getCurrent().getId());
            manager.setCurrentPlayer();
            infoPanel.setActivePlayer(manager.getCurrent().getId());
            if (manager.checkWin(manager.getCurrent().getId())) {
                if(gui.getPlayerInfoPanel().getScoreIcon(manager.getCurrent().getId()).getScoreValue() >
                        gui.getPlayerInfoPanel().getScoreIcon(manager.getOther().getId()).getScoreValue())
                    manager.win(manager.getCurrent().getName());
                else
                    manager.win(manager.getOther().getName());
            }

        }
    }
}

