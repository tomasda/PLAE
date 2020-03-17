package com.opencanarias.apsct.modulo.generic.service.certificate.utils;

/**
 * 
 *
 * <b>Package: </b> org.puertosdetenerife.gestor.expedientes.services.certification.afirma<br>
 * <b>Class: </b> AFirmaSimpleValidation<br>
 *
 * Contiene el resultado de la validación simple de un certificado
 *<br>
 * ----------------------------------------------<br>
 
 
 */
public class AFirmaSimpleValidation {

	protected String code = null;
	protected String result  = null;
	protected String exception = null;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getException() {
		return exception;
	}
	
	public void setException(String exception) {
		this.exception = exception;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String  toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("\n\tAFirmaSimpleValidation");
		sb.append("\n\t======================");
		sb.append("\n\tcode: ").append(code);
		sb.append("\n\tresult: ").append(result);
		sb.append("\n\texception: ").append(exception);
		
		return sb.toString();
	}
}
