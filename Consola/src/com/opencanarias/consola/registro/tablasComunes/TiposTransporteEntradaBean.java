package com.opencanarias.consola.registro.tablasComunes;

public class TiposTransporteEntradaBean {

	String id;
	String descripcion;
	
	public TiposTransporteEntradaBean(String ID_, String DESCRIPCION_){
		id = ID_;
		descripcion = DESCRIPCION_;
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
