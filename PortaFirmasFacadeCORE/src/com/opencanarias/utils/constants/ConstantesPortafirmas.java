package com.opencanarias.utils.constants;

public interface ConstantesPortafirmas {
	// Codigos de respuesta para Portafirmas.
	public static final String FWS_PF_COD_SUCCESSFUL = "FWS-PF-0000";
	public static final String FWS_PF_DESC_COD_SUCCESSFUL = "La ejecucion del servicio ha sido exitosa. ";
	public static final String FWS_PF_COD_ERR_GENERICO = "FWS-PF-0001";
	public static final String FWS_PF_DESC_ERR_GENERICO = "Se ha producido un error desconocido. Pongase en contacto con el administrador. ";
	public static final String FWS_PF_COD_ERR_PARAMETROS = "FWS-PF-0002";
	public static final String FWS_PF_DESC_ERR_PARAMETROS = "Los parametros de entrada no son correctos. Por favor referirse al manual del desarrollador para conformar una entrada correcta. ";
	public static final String FWS_PF_COD_ERR_RECUPERAR = "FWS-PF-0003";
	public static final String FWS_PF_DESC_ERR_NO_EXISTEN = "Alguno de los documentos especificados no se ha podido localizar en el portafirmas. ";
	public static final String FWS_PF_DESC_ERR_NO_EXISTE = "El documento no se ha podido localizar en el portafirmas";
	public static final String FWS_PF_DESC_ERR_NO_SOLICITANTE = "Alguno de las documentos especificados no se ha podido recuperar debido a que el solicitante no coincide con el que realizo el envio. ";
	public static final String FWS_PF_DESC_ERR_DOC_NO_SOLICITANTE = "El documento no se ha podido recuperar debido a que el solicitante no coincide con el que realizo el envio";
	public static final String FWS_PF_DESC_ERR_EN_TRAMITE = "Alguno de los documentos especificados ya se encuentra en tramite y no puede ser recuperado.";
	public static final String FWS_PF_DESC_ERR_DOC_EN_TRAMITE = "El documento ya se encuentra en tramite";
	public static final String FWS_PF_COD_ERR_VAL_ESTADO_DOCUMENTO = "FWS-PF-0004";
	public static final String FWS_PF_DESC_ERR_VAL_ESTADO_DOCUMENTO = "Alguno de los documentos especificados se encuentra en tramitacion. ";
	public static final String FWS_PF_COD_ERR_VAL_EXISTENCIA_DOCUMENTO = "FWS-PF-0005";
	public static final String FWS_PF_DESC_ERR_VAL_EXISTENCIA_DOCUMENTO = "Alguno de las documentos especificados no se encuentra en el Gestor Documental o no dispone de los permisos suficientes. ";
	public static final String FWS_PF_COD_ERR_VAL_TIPO_FIRMA = "FWS-PF-0006";
	public static final String FWS_PF_DESC_ERR_VAL_TIPO_FIRMA = "Alguno de los documentos especificados no permite el tipo de firma indicado. ";
	public static final String FWS_PF_COD_ERR_VAL_FORMATO = "FWS-PF-0007";
	public static final String FWS_PF_DESC_ERR_VAL_FORMATO= "Alguno de los documentos especificados no tiene un formato valido para la aplicacion. ";
	public static final String FWS_PF_COD_ERR_DNI_USUARIO = "FWS-PF-0008";
	public static final String FWS_PF_DESC_ERR_DNI_USUARIO= "El usuario solicitante que ha indicado, no tiene un DNI o NIE. ";
	public static final String FWS_PF_COD_ERR_NO_FIRMANTES = "FWS-PF-0009";
	public static final String FWS_PF_DESC_ERR_NO_FIRMANTES = "No se encontraron firmantes para el solicitante indicado. ";
	
	public static final String FWS_PF_NOTIFICACION_CAMBIO_GRUPO = "PORTAFIRMAS_CAMBIO_GRUPO";
	public static final String FWS_PF_NOTIFICACION_FIN_CIRCUITO = "PORTAFIRMAS_FIN_CIRCUITO";
	public static final String FWS_PF_OBSERVACION_RECUPERAR_DOC = "Documento recuperado de manera voluntaria por el Solicitante del envio";
	
	public static final String CONFIG_PF_CALLBACK_NOTIFICACION_CORREO = "portafirmas.callback.notificacion.correo";
}
