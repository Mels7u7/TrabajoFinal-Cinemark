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
	@JoinColumn(name = "idContador")
	private Contador contadorFactura;

	@NotEmpty(message = "Ingresa el analisis de la factura")
	@Column(name = "analisisFactura", nullable = false, length = 60)
	private String analisisFactura;

	@NotNull(message = "La fecha es obligatoria")
	@Past(message = "La fecha debe estar en el pasado")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaFactura")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFactura;

	public Factura(int idFactura, Lista_Compra listaFactura, Contador contadorFactura, String analisisFactura,
			Date fechaFactura) {
		super();
		this.idFactura = idFactura;
		this.listaFactura = listaFactura;
		this.contadorFactura = contadorFactura;
		this.analisisFactura = analisisFactura;
		this.fechaFactura = fechaFactura;
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

	public Contador getContadorFactura() {
		return contadorFactura;
	}

	public void setContadorFactura(Contador contadorFactura) {
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

}
