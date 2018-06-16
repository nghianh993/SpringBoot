package fis.htkh.services;

import fis.htkh.entities.Role;
import fis.htkh.models.RoleModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoleService {

    Page<Role> getListRole(int pageIndex, int pageSize, String key, Integer desending);

    List<Role> getListRole();

    List<Role> getListRoleByListId(List<Long> ids);

    RoleModel getRoleDetail(Long id);

    boolean editRole(RoleModel model);

    boolean addRole(RoleModel model);

    boolean deleteRole(Long id);
}
