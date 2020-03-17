package com.opencanarias.utils.constants;

public interface ConstantesCallback {
	
	public static final String CB_TIPO_FIRMA = "FIRMA";
	public static final String CB_TIPO_NOTIFICACION = "NOTIFICACION";
	public static final String CB_TIPO_NOTIFICACION_EXPEDIENTE = "NOTIFICACION-EXPEDIENTE";	
	public static final String CB_TIPO_REQUERIMIENTO = "REQUERIMIENTO";
	public static final String CB_TIPO_ACTUACION = "ACTUACION";
	public static final String CB_TIPO_FIRMA_MANUSCRITA = "FIRMA-MANUSCRITA";	
	
	public static final String CB_METODO_FIRMA = "callBackFirma";
	public static final String CB_METODO_NOTIFICACION = "callBackNotificacion";
	public static final String CB_METODO_NOTIFICACION_EXPEDIENTE = "callBackNotificacionExpediente";	
	public static final String CB_METODO_REQUERIMIENTO = "callBackRequerimiento";
	public static final String CB_METODO_ACTUACION = "callBackActuacion";
	public static final String CB_METODO_FIRMA_MANUSCRITA = "callBackFirmaManuscrita";	
	
	// Codigos de respuesta para Callbacks.
	public static final String CB_FIRMA_COD_SUCCESSFUL = "CB-FIRMA-0000";
	public static final String CB_FIRMA_COD_ERROR = "CB-FIRMA-0004";
	public static final String CB_NOTIFICACION_COD_SUCCESSFUL = "CB-NOTIFICACION-0001";
	public static final String CB_NOTIFICACION_COD_ERROR_BACKOFFICE = "CB-NOTIFICACION-0002";
	public static final String CB_NOTIFICACION_COD_ERROR = "CB-NOTIFICACION-0004";
	public static final String CB_NOTIF_EXP_COD_SUCCESSFUL = "CB-NOTIFICACION-EXP-0000";
	public static final String CB_NOTIF_EXP_COD_ERROR_BACKOFFICE = "CB-NOTIFICACION-EXP-0002";
	public static final String CB_NOTIF_EXP_COD_ERROR = "CB-NOTIFICACION-EXP-0004";
	public static final String CB_REQUERIMIENTO_COD_SUCCESSFUL = "CB-REQUERIMIENTO-0000";
	public static final String CB_REQUERIMIENTO_COD_ERROR_BACKOFFICE = "CB-REQUERIMIENTO-0002";	
	public static final String CB_REQUERIMIENTO_COD_ERROR = "CB-REQUERIMIENTO-0004";			
	public static final String CB_ACTUACIONES_COD_SUCCESSFUL = "CB-ACTUACION-0000";
	public static final String CB_ACTUACIONES_COD_ERROR = "CB-ACTUACION-0004";	
	public static final String CB_FIRMA_MANUSCRITA_COD_SUCCESSFUL = "CB-FIRMA-MANUSCRITA-0000";
	public static final String CB_FIRMA_MANUSCRITA_COD_ERROR = "CB-FIRMA-MANUSCRITA-0004";	
	
	public static final String CB_FIRMA_TIMER_ACTIVE =  "cbFirmaTimer.active";
	public static final String CB_FIRMA_TIMER_DAYS_OF_WEEK = "cbFirmaTimer.daysOfWeek";
	public static final String CB_FIRMA_TIMER_HOURS = "cbFirmaTimer.hours";
	public static final String CB_FIRMA_TIMER_MINUTES = "cbFirmaTimer.minutes";
	public static final String CB_FIRMA_TIMER_SECONDS = "cbFirmaTimer.seconds";
	public static final String CB_FIRMA_TIMER_MAX_INTENTOS_EMAIL = "cbFirmaTimer.maxIntentos.email";
	
