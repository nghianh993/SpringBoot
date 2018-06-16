package fis.htkh.repositories;

import fis.htkh.entities.GroupPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupPermissionRepository extends JpaRepository<GroupPermission, Long> {

    public List<GroupPermission> findAll();

    public List<GroupPermission> findByParentid(Long parentId);

}
