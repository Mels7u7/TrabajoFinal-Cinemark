package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Proveedor;

public interface IProveedorService {
	public Integer insertar(Proveedor proveedor);

	public void modificar(Proveedor proveedor);

	public void eliminar(int idProveedor);

	Optional<Proveedor> listarId(int idProveedor);

	List<Proveedor> listar();

	List<Proveedor> buscarNombre(String nombreProveedor);

	List<Proveedor> buscarXRucProveedor(String RucProveedor);

}
