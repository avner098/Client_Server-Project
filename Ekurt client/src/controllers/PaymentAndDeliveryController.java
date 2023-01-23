package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Massage;
import entities.ShoppingCart;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author Avner
 * 
 *         controller class where the client can choose shipping option and the
 *         relevant discount apply to the total price of the order
 */
public class PaymentAndDeliveryController implements Initializable {

	/**
	 * Shipping option on comboBox
	 */
	public static ObservableList<String> delivery = FXCollections.observableArrayList(new String("PickUp"),
			new String("Shiping"));

	/**
	 * payment option on comboBox
	 */
	public static ObservableList<String> pay = FXCollections.observableArrayList();

	/**
	 * String to add payment option if there is
	 */
	public static String payoption = new String();
	/**
	 * total price after discount
	 */
	public static String totalAfterdiscunt = new String();

	/**
	 * region for the order
	 */
	public static String regionChoose;

	/**
	 * Client preferred delivery option
	 */
	private String deliveryChoose;
	/**
	 * client user name
	 */
	private String username;
	/**
	 * order number
	 */
	private String orderId = order2Controller.orderNumber;
	/**
	 * machine name for the delivery
	 */
	private String machineName;

	/**
	 * hbox that we want to hide when the client want pickup
	 */
	@FXML
	private HBox delivaryopt;
	/**
	 * back button
	 */
	@FXML
	private Button btnBack = null;
	/**
	 * pay button
	 */
	@FXML
	private Button payBtn = null;
	/**
	 * label that indicate the payment success
	 */
	@FXML
	private Label paymentSucsess;

	/**
	 * label that indicate that the payment fail
	 */
	@FXML
	private Label paymentFail;
	/**
	 * label with the total price
	 */
	@FXML
	private Label totalprice;
	/**
	 * label with the order number
	 */
	@FXML
	private Label orderNumber;

	/**
	 * logo image
	 */
	@FXML
	private ImageView img;

	/**
	 * combo box with the delivery option
	 */
	@FXML
	private ComboBox<String> deliveryCombo;

	/**
	 * combo box with the payment option
	 */
	@FXML
	private ComboBox<String> paymentCombo;
	/**
	 * combo box with the payment option
	 */
	@FXML
	private ComboBox<String> shipPaymentCombo;

	/**
	 * Field to enter address
	 */
	@FXML
	private TextField address;

	/**
	 * HBox with machine component
	 */
	@FXML
	private HBox Hmachine;

	/**
	 * HBox with address component
	 */
	@FXML
	private HBox Haddress;

	/**
	 * set the delivery Choose and start the show method
	 * 
	 * @param event click on show button
	 */
	@FXML
	void onShow(ActionEvent event) {
		deliveryChoose = deliveryCombo.getValue();
		show();
	}

	/**
	 * This method deletes the order if the customer leaves the window without going
	 * to the payment screen.
	 * 
	 */
	private void closeProgram() {
		ArrayList<String> priceAmount = new ArrayList<>();
		priceAmount.add(order2Controller.orderNumber);
		priceAmount.add(order2Controller.machine);
		Massage messageToServer = new Massage();
		messageToServer.setOperation("delete order after timeout");
		messageToServer.setData(priceAmount);
		client.ClientUI.chat.accept(messageToServer);
	}

	/**
	 * show the client the total price after discount and show him the prefer
	 * delivery option
	 */
	private void show() {
		pay.clear();
		pay.add(new String("Credit card"));

		Massage messageToServer = new Massage();

		if (deliveryChoose == null) {

			paymentFail.setVisible(true);

		} else {
			paymentFail.setVisible(false);

			if (deliveryChoose.equals("PickUp")) {
				Hmachine.setVisible(true);
				Haddress.setVisible(false);

				messageToServer.setOperation("get region");
				messageToServer.getData().add(machineName);
				client.ClientUI.chat.accept(messageToServer);

			}
			if (deliveryChoose.equals("Shiping")) {
				Hmachine.setVisible(false);
				Haddress.setVisible(true);
			}

			ArrayList<String> arr = new ArrayList<>();

			////////////////
			arr.add(username);
			////////////////
			arr.add(regionChoose);
			arr.add(OrderSummeryController.totalprice);
			messageToServer.setOperation("get discunt");
			messageToServer.setData(arr);
			client.ClientUI.chat.accept(messageToServer);

			System.out.println(totalAfterdiscunt);

			totalprice.setText("Total order price: " + totalAfterdiscunt + "$");

			arr.clear();
			////////////////
			arr.add(username);
			////////////////
			arr.add(regionChoose);
			arr.add(OrderSummeryController.totalprice);
			messageToServer.setOperation("get subscriber");
			messageToServer.setData(arr);
			client.ClientUI.chat.accept(messageToServer);

			if (payoption.equals("Deferred payment")) {
				pay.add("Deferred payment");
			}

		}

	}

