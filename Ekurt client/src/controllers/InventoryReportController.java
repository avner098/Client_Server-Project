package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.IInventoryReportController;
import common.ILoginController;
import common.Massage;
import entities.InventoryReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

//InventoryReport
/**
 * This class is used for setting the Inventory Reports.
 *
 */
public class InventoryReportController implements Initializable {
	public InventoryReportController() {
		flag = 0;
		IIRC = new InventoryReportManager();
	}

	IInventoryReportController IIRC;

	public InventoryReportController(IInventoryReportController iirc) {
		flag = 1;
		IIRC = iirc;
	}

	/**
	 * Inventory report data that back from the server
	 */
	public static ArrayList<String> data = new ArrayList<>();

	/**
	 * XYChart Series for report.
	 */
	public XYChart.Series<String, Integer> series;

	public int flag = 0;

	/**
	 * to use regionDateAndMachineArray from RegionManagerReportChooseController or
	 * CEOReportChooseController.
	 */
	public ArrayList<String> data1 = new ArrayList<>();
	/**
	 * Report details that printed in top of the report window.
	 */
	String nameData;
	/**
	 * total amount of missing products in a month.
	 */
	Integer totalMissing = 0;

	@FXML
	private Button BackBTN;

	@FXML
	private Label AverageMissingLabel;

	@FXML
	private Label NameDateLabel;

	@FXML
	private ImageView img;

	@FXML
	private LineChart<String, Integer> inventoryReportBarChart;

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
	 * this method set the chart with the inventory data related to the chosen
	 * month, year and machine.
	 * 
	 * @throws Exception
	 */

	ArrayList<String> update_chart_view() throws Exception {
		data.clear();
		data = IIRC.create_Inventory_Report();
		ArrayList<String> error = new ArrayList<>();
		
		ArrayList<Integer> dateArray = new ArrayList<>(); // [9/10/2023] 09/10/2023
		ArrayList<String> totalAmount = new ArrayList<>();
		for (int i = 0; i < 31; i++) {
			dateArray.add(0);
		}
		
		if(data.size() < 2) { 
			error.add("received missing data from server");
			return error;
		}
		
		for (int i = 0; i <= data.size() - 2; i = i + 2) {
			if (data.get(i + 1) == null || data.get(i + 1).equals("")) { // date
				{
					error.add("NULL date");
					return error;
				}
			}
			String[] arrOfStr = data.get(i + 1).split("/");
			int j = 0;
			for (String x : arrOfStr) {
			
				if (x == null) {
					error.add("NULL date");
					return error;
				}
				if (j == 0) {
					j++;
					if(x.matches("[a-zA-Z]+"))
					{
						error.add("Illegal date");
						return error;
					}
					if (Integer.parseInt(x) < 1 || Integer.parseInt(x) > 31) {
						error.add("Illegal date");
						return error;
					}
				}
				else if (j == 1) {
					j++;
					if(x.matches("[a-zA-Z]+"))
					{
						error.add("Illegal date");
						return error;
					}					
					if (Integer.parseInt(x) < 1 || Integer.parseInt(x) > 12) {
						error.add("Illegal date");
						return error;
					}
				}
				else if (j == 2)
					
					if(x.matches("[a-zA-Z]+"))
					{
						error.add("Illegal date");
						return error;
					}
					if (Integer.parseInt(x) < 0 || Integer.parseInt(x) > 2023) {
						error.add("Illegal date");
						return error;
					}

			}


			if (data.get(i + 1).length() != 10) {
				error.add("Illegal date");
				return error;
			}
			String day = data.get(i + 1).substring(0, 2);
			if (Integer.parseInt(day) < 0 || day.contains("/") || Integer.parseInt(day) > 31) {
				error.add("Illegal date");
				return error;
			}
		
			int d = Integer.parseInt(day);
			if (data.get(i) == null || data.get(i).equals("")) { // amount
				error.add("NULL amount");
				return error;
			}
			try {
			if (Integer.parseInt(data.get(i)) < 0) {
				error.add("Illegal amount");
				return error;
			}
			}catch(Exception e){
				error.add("Illegal amount");
				return error;
			}
			dateArray.add(d - 1, Integer.parseInt(data.get(i)));
			totalMissing += Integer.parseInt(data.get(i));
		}
		series = new XYChart.Series<>();
		for (int i = 0; i < 31; i++) {
			series.getData().add(new Data<String, Integer>(Integer.toString(i + 1), dateArray.get(i)));
		}
		totalAmount.add(totalMissing.toString());
		return totalAmount;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (flag == 0) {
			Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
			img.setImage(image);
			data1 = RegionManagerReportChooseController.regionDateAndMachineArray;
			if (data1 == null || data1.isEmpty()) {
				data1 = CEOReportChooseController.regionDateAndMachineArray;
			}
			nameData = "Machine #" + data1.get(2) + " in " + data1.get(1);
			NameDateLabel.setText(" " + nameData);
			try {
				update_chart_view();
			} catch (Exception e) {
				e.printStackTrace();
			}
			AverageMissingLabel.setText("Average missing products a day: " + Float.toString(totalMissing / 31));
			inventoryReportBarChart.getData().add(series);
		}
	}

	public class InventoryReportManager implements IInventoryReportController {

		@Override
		public ArrayList<String> create_Inventory_Report() {
			Massage messageToServer = new Massage();
			messageToServer.setOperation("getmonthlyinventoryreportdata");
			messageToServer.setData(data1);
			client.ClientUI.chat.accept(messageToServer);
			return data;
		}
	}
}
