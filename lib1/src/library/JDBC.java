package library;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
	
	public static Connection getConnection() throws SQLException,
		ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/book";
		String username = "root";
		String password = "root";
		Connection conn = DriverManager.getConnection(url, username, 
		password);
		return conn;
	}
			
}
		