	/**
	 * send to the server all the payment and delivery details
	 * 
	 * @param event click on pay button
	 */
	@FXML
	void onPay(ActionEvent event) {

		ArrayList<String> topay = new ArrayList<>();
		Massage messageToServer = new Massage();
		String payoption;
		if (deliveryChoose == null) {
			paymentFail.setVisible(true);
		} else if (deliveryChoose.equals("PickUp")) {
			topay.add("PickUp");

			String machineName1 = this.machineName;
			payoption = paymentCombo.getValue();

			if (machineName1 == null || payoption == null) {
				paymentFail.setVisible(true);
			} else {
				paymentSucsess.setVisible(true);
				topay.add(machineName1);
				topay.add(payoption);
			}
		} else if (deliveryChoose.equals("Shiping")) {
			topay.add("Shiping");
			String addressST = address.getText();
			payoption = shipPaymentCombo.getValue();

			if (addressST == null || payoption == null || addressST.equals("")) {
				paymentFail.setVisible(true);
			} else {
				paymentSucsess.setVisible(true);
				topay.add(addressST);
				topay.add(payoption);
			}
		} else {
			paymentFail.setVisible(true);
			return;
		}

		if (topay.size() == 3) {
			topay.add(orderId);
			topay.add(totalAfterdiscunt);
			topay.add(username);
			messageToServer.setOperation("pay");
			messageToServer.setData(topay);
			client.ClientUI.chat.accept(messageToServer);

			btnBack.setDisable(true);
			payBtn.setDisable(true);

			ShoppingCart shopingCart = ShoppingCart.getInstance();

			shopingCart.emptycart();
			order2Controller.flag = false;
			order2Controller.flag1 = true;

			((Node) event.getSource()).getScene().getWindow().hide();
			// CustomerServiceWelcomeController CSWC = new
			// CustomerServiceWelcomeController(verifieUser2.get(1));
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/order1.fxml"));

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

				Massage messageToServer1 = new Massage(); // disconnect.add("disconnect");
				messageToServer1.setOperation("disconnect");
				disconnect.add(ip_adrrs[1]);
				disconnect.add(ClientUI.chat.getHost());
				disconnect.add(LoginController.verifieUser.get(4));
				disconnect.add(LoginController.verifieUser.get(5));
				messageToServer1.setData(disconnect);

				stage.setOnCloseRequest(event1 -> {

					client.ClientUI.chat.accept(messageToServer1);
					client.ClientUI.chat.client.quit();

				});

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * close the order and move the client to the main window
	 * 
	 * @param event click on close order button
	 */
	@FXML
	void onMain(ActionEvent event) {

		closeProgram();

		((Node) event.getSource()).getScene().getWindow().hide();
		// CustomerServiceWelcomeController CSWC = new
		// CustomerServiceWelcomeController(verifieUser2.get(1));
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/order1.fxml"));

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

			Massage messageToServer = new Massage(); // disconnect.add("disconnect");
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
	 * move the client back to order summary window
	 * 
	 * @param event click on back button
	 */
	@FXML
	void pressBack(ActionEvent event) {
		OrderSummeryController.list.clear();
		((Node) event.getSource()).getScene().getWindow().hide();
		// CustomerServiceWelcomeController CSWC = new
		// CustomerServiceWelcomeController(verifieUser2.get(1));
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/Order Summery.fxml"));

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
			messageToServer.setOperation("disconnect");
			// disconnect.add("disconnect");
			disconnect.add(ip_adrrs[1]);
			disconnect.add(ClientUI.chat.getHost());
			disconnect.add(LoginController.verifieUser.get(4));
			disconnect.add(LoginController.verifieUser.get(5));
			messageToServer.setData(disconnect);

			stage.setOnCloseRequest(event1 -> {
				event1.consume();
				closeProgram();
				stage.close();

				client.ClientUI.chat.accept(messageToServer);
				client.ClientUI.chat.client.quit();

			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * initialize the gui
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);

		orderNumber.setText(orderId);
		Hmachine.setVisible(false);
		Haddress.setVisible(false);

		paymentFail.setVisible(false);
		paymentSucsess.setVisible(false);

		deliveryCombo.setItems(delivery);

		paymentCombo.setItems(pay);
		shipPaymentCombo.setItems(pay);

		username = LoginController.verifieUser.get(4);
		machineName = order2Controller.machine;

		Massage messageToServer = new Massage();
		ArrayList<String> arr = new ArrayList<>();
		arr.add(machineName);
		messageToServer.setOperation("get region");
		messageToServer.setData(arr);
		client.ClientUI.chat.accept(messageToServer);

		if (machineChooseController.status.equals("pysical")) {
			delivaryopt.setVisible(false);
			deliveryChoose = "PickUp";
			show();
		}
	}
}
