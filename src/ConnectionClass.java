import java.sql.*;
public class ConnectionClass {
	
	private static Connection conn;
	
	public static Connection connect() {
	
		if(conn==null)
		{
			try {
				String url = "jdbc:mysql://127.0.0.1:3306/JDBCProject?useSSL=false";
				String username = "root";
				String password = "root";
				conn = DriverManager.getConnection(url, username, password);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return conn;
	
	}

}
