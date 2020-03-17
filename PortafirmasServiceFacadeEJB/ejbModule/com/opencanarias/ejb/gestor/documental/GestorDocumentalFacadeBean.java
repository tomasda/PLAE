package com.opencanarias.ejb.gestor.documental;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBException;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.codec.binary.Base64;
import org.jboss.logging.Logger;
import org.jboss.ws.api.annotation.WebContext;

import com.opencan.office.beans.SignatureInfo;
import com.opencan.office.exceptions.OfficeServiceException;
import com.opencan.repository.exceptions.RepositoryException;
import com.opencan.repository.implementations.alfresco.AlfRepositoryService;
import com.opencan.repository.interfaces.IRepositoryService;
import com.opencan.repository.objects.Parameters;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.exceptions.ExceptionBean;
import com.opencanarias.exceptions.GenericFacadeException;
import com.opencanarias.exceptions.GestorDocumentalFacadeException;
//import com.opencanarias.facade.gestor.documental.core.GestorDocumentalFacadeCore;
//import com.opencanarias.facade.gestor.documental.core.GestorDocumentalFacadeHelper;
import com.opencanarias.utils.CSVUtils;
import com.opencanarias.utils.ConfigUtils;
import com.opencanarias.utils.Constantes;
import com.opencanarias.utils.DateUtils;
import com.opencanarias.utils.ExceptionUtils;
import com.opencanarias.utils.FileUtils;
import com.opencanarias.utils.FirmaUtils;
import com.opencanarias.utils.MetadataUtils;
import com.opencanarias.utils.StringUtils;

import es.apt.ae.facade.entities.Documento;
import es.apt.ae.facade.entities.Expediente;
//import es.apt.ae.facade.entities.earegistro.Asiento;
//import es.apt.ae.facade.entities.earegistro.DocumentoAsiento;
import es.apt.ae.facade.entities.utils.EntitiesUtils;
import es.apt.ae.facade.repository.dto.DocumentoRepositorioItem;
import es.apt.ae.facade.utils.transform.FacadeMarshallUnMarshallUtils;
import es.apt.ae.facade.ws.params.commons.in.Credenciales;
import es.apt.ae.facade.ws.params.commons.out.Respuesta;
import es.apt.ae.facade.ws.params.gestor.documental.in.DatosNodo;
import es.apt.ae.facade.ws.params.gestor.documental.in.DatosVisualizacionDocumento;
import es.apt.ae.facade.ws.params.gestor.documental.in.ParametrosSGD;
import es.apt.ae.facade.ws.params.gestor.documental.out.DocumentoSalida;
import es.apt.ae.facade.ws.params.gestor.documental.out.NodoPadre;
import es.apt.ae.facade.ws.params.gestor.documental.out.Resultado;

/**
 * @author Open Canarias S.L.
 */

