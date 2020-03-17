package com.opencanarias.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.alfresco.util.ISO8601DateFormat;

public class MetadataUtils {
	
	/* 	METADATOS DE PROCEDIMIENTO */
	public static final String NOMBRE_METADATO_PROC_CLASIFICACION = "proc_clasificacion";
	
	/* 	METADATOS DE EXPEDIENTE */
	public static final String NOMBRE_METADATO_EXP_ID = "exp_id";
	public static final String NOMBRE_METADATO_EXP_ORGANO = "exp_organo";	
	public static final String VALOR_METADATO_EXP_ORGANO = "E00003601";
	public static final String NOMBRE_METADATO_EXP_FECHA_APERTURA = "exp_fechaApertura";	
	public static final String NOMBRE_METADATO_EXP_FECHA_NOTIFICACION_RESOLUCION = "exp_fechaNotificacionResolucion";
	public static final String ETIQUETA_METADATO_EXP_FECHA_NOTIFICACION_RESOLUCION = "Fecha Notificación Resolución";	
	public static final String NOMBRE_METADATO_EXP_FECHA_EFECTIVA_NOTIFICACION_RESOLUCION = "exp_fechaEfectivaNotificacionResolucion";
	public static final String ETIQUETA_METADATO_EXP_FECHA_EFECTIVA_NOTIFICACION_RESOLUCION	= "Fecha Efectiva Notificación Resolución";	
	public static final String NOMBRE_METADATO_EXP_USUARIO_INICIA = "exp_usuarioInicia";
	public static final String ETIQUETA_METADATO_EXP_USUARIO_INICIA = "Usuario creador";		
	public static final String NOMBRE_METADATO_EXP_ASUNTO = "exp_asunto";
	public static final String NOMBRE_METADATO_EXP_NOMBRE_PROCEDIMIENTO = "exp_nombreProcedimiento";	
	public static final String NOMBRE_METADATO_EXP_ASIENTO_ORIGEN = "exp_asientoOrigen";
	public static final String NOMBRE_METADATO_EXP_VERSION_NTI = "exp_versionNTI";	
	public static final String VALOR_METADATO_EXP_VERSION_NTI = "http://administracionelectronica.gob.es/ENI/XSD/v1.0/expediente-e";	
	public static final String NOMBRE_METADATO_EXP_INTERESADO = "exp_interesado";
	public static final String NOMBRE_METADATO_EXP_NOMBRE_INTERESADO = "exp_nombreInteresado";
	public static final String NOMBRE_METADATO_EXP_TIPO_FIRMA = "exp_tipoFirma";
	public static final String VALOR_METADATO_EXP_TIPO_FIRMA_CSV = "CSV";
	public static final String VALOR_METADATO_EXP_TIPO_FIRMA_PIN = "PIN ciudadano";
	public static final String VALOR_METADATO_EXP_TIPO_FIRMA_CERTIFICADA = "Firma certificada";
	public static final String VALOR_METADATO_EXP_TIPO_FIRMA_ELECT_AVANZ = "Firma electrónica avanzada";
	public static final String VALOR_METADATO_EXP_TIPO_FIRMA_CERT_ELECT = "Certificado electrónico";	
	public static final String VALOR_METADATO_EXP_TIPO_FIRMA_ELECT_AVANZ_CERT = "Firma electrónica avanzada basada en certificados";
	public static final String NOMBRE_METADATO_EXP_CSV = "exp_valorCSV";
	public static final String VALOR_METADATO_EXP_CSV = "";	
	public static final String NOMBRE_METADATO_EXP_DEF_GENERACION_CSV = "exp_definicionGeneracionCSV";
	public static final String VALOR_METADATO_EXP_DEF_GENERACION_CSV = "";
	public static final String NOMBRE_METADATO_EXP_RUTA_CARPETA_ASIENTO = "exp_rutaCarpetaAsiento";
	public static final String NOMBRE_METADATO_EXP_ESTADO = "exp_estado";
	public static final String VALOR_METADATO_EXP_ESTADO_ABIERTO = "Abierto";
	public static final String VALOR_METADATO_EXP_ESTADO_CERRADO = "Cerrado";	
	public static final String NOMBRE_METADATO_EXP_FECHA_FIN = "exp_fechaFinalizacion";	
	
	/* METADATOS DE ASIENTO */
	public static final String NOMBRE_METADATO_REL_EXPEDIENTE_RELACIONADO = "rel_expedienteRelacionado";
	public static final String NOMBRE_METADATO_REL_CARPETA_EXPEDIENTE = "rel_rutaExpediente";

	/*  METADATOS DE DOCUMENTO */
	
