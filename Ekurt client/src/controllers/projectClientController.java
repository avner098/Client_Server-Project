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
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class projectClientController implements Initializable {

	public ArrayList<String> update = new ArrayList<>();
	public ArrayList<String> info = new ArrayList<>();
	public static ArrayList<String> infotoprint = new ArrayList<>();

	final public static int DEFAULT_PORT = 5555;

	@FXML
	private TextField card;
	

	@FXML
	private TextField cardUpdate;

	@FXML
	private TextField email;

	@FXML
	private TextField idupdate;
	@FXML
	private TextField firstName;

	@FXML
	private TextField id;

	@FXML
	private ImageView img;

	@FXML
	private TextField lastName;

	@FXML
	private TextField idInfo;
	@FXML
	private TextField phone;

	@FXML
	private TextArea putInfo = null;

	@FXML
	private TextField subscriber;

	@FXML
	private TextField subscriberUpdate;

	@FXML
	/*
	 * Requests server to update Data
	 */
	void update(ActionEvent event) {
		Massage messageToServer = new Massage();
		
		update.clear();
		messageToServer.setOperation("update");
		//update.add("update");
		String idU = idupdate.getText();
		String cardU = cardUpdate.getText();
		String subscriberU = subscriberUpdate.getText();
		update.add(idU);
		update.add(cardU);
		update.add(subscriberU);
		messageToServer.setData(update);
		client.ClientUI.chat.accept(messageToServer);

	}

	@FXML

	/*
	 * Method that requests information for entered customer ID
	 */
	void getInformation(ActionEvent event) {
		Massage messageToServer = new Massage();
		info.clear();
		//info.add("information");
		messageToServer.setOperation("information");;
		info.add(idInfo.getText());
		messageToServer.setData(info);
		client.ClientUI.chat.accept(messageToServer);
		if (!infotoprint.isEmpty())
			setInfo(infotoprint);

	}

	/*
	 * customer ID Method that print the data to text area
	 */
	public void setInfo(ArrayList<String> info) {
		try {

			putInfo.setText(info.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Method that prints straight to the console
	 */
	public void display(String message) {

		System.out.println("> " + message);
	}

	/*
	 * Method that starts the Client GUI
	 */
	public void start(Stage primaryStage) {


	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);		
	}

}
