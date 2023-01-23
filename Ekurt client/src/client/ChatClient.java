
package client;

import common.*;
import controllers.ActivateSaleController;
import controllers.CEOReportChooseController;
import controllers.ChatControlerr;
import controllers.CustomerReportsController;
import controllers.InventoryReportController;
import controllers.LoginController;
import controllers.MakeDiscountController;
import controllers.MangeOrderControl;
import controllers.MissingProductsNotificationsController;
import controllers.OpenDeliveryController;
import controllers.OpenTaskMarktingController;
import controllers.OrderReportsController;
import controllers.OrderSummeryController;
import controllers.PaymentAndDeliveryController;
import controllers.RegionManagerReportChooseController;
import controllers.RestokeCallController;
import controllers.SendRequestToEmployeeController;
import controllers.SetMinimumAmountController;
import controllers.UpdateInventoryController;
import controllers.UsersApprovalController;
import controllers.machineChooseController;
import controllers.order1Controller;
import controllers.order2Controller;
import controllers.projectClientController;
import controllers.regisrionController;
import controllers.subscriberController;
import javafx.scene.image.Image;
import ocsf.client.AbstractClient;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/**
 * Class that talk straight to the server
 * 
 * @author
 *
 */
public class ChatClient extends AbstractClient {

	ChatIF clientUI;
	/**
	 * Indicate if the server is still working on your request
	 */
	public static boolean awaitResponse = false;

