package com.akash.hibernate.hibernatetut.manytomany;

import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class ManyToManyImpl
{
	public void manyToManyImpl()
	{
		createObject();
	}
	
	private void createObject()
	{
		Role role1=new Role();
		role1.setAuthority("ADMIN");
		
		Role role2=new Role();
		role2.setAuthority("USER");
		
		User user1=new User();
		user1.setUsername("Ram");
		
		User user2=new User();
		user2.setUsername("Shyam");
		user2.getRoles().add(role1);
		user2.getRoles().add(role2);
		
		role1.getUsers().add(user2);
		role2.getUsers().add(user2);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user1);
        session.save(user2);
        session.save(role1);
        session.save(role2);
        session.getTransaction().commit();
	}
}


/**
*<=========Without using mappedBy property============>
*Hibernate: create table Role (id integer not null auto_increment, authority varchar(255), primary key (id))
*Hibernate: create table Role_User (Role_id integer not null, users_id integer not null)
*Hibernate: create table User (id integer not null auto_increment, username varchar(255), primary key (id))
*Hibernate: create table User_Role (User_id integer not null, roles_id integer not null)
*Hibernate: alter table Role_User add index FK8B6B91F42100828D (Role_id), add constraint FK8B6B91F42100828D foreign key (Role_id) references Role (id)
*Hibernate: alter table Role_User add index FK8B6B91F4BF1C1F10 (users_id), add constraint FK8B6B91F4BF1C1F10 foreign key (users_id) references User (id)
*Hibernate: alter table User_Role add index FK8B9F886AC62B466D (User_id), add constraint FK8B9F886AC62B466D foreign key (User_id) references User (id)
*Hibernate: alter table User_Role add index FK8B9F886ABF18FAE6 (roles_id), add constraint FK8B9F886ABF18FAE6 foreign key (roles_id) references Role (id)
*
*
*<=================Insert Operation====================>
*Hibernate: insert into User (username) values (?)
*Hibernate: insert into User (username) values (?)
*Hibernate: insert into Role (authority) values (?)
*Hibernate: insert into Role (authority) values (?)
*Hibernate: insert into User_Role (User_id, roles_id) values (?, ?)
*Hibernate: insert into User_Role (User_id, roles_id) values (?, ?)
*Hibernate: insert into Role_User (Role_id, users_id) values (?, ?)
*Hibernate: insert into Role_User (Role_id, users_id) values (?, ?)
*
*
*
*NOTE
*(1)Lets say we have ManyToMany mapping in Tables A and B without using mappedBy property, then
*	this is equivalent to OneToMany from A to B and OneToMany from B to A.
*	i.e. we have 2 more tables created, A_B and B_A.
*(2)Object of both A and B can be created separately.
*(3)All foreign keys are Nullable false.
*
*
***<==========With using mappedBy property=============>
*Hibernate: create table Role (id integer not null auto_increment, authority varchar(255), primary key (id))
*Hibernate: create table User (id integer not null auto_increment, username varchar(255), primary key (id))
*Hibernate: create table User_Role (users_id integer not null, roles_id integer not null)
*Hibernate: alter table User_Role add index FK8B9F886ABF18FAE6 (roles_id), add constraint FK8B9F886ABF18FAE6 foreign key (roles_id) references Role (id)
*Hibernate: alter table User_Role add index FK8B9F886ABF1C1F10 (users_id), add constraint FK8B9F886ABF1C1F10 foreign key (users_id) references User (id)
*
*
*<=================Insert Operation====================>
*Hibernate: insert into User (username) values (?)
*Hibernate: insert into User (username) values (?)
*Hibernate: insert into Role (authority) values (?)
*Hibernate: insert into Role (authority) values (?)
*Hibernate: insert into User_Role (users_id, roles_id) values (?, ?)
*Hibernate: insert into User_Role (users_id, roles_id) values (?, ?)
*
*
*/

