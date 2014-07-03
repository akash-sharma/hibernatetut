package com.akash.hibernate.hibernatetut.manytoone;

import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class ManyToOneImpl
{
	public void manyToOne()
	{
		createManyToOne();
	}
	
	private void createManyToOne()
	{
		Company company=new Company();
		company.setCompanyName("Google");
		
		Employee employee1=new Employee();
		employee1.setEmpname("Ram");
		employee1.setCompany(company);
		
		Employee employee2=new Employee();
		employee2.setEmpname("Shyam");
		employee2.setCompany(company);
		
		Employee employee3=new Employee();
		employee3.setEmpname("Ashok");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(company);
		session.save(employee1);
		session.save(employee2);
		session.save(employee3);
		session.getTransaction().commit();
	}
	
	private void deleteCompany()
	{
		
	}
	
	private void deleteEmployee()
	{
		
	}
}


/**
 *
 *Hibernate: create table Company (companyId integer not null auto_increment, companyName varchar(255), primary key (companyId))
 *Hibernate: create table Employee (employeeId integer not null auto_increment, empname varchar(255), company_companyId integer, primary key (employeeId))
 *Hibernate: alter table Employee add index FK4AFD4ACEB7D38B2F (company_companyId), add constraint FK4AFD4ACEB7D38B2F foreign key (company_companyId) references Company (companyId)
 *
 *
 *Hibernate: insert into Company (companyName) values (?)
 *Hibernate: insert into Employee (company_companyId, empname) values (?, ?)
 *Hibernate: insert into Employee (company_companyId, empname) values (?, ?)
 *Hibernate: insert into Employee (company_companyId, empname) values (?, ?)
 *
 *
 *NOTE:
 *When we have ManyToOne mapping from A to B then, i.e. ( A belongsTo B )
 *(1)No third table is created (as in case of ManyToOne)
 *(2)A foreign key is stored in A of B
 *(3)Foreign key can have a null value
 *
 *
 */

