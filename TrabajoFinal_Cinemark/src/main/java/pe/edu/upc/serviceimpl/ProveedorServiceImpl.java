package pe.edu.upc.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Proveedor;
import pe.edu.upc.repository.ProveedorRepository;
import pe.edu.upc.service.IProveedorService;

@Service
public class ProveedorServiceImpl implements IProveedorService {

	@Autowired
	private ProveedorRepository pR;

	@Override
	@Transactional(readOnly = true)
	public List<Proveedor> listar() {
		return pR.findAll(Sort.by(Sort.Direction.DESC, "nombreProveedor"));
	}

}
