package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ClientUI;
import common.Massage;
import entities.MissingProductsMessage;
import entities.RestockCalls;
import entities.UsersForApproval;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * This class is used for missing products notifications, the region manager
 * enters the missing products notifications screen and current missing products
 * are displayed with an option to send a message of that to the operation
 * worker.
 *
 */
public class MissingProductsNotificationsController implements Initializable {

	/**
	 * data about missingproducts from server.
	 */
	public static ArrayList<String> data = new ArrayList<>();
	/**
	 * data about open restock calls from server.
	 */
	public static ArrayList<String> data2 = new ArrayList<>(); //
	/**
	 * options for the regional manager to send message or not.
	 */
	public static ObservableList<String> options = FXCollections.observableArrayList("Yes", "No");
	/**
	 * list MissingProductsMessage to show in table.
	 */
	public static ObservableList<MissingProductsMessage> list = FXCollections.observableArrayList();
	/**
	 * region of the Regional manager.
	 */
	public String Region;

	/**
	 * Back button to go back the previous window.
	 */
	@FXML
	private Button BackButton;

	/**
	 * Ekurt Logo to display on the screen
	 */
	@FXML
	private ImageView img;
	/**
	 * Low_bar column in table.
	 */
	@FXML
	private TableColumn<MissingProductsMessage, String> Low_bar;

	/**
	 * MachineID column in table.
	 */
	@FXML
	private TableColumn<MissingProductsMessage, String> MachineID;

	/**
	 * MachineName column in table.
	 */
	@FXML
	private TableColumn<MissingProductsMessage, String> MachineName;

	/**
	 * MissingProducts column in table.
	 */
	@FXML
	private TableColumn<MissingProductsMessage, String> MissingProducts;

	/**
	 * SendMessageToEmployee column in table.
	 */
	@FXML
	private TableColumn<MissingProductsMessage, String> SendMessageToEmployee;

	/**
	 * button to refresh the data in table.
	 */
	@FXML
	private Button RefreshButton;

	/**
	 * button to send the messages.
	 */
	@FXML
	private Button SendMessagesBTN;

	/**
	 * table that contains the data.
	 */
	@FXML
	private TableView<MissingProductsMessage> table;

	/**
	 * return the region manager to the previous window
	 * 
	 * @param event click on back button
	 */
	@FXML
	void Back(ActionEvent event) {
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

	/**
	 * refresh and update the missing products table.
	 * 
	 * @param event click on Refresh button.
	 */
	@FXML
	void RefreshMissingProductsList(ActionEvent event) {
		// update_table_view();
		check_already_sent_messages();
	}

	/**
	 * send the selected missing products rows that has to sent to operation worker
	 * to the server.
	 * 
	 * @param event click on Send messages button.
	 */
	@FXML
	void Send_Messages_to_Employee(ActionEvent event) {
		ArrayList<String> MessagesList = new ArrayList<>();
		for (MissingProductsMessage item : table.getItems()) {
			String s = "";
			ComboBox<String> SendMessageToEmployee = item.getSendMessageToEmployee(); // Check NULL.
			String selectedSendMessageToEmployee = SendMessageToEmployee.getValue();
			if (selectedSendMessageToEmployee == null) {
			} else if (selectedSendMessageToEmployee.equals("Yes")) {
				s += item.getMachineName() + ":"; // MachineName Region Low_bar MissingProductsMessage
				s += Region + ":";
				s += item.getLow_bar() + ":";
				s += item.createMissingProducts();
				MessagesList.add(s);
			} else {
				break;
			}
		}

		if (MessagesList.size() != 0) {
			Massage messageToServer = new Massage();
			messageToServer.setOperation("sendmissingproductsmessagetoemployee");
			messageToServer.setData(MessagesList);
			client.ClientUI.chat.accept(messageToServer);
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("");
		alert.setHeaderText("Request received.");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.isEmpty()) {
			System.out.println("Alert Closed");
		} else if (result.get() == ButtonType.OK) {
			System.out.println("Confirmed");
		}
		check_already_sent_messages();
	}

	/**
	 * this method create the messages and add them to an ArrayList.
	 * 
	 * @return Missing product messages in ArrayList<MissingProductsMessage>.
	 */
	public ArrayList<MissingProductsMessage> update_table_view() {
		ArrayList<String> regionList = new ArrayList<String>();
		ArrayList<MissingProductsMessage> MPMArray = new ArrayList<MissingProductsMessage>();
		regionList.add(Region);
		data.clear();
		Massage messageToServer = new Massage();
		messageToServer.setOperation("getmissingproductsldata");
		messageToServer.setData(regionList);
		client.ClientUI.chat.accept(messageToServer);
		for (int i = 0; i <= data.size() - 11; i = i + 11) {
			MissingProductsMessage temp = new MissingProductsMessage(data.get(i), data.get(i + 1), data.get(i + 2),
					data.get(i + 3), data.get(i + 4), data.get(i + 5), data.get(i + 6), data.get(i + 7),
					data.get(i + 8), data.get(i + 9), data.get(i + 10), options);
			if (!temp.createMissingProducts().isEmpty()) {
				MPMArray.add(temp);
			}
		}
		return MPMArray;
	}

	/**
	 * this method set the table with the missing products that there is no open
	 * call for them.
	 */
	public void check_already_sent_messages() {
		list.clear();
		data2.clear();
		ArrayList<String> regionList = new ArrayList<String>();
		regionList.add(Region);
		Massage messageToServer = new Massage();
		messageToServer.setOperation("getopenrestokecallsforspecificregion");
		messageToServer.setData(regionList);
		client.ClientUI.chat.accept(messageToServer);
		ArrayList<MissingProductsMessage> MPMArray1 = new ArrayList<MissingProductsMessage>();
		MPMArray1 = update_table_view();
		if (!(MPMArray1.isEmpty())) {
			if (!data2.isEmpty()) {
				for (int i = 0; i <= data2.size() - 6; i = i + 6) {
					RestockCalls temp = new RestockCalls(data2.get(i), data2.get(i + 1), data2.get(i + 2),
							data2.get(i + 3), data2.get(i + 4), data2.get(i + 5));
					for (MissingProductsMessage item : MPMArray1) {
						if (item.getMachineName().equals(temp.getMachineName())
								&& item.getMissingProducts().equals(temp.getProductsToRestock())) {
							MPMArray1.remove(item);
							break;
						}
					}
				}
			}
		}
		if (!(MPMArray1.isEmpty())) {
			for (MissingProductsMessage item1 : MPMArray1) {
				list.add(item1);
			}
		}
		table.setItems(list);
	}

	public void initialize(URL location, ResourceBundle resources) {

		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);
		Region = LoginController.verifieUser.get(6);
		MachineID.setCellValueFactory(new PropertyValueFactory<MissingProductsMessage, String>("MachineID"));
		MachineName.setCellValueFactory(new PropertyValueFactory<MissingProductsMessage, String>("MachineName"));
		Low_bar.setCellValueFactory(new PropertyValueFactory<MissingProductsMessage, String>("Low_bar"));
		MissingProducts
				.setCellValueFactory(new PropertyValueFactory<MissingProductsMessage, String>("MissingProducts"));
		SendMessageToEmployee
				.setCellValueFactory(new PropertyValueFactory<MissingProductsMessage, String>("SendMessageToEmployee"));

		check_already_sent_messages();

	}

}
