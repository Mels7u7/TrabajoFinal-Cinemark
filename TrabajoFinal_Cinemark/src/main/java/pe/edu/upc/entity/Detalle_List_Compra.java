package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "detalle")
public class Detalle_List_Compra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDetalle;

	@ManyToOne
	@JoinColumn(name = "idRecurso")
	@NotNull
	private Recurso recursoDetalle;

	@ManyToOne
	@JoinColumn(name = "idLista")
	private Lista_Compra listaDetalle;

	@Min(2)
	@Max(10000)
	@Column(name = "precioDetalle", nullable = false)
	private int precioDetalle;

	@Min(1)
	@Max(1000)
	@Column(name = "unidadesDetalle", nullable = false)
	private int unidadesDetalle;

	@Transient
	private double importe;

	public Detalle_List_Compra(int idDetalle, Recurso recursoDetalle, Lista_Compra listaDetalle, int precioDetalle,
			int unidadesDetalle) {
		super();
		this.idDetalle = idDetalle;
		this.recursoDetalle = recursoDetalle;
		this.listaDetalle = listaDetalle;
		this.precioDetalle = precioDetalle;
		this.unidadesDetalle = unidadesDetalle;
		this.importe = precioDetalle * unidadesDetalle;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public Detalle_List_Compra() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(int idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Recurso getRecursoDetalle() {
		return recursoDetalle;
	}

	public void setRecursoDetalle(Recurso recursoDetalle) {
		this.recursoDetalle = recursoDetalle;
	}

	public Lista_Compra getListaDetalle() {
		return listaDetalle;
	}

	public void setListaDetalle(Lista_Compra listaDetalle) {
		this.listaDetalle = listaDetalle;
	}

	public int getPrecioDetalle() {
		return precioDetalle;
	}

	public void setPrecioDetalle(int precioDetalle) {
		this.precioDetalle = precioDetalle;
	}

	public int getUnidadesDetalle() {
		return unidadesDetalle;
	}

	public void setUnidadesDetalle(int unidadesDetalle) {
		this.unidadesDetalle = unidadesDetalle;
	}

}