@MTOM(enabled=true)
@Stateless
@WebService(targetNamespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", serviceName = "GestorDocumentalService", portName="GestorDocumentalServicePort", name = "IGestorDocumentalService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
//@WebContext(contextRoot = "/APTAEFacadeWS/GestorDocumental", urlPattern="Service")
@TransactionManagement (TransactionManagementType.CONTAINER)
public class GestorDocumentalFacadeBean extends FacadeBean implements GestorDocumentalFacadeRemote, GestorDocumentalFacadeLocal, Serializable {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(GestorDocumentalFacadeBean.class);

	private static final Float MARCA_AGUA_FIRMA_REGISTRO_ESCALA = 0.95f;

	@PersistenceContext (unitName="facade-pu")
	private EntityManager em;
	
	/**
	 * Constructor por defecto
	 */
	public GestorDocumentalFacadeBean() {
		logger.info("[SRV-GD] Instanciando el EJB/WS para Gestor Documental ...");
	}

	@WebMethod
	@WebResult(name = "resultado")
	public String getVersion() throws GestorDocumentalFacadeException {
		logger.info("[SRV-GD] Ejecutando el metodo <<getVersion>> ...");
		try {
			return ConfigUtils.getParametro(Constantes.PROPERTY_VERSION);
		} catch (GenericFacadeException e) {
			throw new GestorDocumentalFacadeException(e);
		}
	}
	
	/**
	 * @see com.opencanarias.ejb.gestor.documental.IGestorDocumentalFacade#crearNodo(java.lang.String)
	 * @deprecated Change for: crearNodoAltoVolumen
	 */
	@Override
	@WebMethod
	@Deprecated
	public String crearNodo(@WebParam(name = "parametros") String parametros) throws GestorDocumentalFacadeException {
		logger.info("[SRV-GD] Ejecutando el metodo <<crearNodo>> ...");
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();
		FacadeMarshallUnMarshallUtils<ParametrosSGD> unmarshaller = new FacadeMarshallUnMarshallUtils<ParametrosSGD>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		FacadeMarshallUnMarshallUtils<Resultado> marshaller = new FacadeMarshallUnMarshallUtils<Resultado>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		try {
			ParametrosSGD params;
			params = unmarshaller.unmarshall_IN(parametros);
//			return GestorDocumentalFacadeCore.crearNodo(resultado, respuesta, params, em, true);
			return "";
		} catch (JAXBException e) {
			logger.error("[SRV-GD]" + e.getMessage(), e);
			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_PARAMETROS);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_PARAMETROS);
			respuesta.setError(ExceptionUtils.stackTraceToString(e));
			resultado.setRespuesta(respuesta);			
			try {
				return marshaller.marshall_OUT(resultado);
			} catch (JAXBException e1) {
				throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
			}
		}
	}

	@Override
	@WebMethod
	public String crearNodoAltoVolumen(ParametrosSGD parametrosSGD) throws GestorDocumentalFacadeException{
		String result = null;
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();
		FacadeMarshallUnMarshallUtils<Resultado> marshaller = new FacadeMarshallUnMarshallUtils<Resultado>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
//		try {
//			GestorDocumentalFacadeCore.crearNodo(resultado, respuesta, parametrosSGD, null, false);	
//		} catch (GestorDocumentalFacadeException e) {
//			throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);						
//		}
		
		try {
			result = marshaller.marshall_OUT(resultado);
		} catch (JAXBException e) {
			throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
		}
		return result;
	}
	
	@Override
	@WebMethod(exclude=true)
	public Resultado crearNodoEJB(ParametrosSGD parametrosSGD) throws GestorDocumentalFacadeException{
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();
//		try {
//			GestorDocumentalFacadeCore.crearNodo(resultado, respuesta, parametrosSGD, null, false);	
//		} catch (GestorDocumentalFacadeException e) {
//			throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);						
//		}

		return resultado;
	}	
	
	/* (non-Javadoc)
	 * @see com.opencanarias.ejb.gestor.documental.IGestorDocumentalFacade#eliminarNodo(java.lang.String)
	 */
	@Override
	@WebMethod
	public String eliminarNodo(@WebParam(name="parametros") String parametros) throws GestorDocumentalFacadeException {
		logger.info("[SRV-GD] Ejecutando el metodo <<eliminarNodo>> ...");

		FacadeMarshallUnMarshallUtils<ParametrosSGD> unmarshaller = new FacadeMarshallUnMarshallUtils<ParametrosSGD>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		FacadeMarshallUnMarshallUtils<Resultado> marshaller = new FacadeMarshallUnMarshallUtils<Resultado>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);

		ParametrosSGD params = null;
		
		try {
			params = unmarshaller.unmarshall_IN(parametros);
		} catch (JAXBException e) {
			logger.error(e);
			throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
		}
		
		Resultado resultado = eliminarNodoEJB(params);
		
		try {
			return marshaller.marshall_OUT(resultado);
		} catch (JAXBException e) {
			logger.error(e);
			throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
		}
	}
	
	@WebMethod(exclude=true)
	public Resultado eliminarNodoEJB(ParametrosSGD parametrosSGD) throws GestorDocumentalFacadeException {
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();

		try {
			if (parametrosSGD.getDatosNodo() == null || parametrosSGD.getDatosNodo().isEmpty()) {
				respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_PARAMETROS);
				respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_PARAMETROS);
				resultado.setRespuesta(respuesta);			
				return resultado;
			}
	
//			GestorDocumentalFacadeHelper.eliminarNodoAlfService(parametrosSGD.getCredenciales(), parametrosSGD.getDatosNodo());
			
			respuesta.setCodigo(Constantes.FWS_GD_COD_SUCCESSFUL);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_SUCCESSFUL);
			resultado.setRespuesta(respuesta);
//		} catch (RepositoryException e) {
//			logger.error("[SRV-GD]" + e.getMessage(), e);
//			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_REPOSITORIO);
//			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_REPOSITORIO);
//			respuesta.setError(ExceptionUtils.stackTraceToString(e));
//			resultado.setRespuesta(respuesta);			
		} catch (Exception e) {
			logger.error("[SRV-GD]" + e.getMessage(), e);
			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_GENERICO);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_GENERICO);
			respuesta.setError(ExceptionUtils.stackTraceToString(e));
			resultado.setRespuesta(respuesta);			
		}
		
		return resultado;
	}
	
	/* (non-Javadoc)
	 * @see com.opencanarias.ejb.gestor.documental.IGestorDocumentalFacade#actualizarNodo(java.lang.String)
	 */
	@Override
	@WebMethod
	public String actualizarNodo(@WebParam(name="parametros") String parametros) throws GestorDocumentalFacadeException {
		logger.info("[SRV-GD] Ejecutando el metodo <<actualizarNodo>> ...");
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();
		FacadeMarshallUnMarshallUtils<ParametrosSGD> unmarshaller = new FacadeMarshallUnMarshallUtils<ParametrosSGD>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		FacadeMarshallUnMarshallUtils<Resultado> marshaller = new FacadeMarshallUnMarshallUtils<Resultado>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		
		try {
			ParametrosSGD params = unmarshaller.unmarshall_IN(parametros);
			if (params.getDatosNodo() == null || params.getDatosNodo().size() == 0) {
				respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_PARAMETROS);
				respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_PARAMETROS);
				resultado.setRespuesta(respuesta);			
				return marshaller.marshall_OUT(resultado);
			}
