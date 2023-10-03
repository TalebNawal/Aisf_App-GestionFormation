package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
//import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	private static Stage stg;
	 @Override
	    public void start(Stage primaryStage) {
	        try {
	        	stg = primaryStage;
	            // Read file fxml and draw interface.
	            Parent root = FXMLLoader.load(getClass()
	                    .getResource("Sign_in.fxml"));

	            primaryStage.setTitle("Aisf");
	            primaryStage.getIcons().add(new Image(getClass().getResource("icon.jpeg").toURI().toString()));
	            
				primaryStage.centerOnScreen();
	            primaryStage.setScene(new Scene(root));
	            primaryStage.show();
	        
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    }
	   
	    public static void main(String[] args) {
	        launch(args);
	    	
	    }
	    public void changeScene(String fxml) throws IOException {
	        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
	        stg.getScene().setRoot(pane);
	    }
	   
	    
}