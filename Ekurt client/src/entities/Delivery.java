package entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 *  * The Delivery class represents a delivery in a delivery management system. 
 * It contains properties for the order number, address, customer name, customer phone number, and delivery date. 
 * It also contains a ComboBox for the delivery's status.
 * @author matan turjeman
 *
 */
public class Delivery {
	private final SimpleStringProperty OrderNumber;
	private final SimpleStringProperty Address;
	private final SimpleStringProperty Customer_Name;
	private final SimpleStringProperty Customer_Phone_Number;
	private final SimpleStringProperty Delivery_Date;
	private ComboBox<String> Status;

	/**
	 * Constructor for creating a new Delivery object.
	 * @param orderNumber
	 * @param address
	 * @param customer_Name
	 * @param customer_Phone_Number
	 * @param delivery_Date
	 * @param data
	 */
	public Delivery(String orderNumber, String address, String customer_Name, String customer_Phone_Number,
			String delivery_Date, ObservableList data) {
	
		this.OrderNumber = new SimpleStringProperty(orderNumber);
		this.Address = new SimpleStringProperty(address);
		this.Customer_Name = new SimpleStringProperty(customer_Name);
		this.Customer_Phone_Number = new SimpleStringProperty(customer_Phone_Number);
		this.Delivery_Date = new SimpleStringProperty(delivery_Date);
		this.Status = new ComboBox(data);
	}

	/**
	 * Get the OrderNumber of the Delivery.
	 * @return
	 */
	public String getOrderNumber() {
		return OrderNumber.get();
	}
	/**
	 * Get the Address of the Delivery.
	 * @return
	 */
	public String getAddress() {
		return Address.get();
	}
	/**
	 * Get the Customer_Name of the Delivery.
	 * @return
	 */
	public String getCustomer_Name() {
		return Customer_Name.get();
	}
	/**
	 * Get the Customer_Phone_Number of the Delivery.
	 * @return
	 */
	public String getCustomer_Phone_Number() {
		return Customer_Phone_Number.get();
	}
	/**
	 * Get the Delivery_Date of the Delivery.
	 * @return
	 */
	public String getDelivery_Date() {
		return  Delivery_Date.get();
	}
	/**
	 * Get the Status of the Delivery.
	 * @return
	 */
	public ComboBox getStatus() {
		return  Status;
	}
	/**
	 * Set the OrderNumber of the Delivery.
	 * @param orderNumber
	 */
	public void setOrderNumber(String orderNumber) {
		OrderNumber.set(orderNumber);
	}
	/**
	 * Set the Status of the Address.
	 * @param address
	 */
	public void setAddress(String address) {
		Address.set(address);
	}
	/**
	 * Set the Customer_Name of the Delivery.
	 * @param customer_Name
	 */
	public void setCustomer_Name(String customer_Name) {
		Customer_Name.set(customer_Name);
	}
	/**
	 * Set the customer_Phone_Number of the Delivery.
	 * @param customer_Phone_Number
	 */
	public void setCustomer_Phone_Number(String customer_Phone_Number) {
		Customer_Phone_Number.set(customer_Phone_Number);
	}
	/**
	 * Set the delivery_Date of the Delivery.
	 * @param delivery_Date
	 */
	public void setDelivery_Date(String delivery_Date) {
		Delivery_Date.set(delivery_Date);
	}
	/**
	 * Set the Status of the Delivery.
	 * @param status
	 */
	public void setStatus(String status) {
		this.Status.setValue(status);
	}
	
	
	
	
	
	
	
	
	

}
