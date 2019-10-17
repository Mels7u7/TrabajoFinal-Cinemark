package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.entity.EmpleadoxLC;

public interface EmpleadoxLCRepository extends JpaRepository<EmpleadoxLC,Integer> {

	@Query("from EmpleadoxLC e where e.listaEmpleadoLC like %:listaEmpleadoLC%")
	List<EmpleadoxLC> findByListaCompra(EmpleadoxLC listaEmpleadoLC);
	
	@Query("from EmpleadoxLC e where e.empleadoEmpleadoLC like %:empleadoEmpleadoLC%")
	List<EmpleadoxLC> findByEmpleado(EmpleadoxLC empleadoEmpleadoLC);
}
