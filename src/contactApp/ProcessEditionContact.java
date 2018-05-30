package contactApp;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import traitementDataContact.Contact;
import traitementDataContact.ContactData;

public class ProcessEditionContact {
 	@FXML
    private TextField champNom;

    @FXML
    private TextField champPrénom;

    @FXML
    private TextField champTéléphone;
    
    @FXML
    private TextField champNotes; 
	    

	    // MADOC Récupère les données des champs de l'UI pour être placer dans l'instance nouveau contact
	    public void processEditContact(Contact contact) {
	    	
	    	champNom.setText(contact.getNom());
	    	champPrénom.setText(contact.getPrénom());
	    	champTéléphone.setText(contact.getTéléphone());
	    	champNotes.setText(contact.getNote());
	    	 
	    	
	    	
	    	
//	    	String nom = contact.getNom();
//	    	String prénom = contact.getPrénom();
//	        String téléphone =contact.getTéléphone();
//	        String notes =contact.getNote();
//   
//	        Contact nouveauContact = new Contact(nom, prénom, téléphone,notes);
//	        // Singleton permet d'acceder à la seule instance de la classe --- SINGLETON---- Ajout du contact à la liste de contactData
//	        ContactData.getInstance().addContact(nouveauContact);
//	        //Permet de renvoyer la tache traité 
//	        return nouveauContact;
	    }

		public void processMiseAJourContact(Contact contactSelectionné) {
			contactSelectionné.setPrénom(champPrénom.getText());
			contactSelectionné.setNom(champNom.getText());
			contactSelectionné.setTéléphone(champTéléphone.getText());
			contactSelectionné.setNote(champNotes.getText());
	   
		        
		        // Singleton permet d'acceder à la seule instance de la classe --- SINGLETON---- Ajout du contact à la liste de contactData
		        ContactData.getInstance().saveContacts();;
			
		}
}

