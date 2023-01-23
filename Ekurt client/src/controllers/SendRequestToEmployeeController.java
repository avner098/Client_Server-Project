package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Massage;
import entities.Delivery;
import entities.Sale;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * class the sent request to employee to activate/deactivate specific sale
 * discount
 * 
 * @author matan turjeman
 *
 */
public class SendRequestToEmployeeController implements Initializable {
	/**
	 * list of row from the table in gui window
	 */
	public static ObservableList<Sale> list = FXCollections.observableArrayList();
	/**
	 * options in the combo box status 
	 */
	public static ObservableList<String> options = FXCollections.observableArrayList("Activate Sale", "diActivte Sale");
	/**
	 * use  to save data info from db of sale
	 */
	public static ArrayList<String> data = new ArrayList<String>();

	/**
	 * back button
	 */
	@FXML
	private Button backbtn;

	/**
	 * table column of id
	 */
	@FXML
	private TableColumn<Sale, String> id;

	/**
	 * table column of region
	 */
	@FXML
	private TableColumn<Sale, String> region;

	/**
	 * table column of percentage
	 */
	@FXML
	private TableColumn<Sale, String> percentage;

	/**
	 * table column of description
	 */
	@FXML
	private TableColumn<Sale, String> description;
	/**
	 * table column of Product
	 */
	@FXML
	private TableColumn<Sale, String> Product;

	/**
	 * table column of status
	 */
	@FXML
	private TableColumn<Sale, String> status;
	/**
	 * table column of Send_Request_To_Employee
	 */
	@FXML
	private TableColumn<Sale, String> Send_Request_To_Employee;
	/**
	 * table view in the gui
	 */
	@FXML
	private TableView<Sale> table;
	/**
	 * logo
	 */
	@FXML
	private ImageView img;

	/**
	 * fxml method that when click on button back moving to MarktingManger gui
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
	 * fxml method that when click on button Apply send request of the marketing
	 * employee to activate/Deactivate sale
	 * 
	 * @param event
	 */
	@FXML
	void Apply(ActionEvent event) {
		ArrayList<String> info = new ArrayList<>();
		boolean error_Status = false;
		boolean pass_status = false;
		for (int i = 0; i < list.size(); i++) {
//    		System.out.println("List in place "+i);
//    		if(list.get(i).getSend_Request_To_Employee().getValue()!=null)
//    		System.out.println(list.get(i).getSend_Request_To_Employee().getValue().toString());
//    		else
//    			System.out.println(list.get(i).getSend_Request_To_Employee().getValue());
			if (list.get(i).getSend_Request_To_Employee().getValue() != null) {
				if (list.get(i).getStatus().equals("Active Sale")
						&& list.get(i).getSend_Request_To_Employee().getValue().toString().equals("Activate Sale"))
					error_Status = true;
				else if (list.get(i).getStatus().equals("Not Active Sale")
						&& list.get(i).getSend_Request_To_Employee().getValue().toString().equals("diActivte Sale"))
					error_Status = true;
				else {
					info.add(list.get(i).getId());
					info.add("1");
					Massage messageToServer = new Massage();
					messageToServer.setOperation("Marketing Request");
					messageToServer.setData(info);
					client.ClientUI.chat.accept(messageToServer);
					info.clear();
					pass_status = true;

				}
			}

		}
		if (error_Status) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Cannot Request Change Sale Status");
			alert.setContentText("Cannot Request Change Sale Status\n When Status is Already Applied\n");
			alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
			alert.showAndWait();
		} else if (pass_status) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Request Change Sale Status");
			alert.setContentText("Great Request Change Sale Status \n");
			alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
			alert.showAndWait();
			addToTable();
		}

	}

	/**
	 * private method that set value from db sale to the table view per column
	 */
	void addToTable() {
		String Request = "";
		data.clear();
		list.clear();
		data.add("initialize");
		data.add(LoginController.verifieUser.get(6));
		Massage messageToServer = new Massage();
		messageToServer.setOperation("Marketing Request");
		messageToServer.setData(data);
		client.ClientUI.chat.accept(messageToServer);
		for (int i = 0; i < data.size(); i = i + 7) {
			String id = data.get(i);
			String region = data.get(i + 1);
			String percentage = data.get(i + 2);
			String decription = data.get(i + 3);
			String status = data.get(i + 4);
			String Request_Status = data.get(i + 5);
			String Product = data.get(i + 6);
			if (status.equals("0")) {
				status = "Not Active Sale";
				Request = "Active Sale";
			} else {
				status = "Active Sale";
				Request = "Not Active Sale";
			}
			list.add(new Sale(id, region, percentage, decription, status, Product, options));
			if (Request_Status.equals("1"))
				list.get(i / 6).setSend_Request_To_Employee(Request);

		}
//		list.add(new Sale("1","2","3","4","5",options));
		table.setItems(list);
	}

	/**
	 * Method that initialize the value in the start of the gui application
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);
		id.setCellValueFactory(new PropertyValueFactory<Sale, String>("id"));
		region.setCellValueFactory(new PropertyValueFactory<Sale, String>("region"));
		percentage.setCellValueFactory(new PropertyValueFactory<Sale, String>("percentage"));
		description.setCellValueFactory(new PropertyValueFactory<Sale, String>("description"));
		status.setCellValueFactory(new PropertyValueFactory<Sale, String>("status"));
		Product.setCellValueFactory(new PropertyValueFactory<Sale, String>("Product"));
		Send_Request_To_Employee
				.setCellValueFactory(new PropertyValueFactory<Sale, String>("Send_Request_To_Employee"));
		addToTable();

	}

}
