package pe.edu.upc.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


import pe.edu.upc.entity.Factura;

public interface IFacturaService {

	public Integer insertar(Factura factura);
	public void modificar(Factura factura);
	public void eliminar(int idFactura);
	Optional<Factura> listarId(int idFactura);
	List<Factura> listar();
	List<Factura> buscarFecha (Date fechaFactura);
	
	
}
