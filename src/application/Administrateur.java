package application;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("serial")
public class Administrateur extends User{
	private boolean isAdmin;
	private int ID;
	
	public Administrateur(String Login, String Passwd, String name, String cin, String tel, boolean isAdmin) {
		super(Login, Passwd, name, cin, tel);
		this.isAdmin = isAdmin;
		setID();
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean a) {
		isAdmin = a;
	}
	public int getID() {
		return ID;
	}
	public void setID() {
		int id = 1;
		boolean changed = true;
		while (changed) {
			changed = false;
			try{
				FileInputStream fis = new FileInputStream("dentistes");
				while (fis.available() > 0) {
					ObjectInputStream ois = new ObjectInputStream(fis);
					Administrateur dentiste = (Administrateur) ois.readObject();
					if (dentiste.getID()==id) {
						id++;
						changed = true;
						break;
					}
				}
				fis.close();
			}catch (EOFException e) {
				// this is fine
			}catch (IOException | ClassNotFoundException ex) {
				ex.printStackTrace();
			} 
		}
		this.ID = id;
	}
}
