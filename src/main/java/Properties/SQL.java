package Properties;


import java.sql.*;



public class SQL extends ReusableMethods {



    private static final String URL = "jdbc:mysql://35.195.57.59";
    private static final String USER = "root";
    private static final String PASSWORD = "OqLfv41B3wyAbwK";

  //  private static Connection con = null;
    private static Statement stmt;
    private static ResultSet rs;

    public void passengersCount() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = null;

        int count;
        try {
        String query = "select count(*) from users";
        con = DriverManager.getConnection(URL, USER, PASSWORD);
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            count = rs.getInt(1);
            System.out.println("Total number of users in the table : " + count);
    }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }



}
