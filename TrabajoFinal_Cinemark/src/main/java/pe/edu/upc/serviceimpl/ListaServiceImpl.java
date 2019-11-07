package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Detalle_List_Compra;
import pe.edu.upc.entity.Lista_Compra;
import pe.edu.upc.repository.ListaRepository;
import pe.edu.upc.service.IDetalle_List_CompraService;
import pe.edu.upc.service.IListaService;

@Service
public class ListaServiceImpl implements IListaService {

	@Autowired
	private ListaRepository lR;

	@Autowired
	private IDetalle_List_CompraService serviceDetalle;

	@Override
	@Transactional
	public Integer insertar(Lista_Compra lista) {
		int rpta = 0;
		if (rpta == 0) {
			lR.save(lista);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void eliminar(int idLista) {
		lR.deleteById(idLista);
	}

	@Override
	@Transactional
	public void modificar(Lista_Compra lista) {
		lR.save(lista);
	}

	@Override
	public Optional<Lista_Compra> listarId(int idLista) {
		Optional<Lista_Compra> lista = lR.findById(idLista);
		if (lista.isPresent()) {
			final Lista_Compra obj = lista.get();

			List<Detalle_List_Compra> detalleLista = serviceDetalle.listar();
			float precioLista = 0;

			for (Detalle_List_Compra e : detalleLista.stream()
					.filter(c -> c.getListaDetalle().getIdLista() == obj.getIdLista()).collect(Collectors.toList()))
				precioLista += e.getPrecioDetalle() * e.getUnidadesDetalle();

			obj.setPrecioLista(precioLista);

			return Optional.of(obj);

		}
		return lista;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lista_Compra> listar() {
		return lR.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lista_Compra> buscar(String notaLista) {
		return lR.findByNotaLista(notaLista);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lista_Compra> buscarEstado(String estadoLista) {
		return lR.buscarEstado(estadoLista);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lista_Compra> buscarProveedor(String nombreProveedor) {
		return lR.findLista_CompraBynombreProveedor(nombreProveedor);
	}

}
