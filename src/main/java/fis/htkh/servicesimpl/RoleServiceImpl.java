package fis.htkh.servicesimpl;

import fis.htkh.entities.Permission;
import fis.htkh.entities.Role;
import fis.htkh.models.RoleModel;
import fis.htkh.repositories.RoleRepository;
import fis.htkh.services.IPermissionService;
import fis.htkh.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    IPermissionService permissionService;

    @Override
    public Page<Role> getListRole(int pageIndex, int pageSize, String key, Integer desending) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return roleRepository.findAllByName(key, pageable);
    }

    @Override
    public List<Role> getListRole() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getListRoleByListId(List<Long> ids) {
        return roleRepository.findByInventoryIdIn(ids);
    }

    @Override
    public RoleModel getRoleDetail(Long id) {
        RoleModel groupModel = new RoleModel();
        Optional<Role> role = roleRepository.findById(id);
        if (!role.isPresent())
            return null;
        Role data = role.get();
        groupModel.setName(data.getRolename());
        //lấy danh sách id chức năng đã chọn
        groupModel.setLstPermision(data.getPermissions().stream().map(Permission::getId).collect(Collectors.toList()));

        return groupModel;
    }

    @Override
    public boolean editRole(RoleModel model) {
        try {
            Optional<Role> role = roleRepository.findById(model.getId());
            if (!role.isPresent())
                return false;
            Role data = role.get();
            data.setRolename(model.getName());
            roleRepository.save(data);
            data.setPermissions(permissionService.findByInventoryIdIn(model.getLstPermision()));
            roleRepository.save(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addRole(RoleModel model) {
        try {
            Role role = new Role();
            role.setRolename(model.getName());
            roleRepository.save(role);
            Set<Permission> permissions = permissionService.findByInventoryIdIn(model.getLstPermision());
            role.setPermissions(permissions);
            roleRepository.save(role);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteRole(Long id) {
        try {
            roleRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
