package org.tritol.erp.application.mainview;

import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.tritol.erp.controlling.tablemodels.ShowStockTableModel;

public class ShowStockPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6609227787428511563L;

	private JTable showStock = new JTable(new ShowStockTableModel());
	
	public ShowStockPanel() {
		init();
	}

	@Override
	protected void init() {
		// TODO show the stock
		this.setLayout(new GridBagLayout());

		addComponent(new JScrollPane(showStock), 0, 1, 1, 1, 1, 1, CENTER, BOTH, new Insets(0, 0, 0, 0), 0, 0);

		this.setVisible(true);
	}
	
	public void setData(Object[][] data) {
		((ShowStockTableModel) showStock.getModel()).setData(data);
	}

}
