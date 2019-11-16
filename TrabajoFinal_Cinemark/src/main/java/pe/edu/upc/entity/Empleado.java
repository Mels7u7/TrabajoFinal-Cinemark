package pe.edu.upc.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "empleado")
public class Empleado implements Serializable {

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

	@Min(1)
	@Max(25)
	@Column(name = "experienciaEmpleado", nullable = false)
	private int experienciaEmpleado;

	@NotEmpty(message = "Ingresa la sede")
	@Column(name = "sedeEmpleado", nullable = false, length = 30)
	private String sedeEmpleado;

	@Size(min = 8, max = 8, message = "El DNI tiene que ser de 8 digitos")
	@NotEmpty(message = "Ingresar DNI")
	@Column(name = "dniEmpleado", nullable = false, length = 10, unique = true)
	private String dniEmpleado;

	@NotEmpty(message = "Ingresa el titulo del empleado")
	@Column(name = "tituloEmpleado", nullable = false, length = 30)
	private String tituloEmpleado;

	@Email(message = "Ingresa un email con el formato correcto.")
	@NotEmpty(message = "Ingresa el correo")
	@Column(name = "correoEmpleado", nullable = false, length = 40)
	private String correoEmpleado;

	@Pattern(regexp = "[\\d]{9}", message = "El n\u00FAmero de celular tiene que ser de 9 digitos y no puede ingresar letras")
	@NotEmpty(message = "Ingresa el celular del empleado")
	@Column(name = "celularEmpleado", nullable = false, length = 10)
	private String celularEmpleado;

	@NotEmpty(message = "Ingresar el puesto del empleado")
	@Column(name = "puestoEmpleado", nullable = false, length = 20)
	private String puestoEmpleado;

	public Empleado(int idEmpleado, String nombreEmpleado, String apellidoEmpleado, int experienciaEmpleado,
			String sedeEmpleado, String dniEmpleado, String tituloEmpleado, String correoEmpleado,
			String celularEmpleado, String puestoEmpleado) {
		this.idEmpleado = idEmpleado;
		this.nombreEmpleado = nombreEmpleado;
		this.apellidoEmpleado = apellidoEmpleado;
		this.experienciaEmpleado = experienciaEmpleado;
		this.sedeEmpleado = sedeEmpleado;
		this.dniEmpleado = dniEmpleado;
		this.tituloEmpleado = tituloEmpleado;
		this.correoEmpleado = correoEmpleado;
		this.celularEmpleado = celularEmpleado;
		this.puestoEmpleado = puestoEmpleado;
	}

	public Empleado() {
		super();
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

	public int getExperienciaEmpleado() {
		return experienciaEmpleado;
	}

	public void setExperienciaEmpleado(int experienciaEmpleado) {
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

	public String getPuestoEmpleado() {
		return puestoEmpleado;
	}

	public void setPuestoEmpleado(String puestoEmpleado) {
		this.puestoEmpleado = puestoEmpleado;
	}

}
