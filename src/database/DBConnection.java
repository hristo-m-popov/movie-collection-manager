package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static Connection conn = null;
	
    public static Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(
                "jdbc:h2:C:\\Users\\Lenovo\\MovieDB\\movie_collection", 
                "sa", 
                ""
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}