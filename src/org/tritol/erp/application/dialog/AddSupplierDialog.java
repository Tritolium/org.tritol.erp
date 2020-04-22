package org.tritol.erp.application.dialog;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddSupplierDialog extends AbstractDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2649968491954421406L;
	// CHANGE design Supplier data input
	private JLabel supplier_name_label = new JLabel("Lieferant:");
	private JLabel supplier_street_label = new JLabel("Straﬂe, Hausnr.:");
	private JLabel supplier_postcode_label = new JLabel("PLZ/Stadt:");

	private JTextField supplier_name_input = new JTextField();
	private JTextField supplier_street_input = new JTextField();
	private JTextField supplier_housenumber_input = new JTextField();
	private JTextField supplier_postcode_input = new JTextField();
	private JTextField supplier_city_input = new JTextField(); // ADD auto-fill

	private JButton cancel_dialog = new JButton("Abbrechen");
	private JButton confirm_dialog = new JButton("Anlegen");

	public AddSupplierDialog() {
		init();
	}

	private void init() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		this.setBounds(100, 100, 500, 135);

		this.cancel_dialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		addComponent(supplier_name_label, 0, 0, 1, 1, 0, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(supplier_street_label, 0, 1, 1, 1, 0, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(supplier_postcode_label, 0, 2, 1, 1, 0, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

		addComponent(supplier_name_input, 1, 0, 3, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(supplier_street_input, 1, 1, 2, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(supplier_housenumber_input, 3, 1, 1, 1, 0, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(supplier_postcode_input, 1, 2, 1, 1, 0.8, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(supplier_city_input, 2, 2, 2, 1, 0, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

		addComponent(cancel_dialog, 2, 3, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 100, 0, 0), 0, 0);
		addComponent(confirm_dialog, 3, 3, 1, 1, 0, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

		this.setVisible(true);
	}

	public String getSupplierName() {
		return supplier_name_input.getText();
	}

	public String getSupplierStreet() {
		return supplier_street_input.getText();
	}

	public String getSupplierHousenumber() {
		return supplier_housenumber_input.getText();
	}

	public String getSupplierPostcode() {
		return supplier_postcode_input.getText();
	}

	public String getSupplierCity() {
		return supplier_city_input.getText();
	}

	public void setConfirmSupplierListener(ActionListener l) {
		this.confirm_dialog.addActionListener(l);
	}
}
