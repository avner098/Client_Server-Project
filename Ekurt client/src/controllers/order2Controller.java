package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import client.ClientUI;
import common.Massage;
import entities.Product;
import entities.ShoppingCart;
import javafx.application.Platform;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class is responsible for all operations of ordering a customer, deleting
 * a product from the order, adding a product, proceeding to payment and more.
 *
 * 
 */
public class order2Controller implements Initializable {
	/**
	 * the server send the data to here
	 */
	public static ArrayList<String> inventory2 = new ArrayList<>(); 
	/**
	 * the server send the data to here
	 */
	public static ArrayList<String> inventory1 = new ArrayList<>();
	/**
	 * hold the order number
	 */
	static public String orderNumber; 
	/**
	 * hold the machine name
	 */
	static public String machine; 
	static public byte[] getIm; 
	/**
	 * the logo image
	 */
	public static Image img; 
	/**
	 * the singleton class for the shopping cart
	 */
	ShoppingCart newOrder = null;

	/**
	 * imageView for the logo image
	 */
	@FXML
	private ImageView logo; 

	/**
	 * textField for the product's name.
	 */
	@FXML
	private TextField bamba_name;
	/**
	 * textField for the serial number
	 */
	@FXML
	private TextField bamba_number; 

	/**
	 * imageView for the product's picture
	 */
	@FXML
	private ImageView bamba_pic; 

	/**
	 * 
	 */
	@FXML
	private TextField bamba_price; //textField for the product's price.

	/**
	 * 
	 */
	@FXML
	private ImageView cola_pic; //imageView for the product's picture

	/**
	 * textField for the product's name.
	 */
	@FXML
	private TextField doritos_name; 
	/**
	 * textField for the serial number
	 */
	@FXML
	private TextField doritos_number; 

	/**
	 * imageView for the product's picture
	 */
	@FXML
	private ImageView doritos_pic; 

	/**
	 * textField for the product's price
	 */
	@FXML
	private TextField doritos_price; 

	/**
	 * textField for the product's name.
	 */
	@FXML
	private TextField fanta_name; 

	/**
	 * textField for the serial number
	 */
	@FXML
	private TextField fanta_number; 

	/**
	 * imageView for the product's picture
	 */
	@FXML
	private ImageView fanta_pic; 

	/**
	 * textField for the product's price
	 */
	@FXML
	private TextField fanta_price; 

	/**
	 * textField for the product's name.
	 */
	@FXML
	private TextField nature_name; 

	/**
	 * textField for the serial number
	 */
	@FXML
	private TextField nature_number; 

	/**
	 * imageView for the product's picture
	 */
	@FXML
	private ImageView nature_pic; 

	/**
	 * textField for the product's price
	 */
	@FXML
 	private TextField nature_price; 
 
	/**
	 * textField for the product's name.
	 */
	@FXML
	private TextField orange_name;

	/**
	 * textField for the serial number
	 */
	@FXML
	private TextField orange_number; 

	/**
	 * imageView for the product's picture
	 */
	@FXML
	private ImageView orange_pic; 

	/**
	 * textField for the product's price
	 */
	@FXML
	private TextField orange_price; 

	/**
	 * textField for the product's name.
	 */
	@FXML
	private TextField sprite_name; 
	/**
	 * textField for the serial number
	 */
	@FXML
	private TextField sprite_number; 

	/**
	 * imageView for the product's picture
	 */
	@FXML
	private ImageView sprite_pic; 

	/**
	 * textField for the product's price
	 */
	@FXML
	private TextField sprite_price; 

	/**
	 * textField for the product's name.
	 */
	@FXML
	private TextField waffle_name; 

	/**
	 * textField for the serial number
	 */
	@FXML
	private TextField waffle_number; 
	/**
	 * imageView for the product's picture
	 */
	@FXML
	private ImageView waffle_pic; 

	/**
	 * textField for the product's price
	 */
	@FXML
	private TextField waffle_price;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	/**
	 * Holds the quantity of the product the user selected
	 */
	@FXML
	private TextField cola_amount; 

	/**
	 * scroller for the product table
	 */
	@FXML
	private ScrollPane scroller;  
	/**
	 * The user click on this button when its finish the order.
	 */
	@FXML
	private Button done_order = null; 

