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
		cloneHumanUsingNeg();
		JpaUtil.close();
	}

	/**
	 * 1 sql queries is fired <br>
	 * 
	 * Hibernate: select human0_.id as id1_4_0_, vehicle2_.id as id1_27_1_,
	 * tyre4_.id as id1_22_2_, human0_.name as name2_4_0_, vehicle2_.name as
	 * name2_27_1_, vehiclelis1_.Human_id as Human_id1_4_0__,
	 * vehiclelis1_.vehicleList_id as vehicleList_id2_5_0__, tyre4_.name as
	 * name2_22_2_, tyres3_.Vehicle_onetomany_id as Vehicle_onetomany_1_27_1__,
	 * tyres3_.tyres_id as tyres_id2_28_1__ from Human human0_ left outer join
	 * Human_Vehicle_onetomany vehiclelis1_ on human0_.id=vehiclelis1_.Human_id
	 * left outer join Vehicle_onetomany vehicle2_ on
	 * vehiclelis1_.vehicleList_id=vehicle2_.id left outer join
	 * Vehicle_onetomany_Tyre tyres3_ on
	 * vehicle2_.id=tyres3_.Vehicle_onetomany_id left outer join Tyre tyre4_ on
	 * tyres3_.tyres_id=tyre4_.id where human0_.id=1 before clone after clone
	 */
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
	 * 4 sql queries are fired <br>
	 * 1 for human <br>
	 * 1 for all vehicle linked to human <br>
	 * 2 for tyres of each vehicle <br>
	 * 
	 * Hibernate: select human0_.id as id1_4_, human0_.name as name2_4_ from
	 * Human human0_ where human0_.id=1 <br>
	 * before clone
	 * 
	 * Hibernate: select human0_.id as id1_4_, human0_.name as name2_4_ from
	 * Human human0_ where human0_.id=1 before clone Hibernate: select
	 * human0_.id as id1_4_, human0_.name as name2_4_ from Human human0_ where
	 * human0_.id=1 before clone Hibernate: select vehiclelis0_.Human_id as
	 * Human_id1_4_0_, vehiclelis0_.vehicleList_id as vehicleList_id2_5_0_,
	 * vehicle1_.id as id1_27_1_, vehicle1_.name as name2_27_1_ from
	 * Human_Vehicle_onetomany vehiclelis0_ inner join Vehicle_onetomany
	 * vehicle1_ on vehiclelis0_.vehicleList_id=vehicle1_.id where
	 * vehiclelis0_.Human_id=?
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
	 * 
	 * after clone
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