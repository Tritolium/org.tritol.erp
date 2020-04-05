package org.tritol.erp.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataAccess {
	
	private static Connection connection;
	
	/**
	 * URL to the database
	 */
	private static final String database = "jdbc:sqlite:database.sqlite";
	/**
	 * URL to serialized DataController instance
	 */
	private static final String ser_database ="./database.ser";

	public static void connect() {
		
		File d_file = new File("database.sqlite");
		
		Boolean init = false;
		
		try {
			if(!d_file.isFile()) {
				d_file.createNewFile();								//create database file if there is none
				init = true;
			}								
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
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
		
		if(init) {
			initDatabase();
		}
	}
	
	private static void initDatabase() {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(
					"CREATE TABLE _order("
					+ "order_nr int,"
					+ "order_date date,"
					+ "arrival_date date,"
					+ "order_state varchar(1),"
					+ "primary key (order_nr)"
					+ ")");
			statement.executeUpdate(
					"CREATE TABLE consumption("
					+ "con_nr int,"
					+ "con_date date,"
					+ "usage varchar(256),"
					+ "primary key (con_nr)"
					+ ")");
			statement.executeUpdate(
					"CREATE TABLE o_article("
					+ "order_nr int,"
					+ "pos_nr varchar(24),"
					+ "art_desc varchar(256),"
					+ "quantity int,"
					+ "price real,"
					+ "foreign key (order_nr) references _order (order_nr)"
					+ ")");
			statement.executeUpdate(
					"CREATE TABLE c_article ("
					+ "con_nr int,"
					+ "pos_nr varchar(24),"
					+ "art_desc varchar(256),"
					+ "quantity int,"
					+ "foreign key (con_nr) references consumption (con_nr)"
					+ ")");
			statement.executeUpdate(
					"CREATE TABLE stock ("
					+ "pos_nr varchar(24),"
					+ "art_desc varchar(256),"
					+ "quantity int,"
					+ "price real,"
					+ "primary key (pos_nr)"
					+ ")");
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
	
	public static void serialize(DataController instance) {
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream(ser_database);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(instance);
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DataController deserialize() {
		FileInputStream fis;
		ObjectInputStream ois;
		DataController instance = null;
		
		try {
			fis = new FileInputStream(ser_database);
			ois = new ObjectInputStream(fis);
			instance = (DataController) ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			System.err.println("Could not load database");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return instance;
	}
}
