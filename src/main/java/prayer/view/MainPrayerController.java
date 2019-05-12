/**
 * 
 */
package prayer.view;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import database.Person;
import database.Prayer;
import databaseDAO.PersonDao;
import databaseDAO.PrayerDao;
import generalApplications.Session;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import prayer.MainPrayerApp;
import prayer.model.PrayerM;

/**
 * @author bcwie
 *
 */
public class MainPrayerController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="tbPrayer"
	private Tab tbPrayer; // Value injected by FXMLLoader

	@FXML // fx:id="lblWeek"
	private Label lblWeek; // Value injected by FXMLLoader

	@FXML // fx:id="btPray"
	private Button btPray; // Value injected by FXMLLoader

	@FXML // fx:id="btAddNewPrayer"
	private Button btAddNewPrayer; // Value injected by FXMLLoader

	@FXML // fx:id="tbPersonsToPrayFor"
	private Tab tbPersonsToPrayFor; // Value injected by FXMLLoader

	@FXML // fx:id="tvPersons"
	private TableView<Person> tvPersons; // Value injected by FXMLLoader

	@FXML // fx:id="tcFirstname"
	private TableColumn<Person, String> tcFirstname; // Value injected by FXMLLoader

	@FXML // fx:id="tcLastname"
	private TableColumn<Person, String> tcLastname; // Value injected by FXMLLoader

	@FXML // fx:id="tcBirthday"
	private TableColumn<Person, Date> tcBirthday; // Value injected by FXMLLoader

	@FXML // fx:id="tcRemarks"
	private TableColumn<Person, String> tcRemarks; // Value injected by FXMLLoader

	@FXML // fx:id="btaddPerson"
	private Button btaddPerson; // Value injected by FXMLLoader

	@FXML // fx:id="tvPrayers"
	private TableView<PrayerM> tvPrayers; // Value injected by FXMLLoader

	@FXML // fx:id="tcDate"
	private TableColumn<PrayerM, LocalDateTime> tcDate; // Value injected by FXMLLoader

	@FXML // fx:id="tcPerson"
	private TableColumn<PrayerM, String> tcPerson; // Value injected by FXMLLoader

	@FXML // fx:id="tcTopic"
	private TableColumn<PrayerM, String> tcTopic; // Value injected by FXMLLoader

	@FXML // fx:id="tcDescription"
	private TableColumn<PrayerM, String> tcDescription; // Value injected by FXMLLoader

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert tbPrayer != null : "fx:id=\"tbPrayer\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert lblWeek != null : "fx:id=\"lblWeek\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert tvPrayers != null : "fx:id=\"tvPrayers\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert tcDate != null : "fx:id=\"tcDate\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert tcPerson != null : "fx:id=\"tcPerson\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert tcTopic != null : "fx:id=\"tcTopic\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert tcDescription != null : "fx:id=\"tcDescription\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert btPray != null : "fx:id=\"btPray\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert btAddNewPrayer != null : "fx:id=\"btAddNewPrayer\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert tbPersonsToPrayFor != null : "fx:id=\"tbPersonsToPrayFor\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert tvPersons != null : "fx:id=\"tvPersons\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert tcFirstname != null : "fx:id=\"tcFirstname\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert tcLastname != null : "fx:id=\"tcLastname\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert tcBirthday != null : "fx:id=\"tcBirthday\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert tcRemarks != null : "fx:id=\"tcRemarks\" was not injected: check your FXML file 'MainPrayerView.fxml'.";
		assert btaddPerson != null : "fx:id=\"btaddPerson\" was not injected: check your FXML file 'MainPrayerView.fxml'.";

		tcDate.setStyle("-fx-alignment: CENTER;");
		tcPerson.setStyle("-fx-alignment: CENTER;");
		tcTopic.setStyle("-fx-alignment: CENTER;");
		tcDescription.setStyle("-fx-alignment: CENTER;");

		tcFirstname.setStyle("-fx-alignment: CENTER;");
		tcLastname.setStyle("-fx-alignment: CENTER;");
		tcBirthday.setStyle("-fx-alignment: CENTER;");
		tcRemarks.setStyle("-fx-alignment: CENTER;");

		tcTopic.setCellValueFactory(cellData -> cellData.getValue().getTopic());
		tcDescription.setCellValueFactory(cellData -> cellData.getValue().getPrayerDescription());
		tcPerson.setCellValueFactory(cellData -> cellData.getValue().getPersons());
		tcDate.setCellValueFactory(cellData -> cellData.getValue().getDueDate());

		tcFirstname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstname()));
		tcLastname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastname()));
		tcBirthday.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getBirthday()));
		tcBirthday.setCellFactory((TableColumn<Person, Date> column) -> {
			return new TableCell<Person, Date>() {
				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null || empty) {
						setText(null);
					} else {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						setText(format.format(item));
					}
				}
			};
		});
	}

	private ObservableList<PrayerM> prayerData = FXCollections.observableArrayList();

	private MainPrayerApp mainPrayerApp;

	public void setMainApp(MainPrayerApp mainPrayerApp) {
		this.mainPrayerApp = mainPrayerApp;
	}

	@FXML
	private void openManagePrayerView() {
		mainPrayerApp.showManagePrayerView();
	}

	private void loadTableView() {
		prayerData.clear();
		List<Prayer> pList = new PrayerDao().getByUser(Session.curentUser);
		for (Prayer prayer : pList) {
			String persons = "";
			if (!prayer.getPerson().isEmpty()) {
				for (Person person : prayer.getPerson()) {
					if (persons == "") {
						persons = person.getFirstname() + " " + person.getLastname();
					} else {
						persons = persons + "\n " + person.getFirstname() + " " + person.getLastname();
					}
				}
			}
			PrayerM pM = new PrayerM(prayer.getPrayer_id(), prayer.getTopic(), prayer.getPrayerDescription(), persons,
					prayer.getDueDate());
			prayerData.add(pM);
		}
		tvPrayers.setItems(prayerData);

		ObservableList<Person> personData = FXCollections.observableArrayList();
		personData.addAll(new PersonDao().getAll());
		tvPersons.setItems(personData);
	}

	@FXML
	public void refreshTableView() {
		loadTableView();
	}

	public void setUp() {
		loadTableView();
	}
}
