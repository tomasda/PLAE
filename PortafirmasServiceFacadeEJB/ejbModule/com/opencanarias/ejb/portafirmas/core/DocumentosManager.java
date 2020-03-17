package com.opencanarias.ejb.portafirmas.core;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jboss.logging.Logger;

import com.opencanarias.ejb.portafirmas.exceptions.PortafirmasServiceException;
import com.opencanarias.exceptions.GenericFacadeException;
import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.utils.Constantes;
import com.opencanarias.utils.DocumentosAlfrescoUtils;
import com.opencanarias.utils.PerformanceUtils;

import es.apt.ae.facade.portafirmas.dto.RespuestaVisualizacionDocumento;
import es.apt.ae.facade.repository.dto.DocumentoRepositorioItem;
import es.apt.ae.facade.utils.transform.FacadeMarshallUnMarshallUtils;
import es.apt.ae.facade.ws.params.commons.in.Credenciales;
import es.apt.ae.facade.ws.params.commons.in.ListaDocumentos;
import es.apt.ae.facade.ws.params.commons.in.ListaRutas;
import es.apt.ae.facade.ws.params.commons.in.ListaUris;
import es.apt.ae.facade.ws.params.gestor.documental.commons.inout.TipoNodoEnum;
import es.apt.ae.facade.ws.params.gestor.documental.in.DatosNodo;
import es.apt.ae.facade.ws.params.gestor.documental.in.DatosVisualizacionDocumento;
import es.apt.ae.facade.ws.params.gestor.documental.in.ParametrosSGD;
import es.apt.ae.facade.ws.params.gestor.documental.out.DocumentoSalida;
import es.apt.ae.facade.ws.params.gestor.documental.out.Resultado;
import es.apt.ae.facade.ws.params.portafirmas.in.alta.AltaDocsPortafirmas;
import es.apt.ae.facade.ws.params.portafirmas.in.recuperar.RecuperarDocsPortafirmas;

public class DocumentosManager {
	
	private static Logger logger = Logger.getLogger(DocumentosManager.class);
	
	//private IGestorDocumentalFacade gestorDocumental;	
	public DocumentosManager() {};
	
//	public DocumentosManager(IGestorDocumentalFacade gestorDocumental){
//		this.gestorDocumental = gestorDocumental;
//	}
	
	
	public String obtenerDirectorioGestorDocumental(String dniUsuario, String username, String password) throws GenericFacadeException, JAXBException, UnsupportedEncodingException {
		Date personalDir = new Date();
		StringBuilder ruta = new StringBuilder();
		ruta.append(DocumentosAlfrescoUtils.calcularRutaPortafirmas());
		Resultado resultado = null;//TODO crearDirectorio(ruta.toString(), dniUsuario, username, password);
		logger.info("[FACHADA-SRV_PORTAFIRMAS] Tiempo creacion de carpeta del usuario: " + PerformanceUtils.tiempoTranscurridoDesde(personalDir.getTime()));
		// Comprobamos que el nodo ya estaba creado
		if (resultado.getRespuesta().getCodigo().equals("FWS-GD-0006") || resultado.getRespuesta().getCodigo().equals("FWS-GD-0000")) {
			ruta.append("/" + (dniUsuario.matches("\\d.*")?"_":"")  + dniUsuario); // Añadimos la ruta
			// Creamos el nodo referente al año
			Date date = new Date(); // your date
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			String yearString = Integer.toString(year);
			Date fechaDir = new Date();
			resultado = null;//TODO crearDirectorio(ruta.toString(), yearString, username, password);
			if (resultado.getRespuesta().getCodigo().equals("FWS-GD-0006") || resultado.getRespuesta().getCodigo().equals("FWS-GD-0000")) {
				logger.info("[FACHADA-SRV_PORTAFIRMAS] Tiempo creacion de carpeta del año" + PerformanceUtils.tiempoTranscurridoDesde(fechaDir.getTime()));
				ruta.append("/_" + yearString); // Añadimos la ruta
				return ruta.toString();
			}
		}
		return null;
	}

