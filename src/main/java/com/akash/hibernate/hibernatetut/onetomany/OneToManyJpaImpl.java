package com.akash.hibernate.hibernatetut.onetomany;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.akash.hibernate.hibernatetut.singledomain.JPACloner;
import com.akash.hibernate.hibernatetut.singledomain.JpaUtil;

public class OneToManyJpaImpl {

	public static void main(String args[]) throws Exception {
		createOneToMany();
		cloneHuman();
		//cloneHumanUsingNeg();
		JpaUtil.close();
	}

	private static void cloneHumanUsingNeg() throws Exception {
		EntityManager entityManager = JpaUtil.getEntitymanager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT h from Human h where h.id=1");
		EntityGraph entityGraph = entityManager.getEntityGraph("graph.human");
		query.setHint("javax.persistence.fetchgraph", entityGraph);
		Human human = (Human) query.getSingleResult();
		System.out.println("before clone");
		List<Class<?>> classesToIgnore = new ArrayList<Class<?>>();
		JPACloner.getJPAClone(human, classesToIgnore);
		entityManager.detach(human);
		entityManager.clear();
		System.out.println("after clone");
		entityManager.merge(human);
		entityManager.getTransaction().commit();
	}

	private static void cloneHuman() throws Exception {
		EntityManager entityManager = JpaUtil.getEntitymanager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT h from Human h where h.id=1");
		Human human = (Human) query.getSingleResult();
		System.out.println("before clone");
		List<Class<?>> classesToIgnore = new ArrayList<Class<?>>();
		JPACloner.getJPAClone(human, classesToIgnore);
		entityManager.detach(human);
		entityManager.clear();
		System.out.println("after clone");
		entityManager.merge(human);
		entityManager.getTransaction().commit();
	}

	/**
	 * 3 sql queries are fired after human is loaded <br>
	 * 1 for vehicle <br>
	 * 2 for tyres of each vehicle <br>
	 * 
	 * Hibernate: select human0_.id as id1_4_, human0_.name as name2_4_ from
	 * Human human0_ where human0_.id=1 before clone Hibernate: select
	 * vehiclelis0_.Human_id as Human_id1_4_0_, vehiclelis0_.vehicleList_id as
	 * vehicleList_id2_5_0_, vehicle1_.id as id1_27_1_, vehicle1_.name as
	 * name2_27_1_ from Human_Vehicle_onetomany vehiclelis0_ inner join
	 * Vehicle_onetomany vehicle1_ on vehiclelis0_.vehicleList_id=vehicle1_.id
	 * where vehiclelis0_.Human_id=?
	 * 
	 * Hibernate: select tyres0_.Vehicle_onetomany_id as
	 * Vehicle_onetomany_1_27_0_, tyres0_.tyres_id as tyres_id2_28_0_, tyre1_.id
	 * as id1_22_1_, tyre1_.name as name2_22_1_ from Vehicle_onetomany_Tyre
	 * tyres0_ inner join Tyre tyre1_ on tyres0_.tyres_id=tyre1_.id where
	 * tyres0_.Vehicle_onetomany_id=?
	 * 
	 * Hibernate: select tyres0_.Vehicle_onetomany_id as
	 * Vehicle_onetomany_1_27_0_, tyres0_.tyres_id as tyres_id2_28_0_, tyre1_.id
	 * as id1_22_1_, tyre1_.name as name2_22_1_ from Vehicle_onetomany_Tyre
	 * tyres0_ inner join Tyre tyre1_ on tyres0_.tyres_id=tyre1_.id where
	 * tyres0_.Vehicle_onetomany_id=? after clone
	 */

	private static void createOneToMany() {
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

		EntityManager entitymanager = JpaUtil.getEntitymanager();
		entitymanager.getTransaction().begin();

		entitymanager.persist(human);
		entitymanager.persist(tyre1);
		entitymanager.persist(tyre2);
		entitymanager.persist(vehicle1);
		entitymanager.persist(vehicle2);

		entitymanager.getTransaction().commit();
		entitymanager.close();
	}
}