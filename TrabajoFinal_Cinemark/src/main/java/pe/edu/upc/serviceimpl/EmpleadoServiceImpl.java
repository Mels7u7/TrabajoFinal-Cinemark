package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Empleado;
import pe.edu.upc.repository.EmpleadoRepository;
import pe.edu.upc.service.IEmpleadoService;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

	@Autowired
	private EmpleadoRepository eR;

	@Override
	@Transactional
	public Integer insertar(Empleado empleado) {
		int rpta = eR.buscarDNIEmpleado(empleado.getDniEmpleado());
		if (rpta == 0) {
			eR.save(empleado);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void modificar(Empleado empleado) {
		eR.save(empleado);
	}

	@Override
	@Transactional
	public void eliminar(int idEmpleado) {
		eR.deleteById(idEmpleado);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Empleado> listarId(int idEmpleado) {
		return eR.findById(idEmpleado);
	}

	@Override
	public List<Empleado> listar() {
		return eR.findAll();
	}

	@Override
	public List<Empleado> BuscarPorPuesto(String puestoEmpleado) {
		return eR.findByPuesto(puestoEmpleado);
	}

}
