package com.akash.hibernate.hibernatetut.sharedrefrence;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class App {

	// Exception in thread "main" org.hibernate.HibernateException: Found shared
	// references to a collection:
	// com.akash.hibernate.hibernatetut.sharedrefrence.Parent2.listOfMaster

	public static void main(String[] args) {
		createMaster();
		//session closed here
		createParent1();
		createParent2();
	}

	static void createParent1() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Set<Master> masterList = getMaster(session);
		Parent1 parent = new Parent1();
		parent.setAbc("abc");
		parent.setListOfMaster(masterList);
		session.save(parent);
		session.getTransaction().commit();
	}

	static void createParent2() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Parent2 parent = new Parent2();
		parent.setAbc("abc");
		Set<Master> masterList = getMasterFromParent1(session);
		parent.setListOfMaster(masterList);
		session.save(parent);
		session.getTransaction().commit();
	}

	static Set<Master> getMasterFromParent1(Session session) {
		Set<Master> masterList = new HashSet<Master>();
		Parent1 parent = (Parent1) session.get(Parent1.class, 1);
		Set<Master> originalList = parent.getListOfMaster();
		Iterator itr = originalList.iterator();
		while (itr.hasNext()) {
			masterList.add((Master) itr.next());
		}
		return masterList;
		// return parent.getListOfMaster();
	}

	static void createMaster() {

		Master master = new Master();
		master.setName("INDIA");
		Master master2 = new Master();
		master2.setName("AUZ");
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(master);
		session.save(master2);
		session.getTransaction().commit();
	}

	static Set<Master> getMaster(Session session) {
		Set<Master> masterList = new HashSet<Master>();
		masterList.add((Master) session.get(Master.class, 1));
		masterList.add((Master) session.get(Master.class, 2));
		return masterList;
	}
}


/*
 * SQL quiries fired
 * 
create table Master (id integer not null auto_increment, name varchar(255), primary key (id))
create table Parent1 (id integer not null auto_increment, abc varchar(255), primary key (id))
create table Parent2 (id integer not null auto_increment, abc varchar(255), primary key (id))
create table Parent2_Master (Parent2_id integer not null, listOfMaster_id integer not null, primary key (Parent2_id, listOfMaster_id))
create table parent1_master (parent_ID integer not null, master_ID integer not null, primary key (parent_ID, master_ID))

alter table Parent2_Master add constraint UK_cwtuh52yqxairhsg1v5oyjnnu  unique (listOfMaster_id)
alter table parent1_master add constraint UK_5v82mymwan9tqv7yigbukjxes  unique (master_ID)

alter table Parent2_Master add constraint FK_cwtuh52yqxairhsg1v5oyjnnu foreign key (listOfMaster_id) references Master (id)
alter table Parent2_Master add constraint FK_l5hisdebgqdhkhwk153sciy3w foreign key (Parent2_id) references Parent2 (id)

alter table parent1_master add constraint FK_5v82mymwan9tqv7yigbukjxes foreign key (master_ID) references Master (id)
alter table parent1_master add constraint FK_991fv9lqsa56o1x12xbajumpv foreign key (parent_ID) references Parent1 (id)

insert into Master (name) values (?)
insert into Master (name) values (?)
select master0_.id as id1_6_0_, master0_.name as name2_6_0_ from Master master0_ where master0_.id=?
select master0_.id as id1_6_0_, master0_.name as name2_6_0_ from Master master0_ where master0_.id=?
insert into Parent1 (abc) values (?)
insert into parent1_master (parent_ID, master_ID) values (?, ?)
insert into parent1_master (parent_ID, master_ID) values (?, ?)
select parent1x0_.id as id1_8_0_, parent1x0_.abc as abc2_8_0_ from Parent1 parent1x0_ where parent1x0_.id=?
select listofmast0_.parent_ID as parent_I1_8_0_, listofmast0_.master_ID as master_I2_26_0_, master1_.id as id1_6_1_, master1_.name as name2_6_1_ from parent1_master listofmast0_ inner join Master master1_ on listofmast0_.master_ID=master1_.id where listofmast0_.parent_ID=?
insert into Parent2 (abc) values (?)
insert into Parent2_Master (Parent2_id, listOfMaster_id) values (?, ?)
insert into Parent2_Master (Parent2_id, listOfMaster_id) values (?, ?)
 * 
 * 
 */