/**
 * 
 */
package com.avenues.sample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.avenues.sample.entities.Parent;

/**
 * @author admin
 *
 */
@Component
@Transactional(propagation=Propagation.REQUIRED)
public class SampleService {

	@Autowired
	private SampleDAOImpl sampleDAO = null;

	public Parent persistParent(Parent parent) {
		sampleDAO.getHibernateTemplate().saveOrUpdate(parent);

		return parent;
	}

	public Parent getParent(String name) {
		return sampleDAO.fetchParentByName(name);
	}

	public String sayHello() {
		return "Hello World!";
	}
}
