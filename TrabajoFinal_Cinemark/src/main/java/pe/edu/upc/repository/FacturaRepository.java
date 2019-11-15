package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {

	@Query("from Factura c where c.contadorFactura.name like %:accountantName%")
	List<Factura> findByNombreContador(@Param("accountantName") String accountantName);

}