	public static final String CB_NOTIFICACIONES_TIMER_ACTIVE =  "cbNotificacionesTimer.active";
	public static final String CB_NOTIFICACIONES_TIMER_DAYS_OF_WEEK = "cbNotificacionesTimer.daysOfWeek";
	public static final String CB_NOTIFICACIONES_TIMER_HOURS = "cbNotificacionesTimer.hours";
	public static final String CB_NOTIFICACIONES_TIMER_MINUTES = "cbNotificacionesTimer.minutes";
	public static final String CB_NOTIFICACIONES_TIMER_SECONDS = "cbNotificacionesTimer.seconds";
	public static final String CB_NOTIFICACIONES_TIMER_MAX_INTENTOS_EMAIL = "cbNotificacionesTimer.maxIntentos.email";
	
	public static final String CB_NOTIFICACIONES_EXP_TIMER_ACTIVE =  "cbNotificacionesExpTimer.active";
	public static final String CB_NOTIFICACIONES_EXP_TIMER_DAYS_OF_WEEK = "cbNotificacionesExpTimer.daysOfWeek";
	public static final String CB_NOTIFICACIONES_EXP_TIMER_HOURS = "cbNotificacionesExpTimer.hours";
	public static final String CB_NOTIFICACIONES_EXP_TIMER_MINUTES = "cbNotificacionesExpTimer.minutes";
	public static final String CB_NOTIFICACIONES_EXP_TIMER_SECONDS = "cbNotificacionesExpTimer.seconds";
	public static final String CB_NOTIFICACIONES_EXP_TIMER_MAX_INTENTOS_EMAIL = "cbNotificacionesExpTimer.maxIntentos.email";	
	
	public static final String CB_REQUERIMIENTOS_TIMER_ACTIVE =  "cbRequerimientosTimer.active";
	public static final String CB_REQUERIMIENTOS_TIMER_DAYS_OF_WEEK = "cbRequerimientosTimer.daysOfWeek";
	public static final String CB_REQUERIMIENTOS_TIMER_HOURS = "cbRequerimientosTimer.hours";
	public static final String CB_REQUERIMIENTOS_TIMER_MINUTES = "cbRequerimientosTimer.minutes";
	public static final String CB_REQUERIMIENTOS_TIMER_SECONDS = "cbRequerimientosTimer.seconds";
	public static final String CB_REQUERIMIENTOS_TIMER_MAX_INTENTOS_EMAIL = "cbRequerimientosTimer.maxIntentos.email";	
	
	public static final String CB_ACTUACIONES_TIMER_ACTIVE =  "cbActuacionesTimer.active";
	public static final String CB_ACTUACIONES_TIMER_DAYS_OF_WEEK = "cbActuacionesTimer.daysOfWeek";
	public static final String CB_ACTUACIONES_TIMER_HOURS = "cbActuacionesTimer.hours";
	public static final String CB_ACTUACIONES_TIMER_MINUTES = "cbActuacionesTimer.minutes";
	public static final String CB_ACTUACIONES_TIMER_SECONDS = "cbActuacionesTimer.seconds";
	public static final String CB_ACTUACIONES_TIMER_MAX_INTENTOS_EMAIL = "cbActuacionesTimer.maxIntentos.email";
	
	public static final String CB_FIRMA_MANUSCRITA_TIMER_ACTIVE =  "cbFirmaManuscritaTimer.active";
	public static final String CB_FIRMA_MANUSCRITA_TIMER_DAYS_OF_WEEK = "cbFirmaManuscritaTimer.daysOfWeek";
	public static final String CB_FIRMA_MANUSCRITA_TIMER_HOURS = "cbFirmaManuscritaTimer.hours";
	public static final String CB_FIRMA_MANUSCRITA_TIMER_MINUTES = "cbFirmaManuscritaTimer.minutes";
	public static final String CB_FIRMA_MANUSCRITA_TIMER_SECONDS = "cbFirmaManuscritaTimer.seconds";
	public static final String CB_FIRMA_MANUSCRITA_TIMER_MAX_INTENTOS_EMAIL = "cbFirmaManuscritaTimer.maxIntentos.email";
}
