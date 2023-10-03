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

public class PageAccueilController {
	
	
	@FXML
	private ToggleButton Accueil_button;
	@FXML
	private ToggleButton formation_button;
	@FXML
	private ToggleButton agent_button;
	@FXML
	private ToggleButton presence_button;
	

	
	public void SeDeconnecter(ActionEvent event) throws IOException {
			}
	
	public void Accueil(ActionEvent event) throws IOException {
        if(!Accueil_button.isSelected()){
        	Accueil_button.setSelected(true);
        }
        else {
        	Main main = new Main();
    		main.changeScene("PageAccueil.fxml");
        }
	}
	public void formation(ActionEvent event) throws IOException {
        if(!formation_button.isSelected()){
        	formation_button.setSelected(true);
        }
    	else {
        	Main main = new Main();
    		main.changeScene("PageFormation.fxml");
        } 
	}
	
	public void agent(ActionEvent event) throws IOException {
        if(!agent_button.isSelected()){
        	agent_button.setSelected(true);
        }
        else {
        	Main main = new Main();
    		main.changeScene("PageAgent.fxml");
        }
	}
	
	public void presence(ActionEvent event) {
        if(!presence_button.isSelected()){
        	presence_button.setSelected(true);
        }
	}
	
	
}
