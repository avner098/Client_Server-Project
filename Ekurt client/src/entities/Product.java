package entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class is responsible for adding the selected product to the shopping
 * cart. The class receives the details of the product.
 *
 */
public class Product {
	
	/**
	 *the columns names of the table 
	 */
	private SimpleStringProperty name;
	private SimpleIntegerProperty amount;
	private SimpleIntegerProperty price;

	/**
	 * The constructor of the class
	 * @param name
	 * @param amount
	 * @param price
	 */
	public Product(String name, int amount, int price) {
		this.name = new SimpleStringProperty(name);
		this.amount = new SimpleIntegerProperty(amount);
		this.price = new SimpleIntegerProperty(price);

	}

	
	/**
	 *return the product name
	 */
	public String getName() {
		return name.getValue();
	}
	
	/**
	 * set the product name
	 * @param name
	 */
	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	
	/**
	 *return the amount of the specific product
	 */
	public Integer getAmount() {
		return amount.getValue();
	}

	
	/**
	 * set the amount of the specific product
	 * @param amount
	 */
	public void setAmount(SimpleIntegerProperty amount) {
		this.amount = amount;
	}
	
	/**
	 * return the price of the specific product
	 */
	public Integer getPrice() {
		return price.getValue();
	}

	/**
	 * set the price of the specific product
	 * @param price
	 */
	public void setPrice(SimpleIntegerProperty price) {
		this.price = price;
	}

}
