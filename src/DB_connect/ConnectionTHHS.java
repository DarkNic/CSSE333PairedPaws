package DB_connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTHHS {
	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public ConnectionTHHS(String serverName, String databaseName) {
		this.serverName = serverName;
		this.databaseName = databaseName;
	}
	
	public boolean connect(String user, String pass) {
		String connectionString = "jdbc:sqlserver://"+this.serverName+";databaseName="+this.databaseName+";user="+user+";password="+pass;
		System.out.println(connectionString);
		try {
			connection = DriverManager.getConnection(connectionString);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
