package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Accountant;

public interface IAccountantService {
	public Integer insertar(Accountant contador);

	public void modificar(Accountant contador);

	public void eliminar(int idContador);

	Optional<Accountant> listarId(int idContador);

	List<Accountant> listar();

	List<Accountant> buscarNombre(String nombreContador);
}
