package pe.edu.upc.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Users;
import pe.edu.upc.repository.UserRepository;
import pe.edu.upc.service.ISecurityService;
import pe.edu.upc.utils.Email;

@Service
public class SecurityServiceImpl implements ISecurityService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Email emailService;

	@Override
	public Pair<Boolean, String> createUser(String username, String password, String email, String name,
			String lastName, String role) {

		Pair<Boolean, String> tuple = Pair.of(true, "");

		if (userRepository.buscarUsername(username) > 0) {
			tuple = Pair.of(true, "Ya existe un usuario con ese username.");
		} else {
			String bcryptPassword = passwordEncoder.encode(password);
			Users user = new Users();
			user.setEnabled(true);
			user.setUsername(username);
			user.setPassword(bcryptPassword);
			user.setName(name);
			user.setLastName(lastName);
			user.setEmail(email);
			userRepository.save(user);

			String nombreCompleto = name.trim() + " " + lastName.trim();

			String mensajeCorreo = "<img src=\"https://cinemarkla.modyocdn.com/uploads/a8852d98-cd8b-4029-a316-dbb44b8632f2/original/cinemark-logo.png\" alt=\"Cinemark\"></br>";
			mensajeCorreo += "<h2>Estimado(a) " + nombreCompleto + " te has registrado satisfactoriamente</h2>";
			mensajeCorreo += "</br></br><h3>Tu usuario es: " + username + "</h3>";
			try {
				emailService.sendEmail("Bienvenido", mensajeCorreo, email);
			} catch (Exception ex) {

			}
			try {
				userRepository.insRol(role, user.getId());
			} catch (Exception ex) {

			}

			user = userRepository.findByUsername(username);

			tuple = Pair.of(false, "Se registro correctamente.");
		}

		return tuple;
	}

}
