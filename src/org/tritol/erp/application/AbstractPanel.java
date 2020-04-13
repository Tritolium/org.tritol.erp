package org.tritol.erp.application;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;

public abstract class AbstractPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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

}
