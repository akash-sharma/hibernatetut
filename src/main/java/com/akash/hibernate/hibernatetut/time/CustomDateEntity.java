package com.akash.hibernate.hibernatetut.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CustomDateEntity {

	/**
	 * https://dzone.com/articles/deeper-look-java-8-date-and
	 * LocalDate , LocalTime , LocalDateTime ,ZoneId , DateTimeFormatter
	 * 
	 * @Temporal should only be set on a java.util.Date or java.util.Calendar property
	 * 
	 * LocalDate and LocalDateTime will be stored in DB but as BLOB
	 * 
	 * http://www.thoughts-on-java.org/persist-localdate-localdatetime-jpa/
	 * http://stackoverflow.com/questions/27750026/java-8-localdatetime-and-hibernate-4
	 * 
	 */
	
	@Id @GeneratedValue
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeInstance;

	@Temporal(TemporalType.DATE)
//	@org.springframework.format.annotation.DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date dob;
	
	private LocalDate dob2;
	
	private LocalDateTime timeInstance2;

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

	public LocalDate getDob2() {
		return dob2;
	}

	public void setDob2(LocalDate dob2) {
		this.dob2 = dob2;
	}

	public LocalDateTime getTimeInstance2() {
		return timeInstance2;
	}

	public void setTimeInstance2(LocalDateTime timeInstance2) {
		this.timeInstance2 = timeInstance2;
	}
}
