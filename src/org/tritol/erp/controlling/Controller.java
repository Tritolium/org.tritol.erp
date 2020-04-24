package org.tritol.erp.controlling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.event.MouseInputListener;

import org.tritol.erp.application.dialog.*;
import org.tritol.erp.application.mainview.MainFrame;
import org.tritol.erp.controlling.dialog.AbstractDialogController;
import org.tritol.erp.controlling.dialog.AddOrderController;
import org.tritol.erp.controlling.dialog.AddSupplierController;
import org.tritol.erp.controlling.dialog.EditOrderController;
import org.tritol.erp.data.DataAccess;

public class Controller {

	// ADD possibly add own controllers for views

	// ADD button to safely close the program

	private MainFrame _view;
	private DataAccess _model;
	private Controller _controller;
	
	private ArrayList<AbstractDialogController> dialogControllerRegister = new ArrayList<AbstractDialogController>();

	private boolean running = true;

	public Controller() {
		this._model = new DataAccess();
		this._view = new MainFrame();
		this._controller = this; // reference to self. For usage in ActionListeners

		addListener();
	}

	public void showView() {
		this._view.setVisible(true);
	}

	private void addListener() {
		//top menu ERP
		this._view.setCloseERPListener(new CloseERPListener());
		
		// top menu distribution
		this._view.setAddOrderListener(new AddOrderListener());
		this._view.setShowOrderListener(new ShowOrderListener());
		this._view.setAddSupplierListener(new AddSupplierListener());

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

	public void addConsumption(int con_nr, LocalDate con_date, String usage) {
		_model.addConsumption(con_nr, con_date, usage);
	}

	public void disconnect() {
		_model.disconnect();
	}
	
	public void registerController(AbstractDialogController dialogController) {
		dialogControllerRegister.add(dialogController);
	}
	
	public void unregisterController(AbstractDialogController dialogController) {
		dialogControllerRegister.remove(dialogController);
	}

	/**
	 * Closes the program
	 * @author Dominik
	 *
	 */
	class CloseERPListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			_view.dispose();
			_model.disconnect();
		}
		
	}
	
	/**
	 * Starts controller for order input dialog
	 * 
	 * @author Dominik
	 *
	 */
	class AddOrderListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new AddOrderController(_controller, _model);
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
			new AddSupplierController(_controller, _model);
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
				new EditOrderController(_controller, _model, order_id);
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
