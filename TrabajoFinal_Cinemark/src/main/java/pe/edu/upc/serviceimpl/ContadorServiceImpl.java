package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Contador;
import pe.edu.upc.repository.ContadorRepository;
import pe.edu.upc.service.IContadorService;

@Service
public class ContadorServiceImpl implements IContadorService {

	@Autowired
	private ContadorRepository cR;

	@Override
	@Transactional
	public Integer insertar(Contador contador) {
		int rpta = cR.buscarDNIContador(contador.getDniContador());
		if (rpta == 0) {
			cR.save(contador);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void modificar(Contador contador) {
		cR.actualizar(contador.getNombreContador(), contador.getInstitucionContador(),
				contador.getNumeroContactoContador(), contador.getIdContador());
	}

	@Override
	@Transactional
	public void eliminar(int idContador) {
		cR.deleteById(idContador);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Contador> listarId(int idContador) {
		return cR.findById(idContador);
	}

	@Override
	public List<Contador> listar() {
		return cR.findAll();
	}

	@Override
	public List<Contador> buscarNombre(String nombreContador) {
		return cR.findByNombreContador(nombreContador);
	}

}
