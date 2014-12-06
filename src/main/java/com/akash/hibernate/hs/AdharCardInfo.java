package com.akash.hibernate.hs;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
public class AdharCardInfo {

	@Id
	@GeneratedValue
	private long id;
	
	@Field(store=Store.YES , index=Index.YES )
	private String adharNumber;
	
	@OneToOne
	@IndexedEmbedded
	private IndianCitizen person;
	
	private String companyName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAdharNumber() {
		return adharNumber;
	}

	public void setAdharNumber(String adharNumber) {
		this.adharNumber = adharNumber;
	}

	public IndianCitizen getPerson() {
		return person;
	}

	public void setPerson(IndianCitizen person) {
		this.person = person;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}