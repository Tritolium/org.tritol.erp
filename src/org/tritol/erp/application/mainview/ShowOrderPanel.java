package org.tritol.erp.application.mainview;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;

import org.tritol.erp.controlling.tablemodels.ShowOrdersTableModel;

public class ShowOrderPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5756985248165665691L;

	private JLabel order_nr_label = new JLabel("Order-Nr.:");
	private JLabel order_date_label = new JLabel("Order-Datum:");
	private JLabel order_state_label = new JLabel("Order-Status:");
	private JTextField order_nr_filter = new JTextField();
	private JTextField order_date_filter = new JTextField();
	private JTextField order_state_filter = new JTextField();
	private JButton confirm_filter = new JButton("Filter");
	private JTable order_show = new JTable(new ShowOrdersTableModel());

	public ShowOrderPanel() {
		init();
	}

	private void init() {
		this.setLayout(new GridBagLayout());

		addComponent(order_nr_label, 0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_date_label, 1, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_state_label, 2, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_nr_filter, 0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_date_filter, 1, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_state_filter, 2, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(confirm_filter, 3, 1, 1, 1, 0.5, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(new JScrollPane(order_show), 0, 2, 3, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0);
	}

	public String getOrderNr() {
		return order_nr_filter.getText();
	}

	public String getOrderState() {
		return order_state_filter.getText();
	}

	public LocalDate getOrderDate() {
		if (order_date_filter.getText().isBlank()) {
			return null;
		} else {
			return LocalDate.parse(order_date_filter.getText());	//TODO safely parsing more types of dates
		}
	}

	public void setFilterListener(ActionListener l) {
		confirm_filter.addActionListener(l);
	}
	
	public void setEditOrdersListener(MouseInputListener l) {
		order_show.addMouseListener(l);
	}

	public void setData(Object[][] data) {
		((ShowOrdersTableModel) order_show.getModel()).setData(data);
	}

	public void reset() {
		order_nr_filter.setText("");
		order_date_filter.setText("");
		order_state_filter.setText("");
		confirm_filter.doClick();
	}
}
