package client;

import controllers.EnterIpController;
import controllers.projectClientController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author G17
 *
 *         class that run main method to launch client side
 */
public class ClientUI extends Application {

	public static ClientConsole chat;
	public static EnterIpController controler;

	/**
	 * method start the GUI
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		/*
		 * projectClientController aFrame = new projectClientController();
		 * 
		 * aFrame.start(primaryStage);
		 */
		EnterIpController aFrame = new EnterIpController();
		aFrame.start(primaryStage);
		controler = aFrame;

	}

	/**
	 * main method to run client side
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}