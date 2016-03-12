package com.akash.hibernate.hibernatetut.time;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CustomDateEntity {

	@Id @GeneratedValue
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeInstance;

	@Temporal(TemporalType.DATE)
//	@org.springframework.format.annotation.DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date dob;

	public Date getTimeInstance() {
		return timeInstance;
	}

	public void setTimeInstance(Date timeInstance) {
		this.timeInstance = timeInstance;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
