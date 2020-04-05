package org.tritol.erp.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataAccess {
	
	private static Connection connection;
	
	/**
	 * URL to the database
	 */
	private static final String database = "jdbc:sqlite:database.sqlite";
	
	public static void connect() {
		try {
			Class.forName("org.sqlite.JDBC");						//load the driver
		} catch (ClassNotFoundException e) {
			System.err.println("Unable to load driver");
		}
		
		try {
			connection = DriverManager.getConnection(database);		//connect to database
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void disconnect() {
		try {
			if(connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("Unable to disconnect");
		}
	}
}
