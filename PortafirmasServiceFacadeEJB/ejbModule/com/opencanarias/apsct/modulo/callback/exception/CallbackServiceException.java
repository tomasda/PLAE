package com.opencanarias.apsct.modulo.callback.exception;

public class CallbackServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CallbackServiceException(String cadena) {
		super(cadena);
	}
	
	public CallbackServiceException(Throwable throwable) {
		super(throwable);
	}
	
	public CallbackServiceException(String cadena , Throwable throwable) {
		super(cadena, throwable);
	}

	
}
