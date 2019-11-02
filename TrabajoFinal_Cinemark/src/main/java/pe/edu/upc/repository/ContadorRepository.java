package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Contador;

@Repository
public interface ContadorRepository extends JpaRepository<Contador, Integer>{
	@Query("from Contador c where c.nombreContador like %:nombreContador%")
	List<Contador> findByNombreContador(@Param("nombreContador")String nombreContador);
	
	@Query("select count(c.dniContador) from Contador c where c.dniContador =:dniContador")
	public int buscarDNIContador(@Param("dniContador") String dniContador);
	
	@Modifying(clearAutomatically = true)
	@Query("update Contador set nombreContador = :nombreContador ,institucionContador = :institucionContador, numeroContactoContador = :numeroContactoContador where idContador=:idContador")
	public void actualizar(@Param("nombreContador")String nombreContador,
			@Param("institucionContador")String institucionContador,
			@Param("numeroContactoContador")String numeroContactoContador,
			@Param("idContador")int idContador);
}
