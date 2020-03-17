package es.apt.ae.facade.dto;

import java.io.Serializable;

public class NodoInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String uri = null;
	private String ruta = null;

	
	public NodoInfo() {
		super();
	}
	
	public NodoInfo(String uri, String ruta) {
		super();
		this.uri = uri;
		this.ruta = ruta;
	}
	
	

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
}
