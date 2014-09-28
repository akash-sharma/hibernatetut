package com.akash.hibernate.hibernatetut.manytomanyoneside;

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
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        session.getTransaction().commit();
	}
}


/**
*  ManyToMany configuration with ManyToMany only on one side.
*  Hibernate: create table Team (id integer not null, teamName varchar(255), teamLead_id integer, primary key (id))
*  Hibernate: create table TeamUser (id integer not null, isTeamLead boolean not null, username varchar(255), primary key (id))
*  Hibernate: create table Team_TeamUser (Team_id integer not null, users_id integer not null, primary key (Team_id, users_id))

*  Hibernate: alter table Team add index FK27B67DEACFA175 (teamLead_id), add constraint FK27B67DEACFA175 foreign key (teamLead_id) references TeamUser (id)
*  Hibernate: alter table Team_TeamUser add index FK5FCD076AA999A466 (Team_id), add constraint FK5FCD076AA999A466 foreign key (Team_id) references Team (id)
*  Hibernate: alter table Team_TeamUser add index FK5FCD076A96925846 (users_id), add constraint FK5FCD076A96925846 foreign key (users_id) references TeamUser (id)
*
*
*  http://sqlperformance.com/2012/11/t-sql-queries/benefits-indexing-foreign-keys
*
*/

