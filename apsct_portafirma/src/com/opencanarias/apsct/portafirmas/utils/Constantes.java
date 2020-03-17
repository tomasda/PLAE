package com.opencanarias.apsct.portafirmas.utils;

public class Constantes {

	/*CONFIG*/
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String URL_NOTIFICACION = "url_notificacion";
	public static final String APPLICATION_USERNAME = (String)ConfigUtils.getParametro(USERNAME);
	public static final String APPLICATION_PASSWORD = (String)ConfigUtils.getParametro(PASSWORD);
	
	/*Beans*/
	public static final String AUSENCIA_BEAN = "ausenciaBean";
	public static final String CIRCUITO_BEAN = "circuitoBean";
	public static final String COLABORADORES_BEAN = "colaboradoresBean";
	public static final String COMMON_BEAN = "commonBean";
	public static final String DOCUMENTOS_BEAN = "documentosBean";
	public static final String ESTADO_BEAN = "estadoBean";
	public static final String USUARIO_BEAN = "usuarioBean";
	public static final String UPLOAD_BEAN = "uploadBean";
	public static final String LOGIN_BEAN = "loginBean";
	public static final String FIRMA_BEAN = "firmaBean";
	public static final String LOGIN_USUARIO_BEAN = "loginUsuarioBean";
	public static final String VALIDACION_USUARIO_BEAN = "validacionUsuarioBean";
	
	/*Controllers*/
	public static final String CIRCUITO_CONTROLLER = "circuitoController";
	
	/*Bandejas*/
	public static final String PENDIENTES = "Pendientes";
	public static final String PERSONAL = "Personal";
	public static final String TRAMITADOS = "Tramitados";
	public static final String ENVIADOS = "Enviados";
	public static final String INICIO = "Administrador - Inicio";	
	
	/*PARAMS*/
	public static final String FIRM = "FIRM";
	public static final String VALD = "VALD";
	public static final String PEND = "PEND";
	public static final String ENVD = "ENVD";
	public static final String BORR = "BORR";
	public static final String RECH = "RECH";
	public static final String RECP = "RECP";
	public static final String URI = "uri";
	public static final String DAY = "DAY";
	public static final String WEEK = "WEEK";
	public static final String MONTH = "MONTH";
	public static final String QUARTER = "QUARTER";
	public static final String MIDDLE = "MIDDLE";
	public static final String YEAR = "YEAR";
	public static final String AUSNECIA = "ausencia";
	public static final String COLABORADORES = "colaboradores";
	public static final String CIRCUITO_ID = "circuitoID";
	public static final String PRDF = "PRDF";
	public static final String GNRD = "GNRD";
	public static final String PRTF = "PRTF";
	public static final String XADES = "XADES";
	public static final String PADES = "PADES";
	public static final String ESTADO_URI = "ESTADO_URI";
	
	/*ORDER-PARAMS*/
	public static final String ORDER_ASUNTO = "asunto";
	public static final String ORDER_NOMBRE_DOC = "nombreDoc";
	public static final String ORDER_FECHA = "FECHA";
	public static final String ORDER_ESTADO = "estado";
	public static final String ORDER_ACCION = "accion";
	public static final String ORDER_USUARIO = "usuario";
	public static final String ORDER_SIST_ORIGEN = "sisOrigen";
	public static final String ORDER_ASC = "ASC";
	public static final String ORDER_DESC = "DESC";
	
	//ausencia
	public static final String GROUP_ID_DELETE = "grupoIdBorrar";
	public static final String USUARIO_REMITENTE = "usuarioRemitente";
	public static final String SELECTED_PERSON = "personaSeleccionada";
	public static final String FECHA_SOLICITUD = "fechaSolicitud";
	public static final String ESTADO_DOCUMENTO = "estadoDocumento";
	public static final String NUMERO_EXPEDIENTE = "numeroExpediente";
	public static final String NOMBRE_DOCUMENTO = "nombreDocumento";
	public static final String ASUNTO_DOCUMENTO = "asuntoDocumento";
	public static final String SISTEMA_ORIGEN = "sistemaOrigen";
	
	/*INFO*/
	public static final String INFO_AUSENT_CORRECT = "Estado de la ausencia cambiado";
	public static final String INFO_TOTAL_DOCUMENTS = "El total de documentos cargados ha sido: ";
	public static final String INFO_CREATE_GROUP = "Se procede a crear un nuevo grupo";
	public static final String INFO_CREATE_GROUP_SUCCESS = "Grupo creado correctamente";
	public static final String INFO_DELETE_GROUP = "Se procede a borrar un grupo";
	public static final String INFO_DELETE_GROUP_SUCCESS = "Grupo borrado correctamente";
	
	/*WARNINGS*/
	public static final String WARN_MUST_SELECT_PERSONS = "Debe indicar al menos una persona.";
	public static final String WARN_MUST_SELECT_PERSONS_AUSENCIA = "Debe indicar al menos una persona que lo sustituya.";
	public static final String WARN_SELECTED_PERSON_EXIST = "La persona indicada ya existe en uno de los grupos.";
	public static final String WARN_AUSENT_YET = "Lo sentimos, pero usted ya es revisor de una persona.";
	
	/*ERRORS*/
	public static final String ERROR_GET_CONFIG_PARAMS = "No se han podido recuperar los parámetros de configuración";
	public static final String ERROR_GET_LIST_PERSONS = "No se ha podido recuperar el listado de personas.";
	public static final String ERROR_GET_LIST_CIRCUITS = "No se ha podido recuperar el listado de circuitos.";
	public static final String ERROR_PERSIST_AUSENT = "No se ha podido registrar su ausencia.";
	public static final String ERROR_GET_TYPE_GROUP = "No se han podido recuperar los tipos de grupos.";
	public static final String ERROR_GET_SELECTED_PERSONS = "No se han podido recuperar las personas seleccionadas.";
	public static final String ERROR_LOAD_USER = "No se ha podido cargar el usuario correctamente";
	public static final String ERROR_PERSIST_CIRCUIT = "No se ha podido dar de alta el circuito correctamente";
	public static final String ERROR_PERSIST_REVISION = "No se ha podido registrar correctamente a los colaboradores";
	public static final String ERROR_BUILD_SIGNBATCH = "No se ha podido construir el lote de firmas";
	public static final String ERROR_RETRIEVE_SIGNBATCH = "Error al tratar la firma realizada";
	
	/*MENSSAGE*/
	public static final String msgRepetirOperacion = "Por favor, inténtelo de nuevo. En caso de que los problemas persistan contacte con el equipo de soporte de la plataforma eAdmin.";
	public static final String msgNingunoFirmado = "No se ha podido firmar ninguno de los documentos seleccionados. " + msgRepetirOperacion;
	public static final String msgAlgunoNoFirmado = "No se ha podido firmar alguno de los documentos seleccionados. " + msgRepetirOperacion;
	public static final String msgTodosFirmados = "Los documentos seleccionados se han firmado correctamente.";
	
	/*ROUTES*/
	public static final String INDEX = "index";
	public static final String LOGIN = "login";
	public static final String LOGINERROR = "loginError";
	public static final String BACK = "back";
	public static final String HELP = "ayuda";
	
}
