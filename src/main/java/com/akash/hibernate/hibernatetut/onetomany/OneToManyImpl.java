package com.akash.hibernate.hibernatetut.onetomany;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class OneToManyImpl
{
	public void oneToManyImpl()
	{
		createOneToMany();
//		deleteHuman();
//		deleteVehicle();
	}
	
	private void createOneToMany()
	{
		Vehicle vehicle1=new Vehicle();
		vehicle1.setName("Car");
		
		Vehicle vehicle2=new Vehicle();
		vehicle2.setName("Bike");
		
		Human human=new Human();
		ArrayList vehicleList=new ArrayList();
		vehicleList.add(vehicle1);
		vehicleList.add(vehicle2);
		human.setName("Ram");
		human.setVehicleList(vehicleList);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(human);
        session.save(vehicle1);
        session.save(vehicle2);
        session.getTransaction().commit();
	}
	
	private void deleteHuman()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query=session.createQuery("from Human where name=:name").setParameter("name", "Ram");
		Human human=(Human)query.list().get(0);
		session.delete(human);
		session.getTransaction().commit();
	}
	
	private void deleteVehicle()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query=session.createQuery("from Vehicle_onetomany where name=:name").setParameter("name", "Car");
		Vehicle vehicle=(Vehicle)query.list().get(0);
		session.delete(vehicle);
		session.getTransaction().commit();
	}
}


/**
 * This setting is unidirectional OneToMany
 * Bidirectional OneToMany ???
 * 
 * Hibernate: create table Human (id integer not null auto_increment, name varchar(255), primary key (id))
 * Hibernate: create table Human_Vehicle_onetomany (Human_id integer not null, vehicleList_id integer not null, unique (vehicleList_id))
 * Hibernate: create table Vehicle_onetomany (id integer not null auto_increment, name varchar(255), primary key (id))
 * Hibernate: alter table Human_Vehicle_onetomany add index FK8131FD1BCF7BEAAC (vehicleList_id), add constraint FK8131FD1BCF7BEAAC foreign key (vehicleList_id) references Vehicle_onetomany (id)
 * Hibernate: alter table Human_Vehicle_onetomany add index FK8131FD1BDBAA7A6A (Human_id), add constraint FK8131FD1BDBAA7A6A foreign key (Human_id) references Human (id)
 * 
 * <<--->>
 * Without saving Vehicle_onetomany table
 * <<--->>
 * Hibernate: insert into Human (name) values (?)
 * 
 *
 * <<--->>
 * With saving Vehicle_onetomany table
 * <<--->>
 * Hibernate: insert into Human (name) values (?)
 * Hibernate: insert into Vehicle_onetomany (name) values (?)
 * Hibernate: insert into Vehicle_onetomany (name) values (?)
 * Hibernate: insert into Human_Vehicle_onetomany (Human_id, vehicleList_id) values (?, ?)
 * Hibernate: insert into Human_Vehicle_onetomany (Human_id, vehicleList_id) values (?, ?)
 * 
 * 
 * NOTE:
 * When we have OneToMany relation from A to B,
 * i.e. A hasMany B then,
 * (1)A third table is created "A_B" with
 *  	storing id of both A and B as foreign key.
 * (2)If we delete object of A, then its related entries 
 * 		in A_B are also deleted automatically 
 * 		but object of B is not deleted.
 * (3)If we try to delete only B then an error will occur,
 *  	com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException:
 *   	Cannot delete or update a parent row: a foreign key constraint fails
 *   
 */
