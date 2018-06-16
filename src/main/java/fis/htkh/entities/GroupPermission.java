package fis.htkh.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the GROUP_PERMISSION database table.
 * 
 */
@Entity
@Table(name="GROUP_PERMISSION")
public class GroupPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GROUP_PERMISSION_SEQ", sequenceName="GROUP_PERMISSION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="GROUP_PERMISSION_SEQ")
	private long id;

	private String name;
	
	private long parentid;

	@OneToMany(mappedBy="groupPermission", fetch=FetchType.EAGER) //, cascade = CascadeType.ALL
	private List<Permission> permissions;

	public GroupPermission() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Permission addPermission(Permission permission) {
		getPermissions().add(permission);
		permission.setGroupPermission(this);

		return permission;
	}

	public Permission removePermission(Permission permission) {
		getPermissions().remove(permission);
		permission.setGroupPermission(null);

		return permission;
	}

	public long getParentid() {
		return parentid;
	}

	public void setParentid(long parentid) {
		this.parentid = parentid;
	}

}