	public String versionarNombreDocumento(String nombreOrigen, int nombreContador) {
		String[] nombreConstructor = nombreOrigen.split("\\.");
		StringBuilder nombreAux = new StringBuilder();
		for (int i = 0; i < nombreConstructor.length; i++) {
			nombreAux.append(nombreConstructor[i]);
			if (i == nombreConstructor.length - 2) {
				nombreAux.append("_" + nombreContador);
			}
			if (i != nombreConstructor.length - 1) {
				nombreAux.append(".");
			}
		}
		return nombreAux.toString();
	}
	
	public RespuestaVisualizacionDocumento obtenerVisualizacionDocumento(String uri, String username, String password) throws PortafirmasFacadeException {
		RespuestaVisualizacionDocumento resultado = new RespuestaVisualizacionDocumento();
		
		ParametrosSGD parametrosSGD = new ParametrosSGD();
		Credenciales credenciales = new Credenciales();
		credenciales.setUsuario(username);
		credenciales.setPassword(password);
		parametrosSGD.setCredenciales(credenciales);
		DatosVisualizacionDocumento datosVisualizacionDocumento = new DatosVisualizacionDocumento();
		datosVisualizacionDocumento.setUri(uri);
		parametrosSGD.getDatosVisualizacionDoc().add(datosVisualizacionDocumento);

		FacadeMarshallUnMarshallUtils<ParametrosSGD> marshallUnmarshallUtilsSGD = new FacadeMarshallUnMarshallUtils<ParametrosSGD>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		String parametros;
		try {
			parametros = marshallUnmarshallUtilsSGD.marshall_IN(parametrosSGD);
			es.apt.ae.facade.ws.params.gestor.documental.out.Resultado resultadoVisualizacion = null;// TODO gestorDocumental.obtenerVisualizacionDocumentoAltoVolumen(parametros);

			if (resultadoVisualizacion.getRespuesta().getCodigo().equals(Constantes.FWS_GD_COD_SUCCESSFUL)) {
				List<DocumentoSalida> docsSalida = resultadoVisualizacion.getDocumentos();
				if (docsSalida != null && docsSalida.size() == 1) {
					DocumentoSalida docSalida = docsSalida.get(0);
					resultado.setNombre(docSalida.getNombre());
					resultado.setContenido(resultadoVisualizacion.getDocumentos().get(0).getContenidoBytes());
					return resultado;
				}
			}
			throw new PortafirmasFacadeException("Error al obtener los resultados correctamente");
		} catch (JAXBException e) {
			throw new PortafirmasFacadeException("Ha ocurrido un problema en la fachada del gestor documental", e);
		}
	}
	
