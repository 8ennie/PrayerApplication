package database;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
public class Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int person_id;
	@ManyToMany (cascade = CascadeType.PERSIST)
	private List<PersonType> personType = new ArrayList<PersonType>();
	@Column(nullable=false)
	private String lastname;
	@Column(nullable=false)
	private String firstname;
	@Temporal(TemporalType.DATE)
	private Date birthday;
	@Column()
	private String emailadress;
	@Column()
	private String phoneNumber;
	@Column()
	private String gender;
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<User> user;
	@Transient
	private int age;
	@Column()
	private String remark;	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Address address;
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<Prayer> prayer = new ArrayList<Prayer>();
	
	
	@PostLoad
	private void postLoad() {
		if(birthday != null) {
			LocalDate date = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			Period p = Period.between(LocalDate.now(), date);
			this.age = p.getYears();			
		}
	}
	
	public int getPerson_id() {
		return person_id;
	}
	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEmailadress() {
		return emailadress;
	}
	public void setEmailadress(String emailadress) {
		this.emailadress = emailadress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public List<User> getUser() {
		return this.user;
	}
	public void setUser(User newUser) {
		this.user.add(newUser);
	}
	public int getAge() {
		return age;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<PersonType> getPersonType() {
		return personType;
	}
	public void addPersonType(PersonType personType) {
		this.personType.add(personType);
	}
	public List<Prayer> getPrayer() {
		return prayer;
	}
	public void setPrayer(Prayer prayer) {
		this.prayer.add(prayer);
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Person [lastname=" + lastname + ", firstname=" + firstname + "]";
	}

	
	
}
