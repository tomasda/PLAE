package com.opencanarias.ejb.portafirmas.exceptions;

public class PortafirmasServiceException extends Exception {

	private static final long serialVersionUID = -9200352361097047048L;
	private static String errorCode;
	private static String description;
	
	public PortafirmasServiceException() {
		super();
	}

	public PortafirmasServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PortafirmasServiceException(String message) {
		super(message);
	}

	public PortafirmasServiceException(Throwable cause) {
		super(cause);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		PortafirmasServiceException.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		PortafirmasServiceException.description = description;
	}

}
