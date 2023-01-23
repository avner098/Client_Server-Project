package server;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginTest {

	private ArrayList<String> userFailPassword;
	private ArrayList<String> userFailUserName;
	private ArrayList<String> userNull;
	private ArrayList<String> userCarrier;
	private ArrayList<String> userCeo;
	private ArrayList<String> userCustomer;
	private ArrayList<String> userCustomer_Service;
	private ArrayList<String> userMarketing_Manager;
	private ArrayList<String> userMarketing_Worker;
	private ArrayList<String> userOpertion_Worker;
	private ArrayList<String> userRegional_Manager;
	private ArrayList<String> userUser;
	private ArrayList<String> userAlreadyLogged;

	@BeforeEach
	void setUp() throws Exception {

		MysqlConnection.connectDB("jdbc:mysql://localhost/project?serverTimezone=IST", "root", "avner098", "localhost");

		userFailPassword = new ArrayList<>();
		userFailPassword.add("david");
		userFailPassword.add("");

		userFailUserName = new ArrayList<>();
		userFailUserName.add("");
		userFailUserName.add("1234");

		userNull = new ArrayList<>();
		userNull.add(null);
		userNull.add(null);

		userAlreadyLogged = new ArrayList<>();
		userAlreadyLogged.add("beny");
		userAlreadyLogged.add("1234");

		userCarrier = new ArrayList<>();
		userCarrier.add("noa");
		userCarrier.add("1234");

		userCeo = new ArrayList<>();
		userCeo.add("amit");
		userCeo.add("1234");

		userCustomer = new ArrayList<>();
		userCustomer.add("dina");
		userCustomer.add("1234");

		userCustomer_Service = new ArrayList<>();
		userCustomer_Service.add("avner");
		userCustomer_Service.add("1234");

		userMarketing_Manager = new ArrayList<>();
		userMarketing_Manager.add("haim");
		userMarketing_Manager.add("1234");

		userMarketing_Worker = new ArrayList<>();
		userMarketing_Worker.add("nadav");
		userMarketing_Worker.add("1234");

		userOpertion_Worker = new ArrayList<>();
		userOpertion_Worker.add("omer");
		userOpertion_Worker.add("1234");

		userRegional_Manager = new ArrayList<>();
		userRegional_Manager.add("michal");
		userRegional_Manager.add("1234");

		userUser = new ArrayList<>();
		userUser.add("arad");
		userUser.add("1234");
	}

	/**
	 * Checking functionality: empty string as a user name and valid password
	 * 
	 * input data: user name = "" , password = "1234"
	 * 
	 * expected result: Array list that in the array position 0 be a String
	 * "NotExist"
	 * 
	 * 
	 */
	@Test
	void LoginUserNameEmpty() {
		String expected = "NotExist";
		String result = MysqlConnection.login(userFailUserName).get(0);
		assertEquals(expected, result);
	}

	/**
	 * Checking functionality: empty string as a password and valid user name
	 * 
	 * input data: user name = "david" , password = ""
	 * 
	 * expected result: Array list that in the array position 0 be a String
	 * "NotExist"
	 */
	@Test
	void LoginPasswordEmpty() {
		String expected = "NotExist";
		String result = MysqlConnection.login(userFailPassword).get(0);
		assertEquals(expected, result);
	}

	/**
	 * Checking functionality: send null argument into login
	 * 
	 * input data: null
	 * 
	 * expected result: Array list that in the array position 0 be a String
	 * "NullInput"
	 */
	@Test
	void LoginNull() {
		String expected = "NullInput";
		String result = MysqlConnection.login(null).get(0);
		assertEquals(expected, result);
	}

	/**
	 * Checking functionality: send empty ArrayList into login
	 * 
	 * input data: new ArrayList<String>()
	 * 
	 * expected result:Array list that in the array position 0 be a String
	 * "EmptyInput"
	 */
	@Test
	void LoginEmpty() {
		String expected = "EmptyInput";
		String result = MysqlConnection.login(new ArrayList<String>()).get(0);
		assertEquals(expected, result);
	}

	/**
	 * Checking functionality: send to login method user that is all ready logged
	 * into the system
	 * 
	 * input data: user name = "beny" , password = "1234"
	 * 
	 * expected result:Array list that in the array position 0 be a String
	 * "alreadyLogged"
	 */
	@Test
	void LoginAlreadyLogged() {
		String expected = "alreadyLogged";
		String result = MysqlConnection.login(userAlreadyLogged).get(0);
		assertEquals(expected, result);
	}

	/**
	 * Checking functionality: send to login method user that is all ready logged
	 * into the system
	 * 
	 * input data: user name = null , password = null
	 * 
	 * expected result:Array list that in the array position 0 be a String
	 * "NullInput"
	 */
	@Test
	void LoginNullUserNameAndPassword() {
		String expected = "NullInput";
		String result = MysqlConnection.login(userNull).get(0);
		assertEquals(expected, result);

		userNull.clear();
		userNull.add(null);
		userNull.add("1234");
		result = MysqlConnection.login(userNull).get(0);
		assertEquals(expected, result);

		userNull.clear();
		userNull.add("beny");
		userNull.add(null);
		result = MysqlConnection.login(userNull).get(0);
		assertEquals(expected, result);
	}

	/**
	 * Checking functionality: send to login method user that his role is Customer
	 * Service
	 * 
	 * input data: user name = "avner" , password = "1234"
	 * 
	 * expected result:Array list with all the client info <role , first name, last
	 * name, isloogedIn{0,1}, user name, password, region, id>
	 */
	@Test
	void LoginUserCustomer_ServiceSucsess() {

		ArrayList<String> expected = new ArrayList<>();
		expected.add("customer Service");
		expected.add("Avner");
		expected.add("Ben Shlomo");
		expected.add("0");
		expected.add("avner");
		expected.add("1234");
		expected.add("");
		expected.add("1");

		ArrayList<String> result = MysqlConnection.login(userCustomer_Service);

		assertEquals(expected, result);

	}

	/**
	 * Checking functionality: send to login method user that his role is CEO
	 * 
	 * input data: user name = "amit" , password = "1234"
	 * 
	 * expected result:Array list with all the client info <role , first name, last
	 * name, isloogedIn{0,1}, user name, password, region, id>
	 */
	@Test
	void LoginUserCeo() {

		ArrayList<String> expected = new ArrayList<>();
		expected.add("ceo");
		expected.add("Amit");
		expected.add("Vinograd");
		expected.add("0");
		expected.add("amit");
		expected.add("1234");
		expected.add("");
		expected.add("2");

		ArrayList<String> result = MysqlConnection.login(userCeo);

		assertEquals(expected, result);

	}

	/**
	 * Checking functionality: send to login method user that his role is Carrier
	 * 
	 * input data: user name = "noa" , password = "1234"
	 * 
	 * expected result:Array list with all the client info <role , first name, last
	 * name, isloogedIn{0,1}, user name, password, region, id>
	 */
	@Test
	void LoginUserCarrier() {

		ArrayList<String> expected = new ArrayList<>();
		expected.add("Carrier");
		expected.add("Noa");
		expected.add("Kirel");
		expected.add("0");
		expected.add("noa");
		expected.add("1234");
		expected.add("UAE");
		expected.add("23");

		ArrayList<String> result = MysqlConnection.login(userCarrier);

		assertEquals(expected, result);

	}

	/**
	 * Checking functionality: send to login method user that his role is Customer
	 * 
	 * input data: user name = "dina" , password = "1234"
	 * 
	 * expected result:Array list with all the client info <role , first name, last
	 * name, isloogedIn{0,1}, user name, password, region, id>
	 */
	@Test
	void LoginUserCustomer() {

		ArrayList<String> expected = new ArrayList<>();
		expected.add("customer");
		expected.add("Dina");
		expected.add("Zak");
		expected.add("0");
		expected.add("dina");
		expected.add("1234");
		expected.add("UAE");
		expected.add("15");

		ArrayList<String> result = MysqlConnection.login(userCustomer);

		assertEquals(expected, result);

	}

	/**
	 * Checking functionality: send to login method user that his role is Marketing
	 * Manager
	 * 
	 * input data: user name = "haim" , password = "1234"
	 * 
	 * expected result:Array list with all the client info <role , first name, last
	 * name, isloogedIn{0,1}, user name, password, region, id>
	 */
	@Test
	void LoginUserMarketing_Manager() {

		ArrayList<String> expected = new ArrayList<>();
		expected.add("marketing Manager");
		expected.add("Haim");
		expected.add("Boskila");
		expected.add("0");
		expected.add("haim");
		expected.add("1234");
		expected.add("UAE");
		expected.add("11");

		ArrayList<String> result = MysqlConnection.login(userMarketing_Manager);

		assertEquals(expected, result);

	}

	/**
	 * Checking functionality: send to login method user that his role is Marketing
	 * Worker
	 * 
	 * input data: user name = "nadav" , password = "1234"
	 * 
	 * expected result:Array list with all the client info <role , first name, last
	 * name, isloogedIn{0,1}, user name, password, region, id>
	 */
	@Test
	void LoginUserMarketing_Worker() {

		ArrayList<String> expected = new ArrayList<>();
		expected.add("marketing worker");
		expected.add("Nadav");
		expected.add("Blum");
		expected.add("0");
		expected.add("nadav");
		expected.add("1234");
		expected.add("South");
		expected.add("10");

		ArrayList<String> result = MysqlConnection.login(userMarketing_Worker);

		assertEquals(expected, result);

	}

	/**
	 * Checking functionality: send to login method user that his role is Regional
	 * Manager
	 * 
	 * input data: user name = "michal" , password = "1234"
	 * 
	 * expected result:Array list with all the client info <role , first name, last
	 * name, isloogedIn{0,1}, user name, password, region, id>
	 */
	@Test
	void LoginUserRegional_Manager() {

		ArrayList<String> expected = new ArrayList<>();
		expected.add("regional Manager");
		expected.add("Michal");
		expected.add("Israel");
		expected.add("0");
		expected.add("michal");
		expected.add("1234");
		expected.add("South");
		expected.add("13");

		ArrayList<String> result = MysqlConnection.login(userRegional_Manager);

		assertEquals(expected, result);

	}

	/**
	 * Checking functionality: send to login method user that his role is User
	 * 
	 * input data: user name = "arad" , password = "1234"
	 * 
	 * expected result:Array list with all the client info <role , first name, last
	 * name, isloogedIn{0,1}, user name, password, region, id>
	 */
	@Test
	void LoginUserUser() {

		ArrayList<String> expected = new ArrayList<>();
		expected.add("user");
		expected.add("Arad");
		expected.add("Nir");
		expected.add("0");
		expected.add("arad");
		expected.add("1234");
		expected.add("");
		expected.add("26");

		ArrayList<String> result = MysqlConnection.login(userUser);

		assertEquals(expected, result);

	}

}
