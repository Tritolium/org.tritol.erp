package org.tritol.erp.application.dialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JDialog;

public abstract class AbstractDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7168295588576016963L;
	protected static final int CENTER = GridBagConstraints.CENTER;
	protected static final int HORIZONTAL = GridBagConstraints.HORIZONTAL;

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
	protected void addComponent(Component comp, int x, int y, int width, int height, double weightx, double weighty,
			int anchor, int fill, Insets insets, int ipadx, int ipady) {
		GridBagConstraints gbc = new GridBagConstraints(x, y, width, height, weightx, weighty, anchor, fill, insets,
				ipadx, ipady);

		this.add(comp, gbc);

	}
}
