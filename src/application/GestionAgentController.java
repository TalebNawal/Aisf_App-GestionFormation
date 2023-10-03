package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
public class GestionAgentController extends MenuAdministrateur {
	
	@FXML
	private TableView<Agent> Data;
	@FXML
	private TableColumn<Agent, String> cin;
	@FXML
	private TableColumn<Agent, String> nom;
	@FXML
	private TableColumn<Agent, String> prenom;
	@FXML
	private TableColumn<Agent, String> direction;
	@FXML
	private TableColumn<Agent, String> sexe;
	@FXML
	private TableColumn<Agent, String> email;
	@FXML
	private TextField search;
	@FXML
	private ComboBox<String> choix;
	@FXML
	private ComboBox<String> choixFormation;
	@FXML
	private Label addMsg;
	
	private ObservableList<Agent> agentList = FXCollections.observableArrayList();
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DATABASE_URL = "jdbc:mysql://localhost/Stage_bd";
	
	public static Agent FASel;
	public static Agent FAClicked;
	
	@FXML
	protected void initialize() {
		super.initialize();
		
		
		cin.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
		nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
		prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
		direction.setCellValueFactory(new PropertyValueFactory<>("Direction"));
		sexe.setCellValueFactory(new PropertyValueFactory<>("Sexe"));
		email.setCellValueFactory(new PropertyValueFactory<>("Email"));
		
		List<String> optionsCat = new ArrayList<>();
		ObservableList<String> data = FXCollections.observableArrayList("Matricule", "Nom", "Prenom","College","Direction","Sexe");
		choix.setItems(data);
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultSet ;
		ResultSetMetaData metaData ; 
		String resultat = "";
		try {
			Class.forName( JDBC_DRIVER ); 
			connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
			statement = connection.createStatement(); 
		    resultSet = statement.executeQuery("SELECT CodeTheme FROM formation ");
			
	    	while (resultSet.next()) {
	    		optionsCat.add(resultSet.getString("CodeTheme"));}
	    	connection.close();
	    	statement.close();
			resultSet.close();
		} catch (SQLException  | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		choixFormation.setItems(FXCollections.observableArrayList(optionsCat));
		refreshTable();
	}
	
	
	private void refreshTable(){
		agentList.clear();
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultSet ;
		ResultSetMetaData metaData ; 
		String resultat = "";
		try {
			Class.forName( JDBC_DRIVER ); 
			connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
			statement = connection.createStatement(); 
		    resultSet = statement.executeQuery("SELECT * FROM agent ");
			
	    	while (resultSet.next()) {
	    		agentList.add(new Agent(resultSet.getString("Matricule"),resultSet.getString("Nom"),
	    				resultSet.getString("Prenom"),resultSet.getString("College"),resultSet.getString("Direction"),resultSet.getString("Sexe"),resultSet.getString("Email")));
	    	}
	    	connection.close();
	    	statement.close();
			resultSet.close();
		} catch (SQLException  | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Data.setItems(agentList);		
	}
	private void recherche(){
		agentList.clear();
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultSet ;
		ResultSetMetaData metaData ; 
		String resultat = "";
		try {
			Class.forName( JDBC_DRIVER ); 
			connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
			statement = connection.createStatement(); 
		    resultSet = statement.executeQuery("SELECT * FROM agent WHERE  "+choix.getValue()+"='"+search.getText()+"'");
			
	    	while (resultSet.next()) {
	    		agentList.add(new Agent(resultSet.getString("Matricule"),resultSet.getString("Nom"),
	    				resultSet.getString("Prenom"),resultSet.getString("College"),resultSet.getString("Direction"),resultSet.getString("Sexe"),resultSet.getString("Email")));
	    	}
	    	connection.close();
	    	statement.close();
			resultSet.close();
		} catch (SQLException  | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Data.setItems(agentList);		
	}
	public void ok(ActionEvent event) throws IOException {
		if (search.getText().isBlank()){addMsg.setText("vous n'avez rien écrit");}
		else if (choix.getValue()== null) {addMsg.setText("il faut choisir la manière de recherche");}
		else {recherche();}
		}
	@FXML
	public void add(ActionEvent event) throws IOException {
		if(choixFormation.getValue() == null) {
			addMsg.setText("Il faut choisir une formation");
		}
		else {
			    addMsg.setText(null);
			    FASel = Data.getSelectionModel().getSelectedItem();
				
		    	FormationAgent formationAgent = new FormationAgent(choixFormation.getValue(),FASel.getMatricule());
				Connection connection = null; 
				Statement statement = null;
				ResultSet resultSet ;
				ResultSetMetaData metaData ; 
				String resultat = "";
				try {
					Class.forName( JDBC_DRIVER ); 
					connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
					statement = connection.createStatement(); 
				    resultSet = statement.executeQuery("SELECT * FROM formation_stagiaire ");
					String query = "INSERT INTO formation_stagiaire (CodeTheme,Matricule) VALUES ('"+formationAgent.getCodeTheme()+"', '"+formationAgent.getMatricule()+"')";
			    	PreparedStatement preparedStmt = connection.prepareStatement(query);
			    	preparedStmt.execute();
					preparedStmt.close();
					
					
					
					Alert confirm = new Alert(Alert.AlertType.INFORMATION);
			    	confirm.setHeaderText(null);
			    	confirm.setTitle("Confirmation");
			    	confirm.setContentText("la nouvelle formation a été ajouté pour ce agent avec succés");
			    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			    	confirm.initOwner(stage);
			    	confirm.show();
				
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}
		
	}
	
	

