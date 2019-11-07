package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "empleadoXLC")
public class EmpleadoxLC implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEmpleadoXLC;

	@ManyToOne
	@JoinColumn(name = "idLista")
	private Lista_Compra listaEmpleadoLC;

	@ManyToOne
	@JoinColumn(name = "idEmpleado")
	private Empleado empleadoEmpleadoLC;

	public EmpleadoxLC(int idEmpleadoXLC, Lista_Compra listaEmpleadoLC, Empleado empleadoEmpleadoLC) {
		super();
		this.idEmpleadoXLC = idEmpleadoXLC;
		this.listaEmpleadoLC = listaEmpleadoLC;
		this.empleadoEmpleadoLC = empleadoEmpleadoLC;
	}

	public EmpleadoxLC() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdEmpleadoXLC() {
		return idEmpleadoXLC;
	}

	public void setIdEmpleadoXLC(int idEmpleadoXLC) {
		this.idEmpleadoXLC = idEmpleadoXLC;
	}

	public Lista_Compra getListaEmpleadoLC() {
		return listaEmpleadoLC;
	}

	public void setListaEmpleadoLC(Lista_Compra listaEmpleadoLC) {
		this.listaEmpleadoLC = listaEmpleadoLC;
	}

	public Empleado getEmpleadoEmpleadoLC() {
		return empleadoEmpleadoLC;
	}

	public void setEmpleadoEmpleadoLC(Empleado empleadoEmpleadoLC) {
		this.empleadoEmpleadoLC = empleadoEmpleadoLC;
	}

}
