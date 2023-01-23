package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Massage;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Class that responsible for creating new sale discount in the marketing
 * manager gui
 * 
 * @author matan turjeman
 *
 */
public class MakeDiscountController implements Initializable {
	/**
	 * use to save to id to insert into the table sale
	 */
	public int currentID = 0;
	/**
	 * data to save info of the db
	 */
	public static ArrayList<String> data = new ArrayList<>();
	/**
	 * use to put in the combo box all the product to show
	 */
	public static ObservableList<String> options = FXCollections.observableArrayList();
	/**
	 * save all the product string
	 */
	public static ArrayList<String> product_for_init_purpose = new ArrayList<String>();
	/**
	 * boolean flag use to check if there is already pattern with the same product
	 */
	public static boolean product_status=false;
	
	/**
	 * text area of the percentage
	 */
	@FXML
	private TextField percentage;

	/**
	 * text area of the  description
	 */
	@FXML
	private TextArea description;

	/**
	 * combo box of represent the product available 
	 */
	@FXML
	private ComboBox<String> ProductCombo;
    /**
     * logo
     */
    @FXML
    private ImageView img;

	/**
	 * method that in given field percentage and field description AND COMBO BOX Product and set the sale
	 * according to the input orderid is generate automatically
	 * 
	 * @param event
	 */
	@FXML
	void Apply(ActionEvent event) {
		Massage messageToServer = new Massage();
		ArrayList<String> query = new ArrayList<>();
		if (ProductCombo.getValue() == null) {
			makeAlert("ERROR","Make New Discount","Cant Set New Sale Discount Must Choose Product First");
			return;
		}
		String Percentage = percentage.getText();
		String Description = description.getText();
		boolean status = validate_fields(Percentage, Description);
		percentage.clear();
		description.clear();
		if (status)
			return;
		messageToServer.setOperation("Markting");
		query.add("check product sales");
		query.add(ProductCombo.getValue());
		query.add(LoginController.verifieUser.get(6));
		messageToServer.setData(query);
		client.ClientUI.chat.accept(messageToServer);
		query.clear();
		if(product_status) {
			makeAlert("ERROR","Make New Discount","Cant Set New Sale When There Is Already Discount with the  specific Product and Region");
			return;
		}
			
		query.add("get current id");
		sql_build_query(query);
		System.out.println("id query output" + data);
		if (data.isEmpty())
			currentID = 1;
		else
			currentID = Integer.parseInt(data.get(0)) + 1;
		query.clear();
		query.add(String.valueOf(currentID));
		query.add(LoginController.verifieUser.get(6));
		query.add(Percentage);
		query.add(Description);
		query.add(ProductCombo.getValue());
		messageToServer.setOperation("Markting");
		messageToServer.setData(query);
		client.ClientUI.chat.accept(messageToServer);
		makeAlert("INFORMATION","Make New Discount","Great Added A New Sale\\n");
	}

	/**
	 * helper method that case sensitive for creating query in db
	 * 
	 * @param info
	 */
	void sql_build_query(ArrayList<String> info) {
		Massage messageToServer = new Massage();
		messageToServer.setOperation("Markting");
		String build_expression = "";
		String action = info.get(0);
		data.clear();
		switch (action) {
		case "get current id":
			build_expression += "SELECT ID FROM sale order by ID DESC LIMIT 1;";
			break;
		}

		data.clear();
		data.add(build_expression);
		System.out.println(data);
		messageToServer.setData(data);
		client.ClientUI.chat.accept(messageToServer);

	}

	/**
	 * helper method to validate the alert in given field
	 * 
	 * @param percentage
	 * @param Description
	 * @return
	 */
	boolean validate_fields(String percentage, String Description) {
		boolean status = false;
		try {
			int number = Integer.parseInt(percentage);
			if (!(number >= 1 && number <= 100))
				status = true;
			if (Description.length() > 45 || Description.length() == 0)
				status = true;

		} catch (NumberFormatException ex) {
			status = true;
		}
		if (status) {
			makeAlert("ERROR", "Make New Discount",
					"Error In One Of The Discount Field \\n\" + \"Please Enter Percentage Between 1 To 100\\n\"\r\n"
							+ "					+ \"Please Enter Description Not Longer Than 45 Letters");
		}

		return status;
	}

	/**
	 * private method to make alert for given situation with given input
	 * @param action
	 * @param s1
	 * @param s2
	 */
	void makeAlert(String action, String s1, String s2) {
		Alert alert = null;
		if (action.equals("ERROR"))
			alert = new Alert(Alert.AlertType.ERROR);
		else
			alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(s1);
		alert.setContentText(s2);
		alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		alert.showAndWait();

	}

	/**
	 * fxml method that when click on button back moving to MarktingManger gui
	 * window.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML

	void Back(ActionEvent event) {

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);
		ArrayList<String> data_1 = new ArrayList<String>();
		data_1.add("initialize_combo_box");
		Massage messageToServer = new Massage();
		messageToServer.setOperation("Markting");
		messageToServer.setData(data_1);
		client.ClientUI.chat.accept(messageToServer);
		options.clear();
		options.addAll(product_for_init_purpose);
		ProductCombo.setItems(options);

	}

}
