package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Detalle_List_Compra;
import pe.edu.upc.repository.Detalle_List_CompraRepository;
import pe.edu.upc.service.IDetalle_List_CompraService;

@Service
public class Detalle_List_CompraServiceImpl implements IDetalle_List_CompraService {

	@Autowired
	private Detalle_List_CompraRepository dR;
	
	@Override
	@Transactional
	public Integer insertar(Detalle_List_Compra detalle) {
		int rpta = 0;
		if( rpta == 0)
		{
			dR.save(detalle);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void modificar(Detalle_List_Compra detalle) {
		dR.save(detalle);	
	}

	@Override
	@Transactional
	public void eliminar(int idDetalle) {
		dR.deleteById(idDetalle);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Detalle_List_Compra> listarId(int idDetalle) {
		return dR.findById(idDetalle);
	}

	@Override
	public List<Detalle_List_Compra> listar() {
		return dR.findAll();
	}

	@Override
	public List<Detalle_List_Compra> buscarCantidadRecurso(int unidadesDetalle) {
		return dR.findByUnidadesDetalle(unidadesDetalle);
	}

}
