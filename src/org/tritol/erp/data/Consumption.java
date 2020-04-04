package org.tritol.erp.data;

import java.time.LocalDate;
import java.util.HashSet;

public class Consumption {
	
	private LocalDate con_date;
	private String usage;
	private HashSet<Article> art_list = new HashSet<Article>();
	
	public Consumption(LocalDate con_date, String usage) {
		this.con_date = con_date;
		this.usage = usage;
	}
	
	public LocalDate getConDate() {
		return con_date;
	}
	
	public String getUsage() {
		return usage;
	}
	
	public HashSet<Article> getArtList(){
		return art_list;
	}
	
}
