package org.tritol.erp.controlling.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.tritol.erp.application.dialog.AddSupplierDialog;
import org.tritol.erp.data.DataAccess;

public class AddSupplierController {
	
	private AddSupplierDialog _view;
	private DataAccess _model;
	
	public AddSupplierController(DataAccess model) {
		_view = new AddSupplierDialog();
		_model = model;
		
		setActionListeners();
	}
	
	private void setActionListeners() {
		_view.setConfirmSupplierListener(new ConfirmSupplierListener());
	}
	
	class ConfirmSupplierListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String supplier_name, street, house_number, postcode, city;
			
			supplier_name = _view.getSupplierName();
			street = _view.getSupplierStreet();
			house_number = _view.getSupplierHousenumber();
			postcode = _view.getSupplierPostcode();
			city = _view.getSupplierCity();
			
			_model.addSupplier(supplier_name, street, house_number, postcode, city);
			
			_view.dispose();
		}
	}
}
