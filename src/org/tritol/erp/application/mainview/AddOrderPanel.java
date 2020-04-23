package org.tritol.erp.application.mainview;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.tritol.erp.controlling.tablemodels.AddArticlesTableModel;

public class AddOrderPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5085109104000138202L;
	private JLabel order_id_label = new JLabel("Order-Nummer:");
	private JLabel order_date_label = new JLabel("Order-Datum:");
	private JLabel order_supplier = new JLabel("Lieferant:");
	private JLabel shipping_label = new JLabel("Versandkosten");
	private JTextField order_id_input = new JTextField();
	private JTextField order_date_input = new JTextField(); // CHANGE use extra date input
	private JComboBox<String> order_supplier_input = new JComboBox<String>();
	private JTable order_input;
	private JTextField shipping_input = new JTextField();
	private JButton add_row_button = new JButton("Artikel hinzufügen");
	private JButton confirm_order = new JButton("Order abschließen");

	public AddOrderPanel() {
		init();
	}

	private void init() {
		this.setLayout(new GridBagLayout());

		order_input = new JTable(new AddArticlesTableModel());

		addComponent(order_id_label, 0, 0, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_id_input, 1, 0, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_date_label, 2, 0, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_date_input, 3, 0, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_supplier, 0, 1, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_supplier_input, 1, 1, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(new JScrollPane(order_input), 0, 2, 4, 4, 1, 1, CENTER, BOTH, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(shipping_label, 0, 6, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(shipping_input, 1, 6, 1, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(add_row_button, 0, 7, 1, 1, 0, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(confirm_order, 3, 7, 1, 1, 0, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
	}

	public String getOrderId() {
		return order_id_input.getText();
	}

	public String getOrderDate() {
		if (matchDate(order_date_input.getText())) {
			return order_date_input.getText();
		} else {
			return "none";
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

	public void setSuppliers(String[] suppliers) {
		order_supplier_input.removeAllItems();
		for (int i = 0; i < suppliers.length; i++) {
			order_supplier_input.addItem(suppliers[i]);
		}
	}

	public void addArticleRow() {
		AddArticlesTableModel model = (AddArticlesTableModel) order_input.getModel();
		model.addRow();
		this.setVisible(true);
	}

	public void reset() {
		order_id_input.setText("");
		order_date_input.setText("");
		shipping_input.setText("");
		((AddArticlesTableModel) order_input.getModel()).clearTable();
	}

	private boolean matchDate(String date) {
		if (date.matches("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d") || date.matches("\\d\\.\\d\\d\\.\\d\\d\\d\\d")
				|| date.matches("\\d\\d\\.\\d\\.\\d\\d\\d\\d") || date.matches("\\d\\.\\d\\.\\d\\d\\d\\d")) {
			return true;
		} else {
			return false;
		}
	}
}
