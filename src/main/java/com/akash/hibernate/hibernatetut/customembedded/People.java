package com.akash.hibernate.hibernatetut.customembedded;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.akash.hibernate.hibernatetut.embedded.Address;

@Entity(name="custom_people")
public class People
{
	@Id @GeneratedValue
	private int peopleId;
	private String name;
	
	@AttributeOverrides({
		@AttributeOverride(name="houseNo", column = @Column(name="office_house_no")),
		@AttributeOverride(name="street", column = @Column(name="office_street")),
		@AttributeOverride(name="city", column = @Column(name="office_city")),
		@AttributeOverride(name="pincode", column = @Column(name="office_pin_code"))
	})
	private Address OfficeAddress;
	
	@AttributeOverrides({
		@AttributeOverride(name="houseNo", column = @Column(name="home_house_no")),
		@AttributeOverride(name="street", column = @Column(name="home_street")),
		@AttributeOverride(name="city", column = @Column(name="home_city")),
		@AttributeOverride(name="pincode", column = @Column(name="home_pin_code"))
	})
	private Address homeAddress;
	
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

	public Address getOfficeAddress() {
		return OfficeAddress;
	}

	public void setOfficeAddress(Address officeAddress) {
		OfficeAddress = officeAddress;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}
}