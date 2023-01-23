package entities;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * this class receives the information for the Missing Products Message
 * 
 * @author
 *
 */
public class MissingProductsMessage {

	/**
	 * machine id
	 */
	private SimpleStringProperty MachineID;
	/**
	 * machine name
	 */
	private SimpleStringProperty MachineName;
	/**
	 * loe bar of machine
	 */
	private SimpleStringProperty Low_bar;
	/**
	 * list of Missing Products in machine
	 */
	private SimpleStringProperty MissingProducts;
	/**
	 * option to send message to employee
	 */
	private ComboBox<String> SendMessageToEmployee;
	/**
	 * product in machine
	 */
	private int lowbar, cola, sprite, fanta, OrangeJuice, natureValley, waffles, doritos, bamba;

	/**
	 * Constructor
	 * 
	 * @param machineID
	 * @param machineName
	 * @param low_bar
	 * @param Cola
	 * @param Sprite
	 * @param Fanta
	 * @param orangeJuice
	 * @param NatureValley
	 * @param Waffles
	 * @param Doritos
	 * @param Bamba
	 * @param data
	 */
	public MissingProductsMessage(String machineID, String machineName, String low_bar, String Cola, String Sprite,
			String Fanta, String orangeJuice, String NatureValley, String Waffles, String Doritos, String Bamba,
			ObservableList data) {
		this.MachineID = new SimpleStringProperty(machineID);
		this.MachineName = new SimpleStringProperty(machineName);
		this.Low_bar = new SimpleStringProperty(low_bar);
		this.SendMessageToEmployee = new ComboBox(data);
		this.cola = Integer.parseInt(Cola);
		this.sprite = Integer.parseInt(Sprite);
		this.fanta = Integer.parseInt(Fanta);
		this.OrangeJuice = Integer.parseInt(orangeJuice);
		this.natureValley = Integer.parseInt(NatureValley);
		this.waffles = Integer.parseInt(Waffles);
		this.doritos = Integer.parseInt(Doritos);
		this.bamba = Integer.parseInt(Bamba);
		this.lowbar = Integer.parseInt(low_bar);
		this.MissingProducts = new SimpleStringProperty(createMissingProducts());
		SendMessageToEmployee.setValue("No");
	}

	/**
	 * getter for machine id
	 * 
	 * @return
	 */
	public String getMachineID() {
		return MachineID.get();
	}

	/**
	 * setter for machine id
	 * 
	 * @param machineID
	 */
	public void setMachineID(String machineID) {
		MachineID.set(machineID);
	}

	/**
	 * getter for machine name
	 * 
	 * @return
	 */
	public String getMachineName() {
		return MachineName.get();
	}

	/**
	 * setter for machine name
	 * 
	 * @param machineName
	 */
	public void setMachineName(String machineName) {
		MachineName.set(machineName);
	}

	/**
	 * getter for low bar
	 * 
	 * @return
	 */
	public String getLow_bar() {
		return Low_bar.get();
	}

	/**
	 * setter for low bar
	 * 
	 * @param low_bar
	 */
	public void setLow_bar(String low_bar) {
		Low_bar.set(low_bar);
	}

	/**
	 * getter for send option
	 * 
	 * @return
	 */
	public ComboBox<String> getSendMessageToEmployee() {
		return SendMessageToEmployee;
	}

	/**
	 * setter for send option
	 * 
	 * @param sendMessageToEmployee
	 */
	public void setSendMessageToEmployee(String sendMessageToEmployee) {
		this.SendMessageToEmployee.setValue(sendMessageToEmployee);
	}

	// cola, sprite, fanta, OrangeJuice, natureValley, waffles, doritos, bamba;
	/**
	 * method create string list of the missing products
	 * 
	 * @return
	 */
	public String createMissingProducts() {
		String s = "";
		if (this.cola < this.lowbar)
			s += "cola-" + Integer.toString(lowbar - cola) + ",";
		if (this.sprite < this.lowbar)
			s += "sprite-" + Integer.toString(lowbar - sprite) + ",";
		if (this.fanta < this.lowbar)
			s += "fanta-" + Integer.toString(lowbar - fanta) + ",";
		if (this.OrangeJuice < this.lowbar)
			s += "OrangeJuice-" + Integer.toString(lowbar - OrangeJuice) + ",";
		if (this.natureValley < this.lowbar)
			s += "natureValley-" + Integer.toString(lowbar - natureValley) + ",";
		if (this.waffles < this.lowbar)
			s += "waffles-" + Integer.toString(lowbar - waffles) + ",";
		if (this.doritos < this.lowbar)
			s += "doritos-" + Integer.toString(lowbar - doritos) + ",";
		if (this.bamba < this.lowbar)
			s += "bamba-" + Integer.toString(lowbar - bamba) + ",";
		if (s.isEmpty())
			return s;
		return s.substring(0, s.length() - 1);
	}

	/**
	 * getter for missing product
	 * 
	 * @return
	 */
	public String getMissingProducts() {
		return MissingProducts.get();
	}

	/**
	 * setter for missing product
	 * 
	 * @param missingProducts
	 */
	public void setMissingProducts(String missingProducts) {
		MissingProducts.set(missingProducts);
	}
}
