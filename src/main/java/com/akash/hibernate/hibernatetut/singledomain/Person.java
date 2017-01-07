package com.akash.hibernate.hibernatetut.singledomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="person_entity")
@Table(name="person_table")
public class Person
{
/**
 * (1) Acc to hibernate, any data that has to be saved in DB must have an annotation of either
 *     @Entity or @Embedded on its class level.
 * (2) An Entity class must have an @Id annotation on a field.Entity class must have a primary key.
 * (3) @GeneratedValue annotation provides the way values of Id field is generated. 
 * (4) @Table annotation specifies the name of the table at DB level and 
 *     @Entity specifies the name of the entity at hibernate level.
 * (5) @Column attribute is used to configure column of a table.
 *     Each data type of java class has a corresponding default data type in DB.
 *     We can also configure this type in @Column annotation.
 * (6) Any persistent object when get updated , will automatically be persisted to DB.
 * (7) Difference between session.load() and session.get()
 * (8) Session class => save() , saveOrUpdate() , merge() , update()
 * (9) EntityManager class => merge() , find() , persist() , flush()  
 */
	
	@Id @GeneratedValue @Column(name="person_id")
	int personId;
	
	@Column(name="person_name")
	String username;
	
	@Column(name="country ")
	private String country;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}