	/**
	 * Holds the quantity of the product the user selected
	 */
	@FXML
	private TextField fante_amount; 

	/**
	 * Holds the quantity of the product the user selected
	 */
	@FXML
	private TextField bamba_amount;

	/**
	 * Holds the quantity of the product the user selected
	 */
	@FXML
	private TextField doritos_amount; 

	/**
	 * Holds the quantity of the product the user selected
	 */
	@FXML
	private TextField neture_amount;

	/**
	 * Holds the quantity of the product the user selected
	 */
	@FXML
	private TextField waffle_amount;


	@FXML
	private VBox vbox1;
	/**
	 * choice box that the customer can choose the machine from.
	 */
	@FXML
	private ChoiceBox<String> choose_machine; 
	
	/**
	 *set the product table that the customer chose:	 
	 */
	@FXML
	private TableView<Product> order_list;

	@FXML
	private TableColumn<Product, String> priceC;

	@FXML
	private TableColumn<Product, String> productC;

	@FXML
	private TableColumn<Product, String> amountC;
	/**
	 * end of the table product.
	 */
	
	/**
	 * Holds the quantity of the product the user selected
	 */
	@FXML
	private TextField sprit_amount;
	/**
	 * Holds the quantity of the product the user selected
	 */
	@FXML
	private TextField orange_amount;
	/**
	 * label for the total price.
	 */
	@FXML
	private Label total_price; 
	/**
	 * update that the customer chose this product
	 */
	@FXML
	private Button cola1 = null; 
	/**
	 * update that the customer chose this product
	 */
	@FXML
	private Button fanta1 = null; 
	/**
	 * update that the customer chose this product
	 */

	@FXML
	private Button sprite1 = null; 
	/**
	 * update that the customer chose this product
	 */
	@FXML
	private Button bamba1 = null; 	
	/**
	 * update that the customer chose this product
	 */
	@FXML
	private Button doritos1 = null; 
	/**
	 * update that the customer chose this product
	 */
	@FXML
	private Button nature1 = null; 
	/**
	 *update that the customer chose this product
	 */
	@FXML 
	private Button orange1 = null;
	/**
	 *update that the customer chose this product 
	 */
	@FXML
	private Button waffle1 = null;

	/**
	 * confirm the chosen machine
	 */
	@FXML
	private Button confirm1 = null;   

	/**
	 * on click the marked product will be deleted.
	 */
	@FXML
	private Button delete_product = null; 
	/**
	 * textField for the product name
	 */
	@FXML
	private TextField cola_name; 

	/**
	 * textField for the product serial number
	 */
	@FXML
	private TextField cola_number ; 

	/**
	 * textField that hold the product's price.
	 */
	@FXML
	private TextField cola_price;

