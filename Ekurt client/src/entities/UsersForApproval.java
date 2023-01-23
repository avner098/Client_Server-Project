package entities;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * this class receives the information for the user need to be approval to be a
 * costumer
 * 
 * @author
 *
 */
public class UsersForApproval {
	/**
	 * name
	 */
	private SimpleStringProperty Name;
	/**
	 * id
	 */
	private SimpleStringProperty ID;
	/**
	 * phone number
	 */
	private SimpleStringProperty PhoneNumber;
	/**
	 * email addres
	 */
	private SimpleStringProperty Email;
	/**
	 * region
	 */
	private SimpleStringProperty Region;
	// private SimpleStringProperty SubscriberNumber; //removed from constructor
	/**
	 * approve / declined
	 */
	private ComboBox<String> Status;

	/**
	 * user name
	 */
	private String userName;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param id
	 * @param pNumber
	 * @param email
	 * @param region
	 * @param username
	 * @param data
	 */
	public UsersForApproval(String name, String id, String pNumber, String email, String region,
			ObservableList data, String username) {
		this.Name = new SimpleStringProperty(name);
		this.ID = new SimpleStringProperty(id);
		this.PhoneNumber = new SimpleStringProperty(pNumber);
		this.Email = new SimpleStringProperty(email);
		this.Region = new SimpleStringProperty(region);
		// this.SubscriberNumber = new SimpleStringProperty(subscriberNumber);
		this.Status = new ComboBox(data);
		this.userName = username;
	}

	/**
	 * getter for user name
	 * 
	 * @return user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * setter for user name
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * getter for name
	 * 
	 * @return
	 */
	public String getName() {
		return Name.get();
	}

	/**
	 * setter for name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		Name.set(name);
	}

	/**
	 * getter for id
	 * 
	 * @return
	 */
	public String getID() {
		return ID.get();
	}

	/**
	 * setter for id
	 * 
	 * @param id
	 */
	public void setID(String id) {
		ID.set(id);
	}

	/**
	 * getter for phone number
	 * 
	 * @return
	 */
	public String getPhoneNumber() {
		return PhoneNumber.get();
	}

	/**
	 * setter for phone number
	 * 
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber.set(phoneNumber);
	}

	/**
	 * getter for Email
	 * 
	 * @return
	 */
	public String getEmail() {
		return Email.get();
	}

	/**
	 * setter for Email
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		Email.set(email);
	}

	/**
	 * getter for Region
	 * 
	 * @return
	 */
	public String getRegion() {
		return Region.get();
	}

	/**
	 * setter for Region
	 * 
	 * @param region
	 */
	public void setRegion(String region) {
		Region.set(region);
	}

	/*
	 * public String getSubscriberNumber() { return SubscriberNumber.get(); }
	 * 
	 * public void setSubscriberNumber(String subscriberNumber) {
	 * SubscriberNumber.set(subscriberNumber); }
	 */

	/**
	 * getter for Status
	 * 
	 * @return
	 */
	public ComboBox<String> getStatus() {
		return Status;
	}

	/**
	 * setter for Status
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.Status.setValue(status);
	}
}
