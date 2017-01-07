package com.akash.hibernate.hibernatetut.onetoone;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class OneToOneMapping
{
	public void operationOnOneToOne()
	{
		createPerson();
//		selectDataAsPojo();
		//deletePerson();
		readPerson();
		HibernateUtil.shutdown();
	}
	
	private void createPerson()
	{
		Vehicle vehicle=new Vehicle();
		vehicle.setVehicleName("Car");
		
		Person person1=new Person();
		person1.setPersonName("Ram");
		person1.setVehicle(vehicle);
		
		Person person2=new Person();
		person2.setPersonName("Shyam");
	
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(person1);
        session.save(person2);
        session.save(vehicle);
        session.getTransaction().commit();
        session.clear();
	}
	
	private void readPerson() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Person person = (Person) session.get(Person.class, 1);
        session.getTransaction().commit();
        session.clear();
	}
	
	private void selectDataAsPojo() {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String queryString="Select new com.akash.hibernate.hibernatetut.onetoone.PersonPojo(p.personName, p.id, p.vehicle.vehicleName) from Person p";
        Query query = session.createQuery(queryString);
        List<PersonPojo> personList = query.list();
        for(PersonPojo person : personList) {
        	System.out.println("name : "+person.getPersonName());
        }
	}
	
	
	
	private void deletePerson()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String queryString="from Person where personName=:name";
        Query query= session.createQuery(queryString);
        query.setParameter("name", "Ram");
        Person person=(Person)query.list().get(0);
        session.delete(person);
        session.getTransaction().commit();
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
* (1)Let say we have 2 Entity classes A and B and
* 		we create object of B as instance variable in A
* 		without any mapping, then MappingException will occur.
* 
* (2) When we apply OnToOne mapping from A to B, 
* 		the B is Nullable true in class A.
* 
* (3) When we apply OneToOne mapping from A to B and
* 		we delete object of A,
* 		then object of B is not deleted automatically.
* 
* 
*/