package com.opencanarias.consola.commons;

public class Patrones {
	private static final String PATTERN_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PATTERN_TELEFONO = "^(0034|\\+34)?(\\d\\d\\d)-? ?(\\d\\d)-? ?(\\d)-? ?(\\d)-? ?(\\d\\d)$";
	private static final String PATTERN_NIF = "(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])";
	
	
	
	
	public static String getPatternCorreo() {
		return PATTERN_CORREO;
	}
	public static String getPatternTelefono() {
		return PATTERN_TELEFONO;
	}
	public static String getPatternNIF() {
		return PATTERN_NIF;
	}

}
