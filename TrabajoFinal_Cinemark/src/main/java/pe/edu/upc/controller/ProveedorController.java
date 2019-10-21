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

import pe.edu.upc.entity.Proveedor;
import pe.edu.upc.service.IProveedorService;

@Controller
@SessionAttributes("proveedor")
@RequestMapping("/proveedores")
public class ProveedorController {

	@Autowired
	private IProveedorService pService;

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}

	@Secured({ "ROLE ADMIN", "ROLE_USER" })
	@GetMapping("/nuevo")
	public String nuevoProveedor(Model model) {
		model.addAttribute("proveedpr", new Proveedor());
		return "proveedor/proveedor";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@PostMapping("/guardar")
	public String guardarProveedor(@Valid Proveedor proveedor, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "/proveedor/proveedor";
		} else {
			int rpta = pService.insertar(proveedor);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe el proveedor con ese RUC");
				return "/proveedor/proveedor";
			} else {
				model.addAttribute("mensaje", "Se ha registrado correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listaProveedores", pService.listar());
		return "/proveedor/listaProveedor";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/listar")
	public String listarProveedores(Model model) {
		try {
			model.addAttribute("proveedor", new Proveedor());
			model.addAttribute("listaProveedores", pService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/proveedor/listaProvedor";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/eliminar")
	public String eliminar(Map<String,Object>model,@RequestParam(value="id") Integer id) {
		try {
			if(id!=null&& id>0) {
				pService.eliminar(id);
				model.put("mensaje", "se cancelo el contrato con el proveedor seleccionado");	
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede anular el contrato con el proveedor seleccionado");
		}
		model.put("listaProveedores", pService.listar());
		return "redirect:/proveedores/listar";
		}
	@Secured("ROLE_ADMIN")
	@GetMapping("/detalle/{id}")
	public String detailsProveedor(@PathVariable(value= "id")int id, Model model) {
		try {
			Optional<Proveedor> proveedor=pService.listarId(id);
			if(!proveedor.isPresent()) {
				model.addAttribute("info","proveedor no existe");
				return "redirect:/proveedores/listar";
			}else {
					model.addAttribute("proveedor",proveedor.get());
				}
		}catch (Exception e) {
			model.addAttribute("error",e.getMessage());
		}
		return  "/proveedor/proveedor";
	}
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Proveedor proveedor) throws ParseException {

		List<Proveedor> listaProveedores;

		proveedor.setNombreProveedor(proveedor.getNombreProveedor());
		listaProveedores = pService.buscarNombre(proveedor.getNombreProveedor());
		if (listaProveedores.isEmpty()) {
			model.put("mensaje", "No se encontró al proveedor con el nombre especificado");
		}
		model.put("listaProvedores", listaProveedores);
		return "proveedor/listaContador";
	}
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Proveedor> proveedor = pService.listarId(id);
		if (proveedor == null) {
			flash.addFlashAttribute("error", "El proveedor no existe en la base de datos");
			return "redirect:/proveedores/listar";
		}
		model.put("proveedor", proveedor.get());

		return "proveedor/verp";
	}
	
		
	
	
		
	
	
			
	
}