	// Metadatos requeridos de un documento
	public static final String NOMBRE_METADATO_NOMBRE = "name";
	public static final String ETIQUETA_METADATO_NOMBRE = "Nombre";	
	public static final String NOMBRE_METADATO_AUTOR = "author";
	public static final String ETIQUETA_METADATO_AUTOR = "Autor";
	public static final String NOMBRE_METADATO_DESCRIPCION = "description";
	public static final String ETIQUETA_METADATO_DESCRIPCION = "Descripci�n";	
	public static final String NOMBRE_METADATO_FECHA_CREACION = "created";
	public static final String NOMBRE_METADATO_DOC_TIPO_ENTIDAD = "doc_tipoEntidad";
	public static final String VALOR_METADATO_DOC_TIPO_ENTIDAD = "Documento";
	public static final String NOMBRE_METADATO_DOC_CATEGORIA = "doc_categoria";
	public static final String VALOR_METADATO_DOC_CATEGORIA_DOCSIMPLE = "Documento Simple";
	public static final String VALOR_METADATO_DOC_CATEGORIA_EXPEDIENTE = "Expediente";
	public static final String ETIQUETA_METADATO_DOC_CATEGORIA = "Categor�a";		
	public static final String NOMBRE_METADATO_DOC_VERSION_NTI = "doc_versionNTI";
	public static final String VALOR_METADATO_DOC_VERSION_NTI = "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e";
	public static final String NOMBRE_METADATO_DOC_ID = "doc_id";
	public static final String NOMBRE_METADATO_DOC_FECHA_CAPTURA = "doc_fechaCaptura";
	public static final String ETIQUETA_METADATO_DOC_FECHA_CAPTURA = "Fecha aportaci�n";	
	public static final String NOMBRE_METADATO_DOC_ORIGEN = "doc_origen";
	public static final String VALOR_METADATO_DOC_ORIGEN_CIUDADANO	= "0-Ciudadano";
	public static final String VALOR_METADATO_DOC_ORIGEN_ADMIN = "1-Administración";
	public static final String ETIQUETA_METADATO_DOC_ORIGEN = "Origen";		
	public static final String NOMBRE_METADATO_DOC_ESTADO_ELABORACION = "doc_estadoElaboracion";
	public static final String VALOR_METADATO_DOC_ESTADO_ELABORACION_ORIGINAL = "Original";
	public static final String VALOR_METADATO_DOC_ESTADO_ELABORACION_COPIA = "Copia";
	
	// Metadatos condicionales de un documento
	public static final String NOMBRE_METADATO_DOC_TIPO_COPIA = "doc_tipoCopia";
	public static final String VALOR_METADATO_DOC_TIPO_COPIA = "";
	public static final String VALOR_METADATO_DOC_TIPO_COPIA_COMPULSADA = "Copia compulsada";	
	public static final String NOMBRE_METADATO_DOC_ID_DOC_ORIGEN = "doc_idDocumentoOrigen";
	public static final String VALOR_METADATO_DOC_ID_DOC_ORIGEN = "";
	public static final String NOMBRE_METADATO_DOC_NOMBRE_FORMATO = "doc_nombreFormato";
	public static final String VALOR_METADATO_DOC_NOMBRE_FORMATO_ISOIEC = "ISO/IEC 26300:2006";
	public static final String VALOR_METADATO_DOC_NOMBRE_FORMATO_SOXML = "Strict Open XML";
	public static final String VALOR_METADATO_DOC_NOMBRE_FORMATO_PDF = "PDF";
	public static final String VALOR_METADATO_DOC_NOMBRE_FORMATO_PNG = "PNG";
	public static final String VALOR_METADATO_DOC_NOMBRE_FORMATO_JPEG = "JPEG";
	public static final String VALOR_METADATO_DOC_NOMBRE_FORMATO_XML = "XML";
	public static final String VALOR_METADATO_DOC_NOMBRE_FORMATO_TXT = "TXT";
	public static final String NOMBRE_METADATO_DOC_SOPORTE_ORIGEN = "doc_soporteOrigen";
	public static final String VALOR_METADATO_DOC_SOPORTE_ORIGEN_DIGITAL = "Digital";
	public static final String VALOR_METADATO_DOC_SOPORTE_ORIGEN_DIGITALIZADO = "Digitalizado";
	public static final String NOMBRE_METADATO_DOC_TIPO_DOCUMENTAL = "doc_tipoDocumental";
	public static final String ETIQUETA_METADATO_DOC_TIPO_DOCUMENTAL = "Tipo documental";
	public static final String NOMBRE_METADATO_TIPO_FIRMA = "doc_tipoFirma";	
	public static final String NOMBRE_METADATO_DOC_TIPO_FIRMA = "doc_tipoFirma";
	public static final String VALOR_METADATO_DOC_TIPO_FIRMA_CSV = "CSV";
	public static final String VALOR_METADATO_DOC_TIPO_FIRMA_PIN = "PIN ciudadano";
	public static final String VALOR_METADATO_DOC_TIPO_FIRMA_CERTIFICADA = "Firma certificada";
	public static final String VALOR_METADATO_DOC_TIPO_FIRMA_ELECT_AVANZ = "Firma electrónica avanzada";
	public static final String VALOR_METADATO_DOC_TIPO_FIRMA_CERT_ELECT = "Certificado electrónico";	
	public static final String VALOR_METADATO_DOC_TIPO_FIRMA_ELECT_AVANZ_CERT = "Firma electrónica avanzada basada en certificados";
	public static final String NOMBRE_METADATO_DOC_FORMATO_FIRMA = "doc_formatoFirma";
	public static final String VALOR_METADATO_DOC_FORMATO_FIRMA_PADES = "PadES";
	public static final String VALOR_METADATO_DOC_FORMATO_FIRMA_CADESD = "CAdES detached";
	public static final String VALOR_METADATO_DOC_FORMATO_FIRMA_CADESA = "CAdES attached";
	public static final String VALOR_METADATO_DOC_FORMATO_FIRMA_XADESD = "XAdES internally detached";
	public static final String VALOR_METADATO_DOC_FORMATO_FIRMA_XADESE = "XAdES enveloped";
	public static final String NOMBRE_METADATO_DOC_ROL_FIRMA = "doc_rolFirma";
	public static final String VALOR_METADATO_DOC_ROL_FIRMA	= "";
	public static final String NOMBRE_METADATO_DOC_ALGORITMO_FIRMA = "doc_algoritmo";
	public static final String VALOR_METADATO_DOC_ALGORITMO_FIRMA = "";
	public static final String NOMBRE_METADATO_DOC_VALOR_CSV = "doc_valorCSV";
	public static final String VALOR_METADATO_DOC_VALOR_CSV = "";
	public static final String NOMBRE_METADATO_DOC_DEF_GENERACION_CSV = "doc_definicionGeneracionCSV";
	public static final String VALOR_METADATO_DOC_DEF_GENERACION_CSV = "";
	
