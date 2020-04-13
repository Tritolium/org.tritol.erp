package org.tritol.erp.application;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.tritol.erp.controlling.tablemodels.AddOrderTableModel;

public class AddOrderPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5085109104000138202L;
	private JLabel order_id_label = new JLabel("Order-Nummer:");
	private JLabel order_date_label = new JLabel("Order-Datum:");
	private JTextField order_id_input = new JTextField();
	private JTextField order_date_input = new JTextField();
	private JTable order_input;
	private JButton add_row_button = new JButton("Artikel hinzufügen");
	private JButton confirm_order = new JButton("Order abschließen");

	public AddOrderPanel() {
		init();
	}

	private void init() {
		this.setLayout(new GridBagLayout());

		order_input = new JTable(new AddOrderTableModel());

		addComponent(order_id_label, 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_id_input, 1, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_date_label, 2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_date_input, 3, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(new JScrollPane(order_input), 0, 1, 3, 4, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(add_row_button, 0, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(confirm_order, 3, 4, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0);
	}

	public int getOrderId() {
		return Integer.parseInt(order_id_input.getText());
	}

	public String getOrderDate() {
		return order_date_input.getText();
	}

	/**
	 * 
	 * @return The table data. null if empty
	 */
	public Object[][] getOrderData() {
		return ((AddOrderTableModel) this.order_input.getModel()).getData();
	}

	public void setAddArticleListener(ActionListener l) {
		this.add_row_button.addActionListener(l);
	}

	public void setConfirmOrderListener(ActionListener l) {
		this.confirm_order.addActionListener(l);
	}

	public void addArticleRow() {
		AddOrderTableModel model = (AddOrderTableModel) order_input.getModel();
		model.addRow();
		this.setVisible(true);
	}

	public void reset() {
		order_id_input.setText("");
		order_date_input.setText("");
		((AddOrderTableModel) order_input.getModel()).clearTable();
	}
}
