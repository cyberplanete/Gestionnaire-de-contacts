package contactApp;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import traitementDataContact.Contact;
import traitementDataContact.ContactData;

public class ProcessNouveauContact {
	 	@FXML
	    private TextField champNom;

	    @FXML
	    private TextField champPrénom;

	    @FXML
	    private TextField champTéléphone;
	    
	    @FXML
	    private TextField champNotes;
	    

	    // MADOC Récupère les données des champs de l'UI pour être placer dans l'instance nouveau contact
	    public Contact processNouveauContact() {
	        String prénom = champPrénom.getText().trim();
	        String nom = champNom.getText().trim();
	        String téléphone = champTéléphone.getText().trim();
	        String notes = champNotes.getText().trim();
   
	        Contact nouveauContact = new Contact(nom, prénom, téléphone,notes);
	        // Singleton permet d'acceder à la seule instance de la classe --- SINGLETON---- Ajout du contact à la liste de contactData
	        ContactData.getInstance().addContact(nouveauContact);
	        //Permet de renvoyer la tache traité 
	        return nouveauContact;
	    }
}

