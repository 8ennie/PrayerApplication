package database;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Prayer implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prayer_id;
	
	@Column(nullable=false)
	private String topic;
	
	@Column(nullable=false)
	private String prayerDescription;
	
	@Column
	private int prayCount;
	
	@Column(nullable=false)
	private int importance;
	
	@Column
	private String notes;
	
	@Column
	private boolean answered;
	
	@Temporal(TemporalType.DATE)
	private Date dateAnswered;
	
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	
	@Column
	private LocalDateTime dueDate;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<Person> person = new ArrayList<Person>();

	@ManyToOne(cascade = CascadeType.PERSIST)
	private User prayerCreator = new User();
	
	
	public String getTopic() {
		return topic;
	}

	public String getPrayerDescription() {
		return prayerDescription;
	}

	public int getPrayCount() {
		return prayCount;
	}

	public int getImportance() {
		return importance;
	}

	public String getNotes() {
		return notes;
	}

	public boolean isAnswered() {
		return answered;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public List<Person> getPerson() {
		return person;
	}

	public User getPrayerCreator() {
		return prayerCreator;
	}

	public void setPrayerCreator(User prayerCreator) {
		this.prayerCreator = prayerCreator;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setPrayerDescription(String prayerDescription) {
		this.prayerDescription = prayerDescription;
	}

	public void setPrayCount(int prayCount) {
		this.prayCount = prayCount;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public void setPerson(List<Person> person) {
		this.person = person;
	}

	public void setPerson(Person person) {
		this.person.add(person);
	}
	
	public Date getDateAnswered() {
		return dateAnswered;
	}

	public void setDateAnswered(Date dateAnswered) {
		this.dateAnswered = dateAnswered;
	}

	public int getPrayer_id() {
		return prayer_id;
	}

	
}















