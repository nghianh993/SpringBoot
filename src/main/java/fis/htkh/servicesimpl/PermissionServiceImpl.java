package fis.htkh.servicesimpl;

import fis.htkh.entities.GroupPermission;
import fis.htkh.entities.Permission;
import fis.htkh.models.PermissionModel;
import fis.htkh.repositories.PermissionRepository;
import fis.htkh.services.IGroupPermissionService;
import fis.htkh.services.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PermissionServiceImpl implements IPermissionService {

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	IGroupPermissionService groupPermissionService;

	@Override
	public Page<Permission> getListPermission(int pageIndex, int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);

		return permissionRepository.findAll(pageable);
	}

	@Override
	public List<Permission> getAllPermission() {
		return permissionRepository.findAllOrderByLinkAsc();
	}

	@Override
	@Cacheable(value = "permission.getPermissionByLink", key = "#link")
	public List<Permission> getPermissionByLink(String link) {
		return permissionRepository.findByLink(link);
	}

	@Override
	public Optional<Permission> getPermissionById(Long id) {
		return permissionRepository.findById(id);
	}

	@Override
	public Set<Permission> findByInventoryIdIn(List<Long> ids) {
		return permissionRepository.findByInventoryIdIn(ids);
	}

	@Override
	@Transactional
	public boolean addPermission(PermissionModel model) {
		Optional<GroupPermission> groupPermission = groupPermissionService.findById(model.getParentId());
		if(!groupPermission.isPresent()) {
			return false;
		}
		Permission permission = new Permission();
		permission.setDescription(model.getDescription());
		permission.setGroupPermission(groupPermission.get());
		permission.setIslock(model.isIslock());
		permission.setLink(model.getLink());
		permissionRepository.save(permission);

		return true;
	}

	@Override
	@Transactional
	public boolean deletePermission(Long id) {
		try {
			permissionRepository.deleteById(id);
			return true;
		}catch(Exception ex) {
			return false;
		}
	}

	@Override
	public boolean unLockOrLockPermission(Long id, boolean islock) {
		Permission permission = permissionRepository.findById(id).get();
		if(permission == null)
			return false;
		permission.setIslock(islock);

		permissionRepository.save(permission);
		return true;
	}

	@Override
	public PermissionModel getPermissionDetail(Long id) {
		PermissionModel model = new PermissionModel();
		Optional<Permission> permission = permissionRepository.findById(id);
		if(!permission.isPresent())
			return null;
		model.setDescription(permission.get().getDescription());
		model.setLink(permission.get().getLink());
		model.setParentId(permission.get().getGroupPermission().getId());
		model.setIslock(permission.get().getIslock());

		return model;
	}

	@Override
	@Transactional
	public boolean editPermission(PermissionModel model) {
		try {
			Optional<GroupPermission> group = groupPermissionService.findById(model.getParentId());
			if (group.isPresent()) {
				Optional<Permission> permissionIt = permissionRepository.findById(model.getId());
				if(!permissionIt.isPresent())
					return false;
				Permission permission = permissionIt.get();
				permission.setDescription(model.getDescription());
				permission.setGroupPermission(group.get());
				permission.setIslock(model.isIslock());
				permission.setLink(model.getLink());
				permissionRepository.save(permission);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
