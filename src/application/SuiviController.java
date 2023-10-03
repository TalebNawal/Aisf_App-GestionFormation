package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Properties;  
 
public class SuiviController extends MenuAdministrateur{
 
	@FXML
	private TableView<Suivi> FormationAgentData;
	@FXML
	private TableColumn<Suivi, String> formationId;
	
	@FXML
	private TableColumn<Suivi, String> agentId;
	@FXML
	private TableColumn<Suivi, String> participation;
	@FXML
	private ToggleGroup Gr2;
	
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DATABASE_URL = "jdbc:mysql://localhost/Stage_bd";
	
	private ObservableList<Suivi> suiviList = FXCollections.observableArrayList();
	public static Suivi FASel;
	public static Suivi FAClicked;

	

	@FXML
	protected void initialize() {
		super.initialize();

		formationId.setCellValueFactory(new PropertyValueFactory<>("CodeTheme"));
		
		agentId.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
		participation.setCellValueFactory(new PropertyValueFactory<>("Participation"));
		
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
		
		FormationAgentData.setItems(suiviList);
		//progresswindow.close();
		
	}



	public void info(MouseEvent event) throws IOException {/*
		if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            Node node = ((Node) event.getTarget()).getParent();
            TableRow<?> row;
            if (node instanceof TableRow) {
                row = (TableRow<?>) node;
            } 
            else {
                // clicking on text part
                row = (TableRow<?>) node.getParent();
            }
            patientClicked = (Patient) row.getItem();
            
            Main main = new Main();
    		main.changeScene("Info_Patient.fxml");
        }*/
	}
	
	
	
	
}