	public Resultado crearDirectorio(String ruta, String nombreNodo, String username, String password) 
			throws JAXBException, UnsupportedEncodingException {
		FacadeMarshallUnMarshallUtils<ParametrosSGD> marshallUtilSGD = new FacadeMarshallUnMarshallUtils<ParametrosSGD>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		ParametrosSGD parametrosSGD = new ParametrosSGD();
		Credenciales credenciales = new Credenciales();
		credenciales.setUsuario(username);
		credenciales.setPassword(password);
		parametrosSGD.setCredenciales(credenciales);
		DatosNodo datosNodo = new DatosNodo();
		datosNodo.setRuta(ruta);
		datosNodo.setNombreNodo(nombreNodo);
		datosNodo.setTipo(TipoNodoEnum.CARPETA);
		parametrosSGD.getDatosNodo().add(datosNodo);
		String resultadoXML = null; //TODO gestorDocumental.crearNodo(marshallUtilSGD.marshall_IN(parametrosSGD));
		FacadeMarshallUnMarshallUtils<Resultado> marshallUtilGSDout = new FacadeMarshallUnMarshallUtils<Resultado>(
						FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		return marshallUtilGSDout.unmarshall_OUT(resultadoXML);
	}
	
	public List<DocumentoRepositorioItem> comprobarExistenciaDocumentosSGD(String username, String password, List<DocumentoRepositorioItem> listaDocumentos) throws PortafirmasServiceException {
		List<DocumentoRepositorioItem> documentosOK = null;//TODO gestorDocumental.consultarNodosInfo(username, password, listaDocumentos);
		if (documentosOK != null && documentosOK.size() == listaDocumentos.size()) {
			logger.info("[FACHADA-SRV_PORTAFIRMAS] Los nodos existen, se prosigue con la ejecucion");
			return documentosOK;
		}
		logger.error("[FACHADA-SRV_PORTAFIRMAS] Se encontro un error y se ha invalidado la comprobacion");
		return null;
	}
	
	public List<DocumentoRepositorioItem>  getListaDocumentosEnviar(AltaDocsPortafirmas altaDocsPortafirmas) throws PortafirmasServiceException {
		PortafirmasServiceException exception = new PortafirmasServiceException();

		List<DocumentoRepositorioItem> listaDocumentosEnviar = new ArrayList<DocumentoRepositorioItem>();
		ListaDocumentos listaDocumentos = altaDocsPortafirmas.getListaDocumentos();

		if (listaDocumentos == null || listaDocumentos.getDocumento() == null || listaDocumentos.getDocumento().isEmpty()) {
			exception.setErrorCode(Constantes.FWS_PF_COD_ERR_PARAMETROS);
			exception.setDescription(Constantes.FWS_PF_DESC_ERR_PARAMETROS);
			throw exception;
		}

		for (es.apt.ae.facade.ws.params.commons.in.Documento documento:listaDocumentos.getDocumento()) {
			DocumentoRepositorioItem docItem = new DocumentoRepositorioItem();
			if (documento.getNombre() == null || documento.getNombre().trim().equals("")) {
				exception.setErrorCode(Constantes.FWS_PF_COD_ERR_PARAMETROS);
				exception.setDescription(Constantes.FWS_PF_DESC_ERR_PARAMETROS);
				throw exception;
			}
			// TODO: resolver problema de que se guarde el nombre aunque se envíe la descripción.
			//docItem.setNombre(documento.getNombre());
			if (documento.getUri() != null && !documento.getUri().trim().isEmpty()) {
				docItem.setUri(documento.getUri());
			} else if (documento.getRuta() != null && !documento.getRuta().trim().isEmpty()) {
				docItem.setRutaCIFS(documento.getRuta());
			} else {
				exception.setErrorCode(Constantes.FWS_PF_COD_ERR_PARAMETROS);
				exception.setDescription(Constantes.FWS_PF_DESC_ERR_PARAMETROS);
				throw exception;
			}
			listaDocumentosEnviar.add(docItem);
		}
		
		return listaDocumentosEnviar;
	}
	
	public List<DocumentoRepositorioItem> getListaDocumentosRecuperar(RecuperarDocsPortafirmas recuperarDocsPortafirmas) throws PortafirmasServiceException {
		List<DocumentoRepositorioItem> listaDocumentosRecuperar = new ArrayList<DocumentoRepositorioItem>();
		ListaUris listaUris = recuperarDocsPortafirmas.getListaUris();
		ListaRutas listaRutas = recuperarDocsPortafirmas.getListaRutas();
		
		if (((listaRutas == null || listaRutas.getRuta() == null || listaRutas.getRuta().isEmpty()) && 
				(listaUris == null || listaUris.getUri() == null || listaUris.getUri().isEmpty())) || 
			(listaRutas != null && listaRutas.getRuta() != null && listaRutas.getRuta().isEmpty()) && 
				 listaUris != null && listaUris.getUri() != null && !listaUris.getUri().isEmpty()) {
			PortafirmasServiceException exception = new PortafirmasServiceException();
			exception.setErrorCode(Constantes.FWS_PF_COD_ERR_PARAMETROS);
			exception.setDescription(Constantes.FWS_PF_DESC_ERR_PARAMETROS);
			throw exception;
		}
		
		if (listaUris != null && listaUris.getUri() != null && !listaUris.getUri().isEmpty()) {
			for (String uri:listaUris.getUri()) {
				DocumentoRepositorioItem docItem = new DocumentoRepositorioItem();
				docItem.setUri(uri);
				listaDocumentosRecuperar.add(docItem);
			}
		}
		
		if (listaRutas != null && listaRutas.getRuta() != null && !listaRutas.getRuta().isEmpty()) {
			for (String ruta:listaRutas.getRuta()) {
				DocumentoRepositorioItem docItem = new DocumentoRepositorioItem();
				docItem.setRutaCIFS(ruta);
				listaDocumentosRecuperar.add(docItem);
			}
		}		
		
		return listaDocumentosRecuperar;
	}
	
}
