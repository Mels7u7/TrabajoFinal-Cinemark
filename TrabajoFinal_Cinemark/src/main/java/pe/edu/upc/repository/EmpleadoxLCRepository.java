package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.entity.EmpleadoxLC;

public interface EmpleadoxLCRepository extends JpaRepository<EmpleadoxLC, Integer> {

	@Query("from EmpleadoxLC e where e.empleadoEmpleadoLC.nombreEmpleado like %:nombreEmpleado%")
	List<EmpleadoxLC> findByNombreEmpleado(@Param("nombreEmpleado") String nombreEmpleado);

}
