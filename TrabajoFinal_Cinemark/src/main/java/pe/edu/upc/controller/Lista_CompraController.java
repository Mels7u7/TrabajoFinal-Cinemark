package pe.edu.upc.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.upc.entity.Detalle_List_Compra;
import pe.edu.upc.entity.Lista_Compra;
import pe.edu.upc.service.IDetalle_List_CompraService;
import pe.edu.upc.service.IListaService;
import pe.edu.upc.service.IProveedorService;

@Controller
@RequestMapping("/listaCompras")
public class Lista_CompraController {

	@Autowired
	private IListaService lService;
	@Autowired
	private IProveedorService pService;

	@Autowired
	private IDetalle_List_CompraService serviceDetalle;

	@GetMapping("/bienvenido")
	public String bienvenido(Model model) {
		return "bienvenido";
	}

	@GetMapping("/nuevo")
	public String nuevaLista_Compra(Model model) {
		model.addAttribute("lista_Compra", new Lista_Compra());
		model.addAttribute("listaProveedores", pService.listar());
		model.addAttribute("valorBoton", "Registrar");
		return "/listaCompra/listaCompra";
	}

	@PostMapping("/guardar")
	public String guardarLista_Compra(@Valid Lista_Compra lista_Compra, BindingResult result, Model model,
			SessionStatus status) throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listaProveedor", pService.listar());
			model.addAttribute("valorBoton", "Registrar");
			return "/listaCompra/listaCompra";
		} else {
			int rpta = -1;
			Optional<Lista_Compra> listaEncontrado = lService.listarId(lista_Compra.getIdLista());
			if (!listaEncontrado.isPresent()) {
				rpta = lService.insertar(lista_Compra);
				model.addAttribute("mensaje", "Se registr� correctamente");
				if (rpta > 0) {
					model.addAttribute("valorBoton", "Registrar");
					status.setComplete();
					return "/listaCompra/listaCompra";
				}

			} else {
				lService.modificar(lista_Compra);
				rpta = 1;
				status.setComplete();
				model.addAttribute("mensaje", "Se modifico correctamente");
			}
		}
		model.addAttribute("listaLista_Compras", lService.listar());

		return "/listaCompra/listaListaCompra";
	}

	@GetMapping("/listar")
	public String listarLista_Compras(Model model) {
		try {
			model.addAttribute("lista_Compra", new Lista_Compra());

			List<Lista_Compra> list = lService.listar();
			List<Detalle_List_Compra> detalleLista = serviceDetalle.listar();

			for (Lista_Compra l : list) {
				float precioLista = 0;

				for (Detalle_List_Compra e : detalleLista.stream()
						.filter(c -> c.getListaDetalle().getIdLista() == l.getIdLista()).collect(Collectors.toList()))
					precioLista += e.getPrecioDetalle() * e.getUnidadesDetalle();

				l.setPrecioLista(precioLista);

			}

			model.addAttribute("listaLista_Compras", list);

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/listaCompra/listaListaCompra";
	}

	@RequestMapping("/eliminar")
	public String eliminar(Model model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				lService.eliminar(id);
				model.addAttribute("mensaje", "Se elimin� correctamente la lista de compra");
			}
		} catch (Exception e) {
			model.addAttribute("mensaje", "No se puede eliminar la lista de compra");
		}
		model.addAttribute("listaLista_Compras", lService.listar());
		return "redirect:/listaCompras/listar";

	}

	@RequestMapping("/buscarp")
	public String buscarProveedor(Map<String, Object> model, @ModelAttribute Lista_Compra lista) throws ParseException {

		List<Lista_Compra> listaListas;
		lista.setNotaLista(lista.getNotaLista());
		listaListas = lService.buscar(lista.getNotaLista());
		if (listaListas.isEmpty()) {
			listaListas = lService.buscarProveedor(lista.getNotaLista());
		}
		if (listaListas.isEmpty()) {
			model.put("mensaje", "No se encontro ningun resultado");
		}
		model.put("listaLista_Compras", listaListas);
		return "listaCompra/listaListaCompra";

	}

	@RequestMapping("/buscare")
	public String buscarEstado(Map<String, Object> model, @ModelAttribute Lista_Compra lista) throws ParseException {

		List<Lista_Compra> listaListas;
		lista.setNotaLista(lista.getNotaLista());
		listaListas = lService.buscar(lista.getNotaLista());
		if (listaListas.isEmpty()) {
			listaListas = lService.buscarEstado(lista.getNotaLista());
		}
		if (listaListas.isEmpty()) {
			model.put("mensaje", "No se encontro ningun resultado");
		}
		model.put("listaLista_Compras", listaListas);
		return "listaCompra/listaListaCompra";

	}

	@RequestMapping("/buscarm")
	public String buscarMayor(Map<String, Object> model, @ModelAttribute Lista_Compra lista) throws ParseException {

		List<Lista_Compra> listaListas;
		lista.setNotaLista(lista.getNotaLista());
		listaListas = lService.buscar(lista.getNotaLista());
		if (listaListas.isEmpty()) {
			listaListas = lService.buscarMayor(lista.getNotaLista());
		}
		if (listaListas.isEmpty()) {
			model.put("mensaje", "No se encontro ningun resultado");
		}
		model.put("listaLista_Compras", listaListas);
		return "listaCompra/listaListaCompra";

	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/detalle/{id}")
	public String detailsLista(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Lista_Compra> detalle = lService.listarId(id);
			if (!detalle.isPresent()) {
				model.addAttribute("info", "Lista de Compra no existe");
				return "redirect:/detalles/listar";
			} else {
				model.addAttribute("listaProveedores", pService.listar());
				model.addAttribute("lista_Compra", detalle.get());
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		model.addAttribute("valorBoton", "Modificar");

		return "/listaCompra/listaCompra";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Lista_Compra> lista_Compra = lService.listarId(id);
		if (lista_Compra == null) {
			flash.addFlashAttribute("error", "La lista de compra no existe en la base de datos.");
			return "redirect:/listaCompras/listar";
		}
		model.put("lista_Compra", lista_Compra.get());

		return "listaCompra/verlc";
	}

}
