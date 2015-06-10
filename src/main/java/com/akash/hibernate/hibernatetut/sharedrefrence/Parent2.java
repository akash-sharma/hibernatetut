package com.akash.hibernate.hibernatetut.sharedrefrence;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Parent2 {

private String abc;

	@Id @GeneratedValue
	int id;

	@OneToMany
	@JoinTable()
	private Set<Master> listOfMaster;

	public String getAbc() {
		return abc;
	}

	public void setAbc(String abc) {
		this.abc = abc;
	}

	public Set<Master> getListOfMaster() {
		return listOfMaster;
	}

	public void setListOfMaster(Set<Master> listOfMaster) {
		this.listOfMaster = listOfMaster;
	}
}