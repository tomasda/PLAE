package es.apt.ae.facade.dto;

import java.io.Serializable;


public class DocumentoInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String nombre = null;
	private String descripcion = null;
	private String asunto = null;
	
	public DocumentoInfo() {
		super();
	}
	
	public DocumentoInfo(String nombre, String descripcion, String asunto) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.asunto = asunto;
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

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

}
