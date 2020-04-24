package org.tritol.erp.application.mainview;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5530344209795531961L;

	public static final int MAINVIEW = 0;
	public static final int SHOWSTOCK = 1;
	public static final int SHOWORDER = 2;

	private JMenuBar menuBar = new JMenuBar();

	private JMenu erpMenu = new JMenu("ERP");
	private JMenuItem closeErp = new JMenuItem("Beenden");

	private JMenu orderMenu = new JMenu("Einkauf");
	private JMenuItem addOrderItem = new JMenuItem("Erfassen");
	private JMenuItem showOrderItem = new JMenuItem("Anzeigen");
	private JMenuItem addSupplierItem = new JMenuItem("Lieferant anlegen");

	private JMenu stockMenu = new JMenu("Lager");
	private JMenuItem showStockItem = new JMenuItem("Anzeigen");

	private AbstractPanel mainView = new MainPanel();

	private AbstractPanel mainPanel = new MainPanel();
	private ShowOrderPanel showOrderPanel = new ShowOrderPanel();
	private ShowStockPanel showStockPanel = new ShowStockPanel();

	public MainFrame() {
		super("ERP");
		init();
	}

	private void init() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 750, 500);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.add(menuBar, BorderLayout.NORTH);

		menuBar.add(erpMenu);
		erpMenu.add(closeErp);

		menuBar.add(orderMenu);
		orderMenu.add(addOrderItem);
		orderMenu.add(showOrderItem);
		orderMenu.add(addSupplierItem);

		menuBar.add(stockMenu);
		stockMenu.add(showStockItem);

		this.add(mainView, BorderLayout.CENTER);
	}

	public void setCloseERPListener(ActionListener l) {
		closeErp.addActionListener(l);
	}

	public void setAddOrderListener(ActionListener l) {
		this.addOrderItem.addActionListener(l);
	}

	public void setShowOrderListener(ActionListener l) {
		this.showOrderItem.addActionListener(l);
	}

	public void setAddSupplierListener(ActionListener l) {
		this.addSupplierItem.addActionListener(l);
	}

	public void setShowStockListener(ActionListener l) {
		this.showStockItem.addActionListener(l);
	}

	public void setGetOrdersFilterListener(ActionListener l) {
		this.showOrderPanel.setFilterListener(l);
	}

	public void setEditOrdersListener(MouseInputListener l) {
		this.showOrderPanel.setEditOrdersListener(l);
	}

	public void setView(int panel) {
		this.remove(mainView);
		switch (panel) {
		case MAINVIEW:
			mainView = mainPanel;
			break;
		case SHOWSTOCK:
			mainView = showStockPanel;
			break;
		case SHOWORDER:
			mainView = showOrderPanel;
			break;
		}
		mainView.reset();
		this.add(mainView, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
		this.setVisible(true);
	}

	public JPanel getView() {
		return mainPanel;
	}

	public ShowOrderPanel getShowOrderView() {
		return showOrderPanel;
	}

	public ShowStockPanel getShowStockView() {
		return showStockPanel;
	}
}
