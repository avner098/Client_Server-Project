package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.TextField;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Massage;
import entities.UsersForApproval;

/**
 * This class is used for Region Manager to Choose accept or deny the Users
 * waiting for approval.
 *
 */
public class UsersApprovalController implements Initializable {
	/**
	 * users waiting for approval or disapproval data received from the server.
	 */
	public static ArrayList<String> data = new ArrayList<>();
	/**
	 * Accept or deny user options.
	 */
	public static ObservableList<String> options = FXCollections.observableArrayList("Accept", "Deny");
	/**
	 * list that contains the data to set in table.
	 */
	public static ObservableList<UsersForApproval> list = FXCollections.observableArrayList();

	/**
	 * Ekurt Logo to display on the screen
	 */
	@FXML
	private ImageView img;

	/**
	 * Apply button that make the changes the regional manager made.
	 */
	@FXML
	private Button ApplyButton;

	/**
	 * Back button to go back the previous window.
	 */
	@FXML
	private Button backButton;

	/**
	 * Email column in table.
	 */
	@FXML
	private TableColumn<UsersForApproval, String> Email;

	/**
	 * ID column in table.
	 */
	@FXML
	private TableColumn<UsersForApproval, String> ID;

	/**
	 * Status column in table.
	 */
	@FXML
	private TableColumn<UsersForApproval, String> Status;

	/**
	 * Name column in table.
	 */
	@FXML
	private TableColumn<UsersForApproval, String> Name;

	/**
	 * PhoneNumber column in table.
	 */
	@FXML
	private TableColumn<UsersForApproval, String> PhoneNumber;

	/**
	 * Region column in table.
	 */
	@FXML
	private TableColumn<UsersForApproval, String> Region;

	/**
	 * table that contains the data.
	 */
	@FXML
	private TableView<UsersForApproval> table;
	
	/**
	 * list of all approved user names
	 */
	public ArrayList<String> username = new ArrayList<>();

	/**
	 * return the region manager to the welcome window
	 * 
	 * @param event click on back button
	 */
	@FXML
	void backButton(ActionEvent event) {
		Massage messageToServer = new Massage();

		((Node) event.getSource()).getScene().getWindow().hide();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/RManagerWelcomeGUI.fxml"));

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

	/**
	 *      
	 * 
	 * this method create an ArrayList with the regional manager choices for the
	 * users in the table and send it to the server.
	 * 
	 * @param event click on apply changes button.
	 */
	@FXML
	void apply_changes(ActionEvent event) {
		ArrayList<String> idStatusArray = new ArrayList<>();
		ArrayList<String> statusList = new ArrayList<>();
		ArrayList<String> idList = new ArrayList<>();
		
		for (UsersForApproval item : table.getItems()) {
			ComboBox<String> statusComboBox = item.getStatus();
			String selectedStatus = statusComboBox.getValue();
			if (selectedStatus != null) { // in case the user didn't want to accept or deny.
				statusList.add(selectedStatus);
				idList.add(item.getID());
				if(selectedStatus.equals("Accept")) {
					
					Massage messageToServer1 = new Massage();
					ArrayList<String> arr = new ArrayList<>();

					arr.add("Ekurt System");
					arr.add(item.getUserName());
					arr.add("you are now a customer!!");
					messageToServer1.setOperation("send messeges");
					messageToServer1.setData(arr);
					client.ClientUI.chat.accept(messageToServer1);
				}
			}
		}

		if (idList.size() > 0) {
			for (int i = 0; i < idList.size(); i++) {
				idStatusArray.add(idList.get(i) + "," + statusList.get(i));
			}
		}

		if (idStatusArray.size() > 0) {
			Massage messageToServer = new Massage();
			messageToServer.setOperation("setwaitingforapprovalusers");
			messageToServer.setData(idStatusArray);
			client.ClientUI.chat.accept(messageToServer);
			
			
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("");
		alert.setHeaderText("Users status changed");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.isEmpty()) {
			System.out.println("Alert Closed");
		} else if (result.get() == ButtonType.OK) {
			System.out.println("Confirmed");
		}
		update_table_view();
	}

	/**
	 * this method set the table with the Users that waiting for Approval data.
	 */
	void update_table_view() {
		data.clear();
		list.clear();
		Massage messageToServer = new Massage();
		messageToServer.setOperation("getusersapprovaldata");
		messageToServer.setData(data);
		client.ClientUI.chat.accept(messageToServer);
		for (int i = 0; i <= data.size() - 10; i = i + 10) {
			String fname = data.get(i) + " " + data.get(i + 1);
			
			if (LoginController.verifieUser.get(6).equals(data.get(i + 8))) {
				list.add(new UsersForApproval(fname, data.get(i + 2), data.get(i + 4), data.get(i + 5), data.get(i + 8),
						options, data.get(i + 6))); // options 7
			}
		}
		table.setItems(list);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);
		Name.setCellValueFactory(new PropertyValueFactory<UsersForApproval, String>("Name"));
		ID.setCellValueFactory(new PropertyValueFactory<UsersForApproval, String>("ID"));
		PhoneNumber.setCellValueFactory(new PropertyValueFactory<UsersForApproval, String>("PhoneNumber"));
		Email.setCellValueFactory(new PropertyValueFactory<UsersForApproval, String>("Email"));
		Region.setCellValueFactory(new PropertyValueFactory<UsersForApproval, String>("Region"));
		Status.setCellValueFactory(new PropertyValueFactory<UsersForApproval, String>("Status"));
		update_table_view();

	}
}
