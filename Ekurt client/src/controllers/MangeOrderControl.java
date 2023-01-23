package controllers;
	import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.ClientUI;
import common.Massage;
import entities.Product;
import entities.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

	/**
	 * This class is used for order management, the customer enters the order management screen and
	 *  their historical orders are displayed.
	 */
	public class MangeOrderControl {
		LoginController loginController; 
		public static ArrayList<String> inventory1 = new ArrayList<>();//The server sending the data to this arrayList

		
	    /**
	     *the columns of the mange order table: 
	     */
	    @FXML
	    private TableColumn<Order, String> Payment_method; //

	    @FXML
	    private TableColumn<Order, String> address;
	    @FXML
	    private TableColumn<Order, String> time;
	    @FXML
	    private TableColumn<Order, String> machine;
	    
	    @FXML
	    private TableColumn<Order, ?> delivery;


	    @FXML
	    private TableColumn<Order, ?> price;

	    @FXML
	    private TableColumn<Order, ?> products;

	    @FXML
	    private TableColumn<Order, ?> status;

	    @FXML
	    private TableColumn<Order, ?> dlivery;

	    @FXML
	    private TableColumn<Order, String> amount;
	
	    @FXML
	    private TableColumn<Order, String> order_num;
	
	    @FXML
	    private TableView<Order> order_table;
	    /**
	     *End of the table columns 
	     */
	    
	    /**
	     * the logo image
	     */
	    @FXML
	    private ImageView img;

	  
	    ObservableList<entities.Order> orders = FXCollections.observableArrayList(); //response to build the table.

	    
	    /**
	     * A button that on click update the carrier that the customer got its order.
	     * @param event
	     */
	    @FXML
	    void gotOrder(ActionEvent event) {
    	    ArrayList<String> order = new ArrayList<>();
    	    Order person = order_table.getSelectionModel().getSelectedItem();
    	    String orderNum = person.getOrder_number();
    	    order.add(orderNum);
    	    //If the customer try to set order that already arrived or didnt sent yet, alert is display.
    	    if(!person.getOrder_status().contains("Order")) {JOptionPane.showMessageDialog(null,"The order has not been sent yet.","status", JOptionPane.ERROR_MESSAGE);}
    	    else {JOptionPane.showMessageDialog(null,"thank you,message sent to the company.","status", JOptionPane.INFORMATION_MESSAGE);}
    	    //ask the server to update the order status to arrived.
    	    Massage messageToServer = new Massage();
	    	messageToServer.setOperation("arrived");
	    	messageToServer.setData(order);
	        client.ClientUI.chat.accept(messageToServer);
	}

	    /**
		 * return the client to the previous window
		 * 
		 * @param event click on back button
		 * @throws IOException
		 */
	    @FXML
	    void back(ActionEvent event) throws IOException {

		  Massage messageToServer = new Massage();

			((Node) event.getSource()).getScene().getWindow().hide();
			//CustomerServiceWelcomeController CSWC = new CustomerServiceWelcomeController(verifieUser2.get(1));
			try {
			//Moves the customer to a window of choosing whether to create a new order or access the order management window
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/order1.fxml")); 			
			//CSWC.name= verifieUser2.get(1);
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
			
			//Ask the server to disconnect the user.
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
			
			
			}catch (IOException e) {
				e.printStackTrace();
			}
			
	    }
	    
	   

	    /**
	     * initialize the class mangeOrder
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    @FXML
	    void initialize() throws ClassNotFoundException {
	    	
	    	Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));//Set the logo image.
	    	img.setImage(image);
	    	
    	    ArrayList<String> order = new ArrayList<>();
    	    //initialize the mange order table:
	    	order_num.setCellValueFactory(new PropertyValueFactory<>("order_number"));
	    	delivery.setCellValueFactory(new PropertyValueFactory<>("dlivery"));
	    	address.setCellValueFactory(new PropertyValueFactory<>("addres"));
	    	Payment_method.setCellValueFactory(new PropertyValueFactory<>("payment"));
	    	products.setCellValueFactory(new PropertyValueFactory<>("product"));
	    	amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
	    	price.setCellValueFactory(new PropertyValueFactory<>("price"));
	    	status.setCellValueFactory(new PropertyValueFactory<>("order_status"));	 	 
	    	machine.setCellValueFactory(new PropertyValueFactory<>("machine"));	 
	    	time.setCellValueFactory(new PropertyValueFactory<>("time"));
	    	//end of initialize the table.
	    	
	        loginController = new LoginController();
	        LoginController.verifieUser.get(4);//user name.
	        Massage messageToServer = new Massage();
    		messageToServer.setOperation("manage order");
    		order.add(LoginController.verifieUser.get(4));
    		messageToServer.setData(order);
            client.ClientUI.chat.accept(messageToServer);
            String prod = "";
            String amount1 = "";
            for(int i=0;i<(inventory1.size());i=i+16) {
            	prod = "";amount1 = "";
            for(int k=5+i;k<13+i;k++) { //The places that the product are in the table that in the data base
            if(Integer.parseInt(inventory1.get(k))>0)
            	{//Checking the quantity of each product and placing it in the table.
            	if(k==5+i) {prod+="cola, ";amount1+=inventory1.get(k)+",";}
            	if(k==6+i) {prod+="sprite, ";amount1+=inventory1.get(k)+",";}
            	if(k==7+i) {prod+="fanta, ";amount1+=inventory1.get(k)+",";}
            	if(k==8+i) {prod+="orange juice, " ;amount1+=inventory1.get(k)+",";}
            	if(k==9+i) {prod+="nature valley, ";amount1+=inventory1.get(k)+",";}
            	if(k==10+i) {prod+="waffles, ";amount1+=inventory1.get(k)+",";}
            	if(k==11+i) {prod+="doritos, ";amount1+=inventory1.get(k)+",";}
            	if(k==12+i) {prod+="bamba";amount1+=inventory1.get(k);}
            	}}
        
                Order p = new Order(inventory1.get(i),inventory1.get(i+1),inventory1.get(i+2),inventory1.get(i+4),prod,amount1,inventory1.get(i+4),inventory1.get(i+13),inventory1.get(i+14),inventory1.get(i+15));
    		    orders.add(p);       
    		    order_table.setItems(orders);}   //After creating an object of the class, the table is initialized.  
            }
	}
	

	  

	


