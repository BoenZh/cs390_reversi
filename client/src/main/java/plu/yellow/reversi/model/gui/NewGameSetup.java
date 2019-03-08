package plu.yellow.reversi.model.gui;

import plu.yellow.reversi.ai.ReversiAI;
import plu.yellow.reversi.model.GameManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

/**
 * Created by King7 on 3/20/17.
 */
public class NewGameSetup extends JFrame implements ActionListener{
    /**
     * The GameWindow
     */
    private GameWindow gui;


    /**
     * Menu Objects for P1 Name
     */
    private JPanel name1Panel;
    private JTextField name1;
    private JLabel label1;

    /**
     * Slider for difficulty of AI
     */
    private JSlider difSlider;
    private int diff;

    /**
     * GameManager class to increment score and implement AI moves
     */
    private GameManager manager;

    /**
     * Menu Objects for P2 Name
     */
    private JPanel name2Panel;
    private JTextField name2;
    private JLabel label2;

    private JButton close;
    private int check;
    private Dimension textDim = new Dimension(100,50);


    public NewGameSetup(){this.setLayout(new BorderLayout());
        this.setSize(200,200);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //Set panel for Player 1 name
        JPanel par1 = new JPanel(new PreserveAspectRatioLayout());
        name1Panel = new JPanel();
        label1 = new JLabel("Player 1:");
        name1 = new JTextField(10);
        //name1.setPreferredSize(textDim);
        name1Panel.add(label1,BorderLayout.WEST);
        name1Panel.add(name1,BorderLayout.CENTER);
        par1.add(name1Panel);

        //Set panel for player 2 name
        JPanel par2 = new JPanel(new PreserveAspectRatioLayout());
        name2Panel = new JPanel();
        label2 = new JLabel("Player 2:");
        name2 = new JTextField(10);
        //name2.setPreferredSize(textDim);
        name2Panel.add(label2,BorderLayout.WEST);
        name2Panel.add(name2,BorderLayout.CENTER);
        par2.add(name2Panel);

        close = new JButton("Enter");
        close.addActionListener(this);

        this.add(par1,BorderLayout.WEST);
        this.add(par2,BorderLayout.EAST);
        this.add(close,BorderLayout.SOUTH);
        this.add(buildDropDown(),BorderLayout.NORTH);
        this.setVisible(true);
    }

    public NewGameSetup(GameWindow g){
        this.gui = g;
        this.setLayout(new BorderLayout());
        this.setSize(400,200);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildSlider();

        //Set panel for Player 1 name
        JPanel par1 = new JPanel(new PreserveAspectRatioLayout());
        name1Panel = new JPanel();
        label1 = new JLabel("Player 1:");
        name1 = new JTextField(10);
        //name1.setPreferredSize(textDim);
        name1Panel.add(label1,BorderLayout.WEST);
        name1Panel.add(name1,BorderLayout.CENTER);
        par1.add(name1Panel);

        //Set panel for Player 2 name
        JPanel par2 = new JPanel(new PreserveAspectRatioLayout());
        name2Panel = new JPanel();
        label2 = new JLabel("Player 2:");
        name2 = new JTextField(10);
        //name2.setPreferredSize(textDim);
        name2Panel.add(label2,BorderLayout.WEST);
        name2Panel.add(name2,BorderLayout.CENTER);
        par2.add(name2Panel);

        close = new JButton("Enter");
        close.addActionListener(this);

        name2Panel.add(difSlider,BorderLayout.SOUTH);
        difSlider.setVisible(false);

        this.add(par1,BorderLayout.WEST);
        this.add(par2,BorderLayout.EAST);
        this.add(close,BorderLayout.SOUTH);
        this.add(buildDropDown(),BorderLayout.NORTH);
        this.setVisible(true);




    }

    private JPanel buildDropDown(){
        JPanel a = new JPanel();
        JLabel gameType = new JLabel("Choose Game Type: ");
        JComboBox dd = new JComboBox();
        dd.addItem("<Choose Game Type>");
        dd.addItem("Vs. AI");
        dd.addItem("Vs. Local Player");
        dd.addItem("Vs. Online Player");
        dd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            JComboBox<String> answer = (JComboBox<String>)e.getSource();
                String result = (String)answer.getSelectedItem();
                if(result.equals("Vs. AI")) {
                    name2.setVisible(false);
                    difSlider.setVisible(true);
                    label2.setText("Select an AI Difficulty");
                    check = 1;
                    System.out.println("AI Selected");
                }
                else if(result.equals("Vs. Local Player")){
                    name2Panel.setVisible(true);
                    check = 2;
                    System.out.println("Local Player");
                }
                else if(result.equals("Vs. Online Player")) {
                    name2Panel.setVisible(false);
                    check =3;
                    System.out.println("Online Player");
                }
            }
        });
        a.setLayout(new BorderLayout());
        a.add(gameType,BorderLayout.WEST);
        a.add(dd,BorderLayout.CENTER);

        return a;
    }

    public static void main(String[] args){
        NewGameSetup ngs = new NewGameSetup();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == close){
            if(check ==1) {
                try {
                    manager = gui.getBoardView().setManager(name1.getText(),diff);
                    ReversiAI b = (ReversiAI)manager.getP(2);
                    System.out.println("AI Difficulty is: "+ b.getDifficulty());
                    gui.getBoardView().setCanPlay();
                    setBoard();
                } catch (NullPointerException j) {
                    System.out.println("Exception Caught");
                }
            }
            else if(check ==2){
                manager = gui.getBoardView().setManager(name1.getText(),name2.getText());
                manager.setPlayer(1,name1.getText());
                manager.setPlayer(2,name2.getText());
                gui.getBoardView().setCanPlay();
                setBoard();
            }
            else if(check == 3){
                //Need to fix this for online play, will play with an AI right now
                manager = gui.getBoardView().setManager(name1.getText(),diff);
                manager.setPlayer(1,name1.getText());
                manager.setPlayer(2,"Online Player");
                gui.getBoardView().setCanPlay();
                setBoard();
            }
            this.dispose();
    }
    }

    public GameManager getManager(){
        return manager;
    }


    /**
     * Helps readability for actionPerformed Class
     */
    private void setBoard(){
        gui.getPlayerInfoPanel().setPlayerName(1, manager.getP(1).getName());
        gui.getPlayerInfoPanel().setPlayerName(2, manager.getP(2).getName());
        gui.getPlayerInfoPanel().setActivePlayer(1);
        manager.setCurrentPlayer(1);
        gui.getBoard().reset();
        gui.getBoardView().updateBoard();

    }

    private void buildSlider(){
        difSlider = new JSlider(0, 4, SwingConstants.HORIZONTAL);
        difSlider.setMajorTickSpacing(1);
        difSlider.setPaintTicks(true);
        Hashtable<Integer, JLabel> labelTable = new Hashtable();
        labelTable.put(0, new JLabel("Easy"));
        labelTable.put(2, new JLabel("Medium"));
        labelTable.put(4, new JLabel("Hard"));
        difSlider.setLabelTable(labelTable);
        difSlider.setPaintLabels(true);

        difSlider.addChangeListener(new SliderListener());
    }

   class SliderListener implements ChangeListener{
       @Override
       public void stateChanged(ChangeEvent e) {
           JSlider source = (JSlider)e.getSource();
           if(!source.getValueIsAdjusting())
                diff = source.getValue();
       }
   }

}
