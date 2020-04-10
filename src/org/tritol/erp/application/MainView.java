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
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu addMenu = new JMenu("Add");
	private JMenuItem orderItem = new JMenuItem("Order");
	
	private JPanel mainPanel = new JPanel();
	private AddOrderPanel addOrderPanel = new AddOrderPanel();
	
	public MainView() {
		super("ERP");
		init();
	}
	
	private void init() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setBounds(200, 200, 500, 350);
		this.add(menuBar, BorderLayout.NORTH);
		menuBar.add(addMenu);
		addMenu.add(orderItem);
		
		this.add(mainPanel, BorderLayout.CENTER);
	}
	
	public void setAddOrderListener(ActionListener l) {
		this.orderItem.addActionListener(l);
	}
	
	public void setAddArticleListener(ActionListener l) {
		this.addOrderPanel.setAddArticleListener(l);
	}
	
	public void setConfirmOrderListener(ActionListener l) {
		this.addOrderPanel.setConfirmOrderListener(l);
	}
	
	public void setView(int panel) {
		this.remove(mainPanel);
		switch(panel) {
		case MAINVIEW:
			mainPanel = new JPanel();
			break;
		case ADDORDER:
			mainPanel = addOrderPanel;
			break;
		}
		this.add(mainPanel, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
		this.setVisible(true);
	}
	
	public JPanel getView() {
		return mainPanel;
	}
	
	public AddOrderPanel getOrderView() {
		return addOrderPanel;
	}
	
	public void addArticleRow() {
		addOrderPanel.addArticleRow();
	}
	
}
