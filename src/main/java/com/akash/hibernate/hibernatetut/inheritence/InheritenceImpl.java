package com.akash.hibernate.hibernatetut.inheritence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.onetoone.Person;
import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class InheritenceImpl
{
	public void inheritenceImpl()
	{
		createVehicle();
//		readVehicleByName("maruti 800", "TwoWheeler");
//		readVehicleByName("maruti 800", "FourWheeler");
//		readVehicleByName("maruti 800", "MovableVehicle");
//		readVehicleByRpm(200, "TwoWheeler");
//		readVehicleByRpm(200, "FourWheeler");
		readVehicleByRpm(200, "MovableVehicle");
		deleteVehicle(200,"TwoWheeler");
		readVehicleByRpm(200, "MovableVehicle");
	}
	
	private void createVehicle()
	{
		TwoWheeler twoWheeler=new TwoWheeler();
		twoWheeler.setName("bajaj scooter");
		twoWheeler.setRpm(200);
		
		FourWheeler fourWheeler=new FourWheeler();
		fourWheeler.setName("maruti 800");
		fourWheeler.setEnginePower(500);
		
		MovableVehicle vehicle=new MovableVehicle();
		vehicle.setName("thella");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(twoWheeler);
        session.save(fourWheeler);
        session.save(vehicle);
        session.getTransaction().commit();
	}
	
	private void readVehicleByName(String vehicleName, String entityName)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List listOfVehicles=(List) session.createQuery("from "+entityName+" where name=:name").setParameter("name", vehicleName).list();
        if(listOfVehicles.size()!=0)
        	System.out.println("vehicle Id for name : "+((MovableVehicle)listOfVehicles.get(0)).getId());
        else
        	System.out.println("output is null");
        session.close();
	}
	
	private void readVehicleByRpm(int rpm, String entityName)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List listOfVehicles=(List) session.createQuery("from "+entityName+" where rpm=:rpm").setParameter("rpm", rpm).list();
        if(listOfVehicles.size()!=0)
        	System.out.println("vehicle id for rpm : "+((MovableVehicle)listOfVehicles.get(0)).getId());
        else
        	System.out.println("output is null");
        session.close();
	}
	
	private void deleteVehicle(int rpm, String entityName)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List listOfVehicles=(List) session.createQuery("from "+entityName+" where rpm=:rpm").setParameter("rpm", rpm).list();
        if(listOfVehicles.size()!=0){
        	session.delete((MovableVehicle)listOfVehicles.get(0));
        	session.flush();
        	System.out.println("object deleted");
        }
        else
        	System.out.println("output is null");
        session.close();
	}
}


/**
*
* Hibernate: create table MovableVehicle (DTYPE varchar(31) not null, id integer not null auto_increment, name varchar(255), rpm integer, enginePower integer, primary key (id))
*
*
* Hibernate: select twowheeler0_.id as id15_, twowheeler0_.name as name15_, twowheeler0_.rpm as rpm15_ from MovableVehicle twowheeler0_ where twowheeler0_.DTYPE='TwoWheeler' and twowheeler0_.name=?
* output is null
* Hibernate: select fourwheele0_.id as id15_, fourwheele0_.name as name15_, fourwheele0_.enginePower as enginePo5_15_ from MovableVehicle fourwheele0_ where fourwheele0_.DTYPE='FourWheeler' and fourwheele0_.name=?
* vehicle.getName() : 2
* Hibernate: select movableveh0_.id as id15_, movableveh0_.name as name15_, movableveh0_.rpm as rpm15_, movableveh0_.enginePower as enginePo5_15_, movableveh0_.DTYPE as DTYPE15_ from MovableVehicle movableveh0_ where movableveh0_.name=?
* vehicle.getName() : 2
*
*NOTE:
*(1)If we try to find a parameter by parent class, that is saved via child class, then object of parent and child return not null.
*	but object of different child class will always return null for that value. 
*
*/

