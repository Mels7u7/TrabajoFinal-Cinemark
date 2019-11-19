package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
	@Query("from Proveedor p where p.nombreProveedor like %:nombreProveedor%")
	List<Proveedor> findByNombreProveedor(@Param("nombreProveedor") String nombreProveedor);

	@Query("select count(p.rucProveedor)from Proveedor p where p.rucProveedor=:rucProveedor")
	public int buscarRUCProveedor(@Param("rucProveedor") String rucProveedor);

	@Query("select p from Proveedor p where p.direccionProveedor like %?1%")
	List<Proveedor> findByDireccion(String direccionProveedor);

}
