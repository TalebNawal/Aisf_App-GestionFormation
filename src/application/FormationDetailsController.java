package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormationDetailsController {
	@FXML
	private TextField code;
	@FXML
	private TextField des;
	@FXML
	private TextField type;
	@FXML
	private TextField form;
	@FXML
	private DatePicker dateD;
	@FXML
	private DatePicker dateF;
	
	@FXML
	private Label msg;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DATABASE_URL = "jdbc:mysql://localhost/Stage_bd";
	
	@FXML
	private void initialize() {
		code.setText(GestionFormationController.formationSel.getCodeTheme());
		des.setText(GestionFormationController.formationSel.getDesignation());
		type.setText(GestionFormationController.formationSel.getType());
		form.setText(GestionFormationController.formationSel.getFormateur());
		dateD.setValue(GestionFormationController.formationSel.getDateDebut());
		dateF.setValue(GestionFormationController.formationSel.getDateFin());
	}
	
	
	
	@FXML
	public void save(ActionEvent event) {
		if (code.getText().equals(GestionFormationController.formationSel.getCodeTheme()) && 
			des.getText().equals(GestionFormationController.formationSel.getDesignation()) &&
			type.getText().equals(GestionFormationController.formationSel.getType()) &&
			form.getText().equals(GestionFormationController.formationSel.getFormateur()) &&
			dateD.getValue().equals(GestionFormationController.formationSel.getDateDebut()) &&
			dateD.getValue().equals(GestionFormationController.formationSel.getDateFin())) {
			cancel(event);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		}
		else if (code.getText().isBlank() || 
				
				form.getText().isBlank() ) {
			msg.setText("les champs * sont obligatoires");
		}
		else if (GestionFormationController.existC(code.getText()) && !code.getText().equals(GestionFormationController.formationSel.getCodeTheme())) {
			msg.setText("La Code existe déja");
		}
		else {
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
				String query = "UPDATE formation SET CodeTheme='"+code.getText()+"', Designation='"+des.getText()+"', Type='"+type.getText()+"', Formateur='"+form.getText()+"', DateDebut='"+dateD.getValue()+"', DateDebut='"+dateF.getValue()+"' WHERE CodeTheme='"+GestionFormationController.formationSel.getCodeTheme()+"'";
		    	PreparedStatement preparedStmt = connection.prepareStatement(query);
		    	preparedStmt.execute();
		    	
				preparedStmt.close();
				
				Alert confirm = new Alert(Alert.AlertType.INFORMATION);
		    	confirm.setHeaderText(null);
		    	confirm.setTitle("Confirmation");
		    	confirm.setContentText("Cette formation a été modifiée avec succés");
		    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		    	confirm.initOwner(stage);
		    	confirm.show();
		    	
		        stage.close();
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

	@FXML
	public void cancel(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
}
