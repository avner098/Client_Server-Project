package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Massage;
import entities.CopyOfProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * @author Avner controller class where the client can update the inventory of
 *         machine
 */
public class UpdateInventoryController implements Initializable {

	/**
	 * rows in the table
	 */
	public static ObservableList<CopyOfProduct> list = FXCollections.observableArrayList();
	/**
	 * data that came back from the server
	 */
	public static ArrayList<String> data = new ArrayList<>();
	/**
	 * list of the inventory for each product
	 */
	public static ArrayList<String> inventory = new ArrayList<>();
	/**
	 * list of the machine
	 */
	public static ObservableList<String> machine = FXCollections.observableArrayList();

	/**
	 * list of the products
	 */
	public ArrayList<String> products = new ArrayList<>();
	/**
	 * list of the amount for each product
	 */
	public ArrayList<String> amount = new ArrayList<>();
	/**
	 * Choose machine name
	 */
	public String MacineName;

	/**
	 * combo box that have all the machines names
	 */
	@FXML
	private ComboBox<String> muchinCombo;

	/**
	 * label that indicate that the update success
	 */
	@FXML
	private Label updateSucsess;

	/**
	 * label that indicate that the update fail
	 */
	@FXML
	private Label updatefail;

	/**
	 * table of the machine inventory
	 */
	@FXML
	private TableView<CopyOfProduct> inventoryTable;

	/**
	 * Column with all the product names
	 */
	@FXML
	private TableColumn<CopyOfProduct, String> productsCol;

	/**
	 * Column with all the inventory
	 */
	@FXML
	private TableColumn<CopyOfProduct, String> inventoryCol;

	/**
	 * Column with all the field that the client can update
	 */
	@FXML
	private TableColumn<CopyOfProduct, TextArea> updateCol;

	/**
	 * logo image
	 */
	@FXML
	private ImageView img;

	/**
	 * update the inventory by send the information to the server
	 * 
	 * @param event click on update button
	 * 
	 */
	@FXML
	void onupdate(ActionEvent event) {

		try {
			Massage messageToServer = new Massage();
			messageToServer.setOperation("update invntory");

			int afterUpdate = 0;
			ArrayList<String> updateArray = new ArrayList<>();
			for (CopyOfProduct x : list) {

				int productInventory = Integer.parseInt(x.getInventory());
				int update = Integer.parseInt(x.getUpdateText());
				afterUpdate = productInventory + update;
				updateArray.add(afterUpdate + "");
			}

			updateArray.addAll(0, products);

			if (MacineName == null)
				throw new Exception();

			updateArray.add(0, MacineName);

			messageToServer.setData(updateArray);

			client.ClientUI.chat.accept(messageToServer);
			updatefail.setVisible(false);
			updateSucsess.setVisible(true);

			for (CopyOfProduct x : list) {
				x.getUpdate().clear();
			}
			ArrayList<String> machinName = new ArrayList<String>();

			MacineName = muchinCombo.getValue();
			System.out.println(MacineName);
			inventoryTable.getItems().clear();
			list.clear();
			products.clear();
			amount.clear();

			messageToServer.setOperation("inventory update Upload");

			machinName.add(MacineName);
			messageToServer.setData(machinName);
			client.ClientUI.chat.accept(messageToServer);

			update_table_view();
		} catch (Exception ex) {
			updatefail.setVisible(true);
			updateSucsess.setVisible(false);
		}

	}

	/**
	 * move the client back to the main window
	 * 
	 * @param event click on the back button
	 */
	@FXML
	void pressBack(ActionEvent event) {

		machine.clear();
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
	 * upload to the screen the current machine inventory and option to update the
	 * inventory table
	 * 
	 * @param event click on show
	 */
	@FXML
	void onShow(ActionEvent event) {

		ArrayList<String> machinName = new ArrayList<String>();

		MacineName = muchinCombo.getValue();
		System.out.println(MacineName);
		inventoryTable.getItems().clear();
		list.clear();
		products.clear();
		amount.clear();
		Massage messageToServer = new Massage();
		messageToServer.setOperation("inventory update Upload");

		machinName.add(MacineName);
		messageToServer.setData(machinName);
		client.ClientUI.chat.accept(messageToServer);

		update_table_view();

	}

	/**
	 * update the inventory table on the screen
	 */
	private void update_table_view() {

		int cnt = 0;

		for (String x : inventory) {
			if (cnt < (inventory.size() / 2)) {
				amount.add(x);
			} else
				products.add(x);
			cnt++;
		}

		for (int i = 0; i < products.size(); i++) {
			CopyOfProduct pro = new CopyOfProduct(products.get(i), amount.get(i));
			pro.getUpdate().setText("0");
			list.add(pro);

		}

		inventoryTable.setItems(list);

	}

	/**
	 * set the machine are available to update
	 */
	private void setmachine() {
		machine.clear();
		Massage messageToServer = new Massage();
		messageToServer.setOperation("get machine by region");
		messageToServer.setData(data);
		client.ClientUI.chat.accept(messageToServer);

		muchinCombo.setItems(machine);

	}

	/**
	 * initialize the GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);

		updateSucsess.setVisible(false);
		updatefail.setVisible(false);

		data.add(LoginController.verifieUser.get(6));

		productsCol.setCellValueFactory(new PropertyValueFactory<CopyOfProduct, String>("product"));
		inventoryCol.setCellValueFactory(new PropertyValueFactory<CopyOfProduct, String>("inventory"));
		updateCol.setCellValueFactory(new PropertyValueFactory<CopyOfProduct, TextArea>("update"));

		setmachine();

	}

}
