package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	// променлива, която пази връзката
    static Connection conn = null;
	
    public static Connection getConnection() {
        try {
        	// зарежда H2 драйвера в паметта
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