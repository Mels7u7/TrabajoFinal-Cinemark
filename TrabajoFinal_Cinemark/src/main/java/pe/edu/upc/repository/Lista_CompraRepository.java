package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Lista_Compra;

@Repository
public interface Lista_CompraRepository extends JpaRepository<Lista_Compra, Integer> {

	@Query("select l from Lista_Compra l where l.estadoLista like %?1%")
	public List<Lista_Compra> findByEstadoLista(String estadoLista);
	
	@Query("select l from Lista_Compra l where l.precioLista < ?1")
	public List<Lista_Compra> findByPrecioLista(int precioLista);
}
