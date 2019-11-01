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

import pe.edu.upc.entity.Contador;
import pe.edu.upc.service.IContadorService;

@Controller
@SessionAttributes("contador")
@RequestMapping("/contadores")
public class ContadorCotroller {
	@Autowired
	private IContadorService cService;

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/nuevo")
	public String nuevoContador(Model model) {
		model.addAttribute("contador", new Contador());
		return "contador/contador";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@PostMapping("/guardar")
	public String guardarContador(@Valid Contador contador, BindingResult result, Model model,
			SessionStatus status) throws Exception {
		if (result.hasErrors()) {
			return "/contador/contador";
		} else {
			int rpta = cService.insertar(contador);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe el contador con ese DNI");
				return  "/contador/contador";
			} else {
				model.addAttribute("mensaje", "Se ha registrado correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listaContadores", cService.listar());
		return "/contador/listaContador";
	}
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/listar")
	public String listarContadores(Model model) {
		try {
			model.addAttribute("contador", new Contador());
			model.addAttribute("listaContadores", cService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/contador/listaContador";
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				cService.eliminar(id);
				model.put("mensaje", "Se canceló el contrato con el contador");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede anular el contrato con el contador seleccionado");
		}
		model.put("listaContadores", cService.listar());

		return "redirect:/contadores/listar";
	}
	@Secured("ROLE_ADMIN")
	@GetMapping("/detalle/{id}")//modificar
	public String detailsContador(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Contador> contador = cService.listarId(id);
			if (!contador.isPresent()) {
				model.addAttribute("info", "contador no existe");
				return "redirect:/contadores/listar";
			} else {
				model.addAttribute("contador", contador.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/contador/contador";
	}
	
	
	
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Contador contador) throws ParseException {

		List<Contador> listaContadores;

		contador.setNombreContador(contador.getNombreContador());
		listaContadores = cService.buscarNombre(contador.getNombreContador());
		if (listaContadores.isEmpty()) {
			model.put("mensaje", "No se encontró al contador con el nombre especificado");
		}
		model.put("listaContadores", listaContadores);
		return "contador/listaContador";
	}
	
	
	
	
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Contador> contador = cService.listarId(id);
		if (contador == null) {
			flash.addFlashAttribute("error", "El contador no existe en la base de datos");
			return "redirect:/contadores/listar";
		}
		model.put("contador", contador.get());

		return "contador/verc";
	}
}
