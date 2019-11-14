package pe.edu.upc.service;

import org.springframework.data.util.Pair;

public interface ISecurityService {

	public Pair<Boolean, String> createUser(String username, String password, String email, String name, String lastName,String role);

}
