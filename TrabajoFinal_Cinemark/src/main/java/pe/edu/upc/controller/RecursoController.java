package pe.edu.upc.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Recurso;
import pe.edu.upc.service.IRecursoService;
import pe.edu.upc.service.IUploadFileService;

@Controller
@SessionAttributes("recurso")
@RequestMapping("/recursos")
public class RecursoController {
	@Autowired
	private IRecursoService rService;

	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping("/bienvenido")
	public String bienvenido(Model model) {
		return "bienvenido";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/nuevo")
	public String nuevoRecurso(Model model) {
		model.addAttribute("recurso", new Recurso());
		model.addAttribute("valorBoton", "Registrar");
		return "recurso/recurso";
	}

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@PostMapping("/guardar")
	public String guardarRecurso(@Valid Recurso recurso, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("valorBoton", "Registrar");
			return "/recurso/recurso";			
		} else {
			if (!foto.isEmpty()) {

				if (recurso.getIdRecurso() > 0 && recurso.getFoto() != null && recurso.getFoto().length() > 0) {

					uploadFileService.delete(recurso.getFoto());
				}

				String uniqueFilename = null;
				try {
					uniqueFilename = uploadFileService.copy(foto);
				} catch (IOException e) {
					e.printStackTrace();
				}

				flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

				recurso.setFoto(uniqueFilename);
			}
			int rpta = -1;
			Recurso obj = rService.listarId(recurso.getIdRecurso());
			if (obj == null) {
				rpta = rService.insertar(recurso);
				model.addAttribute("mensaje", "Se ha registrado correctamente");
				model.addAttribute("valorBoton", "Registrar");
			} else {
				rService.modificar(recurso);
				rpta = 1;
				model.addAttribute("mensaje", "Se ha modificado correctamente");
			}
			if (rpta > 0) {
				status.setComplete();
				
		}
	}
	model.addAttribute("listaRecursos",rService.listar());	
	return"/recurso/listaRecurso";

	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/listar")
	public String listarRecursos(Model model) {
		try {
			model.addAttribute("recurso", new Recurso());

			model.addAttribute("listaRecursos", rService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/recurso/listaRecurso";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				rService.eliminar(id);
				model.put("mensaje", "Se eliminó el recurso");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar el recurso seleccionado");
		}
		model.put("listaRecursos", rService.listar());

		return "redirect:/recursos/listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/detalle/{id}") // modificar
	public String detailsRecursos(@PathVariable(value = "id") int id, Model model) {
		try {
			Recurso recurso = rService.listarId(id);
			if (recurso == null) {
				model.addAttribute("info", "Recurso no existe");
				return "redirect:/recursos/listar";
			} else {
				model.addAttribute("recurso", recurso);
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		model.addAttribute("valorBoton", "Modificar");
		return "/recurso/recurso";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Recurso recurso) throws ParseException {

		List<Recurso> listaRecursos;

		recurso.setNombreRecurso(recurso.getNombreRecurso());
		listaRecursos = rService.buscar(recurso.getNombreRecurso());

		if (listaRecursos.isEmpty()) {
			listaRecursos = rService.buscarNombreRecursoCompleto(recurso.getNombreRecurso());
		}

		if (listaRecursos.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaRecursos", listaRecursos);
		return "recurso/listaRecurso";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Recurso recurso = rService.listarId(id);
		if (recurso == null) {
			flash.addFlashAttribute("error", "El recurso no existe en la base de datos");
			return "redirect:/recursos/listar";
		}
		model.put("recurso", recurso);

		return "recurso/verr";
	}

}