//			GestorDocumentalFacadeCore.actualizarNodo(params, resultado, respuesta, em);
			return marshaller.marshall_OUT(resultado);

		} catch (JAXBException e) {
			logger.error("[SRV-GD]" + e.getMessage(), e);
			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_PARAMETROS);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_PARAMETROS);
			respuesta.setError(ExceptionUtils.stackTraceToString(e));
			resultado.setRespuesta(respuesta);			
			try {
				return marshaller.marshall_OUT(resultado);
			} catch (JAXBException e1) {
				throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
			}
		} catch (Exception e) {
			logger.error("[SRV-GD]" + e.getMessage(), e);
			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_GENERICO);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_GENERICO);
			respuesta.setError(ExceptionUtils.stackTraceToString(e));
			resultado.setRespuesta(respuesta);
			try {
				return marshaller.marshall_OUT(resultado);
			} catch (JAXBException e1) {
				throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
			}
		}
	}

	@Override
	@WebMethod
	public String actualizarNodoAltoVolumen(ParametrosSGD params) throws GestorDocumentalFacadeException{
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();
		FacadeMarshallUnMarshallUtils<Resultado> marshaller = new FacadeMarshallUnMarshallUtils<Resultado>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		try{
			if (params.getDatosNodo() == null || params.getDatosNodo().size() == 0) {
				respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_PARAMETROS);
				respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_PARAMETROS);
				resultado.setRespuesta(respuesta);			
				return marshaller.marshall_OUT(resultado);
			}
