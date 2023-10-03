package application;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


import java.io.FileOutputStream;
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

public class FormationAgentController extends MenuAdministrateur{
 
	@FXML
	private TableView<FormationAgent> FormationAgentData;
	@FXML
	private TableColumn<FormationAgent, String> formationId;
	
	@FXML
	private TableColumn<FormationAgent, String> agentId;
	
	@FXML
	private ToggleGroup Gr2;
	
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DATABASE_URL = "jdbc:mysql://localhost/Stage_bd";
	
	private ObservableList<FormationAgent> formationsAgentList = FXCollections.observableArrayList();
	private ObservableList<Formation> formationList = FXCollections.observableArrayList();
	private ObservableList<Agent> AgentList = FXCollections.observableArrayList();
	public static FormationAgent FASel;
	public static Formation F;
	public static Agent A;
	
	

	@FXML
	protected void initialize() {
		super.initialize();

		formationId.setCellValueFactory(new PropertyValueFactory<>("CodeTheme"));
		
		agentId.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
		
		refreshTable();
		
		
	}
	
	
	
	
	
	/*public boolean isDel() {
		return deleteSel;
	}*/
	
	private void refreshTable(){
		formationsAgentList.clear();
		
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
			
	    	while (resultSet.next()) {
	    		
	    		formationsAgentList.add(new FormationAgent(resultSet.getString("CodeTheme"),resultSet.getString("Matricule")));
	    	}
	    	
	    		connection.close();
		    	statement.close();
				resultSet.close();
			} catch (SQLException  | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		FormationAgentData.setItems(formationsAgentList);
		//progresswindow.close();
		
	}
	/*
	 * public void send(ActionEvent event) throws IOException,
	 * ClassNotFoundException { FASel =
	 * FormationAgentData.getSelectionModel().getSelectedItem(); if (FASel != null)
	 * {
	 * 
	 * Connection connection = null; Statement statement = null; ResultSet resultSet
	 * ; ResultSetMetaData metaData ; String resultat = ""; try { Class.forName(
	 * JDBC_DRIVER ); connection = DriverManager.getConnection(DATABASE_URL, "user",
	 * "user"); statement = connection.createStatement(); resultSet =
	 * statement.executeQuery("SELECT * FROM formation WHERE CodeTheme='"+FASel.
	 * getCodeTheme()+"'");
	 * 
	 * while (resultSet.next()) {
	 * 
	 * formationList.add(new
	 * Formation(resultSet.getString("CodeTheme"),resultSet.getString("Designation")
	 * , resultSet.getString("Type"),resultSet.getDate("DateDebut").toLocalDate(),
	 * resultSet.getDate("DateFin").toLocalDate(),resultSet.getString("Formateur")))
	 * ; }
	 * 
	 * connection.close(); statement.close(); resultSet.close(); } catch
	 * (SQLException | ClassNotFoundException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } try{ Agent agent=new
	 * Agent("aa","bb","cc","dd","cc","dd","dd"); String file_name
	 * ="C:\\Users\\lenovo\\Documents\\generate.pdf"; Document document = new
	 * Document(); PdfWriter.getInstance(document,new FileOutputStream(file_name));
	 * document.open();
	 * 
	 * Paragraph para = new
	 * Paragraph("                                     ---------------------Invitation Formation5---------------------"
	 * ); Paragraph para1 = new Paragraph("              ");
	 * 
	 * Paragraph para2 = new
	 * Paragraph("    Matricule                  : "+agent.getMatricule());
	 * Paragraph para3 = new Paragraph("    Nom                        : hdjqk");
	 * Paragraph para4 = new Paragraph("    Prénom                     : fhjkkkj");
	 * Paragraph para5 = new Paragraph("    Collège                    : fhjkkkj");
	 * Paragraph para6 = new Paragraph("    Direction                  : fhjkkkj");
	 * 
	 * Paragraph paraa = new Paragraph(
	 * "***********************************************Formation************************************************"
	 * ); Paragraph para7 = new Paragraph("    Thème                      : ");
	 * Paragraph para8 = new Paragraph("    Type                       : fhjkkkj");
	 * Paragraph para9 = new Paragraph("    Date Début                 : fhjkkkj");
	 * Paragraph para10 = new Paragraph("    Date Fin                  : fhjkkkj");
	 * Paragraph para11 = new Paragraph("    Formateur                 : fhjkkkj");
	 * 
	 * 
	 * document.add(para); document.add(para1); document.add(para1);
	 * document.add(para1); document.add(para1); document.add(para1);
	 * document.add(para2); document.add(para3); document.add(para4);
	 * document.add(para5); document.add(para6); document.add(para1);
	 * document.add(para1); document.add(para1); document.add(paraa);
	 * document.add(para1); document.add(para1); document.add(para1);
	 * document.add(para7); document.add(para8); document.add(para9);
	 * document.add(para10); document.add(para11);
	 * 
	 * 
	 * 
	 * 
	 * document.close();
	 * 
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * 
	 * System.err.println(e); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * } }
	 */
	/*public void send(ActionEvent event) throws IOException {
		    FASel = FormationAgentData.getSelectionModel().getSelectedItem();
		    int mat=FASel.getMatricule();
		    List<String> myString = new ArrayList<String>();
		    if (FASel != null) {
		    	/*Connection connection = null; 
				Statement statement = null;
				ResultSet resultSet ;
				ResultSetMetaData metaData ; 
				String resultat = "";
				try {
					Class.forName( JDBC_DRIVER ); 
					connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
					statement = connection.createStatement(); 
				    resultSet = statement.executeQuery("SELECT Email FROM agent WHERE Matricule = "+mat);
					
			    	if (resultSet.next()) {
			    		
			    		myString.add(resultSet.getString("Email"));
			    	}
			    	
			    		connection.close();
				    	statement.close();
						resultSet.close();
					} catch (SQLException  | ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    }
		    String to = "saratlbazer1234@gmail.com";
		    String from ="saratlbazer1234@gmail.com";
		    String pwd = null;
		    String sub="hh";
		    String msg="hhhh";
		        //Propriétés
		        Properties p = new Properties();
		        p.put("mail.smtp.host", "smtp.gmail.com");
		        p.put("mail.smtp.socketFactory.port", "465");
		        p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		        p.put("mail.smtp.auth", "true");
		        p.put("mail.smtp.port", "465");
		        //Session
		        Session s = Session.getDefaultInstance(p,
		          new javax.mail.Authenticator() {
		          protected PasswordAuthentication getPasswordAuthentication() {
		             return new PasswordAuthentication(from, pwd);
		          }
		        });
		        //composer le message
		        try {
		        	
		          MimeMessage m = new MimeMessage(s);
		          m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
		          m.setSubject(sub);
		          m.setText(msg);
		          //envoyer le message
		          Transport.send(m);
		          
		        } catch (MessagingException e) {
		          e.printStackTrace();
		        }
		      }
	}
		    
*/
	

	@FXML
	public void sendAll(ActionEvent event) throws IOException {		
		/*Patient patientSelected = patientsData.getSelectionModel().getSelectedItem();
		if (patientSelected != null) {			
			//deleteSel = true;
		
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
						try {
							String host = "jdbc:mysql://localhost:3306/cabinetdentaire";
							String username = "root";
							String password= "ayoub2001";
							Connection con = DriverManager.getConnection( host, username, password);
							Statement mystmt = con.createStatement();
							String query = "DELETE FROM patients WHERE IDPatients ="+patientSelected.getIDPatient();
					    	PreparedStatement preparedStmt = con.prepareStatement(query);
					    	preparedStmt.execute();
							con.close();
							mystmt.close();
							preparedStmt.close();
							
							Alert confirm = new Alert(Alert.AlertType.INFORMATION);
					    	confirm.setHeaderText(null);
					    	confirm.setTitle("Confirmation");
					    	confirm.setContentText("Ce patient a �t� supprim� avec succ�s");
					    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					    	confirm.initOwner(stage);
					    	confirm.show();
							refreshTable();
						} catch (SQLException e) {
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
		}*/
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

