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

import pe.edu.upc.entity.EmpleadoxLC;
import pe.edu.upc.service.IEmpleadoService;
import pe.edu.upc.service.IEmpleadoxLCService;
import pe.edu.upc.service.ILista_CompraService;

@Controller
@SessionAttributes("empleadoxLC")
@RequestMapping("/empleadoxLCs")
public class EmpleadoxLCController {

	@Autowired
	private IEmpleadoxLCService elService;
	
	@Autowired
	private ILista_CompraService lService;
	
	@Autowired
	private IEmpleadoService eService;
	
	
	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}
	
	
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/nuevo")
	public String nuevoEmpleadoxLC(Model model) {
		model.addAttribute("empleadoxLC", new EmpleadoxLC());
		model.addAttribute("listaEmpleados", eService.listar());
		model.addAttribute("listaLista_Compras", lService.listar());
		
		return "empleadoxLC/empleadoxLC";
	}
	
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@PostMapping("/guardar")
	public String guardarEmpleadoxLC(@Valid EmpleadoxLC empleadoxLC, BindingResult result, Model model,
			SessionStatus status) throws Exception {
		if (result.hasErrors()) {
			return "/empleadoxLC/empleadoxLC";
		} else {
			int rpta = elService.insertar(empleadoxLC);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe un empleado con ese nombre");
				model.addAttribute("listaEmpleados", eService.listar());
				model.addAttribute("listaLista_Compras", lService.listar());
				return  "/empleadoxLC/empleadoxLC";
			} else {
				model.addAttribute("mensaje", "Se ha registrado correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listaEmpleadoxLCs", elService.listar());
		return "/empleadoxLC/listaEmpleadoxLC";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/listar")
	public String listarEmpleadoxLC(Model model) {
		try {
			model.addAttribute("empleadoxLC", new EmpleadoxLC());
			model.addAttribute("listaEmpleadoxLCs", elService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/empleadoxLC/listaEmpleadoxLC";
	}
	
	@GetMapping("/detalle/{id}")//modificar
	public String detailsEmpleadoxLC(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<EmpleadoxLC> empleadoxLC = elService.listarId(id);
			if (!empleadoxLC.isPresent()) {
				model.addAttribute("info", "facturar no existe");
				return "redirect:/empleadoxLCs/listar";
			} else {
				model.addAttribute("empleadoxLC", empleadoxLC.get());
				model.addAttribute("listaEmpleados", eService.listar());
				model.addAttribute("listaLista_Compras", lService.listar());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/empleadoxLC/empleadoxLC";
	}
	
	
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				elService.eliminar(id);
				model.put("mensaje", "Se canceló la relación empleado por orden de compra");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede anular la realción empleado por orden de compra seleccionada");
		}
		model.put("listaEmpleadoxLCs", eService.listar());

		return "redirect:/empleadoxLCs/listar";
	}
	
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute EmpleadoxLC empleadoxLC) throws ParseException {

		List<EmpleadoxLC> listaEmpleadoxLCs;
		listaEmpleadoxLCs = elService.buscarNombreEmpleado(empleadoxLC.getEmpleadoEmpleadoLC().getNombreEmpleado());
		if (listaEmpleadoxLCs.isEmpty()) {
			model.put("mensaje", "No se encontr� al empleado con el nombre especificado");
		}
		model.put("listaEmpleadoxLCs", listaEmpleadoxLCs);
		return "empleadoxLC/listaEmpleadoxLC";
	}
		


	
	
	
	
	
	
	
	
}
