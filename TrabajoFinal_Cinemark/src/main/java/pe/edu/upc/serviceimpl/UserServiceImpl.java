package pe.edu.upc.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Users;
import pe.edu.upc.repository.UserRepository;
import pe.edu.upc.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserRepository uR;
	
	@Transactional
	@Override
	public void insRol(String rol, Long user_id) {
		
		uR.insRol(rol, user_id);
	}

	@Transactional
	@Override
	public Integer insert(Users user) {
		int rpta = uR.buscarUsername(user.getUsername());
		if (rpta == 0) {
			uR.save(user);
		}
		return rpta;
	}

	@Override
	public Users BuscarPorNombre(String username) {
		return uR.buscarNombre(username);
	}

}
