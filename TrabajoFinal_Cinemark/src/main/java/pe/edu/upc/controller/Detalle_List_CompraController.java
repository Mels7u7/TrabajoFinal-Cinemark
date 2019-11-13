package pe.edu.upc.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import pe.edu.upc.entity.Detalle_List_Compra;
import pe.edu.upc.service.IDetalle_List_CompraService;
import pe.edu.upc.service.IListaService;
import pe.edu.upc.service.IRecursoService;

@Controller
@SessionAttributes("detalle")
@RequestMapping("/detalles")
public class Detalle_List_CompraController {

	@Autowired
	private IDetalle_List_CompraService dService;
	@Autowired
	private IListaService lService;
	@Autowired
	private IRecursoService rService;

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/nuevo")
	public String nuevoDetalle(Model model) {
		model.addAttribute("detalle", new Detalle_List_Compra());
		model.addAttribute("listaCompras", lService.listar());
		model.addAttribute("listaRecursos", rService.listar());
		model.addAttribute("valorBoton", "Registrar");
		return "detalle/detalle";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@PostMapping("/guardar")
	public String guardarDetalle(@Valid Detalle_List_Compra detalle, BindingResult result, Model model,
			SessionStatus status) throws Exception {

		if (result.hasErrors()) {
			model.addAttribute("listaCompras", lService.listar());
			model.addAttribute("listaRecursos", rService.listar());

			return "/detalle/detalle";
		} else {
			int rpta = -1;
			Optional<Detalle_List_Compra> detalleEncontrado = dService.listarId(detalle.getIdDetalle());
			if (!detalleEncontrado.isPresent()) {
				rpta = dService.insertar(detalle);
				model.addAttribute("mensaje", "Se registr\u00f3 correctamente");
				if (rpta > 0) {
					model.addAttribute("valorBoton", "Registrar");
					status.setComplete();
					return "/detalle/detalle";
				}

			} else {
				dService.modificar(detalle);
				rpta = 1;
				status.setComplete();
				model.addAttribute("mensaje", "Se modific\u00f3 correctamente");
			}

		}
		model.addAttribute("listaDetalles", dService.listar());

		return "redirect:/detalles/listar";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/listar")
	public String listarDetalles(Model model) {
		try {
			model.addAttribute("detalle", new Detalle_List_Compra());
			model.addAttribute("listaDetalles", dService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/detalle/listaDetalle";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {

		try {
			if (id != null && id > 0) {
				dService.eliminar(id);
				model.put("mensaje", "Se ha eliminado el detalle de compra correctamente.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar el detalle de compra seleccionado.");
		}
		model.put("listaDetalles", dService.listar());

		return "redirect:/detalles/listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/detalle/{id}")
	public String detailsDetalle(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Detalle_List_Compra> detalle = dService.listarId(id);
			if (!detalle.isPresent()) {
				model.addAttribute("info", "Detalle no existe");
				return "redirect:/detalles/listar";
			} else {
				model.addAttribute("listaCompras", lService.listar());
				model.addAttribute("listaRecursos", rService.listar());
				model.addAttribute("detalle", detalle.get());
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		model.addAttribute("valorBoton", "Modificar");

		return "/detalle/detalle";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Detalle_List_Compra detalle) throws ParseException {

		List<Detalle_List_Compra> listaDetalles;

		detalle.setRecursoDetalle(detalle.getRecursoDetalle());
		listaDetalles = dService.FindRecursosByListaCompra(detalle.getListaDetalle().getIdLista());

		if (listaDetalles.isEmpty()) {
			model.put("mensaje", "No se encontraron recursos con la cantidad de unidades especificado.");

		}
		model.put("listaDetalles", listaDetalles);
		return "detalle/listaDetalle";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Detalle_List_Compra> detalle = dService.listarId(id);
		if (detalle == null) {
			flash.addFlashAttribute("error", "El detalle de lista de compra no existe en la base de datos.");
			return "redirect:/detalles/listar";
		}
		model.put("detalle", detalle.get());

		return "detalle/verd";
	}

}
