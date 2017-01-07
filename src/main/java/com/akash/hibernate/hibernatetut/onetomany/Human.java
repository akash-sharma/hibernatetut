package com.akash.hibernate.hibernatetut.onetomany;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;

@NamedEntityGraph(name = "graph.human", 
	attributeNodes = { 
		@NamedAttributeNode(value = "vehicleList", subgraph = "tyresGraph") 
	}, 
		subgraphs = { 
			@NamedSubgraph(name = "tyresGraph", 
					attributeNodes = { @NamedAttributeNode(value = "tyres") }) 
			})
@Entity
public class Human {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Vehicle> vehicleList = new HashSet<Vehicle>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Vehicle> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(Set<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}
}