package org.tritol.erp.application.mainview;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.tritol.erp.controlling.tablemodels.ShowStockTableModel;

public class ShowStockPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6609227787428511563L;

	private JPanel filterPanel = new JPanel();

	private JLabel pos_nr_label = new JLabel("Pos.-Nr.:");
	private JLabel art_desc_label = new JLabel("Art.-Beschreibung:");

	private JTextField pos_nr_filter = new JTextField();
	private JTextField art_desc_filter = new JTextField();

	private JButton confirm_filter = new JButton("Filter");

	private JTable showStock = new JTable(new ShowStockTableModel());

	public ShowStockPanel() {
		init();
	}

	@Override
	protected void init() {
		// TODO show the stock

		this.setLayout(new BorderLayout());

		filterPanel.setLayout(new GridLayout(2, 3));

		filterPanel.add(pos_nr_label);
		filterPanel.add(art_desc_label);
		filterPanel.add(confirm_filter);
		filterPanel.add(pos_nr_filter);
		filterPanel.add(art_desc_filter);

		this.add(filterPanel, BorderLayout.NORTH);

		this.add(new JScrollPane(showStock), BorderLayout.CENTER);

		this.setVisible(true);
	}

	public String getPosNr() {
		return pos_nr_filter.getText();
	}

	public String getArtDesc() {
		return art_desc_filter.getText();
	}

	public void setData(Object[][] data) {
		((ShowStockTableModel) showStock.getModel()).setData(data);
	}

	public void setConfirmFilterListener(ActionListener l) {
		confirm_filter.addActionListener(l);
	}

}
