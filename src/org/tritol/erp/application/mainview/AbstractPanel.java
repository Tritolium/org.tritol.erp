package org.tritol.erp.application.mainview;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;

public abstract class AbstractPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final int NONE = GridBagConstraints.NONE;
	protected static final int EAST = GridBagConstraints.EAST;
	protected static final int WEST = GridBagConstraints.WEST;
	protected static final int CENTER = GridBagConstraints.CENTER;
	protected static final int HORIZONTAL = GridBagConstraints.HORIZONTAL;
	protected static final int BOTH = GridBagConstraints.BOTH;

	public void reset() {

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
	protected void addComponent(Component comp, int x, int y, int height, int width, double weightx, double weighty,
			int anchor, int fill, Insets insets, int ipadx, int ipady) {
		GridBagConstraints gbc = new GridBagConstraints(x, y, width, height, weightx, weighty, anchor, fill, insets,
				ipadx, ipady);

		this.add(comp, gbc);

	}
	
	protected abstract void init();

}
