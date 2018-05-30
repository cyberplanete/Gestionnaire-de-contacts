package contactApp;



import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import traitementDataContact.Contact;
import traitementDataContact.ContactData;

public class Controller {
	
	@FXML
	private FilteredList<Contact> listDesContactsControlleur;
	
	@FXML
	private BorderPane borderPaneFenetre;
	
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private TableView<Contact> contactTableView;

	@FXML
	private ContextMenu menuSouris;
	
	
 	@FXML
    private TextField champNom;

    @FXML
    private TextField champPrénom;

    @FXML
    private TextField champTéléphone;
    
    @FXML
    private TextField champNotes;
	
	
	public void initialize() {	
		
		//MADOC CRÉATION DU MENU DELETE POUR LA SOURIS SUR ELEMENT SELECTIONNÉ*****************************************************************************						
				contactTableView.setRowFactory(
					    new Callback<TableView<Contact>, TableRow<Contact>>() {
					        @Override
					        public TableRow<Contact> call(TableView<Contact> tableView) {
					            // Création instance TableRow
					        	final TableRow<Contact> tableRowSurÉlementSelectionné = new TableRow<>();
					            // Création d'une instance du contenant contextMenu
					        	final ContextMenu menuSurContact = new ContextMenu();
					        	final ContextMenu menuSurTableRow = new ContextMenu();
					            //****************************************************************************
					        	//Création d'un élément du menu : Édition du contact
					            MenuItem élémentMenuEdition = new MenuItem("Éditer contact");
					           élémentMenuEdition.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									
									tableView.getItems().setAll(ContactData.getInstance().getListOfContact());
									}
					           });
					           //*******************************************************************************
					          //Création d'un élément du menu : supprimer un contact
					            MenuItem élémentMenuSupprimer = new MenuItem("Supprimer");
					            //suppression de la rangée sélectionné si event
					            élémentMenuSupprimer.setOnAction(new EventHandler<ActionEvent>() {
					                @Override
					                public void handle(ActionEvent event) {
					                	supprimeContact(tableRowSurÉlementSelectionné.getItem());
					                    tableView.getItems().remove(tableRowSurÉlementSelectionné.getItem());
					                    
					                }
					            });
					            
					            //*******************************************************************************
						          //Création d'un élément du menu : Ajouter un contact
						            MenuItem élémentAjouterUnContact = new MenuItem("Ajouter un contact");
						            //suppression de la rangée sélectionné si event
						            élémentAjouterUnContact.setOnAction(new EventHandler<ActionEvent>() {
						                @Override
						                public void handle(ActionEvent event) {
						                    dialogAjoutContact();
						                    tableView.getItems().setAll(ContactData.getInstance().getListOfContact());
						                }
						            });

					            //********************************************************************************
					            //Intégration des éléments du menu dans le contenant
					            menuSurContact.getItems().addAll(élémentMenuEdition, élémentMenuSupprimer);
					            menuSurTableRow.getItems().addAll(élémentAjouterUnContact);
					            // Menu avec élément menu edition suppression sur contact selectionné. Sinon menu ajouter un contact
					            tableRowSurÉlementSelectionné.contextMenuProperty().bind(
					              Bindings.when(Bindings.isNotNull(tableRowSurÉlementSelectionné.itemProperty()))
					              .then(menuSurContact)
					              .otherwise(menuSurTableRow));
					            // only display context menu for non-null items:
					            //  .otherwise((ContextMenu)null));
					            return tableRowSurÉlementSelectionné;
					    }
					});
