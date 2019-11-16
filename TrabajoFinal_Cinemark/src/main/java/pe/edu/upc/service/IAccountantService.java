package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Accountant;

public interface IAccountantService {
	public Integer insert(Accountant accountant);

	public void modify(Accountant accountant);

	public void delete(int accountantId);

	Optional<Accountant> listId(int accountantId);

	List<Accountant> list();

	List<Accountant> findByName(String name);
}
