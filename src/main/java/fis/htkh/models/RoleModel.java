package fis.htkh.models;

import java.util.List;

public class RoleModel {
    private Long id;
    private String name;
    List<Long> lstuser;
    List<Long> lstPermision;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Long> getLstuser() {
        return lstuser;
    }
    public void setLstuser(List<Long> lstuser) {
        this.lstuser = lstuser;
    }
    public List<Long> getLstPermision() {
        return lstPermision;
    }
    public void setLstPermision(List<Long> lstPermision) {
        this.lstPermision = lstPermision;
    }
}
