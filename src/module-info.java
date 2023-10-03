module Stage_GestionFormation {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires java.sql;
	requires java.desktop;
	requires java.sql.rowset;


	
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
