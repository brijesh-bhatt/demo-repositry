/**
 * 
 */
package com.avenues.sample.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.avenues.sample.entities.Child;
import com.avenues.sample.entities.Parent;

/**
 * @author admin
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSampleService {

	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void test1() {
		
		ApplicationContext appContext =
		    	  new ClassPathXmlApplicationContext("datasource.xml");

		SampleService sampleService = (SampleService) appContext.getBean("sampleService");
		
		Parent p = new Parent();
		p.setName("parent");
		p.setCreatedAt(new Date());

		Child c = new Child();
		c.setName("child");
		c.setCreatedAt(new Date());
		c.setParent(p);

		List<Child> children = new ArrayList<Child>();
		children.add(c);

		p.setChildren(children);

		p = sampleService.persistParent(p);
		Assert.assertNotNull(p.getId());
	}

	@Test
	public void test2() {
		ApplicationContext appContext =
		    	  new ClassPathXmlApplicationContext("datasource.xml");

		SampleService sampleService = (SampleService) appContext.getBean("sampleService");

		String name = "parent";

		Parent p = sampleService.getParent(name);

		System.out.println("Parent Name = " + p.getName());

		Assert.assertNotNull(p);
		Assert.assertEquals(name, p.getName());
	}

	@Test
	public void test3() {
		ApplicationContext appContext =
		    	  new ClassPathXmlApplicationContext("datasource.xml");

		SampleService sampleService = (SampleService) appContext.getBean("sampleService");

		String resp = sampleService.sayHello();

		Assert.assertTrue("Hello World!".equals(resp));

		Assert.assertFalse("Hello There!".equals(resp));
	}
}