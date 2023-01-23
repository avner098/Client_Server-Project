package servergui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import common.Massage;
import entities.Client;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import server.MysqlConnection;
import server.Server;
import server.ServerUI;

/**
 * @author Server ControllerClass that controls the Server GUI
 */
public class ProjectServerController implements Initializable {

	@FXML
	private TextField ip;

	@FXML
	private PasswordField password;

	@FXML
	private TextField port;

	@FXML
	private TextField serverStatus;

	@FXML
	private TextField username;

	@FXML
	private TextField DBname;

	@FXML
	private Button disconnectBTN = null;

	@FXML
	private Button connectBTN = null;

	@FXML
	private Button importbtn = null;

	@FXML
	private Button exportbtn = null;

	@FXML
	private TableView<Client> connectclient;

	@FXML
	private TextArea serverConsole;
	
    @FXML
    private ImageView img;

	public static ObservableList<Client> list = FXCollections.observableArrayList();
	private String IP_st;
	private String Dbname_st;
	private String Username_st;
	private String Password_st;
	private String Port_st;

	/**
	 * Load the server data to the server GUI
	 */
	private void loadServerData() {

		InetAddress ip = null;
		String[] ip_adrrs = null;
		String[] ip_adrrs1 = { " ", " " };

		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}

		if (ip != null)
			ip_adrrs = ip.toString().split("/");
		else
			ip_adrrs = ip_adrrs1;

		this.ip.setText(ip_adrrs[1]);
		this.port.setText("5555");
		this.DBname.setText("jdbc:mysql://localhost/project?serverTimezone=IST");
		this.username.setText("root");

	}

	/**
	 * method that on click import all data of user into the data base
	 * 
	 * @param event click
	 */
	@FXML
	void onimport(ActionEvent event) {
		importbtn.setDisable(true);
		exportbtn.setDisable(false);
		Server.importUsersfromDb();
	}

	/**
	 * method that on click export all data of user out from the data base
	 * 
	 * @param event click
	 */
	@FXML
	void onexport(ActionEvent event) {
		exportbtn.setDisable(true);
		importbtn.setDisable(false);
		Server.exportUsersfromDb();
		;
	}

	/**
	 * button connect that make connection with the Database
	 * 
	 * @param event
	 */
	@FXML
	void connect(ActionEvent event) {

		ArrayList<String> data;
		IP_st = ip.getText();
		Dbname_st = DBname.getText();
		Port_st = port.getText();
		Username_st = username.getText();
		Password_st = password.getText();
		data = getparmet();
		ServerUI.dataToConnectDB = data;
		ServerUI.runServer(Port_st);

		connectBTN.setDisable(true);
		disconnectBTN.setDisable(false);
		importbtn.setDisable(false);

		ip.setDisable(true);
		DBname.setDisable(true);
		port.setDisable(true);
		username.setDisable(true);
		password.setDisable(true);

	}

	/**
	 * button disconnect ,that disconnect server from data
	 * 
	 * @param event
	 */
	@FXML

	void disconnect(ActionEvent event) {
		ServerUI.stopServer();
		connectBTN.setDisable(false);
		disconnectBTN.setDisable(true);
		exportbtn.setDisable(true);
		importbtn.setDisable(true);
		ip.setDisable(false);
		DBname.setDisable(false);
		port.setDisable(false);
		username.setDisable(false);
		password.setDisable(false);
	}

	/**
	 * Read parameters from Server GUI
	 * 
	 * @return
	 */
	public ArrayList<String> getparmet() {
		ArrayList<String> data = new ArrayList<>();

		data.add(Dbname_st);
		data.add(Username_st);
		data.add(Password_st);
		data.add(IP_st);

		return data;
	}

	/**
	 * Append lines to server console
	 * 
	 * @param str
	 */
	public void appendText(String str) {
		Platform.runLater(() -> serverConsole.appendText(str));
	}

	/**
	 * Server GUI start
	 * 
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/servergui/projectServer.fxml"));

		} catch (IOException e) {
			e.printStackTrace();

			return;
		}
		Scene s = new Scene(root);

		primaryStage.setScene(s);
		primaryStage.show();

	}

	/**
	 * initialize, method that set the data that loads with the gui start up
	 */
	@SuppressWarnings("unchecked")
	@Override

	public void initialize(URL location, ResourceBundle resources) {
		InputStream stream = null;
		/*
		try {
			stream = new FileInputStream("/Ekurt server/src/servergui/logo.jpg"); 
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(stream != null) {
			Image image = new Image(getClass().getResourceAsStream("/src/servergui/logo.jpg"));
			img.setImage(image);
		}
		*/
		Image image = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
		img.setImage(image);

		loadServerData();

		disconnectBTN.setDisable(true);

		importbtn.setDisable(true);
		exportbtn.setDisable(true);

		TableColumn<Client, String> colIPP = new TableColumn<Client, String>("IP");

		TableColumn<Client, String> colHostT = new TableColumn<Client, String>("Host");

		TableColumn<Client, String> colStatusS = new TableColumn<Client, String>("Status");

		connectclient.getColumns().addAll(colIPP, colHostT, colStatusS);

		colIPP.setCellValueFactory(new PropertyValueFactory<Client, String>("IP"));
		colHostT.setCellValueFactory(new PropertyValueFactory<Client, String>("Host"));
		colStatusS.setCellValueFactory(new PropertyValueFactory<Client, String>("Status"));

		connectclient.setItems(list);

		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				appendText(String.valueOf((char) b));
			}
		};
		System.setOut(new PrintStream(out, true));

	}

}
