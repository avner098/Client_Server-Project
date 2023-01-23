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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * class that represent Marketing Employee that activate sale by order from its
 * manager.
 * 
 * @author matan turjeman
 *
 */
public class MarketingEmployeeController implements Initializable {

	/**
	 * label in the gui 
	 */
	@FXML
	private Label welcome;

    /**
     * logo
     */
    @FXML
    private ImageView img;

	/**
	 * fxml method to move to ActivateSale gui window when click on Activate Sale
	 * button
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void Activate_Sale(ActionEvent event) throws IOException {

		Massage messageToServer = new Massage();

		((Node) event.getSource()).getScene().getWindow().hide();
		// CustomerServiceWelcomeController CSWC = new
		// CustomerServiceWelcomeController(verifieUser2.get(1));
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ActivateSale.fxml"));

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
	 * fxml method to move to OpenTaskMarkting gui window when click on Open Task
	 * From Manager button
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void MoveToOpenTaskWindow(ActionEvent event) throws IOException {

		Massage messageToServer = new Massage();

		((Node) event.getSource()).getScene().getWindow().hide();
		// CustomerServiceWelcomeController CSWC = new
		// CustomerServiceWelcomeController(verifieUser2.get(1));
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/OpenTaskMarkting.fxml"));

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
	 * Method that initialize the value in the start of the gui application
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);
		String name = LoginController.verifieUser.get(1) + " " + LoginController.verifieUser.get(2);
		welcome.setText("Welcome,\n" + name);

	}

}
