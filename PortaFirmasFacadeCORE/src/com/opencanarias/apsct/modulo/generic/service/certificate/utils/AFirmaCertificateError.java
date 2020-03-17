package com.opencanarias.apsct.modulo.generic.service.certificate.utils;


/**
 * 
 *
 * <b>Package: </b> org.puertosdetenerife.gestor.expedientes.services.certification.afirma<br>
 * <b>Class: </b> AFirmaCertificateError<br>
 *
 * Contiene un error en el resultado de la validación de un certificado
 *<br>
 * ----------------------------------------------<br>
 
 
 */
public class AFirmaCertificateError {
	
	protected String certificateId = null;
	protected AFirmaSimpleValidation simpleValidation = null;
	protected AFirmaStateValidation stateValidation = null;
	
	
	public String getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}

	public AFirmaSimpleValidation getSimpleValidation() {
		return simpleValidation;
	}

	public void setSimpleValidation(AFirmaSimpleValidation simpleValidation) {
		this.simpleValidation = simpleValidation;
	}

	public AFirmaStateValidation getStateValidation() {
		return stateValidation;
	}

	public void setStateValidation(AFirmaStateValidation stateValidation) {
		this.stateValidation = stateValidation;
	}

	
	
	public String  toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("\nAFirmaErrorCertificado");
		sb.append("\n======================");
		sb.append("\nidCertificado: ").append(certificateId);
		sb.append("\nsimpleValidation: ").append(simpleValidation);
		sb.append("\nstateValidation: ").append(stateValidation);
		
		return sb.toString();
	}

}
