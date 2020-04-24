package org.tritol.erp.controlling.tablemodels;

import java.time.LocalDate;

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
		case 2: // order_date
			return formatDate((LocalDate) data[rowIndex][columnIndex]);
		case 3: // arrival_date
			return formatDate((LocalDate) data[rowIndex][columnIndex]);
		case 4: // order_state
			if (data[rowIndex][columnIndex].equals("a")) {
				return "geliefert";
			}
			if (data[rowIndex][columnIndex].equals("o")) {
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

	private String formatDate(LocalDate date) {
		StringBuilder date_string_builder;
		if (date != null) {
			date_string_builder = new StringBuilder();
			date_string_builder.append(date.getDayOfMonth());
			date_string_builder.append(".");
			date_string_builder.append(date.getMonthValue());
			date_string_builder.append(".");
			date_string_builder.append(date.getYear());
			return date_string_builder.toString();
		}
		return "---";
	}

}
