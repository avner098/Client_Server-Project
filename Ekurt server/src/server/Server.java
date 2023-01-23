package server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;

import common.Massage;
import entities.Client;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import servergui.ProjectServerController;

/**
 * Server prototype is a class that extends "AbstractServer" class from OCSF
 * 
 * @author
 *
 */
public class Server extends AbstractServer {

	/**
	 * Connection to the data base
	 */
	static Connection conn;
	/**
	 * user information to Connect the data base to
	 */
	private ArrayList<String> dataToConnectDB;

	/**
	 * constructor
	 * 
	 * @param port
	 */
	public Server(int port) {
		super(port);
	}

	/**
	 * A simulation method of importing the user table from an external company
	 */
	public static void importUsersfromDb() {
		MysqlConnection.importUsers();
	}

	/**
	 * A simulation method of exporting the user table from an external company
	 */
	public static void exportUsersfromDb() {
		MysqlConnection.exportUsers();
	}

	/**
	 * HandleMessageFromClient method OVERRIDE the method from the AbstractServer
	 * and handle all the requests from Client
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		try {

			Massage messageFromClient = (Massage) msg;
			ArrayList<String> data = messageFromClient.getData();
			String action = messageFromClient.getOperation();

			Massage messageToClient = new Massage();

			switch (action) {
			
			case "get user":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getUser());
				messageToClient.setOperation("get user");
				client.sendToClient(messageToClient);
				break;
			case "get customer":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getCustomers());
				messageToClient.setOperation("get customer");
				client.sendToClient(messageToClient);
				break;
			case "get customers":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getCustomers());
				messageToClient.setOperation("get customers");
				client.sendToClient(messageToClient);
				break;
			case "get subscriber status":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getSubscriberStatus(data));
				messageToClient.setOperation("get subscriber status");
				client.sendToClient(messageToClient);
				break;
			case "get discount prices":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getDiscountedPrices(data));
				messageToClient.setOperation("get discount prices");
				client.sendToClient(messageToClient);
				break;
			case "arrived":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				MysqlConnection.got_order(data.get(0));
				;
				messageToClient.setOperation("arrived");
				client.sendToClient(messageToClient);
				break;

			// Send to the customer the order status.
			case "get status":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				ArrayList<String> pro3 = new ArrayList<String>();
				pro3 = (MysqlConnection.check_status(data.get(0)));
				messageToClient.setData(pro3);
				messageToClient.setOperation("get status");
				client.sendToClient(messageToClient);
				break;

			case "get user info":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getUserinfo(data.get(0)));
				messageToClient.setOperation("get user info");
				client.sendToClient(messageToClient);
				break;
			case "get users":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getUsers());
				messageToClient.setOperation("get users");
				client.sendToClient(messageToClient);
				break;
			case "Automatic SMS to user":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				MysqlConnection.SendDeliveryDateToUser(data);
				messageToClient.setOperation("Automatic SMS to user");
				messageToClient.setData(data);
				client.sendToClient(messageToClient);
				break;
			case "Carrier":
				if (data.contains("check arrive status")) {
					MysqlConnection.setMessageFromStatus();
					messageToClient.setOperation("Carrier");
					messageToClient.setData(data);
					client.sendToClient(messageToClient);
				} else {
					System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
					MysqlConnection.iniliazeDeliveryTable(data);
					messageToClient.setOperation("Carrier");
					messageToClient.setData(data);
					client.sendToClient(messageToClient);
				}
				break;
			case "Open Task Markting Employee":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				System.out.println("The data is: " + data);
				if (data.get(0).equals("initialize")) {
					ArrayList<String> output = new ArrayList<>();
					output = MysqlConnection.GetOpenTaskForMarkting(data);
					messageToClient.setOperation("Open Task Markting Employee");
					output.add("initialize");
					messageToClient.setData(output);
					client.sendToClient(messageToClient);
				} else {
					MysqlConnection.changeRequestStatusSale(data);
					messageToClient.setOperation("Open Task Markting Employee");
					messageToClient.setData(data);
					client.sendToClient(messageToClient);
				}
				break;
			case "Marketing Request":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				if (data.get(0).equals("initialize")) {
					ArrayList<String> output = new ArrayList<>();
					output = MysqlConnection.showsaleForRequest(data);
					messageToClient.setOperation("Marketing Request");
					output.add("initialize");
					messageToClient.setData(output);
					client.sendToClient(messageToClient);
				} else {
					MysqlConnection.changeRequestStatusSale(data);
					messageToClient.setData(MysqlConnection.userstomessege(data));
					messageToClient.setOperation("Marketing Request");
					client.sendToClient(messageToClient);
				}
				break;
			case "Marketing Employee":

				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				System.out.println("The data is: " + data);
				if (data.get(0).equals("initialize")) {
					ArrayList<String> output2 = new ArrayList<>();
					output2 = MysqlConnection.showsale(data);
					messageToClient.setOperation("Marketing Employee");
					messageToClient.setData(output2);
					client.sendToClient(messageToClient);
				} else {
					MysqlConnection.changeStatusSale(data);
					messageToClient.setOperation("Marketing Employee");
					data.clear();
					messageToClient.setData(data);
					client.sendToClient(messageToClient);
				}

				break;
			case "Markting":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				System.out.println("The data is: " + data);
				if (data.get(0).equals("initialize")) {
					ArrayList<String> output = new ArrayList<>();
					output = MysqlConnection.showsale(data);
					messageToClient.setOperation("Markting");
					output.add("initialize");
					messageToClient.setData(output);

					client.sendToClient(messageToClient);
					break;
				} else if (data.contains("initialize_combo_box")) {
					ArrayList<String> output = new ArrayList<>();
					output = MysqlConnection.getAllProduct();
					messageToClient.setOperation("Markting");
					output.add("initialize_combo_box");
					messageToClient.setData(output);
					client.sendToClient(messageToClient);
					break;
				} else if (data.contains("check product sales")) {
					ArrayList<String> output = new ArrayList<>();
					output = MysqlConnection.checkifProductAppear(data);
					output.add("check product sales");
					messageToClient.setOperation("Markting");
					messageToClient.setData(output);
					client.sendToClient(messageToClient);
				} else if (data.size() > 1) {
					MysqlConnection.addSale(data);
					messageToClient.setOperation("Markting");
					messageToClient.setData(data);
					client.sendToClient(messageToClient);

				} else {
					ArrayList<String> output = new ArrayList<>();
					output = MysqlConnection.AllQueryOperate(data);
					messageToClient.setOperation("Markting");
					messageToClient.setData(output);
					client.sendToClient(messageToClient);
				}

				break;
			case "check arrived status":
				ArrayList<String> output1 = MysqlConnection.checkmessagequery(data);
				messageToClient.setOperation("check arrived status");
				messageToClient.setData(output1);
				client.sendToClient(messageToClient);
				break;

			case "delivery":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				System.out.println("The data is: " + data);
				if (data.get(0).equals("initialize")) {
					ArrayList<String> output = MysqlConnection.printDelivery(data.get(1));
					output.add("initialize");
					System.out.println(output.toString());
					messageToClient.setData(output);
//				} else
//					MysqlConnection.ChangeDeliveryStatus(data);
				} else if (data.get(1).equals("get_Time_Delivery_Duration")) {
					data = MysqlConnection.getTimeDeliveryDuration(data);
					data.add("get_Time_Delivery_Duration");
					messageToClient.setData(data);
//					int Time_Delivery_Duration=Integer.parseInt(data.get(0));
				} else if (data.contains("Change_Status")) {
					MysqlConnection.changeDeliveryStatusWithDate(data);
					data.clear();
					data.add("Change_Status");
					messageToClient.setData(data);
				} else {
					MysqlConnection.closeOrderDelivery(data);
					data.clear();
					messageToClient.setData(data);
				}

				messageToClient.setOperation("delivery");

				client.sendToClient(messageToClient);
				break;
			case "machines":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				ArrayList<String> pro2 = new ArrayList<String>();
				pro2 = (MysqlConnection.getMachines());
				messageToClient.setData(pro2);
				messageToClient.setOperation("get machines");
				client.sendToClient(messageToClient);
				break;
				
			case "get machine":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getMachines());
				messageToClient.setOperation("get machine");
				client.sendToClient(messageToClient);
				break;
				
			case "set time":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				MysqlConnection.getTime(data.get(0));
				messageToClient.setOperation("set time");
				client.sendToClient(messageToClient);
				break;

			case "old number":
				MysqlConnection.oldOrder(data.get(0));
				messageToClient.setOperation("old number");
				client.sendToClient(messageToClient);
				break;

			case "product info":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				ArrayList<String> pro = new ArrayList<String>();
				pro = (MysqlConnection.productInfo());
				messageToClient.setData(pro);
				messageToClient.setOperation("product info");
				client.sendToClient(messageToClient);
				break;

			case "manage order":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				ArrayList<String> order = new ArrayList<String>();
				order = (MysqlConnection.manage_order(data.get(0)));
				messageToClient.setData(order);
				messageToClient.setOperation("manage order");
				client.sendToClient(messageToClient);
				break;

			case "get order number":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				ArrayList<String> orderNUM = new ArrayList<>();
				orderNUM.add(MysqlConnection.getOrderNUmner());
				messageToClient.setData(orderNUM);
				messageToClient.setOperation("get order number");
				client.sendToClient(messageToClient);

				break;
			case "machine invatory":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				ArrayList<String> able1 = new ArrayList<>();
				able1 = MysqlConnection.machineInvatory(data.get(0));
				messageToClient.setData(able1);
				messageToClient.setOperation("machine invatory");
				client.sendToClient(messageToClient);
				break;

			case "delete order after timeout":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				MysqlConnection.timeOut(data);
				messageToClient.setOperation("delete order after timeout");
				client.sendToClient(messageToClient);
				break;

			case "new order":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				MysqlConnection.createOrderRow(data);
				messageToClient.setOperation("new order");
				client.sendToClient(messageToClient);

				break;

			case "update price_amount":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				String price;
				price = MysqlConnection.update_DB(data);
				data.set(0, price);
				messageToClient.setData(data);
				messageToClient.setOperation("price and amount update");
				client.sendToClient(messageToClient);
				break;

			case "get image":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				System.out.println(data);
				InputStream inputStream = MysqlConnection.getImage(data);
				String filename = data.get(0);
				FileOutputStream fos = null;
				try {
					OutputStream os=null;
					try {
					 os = new FileOutputStream(new File(filename + ".jpg"));
					}catch(Exception e) {
						 os = new FileOutputStream(new File("/cola/logo.jpg"));
					}
					byte[] content = new byte[3000000];
					int size = 0;

					while ((size = inputStream.read(content)) != -1) {

						os.write(content, 0, size);
					}

					///////////////////
					/*
					"C:\waffles.jpg"
					"C:\bamba.jpg"
					"C:\cola.jpg"
					"C:\doritos.jpg"
					"C:\Ekurt DB.sql"
					"C:\fanta.jpg"
					"C:\natureValley.jpg"
					"C:\OrangeJuice.jpg"
					"C:\sprite.jpg"
*/
					File newFile = new File(filename + ".jpg");
					messageToClient.setFileName(newFile.getName());

