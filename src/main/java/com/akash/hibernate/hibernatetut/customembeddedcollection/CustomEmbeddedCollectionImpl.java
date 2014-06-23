package com.akash.hibernate.hibernatetut.customembeddedcollection;

import java.util.ArrayList;

import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.embedded.Address;
import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class CustomEmbeddedCollectionImpl
{
	public void runEmbeddedCollection()
	{
		saveEmbeddedCollection();
		readEmbeddedCollectionForLazy();
		readEmbeddedCollectionForEager();
	}
	
	private void saveEmbeddedCollection()
	{
		ArrayList<Address> addresslist=new ArrayList<Address>();
		Address address=new Address();
		address.setCity("delhi");
		address.setHouseNo("122");
		address.setPincode("112211");
		address.setStreet("mg road");
		addresslist.add(address);
		
		address=new Address();
		address.setCity("mumbai");
		address.setHouseNo("141");
		address.setPincode("332267");
		address.setStreet("mg road");
		addresslist.add(address);
		
		People people = new People();
        people.setName("first people");
        people.setAddressList(addresslist);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(people);
        session.getTransaction().commit();
	}
	
	private void readEmbeddedCollectionForLazy()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		People people=(People)session.get(People.class, 1);
		System.out.println("id : "+people.getPeopleId());
		System.out.println("name : "+people.getName());
		ArrayList<Address> addresslist=new ArrayList( people.getAddressList());
		System.out.println("size of address list : "+addresslist.size());
		session.close();
	}
	
	private void readEmbeddedCollectionForEager()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		People people=(People)session.get(People.class, 1);
		System.out.println("id : "+people.getPeopleId());
		System.out.println("name : "+people.getName());
		session.close();
		try{
			System.out.println("size of address list : "+people.getAddressList().size());
		}
		catch(Exception e){
			System.out.println("problem occured : "+e);
		}
	}
}