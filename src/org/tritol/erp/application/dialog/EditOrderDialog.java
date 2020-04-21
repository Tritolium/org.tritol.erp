package org.tritol.erp.application.dialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.tritol.erp.controlling.tablemodels.ShowSingleOrderTableModel;

public class EditOrderDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5165353902396148889L;

	private JLabel order_id_label = new JLabel("Order-Nummer: ");
	private JLabel order_date_label = new JLabel("Order-Datum: ");
	private JLabel order_state_label = new JLabel("Order-Status: ");

	private JLabel order_id = new JLabel();
	private JLabel order_date = new JLabel();
	private JLabel order_state = new JLabel();

	private JTable show_articles = new JTable(new ShowSingleOrderTableModel());

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
		addComponent(order_id_label, 0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_date_label, 0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_state_label, 0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		// add order-specifics
		addComponent(order_id, 1, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_date, 1, 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_state, 1, 2, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		// add article list
		addComponent(new JScrollPane(show_articles), 0, 3, 3, 4, 0, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
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

	/**
	 * 
	 * @param comp
	 * @param x
	 * @param y
	 * @param height
	 * @param width
	 * @param weightx
	 * @param weighty
	 * @param anchor
	 * @param fill
	 * @param insets
	 * @param ipadx
	 * @param ipady
	 */
	private void addComponent(Component comp, int x, int y, int height, int width, double weightx, double weighty,
			int anchor, int fill, Insets insets, int ipadx, int ipady) {
		GridBagConstraints gbc = new GridBagConstraints(x, y, width, height, weightx, weighty, anchor, fill, insets,
				ipadx, ipady);

		this.add(comp, gbc);

	}
}
