package com.opencanarias.utils;

import java.util.Arrays;
import java.util.List;

public interface ExpedientesConstants {
	public static final String PERSISTENCE_UNIT_NAME = "facade-pu";
	
	// ESTADOS DE LOS EXPEDIENTES
	public static final short EXP_ESTADO_ACTIVO = 0;
	public static final short EXP_ESTADO_FINALIZADO = 1;
	public static final short EXP_ESTADO_CANCELADO = 2;
	public static final short EXP_ESTADO_PARALIZADO = 3;
	
	// ESTADOS DE FIRMA DE LOS DOCUMENTOS
	public static final String DOC_ESTADO_SIN_FIRMA = "Sin Firma";
	public static final String DOC_ESTADO_FIRMADO = "Firmado";	
	public static final String DOC_ESTADO_PENDIENTE_PORTAFIRMAS = "Pendiente Portafirmas";
	public static final String DOC_ESTADO_RECHAZADO_PORTAFIRMAS = "Rechazado Portafirmas";
	public static final String DOC_ESTADO_FIRMADO_PORTAFIRMAS = "Firmado Portafirmas";
	public static final String DOC_ESTADO_COMPULSADO = "Compulsado";	
	public static final String DOC_ESTADO_PENDIENTE_FIRMA_MANUSCRITA = "Pendiente Firma Manuscrita";
	public static final String DOC_ESTADO_FIRMADO_MANUSCRITAMENTE = "Firmado Manuscritamente";
	public static final String DOC_ESTADO_RECHAZADO_MANUSCRITAMENTE = "Rechazado Firma Manuscrita";
	public static final String DOC_ESTADO_RECUPERADO_FIRMA_MANUSCRITA = "Recuperado Firma Manuscrita";
	public static final String DOC_ESTADO_DESCRIPCION_RECHAZADO_PORTAFIRMAS = 
			   "--DOCUMENTO NO FIRMADO EN PORTAFIRMAS-- " +
			   "Documento recuperado por el Tramitador del Expediente de " +
			   "manera voluntaria";	
	public static final List<String> DOC_ESTADOS_FIRMA = Arrays.asList(DOC_ESTADO_SIN_FIRMA, DOC_ESTADO_FIRMADO, DOC_ESTADO_COMPULSADO, 
			DOC_ESTADO_PENDIENTE_PORTAFIRMAS, DOC_ESTADO_FIRMADO_PORTAFIRMAS, DOC_ESTADO_RECHAZADO_PORTAFIRMAS);
	
	// ESTADOS DE LAS ACTUACIONES
	public static final String ACTUACION_ESTADO_FINALIZADO = "FINALIZADO";
	public static final String ACTUACION_ESTADO_RECHAZADO = "RECHAZADO";
	
	// ESTADOS DE LOS REQUERIMIENTOS
	public static final String REQ_ESTADO_NO_RESPONDIDO = "NO_RESPONDIDO";
	public static final String REQ_ESTADO_NOTIFICADO = "NOTIFICADO";
	public static final String REQ_ESTADO_RESPONDIDO = "RESPONDIDO";
	public static final String REQ_ESTADO_CANCELADO = "CANCELADO";
	public static final String REQ_ESTADO_CADUCADO = "CADUCADO";

	// TIPO DE NOTIFICACION
	public static final String EXP_TIPO_NOTIFICACION_PAPEL = "papel";
	public static final String EXP_TIPO_NOTIFICACION_TELEMATICA = "telematica";
	public static final String EXP_TIPO_NOTIFICACION_DEH = "deh";
	
	// ESTADOS DE NOTIFICACION DE LOS DOCUMENTOS
	public static final String DOC_ESTADO_SIN_NOTIFICAR = "SIN NOTIFICAR";
	public static final String DOC_ESTADO_ENVIADO_NOTIFICAR = "ENVIADO A NOTIFICAR";
	public static final String DOC_ESTADO_NOTIFICADO = "NOTIFICADO";
	public static final String DOC_ESTADO_COMUNICADO = "COMUNICADO";	
	// ESTADOS DE NOTIFICACION DE LOS DOCUMENTOS EN LAS APLICACIONES DE PLAE
	public static final String DOC_ESTADO_PLAE_SIN_NOTIFICACION = "Sin notificación";
	public static final String DOC_ESTADO_PLAE_COMUNICADO = "Comunicado";	
	public static final String DOC_ESTADO_PLAE_ENVIADO_NOTIFICAR = "Enviado a notificar";
	public static final String DOC_ESTADO_PLAE_NOTIFICADO = "Notificado";
	public static final List<String> DOC_ESTADOS_NOTIFICACION_PLAE = Arrays.asList(DOC_ESTADO_PLAE_SIN_NOTIFICACION, DOC_ESTADO_PLAE_COMUNICADO, 
			DOC_ESTADO_PLAE_ENVIADO_NOTIFICAR, DOC_ESTADO_PLAE_NOTIFICADO);	
	// TIPOS DE NOTIFICACION DE DOCUMENTOS DE EXPEDIENTE
	public static final String DOC_TIPO_COMUNICACION = "2";
	public static final String DOC_TIPO_NOTIFICACION = "3";

	public static final String DOC_NOTIFICADO_COMPARECENCIA = "Comparecencia";
	public static final String DOC_NOTIFICADO_CADUCIDAD = "Caducidad";
	
	public static final String DOC_EXP_REGISTRO_ENTRADA = "entry";
	public static final String DOC_EXP_REGISTRO_SALIDA = "exit";	
	
	public static final String DOC_TIPO_CANCELADO = "canceled";
	public static final String DOC_SUFIJO_CANCELADO = "_PLAE_ANU";
}
