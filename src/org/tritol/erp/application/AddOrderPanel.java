package org.tritol.erp.application;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.tritol.erp.controlling.AddOrderTableModel;

public class AddOrderPanel extends JPanel {

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
		GridBagConstraints gbc = new GridBagConstraints();

		order_input = new JTable(new AddOrderTableModel());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(order_id_label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1;
		this.add(order_id_input, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 0;
		this.add(order_date_label, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.weightx = 1;
		this.add(order_date_input, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 4;
		gbc.gridheight = 3;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(new JScrollPane(order_input), gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.NONE;
		this.add(add_row_button, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		this.add(confirm_order, gbc);
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
