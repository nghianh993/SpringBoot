package fis.htkh.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PermissionModel {
    @NotNull
    private Long id;
    @Size(max = 255)
    private String description;
    @NotNull
    private boolean islock;
    @NotNull
    private Long parentId;
    @NotNull
    private String link;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isIslock() {
        return islock;
    }
    public void setIslock(boolean islock) {
        this.islock = islock;
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
}
