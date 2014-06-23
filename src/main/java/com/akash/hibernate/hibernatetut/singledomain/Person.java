package com.akash.hibernate.hibernatetut.singledomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="person_entity")
@Table(name="person_table")
public class Person
{
	@Id @GeneratedValue @Column(name="person_id")
	int personId;
	
	@Column(name="person_name")
	String username;

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}