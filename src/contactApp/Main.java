package contactApp;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import traitementDataContact.ContactData;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		
			Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
			Scene scene = new Scene(root,1000,500);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Mes Contacts");
			
			primaryStage.show();
			
			setUserAgentStylesheet(STYLESHEET_CASPIAN);

	}
	
	public static void main(String[] args) {
		launch(args);
	}

	 @Override
	    public void stop()  {
	     try {
			 ContactData.getInstance().saveContacts();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}  

	    }

	    @Override
	    public void init() {
	    	try {
				ContactData.getInstance().loadContacts();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	    	
	    }

}
