package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * @author Avner
 * 
 *         this class receives the information for Restock Call and display as a
 *         row in table
 */
public class RestokeCall {

	/**
	 * the id of the call
	 */
	private final SimpleStringProperty callId;
	/**
	 * the product client need to restock
	 */
	private final SimpleStringProperty products;
	/**
	 * machine name
	 */
	private final SimpleStringProperty name;
	/**
	 * region of the machine
	 */
	private final SimpleStringProperty region;
	/**
	 * the low bar of the machine
	 */
	private final SimpleStringProperty lowbar;
	/**
	 * status of the call open/close
	 */
	private ComboBox<String> status;

	/**
	 * Constructor
	 * 
	 * @param callId
	 * @param name
	 * @param region
	 * @param products
	 * @param lowbar
	 * @param option
	 */
	public RestokeCall(String callId, String name, String region, String products, String lowbar,
			ObservableList<String> option) {

		this.callId = new SimpleStringProperty(callId);
		this.products = new SimpleStringProperty(products);
		this.name = new SimpleStringProperty(name);
		this.region = new SimpleStringProperty(region);
		this.lowbar = new SimpleStringProperty(lowbar);
		this.status = new ComboBox<String>(option);
		status.setPromptText("Open");

	}

	/**
	 * getter for call id
	 * 
	 * @return calid
	 */
	public String getCallId() {
		return callId.get();
	}

	/**
	 * Getter for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * getter for region
	 * 
	 * @return region
	 */
	public String getRegion() {
		return region.get();
	}

	/**
	 * getter for products
	 * 
	 * @return products
	 */
	public String getProducts() {
		return products.get();
	}

	/**
	 * getter for low bar
	 * 
	 * @return low bar
	 */
	public String getLowbar() {
		return lowbar.get();
	}

	/**
	 * getter for status String
	 * 
	 * @return status String
	 */
	public String getstatusText() {
		if (status.getValue() == null)
			return "Open";
		return status.getValue();
	}

	/**
	 * getter for status combo box
	 * 
	 * @return status combo box
	 */
	public ComboBox<String> getStatus() {
		return status;
	}

	/**
	 * setter for call id
	 * 
	 * @param callId
	 */
	public void setCallId(String callId) {
		this.callId.set(callId);
	}

	/**
	 * setter for name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name.set(name);
	}

	/**
	 * setter for region
	 * 
	 * @param region
	 */
	public void setRegion(String region) {
		this.region.set(region);
	}

	/**
	 * setter for products
	 * 
	 * @param products
	 */
	public void setProducts(String products) {
		this.products.set(products);
	}

	/**
	 * setter for low bar
	 * 
	 * @param lowbar
	 */
	public void setLowbar(String lowbar) {
		this.lowbar.set(lowbar);
	}

	/**
	 * setter for status
	 * 
	 * @param option
	 */
	public void setstatus(String option) {
		this.status.setValue(option);
		;
	}

}
