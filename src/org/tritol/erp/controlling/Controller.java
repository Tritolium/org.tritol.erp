package org.tritol.erp.controlling;

import java.time.LocalDate;

import org.tritol.erp.data.DataController;
import org.tritol.erp.data.OrderState;

public class Controller {

	public static void addOrder(int order_nr, LocalDate order_date) {
		DataController.getInstance().addOrder(order_nr, order_date);
	}

	public static void addToOrder(int order_nr, String pos_nr, String art_desc, int quantity, double price) {
		DataController.getInstance().addToOrder(order_nr, pos_nr, art_desc, quantity, price);
	}
	
	public static void setOrderState(int order_nr, OrderState state) {
		DataController.getInstance().setOrderState(order_nr, state);
		if(state == OrderState.ARRIVED) {
			DataController.getInstance().addOrderToStock(order_nr);
		}
	}
	
	public static void addConsumption(LocalDate con_date, String usage) {
		DataController.getInstance().addConsumption(con_date, usage);
	}
	
	public static void safeState() {
		DataController.saveInstance();
	}
}
