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

import pe.edu.upc.entity.Accountant;
import pe.edu.upc.service.IAccountantService;

@Controller
@SessionAttributes("contador")
@RequestMapping("/contadores")
public class AccountantController {
	@Autowired
	private IAccountantService cService;

	@RequestMapping("/welcome")
	public String goHome() {
		return "bienvenido";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/nuevo")
	public String addAccountant(Model model) {
		model.addAttribute("accountant", new Accountant());
		model.addAttribute("valorBoton", "Registrar");
		return "contador/contador";
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/guardar")
	public String saveAccountant(@Valid Accountant contador, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("valorBoton", "Registrar");
			return "/contador/contador";
		} else {
			int rpta = -1;
			Optional<Accountant> contadorEncontrado = cService.listarId(contador.getAccountantId());
			if (!contadorEncontrado.isPresent()) {
				rpta = cService.insertar(contador);
				model.addAttribute("mensaje", "Se registr\u00f3 correctamente");
				if (rpta > 0) {
					model.addAttribute("mensaje", "Ya existe el contador con ese DNI");
					model.addAttribute("valorBoton", "Registrar");
					status.setComplete();
					return "/contador/contador";
				}

			} else {
				cService.modificar(contador);
				rpta = 1;
				status.setComplete();
				model.addAttribute("mensaje", "Se modific\u00f3 correctamente");
			}

		}
		model.addAttribute("listaContadores", cService.listar());

		return "redirect:/contadores/listar";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/listar")
	public String listarContadores(Model model) {
		try {
			model.addAttribute("contador", new Accountant());
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
				model.put("mensaje", "Se cancel\u00f3 el contrato con el contador");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede anular el contrato con el contador seleccionado");
		}
		model.put("listaContadores", cService.listar());

		return "redirect:/contadores/listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/detalle/{id}") // modificar
	public String detailsContador(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Accountant> contador = cService.listarId(id);
			if (!contador.isPresent()) {
				model.addAttribute("info", "Contador no existe");
				return "redirect:/contadores/listar";
			} else {
				model.addAttribute("contador", contador.get());

			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		model.addAttribute("valorBoton", "Modificar");
		return "/contador/contador";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Accountant accountant) throws ParseException {

		List<Accountant> listaContadores;

		accountant.setName(accountant.getName());
		listaContadores = cService.buscarNombre(accountant.getName());
		if (listaContadores.isEmpty()) {
			model.put("mensaje", "No se encontr\u00f3 al contador con el nombre especificado");
		}
		model.put("listaContadores", listaContadores);
		return "contador/listaContador";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Accountant> accountant = cService.listarId(id);
		if (accountant == null) {
			flash.addFlashAttribute("error", "El contador no existe en la base de datos");
			return "redirect:/contadores/listar";
		}
		model.put("contador", accountant.get());

		return "contador/verc";
	}
}
