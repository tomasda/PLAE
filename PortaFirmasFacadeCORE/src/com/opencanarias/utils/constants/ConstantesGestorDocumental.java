package com.opencanarias.utils.constants;

public interface ConstantesGestorDocumental {

	// Codigos de respuesta para Gestor Documental.
	public static final String FWS_GD_COD_SUCCESSFUL = "FWS-GD-0000";
	public static final String FWS_GD_DESC_SUCCESSFUL = "La operacion se ha efectuado correctamente. ";
	public static final String FWS_GD_COD_ERR_GENERICO = "FWS-GD-0001";
	public static final String FWS_GD_DESC_ERR_GENERICO = "Se ha producido un error desconocido. Pongase en contacto con el administrador. ";
	public static final String FWS_GD_COD_ERR_PARAMETROS = "FWS-GD-0002";
	public static final String FWS_GD_ERR_PARAMETROS = "Sintactico";	
	public static final String FWS_GD_DESC_ERR_PARAMETROS = "Los parametros de entrada no son correctos. ";
	public static final String FWS_GD_DESC_ERR_DOCUMENTOS_NO_ESPECIFICADOS = "Debe especificar al menos un documentos. ";
	public static final String FWS_GD_DESC_ERR_DOCUMENTOS_INFO = "Debe especificar la uri o la ruta de cada documento. ";
	public static final String FWS_GD_COD_ERR_REPOSITORIO = "FWS-GD-0003";
	public static final String FWS_GD_DESC_ERR_REPOSITORIO = "Se ha producido un error al interactuar con el Gestor Documental. Los errores mas comunes son que no tenga permisos de acceso al recurso y que haya introducido de forma incorrecta sus credenciales. ";
	public static final String FWS_GD_COD_ERR_RESPUESTA_PARCIAL = "FWS-GD-0004";
	public static final String FWS_GD_DESC_ERR_RESPUESTA_PARCIAL = "Se ha generado una respuesta parcial a la invocacion del metodo del servicio. Ver el tag de ERROR para mas informacion. ";
	public static final String FWS_GD_COD_ERR_GENERACION_DOC = "FWS-GD-0005";
	public static final String FWS_GD_DESC_ERR_GENERACION_DOC = "Se ha producido un error en la generacion de alguno de los documentos.";
	public static final String FWS_GD_COD_ERR_NODO_DUPLICADO = "FWS-GD-0006";
	public static final String FWS_GD_DESC_ERR_NODO_DUPLICADO = "Ya se encuentra un nodo con las caracteristicas especificadas dado de alta en el Gestor Documental. ";
	public static final String FWS_GD_COD_ERR_RUTA_INEXISTENTE = "FWS-GD-0007";
	public static final String FWS_GD_DESC_ERR_RUTA_INEXISTENTE = "La ruta especificada no se ha encontrado en el Gestor Documental. ";
	public static final String FWS_GD_COD_ERR_OPERACION_PARCIAL = "FWS-GD-0008";
	public static final String FWS_GD_DESC_ERR_ACTUALIZAR_NODO_PARCIAL = "No se ha podido actualizar la informacion de alguno de los documentos especificados. ";
	
	public static final String CONFI_GD_ALFRESCO_ADM_ELECTRONICA_FOLDER = "gestor.documental.alfresco.admElectronica.folder";
	public static final String CONFI_GD_ALFRESCO_PORTAFIRMAS_FOLDER = "gestor.documental.alfresco.folder.portafirmas";

	public static final String CONFIG_GD_ALFRESCO_USERNAME = "gestor.documental.alfresco.username";
	public static final String CONFIG_GD_ALFRESCO_PASSWORD = "gestor.documental.alfresco.password";

	public static final String CONFIG_GD_ALFRESCO_TIPO_EXPEDIENTE_QNAME = "gestor.documental.alfresco.tipoExpediente.qname";
}

