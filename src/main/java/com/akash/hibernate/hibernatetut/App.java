package com.akash.hibernate.hibernatetut;

import org.hibernate.Session;

public class App
{
    public static void main( String[] args )
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
 
        session.beginTransaction();
        Person person = new Person();
        person.setUsername("first user name");
        session.save(person);
        session.getTransaction().commit();
    }
}