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
import javafx.scene.control.RadioButton;
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

public class HomeAssistant2Controller extends MenuAssistant {
	
	@FXML
	private TableView<Agent> Data;
	@FXML
	private TableColumn<Agent, String> cin;
	@FXML
	private TableColumn<Agent, String> nom;
	@FXML
	private TableColumn<Agent, String> prenom;
	@FXML
	private Label addMsg;
	@FXML
	private TextField search;
	@FXML
	private ComboBox<String> choix;
	@FXML
	private RadioButton present;
	@FXML
	private ToggleButton retour_button;
	private ObservableList<Agent> AgentList = FXCollections.observableArrayList();
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DATABASE_URL = "jdbc:mysql://localhost/Stage_bd";
	
	public static Agent AgSel;
	public static Agent AgClicked;
	@FXML
	protected void initialize() {
		super.initialize();
		
		cin.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
		nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
		prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
		
		List<String> optionsCat = new ArrayList<>();
		ObservableList<String> data = FXCollections.observableArrayList("Matricule", "Nom", "Prenom","College","Direction","Sexe");
		choix.setItems(data);
		refreshTable();
	}
	
	
	
	private void refreshTable(){
		AgentList.clear();
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultSet ;
		ResultSetMetaData metaData ; 
		String resultat = "";
		try {
			Class.forName( JDBC_DRIVER ); 
			connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
			statement = connection.createStatement(); 
		    resultSet = statement.executeQuery("SELECT agent.* FROM formation_stagiaire JOIN agent ON agent.Matricule = formation_stagiaire.Matricule WHERE CodeTheme='"+HomeAssistantController.FASel.getCodeTheme()+"'");
	    	while (resultSet.next()) {
	    		AgentList.add(new Agent(resultSet.getString("Matricule"),resultSet.getString("Nom"),
	    				resultSet.getString("Prenom"),resultSet.getString("College"),resultSet.getString("Direction"),resultSet.getString("Sexe"),resultSet.getString("Email")));
	    	}
	    	connection.close();
	    	statement.close();
			resultSet.close();
		} catch (SQLException  | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Data.setItems(AgentList);		
	}
	private void recherche(){
		AgentList.clear();
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultSet ;
		ResultSetMetaData metaData ; 
		String resultat = "";
		try {
			Class.forName( JDBC_DRIVER ); 
			connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
			statement = connection.createStatement(); 
		    resultSet = statement.executeQuery("SELECT * FROM agent WHERE  "+choix.getValue()+" LIKE '"+search.getText()+"%'");
			
	    	while (resultSet.next()) {
	    		AgentList.add(new Agent(resultSet.getString("Matricule"),resultSet.getString("Nom"),
	    				resultSet.getString("Prenom"),resultSet.getString("College"),resultSet.getString("Direction"),resultSet.getString("Sexe"),resultSet.getString("Email")));
	    	}
	    	connection.close();
	    	statement.close();
			resultSet.close();
		} catch (SQLException  | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Data.setItems(AgentList);	
		search.clear();
	}
	public void ok(ActionEvent event) throws IOException {
		if (search.getText().isBlank()){addMsg.setText("vous n'avez rien écrit");}
		else if (choix.getValue()== null) {addMsg.setText("il faut choisir la manière de recherche");}
		else {recherche();
		
		}
		}
	
	public void save(ActionEvent event) throws IOException {
		AgSel = Data.getSelectionModel().getSelectedItem();	
		if(AgSel == null) {
			addMsg.setText("choisir l'agent");
		}
		else {
			    addMsg.setText(null);
			    
			   	Suivi formationAg = new Suivi(HomeAssistantController.FASel.getCodeTheme(),AgSel.getMatricule(),"oui");
				Connection connection = null; 
				Statement statement = null;
				ResultSet resultSet ;
				ResultSetMetaData metaData ; 
				String resultat = "";
				try {
					Class.forName( JDBC_DRIVER ); 
					connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
					statement = connection.createStatement(); 
				    resultSet = statement.executeQuery("SELECT * FROM suivi_formation_stagiaire WHERE Matricule='"+AgSel.getMatricule()+"' AND CodeTheme='"+HomeAssistantController.FASel.getCodeTheme()+"'");
					if (resultSet.next()) {
						Alert confirm = new Alert(Alert.AlertType.INFORMATION);
				    	confirm.setHeaderText(null);
				    	confirm.setTitle("Alerte");
				    	confirm.setContentText("Cet agent a été marqué présent déja");
				    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				    	confirm.initOwner(stage);
				    	confirm.show();
					}
					else {
						  String query = "INSERT INTO  suivi_formation_stagiaire(CodeTheme,Matricule,Participation) VALUES ('"+formationAg.getCodeTheme()+"','"+	formationAg.getMatricule()+"','"+ formationAg.getParticipation()+"')";
							PreparedStatement preparedStmt = connection.prepareStatement(query);
					    	preparedStmt.execute();
					    	
							preparedStmt.close();
							
							
							
							Alert confirm = new Alert(Alert.AlertType.INFORMATION);
					    	confirm.setHeaderText(null);
					    	confirm.setTitle("Confirmation");
					    	confirm.setContentText("l'agent a été marqué présent avec succés");
					    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					    	confirm.initOwner(stage);
					    	confirm.show();
					}
				  
				
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		        
		}

	public void absent(ActionEvent event) throws IOException {
		AgSel = Data.getSelectionModel().getSelectedItem();	
		if(AgSel == null) {
			addMsg.setText("choisir l'agent");
		}
		else {
			    addMsg.setText(null);
			    
			   	Suivi formationAg = new Suivi(HomeAssistantController.FASel.getCodeTheme(),AgSel.getMatricule(),"non");
				Connection connection = null; 
				Statement statement = null;
				ResultSet resultSet ;
				ResultSetMetaData metaData ; 
				String resultat = "";
				try {
					Class.forName( JDBC_DRIVER ); 
					connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
					statement = connection.createStatement(); 
				    resultSet = statement.executeQuery("SELECT * FROM suivi_formation_stagiaire WHERE Matricule='"+AgSel.getMatricule()+"' AND CodeTheme='"+HomeAssistantController.FASel.getCodeTheme()+"'");
					if (resultSet.next()) {
						Alert confirm = new Alert(Alert.AlertType.INFORMATION);
				    	confirm.setHeaderText(null);
				    	confirm.setTitle("Alerte");
				    	confirm.setContentText("Cet agent a été marqué absent déja");
				    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				    	confirm.initOwner(stage);
				    	confirm.show();
					}
					else {
						  String query = "INSERT INTO  suivi_formation_stagiaire(CodeTheme,Matricule,Participation) VALUES ('"+formationAg.getCodeTheme()+"','"+	formationAg.getMatricule()+"','"+ formationAg.getParticipation()+"')";
							PreparedStatement preparedStmt = connection.prepareStatement(query);
					    	preparedStmt.execute();
					    	
							preparedStmt.close();
							
							
							
							Alert confirm = new Alert(Alert.AlertType.INFORMATION);
					    	confirm.setHeaderText(null);
					    	confirm.setTitle("Confirmation");
					    	confirm.setContentText("l'agent a été marqué absent avec succés");
					    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					    	confirm.initOwner(stage);
					    	confirm.show();
					}
				  
				
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		        
    
	}
	public void retour(ActionEvent event) throws IOException {
        if(!retour_button.isSelected()){
        	retour_button.setSelected(true);
        }
        else {
        	Main main = new Main();
    		main.changeScene("HomeAssistant.fxml");
        }
	}
	
}
		

