package com.akash.hibernate.hibernatetut.onetomany;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;
import com.akash.hibernate.hibernatetut.singledomain.JPACloner;

public class OneToManyImpl {
	public void oneToManyImpl() {
		createOneToMany();
		System.out.println("created human");
		cloneHuman();
		// deleteHuman();
		// deleteVehicle();
		HibernateUtil.shutdown();
	}

	private void cloneHuman() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("SELECT h from Human h where h.id=6");
		
		List<Human> humans = (List<Human>) session.createQuery(
				"SELECT h from Human h where h.id=6")
				.list();
		if (humans.size() > 0) {
			Human human = humans.get(0);
			List<Class<?>> classesToIgnore = new ArrayList<Class<?>>();
			System.out.println("before clone");
			try {
				JPACloner.getJPAClone(human, classesToIgnore);
				System.out.println("after clone");
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.merge(human);
			session.getTransaction().commit();
		} else {
			System.out.println("no human found with id=6");
		}
	}

	private void createOneToMany() {
		Tyre tyre1 = new Tyre();
		tyre1.setName("radial");
		Tyre tyre2 = new Tyre();
		tyre2.setName("tubeless");

		Vehicle vehicle1 = new Vehicle();
		vehicle1.setName("Car");
		Set<Tyre> tyreList1 = new HashSet<Tyre>();
		tyreList1.add(tyre1);
		vehicle1.setTyres(tyreList1);

		Vehicle vehicle2 = new Vehicle();
		vehicle2.setName("Bike");
		Set<Tyre> tyreList2 = new HashSet<Tyre>();
		tyreList2.add(tyre1);
		tyreList2.add(tyre2);
		vehicle1.setTyres(tyreList2);

		Human human = new Human();
		Set<Vehicle> vehicleList = new HashSet<Vehicle>();
		vehicleList.add(vehicle1);
		vehicleList.add(vehicle2);
		human.setName("Ram");
		human.setVehicleList(vehicleList);

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(human);
		session.save(tyre1);
		session.save(tyre2);
		session.save(vehicle1);
		session.save(vehicle2);
		session.getTransaction().commit();
	}

	private void deleteHuman() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Human where name=:name").setParameter("name", "Ram");
		Human human = (Human) query.list().get(0);
		session.delete(human);
		session.getTransaction().commit();
	}

	private void deleteVehicle() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Vehicle_onetomany where name=:name").setParameter("name", "Car");
		Vehicle vehicle = (Vehicle) query.list().get(0);
		session.delete(vehicle);
		session.getTransaction().commit();
	}
}

/**
 * (Q)Why hibernate creates third table in OneToMany but not in case of
 * manyToOne ?
 * 
 * 
 * This setting is unidirectional OneToMany Bidirectional OneToMany ???
 * 
 * Hibernate: create table Human (id integer not null auto_increment, name
 * varchar(255), primary key (id)) Hibernate: create table
 * Human_Vehicle_onetomany (Human_id integer not null, vehicleList_id integer
 * not null, unique (vehicleList_id)) Hibernate: create table Vehicle_onetomany
 * (id integer not null auto_increment, name varchar(255), primary key (id))
 * Hibernate: alter table Human_Vehicle_onetomany add index FK8131FD1BCF7BEAAC
 * (vehicleList_id), add constraint FK8131FD1BCF7BEAAC foreign key
 * (vehicleList_id) references Vehicle_onetomany (id) Hibernate: alter table
 * Human_Vehicle_onetomany add index FK8131FD1BDBAA7A6A (Human_id), add
 * constraint FK8131FD1BDBAA7A6A foreign key (Human_id) references Human (id)
 * 
 * <<--->> Without saving Vehicle_onetomany table <<--->> Hibernate: insert into
 * Human (name) values (?)
 * 
 *
 * <<--->> With saving Vehicle_onetomany table <<--->> Hibernate: insert into
 * Human (name) values (?) Hibernate: insert into Vehicle_onetomany (name)
 * values (?) Hibernate: insert into Vehicle_onetomany (name) values (?)
 * Hibernate: insert into Human_Vehicle_onetomany (Human_id, vehicleList_id)
 * values (?, ?) Hibernate: insert into Human_Vehicle_onetomany (Human_id,
 * vehicleList_id) values (?, ?)
 * 
 * 
 * NOTE: When we have OneToMany relation from A to B, i.e. A hasMany B then,
 * (1)A third table is created "A_B" with storing id of both A and B as foreign
 * key. (2)If we delete object of A, then its related entries in A_B are also
 * deleted automatically but object of B is not deleted. (3)If we try to delete
 * only B then an error will occur,
 * com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException:
 * Cannot delete or update a parent row: a foreign key constraint fails
 * 
 */
