package com.akash.hibernate.hibernatetut.inheritence;

import javax.persistence.Entity;

@Entity
public class TwoWheeler extends MovableVehicle
{
	private int rpm;
	public int getRpm() {
		return rpm;
	}
	public void setRpm(int rpm) {
		this.rpm = rpm;
	}
}