/**
 * 
 */
package com.avenues.sample.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name="child")
public class Child extends BaseEntity {

	private static final long serialVersionUID = -3992093906967415573L;

	@Column(name="name")
	private String name = null;
	
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Parent parent = null;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parent
	 */
	public Parent getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Parent parent) {
		this.parent = parent;
	}

}
