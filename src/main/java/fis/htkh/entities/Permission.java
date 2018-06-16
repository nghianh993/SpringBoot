package fis.htkh.entities;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the PERMISSION database table.
 * 
 */
@Entity
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PERMISSION_SEQ", sequenceName="PERMISSION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="PERMISSION_SEQ")
	private Long id;

	private String description;

	private boolean islock;
	
	private String link;

	@ManyToOne
	@JoinColumn(name="GROUPID")
	private GroupPermission groupPermission;

	public Permission() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getIslock() {
		return this.islock;
	}

	public void setIslock(boolean islock) {
		this.islock = islock;
	}

	public GroupPermission getGroupPermission() {
		return this.groupPermission;
	}

	public void setGroupPermission(GroupPermission groupPermission) {
		this.groupPermission = groupPermission;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}