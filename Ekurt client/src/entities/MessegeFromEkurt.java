package entities;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class receives the information for messages and display in table
 * 
 * @author Avner
 *
 */
public class MessegeFromEkurt {

	/**
	 * user name that send the message
	 */
	private final SimpleStringProperty from;
	/**
	 * the content of the message
	 */
	private final SimpleStringProperty messege;

	/**
	 * Contractor
	 * 
	 * @param from    user name
	 * @param messege content of the message
	 */
	public MessegeFromEkurt(String from, String messege) {

		this.from = new SimpleStringProperty(from);
		this.messege = new SimpleStringProperty(messege);

	}

	/**
	 * getter for from
	 * 
	 * @return String user name
	 */
	public String getFrom() {
		return from.get();
	}

	/**
	 * getter for message
	 * 
	 * @return string message
	 */
	public String getMessege() {
		return messege.get();
	}

	/**
	 * setter for from
	 * 
	 * @param from user name
	 */
	public void setFrom(String from) {
		this.from.set(from);
	}

	/**
	 * setter for message
	 * 
	 * @param messege
	 */
	public void setMessege(String messege) {
		this.messege.set(messege);
	}

}
