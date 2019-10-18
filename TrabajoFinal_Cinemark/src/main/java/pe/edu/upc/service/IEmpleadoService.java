package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Empleado;

public interface IEmpleadoService {

	public Integer insertar(Empleado empleado);

	public void modificar(Empleado empleado);

	public void eliminar(int idEmpleado);

	Optional<Empleado> listarId(int idEmpleado);

	List<Empleado> listar();

	List<Empleado> BuscarPorPuesto(String puestoEmpleado);

}
