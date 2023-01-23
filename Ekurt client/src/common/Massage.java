package common;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * @author Avner class designed to transfer information from the client to the
 *         server and back
 */
public class Massage implements Serializable {

	private ArrayList<String> data;
	private String operation;
	private String Description = null;
	private String fileName = null;
	private int size = 0;
	public byte[] mybytearray;

	/**
	 * constructor
	 */
	public Massage() {
		data = new ArrayList<>();
	}

	/**
	 * method that initializes a bit array of a certain size
	 * 
	 * @param size
	 */
	public void initArray(int size) {
		mybytearray = new byte[size];
	}

	/**
	 * method that returned the action we would like to perform
	 * 
	 * @return operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * method that set the action we want to perform
	 * 
	 * @param operation
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * method that returns a list of information we want to transfer
	 * 
	 * @return array list of data
	 */
	public ArrayList<String> getData() {
		return data;
	}

	/**
	 * method that set the data arraylist we want to send
	 * 
	 * @param data
	 */
	public void setData(ArrayList<String> data) {
		this.data = data;
	}

	/**
	 * method that return the file name
	 * 
	 * @return file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * setter for the file name
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * getter for size
	 * 
	 * @return file size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * setter for size
	 * 
	 * @param size file size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * getter for mybytearray
	 * 
	 * @return mybytearray array of byte
	 */
	public byte[] getMybytearray() {
		return mybytearray;
	}

	/**
	 * getter for mybytearray in specific place
	 * 
	 * @param i place in array
	 * @return byte in place i
	 */
	public byte getMybytearray(int i) {
		return mybytearray[i];
	}

	/**
	 * setter for mybytearray
	 * 
	 * @param mybytearray byte Array
	 */
	public void setMybytearray(byte[] mybytearray) {

		for (int i = 0; i < mybytearray.length; i++)
			this.mybytearray[i] = mybytearray[i];
	}

	/**
	 * getter for Description
	 * 
	 * @return Description String
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * setter for Description
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		Description = description;
	}
}
