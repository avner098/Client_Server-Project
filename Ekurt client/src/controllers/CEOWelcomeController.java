package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
 * * This class is used for CEO to Choose an option.
 *
 */
public class CEOWelcomeController implements Initializable {
	/**
	 * CEO name to show in welcome window.
	 */
	public String name;

	/**
	 * Label that contains CEO name.
	 */
	@FXML
	private Label getName;

	/**
	 * LogOut button to log out.
	 */
	@FXML
	private Button logOut;
	/**
	 * Ekurt Logo to display on the screen
	 */
	@FXML
	private ImageView img;

	/**
	 * fxml method to move to View choose Reports gui window when click on
	 * ViewReprtos button
	 * 
	 * @param event click on Reports button.
	 */
	@FXML
	void getReports(ActionEvent event) {
		Massage messageToServer = new Massage();

		((Node) event.getSource()).getScene().getWindow().hide();
		// CustomerServiceWelcomeController CSWC = new
		// CustomerServiceWelcomeController(verifieUser2.get(1));
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CEOReportChooseWindow.fxml"));

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
	void LogOut(ActionEvent event) {
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);
		name = LoginController.verifieUser.get(1) + " " + LoginController.verifieUser.get(2);
		getName.setText(name);

	}

}
