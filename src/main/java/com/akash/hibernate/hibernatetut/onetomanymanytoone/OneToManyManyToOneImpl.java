package com.akash.hibernate.hibernatetut.onetomanymanytoone;

import java.util.HashSet;

import org.hibernate.Session;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class OneToManyManyToOneImpl
{
	public void oneToManyManyToOneImpl()
	{
		createObject();
	}
	
	private void createObject()
	{
		Student student1=new Student();
		student1.setName("rahul");
		Student student2=new Student();
		student2.setName("saurav");
		HashSet students =new HashSet();
		students.add(student1);
		students.add(student2);
		
		Teacher teacher1=new Teacher();
		teacher1.setName("Ram");
		teacher1.setStudents(students);
		student1.setTeacher(teacher1);
		student2.setTeacher(teacher1);
		
		Teacher teacher2=new Teacher();
		teacher2.setName("Shyam");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(student1);
        session.save(student2);
        session.save(teacher1);
        session.save(teacher2);
        session.getTransaction().commit();
	}
}

/**
*<========When Not using mappedBy property in OneToMany=========>
*
*Hibernate: create table Student (id integer not null auto_increment, name varchar(255), teacher_id integer, primary key (id))
*Hibernate: create table Teacher (id integer not null auto_increment, name varchar(255), primary key (id))
*Hibernate: create table Teacher_Student (Teacher_id integer not null, students_id integer not null, unique (students_id))

*Hibernate: alter table Student add index FKF3371A1BAB93335A (teacher_id), add constraint FKF3371A1BAB93335A foreign key (teacher_id) references Teacher (id)
*Hibernate: alter table Teacher_Student add index FK55FA429E64E40C3D (students_id), add constraint FK55FA429E64E40C3D foreign key (students_id) references Student (id)
*Hibernate: alter table Teacher_Student add index FK55FA429EAB93335A (Teacher_id), add constraint FK55FA429EAB93335A foreign key (Teacher_id) references Teacher (id)
*
*
**<========When Not using mappedBy property in OneToMany=========>
*
*Hibernate: create table Student (id integer not null auto_increment, name varchar(255), teacher_id integer, primary key (id))
*Hibernate: create table Teacher (id integer not null auto_increment, name varchar(255), primary key (id))
*Hibernate: alter table Student add index FKF3371A1BAB93335A (teacher_id), add constraint FKF3371A1BAB93335A foreign key (teacher_id) references Teacher (id)
*
*
*<==============Insert operation without mappedBy================>
*
*Hibernate: insert into Student (name, teacherId) values (?, ?)
*Hibernate: insert into Student (name, teacherId) values (?, ?)
*Hibernate: insert into Teacher (name) values (?)
*Hibernate: insert into Teacher (name) values (?)
*Hibernate: update Student set name=?, teacherId=? where id=?
*Hibernate: update Student set name=?, teacherId=? where id=?
*Hibernate: insert into Teacher_Student (Teacher_id, students_id) values (?, ?)
*Hibernate: insert into Teacher_Student (Teacher_id, students_id) values (?, ?)

*
*
*<================Insert operation With mappedBy==================>
*
*Hibernate: insert into Student (name, teacherId) values (?, ?)
*Hibernate: insert into Student (name, teacherId) values (?, ?)
*Hibernate: insert into Teacher (name) values (?)
*Hibernate: insert into Teacher (name) values (?)
*Hibernate: update Student set name=?, teacherId=? where id=?
*Hibernate: update Student set name=?, teacherId=? where id=?
*
*
*<======================Points to Ponder=========================>
*
*NOTE:
*	Let say we have OneToMany mapping from A to B and ManyToOne from B to A,
*
*(1)In case of OneToMany we have a third table with two foreign keys
*	 of One and Many side table and both keys cannot be NULL (Unidirectional)
*(2)In case of ManyToOne we have a foreign key of One side Table
*	 in Many side Table and that can be NULL (Unidirectional)
*(3)In case of OneToMany--with--ManyToOne , if we have not used mappedBy
*	 then we have 3 foreign keys i.e. combination of (1) and (2)
*(4)In case of OneToMany--with--ManyToOne , if we have used mappedBy
*	 a foreign key is created in B table with Nullable=true .
*
*(5)=>object of B can be saved alone.
*	=>object of A can be saved alone.
*	that means object of any class can be created first (in both cases with and without using mappedBy).
*(6)This is an example of Bidirectional OneToMany OR Bidirectional ManyToOne
*
*
*/