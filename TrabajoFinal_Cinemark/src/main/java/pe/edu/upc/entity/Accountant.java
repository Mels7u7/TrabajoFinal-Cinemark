package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "accountants")
public class Accountant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountantId;

	@NotEmpty(message = "Ingresa el nombre del contador")
	@Column(name = "name", nullable = false, length = 30)
	private String name;

	@Size(min = 8, max = 8, message = "El DNI tiene que ser de 8 d\u00edgitos")
	@NotEmpty(message = "Ingresar DNI")
	@Column(name = "documentNumber", nullable = false, length = 9, unique = true)
	private String documentNumber;

	@NotEmpty(message = "Ingresar instituci\u00f3n")
	@Column(name = "accountingInstitution", nullable = false, length = 30)
	private String accountingInstitution;

	@Pattern(regexp = "[\\d]{9}", message = "El n\u00famero de celular tiene que ser de 9 d\u00edgitos y no se puede ingresar letras")
	@NotEmpty(message = "Ingresar n\u00famero del contador")
	@Column(name = "contactNumber", nullable = false, length = 10)
	private String contactNumber;

	public Accountant(int accountantId, String name, String documentNumber, String accountingInstitution,
			String contactNumber) {
		super();
		this.accountantId = accountantId;
		this.name = name;
		this.documentNumber = documentNumber;
		this.accountingInstitution = accountingInstitution;
		this.contactNumber = contactNumber;
	}

	public Accountant() {
		super();
	}

	public int getAccountantId() {
		return accountantId;
	}

	public void setAccountantId(int accountantId) {
		this.accountantId = accountantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getAccountingInstitution() {
		return accountingInstitution;
	}

	public void setAccountingInstitution(String accountingInstitution) {
		this.accountingInstitution = accountingInstitution;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

}
