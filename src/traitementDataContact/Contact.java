package traitementDataContact;

import javafx.beans.property.SimpleStringProperty;

public class Contact {
	
	private final SimpleStringProperty prénom = new SimpleStringProperty("");
	private final SimpleStringProperty nom = new SimpleStringProperty("");
	private final SimpleStringProperty téléphone = new SimpleStringProperty("");
	private final SimpleStringProperty note = new SimpleStringProperty("");

	
	public Contact(String nom, String prénom, String numéroTéléphone, String note) {
		
		setNom(nom);
		setPrénom(prénom);
		setTéléphone(numéroTéléphone);
		setNote(note);
	}
	
	public String getNom(){
		return nom.get();
	}
	
	public String getPrénom() {
		return prénom.get();
	}
	
	public String getTéléphone() {
		return téléphone.get();
	}
	
	public String getNote() {
		return note.get();
	}

	
	public void setTéléphone(String numéroTéléphone) {
		this.téléphone.set(numéroTéléphone);
	}

	public void setNom(String nom) {
		this.nom.set(nom);
	}

	public void setPrénom(String prénom) {
		this.prénom.set(prénom);
	}

	public void setNote(String note) {
		this.note.set(note);
	}
	
	
	
	
	

}
