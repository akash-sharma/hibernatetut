package com.akash.hibernate.hibernatetut.cache;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class CacheMain {
	
	//test 1st level cache by using get() and load() method

	public static void main(String args[]) throws InterruptedException {

		String name = "akash";
		int age = 20;
		MyCacheEntity ob = null;
		createMyCacheEntity(name, age);
		int counter = 0;
		while (true) {
			counter++;
			Thread.sleep(200);
			ob = getEntity(name);
			if(counter%3 == 0) {
				//evict cache here
			}
			printObject(ob);
			ob = null;
		}
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

	private static MyCacheEntity getEntity(String name) {

		Query queryEntityByName = (Query) HibernateUtil.getSessionFactory()
				.openSession().getNamedQuery("findEntityByName");
		queryEntityByName.setParameter("name", name);
		List<MyCacheEntity> entityList = queryEntityByName.list();
		if (entityList != null && entityList.size() != 0) {
			return entityList.get(0);
		}
		return null;
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


/**
 *	output with cache
	
	
Hibernate: insert into MyCacheEntity (age, name) values (?, ?)
Hibernate: select mycacheent0_.id as id1_8_, mycacheent0_.age as age2_8_, mycacheent0_.name as name3_8_ from MyCacheEntity mycacheent0_ where mycacheent0_.name=?
<===Start : 1441995664931===>
com.akash.hibernate.hibernatetut.cache.MyCacheEntity@108fa9c
1
20
akash
<===End===>
<===Start : 1441995665133===>
com.akash.hibernate.hibernatetut.cache.MyCacheEntity@72bd07
1
20
akash
<===End===>
<===Start : 1441995665336===>
com.akash.hibernate.hibernatetut.cache.MyCacheEntity@d82d5d
1
20
akash
<===End===>
<===Start : 1441995665539===>
com.akash.hibernate.hibernatetut.cache.MyCacheEntity@8a4583
1
20
akash
<===End===>
 
 
 
 */


//============================

/**
 *	Output without cache

Hibernate: insert into MyCacheEntity (age, name) values (?, ?)
Hibernate: select mycacheent0_.id as id1_8_, mycacheent0_.age as age2_8_, mycacheent0_.name as name3_8_ from MyCacheEntity mycacheent0_ where mycacheent0_.name=?
<===Start : 1441996035607===>
com.akash.hibernate.hibernatetut.cache.MyCacheEntity@1842264
1
20
akash
<===End===>
Hibernate: select mycacheent0_.id as id1_8_, mycacheent0_.age as age2_8_, mycacheent0_.name as name3_8_ from MyCacheEntity mycacheent0_ where mycacheent0_.name=?
<===Start : 1441996035826===>
com.akash.hibernate.hibernatetut.cache.MyCacheEntity@1da4e1e
1
20
akash
<===End===>
Hibernate: select mycacheent0_.id as id1_8_, mycacheent0_.age as age2_8_, mycacheent0_.name as name3_8_ from MyCacheEntity mycacheent0_ where mycacheent0_.name=?
<===Start : 1441996036044===>
com.akash.hibernate.hibernatetut.cache.MyCacheEntity@9ba8ac
1
20
akash
<===End===>
Hibernate: select mycacheent0_.id as id1_8_, mycacheent0_.age as age2_8_, mycacheent0_.name as name3_8_ from MyCacheEntity mycacheent0_ where mycacheent0_.name=?
<===Start : 1441996036262===>
com.akash.hibernate.hibernatetut.cache.MyCacheEntity@1594a5f
1
20
akash
<===End===>

*/
