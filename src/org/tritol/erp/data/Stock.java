package org.tritol.erp.data;

import java.io.Serializable;

public class Stock implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4444052758446813007L;
	private String pos_nr;
	private String description;
	private int quantity;
	
	public Stock(String pos_nr, String description, int quantity) {
		this.pos_nr = pos_nr;
		this.description = description;
		this.quantity = quantity;
	}
	
	public String getPosNr() {
		return pos_nr;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void printStock() {
		System.out.println(pos_nr + "  " + description + "  " + quantity);
	}
	
}
