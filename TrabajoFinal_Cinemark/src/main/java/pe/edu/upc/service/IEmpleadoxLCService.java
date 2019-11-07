package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;
import pe.edu.upc.entity.EmpleadoxLC;

public interface IEmpleadoxLCService {

	public Integer insertar(EmpleadoxLC empleadoxLC);

	public void modificar(EmpleadoxLC empleadoxLC);

	public void eliminar(int idEmpleadoXLC);

	Optional<EmpleadoxLC> listarId(int idEmpleadoXLC);

	List<EmpleadoxLC> listar();

	List<EmpleadoxLC> buscarNombreEmpleado(String nombreEmpleado);

}
