package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Massage;
import entities.Sale_Employee;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * class the control the sale activation and un activation
 * 
 * @author matan turjeman
 *
 */
public class ActivateSaleController implements Initializable {
	/**
	 * list of options used to show in the combo box
	 */
	public static ObservableList<String> options = FXCollections.observableArrayList("Not Active Sale", "Active Sale");
	/**
	 * list of row in the table of gui
	 */
	public static ObservableList<Sale_Employee> list = FXCollections.observableArrayList();
	/**
	 * data use to store info from db table sale
	 */
	public static ArrayList<String> data = new ArrayList<>();

	/**
	 * opening label
	 */
	@FXML
	private Label welcome;

	/**
	 * logo
	 */
	@FXML
	private ImageView img;

	/**
	 * table in gui
	 */
	@FXML
	private TableView<Sale_Employee> table;

	/**
	 * table column that represent id in the table of the gui
	 */
	@FXML
	private TableColumn<Sale_Employee, String> id;

	/**
	 * table column that represent region in the table of the gui
	 */
	@FXML
	private TableColumn<Sale_Employee, String> region;

	/**
	 * table column that represent Product in the table of the gui
	 */
	@FXML
	private TableColumn<Sale_Employee, String> Product;

	/**
	 * table column that represent percentage in the table of the gui
	 */
	@FXML
	private TableColumn<Sale_Employee, String> percentage;

	/**
	 * table column that represent description in the table of the gui
	 */
	@FXML
	private TableColumn<Sale_Employee, String> description;

	/**
	 * table column that represent status in the table of the gui
	 */
	@FXML
	private TableColumn<Sale_Employee, String> status;

	/**
	 * fxml method that when click on apply button set the Status The Sale Discount
	 * 
	 * @param event
	 */
	@FXML
	void Apply(ActionEvent event) {
		int Status_index = 4;
		int Request_index = 6;
		int flag = 0;
		ArrayList<String> changeStatus_Array = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getStatus().getValue().toString().equals("Active Sale")
					&& data.get(Status_index).equals("0") && data.get(Request_index).equals("1")) {
				Massage messageToServer = new Massage();
				messageToServer.setOperation("Marketing Employee");
				changeStatus_Array.add(list.get(i).getId());
				changeStatus_Array.add("1");
				messageToServer.setData(changeStatus_Array);
				client.ClientUI.chat.accept(messageToServer);
				changeStatus_Array.clear();
				flag = 1;

			} else if (list.get(i).getStatus().getValue().toString().equals("Not Active Sale")
					&& data.get(Status_index).equals("1") && data.get(Request_index).equals("1")) {
				Massage messageToServer = new Massage();
				messageToServer.setOperation("Marketing Employee");
				changeStatus_Array.add(list.get(i).getId());
				changeStatus_Array.add("0");
				messageToServer.setData(changeStatus_Array);
				client.ClientUI.chat.accept(messageToServer);
				changeStatus_Array.clear();
				flag = 1;
			}
			Status_index += 7;
			Request_index += 7;

		}
		if (flag == 1) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Change Sale Discount Status");
			alert.setContentText("Great Status was Changed\n");
			alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
			alert.showAndWait();
			addToTable();// returning the db to the updated state
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Fail To Change Sale Discount Status");
			alert.setContentText("Cant Change Sale Discount Status without Order From Manager");
			alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
			alert.showAndWait();
			addToTable();// returning the db to the updated state

		}

	}

	/**
	 * fxml method that when click on button back moving to MarketingEmployee gui
	 * window.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void BackWindows(ActionEvent event) throws IOException {

		Massage messageToServer = new Massage();

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
	 * private method that set value from db sale to the table view per column
	 */
	void addToTable() {
		data.clear();
		list.clear();
		data.add("initialize");
		data.add(LoginController.verifieUser.get(6));
		Massage messageToServer = new Massage();
		messageToServer.setOperation("Marketing Employee");
		messageToServer.setData(data);
		client.ClientUI.chat.accept(messageToServer);
		for (int i = 0; i < data.size(); i = i + 7) {
			String id = data.get(i);
			String region = data.get(i + 1);
			String percentage = data.get(i + 2);
			String decription = data.get(i + 3);
			String status = data.get(i + 4);
			if (status.equals("0"))
				status = "Not Active Sale";
			else
				status = "Active Sale";
			String Product = data.get(i + 5);
			String Request = data.get(i + 6);
			list.add(new Sale_Employee(id, region, percentage, decription, Product, options, Request));
			list.get(i / 7).setStatus(status);

		}
		table.setItems(list);
	}

	/**
	 * Method that initialize the value in the start of the gui application
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);
		id.setCellValueFactory(new PropertyValueFactory<Sale_Employee, String>("id"));
		region.setCellValueFactory(new PropertyValueFactory<Sale_Employee, String>("region"));
		percentage.setCellValueFactory(new PropertyValueFactory<Sale_Employee, String>("percentage"));
		description.setCellValueFactory(new PropertyValueFactory<Sale_Employee, String>("description"));
		status.setCellValueFactory(new PropertyValueFactory<Sale_Employee, String>("status"));
		Product.setCellValueFactory(new PropertyValueFactory<Sale_Employee, String>("Product"));
		addToTable();
	}

}
