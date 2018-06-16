package fis.htkh.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserModel {
    private long id;

    private Long organizationid;

    private String address;

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
    private Date datemodify;
    private String images;

    List<Long> lstRole = new ArrayList<Long>();
    List<Long> ids = new ArrayList<Long>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(Long organizationid) {
        this.organizationid = organizationid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(Date datecreate) {
        this.datecreate = datecreate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIplogin() {
        return iplogin;
    }

    public void setIplogin(String iplogin) {
        this.iplogin = iplogin;
    }

    public boolean isIslock() {
        return islock;
    }

    public void setIslock(boolean islock) {
        this.islock = islock;
    }

    public String getLockresion() {
        return lockresion;
    }

    public void setLockresion(String lockresion) {
        this.lockresion = lockresion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Long> getLstRole() {
        return lstRole;
    }

    public void setLstRole(List<Long> lstRole) {
        this.lstRole = lstRole;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDatemodify() {
        return datemodify;
    }

    public void setDatemodify(Date datemodify) {
        this.datemodify = datemodify;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
