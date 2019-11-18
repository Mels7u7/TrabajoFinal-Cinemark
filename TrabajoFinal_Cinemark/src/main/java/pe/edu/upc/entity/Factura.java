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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "factura")
public class Factura implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idFactura;

	@ManyToOne
	@JoinColumn(name = "idLista")
	private Lista_Compra listaFactura;

	@ManyToOne
	@JoinColumn(name = "accountantId")
	private Accountant contadorFactura;

	@NotEmpty(message = "Ingresa el an\u00e1lisis de la factura")
	@Column(name = "analisisFactura", nullable = false, length = 60)
	private String analisisFactura;

	@NotNull(message = "La fecha es obligatoria")
	@Past(message = "La fecha debe estar en el pasado")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaFactura")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFactura;
	
	@Min(0)
	@Max(4000)
	@Column(name = "precio", nullable = false)
	private float precio;

	public Factura(int idFactura, Lista_Compra listaFactura, Accountant contadorFactura, String analisisFactura,
			Date fechaFactura, float precio) {
		super();
		this.idFactura = idFactura;
		this.listaFactura = listaFactura;
		this.contadorFactura = contadorFactura;
		this.analisisFactura = analisisFactura;
		this.fechaFactura = fechaFactura;
		this.precio = precio;
		
	}

	public Factura() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public Lista_Compra getListaFactura() {
		return listaFactura;
	}

	public void setListaFactura(Lista_Compra listaFactura) {
		this.listaFactura = listaFactura;
	}

	public Accountant getContadorFactura() {
		return contadorFactura;
	}

	public void setContadorFactura(Accountant contadorFactura) {
		this.contadorFactura = contadorFactura;
	}

	public String getAnalisisFactura() {
		return analisisFactura;
	}

	public void setAnalisisFactura(String analisisFactura) {
		this.analisisFactura = analisisFactura;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

}
