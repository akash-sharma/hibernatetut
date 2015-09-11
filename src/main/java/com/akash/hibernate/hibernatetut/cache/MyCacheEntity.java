package com.akash.hibernate.hibernatetut.cache;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="PersonCache")
@Entity
@NamedQuery(
	    name="findEntityByName",
	    query="SELECT ce FROM MyCacheEntity ce where ce.name=:name",
		hints={@QueryHint(name="org.hibernate.cacheable",value="true")}
	)
public class MyCacheEntity {

	@Id
	@GeneratedValue
	private long id;
	
	private String name;

	private int age;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}