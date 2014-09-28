package com.akash.hibernate.hibernatetut.manytomanyoneside;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Team
{
	@Id
	private int id;
	private String teamName;
	
	@ManyToMany
	private Set<TeamUser> users;
	
	@ManyToOne
	private TeamUser teamLead;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Set<TeamUser> getUsers() {
		return users;
	}

	public void setUsers(Set<TeamUser> users) {
		this.users = users;
	}

	public TeamUser getTeamLead() {
		return teamLead;
	}

	public void setTeamLead(TeamUser teamLead) {
		this.teamLead = teamLead;
	}
}