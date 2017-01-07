package com.akash.hibernate.hibernatetut.onetoone;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Person
{
	public Person() {
		
	}
	
	public Person(String personName,Integer id, String vehicleName) {
		
		this();
		this.personName=personName;
		this.id=id;
		this.vehicle = new Vehicle();
		this.vehicle.setVehicleName(vehicleName);
	}
	
	@Id @GeneratedValue
	private int id;
	private String personName;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="person")
	private Vehicle vehicle;
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
}