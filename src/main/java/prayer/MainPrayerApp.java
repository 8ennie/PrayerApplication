package prayer;

import java.util.ArrayList;
import java.util.List;

import database.Person;
import databaseDAO.PersonDao;
import databaseDAO.PersonTypeDao;
import generalApplications.GeneralApplicationLoader;
import generalApplications.view.CreatePersonDialogController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import prayer.view.MainPrayerController;
import prayer.view.ManagePrayerController;

public class MainPrayerApp extends Application{

	private Stage primaryStage;
	private BorderPane rootLayout;
	private MainPrayerController mainPrayerController;
	private ManagePrayerController managePrayerController;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Prayer");
		initRootLayout();
		showMainPrayerView();
	}

	public void Load() {
		launch();
	}
	
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainPrayerApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene =new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void showMainPrayerView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainPrayerApp.class.getResource("view/MainPrayerView.fxml"));
			AnchorPane prayerMainView = (AnchorPane) loader.load();
		
			rootLayout.setCenter(prayerMainView);
			
			mainPrayerController = loader.getController();
			mainPrayerController.setMainApp(this);
			mainPrayerController.setUp();
	       
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void showManagePrayerView() {
		try {	
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainPrayerApp.class.getResource("view/ManagePrayerView.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
		
			 // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Manage Prayer");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
			
		
	        managePrayerController = loader.getController();
	        managePrayerController.setMainApp(this);
	        
	        managePrayerController.setUp();
	        
	        dialogStage.showAndWait();
	        
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void showCreatePersonDialog() {
		CreatePersonDialogController cPDC = GeneralApplicationLoader.showCreatePersonDialog(primaryStage);
		cPDC.setUp();
	}
	

	public List<Person> getPeronData(){
		List<Person> pList = new ArrayList<>();
		pList = new PersonDao().getPersonByType(new PersonTypeDao().getPersonType("PrayFor").getPerosnTypeId());
		return pList;
	}
	
	public MainPrayerController getMainPrayerController() {
		return mainPrayerController;
	}

	public ManagePrayerController getManagePrayerController() {
		return managePrayerController;
	}
}
