package fis.htkh.models;

import fis.htkh.entities.Permission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GroupPermissionModel {
    private long id;
    private String name;
    private long parentid;
    private List<Permission> permissions = new ArrayList<Permission>();
    private List<GroupPermissionModel> lstGroupPermissionModel = new ArrayList<>();

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Collection<Permission> getPermissions() {
        return permissions;
    }
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
    public Long getParentId() {
        return parentid;
    }
    public void setParentId(Long parentId) {
        this.parentid = parentId;
    }
    public List<GroupPermissionModel> getLstGroupPermissionModel() {
        return lstGroupPermissionModel;
    }
    public void setLstGroupPermissionModel(List<GroupPermissionModel> lstGroupPermissionModel) {
        this.lstGroupPermissionModel = lstGroupPermissionModel;
    }
}
