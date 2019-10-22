package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Detalle_List_Compra;

@Repository
public interface Detalle_List_CompraRepository extends JpaRepository<Detalle_List_Compra, Integer>{
	@Query("from Detalle d where d.unidadesDetalle like %:unidadesDetalle%")
	List<Detalle_List_Compra> findByUnidadesDetalle (int unidadesDetalle);

	@Query("select count(d.idDetalle) from Detalle d where d.idDetalle =:idDetalle")
	public int buscarIdDetalle(@Param("idDetalle") int idDetalle);
}
