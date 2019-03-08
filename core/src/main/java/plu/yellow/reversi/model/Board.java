package plu.yellow.reversi.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;



public class Board {
    private Cell board[][];
    private int size;
    private int player;
    private int r,c;
    private Cell[] lastFlipped;

    private int check;

    public int getCurrentPlayer(){
        return player;
    }

    public void setCurrentPlayer(int p){
        player=p;
    }

    public void setR(int row){
        r=row;
    }
    public void setC(int col){
        c=col;
    }


    public Board(int size) {
        super();
        this.size = size;
        board = new Cell[size][size];
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                board[r][c] = new Cell(r, c);

        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                board[r][c].initialize();

        //create board starting position
        board[size / 2 - 1][size / 2 - 1].owner = 1;
        board[size / 2][size / 2 - 1].owner = 2;
        board[size / 2 - 1][size / 2].owner = 2;
        board[size / 2][size / 2].owner = 1;

    }

    public Board(Board other) {
        this(other.size);
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++) {
                board[r][c].owner = other.board[r][c].owner;
            }
    }


    /**
     * Clears the board and replaces all values with 0
     */
    public void clear() {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].owner = 0;
            }
    }

    /**
     * Resets the board, calls Clear() and wipes the board then replaces the beginning peices
     */
    public void reset() {
        clear();
        board[3][3].owner = 1;
        board[3][4].owner = 2;
        board[4][3].owner = 2;
        board[4][4].owner = 1;
    }

    /**
     * Display calls the helper method display, whihc is a to string method
     */
    public void display() {
        display(board);
    }

    /**
     * Helper method for Display, prints to screen a given board
     *
     * @param b passed in board to be printed
     */
    public void display(Cell[][] b) {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++)
                System.out.print(b[r][c].owner + " ");
            System.out.println();
        }

    }

    public boolean noSpace(int[][] b, int p) {
        //Checking no moves left game is over
        return false;
    }

    public void listMove(int p) {
        //eg: position (r,c) is available
    }

    public void saveGame() throws IOException{
        saveGame("game.txt");
    }

    public void saveGame(String fileName) throws IOException {

        StringBuilder builder = new StringBuilder();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                builder.append(board[r][c] + " ");

            }
            builder.append("\n");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(builder.toString());
        writer.close();

    }


    public void loadGame() throws IOException {
        Scanner scanFile = new Scanner(new File("game.txt"));
        for(int r=0; r<8;r++){
            for(int c=0; c<8; c++){
                board[r][c].owner = scanFile.nextInt();
            }
        }
        scanFile.close();

    }

    /**
     * once surrender is intiatd creates a new board
     */
    public void surrender() {
        reset();
    }


    /**
     * Gives a current score of the game
     *
     * @param p player for which we are checking the score
     * @return Score of given player
     */
    public int score(int p) {
        int player = p;
        int score = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].owner == p) {
                    score++;
                }
            }
        }
        return score;
    }

    /**
     * Places move is checkspace is true and flips pieces
     *
     * @param r row in the game matrix
     * @param c column in the game matrix
     * @param p Given player
     * @return Places move on board or says illegal
     */
    public int placeMove(int r, int c, int p) {
        int row = r;
        int col = c;
        int playr = p;
        if (checkSpace(row, col, playr)) {
            flip(row, col, playr);
            board[row][col].owner = playr;


        } else {
            System.out.println("Move is unavailable");
            return 0;
        }
        //display();
        return 1;
    }

    public boolean checkSpace(int r, int c, int pNum) {
        //check that space is an actual empty space
        if (spaceOwner(r, c) != 0 && spaceOwner(r,c) != 3)
            return false;
        //set each players board value
        int other = 0;
        if (pNum == 1)
            other = 2;
        if (pNum == 2)
            other = 1;
        //check for a legal move in each direction
        for (int i = 0; i < 8; i++) {
            if (checkInDirection(board[r][c], i, pNum, other)){
                setR(r);
                setC(c);
                setCurrentPlayer(pNum);
                return true;
            }

        }
        return false;
    }

    private boolean checkInDirection(Cell c, int dir, int pNum, int other) {

        if(cellOwner(c) != 0 && cellOwner(c) != 3)

            if (cellOwner(c) != 0)
                return false;
        // System.out.println("c at: " + c.row + ", " + c.col + ", dir " + dir);
        Cell temp = c.allNeighbors[dir];

        if (cellOwner(temp) == other) {
            //System.out.println("Checking " + dir);
            while (cellOwner(temp) == other)
                temp = temp.allNeighbors[dir];
            if (cellOwner(temp) == pNum)
                return true;
        }
        return false;

    }


    public int availableMoves(int playr) {
        int p = playr; // current player
        int count = 0;
        for(int row = 0; row<8; row++){
            for(int col = 0; col<8; col++) {
                if(board[row][col].owner == 0) {
                    if(checkSpace(row,col,p) == true){
                        count++;
                        board[row][col].owner = 3;
                    }
                }
            }
        }
        return count;
    }

    public void removeAvailableMoves() {

        for(int row = 0; row<8; row++){
            for(int col = 0; col<8; col++) {
                if(board[row][col].owner == 3) {
                    board[row][col].owner = 0;
                }

            }
        }
    }


    /**
     * Determine who is the owner of the current passed  space
     *
     * @param r row in the game matrix
     * @param c column in the game matrix
     * @return The owner of the current space, or -1 if its off the board
     */
    public int spaceOwner(int r, int c) {
        if (r >= 0 && r < size && c >= 0 && c < size)
            return board[r][c].owner;
        return -1;
    }

    public int cellOwner(Cell c) {
        if (c == null)
            return -1;
        return c.owner;
    }


    //only to be used on a space that is already known to be legal
    private Cell flipInDirection(Cell c, int dir, int pNum, int other) {
        Cell temp = c.allNeighbors[dir];
        Cell prev = c;
        while (cellOwner(temp) == other) {
            temp.owner = pNum;
            prev = temp;
            temp = temp.allNeighbors[dir];
        }
        return prev;
    }


    public void flip(int r, int c, int pNum) {
        //set each players board value
        int other = 0;
        if (pNum == 1)
            other = 2;
        if (pNum == 2)
            other = 1;
        //check for a legal move in each direction
        ArrayList<Cell> flipEnds = new ArrayList<Cell>();
        for (int i = 0; i < 8; i++) {
            if (checkInDirection(board[r][c], i, pNum, other)) {
                flipEnds.add(flipInDirection(board[r][c], i, pNum, other));
            }
        }
        lastFlipped = flipEnds.toArray(new Cell[1]);
    }


    //black is 1, white is 2, plane 0
    public static void start() {
        Board b = new Board(8);
        Scanner scan = new Scanner(System.in);
        int r, c;
        int p1 = 0;
        int p2 = 1;
        int count = 0;

        b.display();

    }//end start()

    public int[] getEndR() {
        int[] endR = new int[lastFlipped.length];
        for(int i = 0; i < lastFlipped.length; i++)
            endR[i] = lastFlipped[i].row;
        return endR;
    }

    public int[] getEndC() {
        int[] endC = new int[lastFlipped.length];
        for(int i = 0; i < lastFlipped.length; i++)
            endC[i] = lastFlipped[i].col;
        return endC;
    }

    public static void main (String[]args){
        Board b = new Board(8);
        b.start();


    } //end main



    private class Cell {
        private int row, col;
        private Cell N, NW, W, SW, S, SE, E, NE;
        private Cell[] allNeighbors;
        private int owner;

        public Cell(int r, int c) {
            row = r;
            col = c;
            owner = 0;

            allNeighbors = new Cell[8];
        }

        //This method must run on each cell for the neighbor functionalitiy to work
        public void initialize() {
            //set all references to neighbors
            if (row < size - 1)
                N = board[row + 1][col];
            if (row > 0)
                S = board[row - 1][col];
            if (col < size - 1)
                W = board[row][col + 1];
            if (col > 0)
                E = board[row][col - 1];

            if (row < size - 1 && col < size - 1)
                NW = board[row + 1][col + 1];
            if (row < size - 1 && col > 0)
                NE = board[row + 1][col - 1];
            if (row > 0 && col < size - 1)
                SW = board[row - 1][col + 1];
            if (row > 0 && col > 0)
                SE = board[row - 1][col - 1];

            allNeighbors[0] = N;
            allNeighbors[1] = NW;
            allNeighbors[2] = W;
            allNeighbors[3] = SW;
            allNeighbors[4] = S;
            allNeighbors[5] = SE;
            allNeighbors[6] = E;
            allNeighbors[7] = NE;

        }


        @Override
        public String toString() {
            return "" + owner ;
        }

    }
}
