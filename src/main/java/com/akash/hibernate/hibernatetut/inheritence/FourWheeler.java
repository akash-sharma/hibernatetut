package com.akash.hibernate.hibernatetut.inheritence;

import javax.persistence.Entity;

@Entity
public class FourWheeler extends MovableVehicle
{
	private int enginePower;
	public int getEnginePower() {
		return enginePower;
	}
	public void setEnginePower(int enginePower) {
		this.enginePower = enginePower;
	}
}