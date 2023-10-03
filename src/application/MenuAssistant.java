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

public class MenuAssistant {
	@FXML
	private Label time;
	@FXML
	private Label name;
	@FXML	
	private Circle circle;
	@FXML
	private ToggleButton accueil_button;
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
		
		Sign_in_Controller m = new Sign_in_Controller();
		name.setText(m.getUserName().toUpperCase());
		
		try {
			circle.setFill(new ImagePattern(new Image(getClass().getResource("profile.jpg").toURI().toString(),false)));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}}
	@FXML
	public void SeDeconnecter(ActionEvent event) throws IOException {
		Main main = new Main();
		main.changeScene("Sign_in.fxml");
	}
	public void accueil(ActionEvent event) throws IOException {
        if(!accueil_button.isSelected()){
        	accueil_button.setSelected(true);
        }
        else {
        	Main main = new Main();
    		main.changeScene("AccueilAssistant.fxml");
        }
	}
}
