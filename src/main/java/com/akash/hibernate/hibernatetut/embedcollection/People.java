package com.akash.hibernate.hibernatetut.embedcollection;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.akash.hibernate.hibernatetut.embedded.Address;

@Entity(name="people_collection")
public class People
{
	@Id @GeneratedValue
	private int peopleId;
	private String name;
	
	@ElementCollection
	private Collection<Address> addressList=new ArrayList<Address>();
	
	public int getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(int peopleId) {
		this.peopleId = peopleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(Collection<Address> addressList) {
		this.addressList = addressList;
	}
}