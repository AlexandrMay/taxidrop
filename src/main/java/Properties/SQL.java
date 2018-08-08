package Properties;


import java.sql.*;



public class SQL extends ReusableMethods {

    private static Connection getDBConnection() {

        String jdbcUrl = "jdbc:mysql://35.195.57.59/taxi_dev";

        Connection dbConnection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(jdbcUrl, "root", "OqLfv41B3wyAbwK");
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    public static void getData() throws SQLException {
        String selectTableSQL = "SELECT COUNT(*) FROM users";
        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // выбираем данные с БД
            ResultSet rs = statement.executeQuery(selectTableSQL);

            // И если что то было получено то цикл while сработает
            while (rs.next()) {
                int count= rs.getInt(1);

                System.out.println("count : " + count);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

    } finally {
        if (statement != null) {
            statement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }
    }






//    private static Statement stmt;
//    private static ResultSet rs;
//
//    public void passengersCount() throws ClassNotFoundException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection con;
//
//        int count;
//        try {
//        String query = "SELECT COUNT(*) FROM `users`";
//        con = DriverManager.getConnection("jdbc:mysql://35.233.96.167/taxi_dev","root", "OqLfv41B3wyAbwK");
//        stmt = con.createStatement();
//        rs = stmt.executeQuery(query);
//        while (rs.next()) {
//            count = rs.getInt(1);
//            System.out.println("Total number of users in the table : " + count);
//    }
//        } catch (SQLException sqlEx) {
//            sqlEx.printStackTrace();
//        } finally {
//            //close connection ,stmt and resultset here
//           // try { con.close(); } catch(SQLException se) { /*can't do anything */ }
//            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
//            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
//        }
//    }



}
