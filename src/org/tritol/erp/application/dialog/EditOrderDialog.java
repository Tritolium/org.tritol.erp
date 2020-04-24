package org.tritol.erp.application.dialog;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.tritol.erp.controlling.tablemodels.ShowSingleOrderTableModel;

public class EditOrderDialog extends AbstractDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5165353902396148889L;

	private JLabel order_id_label = new JLabel("Order-Nummer: ");
	private JLabel order_date_label = new JLabel("Order-Datum: ");
	private JLabel order_state_label = new JLabel("Order-Status: ");

	private JLabel order_deliverydate_label = new JLabel("Liefer-Datum: ");

	private JLabel order_id = new JLabel();
	private JLabel order_date = new JLabel();
	private JLabel order_state = new JLabel();

	private JTextField order_deliverydate = new JTextField();

	private JTable show_articles = new JTable(new ShowSingleOrderTableModel());

	private JButton confirm_order = new JButton("Bestätigen");

	public EditOrderDialog(String order_id) {
		init(order_id);
	}

	private void init(String o_id) {
		this.order_id.setText(o_id);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModalityType(ModalityType.MODELESS);
		this.setLayout(new GridBagLayout());
		this.setBounds(100, 100, 750, 500);

		// add labels
		addComponent(order_id_label, 0, 0, 1, 1, 0, 0, WEST, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_date_label, 0, 1, 1, 1, 0, 0, WEST, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_state_label, 0, 2, 1, 1, 0, 0, WEST, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_deliverydate_label, 2, 0, 1, 1, 0, 0, WEST, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		// add order-specifics
		addComponent(order_id, 1, 0, 1, 1, 1, 0, WEST, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_date, 1, 1, 1, 1, 1, 0, WEST, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_state, 1, 2, 1, 1, 1, 0, WEST, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_deliverydate, 3, 0, 1, 1, 1, 0, WEST, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
		// add article list
		addComponent(new JScrollPane(show_articles), 0, 3, 5, 4, 0, 1, CENTER, BOTH, new Insets(0, 0, 0, 0), 0, 0);
		// add button
		addComponent(confirm_order, 3, 7, 1, 1, 0, 0, WEST, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
	}
	
	public LocalDate getDeliveryDate() {
		if (matchDate(order_deliverydate.getText())) {
			String[] date = order_deliverydate.getText().split("\\.");
			return LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
		} else {
			return null;
		}
	}
	
	public String getOrderNr() {
		return order_id.getText();
	}

	public void setOrderData(LocalDate order_date, String order_state) {
		this.order_date.setText(order_date.toString());
		if (order_state.equals("o")) {
			this.order_state.setText("bestellt");
		} else if (order_state.equals("a")) {
			this.order_state.setText("geliefert");
		}
		this.setVisible(true);
	}

	public void setArticles(Object[][] articles) {
		((ShowSingleOrderTableModel) show_articles.getModel()).setArticles(articles);
	}
	
	public void setConfirmOrderListener(ActionListener l) {
		this.confirm_order.addActionListener(l);
	}
}
