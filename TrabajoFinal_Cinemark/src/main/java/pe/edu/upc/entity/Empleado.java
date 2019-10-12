package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "empleado")
public class Empleado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEmpleado;
	
	@NotEmpty(message = "Ingresa el nombre del empleado")
	@Column(name = "nombreEmpleado", nullable = false, length = 30)
	private String nombreEmpleado;
	
	@NotEmpty(message = "Ingresa el apellido del empleado")
	@Column(name = "apellidoEmpleado", nullable = false, length = 30)
	private String apellidoEmpleado;
	
	@Size(min = 1, max = 2)
	@NotEmpty(message = "Ingresar experiencia")
	@Column(name = "experienciaEmpleado", nullable = false, length = 3)
	private String experienciaEmpleado;

	@NotEmpty(message = "Ingresa la sede")
	@Column(name = "sedeEmpleado", nullable = false, length = 30)
	private String sedeEmpleado;
	
	@Size(min = 8, max = 8)
	@NotEmpty(message = "Ingresar DNI")
	@Column(name = "dniEmpleado", nullable = false, length = 10, unique = true)
	private String dniEmpleado;
	
	@NotEmpty(message = "Ingresa el titulo del empleado")
	@Column(name = "tituloEmpleado", nullable = false, length = 30)
	private String tituloEmpleado;
	
	@Email
	@NotEmpty(message = "Ingresa el correo")
	@Column(name = "correoEmpleado", nullable = false, length = 40)
	private String correoEmpleado;
	
	@Size(min = 9, max = 9)
	@NotEmpty(message = "Ingresar celular de contacto")
	@Column(name = "celularEmpleado", nullable = false, length = 10)
	private String celularEmpleado;

	public Empleado(int idEmpleado, @NotEmpty(message = "Ingresa el nombre del empleado") String nombreEmpleado,
			@NotEmpty(message = "Ingresa el apellido del empleado") String apellidoEmpleado,
			@Size(min = 1, max = 2) @NotEmpty(message = "Ingresar experiencia") String experienciaEmpleado,
			@NotEmpty(message = "Ingresa la sede") String sedeEmpleado,
			@Size(min = 8, max = 8) @NotEmpty(message = "Ingresar DNI") String dniEmpleado,
			@NotEmpty(message = "Ingresa el titulo del empleado") String tituloEmpleado,
			@Email @NotEmpty(message = "Ingresa el correo") String correoEmpleado,
			@Size(min = 9, max = 9) @NotEmpty(message = "Ingresar celular de contacto") String celularEmpleado) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombreEmpleado = nombreEmpleado;
		this.apellidoEmpleado = apellidoEmpleado;
		this.experienciaEmpleado = experienciaEmpleado;
		this.sedeEmpleado = sedeEmpleado;
		this.dniEmpleado = dniEmpleado;
		this.tituloEmpleado = tituloEmpleado;
		this.correoEmpleado = correoEmpleado;
		this.celularEmpleado = celularEmpleado;
	}

	public Empleado() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public String getApellidoEmpleado() {
		return apellidoEmpleado;
	}

	public void setApellidoEmpleado(String apellidoEmpleado) {
		this.apellidoEmpleado = apellidoEmpleado;
	}

	public String getExperienciaEmpleado() {
		return experienciaEmpleado;
	}

	public void setExperienciaEmpleado(String experienciaEmpleado) {
		this.experienciaEmpleado = experienciaEmpleado;
	}

	public String getSedeEmpleado() {
		return sedeEmpleado;
	}

	public void setSedeEmpleado(String sedeEmpleado) {
		this.sedeEmpleado = sedeEmpleado;
	}

	public String getDniEmpleado() {
		return dniEmpleado;
	}

	public void setDniEmpleado(String dniEmpleado) {
		this.dniEmpleado = dniEmpleado;
	}

	public String getTituloEmpleado() {
		return tituloEmpleado;
	}

	public void setTituloEmpleado(String tituloEmpleado) {
		this.tituloEmpleado = tituloEmpleado;
	}

	public String getCorreoEmpleado() {
		return correoEmpleado;
	}

	public void setCorreoEmpleado(String correoEmpleado) {
		this.correoEmpleado = correoEmpleado;
	}

	public String getCelularEmpleado() {
		return celularEmpleado;
	}

	public void setCelularEmpleado(String celularEmpleado) {
		this.celularEmpleado = celularEmpleado;
	}
	
	
}
