package com.akash.hibernate.hibernatetut.onetoone.lazy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Worker {

	@Id
	private Long id;

	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id")
	private WorkerProfile profile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WorkerProfile getProfile() {
		return profile;
	}

	public void setProfile(WorkerProfile profile) {
		this.profile = profile;
	}
}