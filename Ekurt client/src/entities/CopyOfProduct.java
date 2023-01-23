package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextArea;

/**
 * this class receives the information for inventory update table
 *
 */
public class CopyOfProduct {

	/**
	 * product name
	 */
	private final SimpleStringProperty product;
	/**
	 * the inventory of the product
	 */
	private final SimpleStringProperty inventory;
	/**
	 * how match we want to update
	 */
	private TextArea update;

	/**
	 * Constructor
	 * 
	 * @param product
	 * @param inventory
	 */
	public CopyOfProduct(String product, String inventory) {

		this.product = new SimpleStringProperty(product);
		this.inventory = new SimpleStringProperty(inventory);
		this.update = new TextArea();
		update.setPrefWidth(75);
		update.setPrefHeight(30);
	}

	/**
	 * getter for product name
	 * 
	 * @return product
	 */
	public String getProduct() {
		return product.get();
	}

	/**
	 * getter for inventory
	 * 
	 * @return inventory
	 */
	public String getInventory() {
		return inventory.get();
	}

	/**
	 * getter for update String
	 * 
	 * @return
	 */
	public String getUpdateText() {
		if(update.getText().trim()==null || update.getText().trim().equals("")) {
			return "0";
		}
		return update.getText().trim();
	}

	/**
	 * getter for update
	 * 
	 * @return
	 */
	public TextArea getUpdate() {
		return update;
	}

	/**
	 * setter for product
	 * 
	 * @param product
	 */
	public void setProduct(String product) {
		this.product.set(product);
	}

	/**
	 * setter for inventory
	 * 
	 * @param inventory
	 */
	public void setInventory(String inventory) {
		this.inventory.set(inventory);
	}

	/**
	 * setter for update
	 * 
	 * @param update
	 */
	public void setUpdate(String update) {
		this.update.setText(update);
	}

}
