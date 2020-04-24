package org.tritol.erp.controlling.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import org.tritol.erp.application.dialog.AddOrderDialog;
import org.tritol.erp.controlling.Controller;
import org.tritol.erp.data.DataAccess;

public class AddOrderController extends AbstractDialogController{

	private AddOrderDialog _view;
	private DataAccess _model;

	public AddOrderController(Controller parent, DataAccess model) {
		super(parent);
		_model = model;

		Object[][] suppliers = _model.getSuppliers();
		String[] supplier_names = new String[suppliers.length + 1];

		supplier_names[0] = "";

		for (int i = 0; i < suppliers.length; i++) {
			supplier_names[i + 1] = (String) suppliers[i][0];
		}

		_view = new AddOrderDialog(supplier_names);

		setActionListeners();
	}

	private void setActionListeners() {
		this._view.setAddArticleListener(new AddArticleListener());
		this._view.setConfirmOrderListener(new ConfirmOrderListener());
	}

	/**
	 * invokes the dialog to add an article-row
	 * 
	 * @author Dominik
	 *
	 */
	class AddArticleListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			_view.addArticleRow();
		}

	}

	class ConfirmOrderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String order_nr;
			String supplier;
			LocalDate order_date;

			Object[][] order_articles;

			Double shipping_costs;

			order_nr = _view.getOrderNr();
			supplier = _view.getSupplier();
			order_date = _view.getOrderDate();

			order_articles = _view.getOrderData();

			shipping_costs = _view.getShippingCosts();

			_view.dispose();

			_model.addOrder(order_nr, supplier, order_date);

			if (order_articles != null) {
				for (Object[] article : order_articles) {
					_model.addToOrder(order_nr, (String) article[0], (String) article[1],
							Integer.parseInt((String) article[2]), Double.parseDouble((String) article[3]));
				}
			}
			if (shipping_costs > 0)
				_model.addToOrder(order_nr, "9999", "Versandkosten", 1, shipping_costs);

		}

	}
}
