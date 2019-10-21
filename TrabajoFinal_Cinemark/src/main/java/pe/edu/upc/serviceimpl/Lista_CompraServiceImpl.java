package pe.edu.upc.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Lista_Compra;
import pe.edu.upc.repository.Lista_CompraRepository;
import pe.edu.upc.service.ILista_CompraService;

@Service
public class Lista_CompraServiceImpl implements ILista_CompraService {

	@Autowired
	private Lista_CompraRepository lR;

	@Override
	@Transactional
	public Integer insertar(Lista_Compra lista_Compra) {
		int rpta = 0;
		if (rpta == 0) {
			lR.save(lista_Compra);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void eliminar(int idLista) {
		lR.deleteById(idLista);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lista_Compra> listar() {
		return lR.findAll(Sort.by(Sort.Direction.DESC, "notaLista"));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lista_Compra> buscarEstadoLista(String estadoLista) {
		return lR.findByEstadoLista(estadoLista);
	}

}
