package application;

import java.io.IOException;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HomeAdministrateurController extends MenuAdministrateur{
	@FXML
	private PieChart pourcentagePre;
	
	private int nbrepre;
	private int nbreabs;
	
	@FXML
	private BarChart<String, Number> agents;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DATABASE_URL = "jdbc:mysql://localhost/Stage_bd";
	@FXML
	protected void initialize() {
		super.initialize();
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultSet ;
		ResultSetMetaData metaData ; 
		String resultat = "";

		try {
			 
			Class.forName( JDBC_DRIVER ); 
			connection = DriverManager.getConnection(DATABASE_URL, "user", "user");
			statement = connection.createStatement(); 
		    resultSet = statement.executeQuery("SELECT COUNT(*) FROM suivi_formation_stagiaire WHERE Participation ='oui'");
			metaData = resultSet.getMetaData();
			
			if (resultSet.next()) {
		    		nbrepre = resultSet.getInt("COUNT(*)");
		    	}
			statement.close();
			resultSet.close();
			Statement mystmt1 = connection.createStatement();
			String query1 = "SELECT COUNT(*) FROM suivi_formation_stagiaire WHERE Participation ='non'";
	    	ResultSet results1 = mystmt1.executeQuery(query1);
	    	if (results1.next()) {
	    		nbreabs = results1.getInt("COUNT(*)");
	    	}
			mystmt1.close();
			results1.close();
			
			connection.close();
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		PieChart.Data slice1 = new PieChart.Data("Les présences", nbrepre);
	    PieChart.Data slice2 = new PieChart.Data("Les Absences", nbreabs);
	    pourcentagePre.getData().add(slice1);
	    pourcentagePre.getData().add(slice2);
	    pourcentagePre.setLegendSide(Side.LEFT);
	    
	 
	}
}
