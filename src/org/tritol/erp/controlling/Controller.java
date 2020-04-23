package org.tritol.erp.controlling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.JTable;
import javax.swing.event.MouseInputListener;

import org.tritol.erp.application.dialog.*;
import org.tritol.erp.application.mainview.MainFrame;
import org.tritol.erp.controlling.dialog.AddSupplierController;
import org.tritol.erp.data.DataAccess;
import org.tritol.erp.data.OrderState;

public class Controller {

	// ADD possibly add own controllers for views

	// ADD button to safely close the program

	private MainFrame _view;
	private DataAccess _model;

	private boolean running = true;

	public Controller() {
		this._model = new DataAccess();
		this._view = new MainFrame();

		addListener();
	}

	public void showView() {
		this._view.setVisible(true);
	}

	private void addListener() {
		// top menu items
		this._view.setAddOrderListener(new AddOrderListener());
		this._view.setShowOrderListener(new ShowOrderListener());
		this._view.setAddSupplierListener(new AddSupplierListener());

		// AddOrderPanel buttons
		this._view.setAddArticleListener(new AddArticleListener());
		this._view.setConfirmOrderListener(new ConfirmOrderListener());

		// ShowOrderPanel
		this._view.setGetOrdersFilterListener(new GetOrdersListener());
		this._view.setEditOrdersListener(new ClickOrderTableListener());
	}

	/**
	 * Adds an order to the model
	 * 
	 * @param order_nr   The order number
	 * @param order_date The order date
	 */
	public void addOrder(String order_nr, String supplier, LocalDate order_date) {
		_model.addOrder(order_nr, supplier, order_date);
	}

	/**
	 * Adds an article to a given order
	 * 
	 * @param order_nr The order number
	 * @param pos_nr   The position number of the article
	 * @param art_desc The articles description
	 * @param quantity The articles quantity
	 * @param price    The articles price
	 */
	public void addToOrder(String order_nr, String pos_nr, String art_desc, int quantity, double price) {
		_model.addToOrder(order_nr, pos_nr, art_desc, quantity, price);
	}

	public void addSupplier(String supplier_name, String street, String housenumber, String postcode, String city) {
		_model.addSupplier(supplier_name, street, housenumber, postcode, city);
	}

	public boolean isRunning() {
		return running;
	}

	/**
	 * Gets orders from the model with given filters. Leave empty, if no filter
	 * should be applied
	 * 
	 * @param order_nr    Filter per order number
	 * @param order_state Filter per order state
	 * @param order_date  Filter per order date
	 */
	public void getOrders(String order_nr, String order_state, LocalDate order_date) {
		Object[][] orders;

		if (order_state.matches("b.*") | order_state.matches("o.*")) {
			order_state = "o";
		} else if (order_state.matches("g.*") | order_state.matches("a.*")) {
			order_state = "a";
		}

		orders = _model.getOrders(order_nr, order_state, order_date);
		_view.getShowOrderView().setData(orders);
	}

	public void getOrderData(String order_nr, EditOrderDialog dialog) {
		Object[] order_data = _model.getOrderData(order_nr);
		dialog.setOrderData((LocalDate) order_data[0], (String) order_data[1]);
		dialog.setArticles(_model.getArticles(order_nr));
	}

	public void setOrderState(String order_nr, OrderState state) {
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

	/**
	 * Changes the view to AddOrder
	 * 
	 * @author Dominik
	 *
	 */
	class AddOrderListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object[][] supplier_data = _model.getSuppliers();
			if (supplier_data != null) {
				String[] supplier_names = new String[supplier_data.length];
				for (int i = 0; i < supplier_names.length; i++) {
					supplier_names[i] = (String) supplier_data[i][0];
				}
				_view.getAddOrderView().setSuppliers(supplier_names);
			}
			_view.setView(MainFrame.ADDORDER);
		}
	}

	/**
	 * Changes the view to ShowOrder
	 * 
	 * @author Dominik
	 *
	 */
	class ShowOrderListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_view.setView(MainFrame.SHOWORDER);
		}
	}

	/**
	 * Starts controller for supplier input dialog
	 * 
	 * @author Dominik
	 *
	 */
	class AddSupplierListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new AddSupplierController(_model);
		}
	}

	/**
	 * Adds row to the table in AddOrder-View
	 * 
	 * @author Dominik
	 *
	 */
	class AddArticleListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_view.getAddOrderView().addArticleRow();
		}
	}

	/**
	 * Loads orders from model to ShowOrder-View
	 * 
	 * @author Dominik
	 *
	 */
	class GetOrdersListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String order_nr, order_state;
			LocalDate order_date;

			order_nr = _view.getShowOrderView().getOrderNr();
			order_state = _view.getShowOrderView().getOrderState();
			order_date = _view.getShowOrderView().getOrderDate();

			getOrders(order_nr, order_state, order_date);
		}
	}

	/**
	 * Implements method to add an Order to the database
	 * 
	 * @author Dominik
	 *
	 */
	class ConfirmOrderListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_view.setView(MainFrame.MAINVIEW); // sets view back to main
			String pos_nr, art_desc; // writes to database in background
			int quantity;
			int year, month, day;
			double price, shipping;
			String order_nr = _view.getAddOrderView().getOrderId();
			if (!order_nr.isBlank()) {
				Object[][] order_articles = _view.getAddOrderView().getOrderData();
				String supplier = _view.getAddOrderView().getSupplier();
				String order_date_string = _view.getAddOrderView().getOrderDate();
				String[] order_date_array;
				if (!order_date_string.equals("none")) {
					order_date_array = order_date_string.split("\\.");
					year = Integer.parseInt(order_date_array[2]);
					month = Integer.parseInt(order_date_array[1]);
					day = Integer.parseInt(order_date_array[0]);
					addOrder(order_nr, supplier, LocalDate.of(year, month, day));
				} else {
					addOrder(order_nr, supplier, LocalDate.now());
				}

				if (order_articles != null) {
					for (Object[] row : order_articles) {
						pos_nr = (String) row[0];
						art_desc = (String) row[1];
						quantity = Integer.parseInt((String) row[2]);
						price = Double.parseDouble((String) row[3]);
						addToOrder(order_nr, pos_nr, art_desc, quantity, price);
					}
				}
				shipping = _view.getAddOrderView().getShippingCosts();
				if (shipping > 0) {
					addToOrder(order_nr, "9999", "Versandkosten", 1, shipping);
				}
			}
			_view.getAddOrderView().reset();
		}
	}

	/**
	 * Opens a dialog for editing an order
	 * 
	 * @author Dominik
	 *
	 */
	class ClickOrderTableListener implements MouseInputListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				JTable target = (JTable) e.getSource();
				int row = target.getSelectedRow();
				String order_id = (String) target.getValueAt(row, 1);
				EditOrderDialog editOrder = new EditOrderDialog(order_id);
				getOrderData(order_id, editOrder);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}

}
