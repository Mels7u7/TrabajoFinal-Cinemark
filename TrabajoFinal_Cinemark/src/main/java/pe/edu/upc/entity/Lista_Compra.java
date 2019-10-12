package pe.edu.upc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "listaCompra")
public class Lista_Compra implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idLista;
	
	@NotEmpty(message = "Ingresa la nota de la lista de compra")
	@Column(name = "notaLista", nullable = false, length = 70)
	private String notaLista;
	
	@ManyToOne
	@JoinColumn(name = "idProveedor")
	private Proveedor proveedorLista;
	
	@NotEmpty(message = "Ingresa el estado de la lista de compra")
	@Column(name = "estadoLista", nullable = false, length = 20)
	private String estadoLista;
	
	@Column(name = "precioLista", nullable = false, length = 10)
	private boolean precioLista;
	
	@NotNull(message = "La fecha es obligatoria")
	@Past(message = "La fecha debe estar en el pasado")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaLista")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaLista;

	public Lista_Compra(int idLista, @NotEmpty(message = "Ingresa la nota de la lista de compra") String notaLista,
			Proveedor proveedorLista, @NotEmpty(message = "Ingresa el estado de la lista de compra") String estadoLista,
			boolean precioLista,
			@NotNull(message = "La fecha es obligatoria") @Past(message = "La fecha debe estar en el pasado") Date fechaLista) {
		super();
		this.idLista = idLista;
		this.notaLista = notaLista;
		this.proveedorLista = proveedorLista;
		this.estadoLista = estadoLista;
		this.precioLista = precioLista;
		this.fechaLista = fechaLista;
	}

	public Lista_Compra() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdLista() {
		return idLista;
	}

	public void setIdLista(int idLista) {
		this.idLista = idLista;
	}

	public String getNotaLista() {
		return notaLista;
	}

	public void setNotaLista(String notaLista) {
		this.notaLista = notaLista;
	}

	public Proveedor getProveedorLista() {
		return proveedorLista;
	}

	public void setProveedorLista(Proveedor proveedorLista) {
		this.proveedorLista = proveedorLista;
	}

	public String getEstadoLista() {
		return estadoLista;
	}

	public void setEstadoLista(String estadoLista) {
		this.estadoLista = estadoLista;
	}

	public boolean isPrecioLista() {
		return precioLista;
	}

	public void setPrecioLista(boolean precioLista) {
		this.precioLista = precioLista;
	}

	public Date getFechaLista() {
		return fechaLista;
	}

	public void setFechaLista(Date fechaLista) {
		this.fechaLista = fechaLista;
	}
	
	

}
