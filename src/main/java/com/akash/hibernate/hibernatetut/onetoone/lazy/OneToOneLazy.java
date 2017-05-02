package com.akash.hibernate.hibernatetut.onetoone.lazy;

import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

/**
 * In Worker - WorkerProfile relation, owner entity is Worker, i.e WorkerProfile
 * id is saved as foreign key in Worker table.Whenever you will try to get
 * profile from worker object via hibernate, lazy loading will work accordingly.
 * This is good design when using hibernate. Owner entity should be the root of
 * your object graph.
 * 
 * Definition of owner table : A table which holds foreign key of relationship.
 * 
 * While in case of Manager - ManagerProfile , owner entity is ManagerProfile.
 * primary key of Manager is saved as foreign key in ManagerProfile. So while
 * fetching profile from manager, even for OneToOne lazy , lazy loading will not
 * work in hibernate.This is bad design.Making owner entity as leaf node in
 * object graph.
 * 
 * Select query for Worker :(lazy loading working fine)
 * 
 * Hibernate: select worker0_.id as id1_31_0_, worker0_.name as name2_31_0_,
 * worker0_.profile_id as profile_id3_31_0_ from Worker worker0_ where
 * worker0_.id=?
 *
 *
 * Select query for Manager :(lazy loading not working)
 *
 * Hibernate: select manager0_.id as id1_7_0_, manager0_.name as name2_7_0_ from
 * Manager manager0_ where manager0_.id=? <br>
 * <br>
 * 
 * Hibernate: select managerpro0_.id as id1_8_0_, managerpro0_.manager_id as
 * manager_id3_8_0_, managerpro0_.phone as phone2_8_0_ from ManagerProfile
 * managerpro0_ where managerpro0_.manager_id=?
 */
public class OneToOneLazy {

	public static void main(String args[]) {

		try {
			createWorker();
			fetchWorker();
			createManager();
			fetchManager();
			HibernateUtil.shutdown();
		} catch (Exception e) {
			System.out.println(e);
			HibernateUtil.shutdown();
		}
	}

	private static void createWorker() {
		Worker worker = new Worker();
		worker.setName("ram");
		worker.setId(1l);
		WorkerProfile profile = new WorkerProfile();
		profile.setPhone("12121212");
		worker.setProfile(profile);

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(worker);
		session.save(profile);
		session.getTransaction().commit();
	}

	private static void fetchWorker() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Worker worker = (Worker) session.get(Worker.class, 1l);
		session.getTransaction().commit();
	}

	private static void createManager() {
		Manager manager = new Manager();
		manager.setName("shyam");
		manager.setId(1l);
		ManagerProfile profile = new ManagerProfile();
		profile.setPhone("9898989");
		manager.setProfile(profile);
		profile.setManager(manager);

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(manager);
		session.save(profile);
		session.getTransaction().commit();
	}

	private static void fetchManager() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Manager manager = (Manager) session.get(Manager.class, 1l);
		session.getTransaction().commit();
	}
}