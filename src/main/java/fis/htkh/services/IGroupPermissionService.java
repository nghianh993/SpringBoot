package fis.htkh.services;

import fis.htkh.entities.GroupPermission;
import fis.htkh.models.GroupPermissionModel;

import java.util.List;
import java.util.Optional;

public interface IGroupPermissionService {

    List<GroupPermission> getListPermission(Long parentId);

    Optional<GroupPermission> findById(Long id);

    void addGroup(String name, Long parentId);

    GroupPermission getDetailGroup(Long id);

    boolean updateGroup(GroupPermission groupPermission);

    boolean deleteGroup(Long id);
}
