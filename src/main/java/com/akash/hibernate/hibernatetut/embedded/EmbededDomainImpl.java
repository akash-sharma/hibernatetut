package com.akash.hibernate.hibernatetut.embedded;

import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class EmbededDomainImpl
{
	public void operatePersonAndAddr()
	{
		savePersonWithAddr();
		readPersonWithAddr();
	}
	
	private void savePersonWithAddr()
	{
		Address address=new Address();
		address.setCity("delhi");
		address.setHouseNo("122");
		address.setPincode("112211");
		address.setStreet("mg road");
		
		People people = new People();
        people.setName("first people");
        people.setAddress(address);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(people);
        session.getTransaction().commit();
	}
	
	private void readPersonWithAddr()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		People people=(People)session.get(People.class, 1);
		System.out.println("people : "+people);
		if(people!=null)
		{
			Address address=people.getAddress();
			System.out.println("Id : "+people.getPeopleId());
			System.out.println("name : "+people.getName());
			System.out.println("HouseNo : "+address.getHouseNo());
		}
		session.close();
	}
}