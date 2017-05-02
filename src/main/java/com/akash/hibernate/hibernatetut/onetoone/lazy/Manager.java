package com.akash.hibernate.hibernatetut.onetoone.lazy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Manager {

	@Id
	private Long id;

	private String name;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "manager")
	private ManagerProfile profile;

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

	public ManagerProfile getProfile() {
		return profile;
	}

	public void setProfile(ManagerProfile profile) {
		this.profile = profile;
	}
}