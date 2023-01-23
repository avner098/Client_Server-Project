package controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import common.IInventoryReportController;

//[number of missing products, date, number of missing products, another date]
class InventoryReportControllerTest {
	public class StubInventoryReport implements IInventoryReportController {
		@Override
		public ArrayList<String> create_Inventory_Report() {
			return dataFromServer;
		}

	}

	public IInventoryReportController iirc;
	public ArrayList<String> dataFromServer = new ArrayList<String>(); // [number of missing products, date, number //
																		// of missing products, another date]
	public ArrayList<String> data = new ArrayList<String>();
	public InventoryReportController inventoryReportController;

	@BeforeEach
	void setUp() throws Exception {
		iirc = new StubInventoryReport();
	}

	@Test
	// Checking functionality: The method get the input data and return total
	// missing amount in the given month.
	// input data: missing amount = 100, date = 10/01/2023, amount = 15, date =
	// 11/01/2023, amount = 3, date = 03/01/2023
	// expected result: 118.
	void ReportCreationSuccssesed() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("100");
		dataFromServer.add("10/01/2023");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("03/01/2023");

		int expected = 118;
		int result = Integer.parseInt(inventoryReportController.update_chart_view().get(0));
		assertEquals(expected, result);

	}

	@Test
	// Checking functionality: The method get the input data with a NULL amount.
	// input data: missing amount = 100, date = 10/01/2023, amount = 15, date =
	// 11/01/2023, amount = 3, date = 03/01/2023, amount = null, date = 08/01/2023.
	// expected result: "NULL amount"
	void ReportCreationNULLAmountFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("100");
		dataFromServer.add("10/01/2023");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("03/01/2023");
		dataFromServer.add(null);
		dataFromServer.add("08/01/2023");

		String expected = "NULL amount";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);

	}

	@Test
	// Checking functionality: The method get the input data with a NULL date.
	// input data: missing amount = 100, date = 10/01/2023, amount = 15, date =
	// 11/01/2023, amount = 3, date = null, amount = 5, date = 08/01/2023.
	// expected result: "NULL date"
	void ReportCreationNULLDateFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("100");
		dataFromServer.add("10/01/2023");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add(null);
		dataFromServer.add("5");
		dataFromServer.add("08/01/2023");

		String expected = "NULL date";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);
	}

	@Test
	// Checking functionality: The method get the input data with a invalid day date
	// ("aa" instead of an integer number).
	// input data: missing amount = 100, date = aa/01/2023, amount = 15, date =
	// 11/01/2023, amount = 3, date = 13/01/2023, amount = 5, date = 08/01/2023.
	// expected result: "Illegal date"
	void ReportCreationWrongDateFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("100");
		dataFromServer.add("aa/01/2023");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("13/01/2023");
		dataFromServer.add("5");
		dataFromServer.add("08/01/2023");

		String expected = "Illegal date";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);
	}

	@Test
	// Checking functionality: The method get the input data with a invalid amount
	// ("aa0" instead of an integer number).
	// input data: missing amount = aa0, date = 10/01/2023, amount = 15, date =
	// 11/01/2023, amount = 3, date = 01/01/2023, amount = 5, date = 08/01/2023.
	// expected result: "Illegal amount"
	void ReportCreationCharacterAmountFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("aa0");
		dataFromServer.add("10/01/2023");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("01/01/2023");
		dataFromServer.add("5");
		dataFromServer.add("08/01/2023");

		String expected = "Illegal amount";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);
	}

	@Test
	// Checking functionality: The method get the input data with a wrong format
	// date (no year).
	// input data: missing amount = 500, date = 10/01, amount = 15, date =
	// 11/01/2023, amount = 3, date = 05/01/2023, amount = 3, date = 08/01/2023.
	// expected result: "Illegal date"
	void ReportCreationIllegaShortlDatetFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("500");
		dataFromServer.add("10/01");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("05/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("08/01/2023");

		String expected = "Illegal date";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);
	}

	@Test
	// Checking functionality: The method get the input data with a date format
	// amount (Illegal amount).
	// input data: missing amount = 03/01/2023, date = 10/01/2023, amount = 15, date
	// = 11/01/2023, amount = 3, date = 05/01/2023, amount = 3, date = 08/01/2023.
	// expected result: "Illegal amount"
	void ReportCreationDateInstedOfAmountFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("03/01/2023");
		dataFromServer.add("10/01/2023");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("05/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("08/01/2023");

		String expected = "Illegal amount";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);
	}

	@Test
	// Checking functionality: The method get the input data with amount instead of
	// date (Illegal date format).
	// input data: missing amount = 500, date = 10, amount = 15, date = 11/01/2023,
	// amount = 3, date = 05/01/2023, amount = 3, date = 08/01/2023.
	// expected result: "Illegal date"
	void ReportCreationAmountInstedOfDateFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("500");
		dataFromServer.add("10");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("05/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("08/01/2023");

		String expected = "Illegal date";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);
	}

	@Test
	// Checking functionality: The method get the input data with negative amount.
	// input data: missing amount = -500, date = 10/01/2023, amount = 15, date =
	// 11/01/2023, amount = 3, date = 05/01/2023, amount = 3, date = 08/01/2023.
	// expected result: "Illegal amount"
	void ReportCreationNegativeAmountFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("-500");
		dataFromServer.add("10/01/2023");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("05/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("08/01/2023");

		String expected = "Illegal amount";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);
	}

	@Test
	// Checking functionality: The method get the input data with an illegal
	// argument as day in date (day is bigger than 31).
	// input data: missing amount = 500, date = 100/01/2023, amount = 15, date =
	// 11/01/2023, amount = 3, date = 05/01/2023, amount = 3, date = 08/01/2023.
	// expected result: "Illegal date"
	void ReportCreationIllegalDayFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("500");
		dataFromServer.add("100/01/2023");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("05/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("08/01/2023");

		String expected = "Illegal date";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);
	}

	@Test
	// Checking functionality: The method get the input data with an illegal
	// argument as month in date (month is lower than 1).
	// input data: missing amount = 500, date = 10/00/2023, amount = 15, date =
	// 11/01/2023, amount = 3, date = 05/01/2023, amount = 3, date = 08/01/2023.
	// expected result: "Illegal date"
	void ReportCreationIllegalMonthFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("500");
		dataFromServer.add("10/00/2023");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("05/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("08/01/2023");
		String expected = "Illegal date";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);
	}

	@Test
	// Checking functionality: The method get the input data with an illegal
	// argument as year in date (year is bigger than 2023).
	// input data: missing amount = 500, date = 10/10/2090, amount = 15, date =
	// 11/01/2023, amount = 3, date = 05/01/2023, amount = 3, date = 08/01/2023.
	// expected result: "Illegal date"
	void ReportCreationIllegalYearFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("500");
		dataFromServer.add("10/10/2090");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("05/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("08/01/2023");
		String expected = "Illegal date";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);
	}
	
	@Test
	// Checking functionality: The method get the input data with an illegal argument as amount (amount is an empty string).
	// input data: missing amount = "", date = 10/10/2023, amount = 15, date = 11/01/2023, amount = 3, date = 05/01/2023, amount = 3, date = 08/01/2023.
	// expected result: "NULL amount"
	void ReportCreationEmptyAmountFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("");
		dataFromServer.add("10/10/2023");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("05/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("08/01/2023");
		String expected = "NULL amount";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);

	}
	
	@Test
	// Checking functionality: The method get the input data with an illegal argument as  date (date is an empty string).
	// input data: missing amount = 500, date = "" amount = 15, date = 11/01/2023, amount = 3, date = 05/01/2023, amount = 3, date = 08/01/2023.
	// expected result: "NULL date"
	void ReportCreationEmptyDateFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("500");
		dataFromServer.add("");
		dataFromServer.add("15");
		dataFromServer.add("11/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("05/01/2023");
		dataFromServer.add("3");
		dataFromServer.add("08/01/2023");
		String expected = "NULL date";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);
	}
	
	
	@Test
	// Checking functionality: The method get the input data with missing arguments.
	// input data: missing amount = 500.
	// expected result: "received missing data from server"
	void ReportCreationMissingFieldsFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		dataFromServer.add("500");
		String expected = "received missing data from server";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);
	}
	
	@Test
	// Checking functionality: The method get the input data as null arraylist.
	// input data: NULL ArrayList.
	// expected result: "received missing data from server"
	void ReportCreationNullArrayListFailure() throws Exception {
		InventoryReportController inventoryReportController = new InventoryReportController(iirc);
		String expected = "received missing data from server";
		String result = inventoryReportController.update_chart_view().get(0);
		assertEquals(expected, result);
	}
	
}
