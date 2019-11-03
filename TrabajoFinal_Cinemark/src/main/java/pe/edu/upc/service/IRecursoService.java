package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Recurso;

public interface IRecursoService {
	public Integer insertar(Recurso recurso);
	public void modificar(Recurso recurso);
	public void eliminar(int idRecurso);
	Optional<Recurso> listarId(int idRecurso);
	List<Recurso> listar();
	List<Recurso> buscar(String nombreRecurso);
	List<Recurso> buscarNombreRecursoCompleto(String nombreRecurso);
}
