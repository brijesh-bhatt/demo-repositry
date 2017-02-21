/**
 * 
 */
package com.avenues.sample.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name="parent")
public class Parent extends BaseEntity {

	private static final long serialVersionUID = -3992093906967415573L;

	@Column(name="name")
	private String name = null;

	@OneToMany(mappedBy="parent", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Child> children = null;

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
	 * @return the children
	 */
	public List<Child> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Child> children) {
		this.children = children;
	}

}
