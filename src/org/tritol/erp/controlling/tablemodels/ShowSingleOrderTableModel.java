package org.tritol.erp.controlling.tablemodels;

public class ShowSingleOrderTableModel extends AbstractArticlesTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6315621847627616669L;
	
	
	
	public void setArticles(Object[][] articles) {
		this.data = new Object[articles.length][4];
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < 4; j++) {
				data[i][j] = articles[i][j+1];
			}
		}
	}

}
