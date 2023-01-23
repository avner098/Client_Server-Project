package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Massage;
import entities.RestokeCall;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * @author Avner controller class where the client can see all the restock calls
 *         the area manager open and he can close them
 */
public class RestokeCallController implements Initializable {

	/**
	 * rows in the table
	 */
	public static ObservableList<RestokeCall> list = FXCollections.observableArrayList();
	/**
	 * open or close call on comboBox
	 */
	private static ObservableList<String> option = FXCollections.observableArrayList(new String("Open"),
			new String("Close"));

	/**
	 * data that back from the server
	 */
	public static ArrayList<String> data = new ArrayList<>();

	/**
	 * logo image
	 */
	@FXML
	private ImageView img;

	/**
	 * back button
	 */
	@FXML
	private Button btnBack;

	/**
	 * update button
	 */
	@FXML
	private Button btnupdate;

	/**
	 * Column with the low bar info
	 */
	@FXML
	private TableColumn<RestokeCall, String> lowbarCol;

	/**
	 * Column with the machine name
	 */
	@FXML
	private TableColumn<RestokeCall, String> machinenNmeCol;

	/**
	 * table with all the Restock calls
	 */
	@FXML
	private TableView<RestokeCall> messegeTable;

	/**
	 * Column with the product need to restock
	 */
	@FXML
	private TableColumn<RestokeCall, String> productCol;

	/**
	 * Column with the call id
	 */
	@FXML
	private TableColumn<RestokeCall, String> callidcol;

	/**
	 * Column with the region of the machine
	 */
	@FXML
	private TableColumn<RestokeCall, String> regionCol;

	/**
	 * label that indicate that the update fail
	 */
	@FXML
	private Label sendFail;

	/**
	 * label that indicate that the update Sucsess
	 */
	@FXML
	private Label sendSucsess;

	/**
	 * Column with the option to close / open call
	 */
	@FXML
	private TableColumn<RestokeCall, ComboBox<String>> statusCol;

	/**
	 * refresh the table ask the server if we have new calls
	 * 
	 * @param event click on refresh button
	 */
	@FXML
	void onRefresh(ActionEvent event) {

		list.clear();
		Massage messageToServer = new Massage();
		messageToServer.setOperation("get open restoke");
		client.ClientUI.chat.accept(messageToServer);

		setrestokeTable();
	}

	/**
	 * send all the close calls to the server
	 * 
	 * @param event click on send Button
	 */
	@FXML
	void onsend(ActionEvent event) {

		boolean flag = false;

		for (RestokeCall x : list) {
			if (x.getstatusText().equals("Close")) {
				flag = true;
				Massage messageToServer = new Massage();
				ArrayList<String> data = new ArrayList<>();
				data.add(x.getCallId());
				messageToServer.setData(data);
				messageToServer.setOperation("close restoke call");
				client.ClientUI.chat.accept(messageToServer);
			}
		}

		if (flag) {
			list.clear();
			Massage messageToServer = new Massage();
			messageToServer.setOperation("get open restoke");
			client.ClientUI.chat.accept(messageToServer);

			setrestokeTable();

			sendFail.setVisible(false);
			sendSucsess.setVisible(true);
		} else {
			sendFail.setVisible(true);
			sendSucsess.setVisible(false);
		}
	}

	/**
	 * move the client back to the main window
	 * 
	 * @param event click on back button
	 */
	@FXML
	void pressBack(ActionEvent event) {
		list.clear();
		Massage messageToServer = new Massage();

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
	 * set all the rows in the table
	 * 
	 */
	private void setrestokeTable() {
		for (int i = 0; i < data.size(); i += 5) {

			RestokeCall restoke = new RestokeCall(data.get(i), data.get(i + 1), data.get(i + 2), data.get(i + 3),
					data.get(i + 4), option);

			list.add(restoke);

		}

		messegeTable.setItems(list);
	}

	/**
	 * initialize the gui
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);

		sendFail.setVisible(false);
		sendSucsess.setVisible(false);

		callidcol.setCellValueFactory(new PropertyValueFactory<RestokeCall, String>("callId"));
		machinenNmeCol.setCellValueFactory(new PropertyValueFactory<RestokeCall, String>("name"));
		regionCol.setCellValueFactory(new PropertyValueFactory<RestokeCall, String>("region"));
		productCol.setCellValueFactory(new PropertyValueFactory<RestokeCall, String>("products"));
		lowbarCol.setCellValueFactory(new PropertyValueFactory<RestokeCall, String>("lowbar"));
		statusCol.setCellValueFactory(new PropertyValueFactory<RestokeCall, ComboBox<String>>("status"));

		Massage messageToServer = new Massage();
		messageToServer.setOperation("get open restoke");
		client.ClientUI.chat.accept(messageToServer);

		setrestokeTable();
	}

}
