package com.akash.hibernate.cache;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.metadata.ClassMetadata;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class CacheMain {

	// test 1st level cache by using get() and load() method

	public static void main(String args[]) throws InterruptedException {

		String name = "akash";
		int age = 20;
		createMyCacheEntity(name, age);
		createMyCacheEntity(name, age + 10);
		int counter = 0;
		while (true) {
			counter++;
			Thread.sleep(200);
			getEntity(name);
			if (counter % 3 == 0) {
				// evict all entity cache regions
				evictAllEntityRegion();
			}
			if (counter % 6 == 0) {
				// evict all entity and query cache
				evictAllRegion();
			}
			if (counter == 14) {
				System.exit(0);
			}
		}
	}

	private static void evictAllEntityRegion() {

		SessionFactory factory = HibernateUtil.getSessionFactory();
		try {
			Map<String, ClassMetadata> classesMetadata = factory
					.getAllClassMetadata();
			for (String entityName : classesMetadata.keySet()) {
				Class<?> clazz = Class.forName(entityName);
				if (clazz.isAnnotationPresent(Cache.class)) {
					System.out.println("Evicting Entity from 2nd level cache: "
							+ entityName);
					factory.getCache().evictEntityRegion(entityName);
				}
			}
		} catch (Exception e) {
			System.out
					.println("Error evicting 2nd level hibernate cache entities: "
							+ e);
		}
	}

	private static void evictAllRegion() {

		SessionFactory factory = HibernateUtil.getSessionFactory();
		factory.getCache().evictAllRegions();
	}

	private static void createMyCacheEntity(String name, int age) {

		MyCacheEntity ob = new MyCacheEntity();
		ob.setAge(age);
		ob.setName(name);
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(ob);
		session.getTransaction().commit();
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

	private static void printObject(MyCacheEntity ob) {

		Date date = new Date();
		System.out.println("<===Start : " + date.getTime() + "===>");
		System.out.println(ob);
		System.out.println(ob.getId());
		System.out.println(ob.getAge());
		System.out.println(ob.getName());
		System.out.println("<===End===>");
	}
}
