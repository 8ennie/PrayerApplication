/**
 * 
 */
package generalApplications;

import java.io.IOException;

import generalApplications.view.CreatePersonDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author bcwie
 *
 */
public class GeneralApplicationLoader {

	private static CreatePersonDialogController  cPDC = new CreatePersonDialogController();


	public static CreatePersonDialogController showCreatePersonDialog(BorderPane rootLayout) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CreatePersonDialogController.class.getResource("CreatePersonDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			rootLayout.setCenter(page);
			cPDC = loader.getController();
			return cPDC;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static CreatePersonDialogController showCreatePersonDialog(Stage primaryStage) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CreatePersonDialogController.class.getResource("CreatePersonDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			 // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("CreatePerson");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        
			cPDC = loader.getController();
			
			dialogStage.showAndWait();
			return cPDC;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
