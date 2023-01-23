package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import common.Massage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * This class is used to install the interface for the client, the client can
 * choose whether to install it at his place or on a specific machine. When
 * choosing a specific machine, the customer will not be able to choose another
 * machine later
 *
 */
public class machineChooseController implements Initializable {

	public static ArrayList<String> inv = new ArrayList<>();

	/**
	 * The logo of the company.
	 */
	@FXML
	private ImageView img;

	/**
	 * The machine that the customer chose.
	 */
	public static String machine2 = null; 

	/**
	 * the status of the order
	 */
	public static String status; 
	/**
	 * choice box that the customer can choose machine from.
	 */
	@FXML
	private ChoiceBox<String> choose_machine; 

	/**
	 * This method is used for selecting the installation of the interface on a
	 * specific machine, the customer will have the option to choose any machine on
	 * which he would like to install. 
	 * @param event
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@FXML
	void pysical(ActionEvent event) throws IOException, InterruptedException {

		status = "pysical"; //pysical order
		Massage messageToServer = new Massage();
		System.out.println(inv.get(0));

		if (choose_machine.getValue() != null) {
			machine2 = choose_machine.getValue();
			//on clice the login window appear
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/login.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
			messageToServer.setOperation("disconnect");
			stage.setOnCloseRequest(event1 -> {
			client.ClientUI.chat.client.quit();

			});
			((Node) event.getSource()).getScene().getWindow().hide();
		}
	}

	/**
	 * For a remote order, the method will move the customer to the next login page.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void remote(ActionEvent event) throws IOException {
		status = "remote";
		Massage messageToServer = new Massage();
		//on clice the login window appear
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/login.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root1));
		stage.show();

		messageToServer.setOperation("disconnect");
		stage.setOnCloseRequest(event1 -> {

			client.ClientUI.chat.client.quit();

		});
		((Node) event.getSource()).getScene().getWindow().hide();

	}

	/**
	 *The method initializes the class,
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg")); //set the logo image in the window
		img.setImage(image);
		ArrayList<String> priceAmount = new ArrayList<>();
		Massage messageToServer6 = new Massage();
		messageToServer6.setData(priceAmount);
		messageToServer6.setOperation("machines");// Asks the server to insert a new order line
		client.ClientUI.chat.accept(messageToServer6);
		String[] st = { inv.get(0), inv.get(1), inv.get(2), inv.get(3), inv.get(4), inv.get(5), }; // Updates the list
		// of machines
		choose_machine.getItems().addAll(st);
	}

}
