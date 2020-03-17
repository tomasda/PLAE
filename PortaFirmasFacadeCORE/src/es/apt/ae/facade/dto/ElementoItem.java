package es.apt.ae.facade.dto;

import java.io.Serializable;

public class ElementoItem  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected String id = null;
	protected String descripcion = null;
	
	
	public ElementoItem() {
		super();
	}
	public ElementoItem(String id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	

}
