package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.Recurso;

public interface IRecursoService {
	public Integer insertar(Recurso recurso);

	public void modificar(Recurso recurso);

	public void eliminar(int idRecurso);

	Recurso listarId(int idRecurso);

	List<Recurso> listar();

	List<Recurso> buscar(String nombreRecurso);

	List<Recurso> buscarNombreRecursoCompleto(String nombreRecurso);
}
