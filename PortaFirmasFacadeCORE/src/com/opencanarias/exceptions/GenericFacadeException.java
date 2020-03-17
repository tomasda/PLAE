package com.opencanarias.exceptions;

public class GenericFacadeException extends Exception {
	private static final long serialVersionUID = 5358796804527883631L;

	protected String code;
	protected String name;

	public GenericFacadeException() {
		super();
	}

	public GenericFacadeException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenericFacadeException(String message) {
		super(message);
	}
	
	public GenericFacadeException(String code, String name, String message) {
		super(message);
		this.code = code;
		this.name = name;
	}	

	public GenericFacadeException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
