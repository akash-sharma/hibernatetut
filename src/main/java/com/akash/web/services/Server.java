package com.akash.web.services;

import org.apache.cxf.frontend.ServerFactoryBean;


public final class Server
{
    public static void main(String args[]) throws Exception
    {
    	System.out.println("aaa");
		CourseManagerImpl implementor = new CourseManagerImpl();
		ServerFactoryBean svrFactory = new ServerFactoryBean();
		svrFactory.setAddress("http://localhost:9000/CourseManager");
		svrFactory.setServiceBean(implementor);
		svrFactory.create();
		System.out.println("Server ready...");
    }
}