					byte[] mybytearray = new byte[(int) newFile.length()];
					FileInputStream fis = new FileInputStream(newFile);
					BufferedInputStream bis = new BufferedInputStream(fis);

					messageToClient.initArray(mybytearray.length);
					messageToClient.setSize(mybytearray.length);

					bis.read(messageToClient.getMybytearray(), 0, mybytearray.length);

				} catch (FileNotFoundException e) {
					messageToClient.setOperation("get image");
					client.sendToClient(messageToClient);
					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
				messageToClient.setOperation("get image");
				client.sendToClient(messageToClient);
				break;

			case "close restoke call":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				MysqlConnection.closeRestokeCall(data);
				messageToClient.setOperation("close restoke call");
				client.sendToClient(messageToClient);
				break;
			case "get open restoke":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getOpenRestoke());
				messageToClient.setOperation("get open restoke");
				client.sendToClient(messageToClient);
				break;
			case "get region":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getRegion(data));
				messageToClient.setOperation("get region");
				client.sendToClient(messageToClient);
				break;
			case "send messeges":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				MysqlConnection.sendMessges(data);
				messageToClient.setOperation("send messeges");
				client.sendToClient(messageToClient);
				break;
			case "get users to messege":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.userstomessege(data));
				messageToClient.setOperation("get users to messege");
				client.sendToClient(messageToClient);
				break;
			case "get messeges":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getmesseges(data));
				;
				messageToClient.setOperation("get messeges");
				client.sendToClient(messageToClient);
				break;
			case "pay":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				MysqlConnection.toPay(data);
				messageToClient.setOperation("pay");
				client.sendToClient(messageToClient);
				break;
			case "get subscriber":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getsubscriber(data));
				messageToClient.setOperation("get subscriber");
				client.sendToClient(messageToClient);
				break;
			case "get discunt":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getdiscount(data));
				messageToClient.setOperation("get discunt");
				client.sendToClient(messageToClient);
				break;

			case "get prices":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getprices());
				messageToClient.setOperation("get prices");
				client.sendToClient(messageToClient);
				break;
			case "get order summery":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getOrderSummery(data.get(0)));
				messageToClient.setOperation("get order summery");
				client.sendToClient(messageToClient);
				break;
			case "update invntory":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				MysqlConnection.updateInventory(data);
				messageToClient.setOperation("update invntory");
				client.sendToClient(messageToClient);
				break;

			case "get machine by region":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.getMachines());
				messageToClient.setOperation("get machine by region");
				client.sendToClient(messageToClient);
				break;

			case "inventory update Upload":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setData(MysqlConnection.inventotyUpdateUpload(data.get(0)));
				messageToClient.setOperation("inventory update");
				client.sendToClient(messageToClient);
				break;

			case "subscriber":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				String subscriber = MysqlConnection.subscriberRegisrtion(data);
				data.set(0, subscriber);
				messageToClient.setData(data);
				messageToClient.setOperation("subscriber");
				client.sendToClient(messageToClient);
				break;

			case "register":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				String register = MysqlConnection.saveData(data);
				data.set(0, register);
				messageToClient.setData(data);
				messageToClient.setOperation("register");
				client.sendToClient(messageToClient);
				break;

			case "logout":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				MysqlConnection.logout(data);
				messageToClient.setData(data);
				messageToClient.setOperation("notset");
				client.sendToClient(messageToClient);
				break;

			case "login":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				ArrayList<String> loginInfo = MysqlConnection.login(data);

				if (loginInfo.size() > 1) {
					MysqlConnection.setTologged(data);
				}
				// loginInfo.add(0, "login");
				/*
				 * loginInfo.add(data.get(0)); loginInfo.add(data.get(1));
				 */
				System.out.println(loginInfo.toString());

				messageToClient.setData(loginInfo);
				messageToClient.setOperation("login");

				client.sendToClient(messageToClient);

				break;

			case "information":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);

				ArrayList<String> table = MysqlConnection.printsubscriber(data.get(0));

				System.out.println(table.toString());
				/*
				 * if(table.isEmpty()) table.add("no id"); table.add(0, "set");
				 */
				if (table.isEmpty())
					messageToClient.setOperation("no id");
				messageToClient.setOperation("set");

				messageToClient.setData(table);

				client.sendToClient(messageToClient);

				break;

			case "update":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client); // data.remove(0);
				MysqlConnection.Update(data);

				data.set(0, "notset");
				messageToClient.setData(data);
				client.sendToClient(messageToClient);
				break;

			case "connect":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				data.add("connect");
				updateTable(data);

				// data.set(0, "notset");
				messageToClient.setData(data);
				messageToClient.setOperation("notset");
				client.sendToClient(messageToClient);

				break;

			case "disconnect":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);

				if (data.size() > 3) {
					ArrayList<String> LogOutUser = new ArrayList<>();
					LogOutUser.add(data.get(2));
					LogOutUser.add(data.get(3));
					System.out.println(LogOutUser);
					MysqlConnection.logout(LogOutUser);
					data.remove(3);
					data.remove(2);

				}
				data.add("disconnect");
				updateTable(data);
				data.set(0, "disconnect");
				messageToClient.setData(data);

				client.sendToClient(messageToClient);
				break;

			case "getusersapprovaldata":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setOperation("getusersapprovaldata");
				messageToClient.setData(MysqlConnection.getWaitingForApprovalUsers());
				client.sendToClient(messageToClient);
				break;

			case "setwaitingforapprovalusers":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setOperation("setwaitingforapprovalusers");
				messageToClient.setData(MysqlConnection.setWaitingForApprovalUsers(data));
				client.sendToClient(messageToClient);
				break;

			case "getmachinesIDforRegion":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setOperation("getmachinesIDforRegion");
				messageToClient.setData(MysqlConnection.getmachinesIDforRegion(data));
				client.sendToClient(messageToClient);
				break;

			case "getminimumamount":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setOperation("getminimumamount");
				messageToClient.setData(MysqlConnection.getMinimumValue(data));
				client.sendToClient(messageToClient);
				break;

			case "setnewminimumvaluedata":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setOperation("setnewminimumvaluedata");
				messageToClient.setData(MysqlConnection.SetNewMinimumValue(data));
				client.sendToClient(messageToClient);
				break;

			case "getmissingproductsldata":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				System.out.println(data.toString());
				messageToClient.setOperation("getmissingproductsldata");
				messageToClient.setData(MysqlConnection.getMissingProductsInRegion(data)); /////////////
				client.sendToClient(messageToClient);
				break;

			case "sendmissingproductsmessagetoemployee":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setOperation("sendmissingproductsmessagetoemployee");
				messageToClient.setData(MysqlConnection.SendMissingProductsMessageToRegionEmployee(data)); //////////////
				client.sendToClient(messageToClient);
				break;

			case "getopenrestokecallsforspecificregion":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				System.out.println(data.toString());
				messageToClient.setOperation("getopenrestokecallsforspecificregion");
				messageToClient.setData(MysqlConnection.getOpenRestockCallsforSpecificRegion(data)); /////////////
				client.sendToClient(messageToClient);
				break;

			case "getmonthlyordersreportdata":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				System.out.println(data.toString());
				messageToClient.setOperation("getmonthlyordersreportdata");
				messageToClient.setData(MysqlConnection.getDataForMachineOrdersReport(data)); /////////////
				client.sendToClient(messageToClient);
				break;

			case "getmonthlyactivityreportdata":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				System.out.println(data.toString());
				messageToClient.setOperation("getmonthlyactivityreportdata");
				messageToClient.setData(MysqlConnection.getDataForActivityReport(data)); /////////////
				client.sendToClient(messageToClient);
				break;

			case "getmachinesIDforReports":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				messageToClient.setOperation("getmachinesIDforReports");
				messageToClient.setData(MysqlConnection.getmachinesIDforRegion(data));
				client.sendToClient(messageToClient);
				break;

			case "getmonthlyinventoryreportdata":
				System.out.println("Message received: " + messageFromClient.getOperation() + " from " + client);
				System.out.println(data.toString());
				messageToClient.setOperation("getmonthlyinventoryreportdata");
				messageToClient.setData(MysqlConnection.getDataForMachineInventoryReport(data));
				System.out.println(MysqlConnection.getDataForMachineInventoryReport(data));/////////////
				client.sendToClient(messageToClient);
				break;
			default:
				System.out.println(action);
				throw new Exception("Invalid operation");
			}

		} catch (Exception ex) {
			System.out.println(ex.toString());

		}

	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
		MysqlConnection.connectDB(dataToConnectDB.get(0), dataToConnectDB.get(1), dataToConnectDB.get(2),
				dataToConnectDB.get(3));

	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	/**
	 * updateTable Method that create a new object and add it to the table
	 * 
	 * @param data
	 */
	public void updateTable(ArrayList<String> data) {
		boolean flag = true;
		Client client1 = new Client(data.get(0), data.get(1), data.get(2));

		for (Client x : ProjectServerController.list) {
			if (x.getIP().equals(client1.getIP())) {
				flag = false;
				ProjectServerController.list.set(ProjectServerController.list.indexOf(x), client1);
			}
		}
		if (flag)
			ProjectServerController.list.add(client1);

	}

	/**
	 * Method that sends the connecting data to the MySQLConnection
	 * 
	 * @param data
	 */
	public void setArray(ArrayList<String> data) {

		dataToConnectDB = data;
	}

}
