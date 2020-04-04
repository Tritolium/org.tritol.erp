package org.tritol.erp.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;

public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2908069555472463715L;
	private int order_nr;
	private LocalDate order_date;
	private LocalDate payment_date;
	private LocalDate arrival_date;
	private OrderState state;
	private HashSet<Article> art_list = new HashSet<Article>();

	public Order(int order_nr, LocalDate order_date) {
		this.order_nr = order_nr;
		this.order_date = order_date;
		this.state = OrderState.ORDERED;
	}

	public int getOrderNr() {
		return order_nr;
	}
	
	public LocalDate getOrderDate() {
		return order_date;
	}
	
	public LocalDate getPaymentDate() {
		return payment_date;
	}
	
	public LocalDate getArrivalDate() {
		return arrival_date;
	}
	
	public OrderState getOrderState() {
		return state;
	}
	
	public HashSet<Article> getArticleList(){
		return art_list;
	}

	public void setState(OrderState state) {
		this.state = state;
		switch (state) {
		case ARRIVED:
			arrival_date = LocalDate.now();
			break;
		default:
			break;
		}
	}

	public void add(Article article) {
		art_list.add(article);
	}
	
	public void printOrder() {
		System.out.println("Ordernummer: " + order_nr);
		for(Article a : art_list) {
			a.print();
		}
	}

}