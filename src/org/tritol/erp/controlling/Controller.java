package org.tritol.erp.controlling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import org.tritol.erp.application.MainView;
import org.tritol.erp.data.DataAccess;
import org.tritol.erp.data.OrderState;

public class Controller {

	private MainView _view;
	private DataAccess _model;

	public Controller() {
		this._model = new DataAccess();
		this._view = new MainView();

		addListener();
	}

	public void showView() {
		this._view.setVisible(true);
	}

	private void addListener() {
		this._view.setAddOrderListener(new AddOrderListener());
		this._view.setAddArticleListener(new AddArticleListener());
		this._view.setConfirmOrderListener(new ConfirmOrderListener());
	}

	public void addOrder(int order_nr, LocalDate order_date) {
		_model.addOrder(order_nr, order_date);
	}

	public void addToOrder(int order_nr, String pos_nr, String art_desc, int quantity, double price) {
		_model.addToOrder(order_nr, pos_nr, art_desc, quantity, price);
	}

	public void setOrderState(int order_nr, OrderState state) {
		_model.setOrderState(order_nr, state);
		if (state == OrderState.ARRIVED) {
			_model.setArrivalDate(order_nr, LocalDate.now());
			_model.addOrderToStock(order_nr);
		}
	}

	public void addConsumption(int con_nr, LocalDate con_date, String usage) {
		_model.addConsumption(con_nr, con_date, usage);
	}

	public void disconnect() {
		_model.disconnect();
	}

	class AddOrderListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_view.setView(MainView.ADDORDER);
		}
	}

	class AddArticleListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_view.addArticleRow();
		}
	}

	class ConfirmOrderListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_view.setView(MainView.MAINVIEW); // sets view back to main
			String pos_nr, art_desc; // writes to database in background
			int quantity;
			int year, month, day;
			double price;
			int order_nr = _view.getOrderView().getOrderId();
			Object[][] data = _view.getOrderView().getOrderData();
			String order_date_string = _view.getOrderView().getOrderDate();
			String[] order_date_array;
			if (order_date_string.matches("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d")) { // if date matches dd.mm.yyyy
				order_date_array = order_date_string.split("\\.");
				year = Integer.parseInt(order_date_array[2]);
				month = Integer.parseInt(order_date_array[1]);
				day = Integer.parseInt(order_date_array[0]);
				addOrder(order_nr, LocalDate.of(year, month, day));
			} else {
				addOrder(order_nr, LocalDate.now());
			}
			if (data != null) {
				for (Object[] row : data) {
					pos_nr = (String) row[0];
					art_desc = (String) row[1];
					quantity = Integer.parseInt((String) row[2]);
					price = Double.parseDouble((String) row[3]);
					addToOrder(order_nr, pos_nr, art_desc, quantity, price);
				}
			}
			_view.getOrderView().reset();
		}
	}
}
