package com.akash.hibernate.hibernatetut.onetoone.lazy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WorkerProfile {

	@Id
	@GeneratedValue
	private Long id;

	private String phone;

	// can be more fields for worker profile

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}