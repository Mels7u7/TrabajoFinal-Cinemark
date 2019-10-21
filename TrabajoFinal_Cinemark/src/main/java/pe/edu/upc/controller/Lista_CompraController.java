package pe.edu.upc.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.Lista_Compra;
import pe.edu.upc.service.ILista_CompraService;
import pe.edu.upc.service.IProveedorService;

@Controller
@RequestMapping("/listaCompras")
public class Lista_CompraController {
	
	@Autowired
	private ILista_CompraService lService;
	@Autowired
	private IProveedorService pService;

	@GetMapping("/bienvenido")
	public String bienvenido(Model model) {
		return "bienvenido";
	}

	@GetMapping("/nuevo")
	public String nuevaLista_Compra(Model model) {
		model.addAttribute("lista_Compra", new Lista_Compra());
		model.addAttribute("listaProveedores", pService.listar());
		return "/listaCompra/listaCompra";
	}

	@PostMapping("/guardar")
	public String guardarLista_Compra(@Valid Lista_Compra lista_Compra, BindingResult result, Model model,
			SessionStatus status) throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listaProveedor", pService.listar());
			return "/listaCompra/listaCompra";
		} else {
			lService.insertar(lista_Compra);
			model.addAttribute("mensaje", "Se guardó correctamente la La lista de compra");
			status.setComplete();
			return "redirect:/listaCompras/listar";
		}
	}

	@GetMapping("/listar")
	public String listarLista_Compras(Model model) {
		try {
			model.addAttribute("lista_Compra", new Lista_Compra());
			model.addAttribute("listaLista_Compras", lService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/listaCompra/listaListaCompra";
	}

	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				lService.eliminar(id);
				model.put("mensaje", "Se elimino correctamente la lista de compra");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar la lista de compra");
		}
		model.put("listaLista_Compras", lService.listar());
		return "redirect:/listaCompras/listar";
	}
}
