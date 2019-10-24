package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Recurso;
@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Integer>{
	@Query("select r from Recurso r where r.nombreRecurso like %?1%")
	public List<Recurso> findByNombreRecurso(String nombreRecurso);

	public List<Recurso> findByNombreRecursoLikeIgnoreCase(String recurso);
	
	@Query("select count(r.nombreRecurso) from Recurso r where r.nombreRecurso =:nombreRecurso")
	public int buscarNombreRecurso (@Param("nombreRecurso")String nombreRecurso);
}
