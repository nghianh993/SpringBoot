package fis.htkh.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the "ROLES" database table.
 * 
 */
@Entity
@Table(name="ROLES")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ROLES_SEQ", sequenceName="ROLES_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="ROLES_SEQ")
	private long id;

	private String rolename;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "ROLES_PERMISSION", 
 		joinColumns = @JoinColumn(name = "ROLEID", referencedColumnName = "ID"),
 		inverseJoinColumns = @JoinColumn(name = "PERMISSIONID", referencedColumnName = "ID"))
 	private Set<Permission> permissions = new HashSet<Permission>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "USER_ROLE_MAPPING",
 		joinColumns = @JoinColumn(name = "ROLEID", referencedColumnName = "ID"),
 		inverseJoinColumns = @JoinColumn(name = "USERID", referencedColumnName = "ID"))
 	private Set<User> users = new HashSet<User>();

	public Role() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}