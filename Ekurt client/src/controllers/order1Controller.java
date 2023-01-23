package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ClientUI;
import common.Massage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * This class is a transition between windows, depending on what the client
 * chooses.
 *
 */
public class order1Controller implements Initializable {

	/**
	 * return from the server
	 */
	public static ArrayList<String> inventory2 = new ArrayList<>();

	/**
	 * flag to display the order status one time.
	 */
	static boolean flag = true;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	/**
	 * The logo image.
	 */
	@FXML
	private ImageView img;

	@FXML
	private ImageView image1;

	@FXML
	private ImageView image2;

	@FXML
	private ImageView image3;

	/**
	 * welcome label
	 */
	@FXML
	private Label welcome;

	/**
	 * stage that use for closing the window in case of abandoning an order
	 */
	public static Stage stage1;

	/**
	 * event for using the back button in another method
	 */
	public static ActionEvent event1;

	/**
	 * logout the user from the system
	 * 
	 * @param event click on logout button
	 */
	@FXML
	void logout(ActionEvent event) {
		if (order2Controller.flag) {
			closeProgram(); // deleting the order
		}
		Massage messageToServer = new Massage();

		String usernameString; // holding the user name
		String passwordString; // holding the password
		ArrayList<String> logout = new ArrayList<>();

		usernameString = LoginController.verifieUser.get(4);
		passwordString = LoginController.verifieUser.get(5);
		// asking the server to logout.
		messageToServer.setOperation("logout");
		logout.add(usernameString);
		logout.add(passwordString);
		messageToServer.setData(logout);

		client.ClientUI.chat.accept(messageToServer);

		((Node) event.getSource()).getScene().getWindow().hide();

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/login.fxml"));

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
	 * move the client to chat window where the client can see his messeges
	 * 
	 * @param event click on messeges
	 */
	@FXML
	void messege(ActionEvent event) {

		Massage messageToServer = new Massage();

		((Node) event.getSource()).getScene().getWindow().hide();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/chat.fxml"));

			// CSWC.name= verifieUser2.get(1);
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
			stage1 = stage;
			event1 = event;
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

				event1.consume();
				closeProgram();// in case of abandonment of the order, the server will delete the order.
				stage.close();

				client.ClientUI.chat.accept(messageToServer);
				client.ClientUI.chat.client.quit();

			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * move the client to create_new_order window where the client can create a new
	 * order remote or on site
	 * 
	 * @param event click on create_new_order button
	 * @throws IOException
	 */
	@FXML
	void create_new_order(ActionEvent event) throws IOException {

		Massage messageToServer = new Massage();

		((Node) event.getSource()).getScene().getWindow().hide();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/order2.fxml"));

			// CSWC.name= verifieUser2.get(1);
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
			order2Controller.stage = stage;
			//stage1 = stage;
			
			event1 = event;
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

				event1.consume();
				closeProgram();// in case of abandonment of the order, the server will delete the order.
				stage.close();

				client.ClientUI.chat.accept(messageToServer);
				client.ClientUI.chat.client.quit();

			});

		} catch (IOException e) {
			e.printStackTrace();
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
	 * move the client to manage_your_orders window where he can see all his orders
	 * 
	 * @param event click on manage your orders button
	 * @throws IOException
	 */
	@FXML
	void manage_your_orders(ActionEvent event) throws IOException {

		Massage messageToServer = new Massage();

		((Node) event.getSource()).getScene().getWindow().hide();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/mangeOrder.fxml"));// Transfers the
																									// customer to the
																									// order management
																									// window.
			// CSWC.name= verifieUser2.get(1);
			Parent root1 = (Parent) fxmlLoader.load();
			stage1 = new Stage();
			stage1.setScene(new Scene(root1));
			stage1.show();

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

			stage1.setOnCloseRequest(event1 -> {
				event1.consume();
				closeProgram();
				stage1.close();

				client.ClientUI.chat.accept(messageToServer);
				client.ClientUI.chat.client.quit();

			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * A method to initialize the class
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));// set the logo image
		img.setImage(image);
		welcome.setText("Welcome " + LoginController.verifieUser.get(1) + " " + LoginController.verifieUser.get(2));// set
																													// label
																													// that
																													// says
																													// welcome
																													// with
																													// the
																													// user
																													// name
		ArrayList<String> name = new ArrayList<>();
		Massage messageToServer5 = new Massage();
		name.add(LoginController.verifieUser.get(4));// user name
		messageToServer5.setData(name);
		messageToServer5.setOperation("get status");// Requests the server to update the database with the order
													// execution time
		client.ClientUI.chat.accept(messageToServer5);
		String check = "";
		if (inventory2 == null || inventory2.isEmpty() || inventory2.get(0) == null) {
			check = "";
		} // check that the server return the order status.
		else {
			check = inventory2.get(0);// The order status.
			if (!(check.equals("") || check == null)) {
				// make sure that the alert will be displayed once.
				if (flag) {
					flag = false;
					JOptionPane.showMessageDialog(null, inventory2.get(0), "status", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

}
