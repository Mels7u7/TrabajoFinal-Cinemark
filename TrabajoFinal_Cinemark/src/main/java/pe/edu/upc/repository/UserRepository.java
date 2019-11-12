package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	public Users findByUsername(String username);

	@Transactional
	@Modifying
	@Query(value = "insert into roles (rol, user_id) VALUES (:rol, :user_id)", nativeQuery = true)
	public void insRol(@Param("rol") String authority, @Param("user_id") Long user_id);

	@Transactional
	@Modifying
	@Query(value = "insert into users (enabled, username,password) VALUES (:enabled, :username, :password)", nativeQuery = true)
	public void addUser(@Param("enabled") boolean enabled, @Param("username") String username,
			@Param("password") String password);

	@Query("select count(u.username) from Users u where u.username =:username")
	public int buscarUsername(@Param("username") String nombre);

	@Query("from Users u where u.username = :username")
	public Users buscarNombre(@Param("username") String username);
}
