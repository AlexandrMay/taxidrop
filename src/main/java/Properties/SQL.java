package Properties;


import java.sql.*;
import java.util.ArrayList;


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

    public void setData(String request) throws SQLException {
        String selectTableSQL = request;
        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(selectTableSQL);

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


    public int getCountData(String request) throws SQLException {
        int count = 0;
        String selectTableSQL = request;
        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // выбираем данные с БД
            ResultSet rs = statement.executeQuery(selectTableSQL);

            // И если что то было получено то цикл while сработает
            while (rs.next()) {
                count = rs.getInt(1);

                //  System.out.println("count : " + count);
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
        return count;
    }
    public int getIntData(String request, String columnName) throws SQLException {
        int count = 0;
        String selectTableSQL = request;
        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                count = rs.getInt(columnName);
                System.out.println(count);
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
        return count;
    }

    public ArrayList<Integer> getIntArrayData(String request, String columnName) throws SQLException {
        ArrayList<Integer> list = new ArrayList<Integer>();

        String selectTableSQL = request;
        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                list.add(rs.getInt(columnName));
//                for (int i = 0; i < list.size(); i++) {
//                    System.out.println(list.get(i));
//                }

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
        return list;
    }
}
