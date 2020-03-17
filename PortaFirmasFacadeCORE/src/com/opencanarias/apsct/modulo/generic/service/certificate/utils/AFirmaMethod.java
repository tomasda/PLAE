package com.opencanarias.apsct.modulo.generic.service.certificate.utils;

/**
 * 
 *
 * <b>Package: </b> org.puertosdetenerife.gestor.expedientes.services.certification.afirma<br>
 * <b>Class: </b> AFirmaMethod<br>
 *
 * Contiene la información detallada sobre un método de verificación.
 *<br>
 * ----------------------------------------------<br>
 
 
 */
public class AFirmaMethod {
	
	protected String url = null;
	protected String protocol = null;
	
	public String getProtocol() {
		return protocol;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("\n\t\t\tAFirmaMethod");
		sb.append("\n\t\t\t============");
		sb.append("\n\t\t\turl: ").append(url);
		sb.append("\n\t\t\tprotocol: ").append(protocol);
		
		return sb.toString();
	}
	
}
