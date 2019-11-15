package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Accountant;

@Repository
public interface AccountantRepository extends JpaRepository<Accountant, Integer> {
	@Query("from Accountant c where c.name like %:name%")
	List<Accountant> findByNombreContador(@Param("name") String name);

	@Query("select count(c.documentNumber) from Accountant c where c.documentNumber =:documentNumber")
	public int buscarDNIContador(@Param("documentNumber") String documentNumber);

	@Modifying(clearAutomatically = true)
	@Query("update Accountant set name = :name ,accountingInstitution = :accountingInstitution, contactNumber = :contactNumber where accountantId=:accountantId")
	public void actualizar(@Param("name") String name, @Param("accountingInstitution") String accountingInstitution,
			@Param("contactNumber") String contactNumber, @Param("accountantId") int accountantId);
}
