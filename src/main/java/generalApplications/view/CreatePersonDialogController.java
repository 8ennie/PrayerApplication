
package generalApplications.view;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import database.Person;
import database.PersonType;
import databaseDAO.PersonDao;
import databaseDAO.PersonTypeDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * @author bcwie
 *
 */
public class CreatePersonDialogController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private RadioButton rbMale;

	@FXML
	private ToggleGroup tgGender;

	@FXML
	private RadioButton rbFemale;

	@FXML
	private RadioButton rbOther;

	@FXML
	private TextArea txtRemark;

	@FXML
	private TextField txtLastName;

	@FXML
	private TextField txtFirstName;

	@FXML
	private ComboBox<String> cbExistingPersons;

	@FXML
	private TextField txtPhoneNumber;

	@FXML
	private TextField txtEmailAdresse;

	@FXML
	private DatePicker dpBirthday;

	@FXML
	private Label lblTitel;
	
	@FXML
    private Label lblError;
	
	private Person person;
	
	@FXML
	void initialize() {
		assert rbMale != null : "fx:id=\"rbMale\" was not injected: check your FXML file 'CreatePersonDialog.fxml'.";
		assert tgGender != null : "fx:id=\"tgGender\" was not injected: check your FXML file 'CreatePersonDialog.fxml'.";
		assert rbFemale != null : "fx:id=\"rbFemale\" was not injected: check your FXML file 'CreatePersonDialog.fxml'.";
		assert rbOther != null : "fx:id=\"rbOther\" was not injected: check your FXML file 'CreatePersonDialog.fxml'.";
		assert txtRemark != null : "fx:id=\"txtRemark\" was not injected: check your FXML file 'CreatePersonDialog.fxml'.";
		assert txtLastName != null : "fx:id=\"txtLastName\" was not injected: check your FXML file 'CreatePersonDialog.fxml'.";
		assert txtFirstName != null : "fx:id=\"txtFirstName\" was not injected: check your FXML file 'CreatePersonDialog.fxml'.";
		assert cbExistingPersons != null : "fx:id=\"cbExistingPersons\" was not injected: check your FXML file 'CreatePersonDialog.fxml'.";
		assert txtPhoneNumber != null : "fx:id=\"txtPhoneNumber\" was not injected: check your FXML file 'CreatePersonDialog.fxml'.";
		assert txtEmailAdresse != null : "fx:id=\"txtEmailAdresse\" was not injected: check your FXML file 'CreatePersonDialog.fxml'.";
		assert dpBirthday != null : "fx:id=\"dpBirthday\" was not injected: check your FXML file 'CreatePersonDialog.fxml'.";
		assert lblTitel != null : "fx:id=\"lblTitel\" was not injected: check your FXML file 'CreatePersonDialog.fxml'.";
		assert lblError != null : "fx:id=\"lblError\" was not injected: check your FXML file 'CreatePersonDialog.fxml'.";
		
		dpBirthday.getEditor().setDisable(true);
		dpBirthday.getEditor().setEditable(false);
		
	}

	
	public void setUp() {
		fillCBExistingPersons();
	}
	

	private void fillCBExistingPersons() {
		List<Person> pList = new PersonDao().getPersonByTypeNot(new PersonTypeDao().getPersonType("PrayFor").getPerosnTypeId());
		for (Person person : pList) {
			cbExistingPersons.getItems().add(person.getLastname() + " " + person.getFirstname() + "[" +person.getPerson_id() + "]");
		}
	}
	
	private boolean preCheckPerson() {
    	if(txtFirstName.getText().equals("")) {
    		error("Please Enter the First Name");
    		return false;
    	}
    	if(txtLastName.getText().equals("")) {
    		error("Please Enter the Last Name");
    		return false;
    	}
    	if(tgGender.getSelectedToggle() == null) {
    		error("Please Select a Gender");
    		return false;
    	}
    	lblError.setVisible(false);
    	return true;
    }
	
	private void error(String error) {
		lblError.setText("An Error Ocored: " + error);
		lblError.setVisible(true);
	}
	
	
	@FXML
	private void addPerson() {	
		PersonDao pDao = new PersonDao();
		if(person == null) {
 			if(preCheckPerson()) {
				List<Person> pList = pDao.findByLastName(txtLastName.getText());
				if(!pList.isEmpty()) {
					for (Person p : pList) {
						if(p.getFirstname().equals(txtFirstName.getText())) {
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Confirmation Dialog");
							alert.setHeaderText("You Already have a Person with the same Firstname and Lastname");
							alert.setContentText("Do You want to Add the Person Anyway?");
							
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK){
							    // ... user chose OK
							} else {
							    // ... user chose CANCEL or closed the dialog
								return;
							}
						}
					}	
				}
				person = new Person();
				person.setFirstname(txtFirstName.getText());
				person.setLastname(txtLastName.getText());
				person.setRemark(txtRemark.getText());
				if(tgGender.getSelectedToggle() == rbMale) {
					person.setGender("Male");
				}else if(tgGender.getSelectedToggle() == rbFemale) {
					person.setGender("Female");
				}else{
					person.setGender("Other");
				}
				if(dpBirthday.getValue() != null) {
					person.setBirthday(java.sql.Date.valueOf(dpBirthday.getValue()));
				}
				if(!(txtEmailAdresse.getText().equals(""))) {
					person.setEmailadress(txtEmailAdresse.getText());
				}
				if(!(txtPhoneNumber.getText().equals(""))) {
					person.setPhoneNumber(txtPhoneNumber.getText());;
				}
			}else {
				return;
			}
		} 
		try {
			PersonType pT = new PersonTypeDao().getPersonType("PrayFor");
			pT.addPerson(person);
			person.addPersonType(pT);
//			pDao.merge(person);
			pDao.persist(person);
			new Alert(AlertType.INFORMATION, "Person Added Successfully!").showAndWait();
			
		} catch (Exception e) {
			new Alert(AlertType.INFORMATION, "Problem!").showAndWait();
			// TODO: handle exception
		}
		
		exit();
	}
	
	@FXML
	private void exit() {
		this.txtFirstName.getScene().getWindow().hide();
	}
	
	
	@FXML
	private void onSelPerson() {
		if(cbExistingPersons.getValue() != null) {
			person = getSelPerson(cbExistingPersons.getValue());
			
			txtFirstName.setText(person.getFirstname());
			txtLastName.setText(person.getLastname());
			txtRemark.setText(person.getRemark());
			switch (person.getGender()) {
			case "Male":
				rbMale.setSelected(true);
				break;
			case "Female":
				rbFemale.setSelected(true);
				break;
			case "Other":
				rbOther.setSelected(true);
				break;
			default:
				break;
			}
			tgGender.selectedToggleProperty();
			dpBirthday.setValue(Instant.ofEpochMilli(person.getBirthday().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
			txtPhoneNumber.setText(person.getPhoneNumber());
			txtEmailAdresse.setText(person.getEmailadress());
		}
	}
		
	private Person getSelPerson(String sel) {
		String[]  split1 = sel.split("\\[");
		String[]  split2 = split1[1].split("\\]");
		Person p = new PersonDao().findById(Integer.valueOf(split2[0]));
		return p;
	}
	
}

