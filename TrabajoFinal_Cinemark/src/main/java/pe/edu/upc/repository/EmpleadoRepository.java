package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{
	@Query("from Empleado e where e.puestoEmpleado like %:puestoEmpleado%")
	List <Empleado> findByPuesto(@Param("puestoEmpleado")String puestoEmpleado);

	@Query("select count(e.dniEmpleado) from Empleado e where e.dniEmpleado =:dniEmpleado")
	public int buscarDNIEmpleado(@Param("dniEmpleado") String dniEmpleado);
}
