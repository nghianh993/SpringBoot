package fis.htkh.repositories;

import fis.htkh.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	
	List<Organization> findByParentid(Long parentid);
}
