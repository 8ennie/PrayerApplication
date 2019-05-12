package prayer.model;

import java.time.LocalDateTime;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PrayerM {

	private final IntegerProperty prayer_id;
	private final StringProperty topic;
	private final StringProperty persons;
    private final StringProperty prayerDescription;
    private final ObjectProperty<LocalDateTime> dueDate;
    
	public PrayerM(int prayer_id, String topic, String prayerDescription,String persons, LocalDateTime date) {
		this.prayer_id = new SimpleIntegerProperty(prayer_id);
		this.topic = new SimpleStringProperty(topic);
		this.prayerDescription = new SimpleStringProperty(prayerDescription);
		this.persons = new SimpleStringProperty(persons);
		this.dueDate = new SimpleObjectProperty<LocalDateTime>(date);
		
	}

	

	public StringProperty getTopic() {
		return topic;
	}


	public StringProperty getPrayerDescription() {
		return prayerDescription;
	}


	public IntegerProperty getPrayer_id() {
		return prayer_id;
	}


	public ObjectProperty<LocalDateTime> getDueDate() {
		return dueDate;
	}


	public StringProperty getPersons() {
		return persons;
	}
}
