package plu.yellow.reversi.model;

/**
 * Created by King7 on 3/27/17.
 */
import plu.yellow.reversi.model.gui.GameWindow;
import plu.yellow.reversi.ai.ReversiAI;
import plu.yellow.reversi.model.gui.NewGameSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameManager {
    /**
     * The GameWindow object
     */
    private GameWindow gui;


    private AbstractPerson p1;
    private AbstractPerson p2;
    private ReversiAI ai;
    private AbstractPerson currentPlayer;
    private int check;

    /**
     * Initializes fields
     * @param m The Current instance of Board
     */

    public GameManager(GameWindow m, String name1, int diff){
        gui = m;
        p1 = new Person(1,name1);
        p2 = new ReversiAI(diff);
        p2.setName("AI");
        ai = (ReversiAI)p2;
        setCurrentPlayer(1);
    }

    public GameManager(GameWindow m, String name1, String name2){
        gui = m;
        p1 = new Person(1,name1);
        p2 = new Person(2,name2);
        setCurrentPlayer(1);
    }

    public AbstractPerson getOther(){
        if(currentPlayer.getId() == 1)
            return p2;
        else
            return p1;
    }

    public int[] aiMakeTurn(){
        return ai.playBestMove(gui.getBoard(), ai.getDifficulty());
    }
    public AbstractPerson getP(int i){
        if(i == 1)
            return p1;
        else if(i == 2)
            return p2;
        else
            throw new NullPointerException("GAME MANAGER ERROR");
    }
    /**
     *  Instantiates player objects with given player IDnumbers and passed in given names
     *
     *  @param    i       player 1 or 2
     *  @param    name    name from player
     */
    public void setPlayer (int i, String name){
        if(i == 1)
            p1 = new Person(1, name);
        else if(i == 2)
            p2 = new Person(2,name);
        check = 2;
    } //end setPlayers

    /**
     * Sets the current player to correspondingp layer value.
     */
    public void setCurrentPlayer() {
        if (currentPlayer.getId() == 1)
            currentPlayer = p2;
        else
            currentPlayer = p1;
    } //end setCurrentPlayer

    public void setCurrentPlayer(int i){
        currentPlayer = p1;

    }

    public int[] getBoardR() {
        return gui.getBoard().getEndR();
    }

    public int[] getBoardC(){return gui.getBoard().getEndC();}

    public Board getBoard(){return gui.getBoard();}

    /**
     * Checks certain conditions on the board to check if the game is over.
     * @return the win condition of the board
     */
    public boolean checkWin(int plr){
        int other = 0;
        if(plr == 1)
            other = 2;
        else
            other = 1;
        if(gui.getBoard().availableMoves(plr) == 0) {
            System.out.println("Game Over. Player " + plr);
            return true;
        }
        return false;
    }


    public void win(String plr) {
        /*JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setSize(new Dimension(200,64));
        panel.setLayout(null);

        JLabel label = new JLabel(plr + " has won!");
        label.setFont(new Font("Arial", Font.BOLD,14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        */
        JOptionPane.showMessageDialog(null, plr + " has won! Start a new game to play again.","Game Over!", JOptionPane.PLAIN_MESSAGE);


    }


    /**
     *  Gets the current player object
     *
     *  @return the current Player Object
     */
    public AbstractPerson getCurrent() {

        return currentPlayer;
    } //end getCurrent


}
