package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Lista_Compra;

public interface ILista_CompraService {

	public Integer insertar(Lista_Compra lista_Compra);

	public void eliminar(int idLista);

	List<Lista_Compra> listar();
	
	Optional<Lista_Compra> listarId(int idLista);
	
	List<Lista_Compra> buscarEstadoLista(String estadoLista);
}
