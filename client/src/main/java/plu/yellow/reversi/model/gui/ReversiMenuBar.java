package plu.yellow.reversi.model.gui;

import plu.yellow.reversi.Social.TestUserDB;
import plu.yellow.reversi.Social.UserDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * The main menu bar.
 */
public class ReversiMenuBar extends JMenuBar implements ActionListener {

    /**
     * The GameWindow
     */
    private GameWindow gui;
    private TestUserDB db;
    private UserDB testObj;

    /**
     * Quit item
     */
    private JMenuItem quitMenuItem;
    private JMenuItem undoMenuItem;
    private JMenuItem newGameMenuItem;
    private JMenuItem rulesMenuItem;
    private JMenuItem leaderboardMenuItem;
    private JMenuItem availableMovesMenuItem;
    private JMenuItem surrenderMenuItem;
    private JMenuItem saveGameMenuItem;
    private JMenuItem loadGameMenuItem;
    private JMenuItem login;
    private JMenuItem online;
    private JMenuItem reg;

    private Scanner in=new Scanner(System.in);
    private NewGameSetup ngs;

    /**
     * Constructs the menu bar
     *
     * @param gui the main GameWindow
     */
    public ReversiMenuBar(GameWindow gui) {
        this.gui = gui;

        // Build the "Game" menu
        this.add(buildGameMenu());

        //Build the "Help" Menu
        this.add(helpMenu());

        //Build the Social menu
        this.add(socialMenu());

        // Add the developer menu.  This should be removed when
        // the game is released
        this.add(new DeveloperMenu(gui));
        testObj = new UserDB();
    }

