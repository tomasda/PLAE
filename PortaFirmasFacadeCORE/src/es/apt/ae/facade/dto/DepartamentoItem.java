package es.apt.ae.facade.dto;

import java.io.Serializable;

public class DepartamentoItem implements Serializable, Comparable<DepartamentoItem> {
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String descripcion;
	private String grupoAlfresco;
	private Boolean decretable;
	private Boolean habilitado;

	
	public DepartamentoItem() {
		super();
	}
	public DepartamentoItem(String id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}
	public DepartamentoItem(String id, String descripcion, String grupoAlfresco) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.grupoAlfresco = grupoAlfresco;
	}
	public DepartamentoItem(String id, String descripcion, String grupoAlfresco, 
			Boolean decretable, Boolean habilitado) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.grupoAlfresco = grupoAlfresco;
		this.decretable = decretable;
		this.habilitado = habilitado;
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
	public String getGrupoAlfresco() {
		return grupoAlfresco;
	}
	public void setGrupoAlfresco(String grupoAlfresco) {
		this.grupoAlfresco = grupoAlfresco;
	}
	public Boolean getDecretable() {
		return decretable;
	}
	public void setDecretable(Boolean decretable) {
		this.decretable = decretable;
	}
	public Boolean getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}
	public int compareTo(DepartamentoItem o) {
		return this.descripcion.compareTo(o.getDescripcion());
	}
	
}
