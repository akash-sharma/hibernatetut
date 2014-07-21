package com.akash.web.services;

public class CourseManagerImpl implements CourseManager
{
	public void addCourse(Course course)
	{
		System.out.println("call recieved");
		System.out.println("Course code: " + course.getCode());
		System.out.println("Course name: " + course.getName());
	}
}