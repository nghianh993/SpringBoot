package fis.htkh.servicesimpl;

import fis.htkh.entities.GroupPermission;
import fis.htkh.models.GroupPermissionModel;
import fis.htkh.repositories.GroupPermissionRepository;
import fis.htkh.services.IGroupPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupPermissionServiceImpl implements IGroupPermissionService {

    @Autowired
    GroupPermissionRepository groupPermissionRepository;

    @Override
    /*@Cacheable(value = "permission.getListPermission", key = "#parentId")*/
    public List<GroupPermission> getListPermission(Long parentId) {
        List<GroupPermission> groupPermissions = groupPermissionRepository.findByParentid(parentId);

        return groupPermissions;
    }

    @Override
    public void addGroup(String name, Long parentId) {
        GroupPermission group = new GroupPermission();
        group.setName(name);
        group.setParentid(parentId);

        groupPermissionRepository.save(group);
    }

    @Override
    public Optional<GroupPermission> findById(Long id) {
        return groupPermissionRepository.findById(id);
    }

    @Override
    public GroupPermission getDetailGroup(Long id) {
        Optional<GroupPermission> group = groupPermissionRepository.findById(id);
        GroupPermission model = new GroupPermission();
        if(group.isPresent()) {
            return group.get();
        }
        return null;
    }

    @Override
    public boolean updateGroup(GroupPermission groupPermission) {
        try {
            Optional<GroupPermission> group = groupPermissionRepository.findById(groupPermission.getId());
            if(group.isPresent()) {
                GroupPermission data = group.get();
                data.setName(groupPermission.getName());
                data.setParentid(groupPermission.getParentid());
                groupPermissionRepository.save(data);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteGroup(Long id) {
        try {
            groupPermissionRepository.deleteById(id);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
