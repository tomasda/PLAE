package com.opencanarias.apsct.modulo.generic.service.certificate.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 
 *
 * <b>Package: </b> org.puertosdetenerife.gestor.expedientes.services.certification.afirma<br>
 * <b>Class: </b> AFirmaChainValidation<br>
 *
 * Contiene el resultado de la validación de la cadena de confianza de un certificado
 *<br>
 * ----------------------------------------------<br>
 
 
 */
public class AFirmaChainValidation {
	
	protected String code = null;
	protected String description = null;
	protected List<AFirmaCertificateError> errors = null;

	public AFirmaChainValidation () {
		errors = new ArrayList<AFirmaCertificateError>();
	}
	 
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<AFirmaCertificateError> getErrors() {
		return errors;
	}

	public void setErrors(List<AFirmaCertificateError> errors) {
		this.errors = errors;
	}

	public void addError (AFirmaCertificateError error) {
		this.errors.add(error);
	}
	
	public String  toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("\n\tAFirmaValidacionCadena");
		sb.append("\n\t======================");
		sb.append("\n\todigoResultado: ").append(code);
		sb.append("\n\tdescResultado: ").append(description);
		sb.append("\n\terrors: ");
	        Iterator<AFirmaCertificateError> iterator = errors.iterator();
	         while(iterator.hasNext()) {
	        	sb.append("\n\t\t" + iterator.next().toString());
	        }
		
		return sb.toString();
	}



	
}
