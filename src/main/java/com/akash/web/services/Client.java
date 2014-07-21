package com.akash.web.services;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.interceptor.LoggingOutInterceptor;


public final class Client
{
    public static void main(String args[]) throws Exception
    {
		ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
		factory.setServiceClass(CourseManager.class);
		factory.setAddress("http://localhost:9000/CourseManager");
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		CourseManager client = (CourseManager) factory.create();
		Course course = new Course();
		course.setCode("C01");
		course.setName("English Grammer");
		client.addCourse(course);
    }
}