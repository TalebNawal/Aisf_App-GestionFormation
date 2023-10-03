package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;

public class AcceuilAssistantController extends MenuAssistant {
	@FXML
	private TableView<Suivi> Data;
	@FXML
	private TableColumn<Suivi, String> formationID;
	@FXML
	private TableColumn<Suivi, String> agentID;
	@FXML
	private TableColumn<Suivi, String> participation;
	@FXML
	private Label addMsg;
	@FXML
	private TextField search;
	@FXML
	private ComboBox<String> choix;
	@FXML
	private ToggleButton retour_button;
	@FXML
	private ToggleButton formation_button;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DATABASE_URL = "jdbc:mysql://localhost/Stage_bd";
	
	private ObservableList<Suivi> suiviList = FXCollections.observableArrayList();
	public static Suivi FASel;
	public static Suivi FAClicked;

	

	@FXML
	protected void initialize() {
		super.initialize();

		formationID.setCellValueFactory(new PropertyValueFactory<>("CodeTheme"));
		agentID.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
		participation.setCellValueFactory(new PropertyValueFactory<>("Participation"));
		
		List<String> optionsCat = new ArrayList<>();
		ObservableList<String> data = FXCollections.observableArrayList("Matricule","CodeTheme");
		choix.setItems(data);
		
		refreshTable();
		
		
	}
	private void refreshTable(){
		suiviList.clear();
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultSet ;
		ResultSetMetaData metaData ; 
		String resultat = "";
		try {
			Class.forName( JDBC_DRIVER ); 
			connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
			statement = connection.createStatement(); 
		    resultSet = statement.executeQuery("SELECT * FROM suivi_formation_stagiaire ");
			
	    	while (resultSet.next()) {
	    		
	    		suiviList.add(new Suivi(resultSet.getString("Matricule"),resultSet.getString("CodeTheme"),resultSet.getString("Participation")));
	    	}
	    	
	    		connection.close();
		    	statement.close();
				resultSet.close();
			} catch (SQLException  | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		Data.setItems(suiviList);
		//progresswindow.close();
		
	}
	private void recherche(){
		suiviList.clear();
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultSet ;
		ResultSetMetaData metaData ; 
		String resultat = "";
		try {
			Class.forName( JDBC_DRIVER ); 
			connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
			statement = connection.createStatement(); 
		    resultSet = statement.executeQuery("SELECT * FROM suivi_formation_stagiaire WHERE  "+choix.getValue()+" LIKE '"+search.getText()+"%'");
			
	    	while (resultSet.next()) {
	    		suiviList.add(new Suivi(resultSet.getString("Matricule"),resultSet.getString("CodeTheme"),resultSet.getString("Participation")));
	    	}
	    	connection.close();
	    	statement.close();
			resultSet.close();
			} catch (SQLException  | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Data.setItems(suiviList);	
			search.clear();
		}
	public void ok(ActionEvent event) throws IOException {
		if (search.getText().isBlank()){addMsg.setText("vous n'avez rien écrit");}
		else if (choix.getValue()== null) {addMsg.setText("il faut choisir par quoi chercher");}
		else {recherche();}
		}
	@FXML
	public void formation(ActionEvent event) throws IOException {
		  if(!formation_button.isSelected()){
			  formation_button.setSelected(true);
	        }
	      else {
	        	Main main = new Main();
	    		main.changeScene("HomeAssistant.fxml");
		        }}
	public void retour(ActionEvent event) throws IOException {
        if(!retour_button.isSelected()){
        	retour_button.setSelected(true);
        }
        else {
        	Main main = new Main();
    		main.changeScene("AccueilAssistant.fxml");
        }
	}
}
