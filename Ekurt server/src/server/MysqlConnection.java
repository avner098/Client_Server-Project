package server;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import common.Massage;

/**
 * A class that connects the server to the database and you can insert, update
 * and import information from the database
 * 
 * @author
 *
 */
public class MysqlConnection {

	/**
	 * Connection to the DB
	 */
	static Connection conn;

	/**
	 * the order number that we are working on
	 */
	public static String orderNumber;

	public static ArrayList<String> able2 = new ArrayList<>();
	/**
	 * machine name that we are working on
	 */
	static String machineName;

	/**
	 * Method that connects the Database to server
	 * 
	 * @param DBname
	 * @param username
	 * @param password
	 * @param ip
	 */
	public static void connectDB(String DBname, String username, String password, String ip) {
		try {
			// Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			conn = DriverManager.getConnection(DBname, username, password);
			System.out.println("SQL connection succeed");

		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public static String getOrderNUmner() {
		return orderNumber;
	}

	/**
	 * method that imports the user table
	 */
	public static void importUsers() {
		try {
			PreparedStatement importtouser = conn.prepareStatement("INSERT INTO users SELECT * FROM importtouser ;");
			importtouser.executeUpdate();

			PreparedStatement delete = conn.prepareStatement("DELETE FROM importtouser ;");
			delete.executeUpdate();
			System.out.println("All users were imported successfully");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * method that exports the user table
	 */
	public static void exportUsers() {
		try {
			PreparedStatement importtouser = conn.prepareStatement("INSERT INTO importtouser  SELECT * FROM  users;");
			importtouser.executeUpdate();

			PreparedStatement delete = conn.prepareStatement("DELETE FROM users ;");
			delete.executeUpdate();

			System.out.println("All users were exported successfully");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * method that inserts a new file into the file table
	 * 
	 * @param fileName
	 * @param inputStrem
	 */
	public static void updateFile(String fileName, InputStream inputStrem) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO files values(?,?);");
			ps.setString(1, fileName);
			ps.setBlob(2, inputStrem);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A method that updates the inventory table with the inventory it receives
	 * according to the name of the machine
	 * 
	 * @param data
	 */
	public static void updateInventory(ArrayList<String> data) {

		try {
			String machinName = data.get(0);
			String qury = "update inventory set ";
			data.remove(0);
			ArrayList<String> products = new ArrayList<>();
			ArrayList<String> amount = new ArrayList<>();
			int cnt = 0;

			for (String x : data) {
				if (cnt < (data.size() / 2)) {
					products.add(x);
				} else
					amount.add(x);
				cnt++;
			}

			for (int i = 0; i < products.size(); i++) {
				if (i != products.size() - 1) {
					// qury += "?,";
					qury += products.get(i) + " = ? ,";
				} else {
					// qury += "?";
					qury += products.get(i) + " = ? ";
				}
			}
			qury += " where machine_name = ?";
			PreparedStatement ps = conn.prepareStatement(qury);

			cnt = 1;
			for (String x : amount) {
				ps.setString(cnt, amount.get(cnt - 1));
				cnt++;
			}

			ps.setString(cnt, machinName);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * method that returns all the prices of the products
	 * 
	 * @return products prices
	 */
	public static ArrayList<String> getprices() {
		ArrayList<String> data = new ArrayList<>();
		try {
			PreparedStatement getPrices = conn.prepareStatement("SELECT price FROM products");
			ResultSet rs = getPrices.executeQuery();

			while (rs.next()) {
				data.add(rs.getInt("price") + "");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * method that returns all machine names found in a certain region
	 * 
	 * @param region
	 * @return List of machine names
	 */
	public static ArrayList<String> getMachineByRegion(String region) {
		ArrayList<String> data = new ArrayList<>();
		try {
			PreparedStatement getMachineName = conn
					.prepareStatement("SELECT machine_name FROM machine where region = ? ");
			getMachineName.setString(1, region);
			ResultSet rs = getMachineName.executeQuery();

			while (rs.next()) {
				data.add(rs.getString("machine_name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(region);
		System.out.println(data);
		return data;
	}

	/**
	 * method that registers customers as subscribers
	 * 
	 * @param data costumer details
	 * @return Customer Status Whether the registration has been received or not
	 */
	public static String subscriberRegisrtion(ArrayList<String> data) {
		try {
			ArrayList<String> userinfo = new ArrayList<>();
			String usernamepending = "";
			String usersubscriber = "";
			boolean isUser = true;

			PreparedStatement userNameCheck = conn.prepareStatement(
					"SELECT first_name,last_name,ID,phone_number,email,credit_card_number FROM costumers where username = ? AND ID = ?");
			userNameCheck.setString(1, data.get(0));
			userNameCheck.setString(2, data.get(1));
			ResultSet rs = userNameCheck.executeQuery();

			PreparedStatement costumerNameCheck = conn
					.prepareStatement("SELECT UserName FROM userspendingforapproval where UserName = ? AND ID = ?");
			costumerNameCheck.setString(1, data.get(0));
			costumerNameCheck.setString(2, data.get(1));
			ResultSet rs1 = costumerNameCheck.executeQuery();

			PreparedStatement subscriberNameCheck = conn
					.prepareStatement("SELECT username FROM subscriber where username = ? AND ID = ?");
			subscriberNameCheck.setString(1, data.get(0));
			subscriberNameCheck.setString(2, data.get(1));
			ResultSet rs2 = subscriberNameCheck.executeQuery();

			while (rs.next()) {
				userinfo.add(rs.getString("first_name"));
				userinfo.add(rs.getString("last_name"));
				userinfo.add(rs.getString("ID"));
				userinfo.add(rs.getString("phone_number"));
				userinfo.add(rs.getString("email"));
				userinfo.add(rs.getString("credit_card_number"));

			}
			while (rs1.next()) {
				usernamepending += rs1.getString("UserName");
			}

			while (rs2.next()) {
				usersubscriber += rs2.getString("username");
			}

			for (String x : userinfo) {
				if (x == null || x.equals("")) {
					isUser = false;
				}
			}

			if (usersubscriber.length() > 0) {
				return "alreadysub";
			} else if (usernamepending.length() > 0) {
				return "ispending";
			} else if (!isUser) {
				return "notAuser";
			} else if (isUser && usersubscriber.length() == 0 && usernamepending.length() == 0) {

				PreparedStatement psInsert = conn.prepareStatement("insert into subscriber values(?,?,?,?,?,?,?,?,?)");

				psInsert.setString(1, userinfo.get(0));
				psInsert.setString(2, userinfo.get(1));
				psInsert.setString(3, userinfo.get(2));
				psInsert.setString(4, userinfo.get(3));
				psInsert.setString(5, userinfo.get(4));
				psInsert.setString(6, userinfo.get(5));
				psInsert.setString(7, data.get(0));
				psInsert.setInt(8, 20);
				psInsert.setInt(9, 0);
				psInsert.executeUpdate();
				return "OK";
			} else {
				return "notok";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "notok";
	}

	/**
	 * A method that returns whether the customer is a registered subscriber
	 * 
	 * @param data costumer details
	 * @return Subscription status
	 */
	public static ArrayList<String> getsubscriber(ArrayList<String> data) {

		ArrayList<String> sub = new ArrayList<>();
		String user = data.get(0);
		try {

			String usernamesub = null;

			PreparedStatement subdis = conn.prepareStatement("SELECT username FROM subscriber WHERE username=?;");

			subdis.setString(1, user);

			ResultSet rs = subdis.executeQuery();

			while (rs.next()) {
				usernamesub = rs.getString("username");
			}

			if (usernamesub == null) {
				sub.add("");
			} else {
				sub.add("Deferred payment");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sub;
	}

	/**
	 * method that returns the total price of the purchase after all the discounts
	 * it deserves
	 * 
	 * @param data A list of the username, the region of purchase and the price of
	 *             the order
	 * @return Total price after discount
	 */
	public static ArrayList<String> getdiscount(ArrayList<String> data) {
		/*
		 * double totaAfterDiscount = Integer.parseInt(total) -
		 * (double)Double.parseDouble(total)*(Double.parseDouble(discount)/100);
		 * 
		 */
		ArrayList<String> total = new ArrayList<>();
		String user = data.get(0);
		String region = data.get(1);
		String ordertotal = data.get(2);
		try {
			double totalAfter = 0;
			double discount = 0;
			ArrayList<Integer> sale = new ArrayList<>();

			PreparedStatement subdis = conn.prepareStatement("SELECT firstDiscount FROM subscriber WHERE username=?;");

			subdis.setString(1, user);

			ResultSet rs = subdis.executeQuery();

			while (rs.next()) {
				discount = (double) rs.getInt("firstDiscount") / 100;

			}

			totalAfter = Double.parseDouble(ordertotal) - Double.parseDouble(ordertotal) * discount;

			String st = String.format("%.3f", totalAfter);
			total.add(st);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	/**
	 * method that returns the summary of the order price and what the order
	 * contains
	 * 
	 * @param data
	 * @return order summary
	 */
	public static ArrayList<String> getOrderSummery(String data) {

		try {
			ArrayList<String> amount = new ArrayList<>();
			ArrayList<String> products = new ArrayList<>();

			String qury = "SELECT ";
			int counter = 1;
			int QuestionMarksToAdd = 0;

			PreparedStatement productName = conn.prepareStatement("SELECT name FROM products;");

			ResultSet rs = productName.executeQuery();

			while (rs.next()) {

				products.add(rs.getString("name"));
				QuestionMarksToAdd++;

				counter++;
			}

			for (int i = 0; i < QuestionMarksToAdd; i++) {
				if (i != QuestionMarksToAdd - 1) {
					// qury += "?,";
					qury += products.get(i) + ",";
				} else {
					// qury += "?";
					qury += products.get(i);
				}
			}

			qury += " FROM orders WHERE order_number=?;";

			PreparedStatement amountById = conn.prepareStatement(qury);

			amountById.setString(1, data);

			ResultSet rs1 = amountById.executeQuery();

			counter = 0;
			while (rs1.next()) {

				// inventory.add(rs1.getInt(counter)+"");
				for (String x : products) {
					amount.add(rs1.getInt(x) + "");
				}

				counter++;
			}

			PreparedStatement totalPrice = conn
					.prepareStatement("SELECT total_order_price FROM orders WHERE order_number=?;");

			totalPrice.setString(1, data);

			ResultSet rs2 = totalPrice.executeQuery();
			while (rs2.next()) {

				amount.add(0, rs2.getString("total_order_price"));

				counter++;
			}
			if (amount.size() == 0)
				return amount;

			for (String x : products) {
				amount.add(x);
			}

			return amount;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * method that requests from the database the status of the inventory in the
	 * machine by name and returns the inventory of the machine by products and the
	 * inventory of each product
	 * 
	 * @param name machine name
	 * @return inventory
	 */
	public static ArrayList<String> inventotyUpdateUpload(String name) {

		try {
			ArrayList<String> inventory = new ArrayList<>();
			ArrayList<String> products = new ArrayList<>();

			String qury = "SELECT ";
			int counter = 1;
			int QuestionMarksToAdd = 0;
			PreparedStatement productName = conn.prepareStatement("SELECT name FROM products;");

			ResultSet rs = productName.executeQuery();

			while (rs.next()) {

				products.add(rs.getString("name"));
				QuestionMarksToAdd++;

				counter++;
			}

			for (int i = 0; i < QuestionMarksToAdd; i++) {
				if (i != QuestionMarksToAdd - 1) {
					// qury += "?,";
					qury += products.get(i) + ",";
				} else {
					// qury += "?";
					qury += products.get(i);
				}
			}

			qury += " FROM inventory WHERE machine_name=?;";

			PreparedStatement inventoryById = conn.prepareStatement(qury);
			/*
			 * for (int j = 0; j < QuestionMarksToAdd; j++) { inventoryById.setString(j + 1,
			 * products.get(j));
			 * 
			 * }
			 */
			// inventoryById.setString(QuestionMarksToAdd + 1, id);
			inventoryById.setString(1, name);

			ResultSet rs1 = inventoryById.executeQuery();

			counter = 0;
			while (rs1.next()) {

				// inventory.add(rs1.getInt(counter)+"");
				for (String x : products) {
					inventory.add(rs1.getString(x));
				}

				counter++;
			}

			if (inventory.size() == 0)
				return inventory;

			for (String x : products) {
				inventory.add(x);
			}

			return inventory;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method that sends query to insert customer to Database
	 * 
	 * @param data
	 * @return
	 */
	public static String saveData(ArrayList<String> data) {
		try {
			String username = "";

			PreparedStatement costumerNameCheck = conn
					.prepareStatement("SELECT UserName FROM userspendingforapproval where UserName = ? OR ID = ?");
			costumerNameCheck.setString(1, data.get(6));
			costumerNameCheck.setString(2, data.get(2));
			ResultSet rs1 = costumerNameCheck.executeQuery();

			while (rs1.next()) {

				username += rs1.getString("UserName");
			}

			if (username.equals("") || username == null) {

				PreparedStatement psInsert = conn
						.prepareStatement("insert into userspendingforapproval values(?,?,?,?,?,?,?,?,?,?)");

				psInsert.setString(1, data.get(0));
				psInsert.setString(2, data.get(1));
				psInsert.setString(3, data.get(2));
				psInsert.setString(4, data.get(3));
				psInsert.setString(5, data.get(4));
				psInsert.setString(6, data.get(5));
				psInsert.setString(7, data.get(6));
				psInsert.setString(8, data.get(7));
				psInsert.setString(9, data.get(8));
				psInsert.setString(10, "frozen");
				psInsert.executeUpdate();
				return "OK";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "NotOK";
	}

	/**
	 * method that returns the customer's information according to ID
	 * 
	 * @param id
	 * @return
	 */
	public static ArrayList<String> printsubscriber(String id) {
		ArrayList<String> data = new ArrayList<>();

		try {

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM users where ID = ?;");
			ps.setString(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				data.add(rs.getString("first_name"));
				data.add(rs.getString("last_name"));
				data.add(rs.getString("ID"));
				data.add(rs.getString("phone_number"));
				data.add(rs.getString("email"));
				data.add(rs.getString("credit_card_number"));
				data.add(rs.getString("subscriber_number"));
			}

			// first_name, last_name, ID, phone_number, email, credit_card_number,
			// subscriber_number

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * method that updates the payment details in the orders table
	 * 
	 * @param data
	 */
	public static void toPay(ArrayList<String> data) {

		String deliveryOption = data.get(0);
		String address = data.get(1);
		String payOption = data.get(2);
		String orderId = data.get(3);
		String totalPrice = data.get(4);
		String username = data.get(5);
		try {

			PreparedStatement ps = conn.prepareStatement(
					"update orders set status = ? ,delivery=?,address=?,Payment_method=?,total_order_price=?   where order_number = ?");

			ps.setString(1, "paidUp");
			ps.setString(2, deliveryOption);
			ps.setString(3, address);
			ps.setString(4, payOption);
			ps.setString(5, totalPrice);
			ps.setString(6, orderId);
			ps.executeUpdate();

			PreparedStatement ps1 = conn.prepareStatement("update subscriber set firstDiscount=0  where username =?");

			ps1.setString(1, username);
			ps1.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that update customer data in Database
	 * 
	 * @param data
	 */
	public static void Update(ArrayList<String> data) {

		String ID;
		String card_number;
		String sub_number;

		try {

			ID = data.get(0);

			if (data.get(2).equals("")) {
				card_number = data.get(1);
				PreparedStatement ps = conn.prepareStatement("update users set credit_card_number = ?  where ID = ?");
				ps.setString(1, card_number);
				ps.setString(2, ID);
				ps.executeUpdate();
			} else if (data.get(1).equals("")) {
				sub_number = data.get(2);
				PreparedStatement ps = conn.prepareStatement("update users set subscriber_number = ?  where ID = ?");
				ps.setString(1, sub_number);
				ps.setString(2, ID);
				ps.executeUpdate();
			} else if (!data.get(1).equals("") && !data.get(2).equals("")) {
				card_number = data.get(1);
				sub_number = data.get(2);
				PreparedStatement ps = conn.prepareStatement(
						"update users set subscriber_number = ? , credit_card_number = ?  where ID = ?");
				ps.setString(1, sub_number);
				ps.setString(2, card_number);
				ps.setString(3, ID);
				ps.executeUpdate();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * method that updates the database that the user has logged out
	 * 
	 * @param userinfo
	 */
	public static void logout(ArrayList<String> userinfo) {
		try {

			PreparedStatement ps = conn
					.prepareStatement("update users set isloggedIn = ? where username = ? AND paswword = ?;");
			ps.setString(1, "0");
			ps.setString(2, userinfo.get(0));
			ps.setString(3, userinfo.get(1));

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * method that updates the database that the user logged into the system
	 * 
	 * @param userinfo
	 */
	public static void setTologged(ArrayList<String> userinfo) {
		try {

			PreparedStatement ps = conn
					.prepareStatement("update users set isloggedIn = ? where username = ? AND paswword = ?;");
			ps.setString(1, "1");
			ps.setString(2, userinfo.get(0));
			ps.setString(3, userinfo.get(1));

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * method that inserts a new sent message into the data base
	 * 
	 * @param data
	 */
	public static void sendMessges(ArrayList<String> data) {
		try {
			PreparedStatement ps = conn.prepareStatement("insert into messege values(null,?,?,?)");
			ps.setString(1, data.get(0));
			ps.setString(2, data.get(1));
			ps.setString(3, data.get(2));

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Delivery -> costumer(All) area manager -> operation worker(All) operation
	 * worker -> area manager(All)
	 * 
	 * system -> area manager ..Inventory of product X has fallen below a threshold
	 * level in inventory Y system -> costumer system -> subscriber
	 */

	/**
	 * method that checks which types of users the client can send a message to
	 * 
	 * @param data
	 * @return list of user roles that you can send a message to
	 */
	public static ArrayList<String> userstomessege(ArrayList<String> data) {
		ArrayList<String> users = new ArrayList<>();

		try {

			if (data.get(1).equals("opertion worker")) {
				PreparedStatement ps = conn.prepareStatement("SELECT username FROM users where role = ?;");
				ps.setString(1, "regional Manager");

				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					users.add(rs.getString("username"));

				}
			} else if (data.get(1).equals("regional Manager")) {
				PreparedStatement ps = conn.prepareStatement("SELECT username FROM users where role = ?;");
				ps.setString(1, "opertion worker");

				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					users.add(rs.getString("username"));

				}
			} else if (data.get(1).equals("Carrier")) {
				PreparedStatement ps = conn.prepareStatement("SELECT username FROM users where role = ?;");
				ps.setString(1, "customer");

				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					users.add(rs.getString("username"));

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	/**
	 * method that returns the region by machine name
	 * 
	 * @param data machine name
	 * @return region
	 */
	public static ArrayList<String> getRegion(ArrayList<String> data) {
		ArrayList<String> region = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT region FROM machine where machine_name = ?;");
			ps.setString(1, data.get(0));

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				region.add(rs.getString("region"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return region;
	}

	/**
	 * method that returns all messages sent so far to the client
	 * 
	 * @param data
	 * @return List of all messages
	 */
	public static ArrayList<String> getmesseges(ArrayList<String> data) {

		ArrayList<String> messeges = new ArrayList<>();

		try {
			PreparedStatement ps = conn.prepareStatement("SELECT from_user,messege FROM messege where to_user = ?;");
			ps.setString(1, data.get(0));

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				messeges.add(rs.getString("from_user"));
				messeges.add(rs.getString("messege"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return messeges;
	}

	/**
	 * method that updates the database that the replenishment service call has
	 * closed
	 * 
	 * @param data
	 */
	public static void closeRestokeCall(ArrayList<String> data) {
		try {

			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String strDate = dateFormat.format(date);

			PreparedStatement ps = conn
					.prepareStatement("update restoke_calls set status = ?,dateTime=? where restoke_number = ?;");
			ps.setString(1, "Close");
			ps.setString(2, strDate);
			ps.setString(3, data.get(0));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * method that returned all open service calls
	 * 
	 * @return
	 */
	public static ArrayList<String> getOpenRestoke() {
		ArrayList<String> restoke = new ArrayList<>();

		try {
			PreparedStatement ps = conn.prepareStatement("SELECT  * FROM restoke_calls;");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("status").equals("Open")) {
					restoke.add(rs.getString("restoke_number"));
					restoke.add(rs.getString("machine_name"));
					restoke.add(rs.getString("region"));
					restoke.add(rs.getString("products_to_restoke"));
					restoke.add(rs.getString("Low_bar"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return restoke;
	}

	/**
	 * method that receives the username and password verifies against the database
	 * that he is a registered user and if so returns his details
	 * 
	 * @param userinfo username and password
	 * @return user details
	 */
	public static ArrayList<String> login(ArrayList<String> userinfo) {

		ArrayList<String> data = new ArrayList<>();

		try {
			if (userinfo.size() == 0) {
				data.add("EmptyInput");
				return data;
			}
			if (userinfo.get(0) == null) {
				data.add("NullInput");
				return data;
			}

			if (userinfo.get(1) == null) {
				data.add("NullInput");
				return data;
			}

		} catch (NullPointerException e) {

			data.add("NullInput");
			return data;
		}

		try {

			PreparedStatement ps = conn.prepareStatement(
					"SELECT first_name,last_name,role,isloggedIn,username,paswword,region,ID FROM users where username = ? AND paswword = ? ;");
			ps.setString(1, userinfo.get(0));
			ps.setString(2, userinfo.get(1));

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				data.add(rs.getString("role"));
				data.add(rs.getString("first_name"));
				data.add(rs.getString("last_name"));
				data.add(rs.getString("isloggedIn"));
				data.add(rs.getString("username"));
				data.add(rs.getString("paswword"));
				data.add(rs.getString("region"));
				data.add(rs.getString("ID"));

			}
			if (data.size() == 0) {
				data.add("NotExist");
			} else {
				if (data.get(3).equals("1")) {
					data.clear();
					data.add("alreadyLogged");
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data;
	}

	public static InputStream getImage(ArrayList<String> data) {
		InputStream inputStrem = null;
		try {

			Massage msg = new Massage();

			PreparedStatement ps = conn.prepareStatement("SELECT  file FROM files where filename= ?;");
			ps.setString(1, data.get(0));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				inputStrem = rs.getBinaryStream("file");
			}

			// System.out.println(inputStrem);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inputStrem;
	}

	public static ArrayList<String> getMachines() {
		ArrayList<String> machines = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement("select machine_name from inventory");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				machines.add(result.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return machines;

	}

	/**
	 * A method that updates the database at the time of placing the order
	 * 
	 * @param time
	 * @throws SQLException
	 */
	public static void getTime(String time) {
		try {
			PreparedStatement statement = conn.prepareStatement(
					"update orders set dateTime = '" + time + "' where order_number ='" + orderNumber + "'");
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A method that sends the information for each product to be updated before
	 * placing an order.
	 * 
	 * @throws SQLException
	 */
	public static ArrayList<String> productInfo() {
		ArrayList<String> products1 = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT name, serial_number, price from products");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				products1.add(result.getString("name"));
				products1.add(result.getString("serial_number"));
				products1.add(result.getString("price"));
			}
			for (String x : products1) {
				System.out.println(x);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products1;
	}

	public static void oldOrder(String OrderNum) {
		orderNumber = OrderNum;
	}

	/**
	 * A method that sends the information for all existing orders in the database
	 * to update order management.
	 * 
	 * @throws SQLException
	 */
	public static ArrayList<String> manage_order(String user) {
		ArrayList<String> orders = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement(
					"SELECT order_number, delivery, address, Payment_method,total_order_price, cola, sprite, fanta, OrangeJuice, natureValley, waffles, doritos, bamba, status,dateTime,machine FROM orders WHERE username = '"
							+ user + "'");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				orders.add(result.getString(1));
				orders.add(result.getString(2));
				orders.add(result.getString(3));
				orders.add(result.getString(4));
				orders.add(result.getString(5));
				orders.add(result.getString(6));
				orders.add(result.getString(7));
				orders.add(result.getString(8));
				orders.add(result.getString(9));
				orders.add(result.getString(10));
				orders.add(result.getString(11));
				orders.add(result.getString(12));
				orders.add(result.getString(13));
				orders.add(result.getString(14));
				orders.add(result.getString(15));
				orders.add(result.getString(16));
				for (String x : orders) {
					System.out.println(x);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	/**
	 * A method that imports from the database the amount of products from each
	 * machine.
	 * 
	 * @throws SQLException
	 */
	public static ArrayList<String> machineInvatory(String machine) {
		ArrayList<String> able = new ArrayList<>();
		machineName = machine;
		try {
			PreparedStatement add = conn.prepareStatement(
					"update orders set machine = '" + machineName + "'  where order_number = '" + orderNumber + "'");
			add.executeUpdate();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT cola, sprite, fanta, OrangeJuice, natureValley, waffles, doritos, bamba FROM inventory WHERE machine_name = '"
							+ machine + "'");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				able.add(result.getString("cola"));
				able.add(result.getString("sprite"));
				able.add(result.getString("fanta"));
				able.add(result.getString("OrangeJuice"));
				able.add(result.getString("natureValley"));
				able.add(result.getString("waffles"));
				able.add(result.getString("doritos"));
				able.add(result.getString("bamba"));
			}
			able2 = able;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return able;

	}

	/**
	 * The method updates the data base according to the machine updates the
	 * products that need to be added back after the customer has canceled
	 * 
	 * @throws SQLException
	 */
	public static void timeOut(ArrayList<String> infor) {
		String cola = "0";
		String sprite = "0";
		String fanta = "0";
		String orange = "0";
		String nature = "0";
		String waffle = "0";
		String doritos = "0";
		String bamba = "0";
		try {
			PreparedStatement statement1 = conn.prepareStatement(
					"SELECT cola, sprite, fanta, OrangeJuice, natureValley, waffles, doritos, bamba FROM inventory WHERE machine_name = '"
							+ infor.get(1) + "'");
			ResultSet result1 = statement1.executeQuery();
			while (result1.next()) {
				cola = result1.getString(1);
				sprite = result1.getString(2);
				fanta = result1.getString(3);
				orange = result1.getString(4);
				nature = result1.getString(5);
				waffle = result1.getString(6);
				doritos = result1.getString(7);
				bamba = result1.getString(8);
			}
			PreparedStatement statement = conn.prepareStatement(
					"SELECT cola, sprite, fanta, OrangeJuice, natureValley, waffles, doritos, bamba FROM orders WHERE order_number = '"
							+ infor.get(0) + "'");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				cola = (Integer.parseInt(result.getString(1)) + Integer.parseInt(cola)) + "";
				sprite = (Integer.parseInt(result.getString(2)) + Integer.parseInt(sprite)) + "";
				fanta = (Integer.parseInt(result.getString(3)) + Integer.parseInt(fanta)) + "";
				orange = (Integer.parseInt(result.getString(4)) + Integer.parseInt(orange)) + "";
				nature = (Integer.parseInt(result.getString(5)) + Integer.parseInt(nature)) + "";
				waffle = (Integer.parseInt(result.getString(6)) + Integer.parseInt(waffle)) + "";
				doritos = (Integer.parseInt(result.getString(7)) + Integer.parseInt(doritos)) + "";
				bamba = (Integer.parseInt(result.getString(8)) + Integer.parseInt(bamba)) + "";
			}
			PreparedStatement importtouser1 = conn.prepareStatement("update inventory set cola = '" + cola
					+ "', sprite = '" + sprite + "', fanta = '" + fanta + "',OrangeJuice =  '" + orange
					+ "', natureValley = '" + nature + "',waffles= '" + waffle + "',doritos='" + doritos + "',bamba='"
					+ bamba + "' where machine_name = '" + infor.get(1) + "'");
			importtouser1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement importtouser = conn
					.prepareStatement("delete from orders where order_number = '" + orderNumber + "'");
			importtouser.executeUpdate();

			System.out.println("deleted order");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * The method generates a new order line for a new order.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static void createOrderRow(ArrayList<String> data) {
		boolean flag = false;
		orderNumber = data.get(1);
		String username = data.get(1);
		int num = 0;
		String[] part = null;

		try {
			PreparedStatement statement1 = conn.prepareStatement("SELECT username FROM orders");
			ResultSet result1 = statement1.executeQuery();
			while (result1.next()) {
				if (result1.getString("username").equals(username)) {
					flag = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (flag == false) {
			try {
				// create a new order row in the data base, table orders.
				orderNumber = orderNumber + "0";
				PreparedStatement add1 = conn.prepareStatement(
						"INSERT INTO orders(username, order_number, delivery, address, Payment_method, total_order_price, cola, sprite, fanta, OrangeJuice, natureValley, waffles, doritos, bamba, status,machine) values('"
								+ username + "','" + orderNumber + "','0','0','0',0,0,0,0,0,0,0,0,0,'in progress','"
								+ machineName + "');");
				add1.executeUpdate();
				System.out.println("new order added");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else { // create a new order number.
			PreparedStatement statement;
			try {
				statement = conn
						.prepareStatement("SELECT order_number FROM orders WHERE username = '" + username + "'");

				ResultSet result = statement.executeQuery();
				while (result.next()) {
					String str = result.getString("order_number");
					part = str.split("(?<=\\D)(?=\\d)");
					if (num < Integer.parseInt(part[1])) {
						num = Integer.parseInt(part[1]);
					}

				}
				num = num + 1;
				orderNumber = part[0] + num;

			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				PreparedStatement add = conn.prepareStatement(
						"INSERT INTO orders(username, order_number, delivery, address, Payment_method, total_order_price, cola, sprite, fanta,OrangeJuice, natureValley, waffles, doritos, bamba,status,machine) values('"
								+ username + "','" + orderNumber + "','0','0','0',0,0,0,0,0,0,0,0,0,'in progras','"
								+ machineName + "');");
				add.executeUpdate();
				System.out.println("new order added");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The method updates the data base on selected products and updates the
	 * inventory for each such product according to the machine.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String update_DB(ArrayList<String> data) {

		String amount_product_inventory = "";
		String productName = data.get(2);
		String product = data.get(1);
		String totalPrice = data.get(0);
		String username = data.get(3);
		int total_Price1 = 0;
		int productInt = 0;
		int i = 0;
		try {
			if (productName.equals("cola")) {
				i = 0;
			}
			if (productName.equals("sprite")) {
				i = 1;
			}
			if (productName.equals("fanta")) {
				i = 2;
			}
			if (productName.equals("orengeJuise")) {
				i = 3;
			}
			if (productName.equals("natureValley")) {
				i = 4;
			}
			if (productName.equals("waffles")) {
				i = 5;
			}
			if (productName.equals("doritos")) {
				i = 6;
			}
			if (productName.equals("bamba")) {
				i = 7;
			}
			PreparedStatement statement3 = conn.prepareStatement(
					"SELECT " + productName + " FROM inventory WHERE machine_name = '" + machineName + "'"); // return
																												// the
																												// number
																												// of
																												// product.
			ResultSet result3 = statement3.executeQuery();
			while (result3.next()) {
				amount_product_inventory = result3.getString(productName);
			}
			if (Integer.parseInt(product) > Integer.parseInt(amount_product_inventory)) {
				return "tooMuch";
			} // return tooMuch if the customer ask for more products then the machine has.
			int update_product = Integer.parseInt(amount_product_inventory) - Integer.parseInt(product);
			PreparedStatement statement1 = conn.prepareStatement("UPDATE inventory SET " + productName + " = "
					+ update_product + " where machine_name = '" + machineName + "'"); // update the product amount.
			statement1.executeUpdate();
			PreparedStatement statement = conn.prepareStatement("SELECT total_order_price," + productName
					+ " FROM orders WHERE order_number='" + orderNumber + "'");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				productInt = (result.getInt(productName)) + Integer.valueOf(product);
				total_Price1 += result.getInt("total_order_price") + Integer.valueOf(totalPrice);
			}
			PreparedStatement create = conn.prepareStatement("UPDATE orders SET " + productName + " = " + productInt
					+ ", total_order_price= " + total_Price1 + " where order_number='" + orderNumber + "'");// update
																											// the
																											// total
																											// price
																											// of the
																											// order.
			create.executeUpdate();
			conn.prepareStatement("SELECT total_order_price FROM orders WHERE order_number='" + orderNumber + "'");
			ResultSet result2 = statement.executeQuery();
			while (result2.next()) {
				total_Price1 = result2.getInt("total_order_price");
			}
			totalPrice = total_Price1 + "";
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalPrice;
	}

	/**
	 * method to retrieve field from deliveries table given the status is not
	 * finished and wiht given region
	 * 
	 * @param region
	 * @return
	 */
	public static ArrayList<String> printDelivery(String region) {
		ArrayList<String> data = new ArrayList<>();

		try {

			PreparedStatement ps = conn
					.prepareStatement("SELECT * FROM deliveries where region=? AND Status!='Finished';");
			ps.setString(1, region);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				data.add(rs.getString("ordernumber"));
				data.add(rs.getString("address"));
				data.add(rs.getString("customerName"));
				data.add(rs.getString("customerPhoneNumber"));
				data.add(rs.getString("DeliveryDate"));
				data.add(rs.getString("Status"));
			}
			// first_name, last_name, ID, phone_number, email, credit_card_number,
			// subscriber_number

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;

	}

	/**
	 * method to generate generic query based on the input given
	 * 
	 * @param info
	 */
	public static void ChangeDeliveryStatus(ArrayList<String> info) {

		try {

			PreparedStatement ps = conn.prepareStatement(info.get(0));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * method to generate generic query based on the input given
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<String> AllQueryOperate(ArrayList<String> info) {
		ArrayList<String> data = new ArrayList<>();

		try {
			PreparedStatement ps = conn.prepareStatement(info.get(0));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString("ID");
				data.add(id);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;

	}

	/**
	 * method to insert into sale table values from array list
	 * 
	 * @param info
	 * @throws SQLException
	 */
	public static void addSale(ArrayList<String> info) {
		try {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO sale(id,region,percentage,description,status,Request_Status,product_name) VALUES (?,?,?,?,0,0,?);");
			ps.setString(1, info.get(0));
			ps.setString(2, info.get(1));
			ps.setInt(3, Integer.parseInt(info.get(2)));
			ps.setString(4, info.get(3));
			ps.setString(5, info.get(4));
			int rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method to update the sale status
	 * 
	 * @param info
	 * @throws SQLException
	 */
	public static void changeStatusSale(ArrayList<String> info) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE sale SET Status=?,Request_Status=0 where id=?;");
			ps.setString(1, info.get(1));
			ps.setString(2, info.get(0));
			int rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
		/**
	 * method to retrieve from sale all data with given region
	 * 
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<String> showsale(ArrayList<String> info) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM sale where region=?;");
			ps.setString(1, info.get(1));
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				data.add(rs.getString("id"));
				data.add(rs.getString("region"));
				data.add(rs.getString("percentage"));
				data.add(rs.getString("description"));
				data.add(rs.getString("status"));
				data.add(rs.getString("product_name"));
				data.add(rs.getString("Request_Status"));
			}
		}

		catch (SQLException e) {

			e.printStackTrace();
		}
		return data;

	}
	
	
	/**
	 * method to change the sale request status
	 * 
	 * @param info
	 * @throws SQLException
	 */
	public static void changeRequestStatusSale(ArrayList<String> info) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE sale SET Request_Status=? where id=?;");
			ps.setString(1, info.get(1));
			ps.setString(2, info.get(0));
			int rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method to get from sale table id, region, percentage, description, status
	 * when Request_Status='1' and appropriate region
	 * 
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<String> GetOpenTaskForMarkting(ArrayList<String> info) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT id, region, percentage, description,product_name, status FROM sale WHERE Request_Status='1' AND region=?;");
			ps.setString(1, info.get(1));
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				data.add(rs.getString("id"));
				data.add(rs.getString("region"));
				data.add(rs.getString("percentage"));
				data.add(rs.getString("description"));
				data.add(rs.getString("status"));
				data.add(rs.getString("product_name"));
			}
		}

		catch (SQLException e) {

			e.printStackTrace();
		}
		return data;

	}

	/**
	 * method to get all the sale info with given region input
	 * 
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<String> showsaleForRequest(ArrayList<String> info) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM sale where region=?;");
			ps.setString(1, info.get(1));
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				data.add(rs.getString("id"));
				data.add(rs.getString("region"));
				data.add(rs.getString("percentage"));
				data.add(rs.getString("description"));
				data.add(rs.getString("status"));
				data.add(rs.getString("Request_Status"));
				data.add(rs.getString("product_name"));
			}
		}

		catch (SQLException e) {

			e.printStackTrace();
		}
		return data;

	}

	/**
	 * method that make a query to get all the order from orders table that order
	 * number is different from order number at deliveries table
	 * 
	 * @param info
	 */
	public static void iniliazeDeliveryTable(ArrayList<String> info) {
		ArrayList<String> info_from_order = new ArrayList<String>();
		ArrayList<String> user_result = new ArrayList<String>();
		String name = "";
		String PhoneNumber = "";

		String region = "";
		try {
			PreparedStatement ps = conn
					.prepareStatement("	SELECT o.username, o.order_number, o.address , o.machine \r\n"
							+ "	FROM orders o\r\n" + "	LEFT JOIN deliveries d ON o.order_number = d.ordernumber\r\n"
							+ "	WHERE d.ordernumber IS NULL  AND o.delivery='Shiping' AND o.status='paidUp';");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				info_from_order.add(rs.getString("username"));
				info_from_order.add(rs.getString("order_number"));
				info_from_order.add(rs.getString("address"));
				info_from_order.add(rs.getString("machine"));

			}
			System.out.println(info_from_order);
			for (int i = 0; i < info_from_order.size(); i += 4) {
				user_result = getUserNameInfo(info_from_order.get(i));
				region = getRegionFromMachine(info_from_order.get(i + 3));
				if (user_result.isEmpty()) { // if customer doesn't appear in db we will generate phone number and use
												// user name as name
					System.out.println("not found Customer on DB Generate PhoneNumber");
					name = info_from_order.get(i);
					PhoneNumber = generatePhoneNumber();
				} else {
					name = user_result.get(0);
					PhoneNumber = user_result.get(1);
				}
				PreparedStatement ps1 = conn.prepareStatement(
						"INSERT INTO deliveries VALUES(?,?,?,?,'N/A','Waiting_Delivery_Approval',?);");
				ps1.setString(1, info_from_order.get(i + 1));
				ps1.setString(2, info_from_order.get(i + 2));
				ps1.setString(3, name);
				ps1.setString(4, PhoneNumber);
				ps1.setString(5, region);
				int res = ps1.executeUpdate();

			}
		}

		catch (SQLException e) {

			e.printStackTrace();
		}

	}

	/**
	 * method to generate phone number for customer that doesn't appear on the Data
	 * Base
	 * 
	 * @return
	 */
	public static String generatePhoneNumber() {
		Random random = new Random();
		StringBuilder phoneNumber = new StringBuilder();
		phoneNumber.append("05");
		for (int i = 0; i < 8; i++) {
			phoneNumber.append(random.nextInt(10));
		}
		return phoneNumber.toString();
	}

	/**
	 * helper method that get the first name and last name and phone number from
	 * user table with a given user name input
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	private static ArrayList<String> getUserNameInfo(String username) {

		ArrayList<String> result = new ArrayList<String>();
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT users.first_name, users.last_name ,users.phone_number FROM project.users where username=?;");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.add(rs.getString("first_name") + " " + rs.getString("last_name"));
				result.add(rs.getString("phone_number"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * helper method to get region from machine with given machine name input.
	 * 
	 * @param region
	 * @return
	 * @throws SQLException
	 */
	private static String getRegionFromMachine(String region) {

		String Region = "";
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT region from machine where machine_name=?;");
			ps.setString(1, region);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Region = rs.getString("region");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Region;

	}

	/**
	 * method that use query to get from table machine the Time_Delivery_Duration
	 * using the order number input
	 * 
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<String> getTimeDeliveryDuration(ArrayList<String> info) {

		// from Ordernumber get the location and then get the time_delivery_duration
		ArrayList<String> result1 = new ArrayList<String>();
		ArrayList<String> result2 = new ArrayList<String>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT machine FROM orders where order_number=?;");
			ps.setString(1, info.get(0));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result1.add(rs.getString("machine"));
			}
			PreparedStatement ps1 = conn
					.prepareStatement("SELECT Time_Delivery_Duration FROM machine where location=?;");
			ps1.setString(1, result1.get(0));
			ResultSet rs1 = ps1.executeQuery();
			while (rs1.next()) {
				result2.add(rs1.getString("Time_Delivery_Duration"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result2;

	}

	/**
	 * method that update the status and the delivery date using order number input
	 * 
	 * @param info
	 * @throws SQLException
	 */
	public static void changeDeliveryStatusWithDate(ArrayList<String> info) {
		try {
			PreparedStatement ps = conn
					.prepareStatement("update deliveries set Status =?,DeliveryDate=? where ordernumber=?;");
			ps.setString(1, info.get(1));
			ps.setString(2, info.get(0));
			ps.setString(3, info.get(2));
			int rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * method to add automatic message if order status is arrived
	 */
	public static void setMessageFromStatus() {
		ArrayList<String> username_customer = new ArrayList<String>();
		String username_Carrier = "";
		ArrayList<String> order_number = new ArrayList<String>();
		String region = "";
		ArrayList<String> message = new ArrayList<String>();
		try {
			PreparedStatement ps1 = conn
					.prepareStatement("SELECT order_number,username FROM orders where status='Arrived';");
			ResultSet rs1 = ps1.executeQuery();
			while (rs1.next()) {
				username_customer.add(rs1.getString("username"));
				order_number.add(rs1.getString("order_number"));
			}
			for (int i = 0; i < username_customer.size(); i++) {
				PreparedStatement ps2 = conn.prepareStatement("SELECT region FROM  deliveries where ordernumber=?;");
				ps2.setString(1, order_number.get(i));
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					region = rs2.getString("region");
				}
				PreparedStatement ps4 = conn
						.prepareStatement("SELECT username FROM users where role='Carrier' AND region=?;");
				ps4.setString(1, region);
				ResultSet rs4 = ps4.executeQuery();
				while (rs4.next()) {
					username_Carrier = rs4.getString("username");
				}

				PreparedStatement ps = conn
						.prepareStatement("select messege from messege where from_user=? and to_user=?;");
				ps.setString(1, username_customer.get(i));
				ps.setString(2, username_Carrier);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					message.add(rs.getString("messege"));
				}
				if (!message.contains("Order " + order_number.get(i) + " has Arrived")) {
					PreparedStatement ps3 = conn.prepareStatement("INSERT INTO messege VALUES(null,?,?,?);");
					ps3.setString(1, username_customer.get(i));
					ps3.setString(2, username_Carrier);
					ps3.setString(3, "Order " + order_number.get(i) + " has Arrived");
					ps3.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method that make query to check if message was sent from customer to carrier
	 * to make sure the delivery arrived.
	 * 
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<String> checkmessagequery(ArrayList<String> data) {
		// data[0]=ordernumber from delvieries
		// data[1]=username of worker
		ArrayList<String> checker = new ArrayList<>();
		String username = "";
		String status = "";
		// from ordernumber get username in orders table
		PreparedStatement ps1;
		try {
			ps1 = conn.prepareStatement("SELECT username,status FROM orders where order_number=?;");
			ps1.setString(1, data.get(0));
			ResultSet rs1 = ps1.executeQuery();
			while (rs1.next()) {
				username = rs1.getString("username");
			}

			PreparedStatement ps = conn
					.prepareStatement("SELECT * FROM messege WHERE from_user = ? AND to_user = ? AND messege= ?");
			ps.setString(1, username);
			ps.setString(2, data.get(1));
			ps.setString(3, "Order " + data.get(0) + " has Arrived");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				checker.add(String.valueOf(rs.getInt("messege_number")));
				checker.add(rs.getString("from_user"));
				checker.add(rs.getString("to_user"));
				checker.add(rs.getString("messege"));
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}

		return checker;
	}

	/**
	 * get all product from db
	 * 
	 * @return
	 */
	public static ArrayList<String> getAllProduct() {
		ArrayList<String> output = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT name FROM project.products;");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				output.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}

	/**
	 * get product name with given region and product_name
	 * 
	 * @param data
	 * @return
	 */
	public static ArrayList<String> checkifProductAppear(ArrayList<String> data) {
		ArrayList<String> output = new ArrayList<>();

		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT product_name FROM project.sale where region=? and product_name=?;");
			ps.setString(1, data.get(2));
			ps.setString(2, data.get(1));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				output.add(rs.getString("product_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
	}

	/**
	 * method that insert into table message the appropriate message with the
	 * estimated date delivery also update the status in orders.
	 * 
	 * @param info
	 * @throws SQLException
	 */
	public static void SendDeliveryDateToUser(ArrayList<String> info) {
		try {
			ArrayList<Integer> find_max = new ArrayList<Integer>();
			int max = 0;
			String username = "";
			PreparedStatement ps = conn.prepareStatement("SELECT messege_number FROM project.messege;");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				find_max.add(rs.getInt("messege_number"));
			}
			for (int i = 0; i < find_max.size(); i++) {
				max = Math.max(max, find_max.get(i));
			}
			max = max + 1;// current max to insert in the table
			// finding the username given the ordernumber
			PreparedStatement ps1 = conn.prepareStatement("SELECT username FROM orders where order_number=?;");
			ps1.setString(1, info.get(1));
			ResultSet rs1 = ps1.executeQuery();
			while (rs1.next()) {
				username = rs1.getString("username");
			}
			PreparedStatement ps2 = conn.prepareStatement(
					"INSERT INTO messege (messege_number, from_user, to_user, messege) VALUES (?, ?, ?, ?);");
			ps2.setInt(1, max);
			ps2.setString(2, info.get(0));
			ps2.setString(3, username);
			ps2.setString(4, info.get(2));
			int rs2 = ps2.executeUpdate();
			PreparedStatement ps3 = conn.prepareStatement("UPDATE orders SET status = ? WHERE order_number = ?;");
			ps3.setString(1, info.get(2));
			ps3.setString(2, info.get(1));
			int rs3 = ps3.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method that update the status in orders and deliveries using the order number
	 * input
	 * 
	 * @param data
	 * @throws SQLException
	 */
	public static void closeOrderDelivery(ArrayList<String> data) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE deliveries SET Status = ? WHERE ordernumber = ?;");
			ps.setString(1, data.get(0));
			ps.setString(2, data.get(1));
			int rs = ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("UPDATE orders SET status = ? WHERE order_number = ?;");
			ps1.setString(1, data.get(0));
			ps1.setString(2, data.get(1));
			int rs1 = ps1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method the ask from the data base all the user that their role is user
	 * 
	 * @return list of all the users their role is user
	 */
	public static ArrayList<String> getUsers() {

		ArrayList<String> users = new ArrayList<String>();
		ArrayList<String> pendingUser = new ArrayList<String>();
		try {

			PreparedStatement ps = conn.prepareStatement("SELECT username FROM users where role=?;");

			ps.setString(1, "user");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				users.add(rs.getString("username"));
			}

			PreparedStatement pending = conn.prepareStatement("SELECT UserName FROM userspendingforapproval;");

			ResultSet rs1 = pending.executeQuery();

			while (rs1.next()) {
				pendingUser.add(rs1.getString("UserName"));
			}

			for (String name : pendingUser) {
				if (users.contains(name)) {
					users.remove(name);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * method that ask from the data base the information of the user
	 * 
	 * @param username user name in the data base that we want their information
	 * @return user information
	 */
	public static ArrayList<String> getUserinfo(String username) {
		ArrayList<String> user = new ArrayList<String>();
		try {

			PreparedStatement ps = conn.prepareStatement(
					"SELECT first_name, last_name, ID, username, paswword FROM users where username=?;");

			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				user.add(rs.getString("first_name"));
				user.add(rs.getString("last_name"));
				user.add(rs.getString("ID"));
				user.add(rs.getString("username"));
				user.add(rs.getString("paswword"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * method that set the low bar of given machine
	 * 
	 * @param info list of info (machineID,lowbar)
	 * @return feedback message to the client
	 */
	public static ArrayList<String> SetNewMinimumValue(ArrayList<String> info) {
		String machineID = info.get(0);
		String lowbar = info.get(1);
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE inventory SET Low_bar = ? WHERE machine_name = ?");
			ps.setString(1, lowbar);
			ps.setString(2, machineID);
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("UPDATE machine SET Low_bar = ? WHERE machine_name = ?");
			ps1.setString(1, lowbar);
			ps1.setString(2, machineID);
			ps1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<String> something = new ArrayList<>();
		something.add("Worked");
		return something;
	}

	/**
	 * method that ask from the data base all the id of machines by their region
	 * 
	 * @param info region
	 * @return list of machines in region
	 */
	public static ArrayList<String> getmachinesIDforRegion(ArrayList<String> info) {
		
		String region = info.get(0);
		ArrayList<String> data = new ArrayList<>();
		if (region != null) {
			try {
				PreparedStatement ps = conn.prepareStatement("SELECT machine_name FROM machine WHERE region = ?");
				ps.setString(1, region);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					data.add(rs.getString("machine_name"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			data.add(0, "empty");
		}
		return data;
	}

	/**
	 * method that ask from the data base for Minimum Value of given machine by
	 * machine_id
	 * 
	 * @param info machine_id
	 * @return Minimum Value of given machine
	 */
	public static ArrayList<String> getMinimumValue(ArrayList<String> info) {

		

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("SELECT Low_bar FROM inventory WHERE machine_name = ?;");
			ps.setString(1, info.get(0));

		} catch (SQLException e) {

			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		
		ArrayList<String> data = new ArrayList<>();

		try {
			while (rs.next()) {
				data.add(rs.getString("Low_bar"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		if (data.size() == 0) {
			data.add("empty");
		}
		return data;
	}

	/**
	 * method that ask from the data base for users that are waiting for approval
	 * 
	 * @return list of users that are waiting for approval
	 */
	public static ArrayList<String> getWaitingForApprovalUsers() {
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM userspendingforapproval;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		ArrayList<String> data = new ArrayList<>();

	
		try {
			while (rs.next()) {
				data.add(rs.getString("firstName"));
				data.add(rs.getString("lastName"));
				data.add(rs.getString("ID"));
				data.add(rs.getString("creditCardNumber"));
				data.add(rs.getString("PhoneNumber"));
				data.add(rs.getString("EmailAddress"));
				data.add(rs.getString("UserName"));
				data.add(rs.getString("Password"));
				data.add(rs.getString("Region"));
				data.add(rs.getString("status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;

	}

	/**
	 * method that ask from the data base for approved or declined user that want to
	 * be a customer
	 * 
	 * @param info list of (id ,status) of all the user we want to approve customer
	 *             or not
	 * @return feedback message to the client
	 */
	public static ArrayList<String> setWaitingForApprovalUsers(ArrayList<String> info) {
		for (String s : info) {
			String[] idStatus = s.split(",");
			String sID = idStatus[0];
			String sStatus = idStatus[1];
			
			if (sStatus.equals("Accept")) {
				PreparedStatement ps = null;
				try {
					ps = conn.prepareStatement(
							"SELECT firstName, lastName, ID, creditCardNumber, PhoneNumber, EmailAddress, UserName, Password, Region FROM userspendingforapproval WHERE ID = ?;");
					ps.setString(1, sID);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ResultSet rs = null;
				try {
					rs = ps.executeQuery();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			
				ArrayList<String> data = new ArrayList<>();

			
				try {
					while (rs.next()) {
						data.add(rs.getString("firstName")); 
						data.add(rs.getString("lastName")); 
						data.add(rs.getString("ID")); 
						data.add(rs.getString("creditCardNumber")); 
						data.add(rs.getString("PhoneNumber")); 
						data.add(rs.getString("EmailAddress")); 
						data.add(rs.getString("UserName")); 
						data.add(rs.getString("Password")); 
						data.add(rs.getString("Region")); 
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				PreparedStatement ps11 = null;
				try {
					ps11 = conn.prepareStatement(
							"INSERT INTO costumers (first_name, last_name, ID, phone_number, email, credit_card_number, username, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					ps11.setString(1, data.get(0)); 
					ps11.setString(2, data.get(1)); 
					ps11.setString(3, data.get(2));
					ps11.setString(4, data.get(4));
					ps11.setString(5, data.get(5)); 
					ps11.setString(6, data.get(3));
					ps11.setString(7, data.get(6)); 
					ps11.setString(8, "approved");
					ps11.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				PreparedStatement ps1 = null;
				try {

					ps1 = conn.prepareStatement(
							"update users set first_name=?, last_name=?, phone_number=?, email=?, isloggedIn=?, paswword=?, role=?, region=? where ID=?;");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					ps1.setString(1, data.get(0)); 
					ps1.setString(2, data.get(1)); 

					ps1.setString(3, data.get(4));
					ps1.setString(4, data.get(5)); 
					ps1.setString(5, "0"); 
					
					ps1.setString(6, data.get(7)); 
					ps1.setString(7, "customer"); 
					ps1.setString(8, data.get(8)); 

					ps1.setString(9, data.get(2));
					ps1.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				PreparedStatement ps2 = null;
				try {
					ps2 = conn.prepareStatement("DELETE FROM userspendingforapproval WHERE id = ?;");
					ps2.setString(1, sID);
					ps2.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			else if (sStatus.equals("Deny")) {
				PreparedStatement ps3 = null;
				try {
					ps3 = conn.prepareStatement("DELETE FROM userspendingforapproval WHERE id = ?;");
					ps3.setString(1, sID);
					ps3.executeUpdate();
					;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		ArrayList<String> something = new ArrayList<>();
		something.add("Worked");
		return something;
	}

	
	/**
	 * method that ask from the data base for all the the Missing Products In a
	 * given Region
	 * 
	 * @param info list of region
	 * @return list of Missing Products
	 */
	public static ArrayList<String> getMissingProductsInRegion(ArrayList<String> info) {
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"SELECT machine_id, machine_name, Low_bar, cola, sprite, fanta, OrangeJuice, natureValley, waffles, doritos, bamba FROM inventory WHERE region = ?;"); // 10
			ps.setString(1, info.get(0));
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}


		ArrayList<String> data = new ArrayList<>();

	
		try {
			while (rs.next()) {
				data.add(rs.getString("machine_id"));
				data.add(rs.getString("machine_name"));
				data.add(rs.getString("Low_bar"));
				data.add(rs.getString("cola"));
				data.add(rs.getString("sprite"));
				data.add(rs.getString("fanta"));
				data.add(rs.getString("OrangeJuice"));
				data.add(rs.getString("natureValley"));
				data.add(rs.getString("waffles"));
				data.add(rs.getString("doritos"));
				data.add(rs.getString("bamba"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return data;

	}

	/**
	 * method that ask from the data base insert restock call to the the operation
	 * worker
	 * 
	 * @param info list of MachineName Region Low_bar MissingProductsMessage
	 * @return feedback message to the client
	 */
	public static ArrayList<String> SendMissingProductsMessageToRegionEmployee(ArrayList<String> info) {
		for (String s : info) {
			String[] str = s.split(":");
			String MachineName = str[0];
			String Region = str[1];
			String Low_bar = str[2];
			String MissingProductsMessage = str[3];
			PreparedStatement ps1 = null;
			try {
				ps1 = conn.prepareStatement(
						"INSERT INTO restoke_calls (machine_name, region, low_bar, products_to_restoke, status) VALUES (?, ?, ?, ?, ?);");
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			try {
				ps1.setString(1, MachineName);
				ps1.setString(2, Region);
				ps1.setString(3, Low_bar);
				ps1.setString(4, MissingProductsMessage);
				ps1.setString(5, "Open");
				ps1.executeUpdate();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
		ArrayList<String> something = new ArrayList<>();
		something.add("Worked");
		return something;
	}

	/**
	 * method that ask from the data base for open restock calls by region
	 * 
	 * @param info list of region
	 * @return list of restoke calls
	 */
	public static ArrayList<String> getOpenRestockCallsforSpecificRegion(ArrayList<String> info) {

		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"SELECT restoke_number, machine_name, region, low_bar, products_to_restoke, status FROM restoke_calls WHERE region = ? AND status = ?;"); // 10
			ps.setString(1, info.get(0));
			ps.setString(2, "Open");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		ArrayList<String> data = new ArrayList<>();

	
		try {
			while (rs.next()) {
				data.add(rs.getString("restoke_number"));
				data.add(rs.getString("machine_name"));
				data.add(rs.getString("region"));
				data.add(rs.getString("low_bar"));
				data.add(rs.getString("products_to_restoke"));
				data.add(rs.getString("status"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * method that ask from the data base for data to make a orders report
	 * 
	 * @param info list of region, date, machineID
	 * @return order report
	 */
	public static ArrayList<String> getDataForMachineOrdersReport(ArrayList<String> info) {
		ArrayList<String> data = new ArrayList<>();
		ArrayList<String> dataRegion = new ArrayList<>();
		String date = info.get(1);
		
		String likeDate = "%" + date;
		
		PreparedStatement ps1 = null;
	

		try {
			ps1 = conn.prepareStatement("SELECT machine_name FROM machine WHERE region = ?;"); // 2
			ps1.setString(1, info.get(0));
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		ResultSet rs1 = null;
		try {
			rs1 = ps1.executeQuery();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
		
			while (rs1.next()) {
				String machine = rs1.getString("machine_name");
				
				dataRegion.add(machine);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("SELECT total_order_price, machine FROM orders WHERE dateTime LIKE ?;"); // 2
			ps.setString(1, likeDate);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
		
			while (rs.next()) {
				String totalOrderPrice = rs.getString("total_order_price");
				String machine = rs.getString("machine");
				if (dataRegion.contains(machine)) {
					data.add(totalOrderPrice);
					data.add(machine);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return data;

	}

	/**
	 * method that ask from the data base for data to make a customer Activity
	 * report
	 * 
	 * @param info list of region, date, machineID
	 * @return customer Activity report
	 */
	public static ArrayList<String> getDataForActivityReport(ArrayList<String> info) {

		ArrayList<String> data = new ArrayList<>();
		ArrayList<String> dataRegion = new ArrayList<>();
		String date = info.get(1);
		
		String likeDate = "%" + date;
		
		PreparedStatement ps1 = null;

		try {
			ps1 = conn.prepareStatement("SELECT machine_name FROM machine WHERE region = ?;"); // 2
			ps1.setString(1, info.get(0));
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
		ResultSet rs1 = null;
		try {
			rs1 = ps1.executeQuery();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
			
			while (rs1.next()) {
				String machine = rs1.getString("machine_name");
				
				dataRegion.add(machine);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("SELECT username, machine FROM orders WHERE dateTime LIKE ?;"); // 2
			ps.setString(1, likeDate);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		try {
			
			while (rs.next()) {
				String username = rs.getString("username");
				String machine = rs.getString("machine");
				if (dataRegion.contains(machine)) {
					data.add(username);
					data.add(machine);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return data;

	}


	/**
	 * method that ask from the data base for data to make a Machine Inventory
	 * Report
	 * 
	 * @param info list of region, date, machineID
	 * @return Inventory Report
	 */
	public static ArrayList<String> getDataForMachineInventoryReport(ArrayList<String> info) {
		ArrayList<String> eror = new ArrayList<>();
		ArrayList<String> data = new ArrayList<>();
		ArrayList<String> MachineNameArray = new ArrayList<>();
		String date = info.get(1);
		String MachineID = info.get(2);
		if(date==null) {eror.add("NULL date"); return eror;}
		if(date.equals("")) {eror.add("Illegal date"); return eror;}
		if(MachineID==null) {eror.add("NULL machine"); return eror;}
		if(MachineID.equals("")) {eror.add("Illegal machine"); return eror;}
		
		int flag = 0;
		if(MachineID == null) {eror.add("NULL machine"); return eror;}

		if(date==null) {eror.add("NULL date"); return eror;}
		if (date.matches("[a-zA-Z]+")) {eror.add("Illegal date"); return eror;}
		String[] arrOfStr = date.split("/");
		int i = 0;
		for(String x : arrOfStr) 
		{
			if(x==null) {eror.add("NULL date"); return eror;}
			if(i==0) { i++;
			if(Integer.parseInt(x)<1 ||Integer.parseInt(x)>12) {eror.add("Illegal date"); return eror;}}
			if(i==1) {
				if(Integer.parseInt(x)<0 ||Integer.parseInt(x)>2023) {eror.add("Illegal date"); return eror;}}
			}
		
		String likeDate = "%" + date;
	
		PreparedStatement ps1 = null;
		try {
			ps1 = conn.prepareStatement("SELECT machine_name FROM machine"); // 2
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		ResultSet rs1 = null;
		try {
			rs1 = ps1.executeQuery();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		try {
			
			while (rs1.next()) {
				String machine = rs1.getString("machine_name");
				if(machine.equals(MachineID)) {flag = 1;
				MachineNameArray.add(machine);
				}
				
				
			}
			if(flag == 0 ) {eror.add("Illegal machine"); return eror;}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"SELECT products_to_restoke, dateTime FROM restoke_calls WHERE machine_name = ? AND dateTime LIKE ?;"); // 2
			ps.setString(1, MachineNameArray.get(0));
			ps.setString(2, likeDate);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		try {
			String products;
			String dateTime;
			
		
			while (rs.next()) {
				Integer amount = 0;
				products = rs.getString("products_to_restoke");
				dateTime = rs.getString("dateTime");
				String[] str = products.split(",");
				for (String s : str) {
					String[] sArr = s.split("-");
					String numOfMissing = sArr[1];
					amount += Integer.parseInt(numOfMissing);
				}
				data.add(Integer.toString(amount));
				data.add(dateTime);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Update the data base that the order have been arrived at the customer.
	 * 
	 * @param order
	 * @throws SQLException
	 */
	public static void got_order(String order) {
		PreparedStatement arraived;
		try {
			arraived = conn
					.prepareStatement("update orders set status = 'Arrived' where order_number = '" + order + "'");
			arraived.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method check if there is any order that on the way to the customer.
	 * 
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<String> check_status(String name) {
		ArrayList<String> status = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement(
					"select status from orders where username = '" + name + "' and status like '%Order%'");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				status.add(result.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(status);
		return status;

	}

	/**
	 * method that ask from the data base all the discounted prices if the customer
	 * is a subscriber
	 * 
	 * @param data list of machine name and user name of the customer
	 * @return all the discounted prices if the client is a subscriber
	 */
	public static ArrayList<String> getDiscountedPrices(ArrayList<String> data) {
		ArrayList<String> discount = new ArrayList<>();
		ArrayList<String> prices = new ArrayList<>();
		ArrayList<String> products = new ArrayList<>();
		ArrayList<Integer> discount_precentege = new ArrayList<>();
		String username = data.get(1);
		String machine = data.get(0);
		String sub = null;

		try {

			PreparedStatement subscriber = conn.prepareStatement("select username from subscriber where username = ?;");
			subscriber.setString(1, username);

			ResultSet result = subscriber.executeQuery();
			while (result.next()) {
				sub = result.getString("username");
			}

			PreparedStatement statement = conn.prepareStatement("select name,price from products;");

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				prices.add(rs.getString("name"));
				prices.add(rs.getString("price"));
			}

			if (sub == null || sub.equals("")) {
				ArrayList<String> pricesWithoutDis = new ArrayList<>();
				for (int i = 1; i < prices.size(); i += 2) {
					pricesWithoutDis.add(prices.get(i));
				}
				return pricesWithoutDis;
			} else {
				String region = null;
				PreparedStatement st = conn.prepareStatement("SELECT region FROM machine where machine_name=?;");
				st.setString(1, machine);
				ResultSet rs1 = st.executeQuery();
				while (rs1.next()) {
					region = rs1.getString("region");
				}

				PreparedStatement st1 = conn
						.prepareStatement("SELECT product_name,percentage from  sale where region=? AND status =?;");
				st1.setString(1, region);
				st1.setString(2, "1");
				ResultSet rs2 = st1.executeQuery();
				while (rs2.next()) {
					products.add(rs2.getString("product_name"));
					discount_precentege.add(rs2.getInt("percentage"));
				}

				for (int i = 0; i < prices.size(); i += 2) {
					if (products.contains(prices.get(i))) {
						int price = Integer.parseInt(prices.get(i + 1));
						double priceAfterDis = 0;
						int precentage = discount_precentege.get(products.indexOf(prices.get(i)));

						priceAfterDis = price - price * (precentage / (double) 100);

						String pricest = String.format("%.3f", priceAfterDis);

						discount.add(pricest);
					} else {
						double price = (double) Integer.parseInt(prices.get(i + 1));
						String pricest = String.format("%.3f", price);

						discount.add(pricest);
					}
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return discount;
	}

	/**
	 * method that ask the data base for all the customers user names
	 * 
	 * @return list with all the customers user name
	 */
	public static ArrayList<String> getCustomers() {

		ArrayList<String> customers = new ArrayList<>();
		try {

			PreparedStatement user = conn.prepareStatement("select username from users where role = ?;");
			user.setString(1, "customer");

			ResultSet result = user.executeQuery();
			while (result.next()) {
				customers.add(result.getString("username"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	/**
	 * method that ask the data base for all the costomer user names
	 * 
	 * @return list with all the customers user name
	 */
	public static ArrayList<String> getUser() {

		ArrayList<String> customers = new ArrayList<>();
		try {

			PreparedStatement user = conn.prepareStatement("select username,ID  from costumers  where ID Not in(select ID from subscriber);");

			ResultSet result = user.executeQuery();
			while (result.next()) {
				customers.add(result.getString("username"));
				customers.add(result.getString("ID"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	/**
	 * method that ask the data base for the status of user if he a subscriber or
	 * not
	 * 
	 * @param data user name
	 * @return list with the user name is a subscriber or not
	 */
	public static ArrayList<String> getSubscriberStatus(ArrayList<String> data) {
		ArrayList<String> subscribers = new ArrayList<>();

		try {
			String user = null;
			PreparedStatement ps = conn.prepareStatement("select username from subscriber where username = ?;");
			ps.setString(1, data.get(0));

			ResultSet result = ps.executeQuery();
			while (result.next()) {
				user = result.getString("username");
			}

			if (user == null || user.equals("")) {
				subscribers.add("NotSub");
			} else {
				subscribers.add("sub");
				String password = null;
				PreparedStatement ps1 = conn.prepareStatement("select paswword from users where username = ?;");
				ps1.setString(1, data.get(0));

				ResultSet rs = ps1.executeQuery();
				while (rs.next()) {
					password = rs.getString("paswword");
				}
				subscribers.add(password);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subscribers;
	}

}
