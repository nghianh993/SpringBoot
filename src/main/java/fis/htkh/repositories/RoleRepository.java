package fis.htkh.repositories;

import fis.htkh.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query( "select o from Role o where o.id in :ids" )
    List<Role> findByInventoryIdIn(@Param("ids") List<Long> ids);

    @Query("select o from Role o where :name is null or o.rolename like %:name%")
    Page<Role> findAllByName(@Param("name") String name, Pageable pageReguest);
}
