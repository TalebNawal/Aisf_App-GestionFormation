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

public class GestionFormationController extends MenuAdministrateur{
 
	@FXML
	private TableView<Formation> formationData;
	@FXML
	private TableColumn<Formation, String> formationId;
	@FXML
	private TableColumn<Formation, String> designation;
	@FXML
	private TableColumn<Formation, String> typeFormation;
	@FXML
	private TableColumn<Formation, Date> dateDebut;
	@FXML
	private TableColumn<Formation, Date> dateFin;
	@FXML
	private TableColumn<Formation, Integer> formateur;
	@FXML
	private TextField newCode;
	@FXML
	private TextField newDesi;
	@FXML
	private TextField newFormateur;
	@FXML
	private DatePicker DateFin;
	@FXML
	private DatePicker DateDebut;
	@FXML
	private TextField newType;
	@FXML
	private PasswordField adminPass;
	@FXML
	private ToggleGroup Gr2;
	
	@FXML
	private Label addMsg;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DATABASE_URL = "jdbc:mysql://localhost/Stage_bd";
	
	private ObservableList<Formation> formationsList = FXCollections.observableArrayList();
	
	public static Formation formationSel;
	public static Formation formationClicked;

	@FXML
	protected void initialize() {
		super.initialize();

		formationId.setCellValueFactory(new PropertyValueFactory<>("CodeTheme"));
		designation.setCellValueFactory(new PropertyValueFactory<>("Designation"));
		typeFormation.setCellValueFactory(new PropertyValueFactory<>("Type"));
		dateDebut.setCellValueFactory(new PropertyValueFactory<>("DateDebut"));
		dateFin.setCellValueFactory(new PropertyValueFactory<>("DateFin"));
		formateur.setCellValueFactory(new PropertyValueFactory<>("Formateur"));
		
		refreshTable();
		
		
	}
	
