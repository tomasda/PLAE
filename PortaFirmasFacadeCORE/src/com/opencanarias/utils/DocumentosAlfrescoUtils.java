package com.opencanarias.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.jboss.logging.Logger;

import com.opencan.repository.exceptions.RepositoryException;
import com.opencan.repository.implementations.alfresco.AlfRepositoryService;
import com.opencan.repository.interfaces.IRepositoryService;
import com.opencan.repository.objects.Parameters;
//import com.opencanarias.ejb.common.FacadeBean;

import es.apt.ae.facade.dto.FirmasDocumentoInfo;

public class DocumentosAlfrescoUtils {

	private static Logger logger = Logger.getLogger(DocumentosAlfrescoUtils.class);

	
	public static String calcularRutaPlantillasRegES() {
		try {
			String admElectronicaFolder = ConfigUtils.getParametro(Constantes.CONFI_GD_ALFRESCO_ADM_ELECTRONICA_FOLDER);
			return "/" + admElectronicaFolder + "/Plantillas/Comunes/RegistroES";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String calcularRutaPlantillasMarcasAgua() {
		try {
			String admElectronicaFolder = ConfigUtils.getParametro(Constantes.CONFI_GD_ALFRESCO_ADM_ELECTRONICA_FOLDER);
			return "/" + admElectronicaFolder + "/Plantillas/Comunes/MarcasAgua";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	public static String calcularRutaPlantillasActuaciones() {
		try {
			String admElectronicaFolder = ConfigUtils.getParametro(Constantes.CONFI_GD_ALFRESCO_ADM_ELECTRONICA_FOLDER);
			return "/" + admElectronicaFolder + "/Plantillas/Comunes/Actuaciones";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String calcularRutaPlantillasActuaciones(String tipoActuacionId) {
		try {
			String admElectronicaFolder = ConfigUtils.getParametro(Constantes.CONFI_GD_ALFRESCO_ADM_ELECTRONICA_FOLDER);
			return "/" + admElectronicaFolder + "/Plantillas/" + tipoActuacionId;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String calcularRutaPlantillasENotificaciones() {
		try {
			String admElectronicaFolder = ConfigUtils.getParametro(Constantes.CONFI_GD_ALFRESCO_ADM_ELECTRONICA_FOLDER);
			return "/" + admElectronicaFolder + "/Plantillas/Comunes/ENotificaciones";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String rutaCIFS2RutaRepositorio(String rutaCIFS) {
		try {
//			String rutaRepositorio = "/app:company_home";
//			String[] splitRutaCIFS = rutaCIFS.split("/");
//			for (String rutaParte:splitRutaCIFS) {
//				rutaRepositorio += (rutaParte.trim().equals("")?"":("/cm:" + rutaParte));
//			}
//			return rutaRepositorio;
			return rutaCIFS;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String calcularRutaImagenCabecera() {
		try {
			String admElectronicaFolder = ConfigUtils.getParametro(Constantes.CONFI_GD_ALFRESCO_ADM_ELECTRONICA_FOLDER);
			return "/" + admElectronicaFolder + "/Plantillas/Comunes/Imagenes";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String calcularRutaPortafirmas() {
		try {
			String admElectronicaFolder = ConfigUtils.getParametro(Constantes.CONFI_GD_ALFRESCO_ADM_ELECTRONICA_FOLDER);
			return "/" + admElectronicaFolder + "/" + ConfigUtils.getParametro(Constantes.CONFI_GD_ALFRESCO_PORTAFIRMAS_FOLDER);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** Comprueba si un usuario tiene permisos de lecturas sobre la lista de nodos pasados por parametros.
	 * @param uuids Listado de nodos.
	 * @param ars Inyeccion de dependencias de la Api de Alfresco
	 * @throws RepositoryException
	 */
	public static List<String> comprobarPermisosAlfresco(String username, String password, List<String> uuids, int nodeType, int permission, IRepositoryService ars) throws RepositoryException {
		List<String> urisAutorizadas = null;
		Map<String, Boolean> result = null;
		if (password != null && !password.equals("")) {
			result = getPermisosAlfresco(username, password, uuids, permission, ars);
		} else {
			result = getPermisosAlfresco(username, uuids, nodeType, permission, ars);
		}
		
		if (result != null) {
			urisAutorizadas = new ArrayList<String>();
			Iterator<Entry<String, Boolean>> it = result.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Boolean> entry = it.next();
				String uri = entry.getKey();
				Boolean tienePermiso = entry.getValue();
				logger.info("Comprobando permiso de lectura de usuario [" + username + "] para la uri [" + uri + "} ==> " + tienePermiso);
				if (tienePermiso) {
					urisAutorizadas.add(uri);
				}
			}		
		}
		return urisAutorizadas;
	}
	
	public static Map<String, Boolean> getPermisosAlfresco(String username, String password, List<String> uuids, int permission, IRepositoryService ars) throws RepositoryException {
		Parameters params = new Parameters();
		params.setUuids(uuids);
		params.setUsername(username);
		params.setPassword(password);
		params.setPermission(permission);
		return ars.hasPermissions(params);	
	}
	
	public static Map<String, Boolean> getPermisosAlfresco(String username, List<String> uuids, int nodeType, int permission, IRepositoryService ars) throws RepositoryException {
		Parameters params = new Parameters();
		params.setUserConsult(username);		
		params.setUuids(uuids);
		params.setPermission(permission);
		if (nodeType == 0) { // Expedientes
			params.setNodesTypes(new String[] {Parameters.TIPO_EXPEDIENTE});
		} else if (nodeType == 1) { // Documentos
			params.setNodesTypes(new String[] {Parameters.TIPO_CONTENT, Parameters.TIPO_DOCUMENTO, Parameters.TIPO_DOCUMENTO_ASIENTO});
		}
		//return ars.hasPermissionsBD(params);
		return ars.hasPermissions(params);
	}
	
	public static Map<String, String[]> validarDocsFirmados(List<String> uuids) {
		// Recuperar del repositorio el metadato de firma de los documentos.
		Map<String, String[]> firmasExistentes = new HashMap<String, String[]>();
		try {
			IRepositoryService ars = AlfRepositoryService.getInstance();  //TODO  FacadeBean.RUTA_FICHERO_CONFIGURACION);
			Parameters paramsFirma = new Parameters();
			paramsFirma.setUuids(new ArrayList<String>(uuids));
			paramsFirma.setPropertiesNames(new HashSet<String>(Arrays.asList(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS)));
			HashMap<String, Properties> docsMetadata = ars.getNodesMetadata(paramsFirma);
			for (String uuid:uuids) {
				Properties docMetadata = docsMetadata.get(uuid);
				String[] firmas = null;
				if (docMetadata != null && docMetadata.containsKey(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS)) {
					Object firmasObj = docMetadata.get(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS);
					firmas = MetadataUtils.formatMetadatoFirmasToArray(firmasObj);
				} else {
					return null;
				}
				firmasExistentes.put(uuid, firmas);
			}				
		} catch (Exception e) {
			logger.error("Error al recuperar la firma actual de los documentos");
			return null;
		}

		return firmasExistentes;		
	}

	public static HashMap<String, String[]> getFirmasDocsRepositorio(List<String> uuids) {
		// Recuperar del repositorio el metadato de firma de los documentos.
		HashMap<String, String[]> firmasExistentes = new HashMap<String, String[]>();
		try {
			IRepositoryService ars = AlfRepositoryService.getInstance();  //TODO  FacadeBean.RUTA_FICHERO_CONFIGURACION);FacadeBean.RUTA_FICHERO_CONFIGURACION);
			Parameters paramsFirma = new Parameters();
			paramsFirma.setUuids(new ArrayList<String>(uuids));
			paramsFirma.setPropertiesNames(new HashSet<String>(Arrays.asList(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS)));
			HashMap<String, Properties> docsMetadata = ars.getNodesMetadata(paramsFirma);
			for (String uuid:uuids) {
				Properties docMetadata = docsMetadata.get(uuid);
				String[] firmas = null;
				if (docMetadata != null && docMetadata.containsKey(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS)) {
					Object firmasObj = docMetadata.get(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS);
					firmas = MetadataUtils.formatMetadatoFirmasToArray(firmasObj);
				}
				firmasExistentes.put(uuid, firmas);
			}				
		} catch (Exception e) {
			logger.error("Error al recuperar la firma actual de los documentos");
			return null;
		}

		return firmasExistentes;
	}
	
	public static HashMap<String, FirmasDocumentoInfo> getFirmasCompulsasDocsRepositorio(List<String> uuids) {
		// Recuperar del repositorio el metadato de firma de los documentos.
		HashMap<String, FirmasDocumentoInfo> firmasExistentes = new HashMap<String, FirmasDocumentoInfo>();
		try {
			IRepositoryService ars = AlfRepositoryService.getInstance();  //TODO  FacadeBean.RUTA_FICHERO_CONFIGURACION);FacadeBean.RUTA_FICHERO_CONFIGURACION);
			Parameters paramsFirma = new Parameters();
			paramsFirma.setUuids(new ArrayList<String>(uuids));
			paramsFirma.setPropertiesNames(new HashSet<String>(Arrays.asList(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS, 
					MetadataUtils.NOMBRE_METADATO_DOC_ESTADO_ELABORACION,
					MetadataUtils.NOMBRE_METADATO_DOC_TIPO_COPIA)));
			HashMap<String, Properties> docsMetadata = ars.getNodesMetadata(paramsFirma);
			for (String uuid:uuids) {
				Properties docMetadata = docsMetadata.get(uuid);
				String[] firmas = null;
				if (docMetadata != null && docMetadata.containsKey(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS)) {
					Object firmasObj = docMetadata.get(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS);
					firmas = MetadataUtils.formatMetadatoFirmasToArray(firmasObj);
				}
			    // Comprobar si la firma es compulsa
				boolean compulsa = false;
			    String estadoElaboracion = (String)(docMetadata.get(MetadataUtils.NOMBRE_METADATO_DOC_ESTADO_ELABORACION));
			    String tipoCopia = (String)(docMetadata.get(MetadataUtils.NOMBRE_METADATO_DOC_TIPO_COPIA));
			    if (MetadataUtils.VALOR_METADATO_DOC_ESTADO_ELABORACION_COPIA.equals(estadoElaboracion) &&
						MetadataUtils.VALOR_METADATO_DOC_TIPO_COPIA_COMPULSADA.equals(tipoCopia)) {					
					compulsa = true;
				}
				firmasExistentes.put(uuid, new FirmasDocumentoInfo(firmas, compulsa));
			}
		} catch (Exception e) {
			logger.error("Error al recuperar la firma actual de los documentos");
			return null;
		}

		return firmasExistentes;
	}	
	
	public static Map<String, byte[]> getContenidoDocumentos(List<String> uuids) {
		try {
			IRepositoryService ars = AlfRepositoryService.getInstance();  //TODO  FacadeBean.RUTA_FICHERO_CONFIGURACION);FacadeBean.RUTA_FICHERO_CONFIGURACION);
			Parameters parameters = new Parameters();
			parameters.setUuids(uuids);
			Map<String, byte[]> contents = ars.downloadDocuments(parameters);	
			return contents;
		} catch (Exception e) {
			logger.error("Error al recuperar la firma actual de los documentos");
			return null;
		}
	}	
}