	/**
	 * constructor
	 * 
	 * @param host
	 * @param port
	 * @param clientUI
	 * @throws IOException
	 */
	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
	}

	/**
	 * Method that handle with data the received from the server
	 */
	public void handleMessageFromServer(Object msg) {

		awaitResponse = false;

		Massage messageFromServer = (Massage) msg;
		ArrayList<String> data = messageFromServer.getData();

		String action = messageFromServer.getOperation();

		System.out.println(action);

		switch (action) {
		case "get user":
			for(int i = 0 ; i< data.size();i+=2) {
			subscriberController.users.add(data.get(i));
			subscriberController.ids.add(data.get(i+1));
			}
			break;
		case "get machine":
			for (String x : data) {
				CEOReportChooseController.Machineoptions.add(x);
			}
			break;
		case "get customer":
			CustomerReportsController.usernameArray = data;
			break;
		case "check arrived status":
			if (data.isEmpty())
				OpenDeliveryController.flag_Status_from_server = true;
			else
				OpenDeliveryController.flag_Status_from_server = false;
			break;
		case "get customers":
			for (String x : data) {
				LoginController.CustomerUserName.add(x);
			}
			break;
		case "get subscriber status":
			LoginController.subscriber = data;
			break;
		case "get discount prices":

			OrderSummeryController.price_dis = data;
			break;
		case "arrived":
			order2Controller.inventory2 = data;
			break;

		// Ask the server to get the status of the order.
		case "get status":
			order1Controller.inventory2 = data;
			break;

		case "get user info":
			regisrionController.userData = data;
			break;
		case "get users":
			for (String x : data) {
				regisrionController.users.add(x);

			}
			break;
		case "delivery":
			if (data.contains("initialize")) {
				data.remove(data.size() - 1);
				System.out.println("OK4");
				OpenDeliveryController.data = data;
			} else if (data.contains("get_Time_Delivery_Duration")) {
				data.remove(data.size() - 1);
				OpenDeliveryController.Time_Delivery_Duration = Integer.parseInt(data.get(0));
			} else if (data.contains("Change_Status")) {
				data.remove(data.size() - 1);
				System.out.println("");
			} else {
				System.out.println("");
			}

			break;
		case "Markting":
			if (data.contains("initialize")) {
				data.remove(data.size() - 1);
				SendRequestToEmployeeController.data = data;
			} else if (data.contains("initialize_combo_box")) {
				data.remove(data.size() - 1);
				MakeDiscountController.product_for_init_purpose = data;
			} else if (data.contains("check product sales")) {
				data.remove(data.size() - 1);
				System.out.println(data);
				if (!data.isEmpty())
					MakeDiscountController.product_status = true;
			} else {
				System.out.println("OK5");
				MakeDiscountController.data = data;
			}
			break;
		case "Marketing Employee":
			if (!data.isEmpty())
				ActivateSaleController.data = data;
			break;
		case "Marketing Request":
			if (data.contains("initialize")) {
				data.remove(data.size() - 1);
				SendRequestToEmployeeController.data = data;
			} else
				System.out.println("");
			break;
		case "Open Task Markting Employee":
			if (data.contains("initialize")) {
				data.remove(data.size() - 1);
				OpenTaskMarktingController.data = data;
			} else
				System.out.println("");
			break;
		case "Carrier":
			if (data.contains("check arrive status")) {
				System.out.println("");
			}
			System.out.println("Carrier init");
			break;
		case "Automatic SMS to user":
			break;

		case "get machines":
			order2Controller.inventory1 = data;
			machineChooseController.inv = data;
			break;

		case "set time":
			order2Controller.inventory2 = data;
			break;

		case "old number":
			order2Controller.inventory1 = data;
			break;
		case "product info":
			order2Controller.inventory2 = data;
			break;

		case "manage order":
			MangeOrderControl.inventory1 = data;
			break;

		case "get order number":
			order2Controller.inventory1 = data;
			break;

		case "machine invatory":
			order2Controller.inventory1 = data;
			break;

		case "delete order after timeout":
			order2Controller.inventory2 = data;
			break;

		case "new order":
			order2Controller.inventory2 = data;
			break;

		case "price and amount update":
			order2Controller.inventory2 = data;
			break;

		case "get image":

			Image img = new Image(new ByteArrayInputStream(messageFromServer.getMybytearray()));
			order2Controller.img = img;
			break;
		case "close restoke call":
			break;
		case "get open restoke":
			RestokeCallController.data = data;
			break;
		case "get region":
			PaymentAndDeliveryController.regionChoose = data.get(0);
			break;
		case "send messeges":
			break;
		case "get users to messege":
			for (String x : data) {
				ChatControlerr.userName.add(x);
			}
			break;
		case "get messeges":
			ChatControlerr.data = data;
			break;
		case "pay":
			break;
		case "get subscriber":
			PaymentAndDeliveryController.payoption = data.get(0);
			break;
		case "get discunt":
			PaymentAndDeliveryController.totalAfterdiscunt = data.get(0);
			break;
		case "get prices":
			OrderSummeryController.price = data;
			break;
		case "get order summery":
			OrderSummeryController.order = data;
			break;
		case "update invntory":
			break;
		case "get machine by region":

			for (String x : data) {
				UpdateInventoryController.machine.add(x);

			}

			break;
		case "inventory update":
			UpdateInventoryController.inventory = data;
			break;

		case "subscriber":
			subscriberController.subscriberStatus = data.get(0);
			break;

		case "register":
			regisrionController.registerStatus = data.get(0);
			break;

		case "notset":
			System.out.println("OK");
			break;
		case "set":
			System.out.println("OK1");
			// ClientUI.controler.infotoprint = data;
			projectClientController.infotoprint = data;
			break;
		case "disconnect":
			System.out.println("OK2");
			// this.quit();
			break;
		case "login":
			System.out.println("OK3");
			LoginController.verifieUser.clear();
			LoginController.verifieUser = data;
			break;
		case "getusersapprovaldata":
			UsersApprovalController.data = data;
			break;
		case "setwaitingforapprovalusers":
			// UsersApprovalController.data=data;
			System.out.println("OKAppovedorDenied");
			break;
		case "getmachinesIDforRegion":
			SetMinimumAmountController.MachinesIDdata = data;
			break;
		case "getminimumamount":
			SetMinimumAmountController.data = data;
			System.out.println("OKDataReceived");
			break;
		case "setnewminimumvaluedata":
			System.out.println("OKNewMinValue");
			break;

		case "getmissingproductsldata":
			MissingProductsNotificationsController.data = data;
			break;

		case "sendmissingproductsmessagetoemployee":
			System.out.println("OKMessageSentToEmployee");
			break;

		case "getopenrestokecallsforspecificregion":
			MissingProductsNotificationsController.data2 = data;
			break;

		case "getmonthlyordersreportdata":
			OrderReportsController.data = data;
			break;

		case "getmonthlyactivityreportdata":
			CustomerReportsController.data = data;
			break;

		case "getmachinesIDforReports":
			RegionManagerReportChooseController.MNames = data;
			break;

		case "getmonthlyinventoryreportdata":
			InventoryReportController.data = data;
			break;
		default:
			System.out.println("OK4");

		}

		/*
		 * ArrayList<String> info = null;
		 * 
		 * info = (ArrayList<String>) msg;
		 * 
		 * if (!info.get(0).equals("notset")) ClientUI.controler.infotoprint = info;
		 */
	}

	/**
	 * Method that handle with message from client
	 * 
	 * @param message
	 */
	public void handleMessageFromClientUI(Massage message) {

		try {

			awaitResponse = true;
			sendToServer(message);
			// wait for response

			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			clientUI.display("Could not send message to server: Terminating client." + e);
			quit();
		}
		/*
		 * try { sendToServer(message); } catch(IOException e) { clientUI.display
		 * ("Could not send message to server.  Terminating client."); quit(); }
		 */
	}

	/**
	 * Method that close all the server connections
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
