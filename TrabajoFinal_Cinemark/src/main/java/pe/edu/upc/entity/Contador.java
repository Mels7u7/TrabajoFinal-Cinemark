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
@Table(name = "contador")
public class Contador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idContador;

	@NotEmpty(message = "Ingresa el nombre del contador")
	@Column(name = "nombreContador", nullable = false, length = 30)
	private String nombreContador;

	@Size(min = 8, max = 8,message="El DNI tiene que ser de 8 digitos")
	@NotEmpty(message = "Ingresar DNI")
	@Column(name = "dniContador", nullable = false, length = 9, unique = true)
	private String dniContador;

	@NotEmpty(message = "Ingresar institucion")
	@Column(name = "institucionContador", nullable = false, length = 30)
	private String institucionContador;
	
	@Pattern(regexp="[\\d]{9}", message="El número de celular tiene que ser de 9 digitos y no se puede ingresar letras")
	@NotEmpty(message = "Ingresar número del contador")
	@Column(name = "numeroContactoContador", nullable = false,length=10)
	private String numeroContactoContador;

	public Contador(int idContador, String nombreContador, String dniContador, String institucionContador,
			String numeroContactoContador) {
		super();
		this.idContador = idContador;
		this.nombreContador = nombreContador;
		this.dniContador = dniContador;
		this.institucionContador = institucionContador;
		this.numeroContactoContador = numeroContactoContador;
	}

	public Contador() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdContador() {
		return idContador;
	}

	public void setIdContador(int idContador) {
		this.idContador = idContador;
	}

	public String getNombreContador() {
		return nombreContador;
	}

	public void setNombreContador(String nombreContador) {
		this.nombreContador = nombreContador;
	}

	public String getDniContador() {
		return dniContador;
	}

	public void setDniContador(String dniContador) {
		this.dniContador = dniContador;
	}

	public String getInstitucionContador() {
		return institucionContador;
	}

	public void setInstitucionContador(String institucionContador) {
		this.institucionContador = institucionContador;
	}

	public String getNumeroContactoContador() {
		return numeroContactoContador;
	}

	public void setNumeroContactoContador(String numeroContactoContador) {
		this.numeroContactoContador = numeroContactoContador;
	}

}
