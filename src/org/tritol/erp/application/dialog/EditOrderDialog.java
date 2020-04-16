package org.tritol.erp.application.dialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class EditOrderDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5165353902396148889L;

	private JLabel order_id_label = new JLabel("Order-Nummer:");
	private JLabel order_id = new JLabel();

	public EditOrderDialog(int order_id) {
		init(order_id);
	}

	private void init(int o_id) {
		this.order_id.setText("" + o_id);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setLayout(new GridBagLayout());
		this.setBounds(100, 100, 300, 200);

		addComponent(order_id_label, 0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0);
		addComponent(order_id, 1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0);

		this.setVisible(true);
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
