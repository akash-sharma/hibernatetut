package com.akash.hibernate.hibernatetut.onetoone;


import org.hibernate.Query;
import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class OneToOneMapping
{
	public void operationOnOneToOne()
	{
		createPerson();
		deletePerson();
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