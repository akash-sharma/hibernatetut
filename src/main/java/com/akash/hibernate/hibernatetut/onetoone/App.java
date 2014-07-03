package com.akash.hibernate.hibernatetut.onetoone;

public class App
{
	public static void main(String args[])
	{
		new OneToOneMapping().operationOnOneToOne();
	}
}

//Caused by: org.hibernate.MappingException: Could not determine type for: com.akash.hibernate.hibernatetut.onetoone.Vehicle, at table: Person, for columns: [org.hibernate.mapping.Column(vehicle)]

//Exception in thread "main" org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.akash.hibernate.hibernatetut.onetoone.Vehicle

/**
 * <<Saving Person with Vehicle>>
 * 
 * Hibernate: insert into Person (personName, vehicle_id) values (?, ?)
 * Hibernate: insert into Vehicle (vehicleName) values (?)
 * Hibernate: update Person set personName=?, vehicle_id=? where id=?
 * 
 * 
 * <<Saving Person without Vehicle>>
 * 
 * Hibernate: insert into Person (personName, vehicle_id) values (?, ?)
 * 
 * 
 * NOTE:
 * When we apply OnToOne mapping from A to B, the B is Nullable true in class A.
 * 
 * NOTE:
 * When we apply OneToOne mapping from A to B and we delete object of A,
 * then object of B is not deleted automatically.
 * 
 */