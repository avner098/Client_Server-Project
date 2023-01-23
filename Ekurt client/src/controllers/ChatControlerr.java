package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Massage;
import entities.MessegeFromEkurt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * @author Avner Controller class that control the chat Gui
 */
public class ChatControlerr implements Initializable {

	/**
	 * list of user name the client can send messages
	 */
	public static ObservableList<String> userName = FXCollections.observableArrayList();
	/**
	 * list of row in the (row = message)
	 */
	public static ObservableList<MessegeFromEkurt> list = FXCollections.observableArrayList();

	/**
	 * data the server return to the client
	 */
	public static ArrayList<String> data = new ArrayList<>();

	/**
	 * role of the client in the system
	 */
	public String role;

	/**
	 * logo
	 */
	@FXML
	private ImageView img;

	/**
	 * table of messages
	 */
	@FXML
	private TableView<MessegeFromEkurt> messegeTable;

	/**
	 * columns of the message content
	 */
	@FXML
	private TableColumn<MessegeFromEkurt, String> contentCol;

	/**
	 * columns of the user that send the message
	 */
	@FXML
	private TableColumn<MessegeFromEkurt, String> fromCol;

	/**
	 * content of the message the client want to send
	 */
	@FXML
	private TextArea messegeContex;

	/**
	 * label that indicate the client send fail
	 */
	@FXML
	private Label sendFail;

	/**
	 * label that indicate the client send success
	 */
	@FXML
	private Label sendSucsess;

	/**
	 * combo box with all the user names the client can sent to them message
	 */
	@FXML
	private ComboBox<String> usernameCombo;

	/*
	 * Delivery -> costumer(All) system -> area manager ..Inventory of product X has
	 * fallen below a threshold level in inventory Y area manager -> operation
	 * worker(All) operation worker -> area manager(All) system -> costumer system
	 * -> subscriber
	 */
	/**
	 * method that start when click on send button and send the data to the server
	 * 
	 * @param event click
	 */
	@FXML
	void onsend(ActionEvent event) {

		String userFromSend = LoginController.verifieUser.get(4);
		String userToSend = usernameCombo.getValue();
		String msg = messegeContex.getText();

		if (userToSend == null || msg == null || msg.equals("")) {
			sendFail.setVisible(true);
			sendSucsess.setVisible(false);
		} else {
			sendFail.setVisible(false);
			sendSucsess.setVisible(true);

			Massage messageToServer = new Massage();
			ArrayList<String> arr = new ArrayList<>();

			arr.add(userFromSend);
			arr.add(userToSend);
			arr.add(msg);
			messageToServer.setOperation("send messeges");
			messageToServer.setData(arr);
			client.ClientUI.chat.accept(messageToServer);

			messegeContex.clear();
		}

	}

	/**
	 * This method deletes the order if the customer leaves the window without going
	 * to the payment screen.
	 * 
	 */
	private void closeProgram() {
		ArrayList<String> priceAmount = new ArrayList<>();
		priceAmount.add(order2Controller.orderNumber);
		priceAmount.add(order2Controller.machine);
		Massage messageToServer = new Massage();
		messageToServer.setOperation("delete order after timeout"); // ;//in case of abandonment of the order, the
																	// server will delete the order.
		messageToServer.setData(priceAmount);
		client.ClientUI.chat.accept(messageToServer);
	}

	/**
	 * on click back button the method bring the client back to the previous Gui
	 * 
	 * @param event click
	 */
	@FXML
	void pressBack(ActionEvent event) {
		userName.clear();

		((Node) event.getSource()).getScene().getWindow().hide();
		// CustomerServiceWelcomeController CSWC = new
		// CustomerServiceWelcomeController(verifieUser2.get(1));
		try {

			FXMLLoader fxmlLoader = null;

			if (LoginController.verifieUser.get(0).equals("opertion worker")) {

				fxmlLoader = new FXMLLoader(getClass().getResource("/gui/welcome opertion worker.fxml"));

			} else if (LoginController.verifieUser.get(0).equals("Carrier")) {

				fxmlLoader = new FXMLLoader(getClass().getResource("/gui/Carrier_Welcome.fxml"));

			} else if (LoginController.verifieUser.get(0).equals("regional Manager")) {

				fxmlLoader = new FXMLLoader(getClass().getResource("/gui/RManagerWelcomeGUI.fxml"));
			} else {
				fxmlLoader = new FXMLLoader(getClass().getResource("/gui/order1.fxml"));
			}
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

			Massage messageToServer = new Massage();
			// disconnect.add("disconnect");
			messageToServer.setOperation("disconnect");
			disconnect.add(ip_adrrs[1]);
			disconnect.add(ClientUI.chat.getHost());
			disconnect.add(LoginController.verifieUser.get(4));
			disconnect.add(LoginController.verifieUser.get(5));
			messageToServer.setData(disconnect);

			stage.setOnCloseRequest(event1 -> {
				if (LoginController.verifieUser.get(0).equals("customer")) {
					closeProgram();
				}
				client.ClientUI.chat.accept(messageToServer);
				client.ClientUI.chat.client.quit();

			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * set all the client messages into the screen in table
	 */
	private void setMessege() {
		list.clear();

		for (int i = 0; i < data.size(); i += 2) {
			String user = data.get(i);
			String msg = data.get(i + 1);
			if (msg.length() > 60) {
				String line1 = msg.substring(0, (int) msg.length() / 2);
				String line2 = msg.substring((int) msg.length() / 2);
				msg = line1 + "\n" + line2;
			}
			list.add(new MessegeFromEkurt(user, msg));
		}
		messegeTable.setItems(list);
	}

	/**
	 * refresh the table check if there new Messages
	 * 
	 * @param event click on refresh
	 */
	@FXML
	void onRefresh(ActionEvent event) {

		data.clear();
		Massage messageToServer = new Massage();
		ArrayList<String> arr = new ArrayList<>();

		arr.add(LoginController.verifieUser.get(4));
		messageToServer.setOperation("get messeges");
		messageToServer.setData(arr);
		client.ClientUI.chat.accept(messageToServer);

		setMessege();
	}

	/**
	 * method starts when Gui initializes and upload data to screen
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);

		sendFail.setVisible(false);
		sendSucsess.setVisible(false);

		role = LoginController.verifieUser.get(0);

		fromCol.setCellValueFactory(new PropertyValueFactory<MessegeFromEkurt, String>("from"));
		contentCol.setCellValueFactory(new PropertyValueFactory<MessegeFromEkurt, String>("messege"));

		Massage messageToServer = new Massage();
		ArrayList<String> arr = new ArrayList<>();

		arr.add(LoginController.verifieUser.get(4));
		messageToServer.setOperation("get messeges");
		messageToServer.setData(arr);
		client.ClientUI.chat.accept(messageToServer);

		setMessege();

		arr.add(role);

		messageToServer.setOperation("get users to messege");
		messageToServer.setData(arr);
		client.ClientUI.chat.accept(messageToServer);

		usernameCombo.setItems(userName);
	}

}
