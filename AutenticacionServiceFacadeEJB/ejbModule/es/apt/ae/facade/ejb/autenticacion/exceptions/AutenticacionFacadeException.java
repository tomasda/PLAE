package es.apt.ae.facade.ejb.autenticacion.exceptions;

import com.opencanarias.exceptions.GenericFacadeException;

public class AutenticacionFacadeException extends GenericFacadeException {
	private static final long serialVersionUID = -4401720097321761155L;

	public AutenticacionFacadeException() {
		super();
	}

	public AutenticacionFacadeException(String message, Throwable cause) {
		super(message, cause);
	}

	public AutenticacionFacadeException(String message) {
		super(message);
	}

	public AutenticacionFacadeException(Throwable cause) {
		super(cause);
	}

}