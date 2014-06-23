package com.akash.hibernate.hibernatetut;

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
}