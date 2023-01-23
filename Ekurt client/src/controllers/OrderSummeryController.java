package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Massage;
import entities.OrderSummery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * @author Avner controller class where the client can see the order summary to
 *         confirm his order
 */
public class OrderSummeryController implements Initializable {

	/**
	 * list of the rows in the table
	 */
	public static ObservableList<OrderSummery> list = FXCollections.observableArrayList();

	/**
	 * order details
	 */
	public static ArrayList<String> order = new ArrayList<>();
	/**
	 * list of price for each product
	 */
	public static ArrayList<String> price = new ArrayList<>();
	/**
	 * list of price after discount if there is for each product
	 */
	public static ArrayList<String> price_dis = new ArrayList<>();
	/**
	 * total order price
	 */
	public static String totalprice = new String();
	/**
	 * list of product in the order
	 */
	public ArrayList<String> products = new ArrayList<>();
	/**
	 * list of amount of each product
	 */
	public ArrayList<String> amount = new ArrayList<>();

	/**
	 * label with the total price
	 */
	@FXML
	private Label totalPrice;
	/**
	 * logo image
	 */
	@FXML
	private ImageView img;

	/**
	 * table with the order summary
	 */
	@FXML
	private TableView<OrderSummery> OrderSummeryTable;

	/**
	 * Column with the product names
	 */
	@FXML
	private TableColumn<OrderSummery, String> productsCol;

	/**
	 * Column with the amount of product
	 */
	@FXML
	private TableColumn<OrderSummery, String> AmountCol;

	/**
	 * Column with the price of the row
	 */
	@FXML
	private TableColumn<OrderSummery, String> priceCol;

	/**
	 * This method deletes the order if the customer leaves the window without going
	 * to the payment screen.
	 * 
	 */
	private void closeProgram(Stage stage) {
		ArrayList<String> priceAmount = new ArrayList<>();
		priceAmount.add(order2Controller.orderNumber);
		priceAmount.add(order2Controller.machine);
		Massage messageToServer = new Massage();
		messageToServer.setOperation("delete order after timeout");
		messageToServer.setData(priceAmount);
		client.ClientUI.chat.accept(messageToServer);
	}

	/**
	 * the client confirm the order and move to the payment window
	 * 
	 * @param event click on confirm button
	 */
	@FXML
	void onconfirm(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		// CustomerServiceWelcomeController CSWC = new
		// CustomerServiceWelcomeController(verifieUser2.get(1));
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/payment and delivery.fxml"));

			// CSWC.name= verifieUser2.get(1);
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
			order2Controller.stage = stage;
			ArrayList<String> disconnect = new ArrayList<>();
			String[] ip_adrrs = null;
			String[] ip_adrrs1 = { "", "" };
			InetAddress ip2 = null;

			ip2 = ClientUI.chat.getIp();

			ip_adrrs = ip2.toString().split("/");

			Massage messageToServer = new Massage();
			;
			// disconnect.add("disconnect");
			messageToServer.setOperation("disconnect");
			disconnect.add(ip_adrrs[1]);
			disconnect.add(ClientUI.chat.getHost());
			disconnect.add(LoginController.verifieUser.get(4));
			disconnect.add(LoginController.verifieUser.get(5));
			messageToServer.setData(disconnect);

			stage.setOnCloseRequest(event1 -> {
				event1.consume();
				closeProgram(stage);
				stage.close();

				client.ClientUI.chat.accept(messageToServer);
				client.ClientUI.chat.client.quit();

			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * move the client back to the order window
	 * 
	 * @param event click on back button
	 */
	@FXML
	void pressBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		// CustomerServiceWelcomeController CSWC = new
		// CustomerServiceWelcomeController(verifieUser2.get(1));
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/order2.fxml"));

			// CSWC.name= verifieUser2.get(1);
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
			order2Controller.stage = stage;

			ArrayList<String> disconnect = new ArrayList<>();
			String[] ip_adrrs = null;
			String[] ip_adrrs1 = { "", "" };
			InetAddress ip2 = null;

			ip2 = ClientUI.chat.getIp();

			ip_adrrs = ip2.toString().split("/");

			Massage messageToServer = new Massage();
			// disconnect.add("disconnect");
			messageToServer.setOperation("disconnect");
			disconnect.add(ip_adrrs[1]);
			disconnect.add(ClientUI.chat.getHost());
			disconnect.add(LoginController.verifieUser.get(4));
			disconnect.add(LoginController.verifieUser.get(5));
			messageToServer.setData(disconnect);

			stage.setOnCloseRequest(event1 -> {
				event1.consume();
				closeProgram(stage);
				stage.close();

				client.ClientUI.chat.accept(messageToServer);
				client.ClientUI.chat.client.quit();

			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * set the order details into the table
	 */
	private double setorderSummery() {
		int cnt = 0;

		for (String x : order) {
			if (cnt < (order.size() / 2)) {
				amount.add(x);
			} else
				products.add(x);
			cnt++;
		}
		double total = 0;

		for (int i = 0; i < products.size(); i++) {

			if (Integer.parseInt(amount.get(i)) != 0) {

				double price = (double) Integer.parseInt(amount.get(i))
						* Double.parseDouble(OrderSummeryController.price_dis.get(i));

				String pricest = String.format("%.3f", price);

				OrderSummery pro = new OrderSummery(products.get(i), amount.get(i), pricest);
				list.add(pro);

				total += price;
			}

		}

		OrderSummeryTable.setItems(list);
		return total;
	}

	/**
	 * initialize the GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list.clear();
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);

		productsCol.setCellValueFactory(new PropertyValueFactory<OrderSummery, String>("product"));
		AmountCol.setCellValueFactory(new PropertyValueFactory<OrderSummery, String>("amount"));
		priceCol.setCellValueFactory(new PropertyValueFactory<OrderSummery, String>("price"));

		Massage messageToServer = new Massage();
		ArrayList<String> arr = new ArrayList<>();

		///////////// order number
		arr.add(order2Controller.orderNumber);
		/////////////

		messageToServer.setOperation("get order summery");
		messageToServer.setData(arr);
		client.ClientUI.chat.accept(messageToServer);

		messageToServer.setOperation("get prices");
		client.ClientUI.chat.accept(messageToServer);

		ArrayList<String> arr1 = new ArrayList<>();

		arr1.add(order2Controller.machine);
		arr1.add(LoginController.verifieUser.get(4));
		messageToServer.setData(arr1);
		messageToServer.setOperation("get discount prices");
		client.ClientUI.chat.accept(messageToServer);

		order.remove(0);

		double total = setorderSummery();

		// totalprice = order.get(0);
		totalprice = String.format("%.3f", total);

		totalPrice.setText("Total order price: " + totalprice + "$");
	}

}
