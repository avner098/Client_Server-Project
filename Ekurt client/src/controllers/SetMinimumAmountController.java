package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//import java.util.ArrayList;
/*import javafx.collections.FXCollections;
import javafx.collections.ObservableList;*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ClientUI;
import common.Massage;
import entities.MinimumAmount;

/**
 * This class is used for Region Manager to set or change the low_bar value for
 * a chosen machine.
 *
 */
public class SetMinimumAmountController implements Initializable {
	/**
	 * data that back from the server
	 */
	public static ArrayList<String> data = new ArrayList<>();
	/**
	 * Machines ID data to specific region that back from the server
	 */
	public static ArrayList<String> MachinesIDdata = new ArrayList<>();
	/**
	 * MachineID to show in comboBox for regional manager region.
	 */
	public static ObservableList<String> options = FXCollections.observableArrayList();

	/**
	 * region of the Regional manager.
	 */
	public String region;

	/**
	 * Text field for entering the minimum amout of products for a machine
	 */
	@FXML
	private TextField AmountTextField;

	/**
	 * this method returns the low_bar current value for the machine chose.
	 * 
	 * @return AmountTextField with the current low_bar value for the machine.
	 */
	private String GetAmount() {
		return AmountTextField.getText();
	}

	/**
	 * Back button to go back the previous window.
	 */
	@FXML
	private Button Back;
	/**
	 * Ekurt Logo to display on the screen
	 */
	@FXML
	private ImageView img;

	/**
	 * Button to show the current minimum amount for a machine
	 */
	@FXML
	private Button ShowCurrentMinimumAmount;

	/**
	 * Choice box to select a machine ID from a list of options
	 */
	@FXML
	private ChoiceBox<String> MachineIDCBox = new ChoiceBox<>();

	/**
	 * Label to display the current minimum amount for a machine
	 */
	@FXML
	private Label currentMinimumItemAmount;

	/**
	 * return the region manager to the welcome window
	 * 
	 * @param event click on back button
	 */
	@FXML
	void backButton(ActionEvent event) {
		Massage messageToServer = new Massage();

		((Node) event.getSource()).getScene().getWindow().hide();
		// CustomerServiceWelcomeController CSWC = new
		// CustomerServiceWelcomeController(verifieUser2.get(1));
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/RManagerWelcomeGUI.fxml"));

			// CSWC.name= verifieUser2.get(1);
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();

			ArrayList<String> disconnect = new ArrayList<>();
			String[] ip_adrrs = null;
			String[] ip_adrrs1 = { "", "" };
			InetAddress ip2 = null;

			ip2 = ClientUI.chat.getIp();

			ip_adrrs = ip2.toString().split("/");

			// disconnect.add("disconnect");
			messageToServer.setOperation("disconnect");
			disconnect.add(ip_adrrs[1]);
			disconnect.add(ClientUI.chat.getHost());
			disconnect.add(LoginController.verifieUser.get(4));
			disconnect.add(LoginController.verifieUser.get(5));
			messageToServer.setData(disconnect);

			stage.setOnCloseRequest(event1 -> {
				client.ClientUI.chat.accept(messageToServer);
				client.ClientUI.chat.client.quit();

			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private Button setItemButton;

	/**
	 * this method set the given minimum amount from the user to the new low_bar for
	 * the chosen machine.
	 * 
	 * @param event click on setItemButton
	 */
	@FXML
	void setItemAmount(ActionEvent event) {

		String selectedAmount = GetAmount();
		// String selectedAmount = AmountTextField.getText();
		try {
			Integer.parseInt(selectedAmount);
		} catch (Exception e) {
			selectedAmount = null;
			AmountTextField.clear();
		}
		if (selectedAmount == null || selectedAmount.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter valid amount.", "invalid", JOptionPane.WARNING_MESSAGE);
		} else {

			String selectedMachineID = MachineIDCBox.getSelectionModel().getSelectedItem();
			System.out.println(selectedMachineID);
			ArrayList<String> MachineIdAmountList = new ArrayList<>();
			MachineIdAmountList.add(selectedMachineID); // 0
			MachineIdAmountList.add(selectedAmount); // 1
			Massage messageToServer = new Massage();
			messageToServer.setOperation("setnewminimumvaluedata");
			messageToServer.setData(MachineIdAmountList);
			client.ClientUI.chat.accept(messageToServer);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("");
			alert.setHeaderText("Do you want to apply the changes?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isEmpty()) {
				System.out.println("Alert Closed");
			} else if (result.get() == ButtonType.OK) {
				System.out.println("Confirmed");
				AmountTextField.setText("");///////////////////
				currentMinimumItemAmount.setText("");//////
			} else if (result.get() == ButtonType.CANCEL) {
				System.out.println("Not OK");
			}
		}
	}

	/**
	 * this method get the current machine minimum value and display it to the
	 * region manager on screen.
	 * 
	 * @param event click on Show Current Minimum Amount button.
	 */
	@FXML
	void ShowCurMinAmount(ActionEvent event) {
		data.clear();
		ArrayList<String> MachineIdfromBoxList = new ArrayList<>();
		String selectedMachineID = MachineIDCBox.getSelectionModel().getSelectedItem();
		MachineIdfromBoxList.add(selectedMachineID);
		Massage messageToServer = new Massage();
		messageToServer.setOperation("getminimumamount");
		messageToServer.setData(MachineIdfromBoxList);
		client.ClientUI.chat.accept(messageToServer);

		if (data.size() != 0 && (!data.get(0).equals("Empty"))) {
			String currentMinAmount = data.get(0);
			currentMinimumItemAmount.setText(currentMinAmount);
		}
	}

	/**
	 * set the ChoiceBox of the machines the machineID in the regional manager
	 * region.
	 */
	public void show_machineID() {
		MachinesIDdata.clear();
		options.clear();
		MachinesIDdata.add(region);
		Massage messageToServer = new Massage();
		messageToServer.setOperation("getmachinesIDforRegion");
		messageToServer.setData(MachinesIDdata);
		client.ClientUI.chat.accept(messageToServer);
		if (MachinesIDdata != null) {
			options.addAll(MachinesIDdata);
			MachineIDCBox.setItems(options);
			MachineIDCBox.setValue(options.get(0));
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
	
		
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);
		region = LoginController.verifieUser.get(6);
		show_machineID(); // V
		if (data.size() > 0) {
			currentMinimumItemAmount.setText(data.get(0));
		}
	}

}
