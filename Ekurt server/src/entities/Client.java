package entities;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Entity class that implements a row in a connected client table
 */
public class Client {

	private final SimpleStringProperty IP;
	private final SimpleStringProperty Host;
	private final SimpleStringProperty Status;

	/**
	 * Contractor
	 * 
	 * @param IP     client ip address
	 * @param Host   host ip address
	 * @param Status connected / disconnected
	 */
	public Client(String IP, String Host, String Status) {
		this.IP = new SimpleStringProperty(IP);
		this.Host = new SimpleStringProperty(Host);
		this.Status = new SimpleStringProperty(Status);
	}

	/**
	 * getter for IP
	 * 
	 * @return ip String
	 */
	public String getIP() {
		return IP.get();
	}

	/**
	 * setter for ip
	 * 
	 * @param ip
	 */
	public void setIP(String ip) {
		IP.set(ip);
	}

	/**
	 * getter for host
	 * 
	 * @return host String
	 */
	public String getHost() {
		return Host.get();
	}

	/**
	 * setter for host
	 * 
	 * @param host
	 */
	public void setHost(String host) {
		Host.set(host);
	}

	/**
	 * getter for status
	 * 
	 * @return status String
	 */
	public String getStatus() {
		return Status.get();
	}

	/**
	 * setter for status
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		Status.set(status);
	}
}
