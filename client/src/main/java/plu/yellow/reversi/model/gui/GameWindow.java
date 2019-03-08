package plu.yellow.reversi.model.gui;

import plu.yellow.reversi.model.Board;

import javax.swing.*;
import java.awt.*;

/**
 * The main game window, contains all of the UI.
 */
public class GameWindow extends JFrame {
    //Box holding new game start
    private StartSetup StartSetup;

    /** The board holding the actual gamestate */
    private Board model;

    /** The panel containing the board */
    private BoardView boardView;

    /** The panel containing the player's names and scores */
    private PlayerInfoPanel playerInfoPanel;

    /** The panel containing the game history */
    private GameHistoryPanel historyPanel;

    private JPanel BP;

    /**
     * Constructs a new main window for the game.
     */
    public GameWindow(Board b)
    {
        model = b;

        setTitle("Reversi");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Other panels
        playerInfoPanel = new PlayerInfoPanel();
        boardView = new BoardView(8, playerInfoPanel,this);

        //Start Box
        StartSetup = new StartSetup();


        // Add the menu bar
        this.setJMenuBar(new ReversiMenuBar(this));

        // The panel that holds the BoardView and the PlayerInfoPanel
        JPanel boardPanel = new JPanel(new BorderLayout(0, 0));


        // This panel will preserve the aspect ratio of the component within it
        JPanel preserveAspectPanel = new JPanel(new PreserveAspectRatioLayout() );

        // The board and edges
        JPanel boardAndEdges = new JPanel( new BorderLayout() );



        boardAndEdges.add(BoardEdges.createTopPanel(8), BorderLayout.NORTH);
        boardAndEdges.add(BoardEdges.createLeftPanel(8), BorderLayout.WEST);
        boardAndEdges.add(boardView, BorderLayout.CENTER);
        boardAndEdges.setBorder(BorderFactory.createMatteBorder(0,0,BoardEdges.EDGE_HEIGHT,
                BoardEdges.EDGE_WIDTH,BoardEdges.BACKGROUND_COLOR));
        preserveAspectPanel.add(boardAndEdges);


        boardPanel.add(preserveAspectPanel, BorderLayout.CENTER);
        boardPanel.add(playerInfoPanel, BorderLayout.NORTH);
        BP = boardPanel;

        historyPanel = new GameHistoryPanel();

        // History panel goes in the EAST
        this.add(historyPanel, BorderLayout.EAST);

        // The board panel goes in the center
        this.add(boardPanel, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
        NewGameSetup ngs = new NewGameSetup(this);

    }

    public JPanel getBP(){
        return BP;
    }


    /**
     * Returns the PlayerInfoPanel
     * @return the PlayerInfoPanel
     */
    public PlayerInfoPanel getPlayerInfoPanel() { return playerInfoPanel; }

    /**
     * @return the BoardView
     */
    public BoardView getBoardView() { return boardView; }

    /**
     * @return the board the gui is representing
     */
    public Board getBoard() { return model;}

   public GameHistoryPanel getHistory(){return historyPanel;}
}
