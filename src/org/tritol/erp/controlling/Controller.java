package org.tritol.erp.controlling;

import java.time.LocalDate;

import org.tritol.erp.data.DataAccess;
import org.tritol.erp.data.OrderState;

public class Controller {

	public static void addOrder(int order_nr, LocalDate order_date) {
		DataAccess.addOrder(order_nr, order_date);
	}

	public static void addToOrder(int order_nr, String pos_nr, String art_desc, int quantity, double price) {
		DataAccess.addToOrder(order_nr, pos_nr, art_desc, quantity, price);
	}
	
	public static void setOrderState(int order_nr, OrderState state) {
		DataAccess.setOrderState(order_nr, state);
		if(state == OrderState.ARRIVED) {
			DataAccess.setArrivalDate(order_nr, LocalDate.now());
			DataAccess.addOrderToStock(order_nr);
		}
	}
	
	public static void addConsumption(int con_nr, LocalDate con_date, String usage) {
		DataAccess.addConsumption(con_nr, con_date, usage);
	}
	
	public static void disconnect() {
		DataAccess.disconnect();
	}
}
