package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Recurso;
import pe.edu.upc.repository.RecursoRepository;
import pe.edu.upc.service.IRecursoService;

@Service
public class RecursoServiceImpl implements IRecursoService{

	@Autowired
	private RecursoRepository rR;
	
	@Override
	@Transactional
	public Integer insertar(Recurso recurso) {
		int rpta = rR.buscarNombreRecurso(recurso.getNombreRecurso());
		if (rpta == 0) {
			rR.save(recurso);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void modificar(Recurso recurso) {
		rR.save(recurso);
	}

	@Override
	@Transactional
	public void eliminar(int idRecurso) {
		rR.deleteById(idRecurso);
	}

	@Override
	@Transactional(readOnly = true)
	public Recurso listarId(int idRecurso) {
		Optional<Recurso> op=rR.findById(idRecurso);
		return op.isPresent()?op.get():null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Recurso> listar() {
		return rR.findAll(Sort.by(Sort.Direction.ASC, "nombreRecurso"));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Recurso> buscar(String nombreRecurso) {
		return rR.findByNombreRecurso(nombreRecurso);
	}

	@Override
	public List<Recurso> buscarNombreRecursoCompleto(String nombreRecurso) {
		return rR.findByNombreRecursoLikeIgnoreCase(nombreRecurso);
	}

}
