package pe.edu.upc.controller;


import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import pe.edu.upc.entity.Factura;
import pe.edu.upc.service.IFacturaService;

@Controller
@SessionAttributes("factura")
@RequestMapping("/facturas")
public class FacturaController {

	@Autowired
	private IFacturaService fService;
	
	
	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/nuevo")
	public String nuevoFactura(Model model) {
		model.addAttribute("factura", new Factura());
		return "factura/factura";
	}
	
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@PostMapping("/guardar")
	public String guardarFactura(@Valid Factura factura, BindingResult result, Model model,
			SessionStatus status) throws Exception {
		if (result.hasErrors()) {
			return "factura/factura";
		} else {
			int rpta = fService.insertar(factura);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe el contador con ese DNI");
				return  "/contador/contador";
			} else {
				model.addAttribute("mensaje", "Se ha registrado correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listaContadores", fService.listar());
		return "/factura/listaFactura";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/listar")
	public String listarFacturas(Model model) {
		try {
			model.addAttribute("factura", new Factura());
			model.addAttribute("listaFacturas", fService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/factura/listaFactura";
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				fService.eliminar(id);
				model.put("mensaje", "Se canceló el contrato con el contador");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede anular el contrato con el contador seleccionado");
		}
		model.put("listaContadores", fService.listar());

		return "redirect:/facturas/listar";
	}
	
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/detalle/{id}")//modificar
	public String detailsFactura(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Factura> factura = fService.listarId(id);
			if (!factura.isPresent()) {
				model.addAttribute("info", "facturar no existe");
				return "redirect:/facturas/listar";
			} else {
				model.addAttribute("factura", factura.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/factura/factura";
	}
	

	@Secured({"ROLE_ADMIN","ROLE_USER"})
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
	
	
	
	
	
	
	
	
	
}