	// Metadatos complementarios de documento
	public static final String NOMBRE_METADATO_DOC_FECHA_DOC = "doc_fechaDocumento";
	public static final String ETIQUETA_METADATO_DOC_FECHA_DOC = "Fecha elaboraci�n";	
	public static final String NOMBRE_METADATO_DOC_FECHA_FIN = "doc_fechaFinalizacion";
	public static final String ETIQUETA_METADATO_DOC_FECHA_FIN = "Fecha fin de vigencia";	
	public static final String NOMBRE_METADATO_DOC_REGISTRO = "doc_registro";
	public static final String ETIQUETA_METADATO_DOC_REGISTRO		= "Registro";	
	public static final String NOMBRE_METADATO_DOC_FIRMAS = "doc_firmas";
	public static final String NOMBRE_METADATO_FIRMAS = "doc_firmas";
	public static final String DOC_FIRMAS_SEPARADOR		= "##########";
	public static final String NOMBRE_METADATO_DOC_TIMESTAMP = "doc_timestamp";
	public static final String VALOR_METADATO_DOC_TIMESTAMP = "";
	public static final String NOMBRE_METADATO_DOC_OBSERVACIONES = "doc_observaciones";
	public static final String VALOR_METADATO_DOC_OBSERVACIONES = "";
	public static final String ETIQUETA_METADATO_DOC_OBSERVACIONES = "Observaciones";	
	
	
	// Mapa de pares <nombre campo, descripcion campo>
	public static final HashMap<String, String> ETIQUETAS_METADATOS_MAP = new HashMap<String, String>();
    static {
    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_EXP_USUARIO_INICIA, ETIQUETA_METADATO_EXP_USUARIO_INICIA);
    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_EXP_FECHA_NOTIFICACION_RESOLUCION, ETIQUETA_METADATO_EXP_FECHA_NOTIFICACION_RESOLUCION);
    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_EXP_FECHA_EFECTIVA_NOTIFICACION_RESOLUCION, ETIQUETA_METADATO_EXP_FECHA_EFECTIVA_NOTIFICACION_RESOLUCION);

    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_NOMBRE, ETIQUETA_METADATO_NOMBRE);
    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_DESCRIPCION, ETIQUETA_METADATO_DESCRIPCION);
    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_AUTOR, ETIQUETA_METADATO_AUTOR);
    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_DOC_ORIGEN, ETIQUETA_METADATO_DOC_ORIGEN);
    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_DOC_CATEGORIA, ETIQUETA_METADATO_DOC_CATEGORIA);
    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_DOC_TIPO_DOCUMENTAL, ETIQUETA_METADATO_DOC_TIPO_DOCUMENTAL);
    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_DOC_FECHA_DOC, ETIQUETA_METADATO_DOC_FECHA_DOC);
    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_DOC_FECHA_CAPTURA, ETIQUETA_METADATO_DOC_FECHA_CAPTURA);
    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_DOC_FECHA_FIN, ETIQUETA_METADATO_DOC_FECHA_FIN);
    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_DOC_REGISTRO, ETIQUETA_METADATO_DOC_REGISTRO);
    	ETIQUETAS_METADATOS_MAP.put(NOMBRE_METADATO_DOC_OBSERVACIONES, ETIQUETA_METADATO_DOC_OBSERVACIONES);
    }

	/**
	 * Metodo encargado de generar los  metadatos que se almacenan en una carpeta 
	 * de un expediente en el repositorio de documentos en el momento de su inicio
	 * 
	 * @return
	 */
	public static HashMap<String, Object> generarMetadatosInicioExp(String usuarioInicia, String expId, String expAsunto, Date expFechaInicio,
			String interesadoId, String interesadoNombre, String procId, String procDesc, 
			String regNumero, String regUri, String regRuta) {
		HashMap<String, Object> metadatosExpediente = new HashMap<String, Object>();
			
		//METADATOS OBLIGATORIOS
		String organo = VALOR_METADATO_EXP_ORGANO;
		metadatosExpediente.put(NOMBRE_METADATO_EXP_VERSION_NTI, VALOR_METADATO_EXP_VERSION_NTI);
		metadatosExpediente.put(NOMBRE_METADATO_EXP_ID, identificadorExpediente(expId, expFechaInicio, organo));
		metadatosExpediente.put(NOMBRE_METADATO_EXP_ORGANO, organo);
		metadatosExpediente.put(NOMBRE_METADATO_EXP_FECHA_APERTURA, expFechaInicio);
		metadatosExpediente.put(NOMBRE_METADATO_PROC_CLASIFICACION, clasificacionExpediente(procId, organo));
		metadatosExpediente.put(NOMBRE_METADATO_EXP_INTERESADO, interesadoId);
		metadatosExpediente.put(NOMBRE_METADATO_EXP_TIPO_FIRMA, VALOR_METADATO_EXP_TIPO_FIRMA_ELECT_AVANZ_CERT);
		
		if (metadatosExpediente.get(NOMBRE_METADATO_EXP_TIPO_FIRMA).equals(VALOR_METADATO_EXP_TIPO_FIRMA_CSV)) {
			metadatosExpediente.put(NOMBRE_METADATO_EXP_CSV, VALOR_METADATO_EXP_CSV);
			metadatosExpediente.put(NOMBRE_METADATO_EXP_DEF_GENERACION_CSV, VALOR_METADATO_EXP_DEF_GENERACION_CSV);
		}
		
		//METADATOS NO OBLIGATORIOS
		if (!StringUtils.isNullOrEmpty(regNumero)) {
			if (!StringUtils.isNullOrEmpty(regUri)) {
				metadatosExpediente.put(NOMBRE_METADATO_EXP_ASIENTO_ORIGEN, regUri); 
			}
			if (!StringUtils.isNullOrEmpty(regRuta)) {
				metadatosExpediente.put(NOMBRE_METADATO_EXP_RUTA_CARPETA_ASIENTO, regRuta);
			}
		}
		metadatosExpediente.put(NOMBRE_METADATO_EXP_ESTADO, estadoFechaFin(null));
		metadatosExpediente.put(NOMBRE_METADATO_EXP_ASUNTO, expAsunto);
		metadatosExpediente.put(NOMBRE_METADATO_EXP_NOMBRE_PROCEDIMIENTO, procDesc); 
		metadatosExpediente.put(NOMBRE_METADATO_EXP_NOMBRE_INTERESADO, interesadoNombre);
		metadatosExpediente.put(NOMBRE_METADATO_EXP_USUARIO_INICIA, usuarioInicia);
		
		return metadatosExpediente;		
	}
	
	/**
	 * Metodo encargado de generar los  metadatos que se almacenan en una carpeta 
	 * de un expediente en el repositorio de documentos en el momento de su finalizacion
	 * 
	 * @return
	 */
	public static HashMap<String, Object> generarMetadatosFinExp(Date expFechaFin) {
		HashMap<String, Object> metadatosExpediente = new HashMap<String, Object>();
			
		metadatosExpediente.put(NOMBRE_METADATO_EXP_FECHA_FIN, expFechaFin);
		metadatosExpediente.put(NOMBRE_METADATO_EXP_ESTADO, estadoFechaFin(expFechaFin));
		
		return metadatosExpediente;		
	}
	
	/**
	 * Metodo encargado de generar los  metadatos que se almacenan en una carpeta 
	 * de un expediente en el repositorio de actualizar la informacion de notificacion de la resolucion
	 * 
	 * @return
	 */
	public static HashMap<String, Object> generarMetadatosNotifResolucion(Date fechaNotifResol, Date fechaEfecNotifResol) {
		HashMap<String, Object> metadatosExpediente = new HashMap<String, Object>();
			
		if (fechaNotifResol != null) {
			metadatosExpediente.put(NOMBRE_METADATO_EXP_FECHA_NOTIFICACION_RESOLUCION, fechaNotifResol);
		}
		if (fechaEfecNotifResol != null) { 
			metadatosExpediente.put(NOMBRE_METADATO_EXP_FECHA_EFECTIVA_NOTIFICACION_RESOLUCION, fechaEfecNotifResol);
		}
		
		return metadatosExpediente;		
	}
	
	public static HashMap<String, Object> generarMetadatosSubidaDoc(String usuario, String docNombre, String docDescripcion, 
			String docFormato, String docTipo, String docId, Date docFechaElaboracion, Date docFechaFinVigencia, Date docFechaCaptura, String docObservaciones,
			String docFirmaXML, String expId, String regNumero, boolean esRegElectronico, boolean esInicioExp){
		HashMap<String, Object> metadatosDocumento = new HashMap<String, Object>();
		generarMetadatosObligatoriosDocumento(true, metadatosDocumento, usuario, docNombre, docDescripcion, docFormato, 
				docTipo, docId, docFechaElaboracion, docFechaCaptura, expId, regNumero, esRegElectronico, esInicioExp);
		generarMetadatosNoObligatoriosDocumento(metadatosDocumento, docFechaElaboracion, docFechaFinVigencia, regNumero, docObservaciones, docFirmaXML);//añadir fecha fin documento
		return metadatosDocumento;
	}
	
	public static HashMap<String, Object> generarMetadatosActualizacionDoc(String usuario, String docNombre, String docDescripcion, 
			String docFormato, String docTipo, String docId, Date docFechaElaboracion, Date docFechaFinVigencia, Date docFechaCaptura, String docObservaciones,
			String docFirmaXML, String expId, String regNumero, boolean esRegElectronico, boolean esInicioExp){
		HashMap<String, Object> metadatosDocumento = new HashMap<String, Object>();
		generarMetadatosObligatoriosDocumento(false, metadatosDocumento, usuario, docNombre, docDescripcion, docFormato, 
				docTipo, docId, docFechaElaboracion, docFechaCaptura, expId, regNumero, esRegElectronico, esInicioExp);
		generarMetadatosNoObligatoriosDocumento(metadatosDocumento, docFechaElaboracion, docFechaFinVigencia, regNumero, docObservaciones, docFirmaXML);//añadir fecha fin documento
		return metadatosDocumento;
	}
	
	private static HashMap<String, Object> generarMetadatosObligatoriosDocumento(boolean actualizarNombre, HashMap<String, Object> metadatosDocumento, 
			String usuario, String docNombre, String docDescripcion, String docFormato, String docTipo, String docId, Date docFechaFinVigencia, 
			Date docFechaCaptura, String expId, String regNumero, boolean esRegElectronico, boolean esInicioExp) {

		if (actualizarNombre) {
			metadatosDocumento.put(NOMBRE_METADATO_NOMBRE, docNombre);
		}
		metadatosDocumento.put(NOMBRE_METADATO_AUTOR, usuario);
		metadatosDocumento.put(NOMBRE_METADATO_DESCRIPCION, docDescripcion);
		
		metadatosDocumento.put(NOMBRE_METADATO_DOC_TIPO_ENTIDAD, VALOR_METADATO_DOC_TIPO_ENTIDAD);
		String organo = VALOR_METADATO_EXP_ORGANO;
		if((expId != null && !expId.trim().equalsIgnoreCase("")) || esInicioExp) { // Si tiene un expediente definido
			metadatosDocumento.put(NOMBRE_METADATO_DOC_CATEGORIA, VALOR_METADATO_DOC_CATEGORIA_EXPEDIENTE);
			if (docId != null) {
				metadatosDocumento.put(NOMBRE_METADATO_DOC_ID, 
						identificadorNormDocExpediente(organo, ((docFechaCaptura==null)?(new Date()):docFechaCaptura), docId));
			}
		} else { // Documento Simple
			metadatosDocumento.put(NOMBRE_METADATO_DOC_CATEGORIA, VALOR_METADATO_DOC_CATEGORIA_DOCSIMPLE);
			if (docId != null) {
				metadatosDocumento.put(NOMBRE_METADATO_DOC_ID, 
						identificadorNormDocSimple(organo, ((docFechaCaptura==null)?(new Date()):docFechaCaptura), docId));
			}			
		}
		metadatosDocumento.put(NOMBRE_METADATO_DOC_VERSION_NTI, VALOR_METADATO_DOC_VERSION_NTI);
		if (docFechaCaptura != null) {
			metadatosDocumento.put(NOMBRE_METADATO_DOC_FECHA_CAPTURA, docFechaCaptura);
		}
		
		metadatosDocumento.put(NOMBRE_METADATO_DOC_ORIGEN, origenDocumento(regNumero, docNombre));
		metadatosDocumento.put(NOMBRE_METADATO_DOC_ESTADO_ELABORACION, VALOR_METADATO_DOC_ESTADO_ELABORACION_ORIGINAL);
		metadatosDocumento.put(NOMBRE_METADATO_DOC_TIPO_COPIA, VALOR_METADATO_DOC_TIPO_COPIA);
		metadatosDocumento.put(NOMBRE_METADATO_DOC_ID_DOC_ORIGEN, VALOR_METADATO_DOC_ID_DOC_ORIGEN);
		metadatosDocumento.put(NOMBRE_METADATO_DOC_NOMBRE_FORMATO, nombreFormatoFichero(docFormato));	
		metadatosDocumento.put(NOMBRE_METADATO_DOC_SOPORTE_ORIGEN, soporteOrigenDocumento(regNumero, docNombre, esRegElectronico));
		metadatosDocumento.put(NOMBRE_METADATO_DOC_TIPO_DOCUMENTAL, docTipo);
		try {
			metadatosDocumento.put(NOMBRE_METADATO_DOC_TIPO_FIRMA, new String(VALOR_METADATO_DOC_TIPO_FIRMA_ELECT_AVANZ_CERT.getBytes("ISO-8859-15"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			metadatosDocumento.put(NOMBRE_METADATO_DOC_TIPO_FIRMA, VALOR_METADATO_DOC_TIPO_FIRMA_ELECT_AVANZ_CERT);
		}
		metadatosDocumento.put(NOMBRE_METADATO_DOC_FORMATO_FIRMA, VALOR_METADATO_DOC_FORMATO_FIRMA_XADESE);
		metadatosDocumento.put(NOMBRE_METADATO_DOC_ROL_FIRMA, VALOR_METADATO_DOC_ROL_FIRMA);
		metadatosDocumento.put(NOMBRE_METADATO_DOC_ALGORITMO_FIRMA, VALOR_METADATO_DOC_ALGORITMO_FIRMA);
		metadatosDocumento.put(NOMBRE_METADATO_DOC_VALOR_CSV, VALOR_METADATO_DOC_VALOR_CSV);
		metadatosDocumento.put(NOMBRE_METADATO_DOC_DEF_GENERACION_CSV, VALOR_METADATO_DOC_DEF_GENERACION_CSV);
		
		return metadatosDocumento;
	}	
	
	private static HashMap<String, Object> generarMetadatosNoObligatoriosDocumento(HashMap<String, Object> metadatosDocumento, Date docFechaElaboracion, 
			Date docFechaFin, String regNumero, String docObservaciones, String docFirmaXML){

		metadatosDocumento.put(NOMBRE_METADATO_DOC_FECHA_DOC, docFechaElaboracion);
		if (docFechaFin != null) {
			metadatosDocumento.put(NOMBRE_METADATO_DOC_FECHA_FIN, docFechaFin);
		}
		if (regNumero != null) {
			metadatosDocumento.put(NOMBRE_METADATO_DOC_REGISTRO, regNumero);
		}
		if (docFirmaXML != null) {
			metadatosDocumento.put(NOMBRE_METADATO_DOC_FIRMAS, docFirmaXML);
		}
		metadatosDocumento.put(NOMBRE_METADATO_DOC_TIMESTAMP, VALOR_METADATO_DOC_TIMESTAMP);
		metadatosDocumento.put(NOMBRE_METADATO_DOC_OBSERVACIONES, docObservaciones);
		
		return metadatosDocumento;
	}
	
	public static HashMap<String, Object> generarMetadatoFechaFinDocumento(Date docFechaFin){
		HashMap<String, Object> metadatosDocumento = new HashMap<String, Object>();

		if (docFechaFin != null) {
			metadatosDocumento.put(NOMBRE_METADATO_DOC_FECHA_FIN, docFechaFin);
		}
		
		return metadatosDocumento;
	}
	
	public static HashMap<String, Object> generarMetadatosActualizacionDocumento(Date docFechaCaptura, String expId, String docId) {
		HashMap<String, Object> metadatosDocumento = new HashMap<String, Object>();

		if (docFechaCaptura != null) {
			metadatosDocumento.put(NOMBRE_METADATO_DOC_FECHA_CAPTURA, docFechaCaptura);
		}
		
		String organo = VALOR_METADATO_EXP_ORGANO;
		if(expId != null && !expId.trim().equalsIgnoreCase("")) { //Si tiene un expediente definido
			metadatosDocumento.put(NOMBRE_METADATO_DOC_ID, identificadorNormDocExpediente(organo, docFechaCaptura, docId));
		} else{//Documento Simple
			metadatosDocumento.put(NOMBRE_METADATO_DOC_ID, identificadorNormDocSimple(organo, docFechaCaptura, docId));
		}		
		
		return metadatosDocumento;
	}
	
	public static HashMap<String, Object> generarMetadatoFirmaUnicaDocumento(String docFirmaXML){
		HashMap<String, Object> metadatosDocumento = new HashMap<String, Object>();
		String[] firmaMetadato = new String[1];
		firmaMetadato[0] = docFirmaXML; //new String(docFirmaXML);					
		metadatosDocumento.put(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS, firmaMetadato);
		
		return metadatosDocumento;
	}
	
	public static HashMap<String, Object> generarMetadatoFirmasDocumento(String docFirmaXMLActual, String docFirmaXMLNueva){
		HashMap<String, Object> metadatosDocumento = new HashMap<String, Object>();
		String firmas = docFirmaXMLActual + DOC_FIRMAS_SEPARADOR + docFirmaXMLNueva;
		metadatosDocumento.put(NOMBRE_METADATO_DOC_FIRMAS, firmas);
		
		return metadatosDocumento;
	}
	
	public static HashMap<String, Object> generarMetadatoCompulsaDocumento(boolean compulsa){
		HashMap<String, Object> metadatosDocumento = new HashMap<String, Object>();
		if (compulsa) {
			metadatosDocumento.put(NOMBRE_METADATO_DOC_ESTADO_ELABORACION, VALOR_METADATO_DOC_ESTADO_ELABORACION_COPIA);
			metadatosDocumento.put(NOMBRE_METADATO_DOC_TIPO_COPIA, VALOR_METADATO_DOC_TIPO_COPIA_COMPULSADA);
		}
		return metadatosDocumento;
	}
	
	public static HashMap<String, Object> generarMetadatosAsiento(String expRelacionadoUri, String expRelacionadoRuta){
		HashMap<String, Object> metadatosAsiento = new HashMap<String, Object>();
		
		metadatosAsiento.put(NOMBRE_METADATO_REL_EXPEDIENTE_RELACIONADO, expRelacionadoUri);
		metadatosAsiento.put(NOMBRE_METADATO_REL_CARPETA_EXPEDIENTE, expRelacionadoRuta);
		
		return metadatosAsiento;
	}
	
	public static HashMap<String, Object> generarMetadatosDocAsientoADocExp(Date docFechaCaptura, String docId) {
		HashMap<String, Object> metadatosDocumento = new HashMap<String, Object>();

		String organo = VALOR_METADATO_EXP_ORGANO;
		
		metadatosDocumento.put(NOMBRE_METADATO_DOC_CATEGORIA, VALOR_METADATO_DOC_CATEGORIA_EXPEDIENTE);
		metadatosDocumento.put(NOMBRE_METADATO_DOC_ID, identificadorNormDocExpediente(organo, docFechaCaptura, docId));
		
		return metadatosDocumento;		
	}
	
	public static HashMap<String, Object> generarMetadatosDocExpADocAsiento(String regNumero) {
		HashMap<String, Object> metadatosDocumento = new HashMap<String, Object>();

		metadatosDocumento.put(NOMBRE_METADATO_DOC_REGISTRO, regNumero);
		
		return metadatosDocumento;		
	}	
	
	public static HashMap<String, Object> generarMetadatoNumRegistro(String regNumero){
		HashMap<String, Object> metadatosDocumento = new HashMap<String, Object>();
		if (regNumero != null) {
			metadatosDocumento.put(NOMBRE_METADATO_DOC_REGISTRO, regNumero);
		}
		return metadatosDocumento;
	}
	
	/**
	 * Metodo encargado de configurar el listado de nombres de las propiedades a mostrar en la consulta
	 * de metadatos de un expediente. 
	 * 
	 * @return
	 */	
	public static Set<String> getNombresMetadatosExpConsulta() {
		Set<String> nombresMetadatosExpConsulta = new HashSet<String>();
		
		nombresMetadatosExpConsulta.add(NOMBRE_METADATO_EXP_USUARIO_INICIA);
		nombresMetadatosExpConsulta.add(NOMBRE_METADATO_EXP_FECHA_NOTIFICACION_RESOLUCION);
		nombresMetadatosExpConsulta.add(NOMBRE_METADATO_EXP_FECHA_EFECTIVA_NOTIFICACION_RESOLUCION);
		//nombresMetadatosExpConsulta.add(NOMBRE_METADATO_EXP_FECHA_FIN);
		
		return nombresMetadatosExpConsulta;
	}
	
	/**
	 * Metodo encargado de configurar el listado de nombres de las propiedades a mostrar en la consulta
	 * de metadatos de un expediente. 
	 * 
	 * @return
	 */	
	public static Set<String> getNombresMetadatosDocConsulta() {
		Set<String> nombresMetadatosDocConsulta = new HashSet<String>();
		
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_NOMBRE);
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_DESCRIPCION);
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_AUTOR);		
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_DOC_ORIGEN);
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_DOC_CATEGORIA);
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_DOC_TIPO_DOCUMENTAL);
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_DOC_FECHA_DOC);		
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_DOC_FECHA_CAPTURA);
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_DOC_FECHA_FIN);
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_DOC_REGISTRO);
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_DOC_OBSERVACIONES);		
		
		return nombresMetadatosDocConsulta;
	}
	
	public static Set<String> getNombresMetadatosDocEdicion() {
		Set<String> nombresMetadatosDocConsulta = new HashSet<String>();
		
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_DOC_FECHA_FIN);
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_DOC_FECHA_DOC);
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_DESCRIPCION);
		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_DOC_TIPO_DOCUMENTAL);
