package client;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;

import common.*;

/**
 * @author G17 Create connection from multiple Clients to one server
 */
public class ClientConsole implements ChatIF {

	final public static int DEFAULT_PORT = 5555;

	public ChatClient client;

	/**
	 * Constructor
	 * 
	 * @param host
	 * @param port
	 */
	public ClientConsole(String host, int port) {

		try {
			client = new ChatClient(host, port, this);

		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!" + " Terminating client.");
			System.exit(1);
		}
	}

	/**
	 * get the client IP address
	 * 
	 * @return ip address
	 */
	public InetAddress getIp() {

		return client.getInetAddress();
	}

	/**
	 * get the client Host address
	 * 
	 * @return host ip
	 */
	public String getHost() {

		return client.getHost();
	}

	/**
	 * Method that accept data from the client and send it to the server by
	 * handleMessageFromClientUI method
	 * 
	 * @param msg
	 */
	public void accept(Massage msg) {

		try {

			client.handleMessageFromClientUI(msg);

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Unexpected error while reading from console!");
		}
	}

	/**
	 * Method that prints straight to the console
	 */
	public void display(String message) {

		System.out.println("> " + message);
	}

}
