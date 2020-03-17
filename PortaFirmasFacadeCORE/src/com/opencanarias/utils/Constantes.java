package com.opencanarias.utils;

import java.util.HashMap;

import com.opencanarias.exceptions.ExceptionBean;
import com.opencanarias.utils.constants.ConstantesCallback;
import com.opencanarias.utils.constants.ConstantesGeneric;
//import com.opencanarias.utils.constants.ConstantesActuaciones;
//import com.opencanarias.utils.constants.ConstantesCallback;
//import com.opencanarias.utils.constants.ConstantesCommons;
//import com.opencanarias.utils.constants.ConstantesExpedientes;
//import com.opencanarias.utils.constants.ConstantesFirma;
//import com.opencanarias.utils.constants.ConstantesGeneric;
//import com.opencanarias.utils.constants.ConstantesGestorDocumental;
//import com.opencanarias.utils.constants.ConstantesNotificaciones;
//import com.opencanarias.utils.constants.ConstantesPortafirmas;
//import com.opencanarias.utils.constants.ConstantesRegistro;
//import com.opencanarias.utils.constants.ConstantesRequerimientos;
//import com.opencanarias.utils.constants.ConstantesTerceros;
import com.opencanarias.utils.constants.ConstantesGestorDocumental;
import com.opencanarias.utils.constants.ConstantesPortafirmas;

/**
 * Esta clase contendra todas aquellas constantes usadas por la aplicacion
 * @author Open Canarias
 *
 */
public class Constantes implements ConstantesGestorDocumental,ConstantesPortafirmas, ConstantesCallback, ConstantesGeneric   {//implements ConstantesRequerimientos, ConstantesExpedientes, ConstantesGestorDocumental, 
	//ConstantesRegistro, ConstantesPortafirmas, ConstantesActuaciones, ConstantesNotificaciones, 
	//ConstantesTerceros, ConstantesFirma, ConstantesCommons, ConstantesGeneric, ConstantesCallback {
	
	public static final String FILE_NAME_CONFIGURATION = "facade_configuration.properties";

	public static final int TIPO_TERCERO_INTERESADO = 1;
	public static final int TIPO_TERCERO_REPRESENTANTE = 2;

	public static final String GESTOR_DOCUMENTAL_USERNAME = "admin";
	public static final String GESTOR_DOCUMENTAL_PASSWORD = "admin";
	
	public static final String DPT0_DECRETAR = "_DEC_dptoDecretar";

	public static final String CADENA_VACIA = "";

	public static final String PROPERTY_ENTORNO = "entorno";
	public static final String PROPERTY_VERSION = "version";
	public static final String PROPERTY_EXPEDIENTES_BUSQUEDA_MAX_RESULTS = "servicio.expedientes.busqueda.max.results";
	
	public static final String PERSISTENCEUNIT_APTAE_NAME = "facade-pu";

	// Codigos de respuesta del WJB/WS
	public static final String FWS_COD_SUCCESSFUL = "FWS-0000";
	public static final String FWS_COD_ERROR = "FWS-0001";
	public static final String FWS_DESC_SUCCESSFUL = "La consulta se ha ejecutado con exito.";
	public static final String FWS_COD_ERR_REPOSITORIO = "FWS-0002";
	public static final String FWS_DESC_ERR_REPOSITORIO = "Error en la conexion con el gestor documental. Los errores mas probables son un usuario/password incorrectos o problemas de conexion. Intentelo de nuevo y si el error persiste, contacte con el administrador.";
	public static final String FWS_COD_ERR_AUTHENTICATION = "FWS-0003";
	public static final String FWS_DESC_ERR_AUTHENTICATION = "Error en la validacion de credenciales. Los errores mas probables son un usuario/password incorrectos o problemas de conexion. Intentelo de nuevo y si el error persiste, contacte con el administrador.";
	
	
	public static final String CONFI_GD_ALFRESCO_ADM_ELECTRONICA_FOLDER = "TODO";
	public static final String CONFI_GD_ALFRESCO_PORTAFIRMAS_FOLDER = "TODO";
	public static final String FWS_DESC_ERROR_PARAMS = "TODO";

	public static final String FWS_EXP_DESC_ERROR_0001 = null;

	public static final String FWS_DESC_ERROR_CREDENCIALES = null;





	
	public static HashMap<Integer, ExceptionBean> mensajesExp;
	static {
		mensajesExp = new HashMap<Integer, ExceptionBean>();
//		mensajesExp.put(1, new ExceptionBean(FWS_EXP_COD_ERROR_0001, FWS_EXP_ERROR_0001, FWS_EXP_DESC_ERROR_0001));
//		mensajesExp.put(2, new ExceptionBean(FWS_EXP_COD_ERROR_0002, FWS_EXP_ERROR_0002, ""));
//		mensajesExp.put(3, new ExceptionBean(FWS_EXP_COD_ERROR_0003, FWS_EXP_ERROR_0003, FWS_EXP_DESC_ERROR_0003));
//		mensajesExp.put(4, new ExceptionBean(FWS_EXP_COD_ERROR_0004, FWS_EXP_ERROR_0004, ""));
//		mensajesExp.put(5, new ExceptionBean(FWS_EXP_COD_ERROR_0005, FWS_EXP_ERROR_0005, ""));
	}
	
}
