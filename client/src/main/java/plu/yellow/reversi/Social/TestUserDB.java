package plu.yellow.reversi.Social;

/**
 * Created by boen zhang on 4/30/2017.
 */


        import java.sql.*;

        import java.util.Scanner;

public class TestUserDB {

    static UserDB testObj = new UserDB();

    static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        int flag = 0;

        while (flag == 0) {

            printOptions();

            flag = getChoice();

        }

    }

    public static void printOptions() {

        String case1 = "Case 1: view online user";

        String case2 = "Case 2: leader board";

        String case3 = "Case 3: null";

        String case4 = "Case 4: null";

        String case5 = "Case 5: null";

        String case6 = "Case 6: null";

        System.out.printf("\n0. Open Database\n1. %s\n2. %s\n3. %s\n4. %s\n5. %s\n6. %s\n7. Close DB and Quit\n",

                case1, case2, case3, case4, case5, case6);
        System.out.println("To test it, press 0 first, when finish, please press 7 to close DB. Thank you.");

    }

    public static int getChoice() throws SQLException {

        System.out.print("Enter choice: ");

        int choice = keyboard.nextInt();

        keyboard.nextLine(); // clear buffer

        switch (choice) {

            case 0:

                testObj.openDB();

                break;

            case 1:

                callOnline();

                break;

            case 2:

                callLeaderBoard();

                break;



            case 7:

                testObj.closeDB();
                System.out.println("bye");

                return 1;

        }

        return 0;

    }



    static void callOnline() throws SQLException {
        ResultSet rs;
        rs = testObj.online();
        System.out.println("View online user");
        System.out.println("***********************************");
        System.out.println("Username       Score");
        while (rs.next()) {
            System.out.println(rs.getString(1) + "  ,     " + rs.getString(2));
        }

    }
    public static void callLeaderBoard() throws SQLException {
        ResultSet rs;
        rs = testObj.leaderBoard();
        System.out.println("Leader Board");
        System.out.println("***********************************");
        System.out.println("Username       Score");
        while (rs.next()) {
            System.out.println(rs.getString(1) + "  ,     " + rs.getString(2));
        }

    }





}
