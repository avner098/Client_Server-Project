package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import common.ILoginController;

class LoginControllerTest {

	class stubLoginControllerManager implements ILoginController {

		@Override
		public String verifay(String username, String password) {
			return role;
		}
	}

	static String role;
	ILoginController ILC;
	LoginController Lc;

	@BeforeEach
	void setUp() throws Exception {

		ILC = new stubLoginControllerManager();
		Lc = new LoginController();
		Lc.set(ILC);
	}
	
	/**
	 * Checking functionality: the server enter null as a role
	 * 
	 * input data: user name = "yosi" , password = "1234"
	 * 
	 * expected result:String with the message "NullOutput"
	 * 
	 */
	@Test
	void loginCheckRoleIsNull() {
		role = null;
		String expected = "NullOutput";
		String result = Lc.loginCheck("yosi", "1234");
		
		assertEquals(expected,result);
	}

	/**
	 * Checking functionality: client enter null user name
	 * 
	 * input data: user name = null , password = "1234"
	 * 
	 * expected result:String with the message "NullInput"
	 * 
	 */
	@Test
	void loginCheckNullUserName() {
		String expected = "NullInput";
		String result = Lc.loginCheck(null, "1234");
		
		assertEquals(expected,result);
	}
	
	/**
	 * Checking functionality: client enter empty user name
	 * 
	 * input data: user name = "" , password = "1234"
	 * 
	 * expected result:String with the message "EmptyInput"
	 * 
	 */
	@Test
	void loginCheckEmptyUserName() {
		String expected = "EmptyInput";
		String result = Lc.loginCheck("", "1234");
		
		assertEquals(expected,result);
	}

	/**
	 * Checking functionality: client enter null password into the login
	 * 
	 * input data: user name = "yosi" , password = null
	 * 
	 * expected result: String with the message "NullInput"
	 * 
	 * 
	 */
	@Test
	void loginCheckNullPassword() {
		String expected = "NullInput";
		String result = Lc.loginCheck("yosi", null);
		
		assertEquals(expected,result);
	}
	
	/**
	 * Checking functionality: client enter empty password
	 * 
	 * input data: user name = "yosi" , password = ""
	 * 
	 * expected result: String with the message "EmptyInput"
	 * 
	 * 
	 */
	@Test
	void loginCheckEmptyPassword() {

		String expected = "EmptyInput";
		String result = Lc.loginCheck("yosi", "");
		
		assertEquals(expected,result);
	}
	
	
	/**
	 * Checking functionality: client enter user that not exist in the data base
	 * 
	 * input data: user name = "yosi" , password = "1234"
	 * 
	 * expected result: String with the message "NotExist"
	 * 
	 * 
	 */
	@Test
	void loginCheckWrongUserName() {
		role = "NotExist";
		String expected = "NotExist";
		String result = Lc.loginCheck("yosi", "1234");
		
		assertEquals(expected,result);
	}
	
	/**
	 * Checking functionality: client enter user that his role is opertion worker
	 * 
	 * input data: user name = "avner" , password = "1234"
	 * 
	 * expected result: String with the role "opertion worker" 
	 * 
	 * 
	 */
	@Test
	void loginCheckSucses() {
		role = "opertion worker";
		String expected = "opertion worker";
		String result = Lc.loginCheck("avner", "1234");
		
		assertEquals(expected,result);
	}
	
	/**
	 * Checking functionality: client enter user that is all ready logged into the system
	 * 
	 * input data: user name = "amit" , password = "1234"
	 * 
	 * expected result: String with the message "alreadyLogged"
	 * 
	 * 
	 */
	@Test
	void loginCheckalreadyLogged() {
		role = "alreadyLogged";
		String expected = "alreadyLogged";
		String result = Lc.loginCheck("amit", "1234");
		
		assertEquals(expected,result);
	}
	
	
	/**
	 * Checking functionality: client enter user name with tab and valid password
	 * 
	 * input data: user name = "yo\tsi" , password = "1234"
	 * 
	 * expected result: String with the message "username with space"
	 * 
	 * 
	 */
	@Test
	void loginCheckUserNameWithSpace() {
		
		String expected = "username with space";
		String result = Lc.loginCheck("yo	si", "1234");
		
		assertEquals(expected,result);
	}
	
	
	/**
	 * Checking functionality: client enter password with space and valid user name 
	 * 
	 * input data: user name = "yosi" , password = "123 4"
	 * 
	 * expected result: String with the message "password with space"
	 * 
	 * 
	 */
	@Test
	void loginCheckPasswordWithSpace() {
		
		String expected = "password with space";
		String result = Lc.loginCheck("yosi", "123 4");
		
		assertEquals(expected,result);
	}

}
