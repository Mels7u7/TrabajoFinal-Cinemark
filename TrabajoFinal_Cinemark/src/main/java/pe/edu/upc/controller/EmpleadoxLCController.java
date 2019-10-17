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

import pe.edu.upc.entity.Contador;
import pe.edu.upc.entity.EmpleadoxLC;
import pe.edu.upc.service.IEmpleadoxLCService;

@Controller
@SessionAttributes("empleadoxLC")
@RequestMapping("/empleadosxLC")
public class EmpleadoxLCController {

	@Autowired
	private IEmpleadoxLCService eService;
	
	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}
	
	
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/nuevo")
	public String nuevoEmpleadoxLC(Model model) {
		model.addAttribute("empleadoxLC", new EmpleadoxLC());
		return "empleadoxLC/empleadoxLC";
	}
	
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@PostMapping("/guardar")
	public String guardarEmpleadoxLC(@Valid EmpleadoxLC empleadoxLC, BindingResult result, Model model,
			SessionStatus status) throws Exception {
		if (result.hasErrors()) {
			return "/empleadoxLC/empleadoxLC";
		} else {
			int rpta = eService.insertar(empleadoxLC);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe el contador con ese DNI");
				return  "/empleadoxLC/empleadoxLC";
			} else {
				model.addAttribute("mensaje", "Se ha registrado correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listaEmpleadosxLC", eService.listar());
		return "/empleadoxLC/listaEpleadoxLC";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/listar")
	public String listarContadores(Model model) {
		try {
			model.addAttribute("contador", new Contador());
			model.addAttribute("listaContadores", eService.listar());
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
				eService.eliminar(id);
				model.put("mensaje", "Se canceló el contrato con el contador");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede anular el contrato con el contador seleccionado");
		}
		model.put("listaEmpleadosxLC", eService.listar());

		return "redirect:/empleadosxLC/listar";
	}
	

	@Secured("ROLE_ADMIN")
	@GetMapping("/detalle/{id}")//modificar
	public String detailsEmpleadoxLC(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<EmpleadoxLC> empleadoxLC = eService.listarId(id);
			if (!empleadoxLC.isPresent()) {
				model.addAttribute("info", "contador no existe");
				return "redirect:/empeladosxLC/listar";
			} else {
				model.addAttribute("empleadoxLC", empleadoxLC.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/empleadoxLC/empleadoxLC";
	}
	

	
	
	
	
	
	
	
	
	
	
}
