package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Detalle_List_Compra;
import pe.edu.upc.entity.Factura;
import pe.edu.upc.repository.FacturaRepository;
import pe.edu.upc.service.IDetalle_List_CompraService;
import pe.edu.upc.service.IFacturaService;

@Service
public class FacturaServiceImpl implements IFacturaService {

	@Autowired
	private FacturaRepository fR;
	
	@Autowired
	private IDetalle_List_CompraService serviceDetalle;

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
		Optional<Factura> lista = fR.findById(idFactura);
		if (lista.isPresent()) {
			final Factura obj = lista.get();

			List<Detalle_List_Compra> detalleLista = serviceDetalle.listar();
			float precioLista = 0;

			for (Detalle_List_Compra e : detalleLista.stream()
					.filter(c -> c.getListaDetalle().getIdLista() == obj.getIdFactura()).collect(Collectors.toList()))
				precioLista += e.getPrecioDetalle() * e.getUnidadesDetalle();

			obj.setPrecio(precioLista);

			return Optional.of(obj);

		}
		return lista;
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
