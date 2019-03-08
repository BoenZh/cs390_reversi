
package plu.yellow.reversi.test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import plu.yellow.reversi.model.Board;

import java.io.File;
import java.io.IOException;

/**
 * Created by boen zhang on 3/16/2017.

public class BoardTest {

    @Test
    public void testScore(){

        Board b = new Board(8);
        b.start();

        assertEquals(2,b.score(1));
        assertEquals(2,b.score(2));
    }

    @Test
    public void testPlaceMove(){
        Board b = new Board(8);
        b.start();
        b.display();
        b.placeMove(5,3,1);
        System.out.println();
        b.display();
        b.placeMove(3,2,2);
        System.out.println();
        b.display();
        b.placeMove(2,1,1);
        System.out.println();
        b.display();
        System.out.println("next should be invaild");
        b.placeMove(1,0,2);
        System.out.println();
        b.display();
        b.placeMove(5,5,2);
        System.out.println();
        b.display();
        b.placeMove(3,5,1);
        System.out.println();
        b.display();






        assertEquals(8,b.score(1));
        assertEquals(1,b.score(2));


    }

    @Test
    public void checkSpace(){
        Board b = new Board(8);
        b.start();
        b.placeMove(0,0,1);
        assertEquals(2,b.score(1));
        assertEquals(2,b.score(2));

    }

    @Test
    public void TestCheckSpace(){
        Board b = new Board(8);
        b.start();
        assertEquals(true,b.checkSpace(5,3,1));
        assertEquals(false,b.checkSpace(0,0,1));
        assertEquals(false,b.checkSpace(5,3,2));
        b.placeMove(5,3,1);
        assertEquals(true,b.checkSpace(5,2,2));

    }

    @Test
    public void testSaveGame() throws IOException {
        Board b = new Board(8);
        b.start();
        b.placeMove(5,3,1);
        System.out.println();
        b.placeMove(3,2,2);
        System.out.println();
        b.display();
        b.saveGame();
        File temp = new File("game.txt");
        assertTrue(temp.exists());
    }

    @Test
    public void testLoadGame() throws IOException {
        Board b = new Board(8);
        b.start();
        b.placeMove(5,3,1);
        b.placeMove(3,2,2);
        b.saveGame();
        b.loadGame();

       // File res = new File("game.txt");
        //assertTrue(res.length()>0);
        //assertTrue((res.getAbsoluteFile().exists()));
    }
}
package plu.yellow.reversi.test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import plu.yellow.reversi.model.Board;

import java.io.File;
import java.io.IOException;
*/
/**
 * Created by boen zhang on 3/16/2017.
 */
public class BoardTest {

    @Test
    public void testScore(){

        Board b = new Board(8);
        b.start();

        assertEquals(2,b.score(1));
        assertEquals(2,b.score(2));
    }

    @Test
    public void testPlaceMove(){
        Board b = new Board(8);
        b.start();
        b.display();
        b.placeMove(5,3,1);
        System.out.println();
        b.display();
        b.placeMove(3,2,2);
        System.out.println();
        b.display();
        b.placeMove(2,1,1);
        System.out.println();
        b.display();
        System.out.println("next should be invaild");
        b.placeMove(1,0,2);
        System.out.println();
        b.display();
        b.placeMove(5,5,2);
        System.out.println();
        b.display();
        b.placeMove(3,5,1);
        System.out.println();
        b.display();






        assertEquals(8,b.score(1));
        assertEquals(1,b.score(2));


    }

    @Test
    public void checkSpace(){
        Board b = new Board(8);
        b.start();
        b.placeMove(0,0,1);
        assertEquals(2,b.score(1));
        assertEquals(2,b.score(2));

    }

    @Test
    public void TestCheckSpace(){
        Board b = new Board(8);
        b.start();
        assertEquals(true,b.checkSpace(5,3,1));
        assertEquals(false,b.checkSpace(0,0,1));
        assertEquals(false,b.checkSpace(5,3,2));
        b.placeMove(5,3,1);
        assertEquals(true,b.checkSpace(5,2,2));

    }

    @Test
    public void testSaveGame() throws IOException {
        Board b = new Board(8);
        b.start();
        b.placeMove(5,3,1);
        System.out.println();
        b.placeMove(3,2,2);
        System.out.println();
        b.display();
        b.saveGame();
        File temp = new File("game.txt");
        assertTrue(temp.exists());
    }

    @Test
    public void testLoadGame() throws IOException {
        Board b = new Board(8);
        b.start();
        b.placeMove(5,3,1);
        b.placeMove(3,2,2);
        b.saveGame();
        b.loadGame();

       // File res = new File("game.txt");
        //assertTrue(res.length()>0);
        //assertTrue((res.getAbsoluteFile().exists()));
    }
}
