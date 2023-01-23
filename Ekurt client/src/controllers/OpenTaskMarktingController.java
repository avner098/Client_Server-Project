package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Massage;
import entities.Sale;
import entities.Sale_Task;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * class that control that show the task from manager and close it accordingly
 * 
 * @author matan turjeman
 *
 */
public class OpenTaskMarktingController implements Initializable {
	/**
	 * list of row use it the table gui
	 */
	public static ObservableList<Sale_Task> list = FXCollections.observableArrayList();
	/**
	 * data use to store info from table sale
	 */
	public static ArrayList<String> data = new ArrayList<String>();

	/**
	 * back button
	 */
	@FXML
	private Button backbtn;

	/**
	 * table view in the gui window
	 */
	@FXML
	private TableView<Sale_Task> table;

	/**
	 * table column id for the id column
	 */
	@FXML
	private TableColumn<Sale_Task, String> id;

	/**
	 * table column region for the id column
	 */
	@FXML
	private TableColumn<Sale_Task, String> region;

	/**
	 * table column percentage for the id column
	 */
	@FXML
	private TableColumn<Sale_Task, String> percentage;

	/**
	 * table column description for the id column
	 */
	@FXML
	private TableColumn<Sale_Task, String> description;
    /**
     * table column Product for the id column
     */
    @FXML
    private TableColumn<Sale_Task, String> Product;


	/**
	 * table column status for the id column
	 */
	@FXML
	private TableColumn<Sale_Task, String> status;

	/**
	 * table column id for the Open_Task column
	 */
	@FXML
	private TableColumn<Sale_Task, String> Open_Task;


    /**
     * logo
     */
    @FXML
    private ImageView img;


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
		messageToServer.setOperation("Open Task Markting Employee");
		messageToServer.setData(data);
		client.ClientUI.chat.accept(messageToServer);
		for (int i = 0; i < data.size(); i = i + 6) {
			String id = data.get(i);
			String region = data.get(i + 1);
			String percentage = data.get(i + 2);
//			String decription = data.get(i + 3);
			String decription="";
			String status = data.get(i + 4);
			String Product=data.get(i+5);
			if (status.equals("0")) {
				status = "Not Active Sale";
				decription="Need To Activate Sale";
			}
				
			else {
				status = "Active Sale";
				decription="Need To Dis Activate Sale";
			}
			String Request_Status="Open";
			list.add(new Sale_Task(id, region, percentage, decription, status,Product,Request_Status));

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
		id.setCellValueFactory(new PropertyValueFactory<Sale_Task, String>("id"));
		region.setCellValueFactory(new PropertyValueFactory<Sale_Task, String>("region"));
		percentage.setCellValueFactory(new PropertyValueFactory<Sale_Task, String>("percentage"));
		description.setCellValueFactory(new PropertyValueFactory<Sale_Task, String>("description"));
		Product.setCellValueFactory(new PropertyValueFactory<Sale_Task, String>("Product"));
		status.setCellValueFactory(new PropertyValueFactory<Sale_Task, String>("status"));
		Open_Task.setCellValueFactory(new PropertyValueFactory<Sale_Task, String>("Open_Task"));
		addToTable();

	}

}
