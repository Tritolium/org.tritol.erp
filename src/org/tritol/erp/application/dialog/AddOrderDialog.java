package org.tritol.erp.application.dialog;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.tritol.erp.controlling.tablemodels.AddArticlesTableModel;

public class AddOrderDialog extends AbstractDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -276789095503385395L;

	private JLabel order_id_label = new JLabel("Order-Nummer:");
	private JLabel order_date_label = new JLabel("Order-Datum:");
	private JLabel order_supplier = new JLabel("Lieferant:");
	private JLabel shipping_label = new JLabel("Versandkosten");
	private JTextField order_nr_input = new JTextField();
	private JTextField order_date_input = new JTextField(); // CHANGE use extra date input
	private JComboBox<String> order_supplier_input;
	private JTable order_input;
	private JTextField shipping_input = new JTextField();
	private JButton add_row_button = new JButton("Artikel hinzufügen");
	private JButton confirm_order = new JButton("Order abschließen");

	public AddOrderDialog(String[] suppliers) {
		init(suppliers);
	}

	public void init(String[] suppliers) {
		this.setLayout(new GridBagLayout());
		this.setBounds(100, 100, 1000, 600);

		order_input = new JTable(new AddArticlesTableModel());
		order_supplier_input = new JComboBox<String>(suppliers);

		addComponent(order_id_label, 0, 0, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_nr_input, 1, 0, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_date_label, 2, 0, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_date_input, 3, 0, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_supplier, 0, 1, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_supplier_input, 1, 1, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(new JScrollPane(order_input), 0, 2, 4, 4, 1, 1, CENTER, BOTH, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(shipping_label, 0, 6, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(shipping_input, 1, 6, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(add_row_button, 0, 7, 1, 1, 0, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(confirm_order, 3, 7, 1, 1, 0, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
	
		this.setVisible(true);
	}

	public String getOrderNr() {
		return order_nr_input.getText();
	}

	public LocalDate getOrderDate() {
		if (matchDate(order_date_input.getText())) {
			String[] date = order_date_input.getText().split("\\.");
			return LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
		} else {
			return null;
		}
	}

	public String getSupplier() {
		return (String) order_supplier_input.getSelectedItem();
	}

	public double getShippingCosts() {
		if (!shipping_input.getText().isBlank()) {
			return Double.parseDouble(shipping_input.getText());
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * @return The table data. null if empty
	 */
	public Object[][] getOrderData() {
		return ((AddArticlesTableModel) this.order_input.getModel()).getData();
	}

	public void setAddArticleListener(ActionListener l) {
		this.add_row_button.addActionListener(l);
	}

	public void setConfirmOrderListener(ActionListener l) {
		this.confirm_order.addActionListener(l);
	}

	public void addArticleRow() {
		((AddArticlesTableModel) order_input.getModel()).addRow();
		this.setVisible(true);
	}
}
