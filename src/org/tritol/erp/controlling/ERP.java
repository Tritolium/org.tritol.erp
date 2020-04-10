package org.tritol.erp.controlling;

public class ERP {
	static Controller controller;
	
	public static void main(String[] args) {
		controller = new Controller();
		
		controller.showView();
	}
}
