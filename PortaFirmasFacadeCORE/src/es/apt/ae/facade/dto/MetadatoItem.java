package es.apt.ae.facade.dto;

import java.io.Serializable;
import java.util.List;

public class MetadatoItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre = null;
	private String descripcion = null;
	private Object valor = null;
	private String componente = null;
	private int longitud = -1;
	private boolean editable = false;
	private boolean obligatorio = false;
	private List<ElementoItem> posiblesValores;
	

	public MetadatoItem() {
		super();
	}
	public MetadatoItem(String nombre, String descripcion, Object valor, 
			String componente, boolean editable, boolean obligatorio) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.valor = valor;
		this.componente = componente;
		this.editable = editable;
		this.obligatorio = obligatorio;
	}	
	public MetadatoItem(String nombre, String descripcion, Object valor, 
			String componente, int longitud, boolean editable, boolean obligatorio) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.valor = valor;
		this.componente = componente;
		this.longitud = longitud;
		this.editable = editable;
		this.obligatorio = obligatorio;
	}
	public MetadatoItem(String nombre, String descripcion, Object valor,
			String componente, boolean editable, boolean obligatorio, 
			List<ElementoItem> posiblesValores) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.valor = valor;
		this.componente = componente;
		this.editable = editable;
		this.obligatorio = obligatorio;
		this.posiblesValores = posiblesValores;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Object getValor() {
		return valor;
	}
	public void setValor(Object valor) {
		this.valor = valor;
	}
	public String getComponente() {
		return componente;
	}
	public void setComponente(String componente) {
		this.componente = componente;
	}
	public int getLongitud() {
		return longitud;
	}
	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isObligatorio() {
		return obligatorio;
	}
	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}
	public List<ElementoItem> getPosiblesValores() {
		return posiblesValores;
	}
	public void setPosiblesValores(List<ElementoItem> posiblesValores) {
		this.posiblesValores = posiblesValores;
	}
	

}
