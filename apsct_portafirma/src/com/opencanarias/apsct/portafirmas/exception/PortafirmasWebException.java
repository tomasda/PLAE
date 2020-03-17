package com.opencanarias.apsct.portafirmas.exception;

public class PortafirmasWebException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -106545380387566248L;

	private String webMessage;
	
	public PortafirmasWebException() {
		super();
	}
	
	public PortafirmasWebException(String message, Throwable cause) {
		super(message, cause);
		this.webMessage = message;
	}

	public PortafirmasWebException(String message) {
		super(message);
		this.webMessage = message;
	}

	public PortafirmasWebException(Throwable cause) {
		super(cause);
	}

	public PortafirmasWebException(Throwable cause, String webMessage) {
		super(cause);
		this.webMessage = webMessage;
	}
	
	public String getWebMessage() {
		return webMessage;
	}

	public void setWebMessage(String webMessage) {
		this.webMessage = webMessage;
	}
}
