package com.akash.hibernate.hibernatetut.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class CreateDate {

	public static final String DATE_FORMAT = "MM/dd/yyyy";

	public void persistDate() {

		CustomDateEntity cde = new CustomDateEntity();
		cde.setDob(convertDateFromString("01/01/1990"));
		cde.setTimeInstance(new Date());

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(cde);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}
	
	public void compareDate() {
		
	}

	public Date convertDateFromString(String stringDate) {

		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		try {
			return formatter.parse(stringDate);
		} catch (ParseException e) {
		}
		return null;
	}
}