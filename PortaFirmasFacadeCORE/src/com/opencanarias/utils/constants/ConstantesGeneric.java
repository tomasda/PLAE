package com.opencanarias.utils.constants;

public interface ConstantesGeneric {

	/*******************************************
	 * Errores relativos a certificados
	 *******************************************/
	public static final String VALIDAR_CERT_SERVICIOS_SEDE = "sede.servicios";
	public static final String VALIDAR_CERT_ERROR_CADUCADO = "El certificado de usuario con el que intenta acceder no es válido. Revise que no se trata de un certificado cuya validez ha expirado o que no está aún activo.";
	public static final String VALIDAR_CERT_ERROR_NO_ESPECIFICADO = "Debe especificar un certificado.";
	public static final String VALIDAR_CERT_ERROR_NO_LEIDO = "No se ha podido leer el certificado.";
	public static final String VALIDAR_CERT_ERROR_NO_INFO = "No se ha podido extraer la información necesaria del certificado.";
	public static final String VALIDAR_CERT_ERROR_GENERAL = "No se ha podido comprobar la validez del certificado.";

}
