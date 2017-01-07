package com.akash.hibernate.hibernatetut.singledomain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

	private static EntityManagerFactory emfactory;

	public static EntityManager getEntitymanager() {
		if (emfactory == null) {
			synchronized (JpaUtil.class) {
				if (emfactory == null) {
					System.setProperty("org.jboss.logging.provider", "jdk");
					emfactory = Persistence.createEntityManagerFactory("JPA_TUTORIAL");
				}
			}
		}
		return emfactory.createEntityManager();
	}

	public static void close() {
		if (emfactory != null) {
			emfactory.close();
		}
	}
}