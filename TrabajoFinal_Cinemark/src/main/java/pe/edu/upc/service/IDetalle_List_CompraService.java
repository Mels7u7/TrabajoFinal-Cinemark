package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Detalle_List_Compra;

public interface IDetalle_List_CompraService {

	public Integer insertar(Detalle_List_Compra detalle);

	public void modificar(Detalle_List_Compra detalle);

	public void eliminar(int idDetalle);

	Optional<Detalle_List_Compra> listarId(int idDetalle);
	
	List<Detalle_List_Compra> listar();
	
	List<Detalle_List_Compra> buscarCantidadRecurso (int unidadesDetalle);

}
