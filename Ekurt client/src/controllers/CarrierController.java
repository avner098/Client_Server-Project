package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import client.ClientUI;
import common.Massage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Carrier  that control all the delivery in its region authorize delivery ,
 * close delivery and set Estimate delivery date.
 * 
 * @author matan turjeman
 *
 */
public class CarrierController {
	/**
	 * save name of the Carrier username
	 */
	public String name;
	/**
	 * save the data from db
	 */
	public static ArrayList<String> data = new ArrayList<String>();
	/**
	 * button of the open delivery 
	 */
	@FXML
	private Button deliverybtn;

	/**
	 * label of welcome to set the name
	 */
	@FXML
	private Label welcome;
    /**
     * logo
     */
    @FXML
    private ImageView img;

	@FXML
	void eaea0e1(ActionEvent event) {

	}

	/**
	 * fxml method to move to message gui window when click on message button
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void MoveToMessageWindow(ActionEvent event) throws IOException {
		Massage messageToServer = new Massage();

		((Node) event.getSource()).getScene().getWindow().hide();
		// CustomerServiceWelcomeController CSWC = new
		// CustomerServiceWelcomeController(verifieUser2.get(1));
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/chat.fxml"));

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
	 * fxml method to move to login gui window when click on logout button
	 * 
	 * @param event
	 */
	@FXML
	void logout(ActionEvent event) {

		Massage messageToServer = new Massage();

		String usernameString;
		String passwordString;
		ArrayList<String> logout = new ArrayList<>();

		usernameString = LoginController.verifieUser.get(4);
		passwordString = LoginController.verifieUser.get(5);

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
	 * fxml method to move to OpenDelivery gui window when click on Open delivery
	 * button
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void MoveToDelivaryWindow(ActionEvent event) throws IOException {

		Massage messageToServer = new Massage();

		((Node) event.getSource()).getScene().getWindow().hide();
		// CustomerServiceWelcomeController CSWC = new
		// CustomerServiceWelcomeController(verifieUser2.get(1));
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/OpenDelivery.fxml"));

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
	void eaea0e2(ActionEvent event) {

	}

	@FXML
	void eaea0e3(ActionEvent event) {

	}

	/**
	 * Method that initialize the value in the start of the gui application and also
	 * add new order in the delivery table .
	 */
	@FXML
	void initialize() {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);
		name = LoginController.verifieUser.get(1) + " " + LoginController.verifieUser.get(2);
		welcome.setText("Welcome,\n" + name);
		Massage messageToServer = new Massage();
		messageToServer.setOperation("Carrier");
		messageToServer.setData(data);
		client.ClientUI.chat.accept(messageToServer);
		messageToServer.setOperation("Carrier");
		data.clear();
		data.add("check arrive status");
		messageToServer.setData(data);
		client.ClientUI.chat.accept(messageToServer);
	}
}
