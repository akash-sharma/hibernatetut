package com.akash.hibernate.hibernatetut.singledomain;

import java.util.List;

import org.hibernate.Session;

public class SingleDomain
{
	public void savePerson()
	{
		saveSinglePerson();
    	readSinglePersonByGet();
    	try{
    		readSinglePersonByLoad();
    	}catch(Exception e){}
    	deletePersonByName("first user name");
    	deletePerson();
	}
	
	/**
     * get
     * session.get() method is fetches data from DB
     * return null if no match found
     */
    private void readSinglePersonByGet()
    {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Person person=(Person)session.get(Person.class, 1);
        if(person!=null)
        {
        	System.out.println("name : "+person.getUsername());
        	System.out.println("id : "+person.getPersonId());
        }
        session.getTransaction().commit();
        session.close();
    }
    
    /**
     * load
     * session.load() method first checks in cache and then in DB
     * throws ObjectNotFoundException when no match found
     * load() method may return a proxy object
     */
    private void readSinglePersonByLoad()
    {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Person person=(Person)session.load(Person.class, 2);
        if(person!=null)
        {
        	System.out.println("name : "+person.getUsername());
        	System.out.println("id : "+person.getPersonId());
        }
        session.getTransaction().commit();
        session.close();
    }
    
    private void saveSinglePerson()
    {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Person person = new Person();
        person.setUsername("first user name");
        session.save(person);
        session.getTransaction().commit();
        session.close();
    }
    
    private void deletePerson()
    {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Person person =(Person)session.get(Person.class, 1);
        if(person!=null)
        	session.delete(person);
        session.getTransaction().commit();
        session.close();
    }
    
    private void deletePersonByName(String name)
    {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List personList=session.createQuery("from person_entity where person_name=:name").setParameter("name", name).list();
        if(personList.size()>1)
        {
        	Person person=(Person)personList.get(0);
        	session.delete(person);
        }
        session.getTransaction().commit();
        session.close();
    }
}