package com.opencanarias.apsct.modulo.callback.ejb;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.xml.ws.WebServiceException;

import org.jboss.logging.Logger;

//import com.opencanarias.apsct.modulo.callback.exception.CallbackServiceException;
//import com.opencanarias.apsct.modulo.callback.transform.CallbackTransform;
//import com.opencanarias.apsct.modulo.callback.utils.CallbackCommonUtils;
//import com.opencanarias.utils.Constantes;

//import es.apt.ae.facade.dto.UsuarioItem;
//import es.apt.ae.facade.entities.callback.CallbackTimer;
//import es.apt.ae.facade.entities.earegistro.Tercero;
//import es.apt.ae.facade.entities.portafirmas.Persona;
//import es.apt.ae.facade.utils.constants.AlertasConstants;

@SuppressWarnings("unused")
@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class CallbackEJB implements CallbackLocal, CallbackRemote {

	private static Logger logger = Logger.getLogger(CallbackEJB.class);
	
//	@PersistenceContext (unitName="facade-pu")
//	private EntityManager em;
	
	
//	public void saveCallback(String entidadId, String hash, String email, String estado, boolean estadoIntermedio, String observaciones, 
//			Date fecha, String wsdl, String dni, String actorDni, String tipo) throws CallbackServiceException {
//		CallbackTimer callbackTimer = CallbackCommonUtils.validateCallbackParams(entidadId, hash, email, estado, estadoIntermedio, observaciones, fecha, wsdl, dni, actorDni, tipo);
//
//		em.persist(callbackTimer);
//	}
//	
//	public boolean invokeCbActuacion(String numActuacion, String hash, String email, String estado,
//			String observaciones, Date fecha, String wsdl, String dni, String tipo) throws CallbackServiceException {
//		logger.info("[FACHADA-SRV_CALLBACK] SE VA A INVOCAR AL CALLBACK DE " + tipo + "...");
//		CallbackTimer callbackTimer = CallbackCommonUtils.validateCallbackParams(numActuacion, hash, email, estado, false, observaciones, fecha, wsdl, dni, null, tipo);
//		
//		String params = CallbackTransform.callbackTimerActuacionToParams(callbackTimer);
//		return CallbackCommonUtils.invokeCallback(callbackTimer, params, Constantes.CB_TIPO_ACTUACION, Constantes.CB_METODO_ACTUACION,
//				Constantes.CB_ACTUACIONES_COD_SUCCESSFUL, null, AlertasConstants.ERROR_NOTIFICAR_ACTUACION, em);
//	}
//	
//	public boolean invokeCbFirma(String entidadId, String hash, String email, String estado, boolean estadoIntermedio,
//			String observaciones, Date fecha, String wsdl, String dni, String actorDni, String tipo) throws CallbackServiceException {
//		
//		logger.info("[FACHADA-SRV_CALLBACK] SE VA A INVOCAR AL CALLBACK DE " + tipo + " (ESTADO INTERMEDIO: " + (estadoIntermedio?"SI":"NO") + ")...");
//		CallbackTimer callbackTimer = CallbackCommonUtils.validateCallbackParams(entidadId, hash, email, estado, estadoIntermedio, observaciones, fecha, wsdl, dni, actorDni, tipo);
//		
//		Map<String, Persona> personasMap = new HashMap<String, Persona>();
//		String params = CallbackTransform.callbackTimerFirmaToParams(callbackTimer, personasMap, em);
//		return CallbackCommonUtils.invokeCallback(callbackTimer, params, Constantes.CB_TIPO_FIRMA, Constantes.CB_METODO_FIRMA, 
//				Constantes.CB_FIRMA_COD_SUCCESSFUL, null, AlertasConstants.ERROR_FIRMA_RECHAZO_DOCUMENTO, em);
//	}
//	
//	public boolean invokeCbFirmaManuscrita(String entidadId, String hash, String email, String estado, boolean estadoIntermedio,
//			String observaciones, Date fecha, String wsdl, String dni, String actorDni, String tipo) throws CallbackServiceException {
//		logger.info("[FACHADA-SRV_CALLBACK] SE VA A INVOCAR AL CALLBACK DE " + tipo + " (ESTADO INTERMEDIO: " + (estadoIntermedio?"SI":"NO") + ")...");
//		CallbackTimer callbackTimer = CallbackCommonUtils.validateCallbackParams(entidadId, hash, email, estado, estadoIntermedio, observaciones, fecha, wsdl, dni, actorDni, tipo);
//		
//		String params = CallbackTransform.callbackTimerActuacionToParams(callbackTimer);
//		return CallbackCommonUtils.invokeCallback(callbackTimer, params, Constantes.CB_TIPO_FIRMA_MANUSCRITA, Constantes.CB_METODO_FIRMA_MANUSCRITA,
//				Constantes.CB_FIRMA_MANUSCRITA_COD_SUCCESSFUL, null, AlertasConstants.FIRMA_MANUSCRITA_CAMBIO_ESTADO, em);
//	}
//	
//	public boolean invokeCbNotificacion(String entidadId, String hash, String email, String estado,
//			String observaciones, Date fecha, String wsdl, String dni, String tipo) throws CallbackServiceException {
//		
//		logger.info("[FACHADA-SRV_CALLBACK] SE VA A INVOCAR AL CALLBACK DE " + tipo + "...");
//		CallbackTimer callbackTimer = CallbackCommonUtils.validateCallbackParams(entidadId, hash, email, estado, false, observaciones, fecha, wsdl, dni, null, tipo);
//		
//		String params = CallbackTransform.callbackTimerNotificacionToParams(callbackTimer);
//		return CallbackCommonUtils.invokeCallback(callbackTimer, params, Constantes.CB_TIPO_NOTIFICACION, Constantes.CB_METODO_NOTIFICACION, 
//				Constantes.CB_NOTIFICACION_COD_SUCCESSFUL, Constantes.CB_NOTIFICACION_COD_ERROR_BACKOFFICE, AlertasConstants.ERROR_NOTIFICAR_DOCUMENTO, em);
//	}
//	
//	public boolean invokeCbNotificacionExpediente(String numNotificacion, String hash, String email, String estado,
//			String observaciones, Date fecha, String wsdl, String dni, String tipo) throws CallbackServiceException {
//		
//		logger.info("[FACHADA-SRV_CALLBACK] SE VA A INVOCAR AL CALLBACK DE " + tipo + "...");
//		boolean callbackCompletado = false;
//		CallbackTimer callbackTimer = CallbackCommonUtils.validateCallbackParams(numNotificacion, hash, email, estado, false, observaciones, fecha, wsdl, dni, null, tipo);
//		
//		String params = CallbackTransform.callbackTimerNotificacionExpedienteToParams(callbackTimer, em);
//		try {
//			callbackCompletado = CallbackCommonUtils.invokeCallback(callbackTimer, params, Constantes.CB_TIPO_NOTIFICACION_EXPEDIENTE, Constantes.CB_METODO_NOTIFICACION_EXPEDIENTE, 
//					Constantes.CB_NOTIF_EXP_COD_SUCCESSFUL, Constantes.CB_NOTIF_EXP_COD_ERROR_BACKOFFICE, AlertasConstants.ERROR_NOTIFICAR_DOCUMENTO, em);
//		} catch (WebServiceException e) {
//			logger.error("El servicio " + wsdl + " no tiene definido el metodo callbackNotificacionExpediente. Se invoca al metodo callbackNotificacion.");
//			params = CallbackTransform.callbackTimerNotificacionToParams(callbackTimer);
//			callbackCompletado = CallbackCommonUtils.invokeCallback(callbackTimer, params, Constantes.CB_TIPO_NOTIFICACION, Constantes.CB_METODO_NOTIFICACION, 
//					Constantes.CB_NOTIFICACION_COD_SUCCESSFUL, Constantes.CB_NOTIFICACION_COD_ERROR_BACKOFFICE, AlertasConstants.ERROR_NOTIFICAR_DOCUMENTO, em);
//		}
//		return callbackCompletado;
//	}
//	
//	public boolean invokeCbRequerimiento(String entidadId, String hash, String eMail, String estado,
//			String observaciones, Date fecha, String wsdl, String dni, String actorDni, String tipo) throws CallbackServiceException {
//		logger.info("[FACHADA-SRV_CALLBACK] SE VA A INVOCAR AL CALLBACK DE " + tipo + "...");
//		CallbackTimer callbackTimer = CallbackCommonUtils.validateCallbackParams(entidadId, hash, eMail, estado, false, observaciones, fecha, wsdl, dni, actorDni, tipo);
//		
//		Map<String, Tercero> tercerosMap = new HashMap<String, Tercero>();
//		Map<String, UsuarioItem> usuariosMap = new HashMap<String, UsuarioItem>();
//		String params = CallbackTransform.callbackTimerRequerimientoToParams(callbackTimer, tercerosMap, usuariosMap, em);
//		return CallbackCommonUtils.invokeCallback(callbackTimer, params, Constantes.CB_TIPO_REQUERIMIENTO, Constantes.CB_METODO_REQUERIMIENTO,
//				Constantes.CB_REQUERIMIENTO_COD_SUCCESSFUL, null, AlertasConstants.ERROR_NOTIFICAR_ACTUACION, em);
//	}
	
}