//********************************************************************************************************************
				
				
				
				
				
		listDesContactsControlleur = new FilteredList<Contact>( ContactData.getInstance().getListOfContact());
		
		//Contact contactPascal = new Contact("Joret", "Pascal", "numéroTéléphone", "test");
		//ContactData.getInstance().addContact(contactPascal);
		
		contactTableView.getItems().setAll(ContactData.getInstance().getListOfContact());
		
		//ContactData.getInstance().loadContacts();
	
}
	private void supprimeContact(Contact itemSelected) {
	ContactData.getInstance().supprimeContact(itemSelected);
	contactTableView.getItems().setAll(ContactData.getInstance().getListOfContact());
}
	@FXML
	public void dialogAjoutContact() {

		//Création d'une fenêtre  dialogue vide
		Dialog<ButtonType> dialogueAjoutContact = new Dialog<>();
		dialogueAjoutContact.initOwner(borderPaneFenetre.getScene().getWindow());
		dialogueAjoutContact.setTitle("Ajouter un nouveau contact");
		dialogueAjoutContact.setHeaderText("Utiliser cette fenetre de dialogue pour ajouter un contact");

		FXMLLoader instancefxmlLoader = new FXMLLoader();
		// *** instanceFXML récupère la configuration du dialogue à partir du fichier fxml
		instancefxmlLoader.setLocation(getClass().getResource("dialogAjoutContact.fxml"));

		try {
			//la fenetre de dialogueAjoutContact récupère la configuration de l'instance FXML
			dialogueAjoutContact.getDialogPane().setContent(instancefxmlLoader.load());
		} catch (Exception e) {
			System.out.println("Ne peut pas charger le dialogue");
			e.printStackTrace();
			return;
		}
		//******************************************************************************************************
		// MADOC Affiche bouton ok et ANNULER - FENETRE AJOUT DE TACHE 
		dialogueAjoutContact.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialogueAjoutContact.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		
		Optional<ButtonType> resultat = dialogueAjoutContact.showAndWait();
		
		// MADOC Traitement des données de l'ajout de la tâche --
		if (resultat.isPresent() && resultat.get() == ButtonType.OK) {

			ProcessNouveauContact dialogNouveauContact = instancefxmlLoader.getController();
			// MADOC "Ajout tache": Process des données de la fenêtre par processNouvelleTacheDeUI qui renvoi les données dans l'instance "un contact" 
			Contact unContact = dialogNouveauContact.processNouveauContact();

			// J'obiens une liste à jour du singleton. Sinon le nouvel évenement n'est pas
			// afficher
			// ***1*** N'est plus nécessaire
			// listTodoView.getItems().setAll(TodoData.getInstance().getListDeTaches());

			//MADOC Selectionne la la nouvelle tache. Reçu par Dialog_toFXML.processNouvelleTacheDeUI
			
			
			contactTableView.getSelectionModel().select(unContact);

			System.out.println("Ok à été choisi");
		} else {
			System.out.println("Bouton annulé choisi");
		}
	}

	//**********************************************************************************************************
	public void editContact() {
		Contact contactSelectionné = contactTableView.getSelectionModel().getSelectedItem();
		if (contactSelectionné == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Aucun contact selectionné");
			alert.setHeaderText(null);
			alert.setContentText("Veuillez selectionner le contact à éditer");
			alert.showAndWait();
			return;
		}
		
		
		
		//Création d'une fenêtre  dialogue vide
				Dialog<ButtonType> dialogueEditionContact = new Dialog<>();
				dialogueEditionContact.initOwner(borderPaneFenetre.getScene().getWindow());
				dialogueEditionContact.setTitle("Editer un contact");
				dialogueEditionContact.setHeaderText("Utiliser cette fenetre de dialogue pour editer un contact");

				FXMLLoader instancefxmlLoader = new FXMLLoader();
				// *** instanceFXML récupère la configuration du dialogue à partir du fichier fxml
				instancefxmlLoader.setLocation(getClass().getResource("dialogEditionContact.fxml"));

				try {
					//la fenetre de dialogueAjoutContact récupère la configuration de l'instance FXML
					dialogueEditionContact.getDialogPane().setContent(instancefxmlLoader.load());
				} catch (Exception e) {
					System.out.println("Ne peut pas charger le dialogue");
					e.printStackTrace();
					return;
				}
				//******************************************************************************************************
				// MADOC Affiche bouton ok et ANNULER - FENETRE AJOUT DE TACHE 
				dialogueEditionContact.getDialogPane().getButtonTypes().add(ButtonType.OK);
				dialogueEditionContact.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
				ProcessEditionContact dialogEditionContact = instancefxmlLoader.getController();
				dialogEditionContact.processEditContact(contactSelectionné);
				
				Optional<ButtonType> resultat = dialogueEditionContact.showAndWait();
				
				// MADOC Traitement des données de l'ajout de la tâche --
				if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
					dialogEditionContact.processMiseAJourContact(contactSelectionné);
					 ContactData.getInstance().saveContacts();
					// MADOC "Ajout tache": Process des données de la fenêtre par processNouvelleTacheDeUI qui renvoi les données dans l'instance "un contact" 
					
					 contactTableView.getItems().setAll(ContactData.getInstance().getListOfContact());
					
					
					
					// J'obiens une liste à jour du singleton. Sinon le nouvel évenement n'est pas
					// afficher
					// ***1*** N'est plus nécessaire
					// listTodoView.getItems().setAll(TodoData.getInstance().getListDeTaches());

					//MADOC Selectionne la la nouvelle tache. Reçu par Dialog_toFXML.processNouvelleTacheDeUI
					
					
					//contactTableView.getSelectionModel().select(unContact);

					System.out.println("Ok à été choisi");
				} else {
					System.out.println("Bouton annulé choisi");
				}
			}
	
	
	
	
////MADOC Gestion des touches du clavier - Recoit les données du clavier
//	public void gestionToucheClavier(KeyEvent eventClavier) {
//		ClassTache tacheSelectionnée = listViewTaches.getSelectionModel().getSelectedItem();
//		if (tacheSelectionnée != null) {
//			//MADOC SI TOUCHE DELETE EST UTILISÉ
//			if (eventClavier.getCode().equals(KeyCode.DELETE)) {
//				effaceTache(tacheSelectionnée);
//			}
//		}
//	}
	
	
	
}
