package org.tritol.erp.data;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DataAccess {

	private static Connection connection;

	/**
	 * URL to the database
	 */
	private final String database = "jdbc:sqlite:database.sqlite";

	public DataAccess() {
		connect();
	}

	public void connect() {

		File d_file = new File("database.sqlite");

		Boolean init = false;

		try {
			if (!d_file.isFile()) {
				d_file.createNewFile(); // create database file if there is none
				init = true;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			Class.forName("org.sqlite.JDBC"); // load the driver
		} catch (ClassNotFoundException e) {
			System.err.println("Unable to load driver");
		}

		try {
			connection = DriverManager.getConnection(database); // connect to database
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (init) {
			initDatabase();
		}
	}

	private void initDatabase() {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(
					"CREATE TABLE _order(" + "order_nr int," + "order_date date," + "arrival_date date default null,"
							+ "order_state varchar(1) default 'o'," + "primary key (order_nr)" + ")");
			statement.executeUpdate("CREATE TABLE consumption(" + "con_nr int," + "con_date date,"
					+ "usage varchar(256)," + "primary key (con_nr)" + ")");
			statement.executeUpdate("CREATE TABLE o_article(" + "order_nr int," + "pos_nr varchar(24),"
					+ "art_desc varchar(256)," + "quantity int," + "price real,"
					+ "foreign key (order_nr) references _order (order_nr)" + ")");
			statement.executeUpdate(
					"CREATE TABLE c_article (" + "con_nr int," + "pos_nr varchar(24)," + "art_desc varchar(256),"
							+ "quantity int," + "foreign key (con_nr) references consumption (con_nr)" + ")");
			statement.executeUpdate("CREATE TABLE stock (" + "pos_nr varchar(24)," + "art_desc varchar(256),"
					+ "quantity int," + "price real," + "primary key (pos_nr)" + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("Unable to disconnect");
		}
	}

	public void addConsumption(int con_nr, LocalDate con_date, String usage) {
		PreparedStatement prep_statement;
		try {
			prep_statement = connection
					.prepareStatement("INSERT INTO consumption (con_nr, con_date, usage) VALUES (?, ?, ?)");
			prep_statement.setInt(1, con_nr);
			prep_statement.setDate(2, Date.valueOf(con_date));
			prep_statement.setString(3, usage);
			prep_statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addOrder(int order_nr, LocalDate order_date) {
		PreparedStatement prep_statement;
		if (connection != null) {
			try {
				prep_statement = connection.prepareStatement("INSERT INTO _order (order_nr, order_date) VALUES (?, ?)");
				prep_statement.setInt(1, order_nr);
				prep_statement.setDate(2, Date.valueOf(order_date));
				prep_statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			connect();
			addOrder(order_nr, order_date);
		}
	}

	public void addToOrder(int order_nr, String pos_nr, String art_desc, int quantity, double price) {
		PreparedStatement prep_statement;
		if (connection != null) {
			try {
				prep_statement = connection.prepareStatement(
						"INSERT INTO o_article (order_nr, pos_nr, art_desc, quantity, price) VALUES (?, ?, ?, ?, ?)");
				prep_statement.setInt(1, order_nr);
				prep_statement.setString(2, pos_nr);
				prep_statement.setString(3, art_desc);
				prep_statement.setInt(4, quantity);
				prep_statement.setDouble(5, price);
				prep_statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			connect();
			addToOrder(order_nr, pos_nr, art_desc, quantity, price);
		}
	}

	public void addToStock(String pos_nr, int quantity) throws ArticleNotRegisteredException {
		PreparedStatement prep_statement;
		ResultSet resSet;
		int stock_quantity;
		try {
			// check if pos_nr in stock
			prep_statement = connection.prepareStatement("SELECT pos_nr FROM stock WHERE pos_nr = ?");
			prep_statement.setString(1, pos_nr);
			resSet = prep_statement.executeQuery();
			if (resSet.next()) { // if pos_nr is in stock
				// update quantity
				stock_quantity = resSet.getInt("quantity");
				prep_statement = connection.prepareStatement("UPDATE stock SET quantity = ? WHERE pos_nr = ?");
				prep_statement.setInt(1, stock_quantity + quantity);
				prep_statement.setString(2, pos_nr);
				prep_statement.execute();
			} else { // else throw exception
				throw new ArticleNotRegisteredException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object[][] getOrders(String order_nr, String order_state, LocalDate order_date) {
		int NUMBER = 1;
		int STATE = 2;
		int DATE = 4;
		
		int filter = 0;
		
		if(!order_nr.isBlank()) {
			filter += NUMBER;
		}
		if(!order_state.isBlank()) {
			filter += STATE;
		}
		if(order_date != null) {
			filter += DATE;
		}
		
		PreparedStatement prep_statement;
		ResultSet result_set;
		
		Object[][] results = null;
		
		ArrayList<ArrayList<Object>> converter = new ArrayList<>();
		ArrayList<Object> order;

		try {
			switch(filter) {
			case 0:
				prep_statement = connection.prepareStatement("SELECT * FROM _order");
				break;
			case 1:
				prep_statement = connection.prepareStatement("SELECT * FROM _order WHERE order_nr = ?");
				prep_statement.setString(1, order_nr);
				break;
			case 2:
				prep_statement = connection.prepareStatement("SELECT * FROM _order WHERE order_state = ?");
				prep_statement.setString(1, order_state);
				break;
			case 3:
				prep_statement = connection.prepareStatement("SELECT * FROM _order WHERE order_nr = ? AND order_state = ?");
				prep_statement.setString(1, order_nr);
				prep_statement.setString(2, order_state);
				break;
			case 4:
				prep_statement = connection.prepareStatement("SELECT * FROM _order WHERE order_date = ?");
				prep_statement.setDate(1, Date.valueOf(order_date));
				break;
			case 5:
				prep_statement = connection.prepareStatement("SELECT * FROM _order WHERE order_nr = ? AND order_date = ?");
				prep_statement.setString(1, order_nr);
				prep_statement.setDate(2, Date.valueOf(order_date));
				break;
			case 6:
				prep_statement = connection.prepareStatement("SELECT * FROM _order WHERE order_state = ? AND order_date = ?");
				prep_statement.setString(1, order_state);
				prep_statement.setDate(2, Date.valueOf(order_date));
				break;
			case 7:
				prep_statement = connection.prepareStatement("SELECT * FROM _order WHERE order_nr = ? AND order_state = ? AND order_date = ?");
				prep_statement.setString(1, order_nr);
				prep_statement.setString(2, order_state);
				prep_statement.setDate(3, Date.valueOf(order_date));
				break;
			default:
				prep_statement = connection.prepareStatement("SELECT * FROM _order");
			}
			
			 
			
			result_set = prep_statement.executeQuery();
			
			while(result_set.next()) {
				order = new ArrayList<Object>();
				order.add(result_set.getString("order_nr"));
				order.add(result_set.getDate("order_date").toLocalDate());
				//arrival-date needs check for null
				if(result_set.getDate("arrival_date") != null) {
					order.add(result_set.getDate("arrival_date").toLocalDate());
				} else {
					order.add(null);
				}
				
				order.add(result_set.getString("order_state"));
				converter.add(order);
			}
			
			results = new Object[converter.size()][4];
			
			for(int i = 0; i < converter.size(); i++) {
				for(int j = 0; j < 4; j++) {
					results[i][j] = converter.get(i).get(j);
				}
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return results;
	}

	public void addOrderToStock(int order_nr) {
		PreparedStatement prep_statement;
		ResultSet resSet;
		// get article list
		try {
			prep_statement = connection.prepareStatement("SELECT * FROM o_article WHERE order_nr = ?");
			prep_statement.setInt(1, order_nr);
			resSet = prep_statement.executeQuery();
			while (resSet.next()) {
				try {
					addToStock(resSet.getString("pos_nr"), resSet.getInt("quantity"));
				} catch (ArticleNotRegisteredException e) {
					addNewToStock(resSet.getString("pos_nr"), resSet.getString("art_desc"), resSet.getInt("quantity"),
							resSet.getDouble("price"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// add articles to stock
	}

	private void addNewToStock(String pos_nr, String art_desc, int quantity, double price) {
		PreparedStatement prep_statement;
		try {
			prep_statement = connection
					.prepareStatement("INSERT INTO stock (pos_nr, art_desc, quantity, price) VALUES (?, ?, ?, ?)");
			prep_statement.setString(1, pos_nr);
			prep_statement.setString(2, art_desc);
			prep_statement.setInt(3, quantity);
			prep_statement.setDouble(4, price);
			prep_statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setArrivalDate(int order_nr, LocalDate arrival) {
		PreparedStatement prep_statement;
		if (connection != null) {
			try {
				prep_statement = connection.prepareStatement("UPDATE _order SET arrival_date = ? WHERE order_nr = ?");
				prep_statement.setDate(1, Date.valueOf(arrival));
				prep_statement.setInt(2, order_nr);
				prep_statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void setOrderState(int order_nr, OrderState state) {
		PreparedStatement prep_statement;
		if (connection != null) {
			try {
				prep_statement = connection.prepareStatement("UPDATE _order SET order_state = ? WHERE order_nr = ?");
				switch (state) {
				case ARRIVED:
					prep_statement.setString(1, "a");
					break;
				case IN_DELIVERY:
					prep_statement.setString(1, "e");
					break;
				case ORDERED:
					prep_statement.setString(1, "o");
				}
				prep_statement.setInt(2, order_nr);
				prep_statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
