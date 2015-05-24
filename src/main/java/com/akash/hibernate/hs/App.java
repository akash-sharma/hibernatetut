package com.akash.hibernate.hs;

import org.hibernate.Session;
import org.joda.time.LocalDate;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class App {

	static String passage = "First analyze the doubling time in 8 days for the control cells to reach an almost confluent plate. Than determine whether you will stimulate (2 to x-fold) or inhibit the growth rate by the treatment. With respect to toxicity effects, you will find that sensitivity for compounds will increase by a longer term treatment at 1 to 3 days, becoming almost steady state between 5 and 7 days of treatment. We checked that for end point measurements with alamar blue and hoechst 33342 for toxic compounds. Check stability of the compound in culture. If unstable daily refreshment may be needed. If not, culturing can be performed with or without refreshment of the media. For growth analyses in 24 or 96-wells up to 4 days, no refreshment is needed for good inhibition with (stable) toxicants. If the number of cells at the start is not to high and the compound is stable, it even works for 8 days. However, an intermediate compound and media refreshment can be given after 4 days of treatment. Just adjust the cell number seeded appropriate for your setting.";

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
		person.setDob(new LocalDate("2005-11-12"));
		person.setUsername("amitabh");
		person.setGeneralInfo(passage);
		adharCardInfo.setPerson(person);

		session.save(person);
		session.save(adharCardInfo);

		session.getTransaction().commit();
		session.close();
	}
}
