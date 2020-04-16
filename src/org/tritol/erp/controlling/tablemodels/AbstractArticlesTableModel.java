package org.tritol.erp.controlling.tablemodels;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractArticlesTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Column Names
	 */
	protected final String[] columnNames = { "Pos._Nr", "Art.-Beschreibung", "Anzahl", "Einzelpreis" };

	protected Object[][] data;
	
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}
