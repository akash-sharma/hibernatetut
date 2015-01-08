package com.akash.hibernate.hs;

import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;


public class App {

	public static void main(String args[]) {
		createAdharCard();
		new HSSearch().createQuery();
	}
	
	private static void createAdharCard() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        AdharCardInfo adharCardInfo = new AdharCardInfo();
        adharCardInfo.setAdharNumber("1112");
        adharCardInfo.setCompanyName("abc");
        IndianCitizen person = new IndianCitizen();
        person.setAge(20);
        person.setGender(IndianCitizen.Gender.Male);
        person.setNabalik(true);
        person.setUsername("amitabh");
        adharCardInfo.setPerson(person);
        
        session.save(person);
        session.save(adharCardInfo);
        
        session.getTransaction().commit();
        session.close();
	}
}
