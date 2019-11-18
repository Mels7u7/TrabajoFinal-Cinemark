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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Accountant;
import pe.edu.upc.entity.Detalle_List_Compra;
import pe.edu.upc.entity.Factura;
import pe.edu.upc.service.IAccountantService;
import pe.edu.upc.service.IDetalle_List_CompraService;
import pe.edu.upc.service.IFacturaService;
import pe.edu.upc.service.IListaService;

@Controller
@SessionAttributes("factura")
@RequestMapping("/facturas")
public class FacturaController {

	@Autowired
	private IFacturaService fService;

	@Autowired
	private IAccountantService cService;

	@Autowired
	private IListaService icService;

	@Autowired
	private IDetalle_List_CompraService serviceDetalle;

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/nuevo")
	public String nuevoFactura(Model model) {
		model.addAttribute("factura", new Factura());
		model.addAttribute("contador", new Accountant());
		model.addAttribute("listaContadores", cService.list());
		model.addAttribute("listaLista_Compras", icService.listar());
		model.addAttribute("valorBoton", "Registrar");
		return "/factura/factura";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@PostMapping("/guardar")
	public String guardarFactura(@Valid Factura factura, BindingResult result, Model model, SessionStatus status,
			RedirectAttributes redirAttrs) throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listaContadores", cService.list());
			model.addAttribute("listaLista_Compras", icService.listar());
			model.addAttribute("valorBoton", "Registrar");
			return "/factura/factura";
		} else {
			int rpta = -1;
			Optional<Factura> facturaEncontrado = fService.listarId(factura.getIdFactura());
			if (!facturaEncontrado.isPresent()) {
				rpta = fService.insertar(factura);
				redirAttrs.addFlashAttribute("mensaje", "Se registr\u00f3 correctamente");
				if (rpta > 0) {
					model.addAttribute("valorBoton", "Registrar");
					status.setComplete();
					return "/factura/factura";
				}
			} else {
				fService.modificar(factura);
				rpta = 1;
				redirAttrs.addFlashAttribute("mensaje", "Se modific\u00f3 correctamente");
			}

		}
		model.addAttribute("listaFacturas", fService.listar());

		return "redirect:/facturas/listar";

	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/listar")
	public String listarFacturas(Model model) {
		try {
			model.addAttribute("factura", new Factura());

			List<Factura> list = fService.listar();
			List<Detalle_List_Compra> detalleLista = serviceDetalle.listar();

			for (Factura l : list) {
				float precioLista = 0;

				for (Detalle_List_Compra e : detalleLista.stream()
						.filter(c -> c.getListaDetalle().getIdLista() == l.getListaFactura().getIdLista())
						.collect(Collectors.toList()))
					precioLista += e.getPrecioDetalle() * e.getUnidadesDetalle();

				l.setPrecio(precioLista);

			}

			model.addAttribute("listaFacturas", list);

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/factura/listaFactura";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id,
			RedirectAttributes redirAttrs) {
		try {
			if (id != null && id > 0) {
				fService.eliminar(id);
				redirAttrs.addFlashAttribute("mensaje", "Se cancel\u00f3 la factura");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede anular la factura");
		}
		redirAttrs.addFlashAttribute("listaFacturas", fService.listar());

		return "redirect:/facturas/listar";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/detalle/{id}") // modificar
	public String detailsFactura(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Factura> factura = fService.listarId(id);
			if (!factura.isPresent()) {
				model.addAttribute("info", "facturar no existe");
				return "redirect:/facturas/listar";
			} else {
				model.addAttribute("factura", factura.get());
				model.addAttribute("listaContadores", cService.list());
				model.addAttribute("listaLista_Compras", icService.listar());

			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		model.addAttribute("valorBoton", "Modificar");
		return "/factura/factura";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Factura> factura = fService.listarId(id);
		if (factura == null) {
			flash.addFlashAttribute("error", "La factura no existe en la base de datos");
			return "redirect:/facturas/listar";
		}
		model.put("factura", factura.get());
		return "factura/verf";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Factura factura) throws ParseException {

		List<Factura> listaFacturas;
		listaFacturas = fService.buscarNombreContador(factura.getContadorFactura().getName());
		if (listaFacturas.isEmpty()) {
			model.put("mensaje", "No se encontr\u00f3 al contador con el nombre especificado");
		}
		model.put("listaFacturas", listaFacturas);
		return "factura/listaFactura";
	}

}
