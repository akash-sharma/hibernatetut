package com.akash.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

//	http://www.javacodegeeks.com/2012/12/discovering-the-power-of-apache-camel.html

public class FileRouter {

	public static void main(final String[] arguments)
	{
	   final long durationMs = 10000;
	   final CamelContext camelContext = new DefaultCamelContext();
	   try
	   {
	      camelContext.addRoutes(
	         new RouteBuilder()
	         {
	            @Override
	            public void configure() throws Exception
	            {
	            	from("file:E:\\camel_dir\\input?delete=true")
	               		.to("file:E:\\camel_dir\\output");
	            }
	         });
	      camelContext.start();
	      Thread.sleep(durationMs);
	      camelContext.stop();
	   }
	   catch (Exception camelException)
	   {
		   System.out.println("Exception trying to copy files - "+camelException.toString());
	   }
	}
}
