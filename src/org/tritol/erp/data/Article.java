package org.tritol.erp.data;

import java.io.Serializable;

public class Article implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6752638419857269647L;
	private String pos_nr;
	private String art_desc;
	private int quantity;
	private double price;
	
	public Article(String pos_nr, String art_desc, int quantity, double price) {
		this.pos_nr = pos_nr;
		this.art_desc = art_desc;
		this.quantity = quantity;
		this.price = price;
	}
	
	public String getPosNr() {
		return pos_nr;
	}
	
	public String getArtDesc() {
		return art_desc;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void print() {
		System.out.println(pos_nr + "  " + art_desc + "  " + quantity + "  " + price);
	}
	
}
