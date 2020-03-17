package com.opencanarias.exceptions;

public class ExpedientesFacadeException extends GenericFacadeException {
	private static final long serialVersionUID = -2445404702888926210L;

	public ExpedientesFacadeException() {
		super();		
	}

	public ExpedientesFacadeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpedientesFacadeException(String message) {
		super(message);
	}

	public ExpedientesFacadeException(Throwable cause) {
		super(cause);
	}

	public ExpedientesFacadeException(String code, String name, String message) {
		super(code, name, message);
	}
}
