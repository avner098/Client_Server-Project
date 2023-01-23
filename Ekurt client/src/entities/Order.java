
package entities;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class is responsible for a customer's order, so that in order management
 * the customer can see his orders. The class receives all the details that
 * appear in order management.
 */
public class Order {
	/**
	 * the columns names of the manage order
	 */
	private SimpleStringProperty order_status;
	private SimpleStringProperty order_number;
	private SimpleStringProperty price;
	private SimpleStringProperty dlivery;
	private SimpleStringProperty addres;
	private SimpleStringProperty payment;
	private SimpleStringProperty amount;
	private SimpleStringProperty product;
	private SimpleStringProperty machine;
	private SimpleStringProperty time;

	/**
	 * Contractor
	 * 
	 * @param order_number
	 * @param delivery
	 * @param address
	 * @param Payment_method
	 * @param products
	 * @param amount
	 * @param price
	 * @param status
	 * @param machine
	 * @param time
	 */
	public Order(String order_number, String delivery, String address, String Payment_method, String products,
			String amount, String price, String status, String machine, String time) {
		this.order_status = new SimpleStringProperty(status);
		this.amount = new SimpleStringProperty(amount);
		this.order_number = new SimpleStringProperty(order_number);
		this.price = new SimpleStringProperty(price);
		this.dlivery = new SimpleStringProperty(delivery);
		this.addres = new SimpleStringProperty(address);
		this.product = new SimpleStringProperty(products);
		this.payment = new SimpleStringProperty(Payment_method);
		this.machine = new SimpleStringProperty(machine);
		this.time = new SimpleStringProperty(time);

	}

	/**
	 * Returns the name of the machine
	 */
	public String getMachine() {
		return machine.getValue();
	}

	/**
	 * Enters a value for the machine name
	 * 
	 * @param machine
	 */
	public void setMachine(SimpleStringProperty machine) {
		this.machine = machine;
	}

	/**
	 * Returns the time value
	 */
	public String getTime() {
		return time.getValue();
	}

	/**
	 * Enters a value into the time field
	 * 
	 * @param time
	 */
	public void setTime(SimpleStringProperty time) {
		this.time = time;
	}

	/**
	 * Returns the value in the status field
	 */
	public String getOrder_status() {
		return order_status.getValue();
	}

	/**
	 * Enters a value in the status field
	 */
	public void setOrder_status(SimpleStringProperty order_status) {
		this.order_status = order_status;
	}

	/**
	 * Returns the order number
	 */
	public String getOrder_number() {
		return order_number.getValue();
	}

	/**
	 * Enters a value in the order number field
	 */
	public void setOrder_number(SimpleStringProperty order_number) {
		this.order_number = order_number;
	}

	/**
	 * Returns the price of the order
	 */
	public String getPrice() {
		return price.getValue();
	}

	/**
	 * Inserts the order price into a price value
	 */
	public void setPrice(SimpleStringProperty price) {
		this.price = price;
	}

	/**
	 * Returns if the order is a pick up delivery or an order on the spot
	 */
	public String getDlivery() {
		return dlivery.getValue();
	}

	/**
	 * Enters the method in which the customer wishes to receive the order
	 */
	public void setDlivery(SimpleStringProperty dlivery) {
		this.dlivery = dlivery;
	}

	/**
	 * Returns the address of the customer
	 */
	public String getAddres() {
		return addres.getValue();
	}

	/**
	 * Enter the customer's address in the field
	 */
	public void setAddres(SimpleStringProperty addres) {
		this.addres = addres;
	}

	/**
	 * Returns the payment method
	 */
	public String getPayment() {
		return payment.getValue();
	}

	/**
	 * Enter the payment method in the field
	 */
	public void setPayment(SimpleStringProperty payment) {
		this.payment = payment;
	}

	/**
	 * Returns the quantity of products
	 */
	public String getAmount() {
		return amount.getValue();
	}

	/**
	 * Enter the quantity of the products
	 */
	public void setAmount(SimpleStringProperty amount) {
		this.amount = amount;
	}

	/**
	 * returns the product's name
	 */
	public String getProduct() {
		return product.getValue();
	}

	/**
	 * Enter the product name
	 */
	public void setProduct(SimpleStringProperty product) {
		this.product = product;
	}
}
