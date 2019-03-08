package plu.yellow.reversi.Social;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by King7 on 4/23/17.
 */
public class Leaderboard {
    int size;
    private ArrayList<Account> lb;

    public Leaderboard(){
        lb = new ArrayList<Account>();
    }

    public Leaderboard(ArrayList<Account> leader){
        lb = leader;
    }
    public void addAcct(Account newAcct){
        lb.add(newAcct);
        //System.out.println("Inner added");
        size++;
    }

    public ArrayList<Account> getLb(){
        return lb;
    }

    public void sorter(){
        lbSort();
    }


    private void lbSort() {
        Account a,b;
        int i;
        //System.out.println(size);
        boolean swapped = false;
        for (i = 0; i < size-1; i++) {
            //System.out.println("Entered Loop");
            a = lb.get(i);
            b = lb.get(i+1);
            if (a.getRank() < b.getRank()) {
                lb.remove(i);
                lb.add(i+1,a);
                swapped = true;
            }
        }
            if(!swapped)
                return;
        else
            //System.out.println(lb);
            lbSort();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < lb.size();i++){
            sb.append("Acct "+i+": "+lb.get(i).getRank()+"\n");
        }
        return sb.toString();
    }
}
