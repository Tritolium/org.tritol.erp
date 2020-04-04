package org.tritol.erp.data;

import java.time.LocalDate;
import java.util.HashSet;

public class DataController {
	
	private static DataController instance;
	
	private HashSet<Order> order_list;
	private HashSet<Stock> stock_list;
	private HashSet<Consumption> con_list;
	
	public DataController() {
		order_list = new HashSet<Order>();
		stock_list = new HashSet<Stock>();
		con_list = new HashSet<Consumption>();
	}
	
	public static DataController getInstance() {
		if(instance == null) {
			instance = new DataController();
		}
		return instance;
	}

	public void addOrder(int order_nr, LocalDate order_date) {
		order_list.add(new Order(order_nr, order_date));
	}

	public void addToOrder(int order_nr, String pos_nr, String art_desc, int quantity, double price) {
		getOrder(order_nr).add(new Article(pos_nr, art_desc, quantity, price));
	}
	
	public void setOrderState(int order_nr, OrderState state) {
		getOrder(order_nr).setState(state);
	}
	
	private void addToStock(String pos_nr, int quantity) throws ArticleNotRegisteredException {
		Stock s = getStock(pos_nr);
		if(s!=null) {
			s.setQuantity(s.getQuantity()+quantity);
		}else {
			throw new ArticleNotRegisteredException();
		}
	}
	
	public void addOrderToStock(int order_nr) {
		Order order = getOrder(order_nr);
		for(Article a : order.getArticleList()) {
			try {
				addToStock(a.getPosNr(), a.getQuantity());
			} catch(ArticleNotRegisteredException e) {
				addNewPosNr(a.getPosNr(), a.getArtDesc(),a.getQuantity());
			}
		}
	}
	
	private void addNewPosNr(String pos_nr, String art_desc, int quantity) {
		stock_list.add(new Stock(pos_nr, art_desc, quantity));
	}
	
	public void addConsumption(LocalDate con_date, String usage) {
		con_list.add(new Consumption(con_date, usage));
	}
	
	public Order getOrder(int order_nr) {
		for (Order o : order_list) {
			if (o.getOrderNr() == order_nr) {
				return o;
			}
		}
		return null;
	}
	
	public Stock getStock(String pos_nr) {
		for(Stock s : stock_list) {
			if(s.getPosNr().equals(pos_nr)) {
				return s;
			}
		}
		return null;
	}
	
}
