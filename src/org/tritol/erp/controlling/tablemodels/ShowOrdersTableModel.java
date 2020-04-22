package org.tritol.erp.controlling.tablemodels;

import javax.swing.table.AbstractTableModel;

public class ShowOrdersTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5660910734713715456L;

	private final String[] columnNames = { "Lieferant", "Order-Nr.", "Order-Datum", "Liefer-Datum", "Status" };

	private Object[][] data;

	@Override
	public int getRowCount() {
		if (data != null) {
			return data.length;
		}
		return 0;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		// CHANGE representation of order date
		case 4:
			if (data[rowIndex][4].equals("a")) {
				return "geliefert";
			}
			if (data[rowIndex][4].equals("o")) {
				return "bestellt";
			}
		default:
			return data[rowIndex][columnIndex];
		}
	}

	public void setData(Object[][] d) {
		data = d;
		this.fireTableDataChanged();
	}

}
