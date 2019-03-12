package FilmProduction.Models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnect {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/MovieProduction?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String password = "2301";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static ResultSet main(String query) {
        ResultSet result = null;
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            result = rs;
            /*while (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Total number of actors: " + count);
            }*/
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            return result;
        }
    }

    public static boolean formatQuery(String query) {
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(111);
            e.printStackTrace();
            return false;
        }
        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