	/**
	 * Responsible for the table of the products chosen by the customer
	 */
	ObservableList<Product> products = FXCollections.observableArrayList(); 
	int total = 0;
	public static boolean flag = true; 
	public static boolean flag1 = true;
	public static Stage stage = null;
	Timer timer = new Timer();
	TimerTask exitApp = new TimerTask() {

		/**
		 * This method is a thread run that deletes the order after a predefined time in
		 * the constructor.
		 */
		public void run() {
			System.out.println(flag);
			System.out.println(flag1);
			if (flag) {
				delete_after_timeout();//deleting the order in case that the customer abandoned the order.
				Platform.runLater(() -> {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);//display an alert that says that the order been deleted the order have been abandoned.
					alert.setTitle("Alert");
					alert.setHeaderText(null);
					alert.setContentText(
							"Please note, you have not performed an action for a few minutes, so the order has been deleted.");
					alert.showAndWait();
					Stage stage1;
					if (stage == null) {
						stage1 = order1Controller.stage1;
					} else {
						stage1 = stage;
					}
					stage1.hide();
					ActionEvent event = order1Controller.event1;
					newOrder.emptycart();
					try {
						back(event);
						order2Controller.flag1 = true;
					} catch (IOException e) {

					}

				});
			}

		}
	};


	
	
	/**
	 * A method that sends a request to the server to delete the order in the
	 * database because the time allowed to make an order has passed.
	 * 
	 */
	public void delete_after_timeout() {
		ArrayList<String> priceAmount = new ArrayList<>();
		priceAmount.add(orderNumber);
		priceAmount.add(machine);
		Massage messageToServer = new Massage();
		messageToServer.setOperation("delete order after timeout");
		messageToServer.setData(priceAmount);
		client.ClientUI.chat.accept(messageToServer);
	}

	/**
	 * start the method choose_machine()
	 * 
	 * @param event click on confirm
	 */
	@FXML
	void machineChose(ActionEvent event) {
		choose_machine();
	}

	/**
	 * This method is responsible for selecting a machine Until you choose a
	 * machine, the system does not allow you to choose products, and after you
	 * choose a machine, the method will allow you to choose a product if the
	 * product quantity is greater than 0 To access the information, the method asks
	 * the server for the products' data.
	 */
	public void choose_machine() {
		int i = 0;
		machine = choose_machine.getValue().toString();
		ArrayList<String> priceAmount = new ArrayList<>();
		priceAmount.add(machine);
		newOrder.setMachine(machine);
		Massage messageToServer = new Massage();
		messageToServer.setOperation("machine invatory");// Requests product quantity for each product from the server
		messageToServer.setData(priceAmount);
		client.ClientUI.chat.accept(messageToServer);
		for (String x : inventory1) {
			if (!(x.equals("0"))) {

				if (i == 0) {
					cola1.setDisable(false);
				}
				if (i == 1) {
					sprite1.setDisable(false);
				}
				if (i == 2) {
					fanta1.setDisable(false);
				}
				if (i == 3) {
					orange1.setDisable(false);
				}
				if (i == 4) {
					nature1.setDisable(false);
				}
				if (i == 5) {
					waffle1.setDisable(false);
				}
				if (i == 6) {
					doritos1.setDisable(false);
				}
				if (i == 7) {
					bamba1.setDisable(false);
				}
				i++;
			} else {
				if (i == 0) {
					cola1.setDisable(true);
				}
				if (i == 1) {
					sprite1.setDisable(true);
				}
				if (i == 2) {
					fanta1.setDisable(true);
				}
				if (i == 3) {
					orange1.setDisable(true);
				}
				if (i == 4) {
					nature1.setDisable(true);
				}
				if (i == 5) {
					waffle1.setDisable(true);
				}
				if (i == 6) {
					waffle1.setDisable(true);
				}
				if (i == 7) {
					bamba1.setDisable(true);
				}
				i++;
			}
		}
	}

	/**
	 * The method allows adding a product to the cart. Each product that is added,
	 * the method sends a request to the server to update both the order according
	 * to the product and the quantity from it and also to download the quantity
	 * from that product in the machine.
	 * 
	 * @param name
	 * @param amount
	 * @param pric
	 */
	public void add_product(String name, int amount, int pric) {

		confirm1.setDisable(true);
		ArrayList<String> priceAmount = new ArrayList<>();
		Massage messageToServer = new Massage();

		if (amount <= 0) {
			JOptionPane.showMessageDialog(null, "invalid product amount! must be greater than 0.", "invalid",
					JOptionPane.WARNING_MESSAGE);
		}

		messageToServer.setOperation("update price_amount"); // Requests to update the quantity of the product
		int price = pric * amount;//Calculates the final price for the product with the quantity selected by the customer
		String price1 = price + "";
		String amount1 = amount + "";
		int product = 0;
		priceAmount.add(price1);
		priceAmount.add(amount1);
		priceAmount.add(name);
		priceAmount.add(LoginController.verifieUser.get(4));
		messageToServer.setData(priceAmount);
		client.ClientUI.chat.accept(messageToServer);
		if (inventory2.get(0).equals("tooMuch")) {
			JOptionPane.showMessageDialog(null, "too many", amount1, JOptionPane.WARNING_MESSAGE);
		} // Pops up an alert if the customer requested a larger quantity than the
			// existing quantity of the product in the machine
		else {
			if (name.equals("cola")) {// Updates each product in the shopping cart and updates the customerOrder class
										// that a new product has been received
				Product p = new Product("cola", amount, price);
				products.add(p);
				order_list.setItems(products);
				total_price.setText(inventory2.get(0));
				newOrder.setCola(amount);
				newOrder.setPrice(price);
			} else if (name.equals("fanta")) {
				Product p = new Product("fanta", amount, price);
				products.add(p);
				order_list.setItems(products);
				newOrder.setFanta(amount);
				newOrder.setPrice(price);
				total_price.setText(inventory2.get(0));
			} else if (name.equals("sprite")) {
				Product p = new Product("sprite", amount, price);
				products.add(p);
				order_list.setItems(products);
				total_price.setText(inventory2.get(0));
				newOrder.setSprite(amount);
				newOrder.setPrice(price);
			} else if (name.equals("OrangeJuice")) {
				Product p = new Product("OrangeJuice", amount, price);
				products.add(p);
				order_list.setItems(products);
				total_price.setText(inventory2.get(0));
				newOrder.setOrange(amount);
				newOrder.setPrice(price);
			} else if (name.equals("bamba")) {
				Product p = new Product("bamba", amount, price);
				products.add(p);
				order_list.setItems(products);
				total_price.setText(inventory2.get(0));
				newOrder.setBamba(amount);
				newOrder.setPrice(price);
			} else if (name.equals("doritos")) {
				Product p = new Product("doritos", amount, price);
				products.add(p);
				order_list.setItems(products);
				total_price.setText(inventory2.get(0));
				newOrder.setDoritos(amount);
				newOrder.setPrice(price);
			} else if (name.equals("waffles")) {
				Product p = new Product("waffles", amount, price);
				products.add(p);
				order_list.setItems(products);
				total_price.setText(inventory2.get(0));
				newOrder.setWaffle(amount);
				newOrder.setPrice(price);
			} else if (name.equals("natureValley")) {
				Product p = new Product("natureValley", amount, price);
				products.add(p);
				order_list.setItems(products);
				total_price.setText(inventory2.get(0));
				newOrder.setNature(amount);
				newOrder.setPrice(price);
			}
		}
	}

	/**
	 * add the product to the shopping cart
	 * 
	 * @param event click in add product
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	void add_cola(ActionEvent event) {
		int amount = Integer.parseInt(cola_amount.getText());
		add_product("cola", amount, 9);
	}

	/**
	 * add the product to the shopping cart
	 * 
	 * @param event click in add product
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	void add_fanta(ActionEvent event) {
		int amount = Integer.parseInt(fante_amount.getText());
		add_product("fanta", amount, 8);
	}

	/**
	 * add the product to the shopping cart
	 * 
	 * @param event click in add product
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	void add_sprite(ActionEvent event)  {
		int amount = Integer.parseInt(sprit_amount.getText());
		add_product("sprite", amount, 10);
	}

	/**
	 * add the product to the shopping cart
	 * 
	 * @param event click in add product
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	void add_orange(ActionEvent event) {
		int amount = Integer.parseInt(orange_amount.getText());
		add_product("OrangeJuice", amount, 8);
	}

	/**
	 * The method handles deleting a product from the order. Each product deletion
	 * will remove the product from the shopping cart, lower the order price and the
	 * product will be removed from the "customerOrder" product class.
	 * 
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	void delete_product(ActionEvent event){
		order_list.setEditable(true);
		int selectedIndex = order_list.getSelectionModel().getSelectedIndex();
		int amount = order_list.getItems().get(selectedIndex).getAmount() * (-1);
		int price = order_list.getItems().get(selectedIndex).getPrice() * (-1);
		String name = order_list.getItems().get(selectedIndex).getName();

		if (selectedIndex >= 0) {
			order_list.getItems().remove(selectedIndex);
		}
		ArrayList<String> priceAmount = new ArrayList<>();
		Massage messageToServer = new Massage();
		messageToServer.setOperation("update price_amount");// Requests the server to update the database that a product
															// has been deleted from the cart and to update the
															// inventory
		String price1 = price + "";
		String amount1 = amount + "";
		priceAmount.add(price1);
		priceAmount.add(amount1);
		priceAmount.add(name);
		priceAmount.add(LoginController.verifieUser.get(4));
		messageToServer.setData(priceAmount);
		client.ClientUI.chat.accept(messageToServer);
		total_price.setText(inventory2.get(0));
		newOrder.setPrice(price);
		// Updates each product in the shopping cart and updates the customerOrder class
		// that a certain product has been deleted
		if (name.equals("cola")) {
			newOrder.setCola(amount);
		}
		if (name.equals("sprite")) {
			newOrder.setSprite(amount);
		}
		if (name.equals("fanta")) {
			newOrder.setFanta(amount);
		}
		if (name.equals("OrangeJuice")) {
			newOrder.setOrange(amount);
		}
		if (name.equals("bamba")) {
			newOrder.setBamba(amount);
		}
		if (name.equals("doritos")) {
			newOrder.setDoritos(amount);
		}
		if (name.equals("waffles")) {
			newOrder.setWaffle(amount);
		}
		if (name.equals("natureValley")) {
			newOrder.setNature(amount);
		}

	}

	/**
	 * The method takes the user to the payment and shipping option selection screen
	 * after the product selection is complete.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void done_order(ActionEvent event) throws IOException {

		Massage messageToServer = new Massage();

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
	 * add the product to the shopping cart
	 * 
	 * @param event click in add product
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	void add_bamba(ActionEvent event) {
		int amount = Integer.parseInt(bamba_amount.getText());
		add_product("bamba", amount, 10);
	}

	/**
	 * add the product to the shopping cart
	 * 
	 * @param event click in add product
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	void add_doritos(ActionEvent event) {
		int amount = Integer.parseInt(doritos_amount.getText());
		add_product("doritos", amount, 12);

	}

	/**
	 * add the product to the shopping cart
	 * 
	 * @param event click in add product
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	void add_waffle(ActionEvent event) {
		int amount = Integer.parseInt(waffle_amount.getText());
		add_product("waffles", amount, 12);
	}

	/**
	 * add the product to the shopping cart
	 * 
	 * @param event click in add product
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	void add_neture(ActionEvent event) {
		int amount = Integer.parseInt(neture_amount.getText());
		add_product("natureValley", amount, 15);
	}

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
	 * The method returns the client to the previous page.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void back(ActionEvent event) throws IOException {

		if (newOrder.getMachineStatus() == null) {
			if (newOrder.getMachine() == null) {
				newOrder.setExist(false);
			}
		}

		Massage messageToServer = new Massage();

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
	 * The method updates its information for each item: name, price and serial
	 * number.
	 */
	public void update_products_info() {
		System.out.println(inventory2);

		cola_name.setText(inventory2.get(0));
		cola_number.setText("Serial number: " + inventory2.get(1));
		cola_price.setText("Price: " + inventory2.get(2) + " $");

		sprite_name.setText(inventory2.get(3));
		sprite_number.setText("Serial number: " + inventory2.get(4));
		sprite_price.setText("Price: " + inventory2.get(5) + " $");

		fanta_name.setText(inventory2.get(6));
		fanta_number.setText("Serial number: " + inventory2.get(7));
		fanta_price.setText("Price: " + inventory2.get(8) + " $");

		orange_name.setText(inventory2.get(9));
		orange_number.setText("Serial number: " + inventory2.get(10));
		orange_price.setText("Price: " + inventory2.get(11) + " $");

		nature_name.setText(inventory2.get(12));
		nature_number.setText("Serial number: " + inventory2.get(13));
		nature_price.setText("Price: " + inventory2.get(14) + " $");

		waffle_name.setText(inventory2.get(15));
		waffle_number.setText("Serial number: " + inventory2.get(16));
		waffle_price.setText("Price: " + inventory2.get(17) + " $");

		doritos_name.setText(inventory2.get(18));
		doritos_number.setText("Serial number: " + inventory2.get(19));
		doritos_price.setText("Price: " + inventory2.get(20) + " $");

		bamba_name.setText(inventory2.get(21));
		bamba_number.setText("Serial number: " + inventory2.get(22));
		bamba_price.setText("Price: " + inventory2.get(23) + " $");

	}

	/**
	 * The method enters the information about the products that the customer
	 * ordered into a new order class, this class is implemented in the singleton
	 * model.
	 * 
	 */
	public void create_new_order_singalton() {
		newOrder.setOrderNumber(orderNumber);
		if (newOrder.getCola() > 0) {
			Product p = new Product("cola", newOrder.getCola(), newOrder.getCola() * 9);
			products.add(p);
			order_list.setItems(products);
		}
		if (newOrder.getSprite() > 0) {
			Product p = new Product("sprite", newOrder.getSprite(), newOrder.getSprite() * 10);
			products.add(p);
			order_list.setItems(products);
		}
		if (newOrder.getFanta() > 0) {
			Product p = new Product("fanta", newOrder.getFanta(), newOrder.getFanta() * 8);
			products.add(p);
			order_list.setItems(products);
		}
		if (newOrder.getOrange() > 0) {
			Product p = new Product("OrangeJuice", newOrder.getOrange(), newOrder.getOrange() * 8);
			products.add(p);
			order_list.setItems(products);
		}
		if (newOrder.getBamba() > 0) {
			Product p = new Product("bamba", newOrder.getBamba(), newOrder.getBamba() * 10);
			products.add(p);
			order_list.setItems(products);
		}
		if (newOrder.getDoritos() > 0) {
			Product p = new Product("doritos", newOrder.getDoritos(), newOrder.getDoritos() * 12);
			products.add(p);
			order_list.setItems(products);
		}
		if (newOrder.getNature() > 0) {
			Product p = new Product("natureValley", newOrder.getNature(), newOrder.getNature() * 15);
			products.add(p);
			order_list.setItems(products);
		}
		if (newOrder.getWaffle() > 0) {
			Product p = new Product("waffles", newOrder.getWaffle(), newOrder.getWaffle() * 12);
			products.add(p);
			order_list.setItems(products);
		}
	}

	/**
	 * The method initializes the order: Does not allow you to order a product until
	 * you choose a machine. Asking the server to create a new row in the database
	 * of a new order. Asks the server for the information for each product and
	 * updates the information about the products before the customer enters the
	 * order window. Updates the date and time in the order. Updates the available
	 * machines.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(flag1) {
			timer.schedule(exitApp, new Date(System.currentTimeMillis() + 60 * 1000));
			flag1=false;
		}
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		logo.setImage(image);

		ArrayList<String> product = new ArrayList<>();
		
		//initializes the image of each product:
		product.add("natureValley");
		Massage getImage = new Massage();
		getImage.setData(product);
		getImage.setOperation("get image");
		client.ClientUI.chat.accept(getImage);
		nature_pic.setImage(img);

		product.clear();
		product.add("cola");
		getImage.setData(product);
		getImage.setOperation("get image");
		client.ClientUI.chat.accept(getImage);
		cola_pic.setImage(img);

		product.clear();
		product.add("sprite");
		getImage.setData(product);
		getImage.setOperation("get image");
		client.ClientUI.chat.accept(getImage);
		sprite_pic.setImage(img);

		product.clear();
		product.add("fanta");
		getImage.setData(product);
		getImage.setOperation("get image");
		client.ClientUI.chat.accept(getImage);
		fanta_pic.setImage(img);

		product.clear();
		product.add("OrangeJuice");
		getImage.setData(product);
		getImage.setOperation("get image");
		client.ClientUI.chat.accept(getImage);
		orange_pic.setImage(img);

		product.clear();
		product.add("waffles");
		getImage.setData(product);
		getImage.setOperation("get image");
		client.ClientUI.chat.accept(getImage);
		waffle_pic.setImage(img);

		product.clear();
		product.add("doritos");
		getImage.setData(product);
		getImage.setOperation("get image");
		client.ClientUI.chat.accept(getImage);
		doritos_pic.setImage(img);

		product.clear();
		product.add("bamba");
		getImage.setData(product);
		getImage.setOperation("get image");
		client.ClientUI.chat.accept(getImage);
		bamba_pic.setImage(img);
	
		// Does not allow the customer to select a product
		cola1.setDisable(true);
		sprite1.setDisable(true);
		fanta1.setDisable(true);
		orange1.setDisable(true);
		nature1.setDisable(true);
		doritos1.setDisable(true);
		bamba1.setDisable(true);
		waffle1.setDisable(true);
		//Initializes the table of products that the customer selected
		ArrayList<String> priceAmount = new ArrayList<>();
		productC.setCellValueFactory(new PropertyValueFactory<>("name"));
		amountC.setCellValueFactory(new PropertyValueFactory<>("amount"));
		priceC.setCellValueFactory(new PropertyValueFactory<>("price"));

		Massage messageToServer6 = new Massage();
		messageToServer6.setData(priceAmount);
		messageToServer6.setOperation("machines");// Asks the server to insert a new order line
		client.ClientUI.chat.accept(messageToServer6);

		System.out.println(inventory1.get(0));
		String[] st = { inventory1.get(0), inventory1.get(1), inventory1.get(2), inventory1.get(3), inventory1.get(4),
				inventory1.get(5), }; // Updates the list of machines
		choose_machine.getItems().addAll(st);

		priceAmount.add(LoginController.verifieUser.get(7));
		priceAmount.add(LoginController.verifieUser.get(4));

		newOrder = ShoppingCart.getInstance();

		if (!newOrder.isExist()) {// Checks whether there is an object of the customerOrder class that is a
									// singleton
			order2Controller.flag = true;
			newOrder.setExist(true);
			confirm1.setDisable(false);
			Massage messageToServer = new Massage();
			messageToServer.setData(priceAmount);
			messageToServer.setOperation("new order");// Asks the server to insert a new order line
			client.ClientUI.chat.accept(messageToServer);

			Massage messageToServer1 = new Massage();
			messageToServer1.setData(priceAmount);
			messageToServer1.setOperation("get order number");
			client.ClientUI.chat.accept(messageToServer1);
			orderNumber = inventory1.get(0);
		} else {
			
			choose_machine.setValue(machine);
			choose_machine();
			confirm1.setDisable(true);
			ArrayList<String> number = new ArrayList<>();
			number.add(newOrder.getOrderNumber());
			Massage messageToServer3 = new Massage();
			messageToServer3.setData(number);
			messageToServer3.setOperation("old number");// If there is an object of the class customerrOrder it means
														// that there is an open order and therefore we will continue
														// with the open order
		}
		Massage messageToServer2 = new Massage();
		messageToServer2.setData(priceAmount);
		messageToServer2.setOperation("product info");
		client.ClientUI.chat.accept(messageToServer2);
		update_products_info();
		if (machineChooseController.machine2 != null) {
			machine = machineChooseController.machine2;
			choose_machine.setValue(machine);
			choose_machine();
			confirm1.setDisable(true);
		}
		
		total_price.setText(newOrder.getPrice() + "");
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //get the current time.
		String strDate = dateFormat.format(date);
		ArrayList<String> arr = new ArrayList<>();
		arr.add(strDate);
		Massage messageToServer5 = new Massage();
		messageToServer5.setData(arr);
		messageToServer5.setOperation("set time");// Requests the server to update the database with the order execution
													// time
		client.ClientUI.chat.accept(messageToServer5);
		create_new_order_singalton();

		if (machineChooseController.status.equals("pysical")) {
			newOrder.setMachineStatus("pysical");
			choose_machine.setVisible(false);
			confirm1.setVisible(false);
		}
		//set all the product information labels disable so the customer cannot change anything.
		cola_name.setDisable(true);
		cola_number.setDisable(true);
		cola_price.setDisable(true);
		waffle_price.setDisable(true);
		waffle_number.setDisable(true);
		waffle_name.setDisable(true);
		sprite_price.setDisable(true);
		sprite_number.setDisable(true);
		sprite_name.setDisable(true);
		orange_price.setDisable(true);
		orange_number.setDisable(true);
		orange_name.setDisable(true);
		nature_price.setDisable(true);
		nature_number.setDisable(true);
		nature_name.setDisable(true);
		fanta_price.setDisable(true);
		fanta_number.setDisable(true);
		fanta_name.setDisable(true);
		doritos_price.setDisable(true);
		doritos_pic.setDisable(true);
		doritos_number.setDisable(true);
		doritos_name.setDisable(true);
		bamba_price.setDisable(true);
		bamba_number.setDisable(true);
		bamba_name.setDisable(true);

	}

}
