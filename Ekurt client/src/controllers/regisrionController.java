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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author Avner controller class where the costumer service can register new
 *         costumer
 */
public class regisrionController implements Initializable {

	/**
	 * combo box that contain all the users can register as a costumer
	 */
	@FXML
	private ComboBox<String> userCombo;

	/**
	 * HBox that contain all the registration form
	 */
	@FXML
	private HBox Hform;

	/**
	 * label that notify the user that user name is already taken
	 */
	@FXML
	private Label usernamefail;

	/**
	 * label that notify the user registration success
	 */
	@FXML
	private Label laRegisterSucsess;

	/**
	 * label that notify the user that information is missing
	 */
	@FXML
	private Label laInfoMiss;

	/**
	 * text Field for first name
	 */
	@FXML
	private TextField firstname;

	/**
	 * text Field for last name
	 */
	@FXML
	private TextField lastname;

	/**
	 * text Field for id
	 */
	@FXML
	private TextField id;

	/**
	 * text Field for credit card number
	 */
	@FXML
	private TextField creditcard;

	/**
	 * text Field for phone number
	 */
	@FXML
	private TextField phone;

	/**
	 * text Field for email
	 */
	@FXML
	private TextField email;

	/**
	 * text Field for user name
	 */
	@FXML
	private TextField username;

	/**
	 * text Field for password
	 */
	@FXML
	private TextField password;

	/**
	 * logo image
	 */
	@FXML
	private ImageView img;

	/**
	 * can chose a prefer region
	 */
	@FXML
	private ComboBox<String> ComboRegion;

	/**
	 * label that notify the user he need to choose user
	 */
	@FXML
	private Label showLabel;

	/**
	 * indicate the status of the registration success or fail
	 */
	public static String registerStatus = new String();

	/**
	 * list of the users
	 */
	public static ObservableList<String> users = FXCollections.observableArrayList();
	
	/**
	 * list of the selected user information
	 */
	public static ArrayList<String> userData = new ArrayList<>();

	/**
	 * show the user information on the screen
	 * 
	 * @param event click on show button
	 */
	@FXML
	void onShow(ActionEvent event) {
		laRegisterSucsess.setVisible(false);
		usernamefail.setVisible(false);
		laInfoMiss.setVisible(false);
		showLabel.setVisible(false);
		Hform.setVisible(false);
		
		String user = userCombo.getValue();
		if(user==null || user.equals("")) {
			showLabel.setVisible(true);
		}
		else {
			showLabel.setVisible(false);
			clearAllProprtys();
			
			Massage messageToServer = new Massage();
			ArrayList<String> userName = new ArrayList<>();
			userName.add(user);
			messageToServer.setData(userName);
			messageToServer.setOperation("get user info");
			client.ClientUI.chat.accept(messageToServer);
			
			
			firstname.setText(userData.get(0));;
			lastname.setText(userData.get(1));
			id.setText(userData.get(2));
			id.setDisable(true);
			username.setText(userData.get(3));
			username.setDisable(true);
			password.setText(userData.get(4));
			Hform.setVisible(true);
			
		}

	}

	/**
	 * send to the server all the costumer details
	 * 
	 * @param event click on register button
	 */
	@FXML
	void onRegister(ActionEvent event) {

		laRegisterSucsess.setVisible(false);
		usernamefail.setVisible(false);
		laInfoMiss.setVisible(false);
		showLabel.setVisible(false);

		Massage messageToServer = new Massage();
		ArrayList<String> data = new ArrayList<>();
		boolean flag = false;

		messageToServer.setOperation("register");

		data.add(firstname.getText());
		data.add(lastname.getText());
		data.add(id.getText());
		data.add(creditcard.getText());
		data.add(phone.getText());
		data.add(email.getText());
		data.add(username.getText());
		data.add(password.getText());
		data.add(ComboRegion.getValue());

		for (String x : data) {
			if (x == null || x.equals(""))
				flag = true;
		}

		if (flag) {
			laInfoMiss.setVisible(true);
		} else {
			laInfoMiss.setVisible(false);
			messageToServer.setData(data);
			client.ClientUI.chat.accept(messageToServer);

			if (registerStatus.equals("OK")) {
				laRegisterSucsess.setVisible(true);
				clearAllProprtys();

			} else {
				usernamefail.setVisible(true);
			}

		}

	}

	/**
	 * clear all the text on the TextField
	 */
	private void clearAllProprtys() {
		firstname.clear();
		lastname.clear();
		id.clear();
		creditcard.clear();
		phone.clear();
		email.clear();
		username.clear();
		password.clear();
		ComboRegion.promptTextProperty();
		Hform.setVisible(false);
		setUsers();
	}

	/**
	 * move the client back to the main window
	 * 
	 * @param event click on back button
	 */
	@FXML
	void pressBack(ActionEvent event) {

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
	 * set the user combo box with all the user that can be costumers
	 */
	private void setUsers() {
		users.clear();
		Massage messageToServer = new Massage();
		messageToServer.setOperation("get users");
		client.ClientUI.chat.accept(messageToServer);

	}

	/**
	 * initialize all data when the window upload
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);
		laRegisterSucsess.setVisible(false);
		usernamefail.setVisible(false);
		laInfoMiss.setVisible(false);
		showLabel.setVisible(false);

		ComboRegion.setItems(
				FXCollections.observableArrayList(new String("north"), new String("South"), new String("UAE")));

		setUsers();
		Hform.setVisible(false);
		userCombo.setItems(users);

	}

}
