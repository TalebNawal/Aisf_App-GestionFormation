package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeAssistantController extends MenuAssistant{
	
	@FXML
	private TableView<Formation> Data;
	@FXML
	private TableColumn<Formation, String> formationID;
	@FXML
	private Label addMsg;
	@FXML
	private TextField search;
	@FXML
	private ToggleButton retour_button;
	
	private ObservableList<Formation> formationList = FXCollections.observableArrayList();
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DATABASE_URL = "jdbc:mysql://localhost/Stage_bd";
	public static Formation FASel;
	public static Formation FAClicked;
	@FXML
	protected void initialize() {
		super.initialize();
	
		formationID.setCellValueFactory(new PropertyValueFactory<>("CodeTheme"));
		
		
		refreshTable();
	}
	
	
	
	private void refreshTable(){
		formationList.clear();
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultSet ;
		ResultSetMetaData metaData ; 
		String resultat = "";
		try {
			Class.forName( JDBC_DRIVER ); 
			connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
			statement = connection.createStatement(); 
		    resultSet = statement.executeQuery("SELECT * FROM formation  ");
			
	    	while (resultSet.next()) {
	    		formationList.add(new Formation(resultSet.getString("CodeTheme"),resultSet.getString("Designation"),
	    				resultSet.getString("Type"),resultSet.getDate("DateDebut").toLocalDate(),resultSet.getDate("DateFin").toLocalDate(),resultSet.getString("Formateur")));
	    	}
	    	connection.close();
	    	statement.close();
			resultSet.close();
		} catch (SQLException  | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Data.setItems(formationList);		
	}
	private void recherche(){
		formationList.clear();
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultSet ;
		ResultSetMetaData metaData ; 
		String resultat = "";
		try {
			Class.forName( JDBC_DRIVER ); 
			connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
			statement = connection.createStatement(); 
		    resultSet = statement.executeQuery("SELECT * FROM formation WHERE CodeTheme LIKE '"+search.getText()+"%'");
			
	    	while (resultSet.next()) {
	    		formationList.add(new Formation(resultSet.getString("CodeTheme"),resultSet.getString("Designation"),
	    				resultSet.getString("Type"),resultSet.getDate("DateDebut").toLocalDate(),resultSet.getDate("DateFin").toLocalDate(),resultSet.getString("Formateur")));
	    	}
	    	connection.close();
	    	statement.close();
			resultSet.close();
		} catch (SQLException  | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Data.setItems(formationList);	
		search.clear();
	}
	public void ok(ActionEvent event) throws IOException {
		if (search.getText().isBlank()){addMsg.setText("vous n'avez rien chercher");}
		
		else {recherche();}
		}
	@FXML
	public void liste(ActionEvent event) throws IOException {
		FASel = Data.getSelectionModel().getSelectedItem();	
		if(FASel == null) {
			addMsg.setText("Choisir une formation");
		}
		else {
			    addMsg.setText(null);
	        	Main main = new Main();
	    		main.changeScene("HomeAssistant2.fxml");
		        }
		
	
	}
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
