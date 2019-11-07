package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Lista_Compra;

@Repository
public interface Lista_CompraRepository extends JpaRepository<Lista_Compra, Integer> {

	@Query("from Lista_Compra l where l.estadoLista =:estadoLista")
	public List<Lista_Compra> findByLista(@Param("estadoLista") String estadoLista);

	@Query("from Lista_Compra l where l.proveedorLista.nombreProveedor like %:nombreProveedor%")
	List<Lista_Compra> findByNombreProveedor(@Param("nombreProveedor") String nombreProveedor);
}
