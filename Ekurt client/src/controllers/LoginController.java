package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.ILoginController;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * @author Avner Login Controller that takes data from the user and checks if he
 *         is registered in the system
 */
public class LoginController implements Initializable {

	/**
	 * byte array to set image
	 */
	public static byte[] array;

	/**
	 * list of all the users are costumers
	 */
	public static ObservableList<String> CustomerUserName = FXCollections.observableArrayList();

	/**
	 * data the server return if the use is a subscriber or not
	 */
	public static ArrayList<String> subscriber = new ArrayList<>();

	/**
	 * combo box with all the costumer name
	 */
	@FXML
	private ComboBox<String> customer;

	/**
	 * label that notify the user the user he try to login with is not a costumer
	 * yet
	 */
	@FXML
	private Label userNotCostumer;

	/**
	 * An object of type ILoginController that implements the login verification
	 * type
	 */
	ILoginController loginController;

	/**
	 * constructor set the loginController object to work with the server
	 */
	public LoginController() {
		this.loginController = new LoginControllerManager();
	}

	/**
	 * @return Returns with loginController
	 */
	public ILoginController get() {
		return loginController;
	}

	/**
	 * @param loginController setter for the this.loginController
	 */
	public void set(ILoginController loginController) {
		this.loginController = loginController;
	}

	/**
	 * ArrayList of information that verifies if the customer is registered
	 * //[role,first_name,last_name,isloggedIn,username,paswword,region,ID]
	 */
	public static ArrayList<String> verifieUser = new ArrayList<>();

	/**
	 * label that warns if the username or password is incorrect
	 */
	@FXML
	private Label incorrect;

	/**
	 * logo
	 */
	@FXML
	private ImageView img;
	/**
	 * label that warns if the user is already logged in to the system
	 */
	@FXML
	private Label AlraedyLogged;

	/**
	 * button that activates the login process
	 */
	@FXML
	private Button Login = null;

	/**
	 * Text field to enter a username
	 */
	@FXML
	private TextField userName;

	/**
	 * Text field to enter a password
	 */
	@FXML
	private PasswordField password;

	/**
	 * A fast login simulation method by bringing the phone closer to the EKURT
	 * device
	 * 
	 * @param event click on FastLog button
	 * 
	 */
	@FXML
	void onFastLog(ActionEvent event) {

		String userName = customer.getValue();

		if (userName == null || userName.equals("")) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Alert");
			alert.setHeaderText(null);
			alert.setContentText("Please choose a valid user name");
			alert.showAndWait();
			return;
		}

		Massage messageToServer1 = new Massage();
		ArrayList<String> ar = new ArrayList<>();
		messageToServer1.setOperation("get subscriber status");
		ar.add(userName);
		messageToServer1.setData(ar);
		client.ClientUI.chat.accept(messageToServer1);

