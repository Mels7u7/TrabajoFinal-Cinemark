package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Factura;
import pe.edu.upc.repository.FacturaRepository;
import pe.edu.upc.service.IFacturaService;

@Service
public class FacturaServiceImpl implements IFacturaService {

	
	@Autowired
	private FacturaRepository fR;
	
	
	@Override
	@Transactional
	public Integer insertar(Factura factura) {
		
		int rpta = 0;
		if (rpta == 0) {
			fR.save(factura);
		}
		return rpta;
		
	}

	@Override
	@Transactional
	public void modificar(Factura factura) {
		
		fR.save(factura);
	}

	@Override
	@Transactional
	public void eliminar(int idFactura) {
		
		fR.deleteById(idFactura);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Factura> listarId(int idFactura) {
		return fR.findById(idFactura);
	}

	@Override
	public List<Factura> listar() {
		return fR.findAll();
	}
	
	@Override
	public List<Factura> buscarNombreContador(String nombreContador) {
		return fR.findByNombreContador(nombreContador);
	}


}
