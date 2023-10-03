package application;

public class Suivi {
	private String Matricule;
	private String CodeTheme;
	private String Participation;
	public Suivi(String int1, String string, String string2) {
		// TODO Auto-generated constructor stub
		setCodeTheme(int1);
		setMatricule(string);
		setParticipation(string2);
	}
	public String getCodeTheme() {
		return CodeTheme;
	}
	public void setCodeTheme(String codeTheme) {
		CodeTheme = codeTheme;
	}
	public String getMatricule() {
		return Matricule;
	}
	public void setMatricule(String matricule) {
		Matricule = matricule;
	}
	public String getParticipation() {
		return Participation;
	}
	public void setParticipation(String participation) {
		Participation = participation;
	}
	
}
