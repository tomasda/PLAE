package com.opencanarias.ejb.common;

import java.util.List;

import org.jboss.logging.Logger;

import com.opencanarias.utils.ConfigUtils;
import com.opencanarias.utils.Constantes;

import es.apt.ae.facade.ws.params.commons.out.DocumentoSalidaError;

public class FacadeBean {
	
	private static Logger logger = Logger.getLogger(FacadeBean.class);
	
	public static final String RUTA_FICHERO_CONFIGURACION = ConfigUtils.getRutaNombreFicheroConfiguracion(Constantes.FILE_NAME_CONFIGURATION);
	public static final String USUARIO_GESTOR_EXPEDIENTES = "usuGESTEXP";
	public static final String USUARIO_REGISTRO_ES = "usuREGISTROES";
	public static final String USUARIO_SEDE = "usuSEDE";
	public static final String USUARIO_NOTIFICACIONES = "usuNOTIFICACIONES";
	public static final String USUARIO_PORTAFIRMAS = "usuPORTAFIRMAS";
	public static final String USUARIO_PORTAACTUACIONES = "usuPORTAACTUACIONES";
	public static final String USUARIO_SIDOPU = "usuSIDOPU";	
	public static final String USUARIO_MMPP = "usuMMPP";	
	public static final String USUARIO_ARISTA = "usuARISTA";
	public static final String USUARIO_NAVISION = "usuNAVISION";
	public static final String USUARIO_WEB_APSCT = "usuWebAPSCT";
	public static final String USUARIO_ADMINISTRADOR_PLAE = "adminaefs";
	public static final String USUARIO_ADMINISTRADOR_PLAE_ID = "00000000T";
	
	public static final String FROM = " FROM ";
	public static final String WHERE = " WHERE ";
	public static final String AND = " AND ";
	public static final String OR = " OR ";
	public static final String UNION_ALL = " UNION ALL ";
	public static final String ORDER_BY = " ORDER BY ";
	public static final String ORDER_BY_ASC = " ASC";
	public static final String ORDER_BY_DESC = " DESC";
	

	public static String getValidationErrorMessage(List<String> emptyFields) {
		logger.info("Validando mensaje");
		if (emptyFields == null || emptyFields.isEmpty()) {
			return "";
		}
		String msg = Constantes.FWS_DESC_ERROR_PARAMS; 
		if (emptyFields.size() == 1) {
			msg += "El campo ";
		} else {
			msg += "Los campos ";
		}
		for (int i = 0; i < emptyFields.size(); i++) {
			String dato = emptyFields.get(i);
			msg += "'" + dato + "'";
			if (i < (emptyFields.size()-1)) {
				msg += ", ";
			} else {
				msg += " ";
			}
		}
		if (emptyFields.size() == 1) {
			msg += "es obligatorio.";
		} else {
			msg += "son obligatorios.";
		}	
		logger.info("Validacion de mensaje completada");
		return msg;
	}
	
	public static void addDocumentoSalidaError(List<DocumentoSalidaError> documentosSalidaError, List<String> listaUuidsError, String uuidDoc, String error) {
		DocumentoSalidaError docSalidaError = new DocumentoSalidaError();
		docSalidaError.setUri(uuidDoc);
		docSalidaError.setError(error);
		documentosSalidaError.add(docSalidaError);		
		listaUuidsError.add(uuidDoc);		
	}
	
	public static String getBackofficeByCredentials(String credentials) {
		if (credentials != null && !credentials.equals("")) {
			if (USUARIO_GESTOR_EXPEDIENTES.equals(credentials)) {
				return "Gestor de Expedientes";
			} else if (USUARIO_REGISTRO_ES.equals(credentials)) {
				return "Registro Entrada/Salida";
			} else if (USUARIO_SEDE.equals(credentials)) {
				return "Servicios de Sede Electrónica";
			} else if (USUARIO_NOTIFICACIONES.equals(credentials)) {
				return "Buzón de Notificaciones Electrónicas";
			} else if (USUARIO_PORTAFIRMAS.equals(credentials)) {
				return "Portafirmas";
			} else if (USUARIO_PORTAACTUACIONES.equals(credentials)) {
				return "Porta-Actuaciones";				
			} else if (USUARIO_SIDOPU.equals(credentials)) {
				return "SIDOPU";
			} else if (USUARIO_MMPP.equals(credentials)) {
				return "SALVIA (Mercancías Peligrosas)";
			} else if (USUARIO_ARISTA.equals(credentials)) {
				return "ARISTA";
			}  else if (USUARIO_NAVISION.equals(credentials)) {
				return "NAVISION";
			}
		}
		return null;
	}
	
}
