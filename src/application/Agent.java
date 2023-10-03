package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Agent {
	private String Matricule;
	private String Nom;
	private String Prenom;
	private String College;
	private String Direction;
	private String Sexe;
	private String Email;
	public Agent(String int1, String string, String string2,String string6, String string3, String string4, String string5) {
		// TODO Auto-generated constructor stub
		setMatricule(int1);
		setNom(string);
		setPrenom(string2);
		setCollege(string6); 
		setDirection(string3);
		setSexe(string4);
		setEmail(string5);
	}
	public String getMatricule() {
		return Matricule;
	}
	public void setMatricule(String matricule) {
		Matricule = matricule;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}
	public String getCollege() {
		return College;
	}
	public void setCollege(String college) {
		College = college;
	}
	public String getDirection() {
		return Direction;
	}
	public void setDirection(String direction) {
		Direction = direction;
	}
	public String getSexe() {
		return Sexe;
	}
	public void setSexe(String sexe) {
		Sexe = sexe;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
}
