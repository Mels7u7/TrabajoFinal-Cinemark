package pe.edu.upc.controller;

import java.text.ParseException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import pe.edu.upc.entity.Empleado;
import pe.edu.upc.entity.Users;
import pe.edu.upc.service.IUserService;
import pe.edu.upc.serviceimpl.EmpleadoServiceImpl;

@Controller
@SessionAttributes("empleado")
@RequestMapping("/empleados")
public class EmpleadoController {

	@Autowired
	private EmpleadoServiceImpl eService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IUserService uS;
	

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/nuevo")
	public String nuevoEmpleado(Model model) {
		model.addAttribute("empleado", new Empleado());
		model.addAttribute("valorBoton", "Registrar");
		return "empleado/empleado";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@PostMapping("/guardar")
	public String guardarEmpleado(@Valid Empleado empleado, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("valorBoton", "Registrar");
			return "/empleado/empleado";
		} else {
			
			Users aux = new Users();
			aux.setUsername(empleado.getUser().getUsername());
			aux.setPassword(empleado.getUser().getPassword());
			aux.setEnabled(true);
			
			String bcryptPassword = passwordEncoder.encode(aux.getPassword());
			aux.setPassword(bcryptPassword);
			
			uS.insert(aux);
			aux = uS.BuscarPorNombre(empleado.getUser().getUsername());

			uS.insRol("ROLE_USER", aux.getId());

			empleado.setUser(aux);
			
			
			
			
			int rpta = -1;
			Optional<Empleado> empleadoEncontrado = eService.listarId(empleado.getIdEmpleado());
			if (!empleadoEncontrado.isPresent()) {
	
				
				rpta = eService.insertar(empleado);
				model.addAttribute("mensaje", "Se registr� correctamente");
				if (rpta > 0) {
					model.addAttribute("mensaje", "Ya existe el empleado con ese DNI");
					model.addAttribute("valorBoton", "Registrar");
					status.setComplete();
					return "/empleado/empleado";
				}

			} else {
				eService.modificar(empleado);
				rpta = 1;
				status.setComplete();
				model.addAttribute("mensaje", "Se modific� correctamente");
			}

		}
		model.addAttribute("listaEmpleados", eService.listar());
		return "redirect:/empleados/listar";

	}
	
	
	
	
	

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/listar")
	public String listarEmpleados(Model model) {
		try {
			model.addAttribute("empleado", new Empleado());
			model.addAttribute("listaEmpleados", eService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/empleado/listaEmpleado";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				eService.eliminar(id);
				model.put("mensaje", "Se ha eliminado correctamente.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "La operaci�n ha sido cancelada.");
		}
		model.put("listaEmpleados", eService.listar());

		return "redirect:/empleados/listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/detalle/{id}") // modificar
	public String DetallesEmpleado(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Empleado> empleado = eService.listarId(id);
			if (!empleado.isPresent()) {
				model.addAttribute("info", "Empleado no existe.");
				return "redirect:/empleados/listar";
			} else {
				model.addAttribute("empleado", empleado.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		model.addAttribute("valorBoton", "Modificar");
		return "/empleado/empleado";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping("/buscar")
	public String BuscarPorPuesto(Map<String, Object> model, @ModelAttribute Empleado empleado) throws ParseException {

		List<Empleado> listaEmpleados;

		empleado.setPuestoEmpleado(empleado.getPuestoEmpleado());
		listaEmpleados = eService.BuscarPorPuesto(empleado.getPuestoEmpleado());
		if (listaEmpleados.isEmpty()) {
			model.put("mensaje", "No se encontr� al empleado con el puesto laboral especificado");
		}
		model.put("listaEmpleados", listaEmpleados);
		return "empleado/listaEmpleado";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Empleado> empleado = eService.listarId(id);
		if (empleado == null) {
			flash.addFlashAttribute("error", "El empleado no existe en la base de datos.");
			return "redirect:/empleados/listar";
		}
		model.put("empleado", empleado.get());

		return "empleado/vere";
	}
}