	public static boolean existC(String code) {
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultSet ;
		ResultSetMetaData metaData ; 
		String resultat = "";
		try {
			Class.forName( JDBC_DRIVER ); 
			connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
			statement = connection.createStatement(); 
		    resultSet = statement.executeQuery("SELECT CodeTheme FROM formation WHERE  CodeTheme ='"+code.toUpperCase()+"'");

	    	if (resultSet.next()) {
	    		return true;
	    	}
	    	connection.close();
	    	statement.close();
	    	resultSet.close();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	/*public boolean isDel() {
		return deleteSel;
	}*/
	
	private void refreshTable(){
		formationsList.clear();
		
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultSet ;
		ResultSetMetaData metaData ; 
		String resultat = "";
		try {
			Class.forName( JDBC_DRIVER ); 
			connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
			statement = connection.createStatement(); 
		    resultSet = statement.executeQuery("SELECT * FROM formation ");
			
	    	while (resultSet.next()) {
	    		String string = String.valueOf(resultSet.getInt("Formateur"));
	    		formationsList.add(new Formation(resultSet.getString("CodeTheme"),resultSet.getString("Designation"),
	    				resultSet.getString("Type"),resultSet.getDate("DateDebut").toLocalDate(),resultSet.getDate("DateFin").toLocalDate(),string));
	    	}
	    	
	    		connection.close();
		    	statement.close();
				resultSet.close();
			} catch (SQLException  | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		formationData.setItems(formationsList);
		//progresswindow.close();
		
	}

	@FXML
	public void edit(ActionEvent event) throws IOException {
		formationSel = formationData.getSelectionModel().getSelectedItem();
		if (formationSel != null) {
			AuthentificationController.isDel = false;
			AuthentificationController.src = "FormationDetails.fxml";
			Stage popupwindow=new Stage();	
			popupwindow.setResizable(false);
			popupwindow.initModality(Modality.APPLICATION_MODAL);
			popupwindow.setTitle("Authentification");
			Parent popup = FXMLLoader.load(getClass().getResource("Authentification.fxml"));
			Scene scene1= new Scene(popup);
			popupwindow.setTitle("Modification");
			popupwindow.setScene(scene1);
			popupwindow.centerOnScreen();
			popupwindow.showAndWait();
				
			while (true) {
				if (!popupwindow.isShowing()) {
					refreshTable();
					break;
				}
			}
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("sélectionner la formation à supprimer");
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			alert.initOwner(stage);
			alert.show();
		}
	}

	@FXML
	public void delete(ActionEvent event) throws IOException {		
		formationSel = formationData.getSelectionModel().getSelectedItem();
		if (formationSel != null) {         
		
			AuthentificationController.isDel = true;
			Stage popupwindow=new Stage();	
			popupwindow.setResizable(false);
			popupwindow.initModality(Modality.APPLICATION_MODAL);
			popupwindow.setTitle("Authentification");
			Parent popup = FXMLLoader.load(getClass().getResource("Authentification.fxml"));
			Scene scene1= new Scene(popup);
			popupwindow.setScene(scene1);
			popupwindow.centerOnScreen();
			popupwindow.showAndWait();
				
			AuthentificationController g = new AuthentificationController();
			while (true) {
				if (!popupwindow.isShowing()) {
					if (g.passValide()) {
						g.passDefault();
						Connection connection = null; 
						Statement statement = null;
						ResultSet resultSet ;
						ResultSetMetaData metaData ; 
						String resultat = "";
						try {
							Class.forName( JDBC_DRIVER ); 
							connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
							statement = connection.createStatement(); 
						    resultSet = statement.executeQuery("SELECT * FROM formation ");
							String query = "DELETE FROM formation WHERE CodeTheme ='"+formationSel.getCodeTheme()+"'";
					    	PreparedStatement preparedStmt = connection.prepareStatement(query);
					    	preparedStmt.execute();
					    	
							preparedStmt.close();
							
							Alert confirm = new Alert(Alert.AlertType.INFORMATION);
					    	confirm.setHeaderText(null);
					    	confirm.setTitle("Confirmation");
					    	confirm.setContentText("Cette formation a été supprimée avec succés");
					    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					    	confirm.initOwner(stage);
					    	confirm.show();
							refreshTable();
						} catch (SQLException | ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
					else {
						break;
					}
				}
			}
		}	
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("s�lectionner le patient � supprimer");
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			alert.initOwner(stage);
			alert.show();
		}
	}

	@FXML
	public void add(ActionEvent event) {
		Sign_in_Controller m = new Sign_in_Controller();
		if (newCode.getText().isBlank() || newFormateur.getText().isBlank() || adminPass.getText().isBlank()) {
			addMsg.setText("Tous les champs (*) sont obligatoires");
		}
		else if (!adminPass.getText().equals(m.getUserPass())) {
			addMsg.setText("Le password de l'administrateur est invalide");
		}
		else if (existC(newCode.getText())) {
			addMsg.setText("La formation existe d�j�");
		}
		else {
			addMsg.setText(null);
			Formation formation = new Formation(newCode.getText(), newDesi.getText(),newType.getText(), DateDebut.getValue(), DateFin.getValue(),  newFormateur.getText());
			Connection connection = null; 
			Statement statement = null;
			ResultSet resultSet ;
			ResultSetMetaData metaData ; 
			String resultat = "";
			try {
				Class.forName( JDBC_DRIVER ); 
				connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
				statement = connection.createStatement(); 
			    resultSet = statement.executeQuery("SELECT * FROM formation ");
				String query = "INSERT INTO formation (CodeTheme,Designation , Type, DateDebut, DateFin, Formateur) VALUES ('"+formation.getCodeTheme()+"', '"+formation.getDesignation()+"', '"+formation.getType()+"', '"+formation.getDateDebut()+"', '"+formation.getDateFin()+"', '"+formation.getFormateur()+"')";
		    	PreparedStatement preparedStmt = connection.prepareStatement(query);
		    	preparedStmt.execute();
				preparedStmt.close();
				
				
				
				Alert confirm = new Alert(Alert.AlertType.INFORMATION);
		    	confirm.setHeaderText(null);
		    	confirm.setTitle("Confirmation");
		    	confirm.setContentText("la nouvelle formation a été créé avec succés");
		    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		    	confirm.initOwner(stage);
		    	confirm.show();
				refreshTable();
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    	refreshTable();
	    	
	    	newCode.clear();
	    	newType.clear();
	    	newDesi.clear();
	    	DateDebut.setValue(null);
	    	DateFin.setValue(null);
	    	newFormateur.clear();
	    	adminPass.clear();
		}
	}
	
	
	
	@FXML
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

