package org.tritol.erp.controlling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import javax.swing.JTable;
import javax.swing.event.MouseInputListener;

import org.tritol.erp.application.mainview.MainFrame;
import org.tritol.erp.controlling.dialog.*;
import org.tritol.erp.data.DataAccess;

public class Controller {

	// ADD possibly add own controllers for views

	// ADD button to safely close the program

	private MainFrame _view;
	private DataAccess _model;
	private Controller _controller;
	
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
		
		// top menu stock
		this._view.setShowStockListener(new ShowStockListener());

		// ShowOrderPanel
		this._view.setGetOrdersFilterListener(new GetOrdersListener());
		this._view.setEditOrdersListener(new ClickOrderTableListener());
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

	class ShowStockListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			_view.getShowStockView().setData(_model.getStock());
			_view.setView(MainFrame.SHOWSTOCK);
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
