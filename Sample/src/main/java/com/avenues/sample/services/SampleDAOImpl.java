/**
 * 
 */
package com.avenues.sample.services;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.avenues.sample.entities.Parent;

/**
 * @author abhijitpetkar
 *
 */
@Repository("sampleDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class SampleDAOImpl extends HibernateDaoSupport {

	@Autowired
    public void anyMethodName(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

	@SuppressWarnings("unchecked")
	public Parent fetchParentByName(String name) {
		String query = "select p from Parent p where p.name = ?";

		List<Parent> list = getHibernateTemplate().find(query, name);

		return list.get(0);
	}
}
