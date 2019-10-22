package pe.edu.upc.controller;


import java.util.Map;



import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import pe.edu.upc.service.IContadorService;
import pe.edu.upc.service.IFacturaService;
import pe.edu.upc.service.ILista_CompraService;

@Controller
@SessionAttributes("factura")
@RequestMapping("/facturas")
public class FacturaController {

	@Autowired
	private IFacturaService fService;
	
	@Autowired
	private IContadorService cService;
	
	@Autowired
	private ILista_CompraService icService;
	
	
	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}
	
	
	@GetMapping("/nuevo")
	public String nuevoFactura(Model model) {
		model.addAttribute("factura", new Factura());
		model.addAttribute("listaContadores", cService.listar());
		model.addAttribute("listaLista_Compras", icService.listar());
		return "/factura/factura";
	}
	
	
	
	@PostMapping("/guardar")
	public String guardarFactura(@Valid Factura factura, BindingResult result, Model model,
			SessionStatus status) throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listaContadores", cService.listar());
			return "/factura/factura";
		} else {
			int rpta = fService.insertar(factura);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe esa Factura");
				model.addAttribute("listaContadores", cService.listar());
				model.addAttribute("listaLista_Compras", icService.listar());
				return  "/factura/factura";
			} else {
				model.addAttribute("mensaje", "Se ha registrado correctamente");
				status.setComplete();
				
			}
		}
		model.addAttribute("listaFacturas", fService.listar());
		return "/factura/listaFactura";
		
	}
	
	
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
	
	
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				fService.eliminar(id);
				model.put("mensaje", "Se canceló la factura");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede anular la factura");
		}
		model.put("listaFacturas", fService.listar());

		return "redirect:/facturas/listar";
	}
	
	
	
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
