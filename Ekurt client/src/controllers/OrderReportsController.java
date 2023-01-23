package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Massage;

import entities.UsersForApproval;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * This class is used for setting the Orders Reports.
 */
public class OrderReportsController implements Initializable {
	/**
	 * Order report data that back from the server
	 */
	public static ArrayList<String> data = new ArrayList<>();
	/**
	 * to use regionDateAndMachineArray that contains region, Date And Machine from
	 * RegionManagerReportChooseController or CEOReportChooseController.
	 */
	public static ArrayList<String> data1 = new ArrayList<>();
	/**
	 * Total Number Of Orders Array for machine in a month
	 */
	public static ArrayList<Double> TotalNumOfOrdersArray = new ArrayList<>();
	/**
	 * Machine Names Array in a region.
	 */
	public static ArrayList<String> MachinesArray = new ArrayList<>();
	/**
	 * report date to display in report window.
	 */
	public String date;
	/**
	 * data to set in PieChart.
	 */
	public static ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

	/**
	 * Back button to go back the previous window.
	 */
	@FXML
	private Button BackBTN;

	/**
	 * PieChart that shows the data.
	 */
	@FXML
	private PieChart OrdersPieChart;

	/**
	 * Label to display date in report.
	 */
	@FXML
	private Label DateLabel;
	/**
	 * Ekurt Logo to display on the screen
	 */
	@FXML
	private ImageView img;

	/**
	 * return the region manager or CEO to the their welcome window (depends on
	 * who's viewing the report).
	 * 
	 * @param event click on back button
	 */
	@FXML
	void Back(ActionEvent event) {
		if (LoginController.verifieUser.contains("ceo")) {
			Massage messageToServer = new Massage();

			((Node) event.getSource()).getScene().getWindow().hide();
			// CustomerServiceWelcomeController CSWC = new
			// CustomerServiceWelcomeController(verifieUser2.get(1));
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CEOReportChooseWindow.fxml"));

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
		} else {
			Massage messageToServer = new Massage();

			((Node) event.getSource()).getScene().getWindow().hide();
			// CustomerServiceWelcomeController CSWC = new
			// CustomerServiceWelcomeController(verifieUser2.get(1));
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/RManagerReportChooseWindow.fxml"));

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
	}

	/**
	 * this method set the chart with the Orders data related to the chosen month,
	 * year and region.
	 */
	void update_chart_view() {
		data.clear();
		list.clear();
		MachinesArray.clear();
		TotalNumOfOrdersArray.clear();
		System.out.println(data1);
		Massage messageToServer = new Massage();
		messageToServer.setOperation("getmonthlyordersreportdata");
		messageToServer.setData(data1);
		client.ClientUI.chat.accept(messageToServer);
		System.out.println(data);
		for (int i = 0; i <= data.size() - 2; i = i + 2) {
			System.out.println("1");
			int indexForMachine = MachinesArray.indexOf(data.get(i + 1));
			if (indexForMachine != -1) {
				Double tmpNumOfOrders = TotalNumOfOrdersArray.get(indexForMachine) + 1;
				TotalNumOfOrdersArray.set(indexForMachine, tmpNumOfOrders);
			} else {
				MachinesArray.add(data.get(i + 1));
				indexForMachine = MachinesArray.indexOf(data.get(i + 1));
				Double tmpNumOfOrders = (double) 1;
				TotalNumOfOrdersArray.add(indexForMachine, tmpNumOfOrders);
			}
		}

		for (int i = 0; i < MachinesArray.size(); i++) {

			list.add(new PieChart.Data(MachinesArray.get(i), TotalNumOfOrdersArray.get(i)));
			System.out.println(MachinesArray.get(i));
		}
		list.forEach(
				data -> data.nameProperty().bind(Bindings.concat(data.getName(), " amount ", data.pieValueProperty())));
		OrdersPieChart.getData().addAll(list);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);
		data1 = RegionManagerReportChooseController.regionDateAndMachineArray;
		if (data1 == null || data1.isEmpty()) {
			data1 = CEOReportChooseController.regionDateAndMachineArray;
		}
		date = data1.get(1);
		DateLabel.setText("\t" + date);
		update_chart_view();
	}

}