		if (subscriber.get(0).equals("sub")) {

			String role = loginCheck(userName, subscriber.get(1));

			if (!role.equals("alreadyLogged")) {
				Massage messageToServer = new Massage();
				((Node) event.getSource()).getScene().getWindow().hide();
				// CustomerServiceWelcomeController CSWC = new
				// CustomerServiceWelcomeController(verifieUser2.get(1));
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/order1.fxml"));

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
					disconnect.add(verifieUser.get(4));
					disconnect.add(verifieUser.get(5));
					messageToServer.setData(disconnect);

					stage.setOnCloseRequest(event1 -> {
						client.ClientUI.chat.accept(messageToServer);
						client.ClientUI.chat.client.quit();

					});
					CustomerUserName.clear();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {

				incorrect.setVisible(false);
				AlraedyLogged.setVisible(true);
				userNotCostumer.setVisible(false);
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Alert");
			alert.setHeaderText(null);
			alert.setContentText(
					"Logging into the application is only for subscribers, please log in with a username and password");
			alert.showAndWait();
		}
	}

	@FXML
	/**
	 * A method that activates the login process when the user clicks the login
	 * button
	 * 
	 * @param event Click on the log in button
	 * 
	 */
	void login(ActionEvent event) {

		String usernameString;
		String passwordString;

		usernameString = userName.getText();
		passwordString = password.getText();
		try {
			String role = loginCheck(usernameString, passwordString);
			moveToNextWindow(role, event);
		} catch (NullPointerException e) {
			incorrect.setVisible(true);
			AlraedyLogged.setVisible(false);
			userNotCostumer.setVisible(false);
		}
		/*
		 * if (usernameString.equals("") || passwordString.equals("")) {
		 * incorrect.setVisible(true); } else { }
		 */

	}

	/**
	 * A method that takes the username and password and verifies that they are
	 * correct, if correct, returns the user's role in the system such as: manager
	 * costumer...
	 * 
	 * @param username client username
	 * @param password client password
	 * @return user's role in the system
	 */
	public String loginCheck(String username, String password) {

		if (username == null)
			return "NullInput";

		if (password == null)
			return "NullInput";

		if (username.equals(""))
			return "EmptyInput";

		if (password.equals(""))
			return "EmptyInput";

		if (password.length() != password.trim().length() || password.contains(" ") || password.contains("\t"))
			return "password with space";

		if (username.length() != username.trim().length() || username.contains(" ") || username.contains("\t"))
			return "username with space";

		String role = loginController.verifay(username, password);

		if (role == null) {
			return "NullOutput";
		}
		return role;
	}

	/**
	 * A method that moves the user to the next window relevant to him if he is
	 * registered
	 * 
	 * @param verifieUser2 ArrayList of information that verifies if the customer is
	 *                     registered
	 * 
	 */
	private void moveToNextWindow(String role, ActionEvent event) {

		String action = role;

		Massage messageToServer = new Massage();

		switch (action) {
		case "regional Manager":
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
				disconnect.add(verifieUser.get(4));
				disconnect.add(verifieUser.get(5));
				messageToServer.setData(disconnect);

				stage.setOnCloseRequest(event1 -> {
					client.ClientUI.chat.accept(messageToServer);
					client.ClientUI.chat.client.quit();

				});
				CustomerUserName.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "marketing Manager":
			((Node) event.getSource()).getScene().getWindow().hide();
			// CustomerServiceWelcomeController CSWC = new
			// CustomerServiceWelcomeController(verifieUser2.get(1));
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/MarktingManger.fxml"));

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
				disconnect.add(verifieUser.get(4));
				disconnect.add(verifieUser.get(5));
				messageToServer.setData(disconnect);

				stage.setOnCloseRequest(event1 -> {
					client.ClientUI.chat.accept(messageToServer);
					client.ClientUI.chat.client.quit();

				});
				CustomerUserName.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "marketing worker":
			((Node) event.getSource()).getScene().getWindow().hide();
			// CustomerServiceWelcomeController CSWC = new
			// CustomerServiceWelcomeController(verifieUser2.get(1));
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/MarketingEmployee.fxml"));

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
				disconnect.add(verifieUser.get(4));
				disconnect.add(verifieUser.get(5));
				messageToServer.setData(disconnect);

				stage.setOnCloseRequest(event1 -> {
					client.ClientUI.chat.accept(messageToServer);
					client.ClientUI.chat.client.quit();

				});
				CustomerUserName.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;
		case "Carrier":
			((Node) event.getSource()).getScene().getWindow().hide();
			// CustomerServiceWelcomeController CSWC = new
			// CustomerServiceWelcomeController(verifieUser2.get(1));
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/Carrier_Welcome.fxml"));

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
				disconnect.add(verifieUser.get(4));
				disconnect.add(verifieUser.get(5));
				messageToServer.setData(disconnect);

				stage.setOnCloseRequest(event1 -> {
					client.ClientUI.chat.accept(messageToServer);
					client.ClientUI.chat.client.quit();

				});
				CustomerUserName.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "customer":
			((Node) event.getSource()).getScene().getWindow().hide();
			// CustomerServiceWelcomeController CSWC = new
			// CustomerServiceWelcomeController(verifieUser2.get(1));
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/order1.fxml"));

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
				disconnect.add(verifieUser.get(4));
				disconnect.add(verifieUser.get(5));
				messageToServer.setData(disconnect);

				stage.setOnCloseRequest(event1 -> {
					client.ClientUI.chat.accept(messageToServer);
					client.ClientUI.chat.client.quit();

				});
				CustomerUserName.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "opertion worker":
			((Node) event.getSource()).getScene().getWindow().hide();
			// CustomerServiceWelcomeController CSWC = new
			// CustomerServiceWelcomeController(verifieUser2.get(1));
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/welcome opertion worker.fxml"));

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
				disconnect.add(verifieUser.get(4));
				disconnect.add(verifieUser.get(5));
				messageToServer.setData(disconnect);

				stage.setOnCloseRequest(event1 -> {
					client.ClientUI.chat.accept(messageToServer);
					client.ClientUI.chat.client.quit();

				});
				CustomerUserName.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "customer Service":

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
				disconnect.add(verifieUser.get(4));
				disconnect.add(verifieUser.get(5));
				messageToServer.setData(disconnect);

				stage.setOnCloseRequest(event1 -> {
					client.ClientUI.chat.accept(messageToServer);
					client.ClientUI.chat.client.quit();

				});
				CustomerUserName.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "ceo":
			((Node) event.getSource()).getScene().getWindow().hide();
			// CustomerServiceWelcomeController CSWC = new
			// CustomerServiceWelcomeController(verifieUser2.get(1));
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CEOWelcomeGUI.fxml"));

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
				disconnect.add(verifieUser.get(4));
				disconnect.add(verifieUser.get(5));
				messageToServer.setData(disconnect);

				stage.setOnCloseRequest(event1 -> {
					client.ClientUI.chat.accept(messageToServer);
					client.ClientUI.chat.client.quit();

				});
				CustomerUserName.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "user":
			incorrect.setVisible(false);
			AlraedyLogged.setVisible(false);
			userNotCostumer.setVisible(true);
			break;
		case "alreadyLogged":
			incorrect.setVisible(false);
			AlraedyLogged.setVisible(true);
			userNotCostumer.setVisible(false);
			break;
		/*
		 * case "NotExist": AlraedyLogged.setVisible(false); incorrect.setVisible(true);
		 * break; case "username with space": AlraedyLogged.setVisible(false);
		 * incorrect.setVisible(true); break; case "password with space":
		 * AlraedyLogged.setVisible(false); incorrect.setVisible(true); break;
		 */
		default:
			AlraedyLogged.setVisible(false);
			incorrect.setVisible(true);
			userNotCostumer.setVisible(false);
		}
	}

	/**
	 * initialize, method that set the data that loads with the gui start up
	 * 
	 * @param location
	 * @param resources
	 * 
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);

		incorrect.setVisible(false);
		AlraedyLogged.setVisible(false);
		userNotCostumer.setVisible(false);

		Massage messageToServer = new Massage();
		messageToServer.setOperation("get customers");
		client.ClientUI.chat.accept(messageToServer);

		customer.setItems(CustomerUserName);

	}

	/**
	 * A class that implements the ILoginController interface that manages the
	 * verification of user information
	 * 
	 * @author Avner
	 *
	 */
	public class LoginControllerManager implements ILoginController {

		/**
		 * A method that asks the server if the user is registered
		 */
		@Override
		public String verifay(String usernameString, String passwordString) {
			Massage messageToServer = new Massage();
			ArrayList<String> login = new ArrayList<>();

			messageToServer.setOperation("login");
			login.add(usernameString);
			login.add(passwordString);
			messageToServer.setData(login);
			client.ClientUI.chat.accept(messageToServer);
			return verifieUser.get(0);
		}

	}

}
