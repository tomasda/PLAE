package com.opencanarias.apsct.portafirmas.exception;

public class ElementNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5404632994821734937L;

	public ElementNotFoundException() {
		super();
	}
	
	public ElementNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElementNotFoundException(String message) {
		super(message);
	}

	public ElementNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
