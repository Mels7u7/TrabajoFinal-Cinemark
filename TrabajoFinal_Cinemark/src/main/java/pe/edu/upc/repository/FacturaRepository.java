package pe.edu.upc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;


import pe.edu.upc.entity.Factura;


@Repository
public interface FacturaRepository extends JpaRepository<Factura,Integer> {

	@Query("from Factura c where c.fechaFactura like %:fechaFactura%")
	List<Factura> findByDateFactura(Date fechaFactura);
	
	
	
}
