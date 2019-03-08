package plu.yellow.reversi.model.gui;

import plu.yellow.reversi.model.GameManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

/**
 * This panel displays the history of moves that have taken place
 * througout the game.  It should be updated after every move.
 *
 * TODO:  This currently uses an internal class called Move, which really
 * doesn't belong here.  I suggest moving that over to a model subsystem
 * because it is something many subsystems might be interested in.
 */
public class GameHistoryPanel extends JPanel {

    private JTable historyTable;

    //private ArrayList<Move> history=new ArrayList<Move>();;
    //private Board model;
    private GameManager manager;
    private Object[] row = new Object[4];
    private int i=1;
    private DefaultTableModel model = new DefaultTableModel();


    /*
     *  This is just an example table model to get you started.  It isn't
     *  complete and will need to be updated.
     *
     *  TODO: Update this to work with the rest of the system.

    private class ExampleTableModel extends AbstractTableModel {
        @Override
        public String getColumnName(int column) {
            if( column == 0 ) return "#";
            else if( column == 1 ) return "X";
            else if( column == 2 ) return "Y";
            else if( column == 3 ) return "Player";
            return "";
        }

        @Override
        public int getRowCount() {
            return history.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            //if( rowIndex >= history.size() ) return null;


            Move m = history.get(rowIndex);
            if( columnIndex == 0 ) return "" + (rowIndex + 1);
            else if( columnIndex == 1) return m.r;
            else if( columnIndex == 2) return m.c;
            else if( columnIndex == 3) {
                return m.player;
               /*
                if( m.player == 1 ) return "Player 1";
                else if(m.player == 2 ) return "Player 2";
                else return "";
                */
          //  }
        //    return null;
      //  }
    //}*/

    /*
     * This is a temporary class, just for demo purposes.  This really belongs in the model,
     * not in the GUI.  You should remove this and implement it in the model.
     *
     * TODO: Probably belongs in the model system.

    private class Move {
    private int r,c;
    private int player;
        public Move(int r,int c) {
            this.r=r;
            this.c=c;
        }
        public Move(int p){
            player=p;
        }
    }
    */

    /**
     * Construct a new history panel.  Currently, this places some example
     * history into the panel.  This should be removed.
     *
     * TODO: Implement "real" history
     */
    public GameHistoryPanel() {

        // Fill the history list with some arbitrary example data.
        // This should be stored in the model, not here.
        // TODO: move this to the model
/*
        history.add( new Move(4,4));
        history.add( new Move(5,4));
        history.add( new Move(4,5));
        history.add( new Move(5,4));
*/
        Object[] columns = {"#","X","Y","player"};


        model.setColumnIdentifiers(columns);
        row[0]=i;
        row[1]=4;
        row[2]=4;
        row[3]=2;
        i++;
        model.addRow(row);
        row[0]=i;
        row[1]=5;
        row[2]=4;
        row[3]=1;
        i++;
        model.addRow(row);
        row[0]=i;
        row[1]=4;
        row[2]=5;
        row[3]=2;
        i++;
        model.addRow(row);
        row[0]=i;
        row[1]=4;
        row[2]=4;
        row[3]=1;
        i++;
        model.addRow(row);




        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.setLayout(new BorderLayout());
        historyTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setPreferredSize(new Dimension(250,0));
        historyTable.setGridColor(new Color(220,220,220));
        historyTable.setShowGrid(true);
        historyTable.setFillsViewportHeight(true);

        TableColumnModel cmod = historyTable.getColumnModel();
        cmod.getColumn(0).setPreferredWidth(20);
        cmod.getColumn(1).setPreferredWidth(20);
        historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        JPanel borderPanel = new JPanel(new GridLayout(1,0));
        borderPanel.setBorder(BorderFactory.createTitledBorder("History"));
        borderPanel.add(scrollPane);

        this.add(borderPanel, BorderLayout.CENTER);


    }

    public void update(int r,int c,int p){
        row[0]=i;
        row[1]=r+1;
        row[2]=c+1;
        row[3]=p;
        i++;
        model.addRow(row);

    }


}
