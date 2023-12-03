package task;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static String databaseUrl = "jdbc:mysql://localhost:3306/taskdb";
	private static String username= "root";
	private static String password = "Daniel19292112";
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con  = DriverManager.getConnection(databaseUrl, username, password);
		return con;	
	}
}

