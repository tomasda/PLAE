package com.opencanarias.exceptions;

public class ExceptionBean {

	private String codigo;
	private String nombre;
	private String mensaje;
	
	
	public ExceptionBean(String codigo, String nombre, String mensaje) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.mensaje = mensaje;
	}

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}	
}
