package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Accountant;
import pe.edu.upc.repository.AccountantRepository;
import pe.edu.upc.service.IAccountantService;

@Service
public class AccountantServiceImpl implements IAccountantService {

	@Autowired
	private AccountantRepository cR;

	@Override
	@Transactional
	public Integer insertar(Accountant contador) {
		int rpta = cR.buscarDNIContador(contador.getDocumentNumber());
		if (rpta == 0) {
			cR.save(contador);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void modificar(Accountant contador) {
		cR.actualizar(contador.getName(), contador.getAccountingInstitution(), contador.getAccountingInstitution(),
				contador.getAccountantId());
	}

	@Override
	@Transactional
	public void eliminar(int idContador) {
		cR.deleteById(idContador);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Accountant> listarId(int idContador) {
		return cR.findById(idContador);
	}

	@Override
	public List<Accountant> listar() {
		return cR.findAll();
	}

	@Override
	public List<Accountant> buscarNombre(String nombreContador) {
		return cR.findByNombreContador(nombreContador);
	}

}
