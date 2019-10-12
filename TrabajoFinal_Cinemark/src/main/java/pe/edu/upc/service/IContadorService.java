package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Contador;

public interface IContadorService {
	public Integer insertar(Contador contador);
	public void modificar(Contador contador);
	public void eliminar(int idContador);
	Optional<Contador> listarId(int idContador);
	List<Contador> listar();
	List<Contador> buscarNombre(String nombreContador);
}
