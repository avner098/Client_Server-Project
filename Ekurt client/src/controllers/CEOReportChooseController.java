package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *  This class is used for CEO to Choose the reports they want to view.
 *
 */
public class CEOReportChooseController implements Initializable {
	/**
	 * Year options to show in YearComboBox
	 */
	public static ObservableList<String> Yearoptions = FXCollections.observableArrayList("2018", "2019", "2020", "2021",
			"2022", "2023");
	/**
	 * Month options to show in MonthComboBox
	 */
	public static ObservableList<String> Monthoptions = FXCollections.observableArrayList("01", "02", "03", "04", "05",
			"06", "07", "08", "09", "10", "11", "12");
	/**
	 * Type options to show in ReportTypeComboBox
	 */
	public static ObservableList<String> Typeoptions = FXCollections.observableArrayList("Orders", "Customers",
			"Inventory");
	/**
	 * Region options to show in RegionComboBox
	 */
	public static ObservableList<String> Regionoptions = FXCollections.observableArrayList("north", "South", "UAE");
	/**
	 * Machine Names to show in MachineComboBox
	 */
	public static ObservableList<String> Machineoptions = FXCollections.observableArrayList();
	/**
	 * 	 * region CEO chose.
	 */
	public static String Region;
	/**
	 * an array that contains region, Date And MachineID related to their choices.
	 */
	public static ArrayList<String> regionDateAndMachineArray = new ArrayList<>();

	/**
	 * Back button to go back the previous window.
	 */
	@FXML
	private Button BackBtn;
	
	/**
	 * Ekurt Logo to display on the screen
	 */
	@FXML
	private ImageView img;

	/**
	 * Choose button to make a selection based on the options chosen
	 */
	@FXML
	private Button ChooseBtn;

	/**
	 * Combo box to select the month of report from a list of options
	 */
	@FXML
	private ComboBox<String> MonthComboBox = new ComboBox<>();

	/**
	 * Label to explain the month selection combobox
	 */
	@FXML
	private Label MonthLabel;

	/**
	 * Label to explain the report selection combobox
	 */
	@FXML
	private Label Reporttype;

	/**
	 * Combo box to select the type of report from a list of options
	 */
	@FXML
	private ComboBox<String> ReporttypeComboBox = new ComboBox<>();

	/**
	 * Combo box to select the year of report from a list of options
	 */
	@FXML
	private ComboBox<String> YearComboBox = new ComboBox<>();

	/**
	 * Label to explain the year selection combobox
	 */
	@FXML
	private Label YearLabel;

	/**
	 * Label to explain the machine selection combobox
	 */
	@FXML
	private Label MachineLabel;

	/**
	 * Combo box to select the machine for report from a list of options
	 */
	@FXML
	private ComboBox<String> MachineComboBox = new ComboBox<>();

	/**
	 * Combo box to select the region for report from a list of options
	 */
	@FXML
	private ComboBox<String> RegionComboBox = new ComboBox<>();

	/**
	 * Label to explain the region selection combobox
	 */
	@FXML
	private Label RegionLabel;

    /**
	 * return the CEO to the welcome window
	 * 
	 * @param event click on back button
     */
	@FXML
	void Back(ActionEvent event) {
		Massage messageToServer = new Massage();

		((Node) event.getSource()).getScene().getWindow().hide();
		// CustomerServiceWelcomeController CSWC = new
		// CustomerServiceWelcomeController(verifieUser2.get(1));
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CEOWelcomeGUI.fxml"));

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
	 * this method create the wanted report by given month,year,region and type (machineID needed only for Inventory report) and open to the current report window.
	 * @param event click on Choose button.
	 */
	@FXML
	void ChooseReport(ActionEvent event) {
		regionDateAndMachineArray.clear();

		if (ReporttypeComboBox.getValue() == null || MonthComboBox.getValue() == null
				|| YearComboBox.getValue() == null) {

			JOptionPane.showMessageDialog(null, "There is a missing field.", "invalid", JOptionPane.WARNING_MESSAGE);
		}

		else if (ReporttypeComboBox.getValue().equals("Inventory") && MachineComboBox.getValue() == null) {

			JOptionPane.showMessageDialog(null, "Please choose a machine.", "Choose a machine",
					JOptionPane.INFORMATION_MESSAGE);

		}


		else if ((ReporttypeComboBox.getValue().equals("Orders") && RegionComboBox.getValue() == null)
				|| (ReporttypeComboBox.getValue().equals("Customers") && RegionComboBox.getValue() == null)) {
			JOptionPane.showMessageDialog(null, "Please choose region.", "Choose region",
					JOptionPane.INFORMATION_MESSAGE);
		}

		
		else{
			String Type = ReporttypeComboBox.getValue();
			String Month = MonthComboBox.getValue();
			String Year = YearComboBox.getValue();
			String MachineID = MachineComboBox.getValue();
			String date = Month + "/" + Year;
			String region = RegionComboBox.getValue();
			
			
			if (region==null) {
				regionDateAndMachineArray.add("");
			}
			else {
				regionDateAndMachineArray.add(region);
			}
			regionDateAndMachineArray.add(date);
				
			if (!(MachineID == null)) {
				regionDateAndMachineArray.add(MachineID);
			}
			
			Massage messageToServer = new Massage();
			
			
			switch (Type) {
			case "Orders":
				
					((Node) event.getSource()).getScene().getWindow().hide();
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/OrdersReport.fxml"));
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
					break;
				

			case "Customers":
				// "CustomersReport.fxml"
				
					((Node) event.getSource()).getScene().getWindow().hide();
					// CustomerServiceWelcomeController CSWC = new
					// CustomerServiceWelcomeController(verifieUser2.get(1));
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CustomersReport.fxml"));

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
					break;
				

			case "Inventory":
				
					((Node) event.getSource()).getScene().getWindow().hide();
					// CustomerServiceWelcomeController CSWC = new
					// CustomerServiceWelcomeController(verifieUser2.get(1));
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/InventoryReport.fxml"));

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
					break;
				}
			}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);
		
		Machineoptions.clear();
		
		Massage messageToServer = new Massage();
		messageToServer.setOperation("get machine");
		client.ClientUI.chat.accept(messageToServer);
		
		ReporttypeComboBox.setItems(Typeoptions);
		MonthComboBox.setItems(Monthoptions);
		YearComboBox.setItems(Yearoptions);
		RegionComboBox.setItems(Regionoptions);
		MachineComboBox.setItems(Machineoptions);
		
	}

}
