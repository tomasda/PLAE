package es.apt.ae.facade.portafirmas.dto;

import java.io.Serializable;

public class RespuestaVisualizacionDocumento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7784313886285505562L;
	private String nombre;
	private byte[] contenido;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public byte[] getContenido() {
		return contenido;
	}
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}

}