//			GestorDocumentalFacadeCore.actualizarNodo(params, resultado, respuesta, em);
			return marshaller.marshall_OUT(resultado);
		} catch (JAXBException e) {
			logger.error("[SRV-GD]" + e.getMessage(), e);
			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_PARAMETROS);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_PARAMETROS);
			respuesta.setError(ExceptionUtils.stackTraceToString(e));
			resultado.setRespuesta(respuesta);			
			try {
				return marshaller.marshall_OUT(resultado);
			} catch (JAXBException e1) {
				throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
			}
		} catch (Exception e) {
			logger.error("[SRV-GD]" + e.getMessage(), e);
			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_GENERICO);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_GENERICO);
			respuesta.setError(ExceptionUtils.stackTraceToString(e));
			resultado.setRespuesta(respuesta);
			try {
				return marshaller.marshall_OUT(resultado);
			} catch (JAXBException e1) {
				throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.opencanarias.ejb.gestor.documental.IGestorDocumentalFacade#consultarNodo(java.lang.String)
	 */
	@Override
	@WebMethod
	public String consultarNodo(@WebParam(name = "parametros") String parametros) throws GestorDocumentalFacadeException {
		logger.info("[SRV-GD] Ejecutando el metodo <<consultarNodo>> ...");
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();
		FacadeMarshallUnMarshallUtils<ParametrosSGD> unmarshaller = new FacadeMarshallUnMarshallUtils<ParametrosSGD>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		FacadeMarshallUnMarshallUtils<Resultado> marshaller = new FacadeMarshallUnMarshallUtils<Resultado>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		
		try {
			ParametrosSGD params = unmarshaller.unmarshall_IN(parametros);
			if (params.getDatosNodo() == null || params.getDatosNodo().isEmpty()) {
				respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_PARAMETROS);
				respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_PARAMETROS);
				resultado.setRespuesta(respuesta);
				return marshaller.marshall_OUT(resultado);
			}			
//			GestorDocumentalFacadeCore.consultarNodo(params, resultado, respuesta, true);
			return marshaller.marshall_OUT(resultado);
		} catch (JAXBException e) {
			logger.error("[SRV-GD]" + e.getMessage(), e);
			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_PARAMETROS);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_PARAMETROS);
			respuesta.setError(ExceptionUtils.stackTraceToString(e));
			resultado.setRespuesta(respuesta);			
			try {
				return marshaller.marshall_OUT(resultado);
			} catch (JAXBException e1) {
				throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
			}
		}
	}
	
	@Override
	@WebMethod
	public Resultado consultarNodoAltoVolumen(ParametrosSGD params){
		//TODO: create method
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();
//		return GestorDocumentalFacadeCore.consultarNodo(params, resultado, respuesta, false);
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@WebMethod
	public String moverNodo(@WebParam(name="parametros") String parametros) throws GestorDocumentalFacadeException {
		logger.info("[SRV-GD] Ejecutando el metodo <<moverNodo>> ...");
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();
		FacadeMarshallUnMarshallUtils<ParametrosSGD> unmarshaller = new FacadeMarshallUnMarshallUtils<ParametrosSGD>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		FacadeMarshallUnMarshallUtils<Resultado> marshaller = new FacadeMarshallUnMarshallUtils<Resultado>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		
		try {
			NodoPadre nodoPadre = new NodoPadre();
			ParametrosSGD params = unmarshaller.unmarshall_IN(parametros);
			
			// Segmento de validaciones.
			if (params.getDatosNodo() == null || params.getDatosNodo().isEmpty()) {
				respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_PARAMETROS);
				respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_PARAMETROS);
				resultado.setRespuesta(respuesta);			
				return marshaller.marshall_OUT(resultado);
			}			

			// Comprobamos que de las uris pasadas como parametros al metodo, ninguna corresponde a la carpeta de un expediente. En otras palabras, 
			// del conjunto de las uris a mover, quitamos las que pertenezcan a expedientes. 
			List<String> urisAMover = new ArrayList<String>();// TODO GestorDocumentalFacadeHelper.extraerUris(params.getDatosNodo());
			Query q = em.createNamedQuery(Expediente.findByUrisGroup).setParameter("urisGroup", urisAMover);
			List<Expediente> exps = q.getResultList();
			List<String> urisDeExpedientes = new ArrayList<String> ();
			if (exps != null) {
				for (Expediente exp: exps) {
					urisDeExpedientes.add(exp.getUri());
					urisAMover.remove(exp.getUri());
				}
			}

			// Inyeccion de la api de alfresco desarrollada por Open Canarias.
			IRepositoryService ars = AlfRepositoryService.getInstance(RUTA_FICHERO_CONFIGURACION);
			
			Parameters parameters = new Parameters();
//			parameters.setUsername(params.getCredenciales().getUsuario());
//			parameters.setPassword(params.getCredenciales().getPassword());
			
			for (DatosNodo nodo: params.getDatosNodo()) {
				parameters.getUuids().add(nodo.getUri());				
				parameters.setPath(nodo.getRuta());
				ars.moveDocuments(parameters);
			}

			if (urisDeExpedientes != null && !urisDeExpedientes.isEmpty()) {
				respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_RESPUESTA_PARCIAL);
				respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_RESPUESTA_PARCIAL);
				StringBuilder msgError = new StringBuilder("\nLas siguientes uris corresponden a carpetas de expediente y no pueden ser movidas: \n");
				for (String uri: urisDeExpedientes) {
					msgError.append("\t" + uri + "\n");
				}
				respuesta.setError(msgError.toString());
			} else {
				respuesta.setCodigo(Constantes.FWS_GD_COD_SUCCESSFUL);
				respuesta.setDescripcion(Constantes.FWS_GD_DESC_SUCCESSFUL);
			}
			resultado.setRespuesta(respuesta);
			resultado.getNodoPadre().add(nodoPadre);
			return marshaller.marshall_OUT(resultado);
		} catch (RepositoryException e) {
			logger.error("[SRV-GD]" + e.getMessage(), e);
			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_REPOSITORIO);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_REPOSITORIO);
			respuesta.setError(ExceptionUtils.stackTraceToString(e));
			resultado.setRespuesta(respuesta);			
			try {
				return marshaller.marshall_OUT(resultado);
			} catch (JAXBException e1) {
				throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
			}
		} catch (JAXBException e) {
			logger.error("[SRV-GD]" + e.getMessage(), e);
			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_PARAMETROS);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_PARAMETROS);
			respuesta.setError(ExceptionUtils.stackTraceToString(e));
			resultado.setRespuesta(respuesta);			
			try {
				return marshaller.marshall_OUT(resultado);
			} catch (JAXBException e1) {
				throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
			}
		} catch (Exception e) {
			logger.error("[SRV-GD]" + e.getMessage(), e);
			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_GENERICO);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_GENERICO);
			respuesta.setError(ExceptionUtils.stackTraceToString(e));
			resultado.setRespuesta(respuesta);			
			try {
				return marshaller.marshall_OUT(resultado);
			} catch (JAXBException e1) {
				throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
			}
		}
	}
	
	@Override
	@WebMethod
	public String copiarNodo(@WebParam(name="parametros") String parametros) throws GestorDocumentalFacadeException {
		logger.info("[SRV-GD] Ejecutando el metodo <<copiarNodo>> ...");
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();
		FacadeMarshallUnMarshallUtils<ParametrosSGD> unmarshaller = new FacadeMarshallUnMarshallUtils<ParametrosSGD>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		FacadeMarshallUnMarshallUtils<Resultado> marshaller = new FacadeMarshallUnMarshallUtils<Resultado>(
				FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
		
		try {
			NodoPadre nodoPadre = new NodoPadre();
			ParametrosSGD params = unmarshaller.unmarshall_IN(parametros);
			
			// Segmento de validaciones.
			if (params.getDatosNodo() == null || params.getDatosNodo().isEmpty()) {
				respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_PARAMETROS);
				respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_PARAMETROS);
				resultado.setRespuesta(respuesta);			
				return marshaller.marshall_OUT(resultado);
			}			

			// Inyeccion de la api de alfresco desarrollada por Open Canarias.
			IRepositoryService ars = AlfRepositoryService.getInstance(RUTA_FICHERO_CONFIGURACION);
			
			Parameters parameters = new Parameters();
//			parameters.setUsername(params.getCredenciales().getUsuario());
//			parameters.setPassword(params.getCredenciales().getPassword());
			
			List<String> rutasEnviadas = new ArrayList<String>();
			
			String msgError = null;
			for (DatosNodo nodo: params.getDatosNodo()) {
				if(!isPathInList(rutasEnviadas, nodo.getRuta())){//Si no han sido añadidos nodos para esa ruta
					rutasEnviadas.add(nodo.getRuta());
					parameters.getUuids().addAll(getUuidsFromPath(nodo.getRuta(),params.getDatosNodo()));//se añaden los nodos				
					parameters.setPath(nodo.getRuta());
					HashMap<String, String> resultCopy = ars.copyDocuments(parameters);
					msgError = clasificarDocumentosSalida(resultado.getDocumentos(), resultado.getDocumentosError(), resultCopy);//Se rellenan los documentos
				}
			}
			
			if (resultado.getDocumentosError().isEmpty()){
				respuesta.setCodigo(Constantes.FWS_GD_COD_SUCCESSFUL);
				respuesta.setDescripcion(Constantes.FWS_GD_DESC_SUCCESSFUL);
			} else {
				respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_GENERICO);
				respuesta.setDescripcion(StringUtils.isNullOrEmpty(msgError)?Constantes.FWS_GD_DESC_ERR_GENERICO:msgError);
			}
			resultado.setRespuesta(respuesta);
			resultado.getNodoPadre().add(nodoPadre);
			return marshaller.marshall_OUT(resultado);
		} catch (RepositoryException e) {
			logger.error("[SRV-GD]" + e.getMessage(), e);
			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_REPOSITORIO);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_REPOSITORIO);
			respuesta.setError(ExceptionUtils.stackTraceToString(e));
			resultado.setRespuesta(respuesta);			
			try {
				return marshaller.marshall_OUT(resultado);
			} catch (JAXBException e1) {
				throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
			}
		} catch (JAXBException e) {
			logger.error("[SRV-GD]" + e.getMessage(), e);
			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_PARAMETROS);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_PARAMETROS);
			respuesta.setError(ExceptionUtils.stackTraceToString(e));
			resultado.setRespuesta(respuesta);			
			try {
				return marshaller.marshall_OUT(resultado);
			} catch (JAXBException e1) {
				throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
			}
		} catch (Exception e) {
			logger.error("[SRV-GD]" + e.getMessage(), e);
			respuesta.setCodigo(Constantes.FWS_GD_COD_ERR_GENERICO);
			respuesta.setDescripcion(Constantes.FWS_GD_DESC_ERR_GENERICO);
			respuesta.setError(ExceptionUtils.stackTraceToString(e));
			resultado.setRespuesta(respuesta);			
			try {
				return marshaller.marshall_OUT(resultado);
			} catch (JAXBException e1) {
				throw new GestorDocumentalFacadeException(Constantes.FWS_GD_COD_ERR_GENERICO);
			}
		}
	}
	
	private boolean isPathInList(List<String> listPath, String path){
		for (String pathAux: listPath){
			if(pathAux.equals(path)){
				return true;
			}
		}
		return false;
	}
	
	private List<String> getUuidsFromPath(String path, List<DatosNodo> listDatosNodo){
		List<String> listUuids = new ArrayList<String>();
		for (DatosNodo datosNodo : listDatosNodo){
			if(datosNodo.getRuta().equals(path)){
				listUuids.add(datosNodo.getUri());
			}
		}
		
		return listUuids;
	}
	
	private String clasificarDocumentosSalida(List<DocumentoSalida> documentosOk, List<DocumentoSalida> documentosError, HashMap<String, String> documentos){
		
		String msgError = "";
		
		for(String uriOrigen : documentos.keySet()){
			String respuesta = documentos.get(uriOrigen);
			DocumentoSalida documentoSalida = new DocumentoSalida();
			documentoSalida.setNombre(uriOrigen);
			if (respuesta != null && !respuesta.isEmpty() && !respuesta.startsWith("ERROR:")) {
				documentoSalida.setUri(respuesta);
				documentosOk.add(documentoSalida);
			} else {
				documentosError.add(documentoSalida);
				if (respuesta.startsWith("ERROR:")) {
					msgError += respuesta.substring(6, respuesta.length());
				}
			}
		}
		return msgError;
	}
	
	@Override
	@WebMethod
	public String obtenerVisualizacionDocumento(@WebParam(name="parametros") String parametros) throws GestorDocumentalFacadeException {
		Resultado resultado = obtenerVisualizacionDocumentoComun(parametros, false);
		try {
			return new FacadeMarshallUnMarshallUtils<Resultado>(FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, 
					FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE).marshall_OUT(resultado);
		} catch (JAXBException e) {
			logger.error(e);
			throw new GestorDocumentalFacadeException("Ha ocurrido un error al intentar parsear los parametros de salida. [" + e.getMessage() + "]");
		}		
	}
	
	@Override
	@WebMethod	
	public Resultado obtenerVisualizacionDocumentoAltoVolumen(String parametros) throws GestorDocumentalFacadeException {
		Resultado resultado = obtenerVisualizacionDocumentoComun(parametros, true);
		return resultado;
	}

	private Resultado obtenerVisualizacionDocumentoComun(String parametros, boolean altoVolumen) throws GestorDocumentalFacadeException {
		ParametrosSGD params = null;

		try {
			params = (new FacadeMarshallUnMarshallUtils<ParametrosSGD>(FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE, 
					FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE)).unmarshall_IN(parametros);
		} catch (JAXBException e) {
			logger.error(e);
			throw new GestorDocumentalFacadeException("Ha ocurrido un error al intentar parsear los parametros de entrada. [" + e.getMessage() + "]");
		}
		
		Resultado resultado = obtenerVisualizacionDocumentoEJB(params, altoVolumen);
		
		return resultado;
	}
	
	@WebMethod(exclude = true)
	@Override
	public Resultado obtenerVisualizacionDocumentoEJB(ParametrosSGD params, boolean altoVolumen) throws GestorDocumentalFacadeException {
		Respuesta respuesta = null;
		List<DocumentoRepositorioItem> documentosRepositorioInfo = null;
		List<DocumentoSalida> documentosSalida = null;
		ExceptionBean exceptionBean = null;		

		try {
			if (params == null) {
				throw new GenericFacadeException(Constantes.FWS_GD_COD_ERR_PARAMETROS, 
						Constantes.FWS_GD_ERR_PARAMETROS, Constantes.FWS_EXP_DESC_ERROR_0001);
			}

			Credenciales credenciales = params.getCredenciales();
			// Validar que se han especificado credenciales.
			if (credenciales == null || credenciales.getUsuario() == null || credenciales.getUsuario().trim().equals("") ||
					credenciales.getPassword() == null || credenciales.getPassword().trim().equals("")) {
				throw new GenericFacadeException(Constantes.FWS_GD_COD_ERR_PARAMETROS, 
						Constantes.FWS_GD_ERR_PARAMETROS, Constantes.FWS_DESC_ERROR_CREDENCIALES);
			}	
			
			List<DatosVisualizacionDocumento> listaDatosDocumentos = params.getDatosVisualizacionDoc();
			// Se comprueba que existen los documentos en el repositorio
			documentosRepositorioInfo = null;//TODO GestorDocumentalFacadeHelper.comprobarExistenciaDocumentosSGD(credenciales.getUsuario(), credenciales.getPassword(), listaDatosDocumentos);
		
			if (documentosRepositorioInfo != null && !documentosRepositorioInfo.isEmpty()) {
				List<String> listaUuids = new ArrayList<String>();
				HashMap<String, Float> escalasMap = new HashMap<String, Float>();
				HashMap<String, Boolean> invertirMap = new HashMap<String, Boolean>();
				HashMap<String, Integer> rotacionesMap = new HashMap<String, Integer>();
				HashMap<String, String> rutasUrisMap = new HashMap<String, String>();
				for (DocumentoRepositorioItem docRepoInfo:documentosRepositorioInfo) {
					String uriDoc = docRepoInfo.getUri();
					listaUuids.add(uriDoc);
					escalasMap.put(uriDoc, docRepoInfo.getEscala());
					invertirMap.put(uriDoc, docRepoInfo.getInvertirMarcaAgua());
					rotacionesMap.put(uriDoc, docRepoInfo.getRotacion());
					rutasUrisMap.put(uriDoc, docRepoInfo.getRutaCIFS());
				}
				
				// Descargar el contenido de los documentos.
				IRepositoryService ars = AlfRepositoryService.getInstance(RUTA_FICHERO_CONFIGURACION);			
				Parameters parameters = new Parameters();
				parameters.setUsername(credenciales.getUsuario());
				parameters.setPassword(credenciales.getPassword());
				parameters.setUuids(listaUuids);
				Map<String, byte[]> contents = ars.downloadDocuments(parameters);
				if (contents != null) {
					Iterator<Entry<String, byte[]>> it = contents.entrySet().iterator();
//					HashMap<String, Asiento> asientosMap = new HashMap<String, Asiento>();
//					while (it.hasNext()) {
//						Entry<String, byte[]> entry = it.next();
//						String uuid = entry.getKey();
//						byte[] content = entry.getValue();
//						parameters.setUuids(Arrays.asList(new String[]{uuid}));
//						parameters.setPropertiesNames(new HashSet<String>(Arrays.asList(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS, 
//								MetadataUtils.NOMBRE_METADATO_DOC_REGISTRO, MetadataUtils.NOMBRE_METADATO_DOC_ESTADO_ELABORACION,
//								MetadataUtils.NOMBRE_METADATO_DOC_TIPO_COPIA, MetadataUtils.NOMBRE_METADATO_NOMBRE)));
//						HashMap<String, Object> docMetadata = ars.getMetadata(parameters);
//						String numRegistro = null;
//						String csv = null;
//					    SignatureInfo[] firmantesInfoArray = null;
//					    String docNombre = null;
//					    boolean tieneFirma = false;
//					    boolean tieneRegistro = false;
//					    boolean compulsa = false;
//					    if (docMetadata != null) {
//							numRegistro = (String)docMetadata.get(MetadataUtils.NOMBRE_METADATO_DOC_REGISTRO);
//							if (numRegistro != null && !numRegistro.isEmpty()) {
//								tieneRegistro = true;
//							}
//							String[] firmas = null;
//							if (docMetadata != null && docMetadata.containsKey(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS)) {
//								Object firmasObj = docMetadata.get(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS);
//								firmas = MetadataUtils.formatMetadatoFirmasToArray(firmasObj);
//							}
//						    if (firmas != null && firmas.length > 0) {
//						    	String firma = firmas[firmas.length-1];
//								List<SignatureInfo> firmantesInfo = null;
//								try {
//									firmantesInfo = FirmaUtils.getFirmantes(firmas);
//								} catch (Exception e) {
//									logger.error("No se han podido procesar los firmantes");
//								}
//								if (firmantesInfo != null && !firmantesInfo.isEmpty()) {
//									 tieneFirma = true;
//									 csv = CSVUtils.getDigest(FileUtils.formatNewLines(firma.getBytes()));
//									 firmantesInfoArray = new SignatureInfo[firmantesInfo.size()]; 
//									 firmantesInfoArray = firmantesInfo.toArray(firmantesInfoArray);
//								}
//							    // Comprobar si la firma es compulsa
//							    String estadoElaboracion = (String)(docMetadata.get(MetadataUtils.NOMBRE_METADATO_DOC_ESTADO_ELABORACION));
//							    String tipoCopia = (String)(docMetadata.get(MetadataUtils.NOMBRE_METADATO_DOC_TIPO_COPIA));
//							    if (MetadataUtils.VALOR_METADATO_DOC_ESTADO_ELABORACION_COPIA.equals(estadoElaboracion) &&
//										MetadataUtils.VALOR_METADATO_DOC_TIPO_COPIA_COMPULSADA.equals(tipoCopia)) {					
//									compulsa = true;
//								}						    
//							}
//					    	docNombre = (String)docMetadata.get(MetadataUtils.NOMBRE_METADATO_NOMBRE);
//						}
//					    // Si no tiene registro ni firma se devuelve el documento en claro. En caso contrario se aÃ±ade la marca de agua.
//					    if (tieneRegistro || tieneFirma) {
//					    	String fechaRegistro = null;
//					    	String docExtension = FileUtils.getExtension(docNombre);
//					    	Float escala = escalasMap.get(uuid);
//					    	Boolean invertirMarcaAgua = invertirMap.get(uuid); 
//					    	Integer rotacion = rotacionesMap.get(uuid);
//					    	Asiento asiento = null;
//					    	boolean buscarDocExp = true;
//					    	boolean buscarDocAsiento = true;
//					    	
//					    	if (tieneRegistro) {
//					    		asiento = (asientosMap.get(numRegistro)!=null?asientosMap.get(numRegistro):
//					    			EntitiesUtils.getAsientoByIdentificador(em, numRegistro, null));
//					    		if (asiento != null) {
//					    			fechaRegistro = DateUtils.getDate(asiento.getFechaHora(), DateUtils.dateTimeFormat);
//					    			// Si es un asiento de salida puede que provenga del gestor de expedientes
//					    			buscarDocExp = (asiento.getTipoRegistro().shortValue() == 1 && asiento.getExpediente() != null
//					    					&& !asiento.getExpediente().trim().equals(""));
//					    		}
//					    	}
//					    	if (buscarDocExp) {
//					    		Documento docExpediente = null;
//					    		try {
//					    			docExpediente = EntitiesUtils.existeDocumentoExpedienteByUri(em, uuid);
//					    		} catch (NoResultException e) {
//					    			logger.info("No existe un documento de expediente con uri " + uuid);
//								}
//					    		if (docExpediente != null) {
//						    		if (docExpediente.getRecordType() == null || "exit".equals(docExpediente.getRecordType())) {
//					    				escala = (escala!=null?escala:new Float(docExpediente.getScale()));
//					    				invertirMarcaAgua = (invertirMarcaAgua!=null?invertirMarcaAgua:(docExpediente.getInvertWatermark()==0?false:true));
//					    				rotacion = (rotacion!=null?rotacion:new Integer(docExpediente.getRotation()));
//					    				csv = ((docExpediente.getSignReference() != null && !docExpediente.getSignReference().equals(""))?
//					    						docExpediente.getSignReference():csv);
//					    				buscarDocAsiento = false;
//						    		}
//					    		}
//					    	}
//					    	if (buscarDocAsiento && asiento != null) {
//					    		DocumentoAsiento docAsiento = null;
//					    		try {
//					    			docAsiento = EntitiesUtils.existeDocumentoAsientoByUri(em, uuid, asiento.getId());
//					    		} catch (NoResultException e) {
//					    			logger.info("No existe un documento de asiento con uri " + uuid);
//								}
//				    			if (docAsiento != null) {
//				    				escala = (escala!=null?escala:docAsiento.getEscala());
//				    				invertirMarcaAgua = (invertirMarcaAgua==null?invertirMarcaAgua:docAsiento.getInvertirMarcaAgua());
//				    				rotacion = (rotacion!=null?rotacion:docAsiento.getRotacion());
//				    				csv = ((docAsiento.getCsv() != null && !docAsiento.getCsv().equals(""))?docAsiento.getCsv():csv);
//				    			}
//				    		}
//					    	if (escala == null) {
//					    		escala = MARCA_AGUA_FIRMA_REGISTRO_ESCALA;
//					    	}
//					    	if (invertirMarcaAgua == null) {
//					    		invertirMarcaAgua = false;
//					    	}
//					    	if (rotacion == null) {
//					    		rotacion = 0;
//					    	}
//					    	content = GestorDocumentalFacadeHelper.incluirMarcaAgua(content, docExtension, firmantesInfoArray, csv, compulsa, numRegistro, 
//					    			fechaRegistro, null, escala, invertirMarcaAgua, rotacion);
//					    	// cambiar la extension a pdf si tiene marca de agua.
//					    	if (!docExtension.equalsIgnoreCase("pdf")) {
//					    		docNombre = FileUtils.changeExtension(docNombre, "pdf");
//					    	}
//					    }
//				    	DocumentoSalida docSalida = new DocumentoSalida();
//				    	docSalida.setUri(uuid);
//				    	docSalida.setRuta(rutasUrisMap.get(uuid));
//				    	if (altoVolumen) {
//				    		docSalida.setContenidoBytes(content);
//				    	} else {
//				    		docSalida.setContenido(Base64.encodeBase64String(content));
//				    	}
//				    	docSalida.setNombre(docNombre);
//				    	if (documentosSalida == null) {
//				    		documentosSalida = new ArrayList<DocumentoSalida>();
//				    	}
//				    	documentosSalida.add(docSalida);
//					}
				}
				respuesta = new Respuesta();
				respuesta.setCodigo(Constantes.FWS_GD_COD_SUCCESSFUL);
				respuesta.setDescripcion(Constantes.FWS_GD_DESC_SUCCESSFUL);				    	
			}
		} catch (RepositoryException e) {
			exceptionBean = new ExceptionBean(Constantes.FWS_GD_COD_ERR_REPOSITORIO, null, 
					Constantes.FWS_GD_DESC_ERR_REPOSITORIO);		
//		} catch (OfficeServiceException e) {
//			exceptionBean = new ExceptionBean(Constantes.FWS_GD_COD_ERR_GENERACION_DOC, null, 
//					Constantes.FWS_GD_DESC_ERR_GENERACION_DOC);				
		} catch (GenericFacadeException e) {
			exceptionBean = new ExceptionBean(e.getCode(), e.getName(), e.getMessage());
		} catch (Exception e) {
			exceptionBean = new ExceptionBean(Constantes.FWS_GD_COD_ERR_GENERICO, null, 
					Constantes.FWS_GD_DESC_ERR_GENERICO);			
		} finally {
			if (exceptionBean != null) {
				respuesta = new Respuesta();
				respuesta.setError(exceptionBean.getNombre());
				respuesta.setCodigo(exceptionBean.getCodigo());
				respuesta.setDescripcion(exceptionBean.getMensaje());
			}
		}
						
		Resultado resultado = new Resultado();
		resultado.setRespuesta(respuesta);
		if (documentosSalida != null) {
			resultado.getDocumentos().addAll(documentosSalida);
		}
		
		return resultado;		
	}
	
	@WebMethod(exclude = true)
	@Override
	public boolean comprobarExisteNodo(String ruta) throws GestorDocumentalFacadeException {
		try {
			IRepositoryService ars = AlfRepositoryService.getInstance(RUTA_FICHERO_CONFIGURACION);
	
			return ars.existsNodeByPath(ruta);
		} catch(RepositoryException e) {
			e.printStackTrace();
			throw new GestorDocumentalFacadeException(e);
		} catch(Exception e) {
			e.printStackTrace();
			throw new GestorDocumentalFacadeException(e);
		}
	}	
	
	@WebMethod(exclude = true)
	@Override
	public List<DocumentoRepositorioItem> consultarNodosInfo(String usuario, String password, List<DocumentoRepositorioItem> documentos) throws GestorDocumentalFacadeException {
//		return GestorDocumentalFacadeHelper.consultarNodosInfo(usuario, password, documentos);
		List<DocumentoRepositorioItem> list = null; //TODO 
		return list;
	}
	
}
