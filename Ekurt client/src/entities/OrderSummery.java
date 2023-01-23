package entities;

import javafx.beans.property.SimpleStringProperty;

/**
 * this class receives the information for order summary and display as a row in
 * table
 * 
 * @author Avner
 *
 */
public class OrderSummery {

	/**
	 * product name
	 */
	private final SimpleStringProperty product;
	/**
	 * amount from the product
	 */
	private final SimpleStringProperty amount;
	/**
	 * the total price amount * price
	 */
	private final SimpleStringProperty price;

	/**
	 * Constructor
	 * 
	 * @param product
	 * @param amount
	 * @param price
	 */
	public OrderSummery(String product, String amount, String price) {

		this.product = new SimpleStringProperty(product);
		this.amount = new SimpleStringProperty(amount);
		this.price = new SimpleStringProperty(price);

	}

	/**
	 * getter for product
	 * 
	 * @return product name
	 */
	public String getProduct() {
		return product.get();
	}

	/**
	 * getter for amount
	 * 
	 * @return amount
	 */
	public String getAmount() {
		return amount.get();
	}

	/**
	 * getter for price
	 * 
	 * @return price
	 */
	public String getPrice() {
		return price.get();
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
	 * setter for amount
	 * 
	 * @param amount
	 */
	public void setAmount(String amount) {
		this.amount.set(amount);
	}

	/**
	 * setter for price
	 * 
	 * @param price
	 */
	public void setPrice(String price) {
		this.price.set(price);
	}

}
