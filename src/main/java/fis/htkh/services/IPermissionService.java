package fis.htkh.services;

import fis.htkh.entities.Permission;
import fis.htkh.models.PermissionModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IPermissionService {

	Page<Permission> getListPermission(int pageIndex, int pageSize);
	
	List<Permission> getAllPermission();

	List<Permission> getPermissionByLink(String link);
	
	Optional<Permission> getPermissionById(Long id);

	Set<Permission> findByInventoryIdIn(List<Long> ids);

	boolean addPermission(PermissionModel model);

	boolean deletePermission(Long id);

	boolean unLockOrLockPermission(Long id, boolean islock);

	PermissionModel getPermissionDetail(Long id);

	boolean editPermission(PermissionModel model);
}
