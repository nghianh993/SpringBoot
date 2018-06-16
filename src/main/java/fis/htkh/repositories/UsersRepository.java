package fis.htkh.repositories;

import fis.htkh.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	/*Tìm thông tin người đăng nhập theo username */
	User findByUsername(String username);

	/*Lấy danh sách và phân trang người dùng hệ thống*/
	@Query("select u from User u where :key is null or u.username like %:key% or u.fullname like %:key%")
	Page<User> findAllAccount(@Param("key") String key, Pageable pageable);

	/*Tìm kiếm người dùng theo danh sách id truyền vào*/
	@Query( "select o from User o where o.id in :ids" )
	Set<User> findByInventoryIdIn(@Param("ids") List<Long> ids);


}
