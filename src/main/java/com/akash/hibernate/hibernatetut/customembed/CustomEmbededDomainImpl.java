package com.akash.hibernate.hibernatetut.customembed;

import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.embedded.Address;
import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class CustomEmbededDomainImpl
{
	public void operatePersonAndAddr()
	{
		savePersonWithAddr();
		readPersonWithAddr();
	}
	
	private void savePersonWithAddr()
	{
		Address homeAddress=new Address();
		homeAddress.setCity("delhi");
		homeAddress.setHouseNo("122");
		homeAddress.setPincode("112211");
		homeAddress.setStreet("mg road");
		
		Address officeAddress=new Address();
		officeAddress.setCity("noida");
		officeAddress.setHouseNo("342");
		officeAddress.setPincode("334511");
		officeAddress.setStreet("nm road");
		
		People people = new People();
        people.setName("first people");
        people.setHomeAddress(homeAddress);
        people.setOfficeAddress(officeAddress);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(people);
        session.getTransaction().commit();
	}
	
	private void readPersonWithAddr()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		People people=(People)session.get(People.class, 1);
		if(people!=null)
		{
			Address address=people.getHomeAddress();
			System.out.println("Id : "+people.getPeopleId());
			System.out.println("name : "+people.getName());
			System.out.println("HouseNo : "+address.getHouseNo());
		}
		session.close();
	}
}