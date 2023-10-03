package application;

import java.time.LocalDate;
import java.util.Date;

public class Formation {
	private String CodeTheme;
	private String Designation;
	private String Type;
	private LocalDate DateDebut;
	private LocalDate DateFin;
	private String Formateur;
	public Formation(String string, String string2, String string3, LocalDate localDate, LocalDate localDate2, String string4 ) {
		// TODO Auto-generated constructor stub
		setCodeTheme(string );
		setDesignation(string2 );
		setType(string3 );
		setDateDebut(localDate );
		setDateFin(localDate2 );
		setFormateur(string4 );
	}
	
	

	public String getCodeTheme() {
		return CodeTheme;
	}
	public void setCodeTheme(String codeTheme) {
		CodeTheme = codeTheme;
	}
	public String getDesignation() {
		return Designation;
	}
	public void setDesignation(String designation) {
		Designation = designation;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public LocalDate getDateDebut() {
		return DateDebut;
	}
	public void setDateDebut(LocalDate dateDebut) {
		DateDebut = dateDebut;
	}
	public LocalDate getDateFin() {
		return DateFin;
	}
	public void setDateFin(LocalDate dateFin) {
		DateFin = dateFin;
	}
	public String getFormateur() {
		return Formateur;
	}
	public void setFormateur(String formateur) {
		Formateur = formateur;
	}
	
}
