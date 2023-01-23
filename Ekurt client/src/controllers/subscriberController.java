package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Massage;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * @author Avner Controller class where the costumer service can make a costumer
 *         into subscriber
 */
public class subscriberController implements Initializable {

	/**
	 * label that indicate this user is already a subscriber
	 */
	@FXML
	private Label alreadySuscriber;

	/**
	 * label that indicate the user name or id incorrect
	 */
	@FXML
	private Label inncorrect;

	/**
	 * label that indicate the user pending for approval
	 */
	@FXML
	private Label pendingforaproval;

	/**
	 * label that indicate the user is now a subscriber
	 */
	@FXML
	private Label laRegisterSucsess;

	/**
	 * Field to enter user name
	 */
	@FXML
	private TextField username;

	/**
	 * Field to select user name
	 */

	@FXML
	private ComboBox<String> user;

	/**
	 * Field to enter id
	 */
	@FXML
	private TextField id;

	/**
	 * logo image
	 */
	@FXML
	private ImageView img;

	/**
	 * Indicate if the operation success or fail
	 */
	public static String subscriberStatus = new String();

	/**
	 * list of user name
	 */
	public static ObservableList<String> users = FXCollections.observableArrayList();

	/**
	 * list of user id
	 */
	public static ArrayList<String> ids = new ArrayList<>();
	
	/**
	 * set the user name and the is into the text fields
	 * 
	 * @param event
	 */
	@FXML
	void onShow(ActionEvent event) {
		
		String name = user.getValue();
		
		if (name == null || name.equals("")) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);//display an alert that says that the order been deleted the order have been abandoned.
			alert.setTitle("Alert");
			alert.setHeaderText(null);
			alert.setContentText(
					"Please choose user name");
			alert.showAndWait();
		}
		else {
			this.username.setText(name);
			this.id.setText(ids.get(users.indexOf(name)));
		}

	}

	/**
	 * Submit the request to the sever and indicate if there problems
	 * 
	 * @param event click on submit button
	 */
	@FXML
	void onsubmit(ActionEvent event) {

		alreadySuscriber.setVisible(false);
		inncorrect.setVisible(false);
		pendingforaproval.setVisible(false);
		laRegisterSucsess.setVisible(false);

		Massage messageToServer = new Massage();
		ArrayList<String> data = new ArrayList<>();
		boolean flag = false;
		String user = null;

		messageToServer.setOperation("subscriber");

		data.add(username.getText());
		data.add(id.getText());

		for (String x : data) {
			if (x == null || x.equals(""))
				flag = true;
		}

		if (flag) {
			inncorrect.setVisible(true);
		} else {
			inncorrect.setVisible(false);
			messageToServer.setData(data);

			user = data.get(0);

			client.ClientUI.chat.accept(messageToServer);

			System.out.println(subscriberStatus);
			if (subscriberStatus.equals("OK")) {

				laRegisterSucsess.setVisible(true);

				Massage messageToServer1 = new Massage();
				ArrayList<String> arr = new ArrayList<>();

				arr.add("Ekurt System");
				arr.add(user);
				arr.add("you are now a subscriber!!");
				messageToServer1.setOperation("send messeges");
				messageToServer1.setData(arr);
				client.ClientUI.chat.accept(messageToServer1);

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Alert");
				alert.setHeaderText(null);
				alert.setContentText("SMS and Email sand to the subscriber with his sub number");
				alert.showAndWait();
			} else if (subscriberStatus.equals("alreadysub")) {
				alreadySuscriber.setVisible(true);
			} else if (subscriberStatus.equals("ispending")) {
				pendingforaproval.setVisible(true);
			} else {
				inncorrect.setVisible(true);
			}
		}

	}

	/**
	 * move the client to the main window
	 * 
	 * @param event click on back button
	 */
	@FXML
	void onback(ActionEvent event) {

		Massage messageToServer = new Massage();

		((Node) event.getSource()).getScene().getWindow().hide();
		// CustomerServiceWelcomeController CSWC = new
		// CustomerServiceWelcomeController(verifieUser2.get(1));
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/welcome CustomerService.fxml"));

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
	 * initialize the gui
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ids.clear();
		users.clear();
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);

		Massage messageToServer1 = new Massage();
		messageToServer1.setOperation("get user");

		client.ClientUI.chat.accept(messageToServer1);
		
		this.user.setItems(users);
		
		alreadySuscriber.setVisible(false);
		inncorrect.setVisible(false);
		pendingforaproval.setVisible(false);
		laRegisterSucsess.setVisible(false);

	}

}
