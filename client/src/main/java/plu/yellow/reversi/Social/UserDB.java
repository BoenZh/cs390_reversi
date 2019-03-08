package plu.yellow.reversi.Social;

/**
 * Created by boen zhang on 4/30/2017.
 */
import java.sql.*;

public class UserDB {


    private Connection conn = null;

    /**
     * The default constructor will load and register the MySQL driver
     */
    public UserDB() {
        super();
        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load driver.");
        }
    }

    public void openDB() {

        // Connect to the database
        String username = "ef367";
        String password = "P1K4CHU";

        String url = "jdbc:mysql://mal.cs.plu.edu:3306/company367_2017";

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.toString());
        }
    }

    public void closeDB() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing database: " + e.toString());
        }

    }


    /**
     * the method for executing Find online user
     *
     * @return the set of online username and score
     */
    public ResultSet online() {
        String sql = null;
        ResultSet rset = null;

        try {

            sql = "SELECT    username, score " +

                    "FROM    temp " +

                    "WHERE   online='T' " +
                    "GROUP BY username ";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //pstmt.clearParameters();


            rset = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("createStatement " + e.getMessage() + sql);
        }

        return rset;

    }

    /**
     *
     * @return
     */
    public ResultSet leaderBoard() {
        String sql = null;
        ResultSet rset = null;

        try {

            sql = "SELECT   username, score " +

                    "FROM    temp "+

                    "order by score DESC ";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //pstmt.clearParameters();


            rset = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("createStatement " + e.getMessage() + sql);
        }

        return rset;

    }

    public ResultSet login(String name,String pass) {

        String sql=null;
        ResultSet rset=null;


        try {
            // create a Statement and an SQL string for the statement

            sql = "SELECT *  FROM temp "

                    + "WHERE username = ? and password= ? " ;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.clearParameters();
            pstmt.setString(1, name);
            pstmt.setString(2, pass);

            rset = pstmt.executeQuery();


        } catch (SQLException e) {
            System.out.println("createStatement " + e.getMessage() + sql);
        }

        return rset;

    }

    public int updateOnline(String name){
        String sql = null;
        int i=0;
        try {
            // create a Statement and an SQL string for the statement
            sql = " update temp "+

                    " set online='T' "
                    + " WHERE Username = ? " ;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.clearParameters();
            pstmt.setString(1, name);


            i=pstmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("createStatement " + e.getMessage() + sql);
        }
        return i;

    }

    public int reg(String name, String pass){
        int i=0;
        String sql=null;

        try {
            // create a Statement and an SQL string for the statement

            sql = "insert into temp " +

                    "values( ? ,?,0,'F') ";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.clearParameters();
            pstmt.setString(1, name);
            pstmt.setString(2, pass);


            i=pstmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("createStatement " + e.getMessage() + sql);
        }

        return i;
    }

}