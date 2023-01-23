package entities;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

//19 Columns
/**
 * @author this class receives the information for the low bar for each machine
 *
 */
public class MinimumAmount {
	/**
	 * machine id
	 */
	private String machineID;
	/**
	 * product name
	 */
	private String productName;
	/**
	 * the new low bar
	 */
	private String newMinAmount;

	/**
	 * constractor
	 * 
	 * @param machineID
	 * @param productName
	 * @param selectedAmount
	 */
	public MinimumAmount(String machineID, String productName, String selectedAmount) {
		this.machineID = machineID;
		this.productName = productName;
		this.newMinAmount = selectedAmount;
	}

	/**
	 * getter for machine id
	 * 
	 * @return
	 */
	public String getMachineID() {
		return machineID;
	}

	/**
	 * setter for machine id
	 * 
	 * @param machineID
	 */
	public void setMachineID(String machineID) {
		this.machineID = machineID;
	}

	/**
	 * getter for product name
	 * 
	 * @return
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * setter for product name
	 * 
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * getter for low bar
	 * 
	 * @return
	 */
	public String getNewMinAmount() {
		return newMinAmount;
	}

	/**
	 * setter for low bar
	 * 
	 * @param newMinAmount
	 */
	public void setNewMinAmount(String newMinAmount) {
		this.newMinAmount = newMinAmount;
	}
}
