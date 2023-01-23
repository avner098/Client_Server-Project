package server;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import servergui.ProjectServerController;

/**
 * @author
 * 
 *         class that runs the MAIN of the project and starts the running of the
 *         project
 */
public class ServerUI extends Application {

	final public static int DEFAULT_PORT = 5555;
	public static ArrayList<String> dataToConnectDB = new ArrayList<>();
	public static Server sv;
	public static ProjectServerController controler;

	/**
	 * method start the first GUI
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		ProjectServerController aFrame = new ProjectServerController();

		aFrame.start(primaryStage);

		controler = aFrame;
	}

	/**
	 * main method lunch the Application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Method that start the server running
	 * 
	 * @param p
	 */
	public static void runServer(String p) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(p); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		sv = new Server(port);
		sv.setArray(dataToConnectDB);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}

	/**
	 * Method that stops the server
	 */
	public static void stopServer() {
		sv.stopListening();
		try {
			sv.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
