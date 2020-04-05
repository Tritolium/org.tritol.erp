package org.tritol.erp.application;

import java.time.LocalDate;

import org.tritol.erp.controlling.Controller;
import org.tritol.erp.data.OrderState;

public class ERP {

	public static void main(String[] args) {
		for (int i = 0; i < 80; i++) {
			Controller.addOrder(i, LocalDate.of(2020, 4, 3));
			Controller.addToOrder(i, "0327", "Gärröhrchen ohne Dichtgummi", 1, 0.95d);
			Controller.addToOrder(i, "0057-10", "Gummi Stopfen 24/18 mit 9mm Bohrung", 1, 1.80d);
			Controller.addToOrder(i, "1311", "Kitzinger Weinhefe trocken Neutral Universal 5g", 1, 2.25d);

			Controller.setOrderState(i, OrderState.ARRIVED);
		}
	}

}
