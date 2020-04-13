package org.tritol.erp.application;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainView extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5530344209795531961L;

	public static final int MAINVIEW = 0;
	public static final int ADDORDER = 1;
	public static final int SHOWORDER = 2;
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu orderMenu = new JMenu("Order");
	private JMenuItem addOrderItem = new JMenuItem("Hinzufügen");
	private JMenuItem showOrderItem = new JMenuItem("Anzeigen");
	
	private AbstractPanel mainView = new MainPanel();
	
	private AbstractPanel mainPanel = new MainPanel();
	private AddOrderPanel addOrderPanel = new AddOrderPanel();
	private ShowOrderPanel showOrderPanel = new ShowOrderPanel();
	
	public MainView() {
		super("ERP");
		init();
	}
	
	private void init() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 750, 500);
		this.add(menuBar, BorderLayout.NORTH);
		menuBar.add(orderMenu);
		orderMenu.add(addOrderItem);
		orderMenu.add(showOrderItem);
		
		this.add(mainView, BorderLayout.CENTER);
	}
	
	public void setAddOrderListener(ActionListener l) {
		this.addOrderItem.addActionListener(l);
	}
	
	public void setShowOrderListener(ActionListener l) {
		this.showOrderItem.addActionListener(l);
	}
	
	public void setAddArticleListener(ActionListener l) {
		this.addOrderPanel.setAddArticleListener(l);
	}
	
	public void setConfirmOrderListener(ActionListener l) {
		this.addOrderPanel.setConfirmOrderListener(l);
	}
	
	public void setGetOrdersFilterListener(ActionListener l) {
		this.showOrderPanel.setFilterListener(l);
	}
	
	public void setView(int panel) {
		this.remove(mainView);
		switch(panel) {
		case MAINVIEW:
			mainView = mainPanel;
			break;
		case ADDORDER:
			mainView = addOrderPanel;
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
	
	public AddOrderPanel getAddOrderView() {
		return addOrderPanel;
	}
	
	public ShowOrderPanel getShowOrderView() {
		return showOrderPanel;
	}
	
}
