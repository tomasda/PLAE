package com.opencanarias.apsct.modulo.generic.service.certificate.utils;

import java.util.Date;


/**
 * 
 *
 * <b>Package: </b> org.puertosdetenerife.gestor.expedientes.services.certification.afirma<br>
 * <b>Class: </b> AFirmaVerificationMethodError<br>
 *
 * Contiene la información sobre un método de verificación.
 *<br>
 * ----------------------------------------------<br>
 
 
 */
public class AFirmaVerificationMethodError {
	
	protected String state = null;
	protected String description = null;
	protected Date lastUpdateDate = null;
	protected Date revocationDate = null;
	protected String reason = null;
	protected String tokenOCSP  = null;
	protected String exception = null;
	protected AFirmaMethod method = null;
	
	public AFirmaVerificationMethodError () {
		method = new AFirmaMethod();
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

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getRevocationDate() {
		return revocationDate;
	}

	public void setRevocationDate(Date revocationDate) {
		this.revocationDate = revocationDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTokenOCSP() {
		return tokenOCSP;
	}

	public void setTokenOCSP(String tokenOCSP) {
		this.tokenOCSP = tokenOCSP;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public AFirmaMethod getMethod() {
		return method;
	}

	public void setMethod(AFirmaMethod method) {
		this.method = method;
	}
	
	
	public String  toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("\n\t\tAFirmaVerificationMethodError");
		sb.append("\n\t\t============================");
		sb.append("\n\t\tstate: ").append(state);
		sb.append("\n\t\tdescription: ").append(description);
		sb.append("\n\t\tlastUpdateDate: ").append(lastUpdateDate);
		sb.append("\n\t\trevocationDate: ").append(revocationDate);
		sb.append("\n\t\treason: ").append(reason);
		sb.append("\n\t\ttokenOCSP: ").append(tokenOCSP);
		sb.append("\n\t\texception: ").append(exception);
		sb.append("\n\t\tmethod: ").append(method.toString());
		
		return sb.toString();
	}
}
