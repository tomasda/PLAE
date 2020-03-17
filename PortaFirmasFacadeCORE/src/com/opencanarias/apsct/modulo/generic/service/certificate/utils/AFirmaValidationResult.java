package com.opencanarias.apsct.modulo.generic.service.certificate.utils;

/**
 * 
 *
 * <b>Package: </b> org.puertosdetenerife.gestor.expedientes.services.certification.afirma<br>
 * <b>Class: </b> AFirmaValidationResult<br>
 *
 * Contiene el resultado de la validación de un certificado
 *<br>
 * ----------------------------------------------<br>
   */
public class AFirmaValidationResult {
	
	private boolean result  = false;
	private String description = null;
	private int errorCode = 0;
	private AFirmaSimpleValidation simpleValidation = null;
	private AFirmaChainValidation chainValidation = null;
	private AFirmaStateValidation stateValidation = null;
	
	
	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AFirmaSimpleValidation getSimpleValidation() {
		return simpleValidation;
	}

	public void setSimpleValidation(AFirmaSimpleValidation simpleValidation) {
		this.simpleValidation = simpleValidation;
	}

	public AFirmaChainValidation getChainValidation() {
		return chainValidation;
	}

	public void setChainValidation(AFirmaChainValidation chainValidation) {
		this.chainValidation = chainValidation;
	}

	public AFirmaStateValidation getStateValidation() {
		return stateValidation;
	}

	public void setStateValidation(AFirmaStateValidation stateValidation) {
		this.stateValidation = stateValidation;
	}
	

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String  toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("\nAFirmaResultadoValidacionCertificado");
		sb.append("\n====================================");
		sb.append("\nresult: ").append(result);
		sb.append("\ndescription: ").append(description);
		sb.append("\nerrorCode: ").append(errorCode);
		sb.append("\nsimpleValidation: ").append("\t").append(simpleValidation);
		sb.append("\nchainValidation: ").append("\t").append(chainValidation);
		sb.append("\nstateValidation: ").append("\t").append(stateValidation);
		
		return sb.toString();
	}



	
}
