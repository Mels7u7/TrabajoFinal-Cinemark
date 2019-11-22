package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Lista_Compra;

public interface IListaService {

	public Integer insertar(Lista_Compra lista);
	
	public void eliminar(int idLista);

	public void modificar(Lista_Compra lista);

	Optional<Lista_Compra> listarId(int idLista);

	List<Lista_Compra> listar();

	List<Lista_Compra> buscar(String notaLista);

	List<Lista_Compra> buscarEstado(String estadoLista);

	List<Lista_Compra> buscarProveedor(String nombreProveedor);
	
	List<Lista_Compra> buscarespefico(int id);

}
