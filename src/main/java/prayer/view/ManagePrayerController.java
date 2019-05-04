/**
 * 
 */
package prayer.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import database.Person;
import database.Prayer;
import database.User;
import databaseDAO.PersonDao;
import databaseDAO.PrayerDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import login.view.LoginController;
import prayer.MainPrayerApp;

/**
 * @author bcwie
 *
 */
public class ManagePrayerController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="cbAnswered"
	private CheckBox cbAnswered; // Value injected by FXMLLoader

	@FXML // fx:id="txtNotes"
	private TextArea txtNotes; // Value injected by FXMLLoader

	@FXML // fx:id="txtPrayerDescription"
	private TextArea txtPrayerDescription; // Value injected by FXMLLoader

	@FXML // fx:id="txtPrayerTopic"
	private TextField txtPrayerTopic; // Value injected by FXMLLoader

	@FXML // fx:id="dpDate"
	private DatePicker dpDate; // Value injected by FXMLLoader

	@FXML // fx:id="cbPersons"
	private ComboBox<String> cbPersons; // Value injected by FXMLLoader

	@FXML // fx:id="lblAddedPersons"
	private Label lblAddedPersons; // Value injected by FXMLLoader

	@FXML // fx:id="spImportance"
	private Spinner<Integer> spImportance; // Value injected by FXMLLoader
	
	@FXML // fx:id="lblUsernaem"
	private Label lblUsernaem; // Value injected by FXMLLoader

	private MainPrayerApp mainPrayerApp;

	private List<Person> pList = new ArrayList<>();
	
	private User curUser = LoginController.curUser;

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert cbAnswered != null : "fx:id=\"cbAnswered\" was not injected: check your FXML file 'ManagePrayerView.fxml'.";
		assert txtNotes != null : "fx:id=\"txtNotes\" was not injected: check your FXML file 'ManagePrayerView.fxml'.";
		assert txtPrayerDescription != null : "fx:id=\"txtPrayerDescription\" was not injected: check your FXML file 'ManagePrayerView.fxml'.";
		assert txtPrayerTopic != null : "fx:id=\"txtPrayerTopic\" was not injected: check your FXML file 'ManagePrayerView.fxml'.";
		assert dpDate != null : "fx:id=\"dpDate\" was not injected: check your FXML file 'ManagePrayerView.fxml'.";
		assert cbPersons != null : "fx:id=\"cbPersons\" was not injected: check your FXML file 'ManagePrayerView.fxml'.";
		assert lblAddedPersons != null : "fx:id=\"lblAddedPersons\" was not injected: check your FXML file 'ManagePrayerView.fxml'.";
		assert spImportance != null : "fx:id=\"spImportance\" was not injected: check your FXML file 'ManagePrayerView.fxml'.";
		assert lblUsernaem != null : "fx:id=\"lblUsernaem\" was not injected: check your FXML file 'ManagePrayerView.fxml'.";
		
		dpDate.getEditor().setDisable(true);
		dpDate.getEditor().setEditable(false);
		lblUsernaem.setText("User: " + curUser.getUsername());
	}

	public void setMainApp(MainPrayerApp mainPrayerApp) {
		this.mainPrayerApp = mainPrayerApp;

	}

	@FXML
	private void openMainPrayerView() {
		mainPrayerApp.showMainPrayerView();
	}

	public void setUp() {
		LoadPersons();
		fillSpinner();
	}

	public void LoadPersons() {
		cbPersons.getItems().clear();
		List<Person> pList = mainPrayerApp.getPeronData();
		for (Person person : pList) {
			cbPersons.getItems().add(person.getLastname() + " " + person.getFirstname() + "[" + person.getPerson_id() + "]");
		}
	}

	@FXML
	private void addNewPerson() {
		mainPrayerApp.showCreatePersonDialog();
	}
	
	@FXML
	private void addPerson() {
		if (!cbPersons.getValue().isEmpty()) {
			Person pAdd = getPerson();
			pList.add(pAdd);
			String newP = pAdd.getLastname() + " " + pAdd.getFirstname();
			cbPersons.getItems().remove(cbPersons.getValue());
			if (lblAddedPersons.getText().isEmpty()) {
				lblAddedPersons.setText(newP);
			} else {
				lblAddedPersons.setText(lblAddedPersons.getText() + "; " + newP);
			}
		}
	}

	@FXML
	private void removePerson() {
		cbPersons.getItems().clear();
		LoadPersons();
		lblAddedPersons.setText("");
		pList.clear();
	}

	@FXML
	private void addPrayer() {
		if (preCheck()) {
			Prayer prayer = new Prayer();
			prayer.setTopic(txtPrayerTopic.getText());
			prayer.setPrayerDescription(txtPrayerDescription.getText());
			prayer.setImportance(spImportance.getValue());
			prayer.setPrayerCreator(curUser);
			if (dpDate.getValue() != null) {
				prayer.setDueDate(java.sql.Date.valueOf(dpDate.getValue()));
			}
			if (!txtNotes.getText().isEmpty()) {
				prayer.setNotes(txtNotes.getText());
			}
			if (cbAnswered.isSelected()) {
				prayer.setAnswered(true);
			} else {
				prayer.setAnswered(false);
			}
			if(!pList.isEmpty()) {
				prayer.setPerson(pList);
				for (Person person : pList) {
					person.setPrayer(prayer);
				}				
			}
			try {
				new PrayerDao().persist(prayer);
				txtPrayerTopic.getScene().getWindow().hide();
			} catch (Exception e) {
				new Alert(AlertType.ERROR, "Somthng went Wrong").showAndWait();
			}
			mainPrayerApp.getMainPrayerController().refreshTableView();
		}

	}

	private boolean preCheck() {
		if (txtPrayerTopic.getText().isEmpty()) {
			new Alert(AlertType.ERROR, "Pleas Enter a Topic").showAndWait();
			return false;
		}
		if (txtPrayerDescription.getText().isEmpty()) {
			new Alert(AlertType.ERROR, "Pleas Enter a Description").showAndWait();
			return false;
		}
		return true;
	}

	private void fillSpinner() {
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
		spImportance.setValueFactory(valueFactory);
	}

	private Person getPerson() {
		String[]  split1 = cbPersons.getValue().split("\\[");
		String[]  split2 = split1[1].split("\\]");
		Person p = new PersonDao().findById(Integer.valueOf(split2[0]));
		return p;
	}

}
