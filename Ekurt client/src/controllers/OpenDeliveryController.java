package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import common.Massage;
import entities.Delivery;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * OpenDeliveryController windows that show on table view all the open order and
 * set its appropriate status included set automatic delivery date and send it to
 * the user
 * 
 * @author matan turjeman
 *
 */
public class OpenDeliveryController implements Initializable {
	/**
	 * Variable used to store the region of the carrier
	 */
	public static String Carrier_region = LoginController.verifieUser.get(6); 
	/**
	 * Variable used to store the time_delivery_duration_per_region of each order
	 */
	public static int Time_Delivery_Duration = 0; 							 

	/**
	 * data to save db info from deliveries table
	 */
	public static ArrayList<String> data = new ArrayList<>();
	/**
	 * used for combo box options
	 */
	public static ObservableList<String> options = FXCollections.observableArrayList("Waiting_Delivery_Approval",
			"Set_Eastimated_Delivery_Date", "Arrived");
	/**
	 * use for flag to know when got Arrived confirmation from Customer
	 */
	public static boolean flag_Status_from_server=false;
	/**
	 * list of row  for table view info 
	 */
	public static ObservableList<Delivery> list = FXCollections.observableArrayList();
	/**
	 * table column of Status
	 */
	@FXML
	private TableColumn<Delivery, String> Status;

    /**
     * logo
     */
    @FXML
    private ImageView img;

	/**
	 * apply button to set the status of delivery
	 */
	@FXML
	private Button apply;

	/**
	 * table column of Customer_Phone_Number
	 */
	@FXML
	private TableColumn<Delivery, String> Customer_Phone_Number;

	/**
	 * table column of OrderNum
	 */
	@FXML
	private TableColumn<Delivery, String> OrderNum;

	/**
	 * table column of Customer_Name
	 */
	@FXML
	private TableColumn<Delivery, String> Customer_Name;

	/**
	 * table column of Delivery_Date
	 */
	@FXML
	private TableColumn<Delivery, String> Delivery_Date;

	/**
	 * table column of Address
	 */
	@FXML
	private TableColumn<Delivery, String> Address;

	/**
	 * back button to return to previews window
	 */
	@FXML
	private Button backbtn;

	/**
	 * opening label
	 */
	@FXML
	private Label welcome;

	/**
	 * table of gui
	 */
	@FXML
	private TableView<Delivery> Table_View;

	@FXML
	void eaea0e1(ActionEvent event) {

	}

