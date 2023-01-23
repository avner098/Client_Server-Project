package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Massage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * This class is used for setting the Customers Reports.
 *
 */
public class CustomerReportsController implements Initializable {
	/**
	 * Customers report data that back from the server
	 */
	public static ArrayList<String> data = new ArrayList<>();
	/**
	 * to use regionDateAndMachineArray that contains region, Date And Machine from
	 * RegionManagerReportChooseController or CEOReportChooseController.
	 */
	public static ArrayList<String> data1 = new ArrayList<>();
	/**
	 * Total Number Of Orders Array for user in a month
	 */
	public ArrayList<Integer> TotalOrdersArray = new ArrayList<>();
	/**
	 * usernames array of users that made an order at the chosen month.
	 */
	public static ArrayList<String> usernameArray = new ArrayList<>();

	/**
	 * report date to display in report window.
	 */
	public String date;
	/**
	 * Back button to go back the previous window.
	 */
	@FXML
	private Button BackBTN;

	/**
	 * Ekurt Logo to display on the screen
	 */
	@FXML
	private ImageView img;

	/**
	 * BarChart that shows the data.
	 */
	@FXML
	private BarChart<String, Integer> customersReportBarChart;

	/**
	 * Label to show the report Date.
	 */
	@FXML
	private Label DateLabel;

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
	 * this method set the chart with the customers data related to the chosen
	 * month, year and region.
	 */
	private void update_table_view() {
		data.clear();
		TotalOrdersArray.clear();
		usernameArray.clear();

		System.out.println(data1);

		Massage messageToServer1 = new Massage();
		messageToServer1.setOperation("get customer");
		client.ClientUI.chat.accept(messageToServer1);

		for (String x : usernameArray) {
			TotalOrdersArray.add(0);
		}

		Massage messageToServer = new Massage();
		messageToServer.setOperation("getmonthlyactivityreportdata");
		messageToServer.setData(data1);
		client.ClientUI.chat.accept(messageToServer);

		for (int i = 0; i < data.size(); i += 2) {
			String name = data.get(i);
			String machine = data.get(i + 1);
			int ind = usernameArray.indexOf(name);
			TotalOrdersArray.set(ind, TotalOrdersArray.get(ind) + 1);

		}
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		for (int i = 0; i < usernameArray.size(); i++) {
			String user = usernameArray.get(i);
			Integer orders = TotalOrdersArray.get(i);

			series.getData().add(new Data<String, Integer>(user, orders));
		}

		customersReportBarChart.getData().add(series);

		System.out.println(data);
		System.out.println(TotalOrdersArray);
		System.out.println(usernameArray);

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
		update_table_view();
	}

}
