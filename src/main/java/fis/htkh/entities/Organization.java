package fis.htkh.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the ORGANIZATION database table.
 * 
 */
@Entity
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ORGANIZATION_SEQ", sequenceName="ORGANIZATION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="ORGANIZATION_SEQ")
	private Long id;

	private long lever;

	private String name;

	private Long parentid;
	
	@OneToMany(mappedBy="organization")
	private List<User> users;

	public Organization() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getLever() {
		return this.lever;
	}

	public void setLever(long lever) {
		this.lever = lever;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentid() {
		return this.parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}