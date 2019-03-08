package plu.yellow.reversi.model.gui;



import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Carson on 5/14/2017.
 */
public class StartSetup extends JPanel implements ActionListener{
    private NewGameSetup ngs;

    JButton jbt1 = new JButton("New Game");
    //JButton jbt2 = new JButton("Exit");


    public StartSetup(){
        Box box = Box.createVerticalBox();
        box.add(jbt1);
        box.add(Box.createVerticalStrut(10));
        //box.add(jbt2, CENTER_ALIGNMENT);
        //box.add(Box.createVerticalStrut(10));


        add(box);
    }

    public static void createAndShowGui(){
        JFrame frame = new JFrame();
        frame.add(new StartSetup());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                createAndShowGui();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}


