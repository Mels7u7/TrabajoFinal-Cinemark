package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Detalle_List_Compra;

@Repository
public interface Detalle_List_CompraRepository extends JpaRepository<Detalle_List_Compra, Integer> {
	@Query("select d from Detalle_List_Compra d where d.unidadesDetalle = ?1")
	List<Detalle_List_Compra> findByUnidadesDetalle(int unidadesDetalle);
}
