package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
