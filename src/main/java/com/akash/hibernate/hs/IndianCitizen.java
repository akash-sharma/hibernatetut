package com.akash.hibernate.hs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.joda.time.LocalDate;

@Entity
public class IndianCitizen {

	@Id
	@GeneratedValue
	private long id;

	@Field
	private String username;

	@Field
	private boolean isNabalik;

	@Field
	private int age;

	@Field
	@FieldBridge(impl = LocalDateBridge.class)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate dob;

	@Field
	private Gender gender;

	@Column(length = 500)
	@Field
	private String generalInfo;

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getGeneralInfo() {
		return generalInfo;
	}

	public void setGeneralInfo(String generalInfo) {
		this.generalInfo = generalInfo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isNabalik() {
		return isNabalik;
	}

	public void setNabalik(boolean isNabalik) {
		this.isNabalik = isNabalik;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	static enum Gender {
		Male("Male"), Female("Female");

		private String value;

		Gender(String value) {
			this.value = value;
		}
	}
}