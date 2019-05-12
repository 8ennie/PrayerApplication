package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class PersonGroup implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int personGroup_id;
	
	@Column(nullable=false)
	private String groupName;
	
	@Column
	private String groupDescription;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<Person> person = new ArrayList<Person>();

	public int getGroup_id() {
		return personGroup_id;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public List<Person> getPerson() {
		return person;
	}

	public void setGroup_id(int group_id) {
		this.personGroup_id = group_id;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public void setPerson(List<Person> person) {
		this.person = person;
	}
	
	
	
}
