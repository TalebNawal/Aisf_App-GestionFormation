package application;

import javafx.fxml.FXML;

import javafx.scene.control.ToggleGroup;

import javafx.scene.control.TextField;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ToggleButton;

import javafx.scene.control.PasswordField;

import javafx.scene.shape.Circle;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class PageAgentController {
	
	@FXML
	private ToggleButton Accueil_button;
	@FXML
	private ToggleGroup Gr;
	@FXML
	private ToggleButton formation_button;
	@FXML
	private ToggleButton agent_button;
	@FXML
	private ToggleButton presence_button;
	@FXML
	private TableView agentData;
	@FXML
	private TableColumn agentMatricule;
	@FXML
	private TableColumn agentNom;
	@FXML
	private TableColumn agentPrenom;
	@FXML
	private TableColumn agentDirection;
	@FXML
	private TableColumn agentEmail;
	
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
       
	}
	
	public void presence(ActionEvent event) {
	if(!presence_button.isSelected()){
		presence_button.setSelected(true);
	}
	}

}
