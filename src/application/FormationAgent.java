package application;

public class FormationAgent {
	private String Matricule;
	private String CodeTheme;

	public FormationAgent(String string, String string1) {
		// TODO Auto-generated constructor stub
		setCodeTheme(string);
		setMatricule(string1);
		
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
	
}
