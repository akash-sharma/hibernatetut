package com.akash.hibernate.hibernatetut.manytomanyoneside;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TeamUser
{
	@Id
	private int id;
	private String username;
	private boolean isTeamLead;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isTeamLead() {
		return isTeamLead;
	}
	public void setTeamLead(boolean isTeamLead) {
		this.isTeamLead = isTeamLead;
	}
}