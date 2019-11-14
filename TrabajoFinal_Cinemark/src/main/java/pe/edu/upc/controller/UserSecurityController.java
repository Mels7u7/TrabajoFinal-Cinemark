package pe.edu.upc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Users;
import pe.edu.upc.service.ISecurityService;

@Controller
@RequestMapping(value = "security/")
public class UserSecurityController {

	@Autowired
	private ISecurityService securityService;

	@GetMapping(value = { "/createAccount" })
	public String createAccountView() {
		return "/usersecurity/createAccount";
	}

	@PostMapping(value = { "/createAccount" })
	public String createAccount(@RequestParam(value = "error", required = false) String error, Model model, Users user,
			RedirectAttributes flash) {

		Pair<Boolean, String> outData = securityService.createUser(user.getUsername(), user.getPassword(), user.getEmail(),
																	user.getName(),user.getLastName(),"ROLE_USER");

		if (outData.getFirst()) {
			model.addAttribute("error", outData.getSecond());
			return "/usersecurity/createAccount";
		}

		return "/home";
	}

}
