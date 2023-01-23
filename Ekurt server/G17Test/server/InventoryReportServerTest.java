package server;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryReportServerTest {
	ArrayList<String> info;
	Connection conn;

	@BeforeEach
	void setUp() throws Exception {
		MysqlConnection.connectDB("jdbc:mysql://localhost/project?serverTimezone=IST", "root", "avner098", "localhost");
		conn = MysqlConnection.conn;
		info = new ArrayList<>();
		info.add("north");

	}

	// Checking functionality: The method get the input data and return ArrayList
	// with the amount of missing product.
	// input data: date = 01/2023 , machine name = karmiel
	// expected result: returned ArrayList with the data = 201,10/01/2023
	@Test
	void InventoryReportCreationSuccess() {
		info.add("01/2023");
		info.add("karmiel");
		ArrayList<String> Expected = new ArrayList<>();
		Expected.add("201");
		Expected.add("10/01/2023");
		ArrayList<String> result = MysqlConnection.getDataForMachineInventoryReport(info);

		assertEquals(Expected, result);
	}

	// Checking functionality: The method get the input data (empty date) and return
	// ArrayList
	// with the amount of missing product.
	// input data: date = "" , machine name = haifa
	// expected result: returned ArrayList with the data = Illegal date
	@Test
	void InventoryReportCreationFailDateEmpty() {
		info.add("");
		info.add("haifa");
		ArrayList<String> Expected = new ArrayList<>();
		Expected.add("8");
		Expected.add("10/01/2023");
		Expected.add("4");
		Expected.add("10/01/2023");
		String expected = "Illegal date";
		String result = MysqlConnection.getDataForMachineInventoryReport(info).get(0);
		assertEquals(expected, result);
	}

	// Checking functionality: The method get the input data (empty machine) and
	// return ArrayList
	// with the amount of missing product.
	// input data: date = "01/2023" , machine name = ""
	// expected result: returned ArrayList with the data = Illegal machine
	@Test
	void InventoryReportCreationFailMachineEmpty() {
		info.add("01/2023");
		info.add("");
		ArrayList<String> Expected = new ArrayList<>();
		Expected.add("8");
		Expected.add("10/01/2023");
		Expected.add("4");
		Expected.add("10/01/2023");
		String expected = "Illegal machine";
		String result = MysqlConnection.getDataForMachineInventoryReport(info).get(0);
		assertEquals(expected, result);
	}

	// Checking functionality: The method get wrong data, the date is null, and
	// return "NULL date"
	// input data: date = null , machine name = karmiel
	// expected result: returned ArrayList with the data = "NULL date"
	@Test
	void InventoryReportCreationFailNullDate() {
		info.add(null);
		info.add("karmiel");

		String expected = "NULL date";
		String result = MysqlConnection.getDataForMachineInventoryReport(info).get(0);
		assertEquals(expected, result);

	}

	// Checking functionality: The method get wrong data, the date is 123, and
	// return "Illegal date"
	// input data: date = 123 , machine name = haifa
	// expected result: returned ArrayList with the data = "Illegal date"
	@Test
	void InventoryReportCreationFailInvalidDate() {
		info.add("123");
		info.add("haifa");

		String expected = "Illegal date";
		String result = MysqlConnection.getDataForMachineInventoryReport(info).get(0);
		assertEquals(expected, result);

	}

	// Checking functionality: The method get wrong data, the date is aaa, and
	// return "Illegal date"
	// input data: date = aaa , machine name = karmiel
	// expected result: returned ArrayList with the data = "Illegal date"
	@Test
	void InventoryReportCreationFailInvalidDateCharters() {
		info.add("aaa");
		info.add("karmiel");

		String expected = "Illegal date";
		String result = MysqlConnection.getDataForMachineInventoryReport(info).get(0);
		assertEquals(expected, result);

	}

	// Checking functionality: The method get wrong data, the machine name is
	// Invalid, and return "Illegal machine"
	// input data: date = 01/2023 , machine name = Invalid
	// expected result: returned ArrayList with the data = "Illegal machine"
	@Test
	void InventoryReportCreationFailInvalidMachineName() {
		info.add("01/2023");
		info.add("Invalid");

		String expected = "Illegal machine";
		String result = MysqlConnection.getDataForMachineInventoryReport(info).get(0);
		assertEquals(expected, result);

	}

	// Checking functionality: The method get wrong data, the date is wrong month
	// and future year. the machine name is wrong as well.
	// the first issue is the date so the return value will be Illegal date
	// input data: date = 33/2024 , machine name = Invalid
	// expected result: returned ArrayList with the data = "Illegal date"
	@Test
	void InventoryReportCreationFailInvalidmonthAndYear() {
		info.add("33/2024");
		info.add("Invalid");
		String expected = "Illegal date";
		String result = MysqlConnection.getDataForMachineInventoryReport(info).get(0);
		assertEquals(expected, result);

	}

	// Checking functionality: The method get wrong data, the machine name is null.
	// input data: date = 11/2020 , machine name = null
	// expected result: returned ArrayList with the data = "NULL machine"
	@Test
	void InventoryReportCreationFailMachineNameNull() {
		info.add("11/2020");
		info.add(null);

		String expected = "NULL machine";
		String result = MysqlConnection.getDataForMachineInventoryReport(info).get(0);
		assertEquals(expected, result);

	}
}
