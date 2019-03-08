package plu.yellow.reversi.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import plu.yellow.reversi.Social.Account;
import plu.yellow.reversi.Social.Leaderboard;


import java.util.ArrayList;

/**
 * Created by King7 on 4/23/17.
 */
public class ClientTests {
    private static Leaderboard lb = new Leaderboard();
    @BeforeClass
    public static void BuildLB(){
        for(int i = 0; i < 15; i++){
            Account acct = new Account("Poop", "Head");
            double a = Math.random()*101;
            acct.setRank((int)a);
            lb.addAcct(acct);
        }
    }

    @Test
    public void LeaderBoardTest1(){
        lb.sorter();
        System.out.println(lb.toString());
        Assert.assertEquals(15,lb.getLb().size());
    }

    @Test public void LeaderBoardTest2(){
        lb.sorter();
        boolean check = false;
        for(int i = 0; i < 14; i++){
            if(lb.getLb().get(i).getRank() > lb.getLb().get(i+1).getRank())
                check = true;
            else
                check = false;
        }
        Assert.assertEquals(true,check);
    }
}
