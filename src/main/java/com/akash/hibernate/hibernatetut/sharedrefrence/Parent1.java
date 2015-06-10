package com.akash.hibernate.hibernatetut.sharedrefrence;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Parent1 {

	@Id @GeneratedValue
	int id;
	
	private String abc;
	
	@OneToMany
	@JoinTable(
	        name="parent1_master",
	        joinColumns=
	            @JoinColumn(name="parent_ID", referencedColumnName="ID"),
	        inverseJoinColumns=
	            @JoinColumn(name="master_ID", referencedColumnName="ID")
	    )
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