	/**
	 * fxml method that when click on button back moving to Carrier gui window.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void BackWindows(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/Carrier_Welcome.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root1));
		stage.show();
		((Node) event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * fxml method that when click on apply button set the Status of the delivery
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void apply_changes(ActionEvent event) throws IOException {
		ArrayList<String> info = new ArrayList<String>();
		boolean change_status = false;
		for (int i = 0; i < list.size(); i++) {
			int k = i * 6; // get The Current Index Of Date From The DB Values.
			if (list.get(i).getStatus().getValue().toString().equals("Waiting_Delivery_Approval")
					&& (data.get(k + 5).equals("Set_Eastimated_Delivery_Date") || data.get(k + 5).equals("Arrived"))) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Failed Tp Set Delivery Date");
				alert.setContentText("Cant Changed Delivery Status Back To Waiting_Delivery_Approval ");
				alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				alert.showAndWait();
				update_table_view();
				return;
			} else if (list.get(i).getStatus().getValue().toString().equals("Set_Eastimated_Delivery_Date")
					&& data.get(k + 4).equals("N/A")) {
				// Setting Date Automatically and insert in db
				// get Time Delivery Duration
				Massage messageToServer = new Massage();
				messageToServer.setOperation("delivery");
				info.add(data.get(k));
				info.add("get_Time_Delivery_Duration");
				messageToServer.setData(info);
				client.ClientUI.chat.accept(messageToServer);
				info.clear();
				info.add(setDeliveryDate(Time_Delivery_Duration));
				info.add("Set_Eastimated_Delivery_Date");
				info.add(list.get(i).getOrderNumber());
				info.add("Change_Status");
				messageToServer.setData(info);
				// update query to set the time
				client.ClientUI.chat.accept(messageToServer);
				change_status = true;
				info.clear();
				// send message to client about Delivery update
				info.add(LoginController.verifieUser.get(4));
				info.add(list.get(i).getOrderNumber());
				info.add("Order number "+list.get(i).getOrderNumber() +" Was Authorized And The Delivery Date Is "
						+ setDeliveryDate(Time_Delivery_Duration));
				messageToServer.setData(info);
				messageToServer.setOperation("Automatic SMS to user");
				client.ClientUI.chat.accept(messageToServer);
				info.clear();
			} else if (list.get(i).getStatus().getValue().toString().equals("Arrived")) {
				// change Status to Arrived
				// on Delivery and on Orders
				if (data.get(k + 4).equals("N/A")) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Failed To Set Arrive status");
					alert.setContentText("Cant Close Order When there is no date missing step");
					alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
					alert.showAndWait();
					update_table_view();
					return;
				} else {
					//make sure there was a message from specific user
					Massage messageToServer1 = new Massage();
					messageToServer1.setOperation("check arrived status");
					info.clear();
					info.add(list.get(i).getOrderNumber());
					info.add(LoginController.verifieUser.get(4));
					messageToServer1.setData(info);
					client.ClientUI.chat.accept(messageToServer1);
					if(flag_Status_from_server) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Failed To Set Arrive status");
						alert.setContentText("Cant Close Order When we didnt get conformation from user ");
						alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
						alert.showAndWait();
						update_table_view();
						return;
					}
					// change the status
					info.clear();
					Massage messageToServer = new Massage();
					messageToServer.setOperation("delivery");
					info.add("Finished");
					info.add(list.get(i).getOrderNumber());
					messageToServer.setData(info);
					client.ClientUI.chat.accept(messageToServer);
					change_status = true;
				}
			}

		}
		if (change_status) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Set Delivery Date");
			alert.setContentText("Success Setting Delivery Date Or Closing The Order");
			alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
			alert.showAndWait();
			update_table_view();
		}
	}

	/**
	 * private method that return Estimated delivery time from today with given
	 * amount of time.
	 * 
	 * @param amount
	 * @return deliveryDate
	 */
	private String setDeliveryDate(int amount) {
		// Get current date
		Calendar currentDate = Calendar.getInstance();

		// Add int amount of days to current day
		currentDate.add(Calendar.DATE, amount);

		// Format date as a string
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String deliveryDate = sdf.format(currentDate.getTime());
		return deliveryDate;

	}

	/**
	 * private method that set value from db deliveries to the table view per column
	 */
	private void update_table_view() {
		data.clear();
		list.clear();
		data.add("initialize");
		data.add(LoginController.verifieUser.get(6));
		Massage messageToServer = new Massage();
		messageToServer.setOperation("delivery");
		messageToServer.setData(data);
		client.ClientUI.chat.accept(messageToServer);
		for (int i = 0; i < data.size(); i = i + 6) {
			String Ordernumber = data.get(i);
			String address = data.get(i + 1);
			String customerName = data.get(i + 2);
			String customerPhone = data.get(i + 3);
			String DeliveryDate = data.get(i + 4);
			String status = data.get(i + 5);
			list.add(new Delivery(Ordernumber, address, customerName, customerPhone, DeliveryDate, options));
			list.get(i / 6).setStatus(status);
		}
		Table_View.setItems(list);

	}

	@FXML
	void eaea0e(ActionEvent event) {

	}

	/**
	 * Method that initialize the value in the start of the gui application
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);
		OrderNum.setCellValueFactory(new PropertyValueFactory<Delivery, String>("OrderNumber"));
		Address.setCellValueFactory(new PropertyValueFactory<Delivery, String>("Address"));
		Customer_Name.setCellValueFactory(new PropertyValueFactory<Delivery, String>("Customer_Name"));
		Customer_Phone_Number.setCellValueFactory(new PropertyValueFactory<Delivery, String>("Customer_Phone_Number"));
		Delivery_Date.setCellValueFactory(new PropertyValueFactory<Delivery, String>("Delivery_Date"));
		Status.setCellValueFactory(new PropertyValueFactory<Delivery, String>("Status"));
		update_table_view();

	}

}
