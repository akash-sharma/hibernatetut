package com.akash.hibernate.hibernatetut.singledomain;

import java.util.List;

import org.hibernate.Session;

public class SingleDomainImpl
{
	public void savePerson()
	{
		String name="first user name";
		String updatedName="updated user name";
		saveSinglePerson(name);
    	readSinglePersonByGet();
    	try{
    		readSinglePersonByLoad();
    	}catch(Exception e){}
//    	updatePersonViaSaveOrUpdate(name, updatedName);
    	updatePersonViaExecuteUpdate(name, updatedName);
    	deletePersonByName(updatedName);
//    	deletePerson();
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
        	System.out.println("name in Get : "+person.getUsername());
        	System.out.println("id in Get : "+person.getPersonId());
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
        	System.out.println("name in load : "+person.getUsername());
        	System.out.println("id in load : "+person.getPersonId());
        }
        session.getTransaction().commit();
        session.close();
    }
    
    private void saveSinglePerson(String name)
    {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Person person = new Person();
        person.setUsername(name);
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
    
    private void updatePersonViaSaveOrUpdate(String name, String updatedName)
    {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	List personList=session.createQuery("from person_entity where person_name=:name").setParameter("name", name).list();
    	if( personList.size()!=0 )
    	{
    		Person person=(Person)personList.get(0);
    		person.setUsername(updatedName);
    		session.saveOrUpdate(person);
    	}
    	session.getTransaction().commit();
    	session.close();
    }
    
    private void updatePersonViaExecuteUpdate(String name, String updatedName)
    {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	session.createQuery("UPDATE person_entity set person_name=:updatedName where person_name=:name").setParameter("name", name).setParameter("updatedName", updatedName).executeUpdate();
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