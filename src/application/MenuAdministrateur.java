package application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class MenuAdministrateur {
	@FXML
	private Label time;
	@FXML
	private Circle circle;
	@FXML
	private ToggleButton Accueil_button;
	@FXML
	private ToggleButton assistant_button;
	@FXML
	private ToggleButton formation_button;
	@FXML
	private ToggleButton agent_button;
	@FXML
	private ToggleButton formationAgent_button;
	@FXML
	private ToggleButton suivi_button;
	@FXML
	private Label name;

	@FXML
	protected void initialize() {
		// Afficher et mettre � jour la date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		Thread thread = new Thread(() -> {
			while (true) {
				LocalDateTime timenow = LocalDateTime.now();
				Platform.runLater(() -> {
					time.setText(dtf.format(timenow));
				});
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		thread.start();
		
		// Afficher la photo par defaut
		try {
			circle.setFill(new ImagePattern(new Image(getClass().getResource("profile.jpg").toURI().toString(),false)));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		// Afficher le nom du dentiste
		Sign_in_Controller m = new Sign_in_Controller();
		if (Sign_in_Controller.isD) {
			name.setText(m.getUserName().toUpperCase());
		}
		
		if(!m.isAdmin() && m.isD) {
			assistant_button.setText("Informations personnelles");
		}
	}
	
	public void SeDeconnecter(ActionEvent event) throws IOException {
		Main main = new Main();
		main.changeScene("Sign_in.fxml");
	}
	
	public void Accueil(ActionEvent event) throws IOException {
        if(!Accueil_button.isSelected()){
        	Accueil_button.setSelected(true);
        }
        else {
        	Main main = new Main();
    		main.changeScene("HomeAdministrateur.fxml");
        }
	}
	
	public void assistant(ActionEvent event) throws IOException {
        if(!assistant_button.isSelected()){
        	assistant_button.setSelected(true);
        }
        else {
        	Main main = new Main();
    		main.changeScene("Gestion_Assistants2.fxml");
        }
	}
	
	public void agent(ActionEvent event) throws IOException {
        if(!agent_button.isSelected()){
        	agent_button.setSelected(true);
        }
        else {
        	Main main = new Main();
    		main.changeScene("GestionAgent.fxml");
        }
	}
	public void formation(ActionEvent event) throws IOException {
        if(!formation_button.isSelected()){
        	formation_button.setSelected(true);
        }
        else {
        	Main main = new Main();
    		main.changeScene("GestionFormation.fxml");
        }
	}
	public void formationAgent(ActionEvent event) throws IOException {
        if(!formationAgent_button.isSelected()){
        	formationAgent_button.setSelected(true);
        }
        else {
        	Main main = new Main();
    		main.changeScene("FormationAgent.fxml");
        }
	}
	public void suivi(ActionEvent event) throws IOException {
        if(!suivi_button.isSelected()){
        	suivi_button.setSelected(true);
        }
        else {
        	Main main = new Main();
    		main.changeScene("Suivi.fxml");
        }
	}
	
}
