package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "proveedor")
public class Proveedor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProveedor;

	@NotEmpty(message = "Ingresa el nombre del proveedor")
	@Column(name = "nombreProveedor", nullable = false, length = 30)
	private String nombreProveedor;

	@NotEmpty(message = "Ingresa la direccion del proveedor")
	@Column(name = "direccionProveedor", nullable = false, length = 50)
	private String direccionProveedor;

	@Size(min = 11, max = 11)
	@NotEmpty(message = "Ingresar RUC")
	@Column(name = "rucProveedor", nullable = false, length = 45, unique = true)
	private String rucProveedor;

	@NotEmpty(message = "Ingresa el nombre de contacto del proveedor")
	@Column(name = "nombrecontactoProveedor", nullable = false, length = 70)
	private String nombrecontactoProveedor;

	@Min(9)
	@Max(9)
	@Column(name = "numerocontactoProveedor", nullable = false)
	private int numerocontactoProveedor;

	public Proveedor(int idProveedor, String nombreProveedor, String direccionProveedor, String rucProveedor,
			String nombrecontactoProveedor, int numerocontactoProveedor) {
		super();
		this.idProveedor = idProveedor;
		this.nombreProveedor = nombreProveedor;
		this.direccionProveedor = direccionProveedor;
		this.rucProveedor = rucProveedor;
		this.nombrecontactoProveedor = nombrecontactoProveedor;
		this.numerocontactoProveedor = numerocontactoProveedor;
	}

	public Proveedor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	public String getDireccionProveedor() {
		return direccionProveedor;
	}

	public void setDireccionProveedor(String direccionProveedor) {
		this.direccionProveedor = direccionProveedor;
	}

	public String getRucProveedor() {
		return rucProveedor;
	}

	public void setRucProveedor(String rucProveedor) {
		this.rucProveedor = rucProveedor;
	}

	public String getNombrecontactoProveedor() {
		return nombrecontactoProveedor;
	}

	public void setNombrecontactoProveedor(String nombrecontactoProveedor) {
		this.nombrecontactoProveedor = nombrecontactoProveedor;
	}

	public int getNumerocontactoProveedor() {
		return numerocontactoProveedor;
	}

	public void setNumerocontactoProveedor(int numerocontactoProveedor) {
		this.numerocontactoProveedor = numerocontactoProveedor;
	}

}
