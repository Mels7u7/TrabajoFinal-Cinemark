package pe.edu.upc.service;

import pe.edu.upc.entity.Users;

public interface IUserService {

	public void insRol(String rol, Long user_id);

	public Integer insert(Users user);

	public Users BuscarPorNombre(String username);
}
