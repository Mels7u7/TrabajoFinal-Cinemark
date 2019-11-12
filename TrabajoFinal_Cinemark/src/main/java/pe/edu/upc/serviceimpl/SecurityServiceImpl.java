package pe.edu.upc.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Users;
import pe.edu.upc.repository.UserRepository;
import pe.edu.upc.service.ISecurityService;

@Service
public class SecurityServiceImpl implements ISecurityService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Pair<Boolean, String> createUser(String username, String password) {

		Pair<Boolean, String> tuple = Pair.of(true, "");

		if (userRepository.buscarUsername(username) > 0) {
			tuple = Pair.of(true, "Ya existe un usuario con ese username.");
		} else {
			String bcryptPassword = passwordEncoder.encode(password);
			Users user = new Users();
			user.setEnabled(true);
			user.setUsername(username);
			user.setPassword(bcryptPassword);
			userRepository.save(user);

			user = userRepository.findByUsername(username);

			try {
				userRepository.insRol("ROLE_USER", user.getId());
			} catch (Exception ex) {

			}

			tuple = Pair.of(false, "Se registro correctamente.");
		}

		return tuple;
	}

}
