package fis.htkh.repositories;

import fis.htkh.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

@ResponseBody
public interface PermissionRepository extends JpaRepository<Permission, Long> {
	/*Tìm danh sách chức năng theo danh sách id truyền vào*/
	@Query( "select o from Permission o where o.id in :ids" )
	Set<Permission> findByInventoryIdIn(@Param("ids") List<Long> ids);

	/*Lấy danh sách chức năng vào sắp sếp theo mã code*/
	@Query("select o from Permission o order by o.link desc")
	List<Permission> findAllOrderByLinkAsc();

	/*Tìm danh sách chức năng theo đường dẫn*/
	List<Permission> findByLink(String link);
}