//		nombresMetadatosDocConsulta.add(NOMBRE_METADATO_AUTOR);		
		
		return nombresMetadatosDocConsulta;
	}
	
	private static String identificadorExpediente(String expId, Date expFechaInicio, String organo) {
		SimpleDateFormat anioFormat = new SimpleDateFormat("yyyy");
		String anio = anioFormat.format(expFechaInicio);
		String idExpediente = "ES_" + organo + "_" + anio + "_EXP_" + expId;

		return idExpediente;
	}
	
	/**
	 * Genera el identificador normalizado de un documento simple
	 * // ES_<Órgano>_<AAAA>_<ID_específico>    (documento simple)
	 * @param organo
	 * @param agno
	 * @param idEspecificoExped
	 * @return
	 */
	private static String identificadorNormDocSimple(String organo, Date fecha, String docName) {
		SimpleDateFormat anioFormat = new SimpleDateFormat("yyyy");
		String anio = anioFormat.format(fecha);
		StringBuilder sb = new StringBuilder();
		String idEspecifico = docName;
		sb.append("ES_").append(organo).append("_").append(anio).append("_").append(idEspecifico);
		return sb.toString();
	}
	
	/**
	 * Genera el identificador normalizado para un documento de un expediente.
	 * //ES_<Órgano>_<AAAA>_EXP_<ID_específico>    (documento de expediente)
	 * @param organo
	 * @param agno
	 * @param idEspecificoExped
	 * @return
	 */
	private static String identificadorNormDocExpediente(String organo, Date fecha, String docName) {
		SimpleDateFormat anioFormat = new SimpleDateFormat("yyyy");
		String anio = anioFormat.format(fecha);
		StringBuilder sb = new StringBuilder();
		String idEspecifico = docName;
		sb.append("ES_").append(organo).append("_").append(anio).append("_EXP_").append(idEspecifico);
		return sb.toString();
	}
	
	private static String origenDocumento(String regNumero, String docNombre) {
		boolean esDocCiudadano = false; 
		if (regNumero != null && regNumero.startsWith("E")) { // es un asiento de entrada
			String regNumeroFormat = regNumero.replaceAll("\\-", "_").replaceAll("\\/", "_");
			if (docNombre != null && !docNombre.startsWith("Justificante_" + regNumeroFormat)) {
				esDocCiudadano = true;
			}
		}
		if (esDocCiudadano) {
			return VALOR_METADATO_DOC_ORIGEN_CIUDADANO;
		} else {
			try {
				return new String(VALOR_METADATO_DOC_ORIGEN_ADMIN.getBytes("ISO-8859-15"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return VALOR_METADATO_DOC_ORIGEN_ADMIN;
			}
		}
	}
	
	private static String clasificacionExpediente(String procId, String organo){
		String clasificacionExp = organo + "_PRO_" + procId;
		
		return clasificacionExp;
	}
	
	/*
	 * Retorna cerrado cuando no est� definido fechaFin
	 */
	private static String estadoFechaFin(Date fechaFin){
		if (fechaFin == null){
			return VALOR_METADATO_EXP_ESTADO_ABIERTO;
		}else{
			return VALOR_METADATO_EXP_ESTADO_CERRADO;
		}
	}
	
	private static String nombreFormatoFichero(String formato) {
		if (formato != null) {
			if (formato.equalsIgnoreCase("odt") || formato.equalsIgnoreCase("ods") 
					|| formato.equalsIgnoreCase("odp") || formato.equalsIgnoreCase("odg")) {
				return VALOR_METADATO_DOC_NOMBRE_FORMATO_ISOIEC;
			} else if (formato.equalsIgnoreCase("xlsx") || formato.equalsIgnoreCase("docx") || formato.equalsIgnoreCase("pptx")) {
				return VALOR_METADATO_DOC_NOMBRE_FORMATO_SOXML;
			} else if (formato.equalsIgnoreCase("pdf")) {
				return VALOR_METADATO_DOC_NOMBRE_FORMATO_PDF;
			} else if (formato.equalsIgnoreCase("png")) {
				return VALOR_METADATO_DOC_NOMBRE_FORMATO_PNG;
			} else if (formato.equalsIgnoreCase("jpg") || formato.equalsIgnoreCase("jpeg")) {
				return VALOR_METADATO_DOC_NOMBRE_FORMATO_JPEG;
			} else if (formato.equalsIgnoreCase("xml")) {
				return VALOR_METADATO_DOC_NOMBRE_FORMATO_XML;
			} else if (formato.equalsIgnoreCase("txt")) {
				return VALOR_METADATO_DOC_NOMBRE_FORMATO_TXT;
			} else {
				return (formato.toUpperCase() + " (N/A)");
			}
		}
		
		return "";
	}
	
	private static String soporteOrigenDocumento(String regNumero, String docNombre, boolean esRegElectronico) {
		boolean esDigitalizado = false; 
		if (regNumero != null && regNumero.startsWith("E")) { // es un asiento de entrada
			String regNumeroFormat = regNumero.replaceAll("\\-", "_").replaceAll("\\/", "_");
			if (docNombre != null && !docNombre.startsWith("Justificante_" + regNumeroFormat) && !esRegElectronico) {
				esDigitalizado = true;
			}
		}
		if (esDigitalizado) {
			return VALOR_METADATO_DOC_SOPORTE_ORIGEN_DIGITALIZADO;
		} else {
			return VALOR_METADATO_DOC_SOPORTE_ORIGEN_DIGITAL;
		}
	}
	
	/**
	 * Metodo encargado de formatear como cadena de texto los valores de un conjunto de metadatos.  
	 * Importante para mostrar en el formato 'dd/MM/yyyy' los campos de fecha.
	 * @return
	 */		
	public static HashMap<String, String> formatMetatadosConsulta(HashMap<String, Object> metadatos) {
		HashMap<String, String> metadatosStr = new HashMap<String, String>();
		
		Iterator<Entry<String, Object>> it = metadatos.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> e = it.next();
			String clave = e.getKey();
			Object valor = e.getValue();
			String valorStr = null;
			
			// Si es un campo de fecha convertirlo al formato 'dd/MM/yyyy'
			if ((clave.startsWith("exp_fecha") || clave.startsWith("doc_fecha"))) {
				valorStr = formatMetadatoFechaToString(valor);
			} else {
				valorStr = (String)valor;
			}
			
			metadatosStr.put(ETIQUETAS_METADATOS_MAP.get(clave), valorStr);
		}
		
		return metadatosStr;
	}
	
	public static String formatFechaToString(String valor) {
		Date fecha = ISO8601DateFormat.parse(valor);
		valor = DateUtils.getDate(fecha, DateUtils.dateFormat);
		return valor;
	}
	
	public static Date formatFechaToDate(String docFechaCapturaStr) {
		Date docFechaCaptura = new Date();
		
		if (docFechaCapturaStr != null) {
			docFechaCaptura = ISO8601DateFormat.parse(docFechaCapturaStr);			
		}
		return docFechaCaptura;
	}	
	
	public static String formatDateToString(Date fecha) {
		if (fecha != null) {
			return ISO8601DateFormat.format(fecha);
		}
		return null;
	}
	
	public static Date formatMetadatoFechaToDate(Object fechaObj) {
		Date fecha = null;
		if (fechaObj instanceof GregorianCalendar) {
			GregorianCalendar fechaGC = (GregorianCalendar)fechaObj;
			fecha = DateUtils.gregorianCalendarToDate(fechaGC);
		} else if (fechaObj instanceof String) {
			String fechaCreacionStr = (String)fechaObj;
			fecha = formatFechaToDate(fechaCreacionStr);
		}
		return fecha;
	}
	
	public static String formatMetadatoFechaToString(Object fechaObj) {
		String fechaStr = null;
		Date fecha = null;
		if (fechaObj instanceof GregorianCalendar) {
			GregorianCalendar fechaGC = (GregorianCalendar)fechaObj;
			fecha = DateUtils.gregorianCalendarToDate(fechaGC);

		} else if (fechaObj instanceof String) {
			String fechaCreacionStr = (String)fechaObj;
			fecha = formatFechaToDate(fechaCreacionStr);
		}
		if (fecha != null) {
			fechaStr = DateUtils.getDate(fecha, DateUtils.dateFormat);
		}
		return fechaStr;
	}
	
	public static String formatMetadatoFechaToString(Object fechaObj, String format) {
		String fechaStr = null;
		Date fecha = null;
		if (fechaObj instanceof GregorianCalendar) {
			GregorianCalendar fechaGC = (GregorianCalendar)fechaObj;
			fecha = DateUtils.gregorianCalendarToDate(fechaGC);

		} else if (fechaObj instanceof String) {
			String fechaCreacionStr = (String)fechaObj;
			fecha = formatFechaToDate(fechaCreacionStr);
		}
		if (fecha != null) {
			fechaStr = DateUtils.getDate(fecha, format);
		}
		return fechaStr;
	}
	
	@SuppressWarnings("unchecked")
	public static String[] formatMetadatoFirmasToArray(Object firmasObj) {
		String[] firmas = null;
		if (firmasObj instanceof String[]) {
			firmas = (String[])firmasObj;
		} else if (firmasObj instanceof List) {
			List<String> firmasList = (List<String>)firmasObj;
			firmas = new String[firmasList.size()];
			firmas = firmasList.toArray(firmas);
		}
		return firmas;
	}
	
	public static final byte[] getBytesFirma(String[] stringArrayFirma) throws Exception {
		try {
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream;
			try {
				objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			} catch (IOException e) {
				e.printStackTrace();			
				throw e;
			}
			objectOutputStream.writeObject(stringArrayFirma);
			objectOutputStream.flush();
			objectOutputStream.close();
	
			final byte[] byteArray = byteArrayOutputStream.toByteArray();
			return byteArray;
		} catch(Exception e){
			throw e;			
		}
	}
}
