package org.tritol.erp.controlling.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import org.tritol.erp.application.dialog.EditOrderDialog;
import org.tritol.erp.controlling.Controller;
import org.tritol.erp.data.DataAccess;

public class EditOrderController extends AbstractDialogController {

	private DataAccess _model;
	private EditOrderDialog _view;

	public EditOrderController(Controller parent, DataAccess model, String order_nr) {
		super(parent);
		_model = model;
		_view = new EditOrderDialog(order_nr);

		init(order_nr);
		setActionListeners();
	}

	private void init(String order_nr) {
		Object[] order_data = _model.getOrderData(order_nr);
		_view.setOrderData((LocalDate) order_data[0], (String) order_data[1]);
		_view.setArticles(_model.getArticles(order_nr));
	}

	private void setActionListeners() {
		_view.setConfirmOrderListener(new ConfirmOrderListener());
	}

	class ConfirmOrderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String order_nr;
			LocalDate deliverydate = _view.getDeliveryDate();
			if (deliverydate != null) {
				order_nr = _view.getOrderNr();
				_view.dispose();
				_model.setOrderDelivered(order_nr, deliverydate);
				_model.addOrderToStock(order_nr);
			} else {
				_view.dispose();
			}
		}

	}
}