    private JMenu buildGameMenu() {
        JMenu menu = new JMenu("Game");
        menu.getAccessibleContext().setAccessibleDescription(
                "New game");

        newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.META_MASK));
        newGameMenuItem.getAccessibleContext().setAccessibleDescription(
                "Start a new game against the computer");
        newGameMenuItem.addActionListener(this);
        menu.add(newGameMenuItem);

        menu.addSeparator();
        saveGameMenuItem= new JMenuItem("Save Game");
        saveGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.META_MASK));
        saveGameMenuItem.getAccessibleContext().setAccessibleDescription("Save current game state.");
        saveGameMenuItem.addActionListener(this);
        menu.add(saveGameMenuItem);

        menu.addSeparator();
        loadGameMenuItem= new JMenuItem("Load Game");
        loadGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.META_MASK));
        loadGameMenuItem.getAccessibleContext().setAccessibleDescription("Load last game state.");
        loadGameMenuItem.addActionListener(this);
        menu.add(loadGameMenuItem);

        menu.addSeparator();
        surrenderMenuItem= new JMenuItem("Surrender");
        surrenderMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.META_MASK));
        surrenderMenuItem.getAccessibleContext().setAccessibleDescription("Surrender to opponent.");
        surrenderMenuItem.addActionListener(this);
        menu.add(surrenderMenuItem);

       menu.addSeparator();
       quitMenuItem = new JMenuItem("Quit");
       quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.META_MASK));
       quitMenuItem.getAccessibleContext().setAccessibleDescription("Exit Reversi.");
       quitMenuItem.addActionListener(this);
       menu.add(quitMenuItem);

        return menu;
    }

    //Add helper method that reads rules and calls an action listening
    private JMenu helpMenu() {
        JMenu menu = new JMenu("Help");
        availableMovesMenuItem = new JMenuItem("Available Moves");
        availableMovesMenuItem.addActionListener(this);
        menu.add(availableMovesMenuItem);

        undoMenuItem = new JMenuItem("Undo move");
        undoMenuItem.addActionListener(this);
        menu.add(undoMenuItem);

        rulesMenuItem = new JMenuItem("Game Rules");
        rulesMenuItem.addActionListener(this);
        menu.add(rulesMenuItem);
        return menu;
    }

    private JMenu socialMenu() {
        JMenu menu = new JMenu("Social");

         login = new JMenuItem("login");
        login.addActionListener(this);
        menu.add(login);

         online = new JMenuItem("show online user");
        online.addActionListener(this);
        menu.add(online);

        leaderboardMenuItem = new JMenuItem("Leaderboard");
        leaderboardMenuItem.addActionListener(this);
        menu.add(leaderboardMenuItem);

         reg=new JMenuItem(("create account"));
        reg.addActionListener(this);
        menu.add(reg);

        return menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quitMenuItem) {

            System.exit(0);
        }
        if(e.getSource()==surrenderMenuItem){
            gui.getBoard().surrender();
            gui.getBoardView().updateBoard();
        }

        if(e.getSource()==saveGameMenuItem){
            try {
                gui.getBoard().saveGame();
                gui.getBoard().reset();
                gui.getBoardView().updateBoard();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        if(e.getSource()==loadGameMenuItem){
            try {
                gui.getBoard().loadGame();
                gui.getBoardView().updateBoard();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }




        if((e.getSource()) ==  newGameMenuItem) {
            ngs = new NewGameSetup(gui);
        }
        if (e.getSource() == rulesMenuItem) {
            JFrame frame = new JFrame();
            frame.setLayout(new BorderLayout());
            frame.setSize(500, 500);
            frame.setTitle("Game Rules");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JTextArea text = new JTextArea();
            text.setWrapStyleWord(true);
            text.setLineWrap(true);
            String line;

            File file = new File("Rules.txt");
            try {
                Scanner infile = new Scanner(file);
                while (infile.hasNext()) {
                    line = infile.nextLine();
                    text.append(line+"\n");
                }
                infile.close();
                JScrollPane scroll = new JScrollPane(text);
                scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                frame.add(scroll, BorderLayout.CENTER);

                frame.setVisible(true);

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

        }
        if(e.getSource() == availableMovesMenuItem) {
            int p = ngs.getManager().getCurrent().getId();
            System.out.println("before method called");
            gui.getBoard().availableMoves(p);
            gui.getBoardView().updateBoard();
        }

        if(e.getSource() == this.leaderboardMenuItem) {

            this.testObj.openDB();
           ResultSet rs2 = this.testObj.leaderBoard();
           JFrame frame11 = new JFrame();
            frame11.setLayout(new BorderLayout());
            frame11.setSize(500, 500);
            frame11.setTitle("Leaderboard");
            frame11.setDefaultCloseOperation(2);
           JTextArea text11 = new JTextArea();
            text11.setWrapStyleWord(true);
            text11.setLineWrap(true);

            try {
                text11.append("username      score");
                text11.append("\n");

                while(rs2.next()) {
                    text11.append(rs2.getString(1) + "  ,           " + rs2.getString(2) + "\n");
                }

                JScrollPane e11 = new JScrollPane(text11);
                e11.setVerticalScrollBarPolicy(22);
                frame11.add(e11, "Center");
                frame11.setVisible(true);
            } catch (SQLException var11) {
                var11.printStackTrace();
            }

            this.testObj.closeDB();
        }

        final JTextField frame12;
        final JTextField text12;
        JButton go1;
        if(e.getSource() == this.login) {
            this.testObj.openDB();
            JFrame rs = new JFrame();
            frame12 = new JTextField("username");
            text12 = new JTextField("password");
            go1 = new JButton("login");
            frame12.setBounds(20, 100, 100, 25);
            text12.setBounds(20, 150, 100, 25);
            go1.setBounds(150, 120, 100, 25);
            rs.setLayout((LayoutManager)null);
            rs.add(frame12);
            rs.add(text12);
            rs.add(go1);
            go1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ReversiMenuBar.this.testObj.openDB();
                    boolean i = false;
                    String name = frame12.getText();
                    String pass = text12.getText();
                    ResultSet rsx = ReversiMenuBar.this.testObj.login(name, pass);

                    try {
                        if(rsx.next()) {
                            System.out.println("Login Successful");
                            int i1 = ReversiMenuBar.this.testObj.updateOnline(name);
                            ReversiMenuBar.this.testObj.closeDB();
                            rs.setVisible(false);
                        } else {
                            System.out.println("Wrong username or password");
                        }
                    } catch (SQLException var7) {
                        var7.printStackTrace();
                    }

                }
            });
            rs.setSize(900, 400);
            rs.setLocationRelativeTo((Component)null);
            rs.setVisible(true);
        }

        if(e.getSource() == this.reg) {
            JFrame rs = new JFrame();
            JTextField frame13 = new JTextField("username");
            JTextField text13 = new JTextField("password");
            go1 = new JButton("create account");
            frame13.setBounds(20, 100, 100, 25);
            text13.setBounds(20, 150, 100, 25);
            go1.setBounds(150, 120, 100, 25);
            rs.setLayout((LayoutManager)null);
            rs.add(frame13);
            rs.add(text13);
            rs.add(go1);
            go1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ReversiMenuBar.this.testObj.openDB();
                    boolean i = false;
                    String name = frame13.getText();
                    String pass = text13.getText();
                    ReversiMenuBar.this.testObj.openDB();
                    int i1 = ReversiMenuBar.this.testObj.reg(name, pass);
                    if(i1 == 1) {
                        System.out.println("account created");
                    } else {
                        System.out.println("username or password invalid, please try again");
                    }

                }
            });
            rs.setSize(900, 400);
            rs.setLocationRelativeTo((Component)null);
            rs.setVisible(true);
        }

        if(e.getSource() == this.online) {
            this.testObj.openDB();
            ResultSet rs2 = this.testObj.online();
            JFrame frame11 = new JFrame();
            frame11.setLayout(new BorderLayout());
            frame11.setSize(500, 500);
            frame11.setTitle("Leaderboard");
            frame11.setDefaultCloseOperation(2);
            JTextArea text11 = new JTextArea();
            text11.setWrapStyleWord(true);
            text11.setLineWrap(true);

            try {
                text11.append("username      score");
                text11.append("\n");

                while(rs2.next()) {
                    text11.append(rs2.getString(1) + "  ,           " + rs2.getString(2) + "\n");
                }

                JScrollPane e11 = new JScrollPane(text11);
                e11.setVerticalScrollBarPolicy(22);
                frame11.add(e11, "Center");
                frame11.setVisible(true);
                this.testObj.closeDB();
            } catch (SQLException var10) {
                var10.printStackTrace();
            }

        }

    }

    }


