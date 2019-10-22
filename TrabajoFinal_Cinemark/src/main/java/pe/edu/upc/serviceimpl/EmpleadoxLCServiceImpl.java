package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Empleado;
import pe.edu.upc.entity.EmpleadoxLC;
import pe.edu.upc.entity.Lista_Compra;
import pe.edu.upc.repository.EmpleadoxLCRepository;
import pe.edu.upc.service.IEmpleadoxLCService;

@Service
public class EmpleadoxLCServiceImpl implements IEmpleadoxLCService{

	@Autowired
	private EmpleadoxLCRepository eR;
	
	@Override
	@Transactional
	public Integer insertar(EmpleadoxLC empleadoxLC) {
		int rpta = 0;
		if (rpta == 0) {
			eR.save(empleadoxLC);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void modificar(EmpleadoxLC empleadoxLC) {
		eR.save(empleadoxLC);
		
	}

	@Override
	@Transactional
	public void eliminar(int idEmpleadoXLC) {
		eR.deleteById(idEmpleadoXLC);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<EmpleadoxLC> listarId(int idEmpleadoXLC) {
		return eR.findById(idEmpleadoXLC);
	}

	@Override
	public List<EmpleadoxLC> listar() {
		
		return eR.findAll();
	}

	public List<EmpleadoxLC> buscarListaCompra(EmpleadoxLC listaEmpleadoLC) {
		
		return eR.findByListaCompra(listaEmpleadoLC);
	}

	public List<EmpleadoxLC> buscarEmpleado(EmpleadoxLC empleadoEmpleadoLC) {
		// TODO Auto-generated method stub
		return eR.findByEmpleado(empleadoEmpleadoLC);
	}

	@Override
	public List<EmpleadoxLC> buscarListaCompra(Lista_Compra lista_Compra) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpleadoxLC> buscarEmpleado(Empleado empleadoEmpleadoLC) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
