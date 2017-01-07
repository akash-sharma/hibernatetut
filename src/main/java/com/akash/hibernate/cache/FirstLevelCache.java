package com.akash.hibernate.cache;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class FirstLevelCache {

	public static void main(String args[]) throws InterruptedException {

		String name = "akash";
		int age = 20;
		//createMyCacheEntity(name, age);
		//createMyCacheEntity(name, age + 10);
		//getEntity(name);
		getCacheEntity();
		HibernateUtil.shutdown();
	}
	
	private static void getEntity(String name) {

		Query queryEntityByName = (Query) HibernateUtil.getSessionFactory()
				.openSession().getNamedQuery("findEntityByName");
		queryEntityByName.setParameter("name", name);
		List<MyCacheEntity> entityList = queryEntityByName.list();
		if (entityList != null) {
			for (MyCacheEntity ob : entityList) {
				printObject(ob);
			}
		}
	}

	private static void getCacheEntity() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		System.out.println("applying get...");
		//printObject((MyCacheEntity) session.get(MyCacheEntity.class, 1L));
		//printObject((MyCacheEntity) session.get(MyCacheEntity.class, 1L));
		System.out.println("applying load...");
		printObject((MyCacheEntity) session.load(MyCacheEntity.class, 1L));
		//printObject((MyCacheEntity) session.load(MyCacheEntity.class, 2L));
		session.getTransaction().commit();
	}

	private static void createMyCacheEntity(String name, int age) {

		MyCacheEntity ob = new MyCacheEntity();
		ob.setAge(age);
		ob.setName(name);
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(ob);
		session.flush();
		session.getTransaction().commit();
	}
	
	private static void printObject(MyCacheEntity ob) {

		Date date = new Date();
		System.out.println("<===Start : " + date.getTime() + "===>");
		System.out.println(ob);
		//System.out.println(ob.getId());
		//System.out.println(ob.getAge());
		//System.out.println(ob.getName());
		System.out.println("<===End===>");
	}
}