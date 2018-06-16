package fis.htkh.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the USERS database table.
 * 
 */
@Entity
@Table(name="USERS")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USERS_SEQ", sequenceName="USERS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="USERS_SEQ")
	private long id;

	private String address;

	@Temporal(TemporalType.DATE)
	private Date datecreate;

	private String email;

	private String fullname;

	private String iplogin;

	private boolean islock;

	private String lockresion;

	private String password;

	private String phone;
	
	private String dob;

	private String username;

	private String images;

	private Date datemodify;
	
	@ManyToOne
	@JoinColumn(name="ORGANIZATIONID")
	private Organization organization;

	//bi-directional many-to-one association to UserRoleMapping
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "USER_ROLE_MAPPING", 
      joinColumns =  @JoinColumn(name = "USERID", referencedColumnName = "ID"),
      inverseJoinColumns =  @JoinColumn(name = "ROLEID", referencedColumnName = "ID"))
	private List<Role> roles = new ArrayList<Role>();

	public User() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDatecreate() {
		return this.datecreate;
	}

	public void setDatecreate(Date datecreate) {
		this.datecreate = datecreate;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getIplogin() {
		return this.iplogin;
	}

	public void setIplogin(String iplogin) {
		this.iplogin = iplogin;
	}

	public boolean getIslock() {
		return this.islock;
	}

	public void setIslock(boolean islock) {
		this.islock = islock;
	}

	public String getLockresion() {
		return this.lockresion;
	}

	public void setLockresion(String lockresion) {
		this.lockresion = lockresion;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public boolean isIslock() {
		return islock;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Date getDatemodify() {
		return datemodify;
	}

	public void setDatemodify(Date datemodify) {
		this.datemodify = datemodify;
	}
}