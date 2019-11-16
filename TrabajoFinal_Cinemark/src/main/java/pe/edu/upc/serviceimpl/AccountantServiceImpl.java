package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Accountant;
import pe.edu.upc.repository.AccountantRepository;
import pe.edu.upc.service.IAccountantService;

@Service
public class AccountantServiceImpl implements IAccountantService {

	@Autowired
	private AccountantRepository cR;

	@Override
	@Transactional
	public Integer insert(Accountant accountant) {
		int rpta = cR.findByDocument(accountant.getDocumentNumber());
		if (rpta == 0) {
			cR.save(accountant);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void modify(Accountant accountant) {
		cR.update(accountant.getName(), accountant.getAccountingInstitution(), accountant.getContactNumber(),
				accountant.getAccountantId());
	}

	@Override
	@Transactional
	public void delete(int accountantId) {
		cR.deleteById(accountantId);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Accountant> listId(int accountant) {
		return cR.findById(accountant);
	}

	@Override
	public List<Accountant> list() {
		return cR.findAll();
	}

	@Override
	public List<Accountant> findByName(String name) {
		return cR.findByName(name);
	}

}
