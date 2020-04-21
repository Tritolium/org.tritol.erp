package org.tritol.erp.controlling.tablemodels;

import java.util.ArrayList;

public class AddArticlesTableModel extends AbstractArticlesTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6598748243409922877L;
	
	// CHANGE to Object[][]
	private ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columnIndex);
	}
	
	public Object[][] getData(){
		if(data.size() > 0) {
		Object[][] arr_data = new Object[data.size()][data.get(0).size()];
		for(int i = 0; i < data.size(); i++) {									//iterate over rows
			for(int j = 0; j < data.get(i).size(); j++) {
				arr_data[i][j] = data.get(i).get(j);
			}
		}
		return arr_data;
		}else {
			return null;
		}
	}
	
	public void setValueAt(Object value, int row, int column) {
		data.get(row).set(column, value);
		fireTableCellUpdated(row, column);
	}
	
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	public void addRow() {
		ArrayList<Object> rowData = new ArrayList<>();
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		data.add(rowData);
		fireTableDataChanged();
	}
	
	public void clearTable() {
		data = new ArrayList<ArrayList<Object>>();
	}
}
