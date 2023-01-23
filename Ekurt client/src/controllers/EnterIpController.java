package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ClientConsole;
import client.ClientUI;
import common.Massage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller class that control in the EnterIp gui
 */
public class EnterIpController implements Initializable {

	/**
	 * the ip of the server that we are want to work with
	 */
	private String ip;
	/**
	 * logo image
	 */
	@FXML
	private ImageView img;

	/**
	 * Field to enter the ip
	 */
	@FXML
	private TextField ipForServer;

	/**
	 * list of data the client want to sent the server to connect
	 */
	public ArrayList<String> Ip = new ArrayList<>();
	/**
	 * DEFAULT_PORT to connect
	 * 
	 */
	final public static int DEFAULT_PORT = 5555;
	/**
	 * list of data the client want to sent the server to disconnect
	 */
	public ArrayList<String> disconnect = new ArrayList<>();

	/**
	 * Method that send the entered server IP from client GUI to server
	 * 
	 * @param event click on connect button
	 * @throws IOException
	 */
	@FXML

	void connect(ActionEvent event) throws IOException {

		Massage messageToServer = new Massage();
		ip = "";
		ip = ipForServer.getText();

		ClientUI.chat = new ClientConsole(ip, 5555);
		// Ip.add("connect");

		String[] ip_adrrs = null;
		String[] ip_adrrs1 = { "", "" };
		InetAddress ip2 = null;

		ip2 = ClientUI.chat.getIp();

		if (ip != null)
			ip_adrrs = ip2.toString().split("/");
		else
			ip_adrrs = ip_adrrs1;

		Ip.add(ip_adrrs[1]);
		Ip.add(ip);

		if (ip.equals("")) {
			Alert alert = new Alert(AlertType.NONE, "enter ip please", ButtonType.FINISH);

			Optional<ButtonType> result = alert.showAndWait();
		} else {
			messageToServer.setOperation("connect");
			messageToServer.setData(Ip);
			client.ClientUI.chat.accept(messageToServer);

			// FXMLLoader fxmlLoader = new
			// FXMLLoader(getClass().getResource("/gui/client.fxml"));
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/machineChoose.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
			/*
			 * disconnect.add("disconnect"); disconnect.add(ip_adrrs[1]);
			 * disconnect.add(ip);
			 */
			messageToServer.setOperation("disconnect");
			stage.setOnCloseRequest(event1 -> {
				client.ClientUI.chat.accept(messageToServer);
				client.ClientUI.chat.client.quit();

			});
			((Node) event.getSource()).getScene().getWindow().hide();
		}
	}

	/**
	 * upload the Gui to the screen
	 * 
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		VBox vbox;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/EnterIp.fxml"));
			vbox = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene s = new Scene(vbox);
		primaryStage.setScene(s);
		primaryStage.show();

	}

	/**
	 * initialize the GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);

	}

}
