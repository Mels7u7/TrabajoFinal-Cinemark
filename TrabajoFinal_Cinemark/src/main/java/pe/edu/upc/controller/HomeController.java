package pe.edu.upc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

	@GetMapping(value = { "/home", "/" })
	public String home() {
		return "home";
	}
}
