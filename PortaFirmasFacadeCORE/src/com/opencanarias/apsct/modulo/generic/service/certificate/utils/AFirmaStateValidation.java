package com.opencanarias.apsct.modulo.generic.service.certificate.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 
 *
 * <b>Package: </b> org.puertosdetenerife.gestor.expedientes.services.certification.afirma<br>
 * <b>Class: </b> AFirmaStateValidation<br>
 *
 * Contiene el resultado de la validación del estado de un certificado
 *<br>
 * ----------------------------------------------<br>
 
 
 */
public class AFirmaStateValidation {
	
	protected String state = null;
	protected String description = null;
	protected List<AFirmaVerificationMethodError> methods = null;

	public AFirmaStateValidation () {
		methods = new ArrayList<AFirmaVerificationMethodError>();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<AFirmaVerificationMethodError> getMethods() {
		return methods;
	}

	public void setMethods(List<AFirmaVerificationMethodError> methods) {
		this.methods = methods;
	}
		
	public void addMethod (AFirmaVerificationMethodError method) {
		methods.add(method);
	}
	
	public String  toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("\n\tAFirmaValidacionEstado");
		sb.append("\n\t======================");
		sb.append("\n\tstate: ").append(state);
		sb.append("\n\tdescription: ").append(description);
		sb.append("\n\tmethods: ");
	        Iterator<AFirmaVerificationMethodError> iterator = methods.iterator();
	        while(iterator.hasNext()) {
	        	sb.append("\n\t\t" + iterator.next().toString());
	        }
		
		return sb.toString();
	}




	
}
