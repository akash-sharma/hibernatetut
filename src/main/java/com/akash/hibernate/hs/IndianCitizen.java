package com.akash.hibernate.hs;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.search.annotations.Field;

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
	private Date dob;
	
	@Field
	private Gender gender;
	
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
		Male("Male"),
		Female("Female");
		
		private String value;
		
		Gender(String value) {
			this.value=value;
		}
	}
}