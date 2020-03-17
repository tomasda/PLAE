package com.opencanarias.ejb.portafirmas;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.jboss.logging.Logger;
import org.jboss.ws.api.annotation.WebContext;

import com.opencan.repository.custom.model.AlfRepositoryConstants;
import com.opencan.repository.exceptions.RepositoryException;
import com.opencan.repository.implementations.alfresco.AlfRepositoryService;
import com.opencan.repository.interfaces.IRepositoryService;
import com.opencan.repository.objects.Parameters;
import com.opencanarias.api.security.utils.LoginModuleConstants;
import com.opencanarias.apsct.modulo.callback.ejb.CallbackLocal;
import com.opencanarias.apsct.modulo.generic.service.GenericServiceCore;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;
import com.opencanarias.ejb.common.FacadeBean;
//import com.opencanarias.ejb.expedientes.ExpedientesFacadeBeanHelper;
import com.opencanarias.ejb.expedientes.dao.ExpedientesDAOLocal;
import com.opencanarias.ejb.gestor.documental.GestorDocumentalFacadeLocal;
import com.opencanarias.ejb.portafirmas.core.AltaDocumentosCore;
import com.opencanarias.ejb.portafirmas.core.DocumentosManager;
import com.opencanarias.ejb.portafirmas.dao.PortafirmasFacadeDAO;
import com.opencanarias.ejb.portafirmas.dao.PortafirmasFacadeDAOLocal;
import com.opencanarias.ejb.portafirmas.exceptions.PortafirmasServiceException;
import com.opencanarias.ejb.portafirmas.threads.ThreadEnviarNotificacionesPortafirmas;
import com.opencanarias.ejb.portafirmas.utils.PortafirmasFacadeProcesosUtils;
import com.opencanarias.exceptions.GenericFacadeException;
import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.security.authentication.AuthenticationHelper;
import com.opencanarias.utils.ConfigUtils;
import com.opencanarias.utils.Constantes;
import com.opencanarias.utils.DateUtils;
import com.opencanarias.utils.DocumentosAlfrescoUtils;
import com.opencanarias.utils.FileUtils;
import com.opencanarias.utils.MetadataUtils;
import com.opencanarias.utils.PerformanceUtils;

import ee.sk.digidoc.DigiDocException;
import ee.sk.digidoc.SignedDoc;
import es.apt.ae.facade.dto.UsuarioItem;
import es.apt.ae.facade.entities.BackOffice;
import es.apt.ae.facade.entities.CatPropiedadesConfiguracion;
import es.apt.ae.facade.entities.User;
import es.apt.ae.facade.entities.portafirmas.Accion;
import es.apt.ae.facade.entities.portafirmas.Ausencia;
import es.apt.ae.facade.entities.portafirmas.CircuitoEntity;
import es.apt.ae.facade.entities.portafirmas.DataBaseUser;
import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;
import es.apt.ae.facade.entities.portafirmas.EstadoDocumento;
import es.apt.ae.facade.entities.portafirmas.Grupo;
import es.apt.ae.facade.entities.portafirmas.GrupoPersona;
import es.apt.ae.facade.entities.portafirmas.Persona;
import es.apt.ae.facade.entities.portafirmas.ProcesoFirma;
import es.apt.ae.facade.entities.portafirmas.TipoCircuito;
import es.apt.ae.facade.entities.portafirmas.TipoGrupo;
import es.apt.ae.facade.portafirmas.dto.RespuestaVisualizacionDocumento;
import es.apt.ae.facade.repository.dto.DocumentoRepositorioItem;
import es.apt.ae.facade.utils.transform.FacadeMarshallUnMarshallUtils;
import es.apt.ae.facade.ws.params.commons.in.Credenciales;
import es.apt.ae.facade.ws.params.commons.out.DocumentoSalidaError;
import es.apt.ae.facade.ws.params.commons.out.DocumentoSalidaOk;
import es.apt.ae.facade.ws.params.commons.out.Respuesta;
import es.apt.ae.facade.ws.params.expedientes.out.visualizacion.DocumentoVisualizacion;
import es.apt.ae.facade.ws.params.gestor.documental.commons.inout.Metadato;
import es.apt.ae.facade.ws.params.gestor.documental.commons.inout.TipoNodoEnum;
import es.apt.ae.facade.ws.params.gestor.documental.in.DatosNodo;
import es.apt.ae.facade.ws.params.gestor.documental.in.ParametrosSGD;
import es.apt.ae.facade.ws.params.gestor.documental.out.NodoPadre;
import es.apt.ae.facade.ws.params.portafirmas.common.InformacionSolicitante;
import es.apt.ae.facade.ws.params.portafirmas.in.Parametros;
import es.apt.ae.facade.ws.params.portafirmas.in.alta.AltaDocsPortafirmas;
import es.apt.ae.facade.ws.params.portafirmas.in.circuitos.ConsultaCircuitos;
import es.apt.ae.facade.ws.params.portafirmas.in.recuperar.RecuperarDocsPortafirmas;
import es.apt.ae.facade.ws.params.portafirmas.out.Circuito;
import es.apt.ae.facade.ws.params.portafirmas.out.Documento;
import es.apt.ae.facade.ws.params.portafirmas.out.Firmante;
import es.apt.ae.facade.ws.params.portafirmas.out.ListaCircuitos;
import es.apt.ae.facade.ws.params.portafirmas.out.ListaDocumentosAltaOk;
import es.apt.ae.facade.ws.params.portafirmas.out.ListaDocumentosEnviados;
import es.apt.ae.facade.ws.params.portafirmas.out.ListaDocumentosError;
import es.apt.ae.facade.ws.params.portafirmas.out.ListaDocumentosOk;
import es.apt.ae.facade.ws.params.portafirmas.out.ListaDocumentosRecuperados;
import es.apt.ae.facade.ws.params.portafirmas.out.ListaFirmantes;
import es.apt.ae.facade.ws.params.portafirmas.out.ListaGrupos;
import es.apt.ae.facade.ws.params.portafirmas.out.Resultado;

/**
 * @author Open Canarias S.L.
 * 
 */
@Stateless
@WebService(targetNamespace = "http://com.opencanarias/services/facade/portafirmas/v01_00", serviceName = "PortafirmasService", portName = "PortafirmasServicePort", name = "IPortafirmasService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@WebContext(contextRoot = "/APTAEFacadeWS/Portafirmas", urlPattern = "Service")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PortafirmasFacadeBean implements PortafirmasFacadeLocal, PortafirmasFacadeRemote, Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(PortafirmasFacadeBean.class);

	private static final String PENDIENTES = "PENDIENTES";
	private static final String TRAMITADOS = "TRAMITADOS";
	private static final String PERSONAL = "PERSONAL";
	private static final String ENVIADOS = "ENVIADOS";

	private static final String FIRM = "FIRM";
	private static final String VALD = "VALD";
	private static final String RECH = "RECH";
	private static final String BORR = "BORR";
	private static final String ENVD = "ENVD";
	private static final String RECP = "RECP";
	
	private static final String XADES = "XAdES";
	private static final String PADES = "PAdES";
	
	@EJB
	private CallbackLocal srvCbFirma;

	@EJB
	private PortafirmasFacadeDAOLocal portafirmasDao;
	
	@EJB
	private GestorDocumentalFacadeLocal gestorDocumental;	
	
	@EJB
	private ExpedientesDAOLocal expedientesDao;	

	@WebMethod
	@WebResult(name = "resultado")
	public String getVersion() throws PortafirmasFacadeException {
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'getVersion' ...");
		try {
			return ConfigUtils.getParametro(Constantes.PROPERTY_VERSION);
		} catch (GenericFacadeException e) {
			throw new PortafirmasFacadeException(e);
		}
	}

	@WebMethod
	@WebResult(name = "resultado")
	public String enviarPortafirmas(String parametros) throws PortafirmasFacadeException {
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'enviarPortafirmas' ...");
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();

		try {
			Parametros params = (new FacadeMarshallUnMarshallUtils<Parametros>(FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_IN_PACKAGE,
					FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_OUT_PACKAGE)).unmarshall_IN(parametros);
			if (AuthenticationHelper.authenticateUser(params.getCredencial().getUsuario(), params.getCredencial().getPassword())) {
				enviarPortafirmasEJB(resultado, respuesta, params);
			} else {
				respuesta.setCodigo(Constantes.FWS_COD_ERR_AUTHENTICATION);
				respuesta.setDescripcion(Constantes.FWS_DESC_ERR_AUTHENTICATION);
			}
		} catch (JAXBException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_PF_DESC_ERR_PARAMETROS, e);
			respuesta.setCodigo(Constantes.FWS_PF_COD_ERR_PARAMETROS);
			respuesta.setDescripcion(Constantes.FWS_PF_DESC_ERR_PARAMETROS);
			respuesta.setError(e.getMessage());
			resultado.setRespuesta(respuesta);
		} catch (Exception e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_PF_DESC_ERR_GENERICO, e);
			respuesta.setCodigo(Constantes.FWS_PF_COD_ERR_GENERICO);
			respuesta.setDescripcion(Constantes.FWS_PF_DESC_ERR_GENERICO);
			respuesta.setError(e.getMessage());
			resultado.setRespuesta(respuesta);
		}
		try {
			return (new FacadeMarshallUnMarshallUtils<Resultado>(FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_IN_PACKAGE,
					FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_OUT_PACKAGE)).marshall_OUT(resultado);
		} catch (JAXBException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Ha ocurrido un error al intentar parsear los parametros de salida.", e);
			throw new PortafirmasFacadeException("Ha ocurrido un error al intentar parsear los parametros de salida. [" + e.getMessage() + "]");
		}
	}

	@WebMethod
	@WebResult(name = "resultado")
	public String recuperarPortafirmas(String parametros) throws PortafirmasFacadeException {
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'recuperarPortafirmas' ...");
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();

		try {
			Parametros params = (new FacadeMarshallUnMarshallUtils<Parametros>(FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_IN_PACKAGE,
					FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_OUT_PACKAGE)).unmarshall_IN(parametros);
			if (AuthenticationHelper.authenticateUser(params.getCredencial().getUsuario(), params.getCredencial().getPassword())) {
				recuperarPortafirmasEJB(resultado, respuesta, params);
			} else {
				respuesta.setCodigo(Constantes.FWS_COD_ERR_AUTHENTICATION);
				respuesta.setDescripcion(Constantes.FWS_DESC_ERR_AUTHENTICATION);
			}

		} catch (JAXBException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_PF_DESC_ERR_PARAMETROS, e);
			respuesta.setCodigo(Constantes.FWS_PF_COD_ERR_PARAMETROS);
			respuesta.setDescripcion(Constantes.FWS_PF_DESC_ERR_PARAMETROS);
			respuesta.setError(e.getMessage());
		} catch (GenericFacadeException e) {
			respuesta.setCodigo(Constantes.FWS_COD_ERR_AUTHENTICATION);
			respuesta.setDescripcion(Constantes.FWS_DESC_ERR_AUTHENTICATION);
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_DESC_ERR_AUTHENTICATION, e);
		} catch (NamingException e) {
			respuesta.setCodigo(Constantes.FWS_COD_ERR_AUTHENTICATION);
			respuesta.setDescripcion(Constantes.FWS_DESC_ERR_AUTHENTICATION);
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_DESC_ERR_AUTHENTICATION, e);
		}
		try {
			return (new FacadeMarshallUnMarshallUtils<Resultado>(FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_IN_PACKAGE,
					FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_OUT_PACKAGE)).marshall_OUT(resultado);
		} catch (JAXBException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Ha ocurrido un error al intentar parsear los parametros de salida.", e);
			throw new PortafirmasFacadeException("Ha ocurrido un error al intentar parsear los parametros de salida. [" + e.getMessage() + "]");
		}
	}

	@WebMethod
	@WebResult(name = "resultado")
	public String consultarCircuitos(@WebParam(name = "parametros") String parametros) throws PortafirmasFacadeException {
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'consultarCircuitos' ...");
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();
		resultado.setRespuesta(respuesta);
		ListaCircuitos listaCircuitos = new ListaCircuitos();
		try {
			Parametros params = (new FacadeMarshallUnMarshallUtils<Parametros>(FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_IN_PACKAGE,
					FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_OUT_PACKAGE)).unmarshall_IN(parametros);

			if (AuthenticationHelper.authenticateUser(params.getCredencial().getUsuario(), params.getCredencial().getPassword())) {
				consultaCircuitosEJB(resultado, respuesta, params, listaCircuitos);
				return (new FacadeMarshallUnMarshallUtils<Resultado>(FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_IN_PACKAGE,
						FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_OUT_PACKAGE)).marshall_OUT(resultado);
			} else {
				respuesta.setCodigo(Constantes.FWS_COD_ERR_AUTHENTICATION);
				respuesta.setDescripcion(Constantes.FWS_DESC_ERR_AUTHENTICATION);
			}
		} catch (JAXBException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_PF_DESC_ERR_PARAMETROS, e);
			respuesta.setCodigo(Constantes.FWS_PF_COD_ERR_PARAMETROS);
			respuesta.setDescripcion(Constantes.FWS_PF_DESC_ERR_PARAMETROS);
			respuesta.setError(e.getMessage());
		} catch (GenericFacadeException e) {
			respuesta.setCodigo(Constantes.FWS_COD_ERR_AUTHENTICATION);
			respuesta.setDescripcion(Constantes.FWS_DESC_ERR_AUTHENTICATION);
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_DESC_ERR_AUTHENTICATION, e);
		} catch (NamingException e) {
			respuesta.setCodigo(Constantes.FWS_COD_ERR_AUTHENTICATION);
			respuesta.setDescripcion(Constantes.FWS_DESC_ERR_AUTHENTICATION);
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_DESC_ERR_AUTHENTICATION, e);
		}
		try {
			return (new FacadeMarshallUnMarshallUtils<Resultado>(FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_IN_PACKAGE,
					FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_OUT_PACKAGE)).marshall_OUT(resultado);
		} catch (JAXBException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Ha ocurrido un error al intentar parsear los parametros de salida.", e);
			throw new PortafirmasFacadeException("Ha ocurrido un error al intentar parsear los parametros de salida. [" + e.getMessage() + "]");
		}
	}

	@WebMethod
	@WebResult(name = "resultado")
	public String consultarFirmantes(@WebParam(name = "parametros") String parametros) throws PortafirmasFacadeException {
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'consultarFirmantes' ...");
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();
		resultado.setRespuesta(respuesta);
		try {
			Parametros params = (new FacadeMarshallUnMarshallUtils<Parametros>(FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_IN_PACKAGE,
					FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_OUT_PACKAGE)).unmarshall_IN(parametros);
			if (!AuthenticationHelper.authenticateUser(params.getCredencial().getUsuario(), params.getCredencial().getPassword())) {
				PortafirmasServiceException exception = new PortafirmasServiceException();
				exception.setErrorCode(Constantes.FWS_COD_ERR_AUTHENTICATION);
				exception.setDescription(Constantes.FWS_DESC_ERR_AUTHENTICATION);
				throw exception;
			}
			boolean esUsuarioPruebas = false;
			HashMap<String, Object> usuarioInfoMap = null;
			if (params.getParametrosConsultaFirmantes().getSolicitanteDNI() != null){
				usuarioInfoMap = AuthenticationHelper.getUserInfoByNumIdentification(params.getCredencial().getUsuario(), params.getCredencial().getPassword(), 
						params.getParametrosConsultaFirmantes().getSolicitanteDNI());
				params.getParametrosConsultaFirmantes().setSolicitanteUsername((String)usuarioInfoMap.get(LoginModuleConstants.PRINCIPAL_USERNAME));
			} else if (params.getParametrosConsultaFirmantes().getSolicitanteUsername() != null){
				usuarioInfoMap = AuthenticationHelper.getUserInfoByDisplayName(params.getCredencial().getUsuario(), params.getCredencial().getPassword(), 
						params.getParametrosConsultaFirmantes().getSolicitanteUsername());
				params.getParametrosConsultaFirmantes().setSolicitanteDNI((String)usuarioInfoMap.get(LoginModuleConstants.PRINCIPAL_NAME));
			}
			
			String[] roles = (String[])usuarioInfoMap.get(LoginModuleConstants.PRINCIPAL_ROLES);
			if (roles != null && Arrays.asList(roles).contains(AuthenticationHelper.ROL_USUARIO_PRUEBAS)) {
				esUsuarioPruebas = true;
			} 
			
			if (params.getParametrosConsultaFirmantes().getSolicitanteDNI() == null) {
				PortafirmasServiceException exception = new PortafirmasServiceException();
				exception.setErrorCode(Constantes.FWS_PF_COD_ERR_DNI_USUARIO);
				exception.setDescription(Constantes.FWS_PF_DESC_ERR_DNI_USUARIO);
				throw exception;
			}
			List<Persona> personas = consultarFirmantesEJB(params.getParametrosConsultaFirmantes().getSolicitanteDNI(), esUsuarioPruebas);
			if (personas.isEmpty()) {
				PortafirmasServiceException exception = new PortafirmasServiceException();
				exception.setErrorCode(Constantes.FWS_PF_COD_ERR_NO_FIRMANTES);
				exception.setDescription(Constantes.FWS_PF_DESC_ERR_NO_FIRMANTES);
				throw exception;
			}
			ListaFirmantes listaFirmantes = new ListaFirmantes();
			for (Persona persona : personas) {
				Firmante firmante = new Firmante();
				firmante.setId(persona.getId());
				firmante.setDni(persona.getNumIdentificacion());
				firmante.setNombre(persona.getNombre());
				firmante.setApellido1(persona.getApellido1());
				firmante.setApellido2(persona.getApellido2());
				listaFirmantes.getFirmantes().add(firmante);
			}
			resultado.setListaFirmantes(listaFirmantes);
			respuesta.setCodigo(Constantes.FWS_PF_COD_SUCCESSFUL);
			
			return (new FacadeMarshallUnMarshallUtils<Resultado>(FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_IN_PACKAGE,
					FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_OUT_PACKAGE)).marshall_OUT(resultado);
		} catch (JAXBException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_PF_DESC_ERR_PARAMETROS, e);
			resultado.setRespuesta(respuesta);
			respuesta.setCodigo(Constantes.FWS_PF_COD_ERR_PARAMETROS);
			respuesta.setDescripcion(Constantes.FWS_PF_DESC_ERR_PARAMETROS);
			respuesta.setError(e.getMessage());
		} catch (GenericFacadeException e) {
			resultado.setRespuesta(respuesta);
			respuesta.setCodigo(Constantes.FWS_COD_ERR_AUTHENTICATION);
			respuesta.setDescripcion(Constantes.FWS_DESC_ERR_AUTHENTICATION);
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " +Constantes.FWS_DESC_ERR_AUTHENTICATION, e);
		} catch (NamingException e) {
			resultado.setRespuesta(respuesta);
			respuesta.setCodigo(Constantes.FWS_COD_ERR_AUTHENTICATION);
			respuesta.setDescripcion(Constantes.FWS_DESC_ERR_AUTHENTICATION);
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " +Constantes.FWS_DESC_ERR_AUTHENTICATION, e);
		} catch (PortafirmasServiceException e) {
			resultado.setRespuesta(respuesta);
			respuesta.setCodigo(e.getErrorCode());
			respuesta.setDescripcion(e.getDescription());
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("[");
			stringBuilder.append(e.getErrorCode());			
			stringBuilder.append("]");
			stringBuilder.append(e.getMessage());
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " +stringBuilder.toString(), e);
		}
		try {
			return (new FacadeMarshallUnMarshallUtils<Resultado>(FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_IN_PACKAGE,
					FacadeMarshallUnMarshallUtils.URI_PORTAFIRMAS_OUT_PACKAGE)).marshall_OUT(resultado);
		} catch (JAXBException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Ha ocurrido un error al intentar parsear los parametros de salida.", e);
			throw new PortafirmasFacadeException("Ha ocurrido un error al intentar parsear los parametros de salida. [" + e.getMessage() + "]");
		}
	}
	
	@WebMethod(exclude = true)
	@Override
	public Respuesta enviarPortafirmasEJB(Resultado resultado, Respuesta respuesta, Parametros params) throws PortafirmasFacadeException {
		String backofficeId = params.getCredencial().getUsuario();
		String usuarioSolicitante = params.getParametrosAlta().getUsuarioSolicitante().getUsername();
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'enviarPortafirmasEJB' ...", backofficeId, usuarioSolicitante);
		AltaDocsPortafirmas altaDocsPortafirmas = params.getParametrosAlta();
		ListaDocumentosEnviados listaDocumentosEnviados = new ListaDocumentosEnviados();
		ListaDocumentosAltaOk listaDocumentosAltaOk = new ListaDocumentosAltaOk();
		ListaDocumentosError listaDocumentosError = new ListaDocumentosError();
		AltaDocumentosCore altaDocumentosCore = new AltaDocumentosCore(portafirmasDao);
		DocumentosManager manejadorDocumentos = null; // TODO new DocumentosManager(gestorDocumental);
		DocumentoPortafirmas documentoPortafirmas = new DocumentoPortafirmas();
		List<DocumentoPortafirmas> listDocumentoPortafirmas = new ArrayList<DocumentoPortafirmas>();
		List<DocumentoRepositorioItem> documentosRepositorioInfo = null;
		HashMap<String, String> rutasUrisMap = new HashMap<String, String>();
		
		try  {
			// Comprueba que la información de entrada de los documentos es correcta.
			List<DocumentoRepositorioItem> documentosEntradaInfo = manejadorDocumentos.getListaDocumentosEnviar(altaDocsPortafirmas);
			
			// Verifica que existen los documentos en el repositorio
			documentosRepositorioInfo = manejadorDocumentos.comprobarExistenciaDocumentosSGD(null, null, documentosEntradaInfo);
			if (documentosRepositorioInfo == null) {
				PortafirmasServiceException exception = new PortafirmasServiceException();
				exception.setErrorCode(Constantes.FWS_PF_COD_ERR_VAL_EXISTENCIA_DOCUMENTO);
				exception.setDescription(Constantes.FWS_PF_DESC_ERR_VAL_EXISTENCIA_DOCUMENTO);
				throw exception;
			}
			// Se comprueba la extension de los documentos
			if (!AltaDocumentosCore.comprobarFormato(documentosRepositorioInfo)) {
				PortafirmasServiceException exception = new PortafirmasServiceException();
				exception.setErrorCode(Constantes.FWS_PF_COD_ERR_VAL_FORMATO);
				exception.setDescription(Constantes.FWS_PF_DESC_ERR_VAL_FORMATO);
				throw exception;
			}
			if (!AltaDocumentosCore.comprobarFormatoDocumentosPDF(documentosRepositorioInfo, params.getParametrosAlta().getTipoFirmaEnum())) {
				PortafirmasServiceException exception = new PortafirmasServiceException();
				exception.setErrorCode(Constantes.FWS_PF_COD_ERR_VAL_TIPO_FIRMA);
				exception.setDescription(Constantes.FWS_PF_DESC_ERR_VAL_TIPO_FIRMA);
				resultado.setRespuesta(respuesta);
				throw exception;	
			}
			// Recupera de la base de datos los documentos que ya existen
			List<String> listaUris = new ArrayList<String>();
			for (DocumentoRepositorioItem documento:documentosRepositorioInfo) {
				listaUris.add(documento.getUri());
				rutasUrisMap.put(documento.getUri(), documento.getRutaCIFS());
			}
			listDocumentoPortafirmas = portafirmasDao.getListDocumentoPortafirmasByURI(listaUris);
			// Se comprueba que ninguno de los documentos ya se encuentra en tramitación
			if (!validarDatos(listDocumentoPortafirmas)) {
				PortafirmasServiceException exception = new PortafirmasServiceException();
				exception.setErrorCode(Constantes.FWS_PF_COD_ERR_VAL_ESTADO_DOCUMENTO);
				exception.setDescription(Constantes.FWS_PF_DESC_ERR_VAL_ESTADO_DOCUMENTO);
				resultado.setRespuesta(respuesta);
				throw exception;	
			}
			// Construimos el solicitante si nos pasan el username
			if (params.getParametrosAlta().getUsuarioSolicitante().getUsername() != null){
				params.getParametrosAlta().getUsuarioSolicitante().setInformacionSolicitante(altaDocumentosCore.construirSolicitante(params));
			}
			// Se comprueba que el usuario tenga DNI
			if (params.getParametrosAlta().getUsuarioSolicitante().getInformacionSolicitante().getDNINIE() == null) {
				PortafirmasServiceException exception = new PortafirmasServiceException();
				exception.setErrorCode(Constantes.FWS_PF_COD_ERR_DNI_USUARIO);
				exception.setDescription(Constantes.FWS_PF_DESC_ERR_DNI_USUARIO);
				resultado.setRespuesta(respuesta);
				throw exception;	
			}
		} catch(PortafirmasServiceException e){
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + e.getDescription(), null, backofficeId, usuarioSolicitante);
			respuesta.setCodigo(e.getErrorCode());
			respuesta.setDescripcion(e.getDescription());
			resultado.setRespuesta(respuesta);
			return respuesta;
		}
		
		BackOffice backoffice = portafirmasDao.getBackOfficeByUsername(params.getCredencial().getUsuario());
		documentoPortafirmas = altaDocumentosCore.crearModeloDocumento(params, listDocumentoPortafirmas, backoffice);
		if (null != altaDocsPortafirmas.getCircuito()) {
			CircuitoEntity circuito = new CircuitoEntity();

			if (altaDocsPortafirmas.getCircuito().getTipoCircuito().toString().equalsIgnoreCase("PRDF")) {
				try {
					circuito = duplicarCircuito(portafirmasDao.getCircuitoById(altaDocsPortafirmas.getCircuito().getIdCircuito()));
				} catch (IllegalAccessException e) {
					throw new PortafirmasFacadeException(e);
				} catch (InstantiationException e) {
					throw new PortafirmasFacadeException(e);
				} catch (InvocationTargetException e) {
					throw new PortafirmasFacadeException(e);
				} catch (NoSuchMethodException e) {
					throw new PortafirmasFacadeException(e);
				}
			} else {
				// Populate de los Grupos
				List<Grupo> listGrupos = new ArrayList<Grupo>();
				int orden = 1;
				for (es.apt.ae.facade.ws.params.portafirmas.in.alta.Grupo grupo : altaDocsPortafirmas.getCircuito().getGrupo()) {
					Grupo grupoEntidad = new Grupo();
					grupoEntidad.setFirmantesRequeridos(new Integer(grupo.getFirmantesRequeridos()).intValue());
					grupoEntidad.setTipoGrupo(portafirmasDao.getTipoGrupobyCod(grupo.getTipoGrupo().toString()));

					for (es.apt.ae.facade.ws.params.portafirmas.in.alta.Persona persona : grupo.getPersonas()) {
						Persona personaEntidad;
						if (persona.getDni() != null) {
							personaEntidad = portafirmasDao.getPersonaByDNI(persona.getDni());
						} else {
							personaEntidad = portafirmasDao.getPersonaById(persona.getId());
						}

						if (personaEntidad != null) {
							GrupoPersona grupoPersona = new GrupoPersona();
							grupoPersona.setPersona(personaEntidad);
							grupoPersona.setGrupo(grupoEntidad);
							grupoPersona.setObligatorio(persona.isObligatorio());
							grupoEntidad.getGrupoPersona().add(grupoPersona);
						} else {
							// TODO: la persona indicada no se encuentra en la tabla de firmantes
						}
					}
					grupoEntidad.setOrden(orden);
					orden++;
					listGrupos.add(grupoEntidad);
				}

				circuito.setListGrupo(listGrupos);
				TipoCircuito tipoCircuito = portafirmasDao.getTipoCircuitobyCod(altaDocsPortafirmas.getCircuito().getTipoCircuito().toString());

				circuito.setTipoCircuito(tipoCircuito);
			}
			circuito.setFechaCreacion(new Date());
			circuito.setOrdenActivo(1);
			documentoPortafirmas.setCircuito(circuito);

			// Seteamos el estado a "En tramite" porque tiene circuito
			// asignado
			EstadoDocumento estadoDocumento = portafirmasDao.getEstadoDocumentobyCod("ENVD");
			documentoPortafirmas.setEstadoDocumento(estadoDocumento);
		} else {
			// Seteamos el estado a "Pendiente de circuito" precisamente
			// porque no se le ha asignado circuito
			EstadoDocumento estadoDocumento = portafirmasDao.getEstadoDocumentobyCod("PEND");
			documentoPortafirmas.setEstadoDocumento(estadoDocumento);
		}

		Accion accionEnviado = portafirmasDao.getAccionByCodigo(ENVD);

		// Si no existen documentos se crean en base a la informaciÃ³n anterior
		if (listDocumentoPortafirmas.isEmpty()) {
			//for (es.apt.ae.facade.ws.params.commons.in.Documento documento : altaDocsPortafirmas.getListaDocumentos().getDocumento()) {
			for (DocumentoRepositorioItem documento:documentosRepositorioInfo) {
				MessageDigest md;
				try {
					md = MessageDigest.getInstance("SHA-1");
				} catch (NoSuchAlgorithmException e) {
					throw new PortafirmasFacadeException(e);
				}
				String cadena = documento.getUri() + new Date().getTime();
				md.update(cadena.getBytes());
				documentoPortafirmas.setUri(documento.getUri());
				documentoPortafirmas.setHash(Hex.encodeHexString(md.digest()));
				documentoPortafirmas.setNombre(documento.getDescripcion()!=null?documento.getDescripcion():documento.getNombre());
				ProcesoFirma procesoFirma = new ProcesoFirma();
				procesoFirma.setAccion(accionEnviado);
				procesoFirma.setFechaAccion(new Date());
				boolean enviado = false;
				try {
					DocumentoPortafirmas documentoPortafirmasCreado = portafirmasDao.save(documentoPortafirmas);
					procesoFirma.setDocumento(documentoPortafirmasCreado);
					portafirmasDao.save(procesoFirma);
					LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Documento Creado con id: " + documentoPortafirmasCreado.getId());
					enviado = true;
					addDocOk(listaDocumentosAltaOk, documento.getUri(), documento.getRutaCIFS());
				} catch (Exception e) {
					LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se ha podido almacenar el documento con la uri: " + documento.getUri(), e, backofficeId, usuarioSolicitante);
					addDocError(listaDocumentosError, documento.getUri(), documento.getRutaCIFS(), "No se ha podido almacenar el documento");
				}
				if (enviado) {
					// Se envian las notificaciones correspondientes
			    	try {
						// Invocación del thread para el envío de notificaciones
						ThreadEnviarNotificacionesPortafirmas thread = new ThreadEnviarNotificacionesPortafirmas(manejadorDocumentos, 
								portafirmasDao, srvCbFirma, documentoPortafirmas, Constantes.FWS_PF_NOTIFICACION_CAMBIO_GRUPO, 
								backofficeId, usuarioSolicitante);
				        thread.start();	
					} catch (Exception e) {
						LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Ha ocurrido un error en el envío de notificaciones", null, backofficeId, usuarioSolicitante);
					} 					
				}
			}
		} else {// Si ya existen se le añaden los nuevos datos y se actualizan
			for (DocumentoPortafirmas doc : listDocumentoPortafirmas) {
				if (doc.getCircuito() != null) {// Comprobamos que no tenga circuitos previos
					LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] El documento con uri " + doc.getUri() + " ya se encuentra en un circuito", null);
					addDocError(listaDocumentosError, doc.getUri(), rutasUrisMap.get(doc.getUri()), "El documento ya se encuentra en un circuito");
				} else {
					doc.setCircuito(documentoPortafirmas.getCircuito().populate());
					doc.setEstadoDocumento(documentoPortafirmas.getEstadoDocumento());
					ProcesoFirma procesoFirma = new ProcesoFirma();
					procesoFirma.setAccion(accionEnviado);
					procesoFirma.setFechaAccion(new Date());
					procesoFirma.setDocumento(doc);
					doc.setMailCreador(altaDocsPortafirmas.getUsuarioSolicitante().getInformacionSolicitante().getCorreoNotificacion());
					boolean enviado = false;
					try {
						portafirmasDao.save(procesoFirma);
						enviado = true;
						addDocOk(listaDocumentosAltaOk, doc.getUri(), rutasUrisMap.get(doc.getUri()));
					} catch (Exception e) {
						LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se ha podido almacenar el documento con la uri: " + doc.getUri(), e, 
								backofficeId, usuarioSolicitante);
						addDocError(listaDocumentosError, doc.getUri(), rutasUrisMap.get(doc.getUri()), "No se ha podido almacenar el documento");
					}
					if (enviado) {
						// Se envian las notificaciones correspondientes
				    	try {
							// Invocación del thread para el envío de notificaciones
							ThreadEnviarNotificacionesPortafirmas thread = new ThreadEnviarNotificacionesPortafirmas(manejadorDocumentos, 
									portafirmasDao, srvCbFirma, doc, Constantes.FWS_PF_NOTIFICACION_CAMBIO_GRUPO, 
									backofficeId, usuarioSolicitante);
					        thread.start();	
						} catch (Exception e) {
							LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Ha ocurrido un error en el envío de notificaciones", e, backofficeId, usuarioSolicitante);
						}
					}					
				}
			}

		}
		
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_PF_DESC_COD_SUCCESSFUL, backofficeId, usuarioSolicitante);
		respuesta.setCodigo(Constantes.FWS_PF_COD_SUCCESSFUL);
		resultado.setRespuesta(respuesta);

		listaDocumentosEnviados.setListaDocumentosAltaOk(listaDocumentosAltaOk);
		listaDocumentosEnviados.setListaDocumentosError(listaDocumentosError);
		resultado.setListaDocumentosEnviados(listaDocumentosEnviados);
		return respuesta;
	}
	
	@WebMethod(exclude = true)
	@Override
	public Persona getPersona(String dni) {
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'getPersona' ...");
		return portafirmasDao.getPersona(dni);
	}

	@WebMethod(exclude = true)
	@Override
	public Respuesta recuperarPortafirmasEJB(Resultado resultado, Respuesta respuesta, Parametros params) {
		String backofficeId = params.getCredencial().getUsuario();
		String usuarioSolicitante = params.getParametrosRecuperar().getUsuarioSolicitante().getUsername();
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'recuperarPortafirmasEJB' ...", backofficeId, usuarioSolicitante);		
		ListaDocumentosRecuperados listaDocumentosRecuperados = new ListaDocumentosRecuperados();
		ListaDocumentosOk documentosRecuperadosOk = new ListaDocumentosOk();
		ListaDocumentosError documentosRecuperadosError = new ListaDocumentosError();
		DocumentosManager manejadorDocumentos =null; // TODO  new DocumentosManager(gestorDocumental);
		HashMap<String, String> rutasUrisMap = new HashMap<String, String>();
		HashMap<String, String> urisRutasMap = new HashMap<String, String>();
		
		if (params.getParametrosRecuperar().getUsuarioSolicitante().getUsername() != null) {// si tenemos el usuario y no tenemos el DNI, lo recuperamos
			try {
				params.getParametrosRecuperar().getUsuarioSolicitante().setInformacionSolicitante(
					AuthenticationHelper.getInformationByDisplayName(
							params.getCredencial().getUsuario(), params.getCredencial().getPassword(), 
							params.getParametrosRecuperar().getUsuarioSolicitante().getUsername()));
			} catch (GenericFacadeException e) {
				LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_COD_ERR_AUTHENTICATION, e, backofficeId, usuarioSolicitante);
			} catch (NamingException e) {
				LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_COD_ERR_AUTHENTICATION, e, backofficeId, usuarioSolicitante);
			}
		}

		boolean docsNoEncontrados = false;
		boolean docsSinAutorizacion = false;
		boolean docsEnTramite = false;
		
		try  {
			String identificacionSolicitante = params.getParametrosRecuperar().getUsuarioSolicitante().getInformacionSolicitante().getDNINIE();
			
			if (identificacionSolicitante != null) {
				RecuperarDocsPortafirmas recuperarDocsPortafirmas = params.getParametrosRecuperar();

				// Comprueba que la información de entrada de los documentos es correcta.
				List<DocumentoRepositorioItem> documentosEntradaInfo = manejadorDocumentos.getListaDocumentosRecuperar(recuperarDocsPortafirmas);
				
				// Verifica los documentos a los que se tiene acceso
				List<DocumentoRepositorioItem> documentosRepositorioInfo = manejadorDocumentos.comprobarExistenciaDocumentosSGD(null, null, documentosEntradaInfo);

				// Obtiene de la base de datos los documentos
				List<DocumentoPortafirmas> listDocumentoPortafirmas = new ArrayList<DocumentoPortafirmas>();
				List<String> listaUris = new ArrayList<String>();
				if (documentosRepositorioInfo != null && !documentosRepositorioInfo.isEmpty()) {
					for (DocumentoRepositorioItem documento:documentosRepositorioInfo) {
						listaUris.add(documento.getUri());
						rutasUrisMap.put(documento.getUri(), documento.getRutaCIFS());
						if (documento.getRutaCIFS() != null && !documento.getRutaCIFS().trim().equals("")) {
							urisRutasMap.put(documento.getRutaCIFS(), documento.getUri());
						}
					}
					listDocumentoPortafirmas = portafirmasDao.getListDocumentoPortafirmasByURI(listaUris);
				}
				
				for (DocumentoRepositorioItem documento:documentosEntradaInfo) {
					String uri = documento.getUri();
					if (uri == null) {
						uri = urisRutasMap.get(documento.getRutaCIFS());
					}
					
					DocumentoPortafirmas documentoPortafirmas = null;
					if (listDocumentoPortafirmas != null) {
						for (DocumentoPortafirmas documentoPortafirmasActual:listDocumentoPortafirmas) {
							if (documentoPortafirmasActual.getUri().equals(uri)) {
								documentoPortafirmas = documentoPortafirmasActual;
								break;
							}
						}
					}
	
					if (null != documentoPortafirmas && null != documentoPortafirmas.getUri() && documentoPortafirmas.getSubidoPorDNI().equals(identificacionSolicitante)) {
						if (documentoPortafirmas.getEstadoDocumento().getCodigo().equals("ENVD") ){
							ProcesoFirma procesoFirma = new ProcesoFirma();
							procesoFirma.setAccion(portafirmasDao.getAccionByCodigo(RECP));
							procesoFirma.setFechaAccion(new Date());
							procesoFirma.setObservaciones(Constantes.FWS_PF_OBSERVACION_RECUPERAR_DOC);
							procesoFirma.setDocumento(documentoPortafirmas);
		
							documentoPortafirmas.setEstadoDocumento(portafirmasDao.getEstadoDocumentobyCod(RECP));
							if (documentoPortafirmas.getSistemaOrigen().getCodigo().equals("PRTF")) {
								documentoPortafirmas.setSistemaOrigen(null);
							}
							portafirmasDao.remove(documentoPortafirmas.getCircuito());
							documentoPortafirmas.setCircuito(null);
							portafirmasDao.save(documentoPortafirmas);
							portafirmasDao.save(procesoFirma);
							addDocOk(documentosRecuperadosOk, uri, rutasUrisMap.get(uri));
						} else {
							docsEnTramite = true;
							addDocError(documentosRecuperadosError, uri, rutasUrisMap.get(uri), Constantes.FWS_PF_DESC_ERR_DOC_EN_TRAMITE);
						}
					} else {
						String msgError = null;
						if (documentoPortafirmas == null || documentoPortafirmas.getUri() == null) {
							docsNoEncontrados = true;
							msgError = Constantes.FWS_PF_DESC_ERR_NO_EXISTE;
						}
						if (!documentoPortafirmas.getSubidoPorDNI().equals(identificacionSolicitante)) {
							docsSinAutorizacion = true;
							msgError = Constantes.FWS_PF_DESC_ERR_DOC_NO_SOLICITANTE;
						}
						addDocError(documentosRecuperadosError, uri, rutasUrisMap.get(documento.getUri()), msgError);
					}
				}
			} else {
				PortafirmasServiceException exception = new PortafirmasServiceException();
				exception.setErrorCode(Constantes.FWS_PF_COD_ERR_PARAMETROS);
				exception.setDescription(Constantes.FWS_PF_DESC_ERR_PARAMETROS);
				throw exception;
			}
		} catch(PortafirmasServiceException e){
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + e.getDescription(), null, backofficeId, usuarioSolicitante);
			respuesta.setCodigo(e.getErrorCode());
			respuesta.setDescripcion(e.getDescription());
			resultado.setRespuesta(respuesta);
			return respuesta;
		}

		listaDocumentosRecuperados.setListaDocumentosOk(documentosRecuperadosOk);

		if (documentosRecuperadosError.getUri().size() > 0) {
			String msg = "";
			if (docsNoEncontrados) {
				msg += Constantes.FWS_PF_DESC_ERR_NO_EXISTEN;
			}
			if (docsSinAutorizacion) {
				msg += Constantes.FWS_PF_DESC_ERR_NO_SOLICITANTE;
			}
			if (docsEnTramite){
				msg += Constantes.FWS_PF_DESC_ERR_EN_TRAMITE;
			}
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + msg, null, backofficeId, usuarioSolicitante);
			respuesta.setCodigo(Constantes.FWS_PF_COD_ERR_RECUPERAR);
			respuesta.setDescripcion(msg);
			listaDocumentosRecuperados.setListaDocumentosError(documentosRecuperadosError);
		} else {
			LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_PF_DESC_COD_SUCCESSFUL, backofficeId, usuarioSolicitante);
			respuesta.setCodigo(Constantes.FWS_PF_COD_SUCCESSFUL);
		}

		resultado.setRespuesta(respuesta);
		resultado.setListaDocumentosRecuperados(listaDocumentosRecuperados);
		return respuesta;
	}
	
	private void addDocOk(ListaDocumentosAltaOk listaDocumentosAltaOk, String uri, String ruta) {
		Documento documentoOK = new Documento();
		documentoOK.setUri(uri);
		documentoOK.setRuta(ruta);
		listaDocumentosAltaOk.getDocumento().add(documentoOK);
	}
	
	private void addDocOk(ListaDocumentosOk listaDocumentosOk, String uri, String ruta) {
		listaDocumentosOk.getUri().add(uri);
		DocumentoSalidaOk documentoOK = new DocumentoSalidaOk();
		documentoOK.setUri(uri);
		documentoOK.setRuta(ruta);
		listaDocumentosOk.getDocumento().add(documentoOK);
	}
	
	private void addDocError(ListaDocumentosError listaDocumentosError, String uri, String ruta, String error) {
		listaDocumentosError.getUri().add(uri);
		//if (documento.getRutaCIFS() != null && !documento.getRutaCIFS().trim().equals("")) {
		DocumentoSalidaError documentoError = new DocumentoSalidaError();
		documentoError.setUri(uri);
		documentoError.setRuta(ruta);
		documentoError.setError(error);
		listaDocumentosError.getDocumento().add(documentoError);
		//}
	}

	@WebMethod(exclude = true)
	@Override
	public Respuesta consultaCircuitosEJB(Resultado resultado, Respuesta respuesta, Parametros params, ListaCircuitos listaCircuitos) {
		String backofficeId = params.getCredencial().getUsuario();
		String solicitanteUserName = (params.getParametrosConsultaCircuitos() != null?params.getParametrosConsultaCircuitos().getSolicitanteUsername():null);
		String solicitanteDNI = (params.getParametrosConsultaCircuitos() != null?params.getParametrosConsultaCircuitos().getSolicitanteDNI():null);
		String usuarioSolicitante = (solicitanteUserName != null?solicitanteUserName:solicitanteDNI);
		Long idCircuito = ((params.getParametrosConsultaCircuitos() != null && params.getParametrosConsultaCircuitos().getIdCircuito() != null)
				?new Long(params.getParametrosConsultaCircuitos().getIdCircuito()):null);
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'consultaCircuitosEJB' ...", backofficeId, usuarioSolicitante);		

		List<CircuitoEntity> circuitos = null;
		
		try {
			if (params.getParametrosConsultaCircuitos() != null) {
				ConsultaCircuitos paramsConsultaCircuitos = params.getParametrosConsultaCircuitos();
				if (solicitanteUserName != null){
					InformacionSolicitante informacionSolicitante = AuthenticationHelper.getInformationByDisplayName(params.getCredencial().getUsuario(), 
							params.getCredencial().getPassword(), solicitanteUserName);
					paramsConsultaCircuitos.setSolicitanteDNI(informacionSolicitante.getDNINIE());
				}
				circuitos = portafirmasDao.getListCircuitosByCodigo("PRDF", paramsConsultaCircuitos.getSolicitanteDNI(), idCircuito);
			} else {
				circuitos = portafirmasDao.getListCircuitosByCodigo("PRDF", null, null);
			}
		} catch (GenericFacadeException e) {
			resultado.setRespuesta(respuesta);
			respuesta.setCodigo(Constantes.FWS_COD_ERR_AUTHENTICATION);
			respuesta.setDescripcion(Constantes.FWS_DESC_ERR_AUTHENTICATION);
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_DESC_ERR_AUTHENTICATION, e, backofficeId, usuarioSolicitante);
		} catch (NamingException e) {
			resultado.setRespuesta(respuesta);
			respuesta.setCodigo(Constantes.FWS_COD_ERR_AUTHENTICATION);
			respuesta.setDescripcion(Constantes.FWS_DESC_ERR_AUTHENTICATION);
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_DESC_ERR_AUTHENTICATION, e, backofficeId, usuarioSolicitante);
		}		

		if (circuitos != null) {
			// Recorremos los circuitos
			for (CircuitoEntity flujo : circuitos) {
				Circuito circuito = new Circuito();
				// Recorremos los grupos
				ListaGrupos listaGrupos = new ListaGrupos();
				TreeMap<Integer, es.apt.ae.facade.ws.params.portafirmas.out.Grupo> gruposXSDMap = new 
						TreeMap<Integer, es.apt.ae.facade.ws.params.portafirmas.out.Grupo>();
				for (Grupo grupo : flujo.getListGrupo()) {
					es.apt.ae.facade.ws.params.portafirmas.out.Grupo grupoXSD = new es.apt.ae.facade.ws.params.portafirmas.out.Grupo();
					grupoXSD.setFirmantesRequeridos(new Integer(grupo.getFirmantesRequeridos()).toString());
					// Recorremos las personas asociadas a un circuito
					ListaFirmantes listaFirmantes = new ListaFirmantes();
					for (GrupoPersona grupoPersona : grupo.getGrupoPersona()) {
						Firmante firmante = new Firmante();
						firmante.setNombre(grupoPersona.getPersona().getNombre());
						firmante.setApellido1(grupoPersona.getPersona().getApellido1());
						firmante.setApellido2(grupoPersona.getPersona().getApellido2());
						firmante.setDni(grupoPersona.getPersona().getNumIdentificacion());
						listaFirmantes.getFirmantes().add(firmante);
					}
					grupoXSD.setListaFirmantes(listaFirmantes);
					grupoXSD.setTipoGrupo(grupo.getTipoGrupo().getDescripcion());
					grupoXSD.setOrden("" + grupo.getOrden());
					gruposXSDMap.put(grupo.getOrden(), grupoXSD);
					//listaGrupos.getGrupos().add(grupoXSD);
				}
				listaGrupos.getGrupos().addAll(gruposXSDMap.values());
				circuito.setListaGrupos(listaGrupos);
				try {
					circuito.setFecha(DateUtils.dateToXMLGregorianCalendar(flujo.getFechaCreacion()));
				} catch(DatatypeConfigurationException e) {
					LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar la fecha del circuito", e, 
							backofficeId, usuarioSolicitante);
				}
				circuito.setIdCircuito(flujo.getId());
				circuito.setDescripcion(flujo.getDescripcion());
				listaCircuitos.getCircuitos().add(circuito);
			}
	
			resultado.setListaCircuitos(listaCircuitos);
			LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] " + Constantes.FWS_PF_DESC_COD_SUCCESSFUL, backofficeId, usuarioSolicitante);
			respuesta.setCodigo(Constantes.FWS_PF_COD_SUCCESSFUL);
		}
		
		return respuesta;
	}

	@WebMethod(exclude = true)
	@Override
	public List<CircuitoEntity> consultaCircuitosPorTipo(String tipo) {
		return portafirmasDao.getListCircuitoByCodigoTipo(tipo);
	}
	
	@WebMethod(exclude = true)
	@Override
	public List<CircuitoEntity> consultaCircuitosPorTipoYSolicitante(String tipo, String dniSolicitante) {
		return portafirmasDao.getListCircuitosByCodigo(tipo, dniSolicitante, null);
	}	

	@WebMethod(exclude = true)
	@Override
	public List<Persona> consultarFirmantesEJB(String dni, boolean esUsuarioPruebas) {
		return portafirmasDao.getListFirmanteByDNISolicitante(dni, esUsuarioPruebas);
	}

	@WebMethod(exclude = true)
	@Override
	public List<Persona> consultarValidadoresEJB() {
		return portafirmasDao.getListValidador();
	}

	@WebMethod(exclude = true)
	@Override
	public List<Persona> consultarPersonasEJB() {
		return portafirmasDao.getListFirmante();
	}

	@WebMethod(exclude = true)
	@Override
	public List<DocumentoPortafirmas> buscarPorBandeja(String usuario, String tipoBandeja, HashMap<String, Object> filtros) {
		String backofficeId = FacadeBean.USUARIO_PORTAFIRMAS;
		String usuarioSolicitante = usuario;
		try {
			HashMap<String, Object> userInfo = AuthenticationHelper.getUserInfoByNumIdentification(null, null, usuario);
			if (userInfo != null && userInfo.containsKey(LoginModuleConstants.PRINCIPAL_USERNAME)) {
				usuarioSolicitante = (String)userInfo.get(LoginModuleConstants.PRINCIPAL_USERNAME);
			}
		} catch (Exception e) {}
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'buscarPorBandeja' ...", backofficeId, usuarioSolicitante);
		
		List<DocumentoPortafirmas> resultado = new ArrayList<DocumentoPortafirmas>();

		if (tipoBandeja.equals(PERSONAL)) {
			if (filtros == null) {
				resultado = portafirmasDao.getDocumentosPersonalesbyDNI(usuario);
			} else {
				resultado = portafirmasDao.getDocumentosPersonalesbyDNIwithFiltros(usuario, filtros);
			}
		} else if (tipoBandeja.equals(TRAMITADOS)) {
			if (filtros == null) {
				resultado = portafirmasDao.getDocumentosTramitadosbyDNI(usuario);
			} else {
				resultado = portafirmasDao.getDocumentosTramitadosbyDNIwithFiltros(usuario, filtros);
			}
		} else if (tipoBandeja.equals(PENDIENTES)) {
			if (filtros == null) {
				resultado = portafirmasDao.getDocumentosPendientesbyDNI(usuario, null);
			} else {
				resultado = portafirmasDao.getDocumentosPendientesbyDNIwithFiltros(usuario, filtros);
			}
		} else if (tipoBandeja.equals(ENVIADOS)) {
			if (filtros == null) {
				resultado = portafirmasDao.getDocumentosEnviadosbyDNI(usuario);
			} else {
				resultado = portafirmasDao.getDocumentosEnviadosbyDNIwithFiltros(usuario, filtros);
			}
		} else {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se encuentra la bandeja indicada '" + tipoBandeja + "'", null, 
					backofficeId, usuarioSolicitante);
		}
		return resultado;
	}

	@WebMethod(exclude = true)
	@Override
	public List<ProcesoFirma> getHistorialDocumento(String usuario, String uri) throws PortafirmasFacadeException {
		String backofficeId = FacadeBean.USUARIO_PORTAFIRMAS;
		String usuarioSolicitante = usuario;
		try {
			HashMap<String, Object> userInfo = AuthenticationHelper.getUserInfoByNumIdentification(null, null, usuario);
			if (userInfo != null && userInfo.containsKey(LoginModuleConstants.PRINCIPAL_USERNAME)) {
				usuarioSolicitante = (String)userInfo.get(LoginModuleConstants.PRINCIPAL_USERNAME);
			}
		} catch (Exception e) {}
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'getHistorialDocumento' ...", backofficeId, usuarioSolicitante);		
		
		List<ProcesoFirma> resultado = portafirmasDao.getProcesoByUri(uri);
		return resultado;
	}

	@WebMethod(exclude = true)
	@Override
	public CircuitoEntity consultarCircuito(Long idCircuito) throws PortafirmasFacadeException {
		CircuitoEntity circuito = portafirmasDao.getCircuitoById(idCircuito.toString());
		return circuito;
	}

	@WebMethod(exclude = true)
	@Override
	public CircuitoEntity consultarCircuitoByUri(String uri) throws PortafirmasFacadeException {
		return portafirmasDao.getCircuitoByUri(uri);
	}

	@WebMethod(exclude = true)
	@Override
	public HashMap<String, Object> actuarSobreListaDocumento(String usuario, List<DocumentoPortafirmas> listDocumentos, String accion, String descripcion)
			throws PortafirmasFacadeException {

		String backofficeId = FacadeBean.USUARIO_PORTAFIRMAS;
		String usuarioSolicitante = usuario;
		try {
			HashMap<String, Object> userInfo = AuthenticationHelper.getUserInfoByNumIdentification(null, null, usuario);
			if (userInfo != null && userInfo.containsKey(LoginModuleConstants.PRINCIPAL_USERNAME)) {
				usuarioSolicitante = (String)userInfo.get(LoginModuleConstants.PRINCIPAL_USERNAME);
			}
		} catch (Exception e) {}
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'actuarSobreListaDocumento' ...", backofficeId, usuarioSolicitante);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		if (!accion.isEmpty()) {
			Persona persona = portafirmasDao.getPersonaByDNI(usuario);
			Accion accionNew = portafirmasDao.getAccionByCodigo(accion);
			if (accionNew.getCodigo().equals(BORR)) {// DEFINIR
				eliminarDocumentoPortafirmas(usuarioSolicitante, listDocumentos, Constantes.GESTOR_DOCUMENTAL_USERNAME, Constantes.GESTOR_DOCUMENTAL_PASSWORD);
			} else {
				EstadoDocumento estadoDocumento = portafirmasDao.getEstadoDocumentobyCod(accion);
				EstadoDocumento estadoDocumentoEnviado = portafirmasDao.getEstadoDocumentobyCod("ENVD");
				List<ProcesoFirma> listProcesoFirma = new ArrayList<ProcesoFirma>();

				for (DocumentoPortafirmas documento : listDocumentos) {
					// Comprobamos si esta sustituyendo a alguien
					Persona ausentado = comprobarSustitucion(persona, documento);
					if (ausentado != null) {
						if (descripcion == null) {
							descripcion = "";
						}
						descripcion += "-Realizado en calidad de sustitucion de: " + ausentado.getNombre() + " " + ausentado.getApellido1() + " " + ausentado.getApellido2() + " -"
								+ ausentado.getCargo() + ".";
					}
					// Generamos el proceso a dar de alta para 1 unico documento
					ProcesoFirma procesoFirma = new ProcesoFirma();
					procesoFirma.setAccion(accionNew);
					procesoFirma.setFechaAccion(new Date());
					procesoFirma.setObservaciones(descripcion);
					procesoFirma.setPersona(persona);
					procesoFirma.setDocumento(documento);
					documento.setEstadoDocumento(estadoDocumentoEnviado);
					// Si la accion a realizar es la solicitada en el grupo
					Grupo grupo = PortafirmasFacadeProcesosUtils.recuperarGrupo(documento.getCircuito().getListGrupo(), documento.getCircuito().getOrdenActivo());

					// Si esa persona tiene que actuar con la accion indicada en ese grupo
					if (grupo.getTipoGrupo().getCodigo().equals(accionNew.getCodigo())) {
						for (GrupoPersona grupoPersona : grupo.getGrupoPersona()) {
							if (grupoPersona.getPersona().getNumIdentificacion().equals(persona.getNumIdentificacion())
									|| (ausentado != null && grupoPersona.getPersona().getNumIdentificacion().equals(ausentado.getNumIdentificacion()))) {
								grupoPersona.setRealidazo(true);
								listProcesoFirma.add(procesoFirma);
								break;
							}
						}
					} else {
						// RECHAZAR o Borrar
						if (accionNew.getCodigo().equals(RECH) || accionNew.getCodigo().equals(BORR)) {
							listProcesoFirma.add(procesoFirma);
						} else {
							// Ver si es colaborador de la persona del grupo y por tanto validar o rechazar
							List<Persona> listPersonasARevisar = (List<Persona>) portafirmasDao.getListRevisoresDe(persona.getNumIdentificacion());
							for (Persona personaARevisar : listPersonasARevisar) {
								for (GrupoPersona grupoPersona : grupo.getGrupoPersona()) {
									if (grupoPersona.getPersona().getNumIdentificacion().equals(personaARevisar.getNumIdentificacion())) {
										// Si su accion es de validacion
										if (accionNew.getCodigo().equals(VALD)) {
											listProcesoFirma.add(procesoFirma);
										}
										break;
									}
								}
							}
						}
					}
				}

				HashMap<String, List<ProcesoFirma>> resultadoActuar = new HashMap<String, List<ProcesoFirma>>();
				if (listDocumentos != null && estadoDocumento != null && !listProcesoFirma.isEmpty()) {
					if (accionNew.getCodigo().equals(FIRM)) {
						resultadoActuar = PortafirmasFacadeProcesosUtils.escribirListaProceso(listProcesoFirma, portafirmasDao);
					} else if (accionNew.getCodigo().equals(VALD)) {
						resultadoActuar = PortafirmasFacadeProcesosUtils.escribirListaProceso(listProcesoFirma, portafirmasDao);
					} else if (accionNew.getCodigo().equals(RECH)) {
						resultadoActuar = portafirmasDao.rechazarListDocumento(listProcesoFirma);
					} else if (accionNew.getCodigo().equals(BORR)) {
						eliminarDocumentoPortafirmas(usuarioSolicitante, listDocumentos, Constantes.GESTOR_DOCUMENTAL_USERNAME, Constantes.GESTOR_DOCUMENTAL_PASSWORD);
					}

					// Se envian las notificaciones correspondientes
					DocumentosManager manejadorDocumentos = null; // TODO new DocumentosManager(gestorDocumental);
			    	try {
						// Invocación del thread para el envío de notificaciones
						ThreadEnviarNotificacionesPortafirmas thread = new ThreadEnviarNotificacionesPortafirmas(manejadorDocumentos, 
								portafirmasDao, srvCbFirma, resultadoActuar);
				        thread.start();	
					} catch (Exception e) {
						LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Ha ocurrido un error en el envío de notificaciones", e, backofficeId, usuarioSolicitante);
					} 

					LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Se realizaron las acciones indicadas", backofficeId, usuarioSolicitante);
					result.put("result", true);
					result.put("message", "Se realizaron las acciones indicadas");
					result.put("listaProcesosOK", resultadoActuar.get("listaProcesosOK"));
					result.put("listaProcesosERROR", resultadoActuar.get("listaProcesosERROR"));
				} else {
					LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se indica una accion adecuada para ninguno de los documentos", null, backofficeId, usuarioSolicitante);
					result.put("result", false);
					result.put("message", "No se indica una accion adecuada para ninguno de los documentos");
				}
			}
		} else {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se ha indicado una accion a realizar", null, 
					backofficeId, usuarioSolicitante);
		}
		return result;
	}

	@WebMethod(exclude = true)
	@Override
	public Boolean altaDocumento(String usuario, DocumentoPortafirmas documento, byte[] binaries, String contentType) throws PortafirmasFacadeException {
		String backofficeId = FacadeBean.USUARIO_PORTAFIRMAS;
		String usuarioSolicitante = usuario;
		try {
			HashMap<String, Object> userInfo = AuthenticationHelper.getUserInfoByNumIdentification(null, null, usuario);
			if (userInfo != null && userInfo.containsKey(LoginModuleConstants.PRINCIPAL_USERNAME)) {
				usuarioSolicitante = (String)userInfo.get(LoginModuleConstants.PRINCIPAL_USERNAME);
			}
		} catch (Exception e) {}
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'altaDocumento' ...", backofficeId, usuarioSolicitante);

		String separator = "/";
		Date globalProcess = new Date();
		byte[] content = binaries;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(documento.getFechaSubidaPortafirmas());

		ParametrosSGD params = new ParametrosSGD();
		Credenciales credenciales = new Credenciales();
		credenciales.setUsuario(Constantes.GESTOR_DOCUMENTAL_USERNAME);
		credenciales.setPassword(Constantes.GESTOR_DOCUMENTAL_PASSWORD);
		params.setCredenciales(credenciales);
		DatosNodo datosNodo = new DatosNodo();
		
		DocumentosManager manejadorDocumentos = null; // TODO new DocumentosManager(gestorDocumental);
		
		try {
			// Eliminar caracteres extraños del documento.
			String nombreDoc = FileUtils.encodeFileName(((documento.getNombre().matches("\\d.*")?"_":"") + documento.getNombre()), 255);

			// Inyeccion de la api de alfresco desarrollada por Open Canarias.
			IRepositoryService ars = AlfRepositoryService.getInstance(FacadeBean.RUTA_FICHERO_CONFIGURACION);
			Parameters parameters = new Parameters();

			String pathCarpetaPortafirmas = DocumentosAlfrescoUtils.calcularRutaPortafirmas();
			String nombreCarpetaUsuario = (documento.getSubidoPorDNI().matches("\\d.*")?"_":"")  + documento.getSubidoPorDNI();
			String pathCarpetaUsuario = pathCarpetaPortafirmas + separator + nombreCarpetaUsuario;
			String[] fechaActualArray = DateUtils.getDate(new Date());
			String nombreCarpetaAnnio = "_" + fechaActualArray[0];
			String pathCarpetaAnnio = pathCarpetaUsuario + separator + nombreCarpetaAnnio;

			// Comprobar si existe la ruta donde va el documento
			boolean crearCarpetaUsuario = false;
			boolean crearCarpetaAnnio = false;
			if (!ars.existsNodeByPath(pathCarpetaAnnio)) {
				crearCarpetaAnnio = true;
				if (!ars.existsNodeByPath(pathCarpetaUsuario)) {
					crearCarpetaUsuario = true;
				}
			}
			
			if (crearCarpetaUsuario) {
				parameters.setPath(pathCarpetaPortafirmas);
				parameters.setNodeName(nombreCarpetaUsuario);
				parameters.setNodeType(AlfRepositoryConstants.TYPE_FOLDER);
				ars.createNode(parameters);	
			}
			
			if (crearCarpetaAnnio) {
				parameters.setPath(pathCarpetaUsuario);
				parameters.setNodeName(nombreCarpetaAnnio);
				parameters.setNodeType(AlfRepositoryConstants.TYPE_FOLDER);
				ars.createNode(parameters);	
			}
			
			// Comprobar si existe un documento con el mismo nombre. En caso afirmativo, renombrarlo simulando distintas versiones.
			if (!crearCarpetaUsuario && !crearCarpetaUsuario) {
				parameters.setPath(pathCarpetaAnnio);
				List<String> nombresDocs = ars.getDocumentsNamesInFolder(parameters);
				if (nombresDocs != null && !nombresDocs.isEmpty()) {
					boolean sigue = true;
					int nombreContador = 0;
					String nombreActual = nombreDoc;
					while (sigue) {
						if (nombresDocs.contains(nombreActual)) {
							LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] El documento '" + nombreDoc + "' ya se encuentra ubicado en su directorio de Alfresco", 
									backofficeId, usuarioSolicitante);
							nombreContador++;
							nombreActual = manejadorDocumentos.versionarNombreDocumento(nombreDoc, nombreContador);
						} else {
							sigue = false;
							nombreDoc = nombreActual;
						}
					}
				}
			}
			
			datosNodo.setRuta(pathCarpetaAnnio);
			datosNodo.setNombreNodo(nombreDoc);
			datosNodo.setTipo(TipoNodoEnum.DOCUMENTO);
			datosNodo.setContentType(contentType);
			datosNodo.setContenido(new String(content));
			params.getDatosNodo().add(datosNodo);

			FacadeMarshallUnMarshallUtils<ParametrosSGD> marshallUtilSGD = new FacadeMarshallUnMarshallUtils<ParametrosSGD>(
					FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE,FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
			// Alta a traves del servicio de GSD
			String resultadoXML = gestorDocumental.crearNodo((marshallUtilSGD.marshall_IN(params)));
			FacadeMarshallUnMarshallUtils<es.apt.ae.facade.ws.params.gestor.documental.out.Resultado> marshallUtilGSDout = 
					new FacadeMarshallUnMarshallUtils<es.apt.ae.facade.ws.params.gestor.documental.out.Resultado>(
						FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE,FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
			Date creacionDocumento = new Date();
			es.apt.ae.facade.ws.params.gestor.documental.out.Resultado resultado = marshallUtilGSDout.unmarshall_OUT(resultadoXML);
			LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Tiempo de creacion del documento en Alfresco: " + PerformanceUtils.tiempoTranscurridoDesde(creacionDocumento.getTime()));

			// Si todo va bien se almacena en bd
			if (resultado != null && resultado.getRespuesta() != null) {
				Respuesta respuesta = resultado.getRespuesta();
				if (respuesta.getCodigo().equals(Constantes.FWS_GD_COD_SUCCESSFUL)) {
					documento.setNombre(nombreDoc);
					documento.setUri(resultado.getUri());
					portafirmasDao.save(documento);
					LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Se ha aportado el documento correctamente '" + nombreDoc + "'", backofficeId, usuarioSolicitante);
				} else {
					LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] " + respuesta.getDescripcion(), null, backofficeId, usuarioSolicitante);
					throw new PortafirmasFacadeException(respuesta.getDescripcion());
				}
			} else {
				LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se pudo aportar el documento.", null, backofficeId, usuarioSolicitante);
				throw new PortafirmasFacadeException("No se pudo aportar el documento.");
			}
		} catch (RepositoryException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Error subiendo el documento a Alfresco", e, backofficeId, usuarioSolicitante);
			throw new PortafirmasFacadeException(e);			
//		} catch (GestorDocumentalFacadeException e) {
//			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Error subiendo el documento a Alfresco", e, backofficeId, usuarioSolicitante);
//			throw new PortafirmasFacadeException(e);
		} catch (GenericFacadeException e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el parametro de fachada donde se encuentra la ruta de Alfresco para portafirmas", 
					e, backofficeId, usuarioSolicitante);
			throw new PortafirmasFacadeException(e);
		} catch (Exception e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Error al persistir documento en base de datos, se eliminara el documento de Alfresco", e, backofficeId, usuarioSolicitante);
			try {
				params.setCredenciales(credenciales);
				DatosNodo nodo = new DatosNodo();
				nodo.setUri(documento.getUri());
				params.getDatosNodo().add(nodo);
				FacadeMarshallUnMarshallUtils<ParametrosSGD> paramsConverter = new FacadeMarshallUnMarshallUtils<ParametrosSGD>(
						FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE,FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
//				gestorDocumental.eliminarNodo(paramsConverter.marshall_IN(params));
//			} catch (GestorDocumentalFacadeException e1) {
//				LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se pudo eliminar el documento de Alfresco", e, backofficeId, usuarioSolicitante);
//				throw new PortafirmasFacadeException("No se pudo eliminar el documento de Alfresco", e);
//			} catch (JAXBException e1) {
//				LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se pudo eliminar el documento de Alfresco", e, backofficeId, usuarioSolicitante);
//				throw new PortafirmasFacadeException("No se pudo eliminar el documento de Alfresco", e);
			}catch (Exception ee) {
				// TODO: handle exception
			}
			throw new PortafirmasFacadeException();
		}

		Boolean result = false;
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Tiempo general de subida de documento: " + PerformanceUtils.tiempoTranscurridoDesde(globalProcess.getTime()), 
				backofficeId, usuarioSolicitante);
		return result;
	}

	@WebMethod(exclude = true)
	@Override
	public List<Persona> consultarRevisoresByDNI(String dni) throws PortafirmasFacadeException {
		if (dni != null) {
			return portafirmasDao.getRevisoresByDNI(dni);
		}
		return null;
	}

	@WebMethod(exclude = true)
	@Override
	public void actualizarDocumento(String usuario, DocumentoPortafirmas documentoPortafirmas) throws PortafirmasFacadeException {
		String backofficeId = FacadeBean.USUARIO_PORTAFIRMAS;
		String usuarioSolicitante = usuario;
		try {
			HashMap<String, Object> userInfo = AuthenticationHelper.getUserInfoByNumIdentification(null, null, usuario);
			if (userInfo != null && userInfo.containsKey(LoginModuleConstants.PRINCIPAL_USERNAME)) {
				usuarioSolicitante = (String)userInfo.get(LoginModuleConstants.PRINCIPAL_USERNAME);
			}
		} catch (Exception e) {}
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'actualizarDocumento' ...", backofficeId, usuarioSolicitante);
		
		try {
			portafirmasDao.save(documentoPortafirmas);
		} catch (Exception e) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se ha podido actualizar el documento", e, backofficeId, usuarioSolicitante);
			throw new PortafirmasFacadeException("No se ha podido actualizar el documento", e);
		}
	}

	@WebMethod(exclude = true)
	@Override
	public List<Accion> getListAcciones() throws PortafirmasFacadeException {
		return portafirmasDao.getlistAccion();
	}

	@WebMethod(exclude = true)
	@Override
	public List<EstadoDocumento> getListEstadoDocumento() throws PortafirmasFacadeException {
		return portafirmasDao.getlistEstadoDocumento();
	}

	@WebMethod(exclude = true)
	@Override
	public List<TipoCircuito> getListTipoCircuito() throws PortafirmasFacadeException {
		return portafirmasDao.getlistTipoCircuito();
	}

	@WebMethod(exclude = true)
	@Override
	public List<TipoGrupo> getListTipoGrupo() throws PortafirmasFacadeException {
		return portafirmasDao.getlistTipoGrupo();
	}

	@WebMethod(exclude = true)
	@Override
	public Ausencia getPersonaAusencia(String dni) throws PortafirmasFacadeException {
		return portafirmasDao.getPersonaAusencia(dni);
	}

	@WebMethod(exclude = true)
	@Override
	public Ausencia saveAusencia(Ausencia ausencia) throws PortafirmasFacadeException {

		return portafirmasDao.saveAusencia(ausencia);
	}

	/*
	 * El hasmap devuelve dos valores, uno es la persona actualizada, identificado con la clave persona y otro noAlmacenados que son las personas que por algÃºn motivo no se han definido como
	 * colaboradores
	 */
	@WebMethod(exclude = true)
	@Override
	public HashMap<String, Object> saveColaboradores(Persona persona, List<Persona> listColaboradores) throws PortafirmasFacadeException {

		HashMap<String, Object> resultado = new HashMap<String, Object>();
		resultado.put("noAlmacenados", portafirmasDao.saveRevision(persona, listColaboradores));
		resultado.put("persona", portafirmasDao.getPersonaByDNI(persona.getNumIdentificacion()));
		return resultado;
	}

	@WebMethod(exclude = true)
	@Override
	public List<Persona> getListRevisoresDe(String dni) throws PortafirmasFacadeException {
		try {
			return portafirmasDao.getListRevisoresDe(dni);
		} catch (Exception e) {
			throw new PortafirmasFacadeException("Error", e);
		}
	}

	@WebMethod(exclude = true)
	@Override
	public List<Persona> getSustitucionByDNI(String dni) throws PortafirmasFacadeException {
		return portafirmasDao.getSustitucionByDNI(dni);
	}
	
	@WebMethod(exclude = true)
	@Override
	public RespuestaVisualizacionDocumento obtenerVisualizacionDocumento(String usuarioSolicitante, String uri) throws PortafirmasFacadeException {
		String backofficeId = FacadeBean.USUARIO_PORTAFIRMAS;
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'obtenerVisualizacionDocumento' ...", backofficeId, usuarioSolicitante);
		
		DocumentoPortafirmas docPortafirmas = portafirmasDao.getDocumentoPortafirmasByUri(uri);
		if (docPortafirmas == null || docPortafirmas.getId() == null) {
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el documento con uri " + uri + " del portafirmas", null, 
					backofficeId, usuarioSolicitante);
			throw new PortafirmasFacadeException("No se ha podido recuperar el documento con uri " + uri + " del portafirmas");
		}	
		
		RespuestaVisualizacionDocumento resultado = new RespuestaVisualizacionDocumento();
		
        // Documento de expediente una vez tramitado
		if (docPortafirmas.getSistemaOrigen() != null && 
				(docPortafirmas.getSistemaOrigen().getCodigo().equals(PortafirmasFacadeDAO.GE) ||
						docPortafirmas.getSistemaOrigen().getCodigo().equals(PortafirmasFacadeDAO.SIDOPU)) &&
				(docPortafirmas.getEstadoDocumento().getCodigo().equals(PortafirmasFacadeDAO.FIRM) ||
						docPortafirmas.getEstadoDocumento().getCodigo().equals(PortafirmasFacadeDAO.RECH) ||
						docPortafirmas.getEstadoDocumento().getCodigo().equals(PortafirmasFacadeDAO.RECP))) {
//			try {
				List<String> listaUuids = Arrays.asList(uri);
				HashMap<String, DocumentoRepositorioItem> docsEntradaInfoMap = new HashMap<String, DocumentoRepositorioItem>();
				docsEntradaInfoMap.put(uri, new DocumentoRepositorioItem(uri));
				List<DocumentoVisualizacion> documentosSalidaOk = new ArrayList<DocumentoVisualizacion>();
				List<DocumentoSalidaError> documentosSalidaError = new ArrayList<DocumentoSalidaError>();
//				Map<String, es.apt.ae.facade.entities.Documento> documentosExpMap = expedientesDao.getDocumentosExpedienteByUris(null, null, listaUuids);
//				ExpedientesFacadeBeanHelper expHelper = new ExpedientesFacadeBeanHelper(expedientesDao);
//				expHelper.obtenerVisualizacionDocumentos(backofficeId, docsEntradaInfoMap,documentosExpMap, documentosSalidaOk, documentosSalidaError);
				if (documentosSalidaOk != null && !documentosSalidaOk.isEmpty()) {
					DocumentoVisualizacion docSalida = documentosSalidaOk.get(0);
					resultado.setNombre(docSalida.getNombre());
					resultado.setContenido(docSalida.getContenido());
				}			
//			} catch (GestorDocumentalFacadeException e) {
//				LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Ha ocurrido un problema en la fachada del gestor documental", e, backofficeId, usuarioSolicitante);
//				throw new PortafirmasFacadeException("Ha ocurrido un problema en la fachada del gestor documental", e);
//			} catch (GenericFacadeException e) {
//				LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Ha ocurrido un problema en la fachada de expedientes", e, backofficeId, usuarioSolicitante);
//				throw new PortafirmasFacadeException("Ha ocurrido un problema en la fachada de expedientes", e);			
//			}
		} else {
			DocumentosManager manejadorDocumentos = null; // TODO new DocumentosManager(gestorDocumental);
			resultado = null; // TODO manejadorDocumentos.obtenerVisualizacionDocumento(uri, Constantes.GESTOR_DOCUMENTAL_USERNAME, Constantes.GESTOR_DOCUMENTAL_PASSWORD);
		}
		
		return resultado;
	}

	@WebMethod(exclude = true)
	@Override
	public es.apt.ae.facade.dto.Resultado recuperarDocumentosFirmar(List<DocumentoPortafirmas> listDocumentos, String usuarioSolicitante, Persona persona, boolean clienteFirmaWeb) 
				throws PortafirmasFacadeException {
		String backofficeId = FacadeBean.USUARIO_PORTAFIRMAS;
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'recuperarDocumentosFirmar' ...", backofficeId, usuarioSolicitante);
		
		es.apt.ae.facade.dto.Resultado resultado = new es.apt.ae.facade.dto.Resultado();
		
		Map<String, DocumentoRepositorioItem> docsOkMap = new HashMap<String, DocumentoRepositorioItem>();
		
		List<String> listUris = new ArrayList<String>();
		for (DocumentoPortafirmas documento : listDocumentos) {// Para cada documento
			if (null != persona) {
				Persona sustituto = comprobarSustitucion(persona, documento);
				// Si la persona indicada es sustituto en el grupo a actuar, o es el firmante solicitado
				if (documento.getCircuito() != null) {
					for (Grupo grupo : documento.getCircuito().getListGrupo()) {
						if (grupo.getOrden() >= documento.getCircuito().getOrdenActivo()) {
							for (GrupoPersona grupoPersona : grupo.getGrupoPersona()) {
								if ((grupoPersona.getPersona().equals(persona) || grupoPersona.getPersona().equals(sustituto)) && !listUris.contains(documento.getUri())) {
									listUris.add(documento.getUri());
									break;
								}
							}
						}
					}
				} else {// Si tiene circuito nulo, comprobar que el estado es pendiente de circuito y que la persona es su propietario
					if (documento.getEstadoDocumento().getCodigo().equals("PEND") && documento.getSubidoPorDNI().equals(persona.getNumIdentificacion())) {
						listUris.add(documento.getUri());
					}
				}
			} else {
				listUris.add(documento.getUri());
			}
		}

		if (!listUris.isEmpty()) {
			if (clienteFirmaWeb) {
				try {
					IRepositoryService ars = AlfRepositoryService.getInstance(FacadeBean.RUTA_FICHERO_CONFIGURACION);
					Parameters parameters = new Parameters();
					parameters.setUuids(listUris);
					Map<String, Boolean> urisExistentesMap = ars.existsNodesByUris(parameters);
					if (urisExistentesMap != null) {
						for (DocumentoPortafirmas documento : listDocumentos) {
							if (urisExistentesMap.containsKey(documento.getUri()) && urisExistentesMap.get(documento.getUri())) {
								DocumentoRepositorioItem docItem = new DocumentoRepositorioItem();
								docItem.setUri(documento.getUri());
								docsOkMap.put(documento.getUri(), docItem);
							}
						}
					}
				} catch (Exception e) {
					logger.error("[FACHADA-SRV_PORTAFIRMAS] Error recuperando documentos a firmar del repositorio");
				}
			} else {
				ParametrosSGD parametros = new ParametrosSGD();
				Credenciales credenciales = new Credenciales();
				credenciales.setUsuario(Constantes.GESTOR_DOCUMENTAL_USERNAME);
				credenciales.setPassword(Constantes.GESTOR_DOCUMENTAL_PASSWORD);
				parametros.setCredenciales(credenciales);
				
				for (String uri : listUris) {// Se contruye un nodo para cada uri
					DatosNodo nodo = new DatosNodo();
					nodo.setUri(uri);
					nodo.setRecuperarContenido(true);
					parametros.getDatosNodo().add(nodo);
				}
	
				// Enviamos y recogemos el resultado
				es.apt.ae.facade.ws.params.gestor.documental.out.Resultado resultadoGD = gestorDocumental.consultarNodoAltoVolumen(parametros);
				// Recorremos el resultado para construir nuestra respuesta
				for (NodoPadre nodoPadre : resultadoGD.getNodoPadre()) {
					for (DocumentoPortafirmas documento : listDocumentos) {
						if (documento.getUri().equals(nodoPadre.getUri())) {
							try {
								DocumentoRepositorioItem docItem = null;
								if (documento.getTipoFirma().equalsIgnoreCase(XADES)) {
									docItem = crearDocumentoParaFirmaXADES(nodoPadre);
								} else if (documento.getTipoFirma().equalsIgnoreCase(PADES)) {
									docItem = crearDocumentoParaFirmaPADES(nodoPadre);
								} else {
									LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Tipo de firma no soportado para el documento con uri '" + nodoPadre.getUri() + "'",  
											null, backofficeId, usuarioSolicitante);
								}
								if (docItem != null) {
									docsOkMap.put(documento.getUri(), docItem);
								}
							} catch (DigiDocException e) {
								LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Error recuperando el contenido del documento con uri '" + nodoPadre.getUri() + "'", e, 
										backofficeId, usuarioSolicitante);
							} catch (Exception e) {
								LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Error recuperando el contenido del documento con uri '" + nodoPadre.getUri() + "'", e, 
										backofficeId, usuarioSolicitante);
							}
							break;
						}
					}
				}
			}
		}
		
		if (docsOkMap.size() == listDocumentos.size()) {
			resultado.setStatus(es.apt.ae.facade.dto.Resultado.SUCCESS);
			resultado.setData(docsOkMap);
		} else if (docsOkMap.size() == 0) {
			resultado.setStatus(es.apt.ae.facade.dto.Resultado.FAIL);
			resultado.setMessage("No ha sido posible recuperar la información de ninguno de los documentos a firmar");
		} else {
			resultado.setStatus(es.apt.ae.facade.dto.Resultado.WARN);
			resultado.setMessage("No ha sido posible recuperar la información de alguno de los documentos a firmar");
			resultado.setData(docsOkMap);
		}
		return resultado;
	}

	private DocumentoRepositorioItem crearDocumentoParaFirmaPADES(NodoPadre nodoPadre) {
		if (nodoPadre == null || nodoPadre.getContenido() == null) {
			return null;
		}
		
		DocumentoRepositorioItem docItem = new DocumentoRepositorioItem();
		byte[] document = nodoPadre.getContenido();
		docItem.setContenidoStr(new String(document));
		docItem.setUri(nodoPadre.getUri());
		return docItem;
	}
	
	private DocumentoRepositorioItem crearDocumentoParaFirmaXADES(NodoPadre nodoPadre) throws DigiDocException {
		if (nodoPadre == null || nodoPadre.getContenido() == null) {
			return null;
		}

		DocumentoRepositorioItem docItem = new DocumentoRepositorioItem();
		byte[] document = Base64.decodeBase64(nodoPadre.getContenido());
		byte[] digest = SignedDoc.digest(document);// Solo en caso de firma XAdES, en PAdES debe pasarse el contenido tal cual
		docItem.setContenidoStr(SignedDoc.bin2hex(digest));
		docItem.setUri(nodoPadre.getUri());
		for (Metadato metadato : nodoPadre.getMetadatos().getMetadato()) {
			if (metadato.getNombre().endsWith(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS)) {
				docItem.setFirmas(metadato.getValor()!=null?(String[])metadato.getValor().toArray(new String[0]):null);
				break;
			}
		}
		return docItem;
	}
	
	@WebMethod(exclude = true)
	@Override
	public HashMap<String, Object> almacenarFirmaXADES(String usuarioSolicitante, HashMap<String, List<String>> solicitud) throws PortafirmasFacadeException {
		
		String backofficeId = FacadeBean.USUARIO_PORTAFIRMAS;
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'almacenarFirmaXADES' ...", backofficeId, usuarioSolicitante);		
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<String> actualizadosOk = new ArrayList<String>();
		List<String> actualizadosError = new ArrayList<String>();
		result.put("actualizadosOK", actualizadosOk);
		result.put("actualizadosError", actualizadosError);
		// Recuperar del repositorio el metadato de firma de los documentos a firmar.
		HashMap<String, String[]> firmasExistentes = new HashMap<String, String[]>();
		IRepositoryService ars = null;
		Parameters parameters = new Parameters();
		try {
			ars = AlfRepositoryService.getInstance(FacadeBean.RUTA_FICHERO_CONFIGURACION);
			List<String> uris = new ArrayList<String>();
			for (String uri : solicitud.keySet()) {
				uris.add(uri);
			}
			parameters.setUuids(uris);
			parameters.setPropertiesNames(new HashSet<String>(Arrays.asList(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS)));
			HashMap<String, Properties> docsMetadata = ars.getNodesMetadata(parameters);
			for (String uri : solicitud.keySet()) {
				Properties docMetadata = docsMetadata.get(uri);
				String[] firmas = null;
				if (docMetadata != null && docMetadata.containsKey(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS)) {
					Object firmasObj = docMetadata.get(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS);
					firmas = MetadataUtils.formatMetadatoFirmasToArray(firmasObj);
				}
				firmasExistentes.put(uri, firmas);
			}
		} catch (RepositoryException e) {
			String error = "[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar el metadato de firma para los documentos";
			LoggerUtils.showError(logger, error, e, backofficeId, usuarioSolicitante);
			throw new PortafirmasFacadeException(error, e);
		}

		for (String uri : solicitud.keySet()) {
			try {
				String[] firmasAnterioresDoc = firmasExistentes.get(uri);
				int totalFirmas = (firmasAnterioresDoc == null ? 0 : firmasAnterioresDoc.length) + 1;
				String[] firmas = new String[totalFirmas];
				int i = 0;
				// Firmas ya existentes
				if (firmasAnterioresDoc != null) {
					for (String firma : firmasAnterioresDoc) {
						firmas[i] = new String(Base64.encodeBase64(firma.getBytes()));
						i++;
					}
				}
				// Nueva firma
				for (String firma : solicitud.get(uri)) {
					firmas[i] = firma;
				}

				// Se añade el aspecto AspectoDocumento si es necesario.
				parameters.setUuids(new ArrayList<String>(Arrays.asList(uri)));
				parameters.getAspects().add(AlfRepositoryConstants.ASPECT_DOCUMENTO);
				ars.addAspects(parameters);	
				
				// Actualizacion del metadato. 
				parameters.getMetadata().put(MetadataUtils.NOMBRE_METADATO_DOC_TIPO_FIRMA, XADES);// PRUEBA
				parameters.getMetadata().put(MetadataUtils.NOMBRE_METADATO_DOC_FIRMAS, firmas);
				ars.updateMetadata(parameters);
				actualizadosOk.add(uri);
			} catch (Exception e) {
				String error = "[FACHADA-SRV_PORTAFIRMAS] No se ha podido almacenar la firma XADES para el documento con uri '" + uri + "'";
				LoggerUtils.showError(logger, error, e, backofficeId, usuarioSolicitante);
				actualizadosError.add(uri);
				throw new PortafirmasFacadeException(error, e);
			}
		}
		return result;
	}

	@WebMethod(exclude = true)
	@Override
	public HashMap<String, Object> almacenarFirmaPADES(String usuarioSolicitante, HashMap<String, List<String>> solicitud) throws PortafirmasFacadeException {
		
		String backofficeId = FacadeBean.USUARIO_PORTAFIRMAS;
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'almacenarFirmaPADES' ...", backofficeId, usuarioSolicitante);			
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<String> actualizadosOk = new ArrayList<String>();
		List<String> actualizadosError = new ArrayList<String>();
		result.put("actualizadosOK", actualizadosOk);
		result.put("actualizadosError", actualizadosError);
		IRepositoryService ars = null;
		Parameters parameters = new Parameters();
		try {
			ars = AlfRepositoryService.getInstance(FacadeBean.RUTA_FICHERO_CONFIGURACION);
		} catch (RepositoryException e) {
			String error = "[FACHADA-SRV_PORTAFIRMAS] No se ha podido instanciar API de Alfresco";
			LoggerUtils.showError(logger, error, e, backofficeId, usuarioSolicitante);
			throw new PortafirmasFacadeException(error, e);
		}

		for (String uri : solicitud.keySet()) {
			try {

				byte[] contenidoFirmado = solicitud.get(uri).get(0).getBytes();
				// Actualizacion del contenido
				parameters.setUuids(new ArrayList<String>(Arrays.asList(uri)));
				parameters.setContent(Base64.decodeBase64(contenidoFirmado));
				// TODO: metadato tipo firma a pades
				parameters.getMetadata().put(MetadataUtils.NOMBRE_METADATO_DOC_TIPO_FIRMA, PADES);
				parameters.setContentType(FileUtils.APPLICATION_PDF);
				ars.updateDocument(parameters);
				actualizadosOk.add(uri);
			} catch (Exception e) {
				String error = "[FACHADA-SRV_PORTAFIRMAS] No se ha podido almacenar la firma PADES para el documento con uri '" + uri + "'";
				LoggerUtils.showError(logger, error, e, backofficeId, usuarioSolicitante);
				actualizadosError.add(uri);
				throw new PortafirmasFacadeException(error, e);
			}
		}
		return result;
	}

	@WebMethod(exclude = true)
	@Override
	public HashMap<String, Object> getDocumentoByUri(String usuarioSolicitante, String uri) {
		String backofficeId = FacadeBean.USUARIO_PORTAFIRMAS;
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'getDocumentoByUri' ...", backofficeId, usuarioSolicitante);	
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			DocumentoPortafirmas documento = portafirmasDao.getDocumentoPortafirmasByUri(uri);
			result.put("documento", documento);
			result.put("resultado", true);
			LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Documento con uri '" + uri + "' recuperado correctamente", backofficeId, usuarioSolicitante);		
		} catch (Exception e) {
			result.put("resultado", false);
			result.put("exception", e);
			LoggerUtils.showError(logger, "[FACHADA-SRV_PORTAFIRMAS] Ocurrio un error al recuperar el documento con uri '" + uri + "'", e, backofficeId, usuarioSolicitante);
		}

		return result;
	}

	@WebMethod(exclude = true)
	@Override
	public HashMap<String, Object> permitirVisualizarEstado(String uri, String usuarioSolicitante) {
		String backofficeId = FacadeBean.USUARIO_PORTAFIRMAS;
		LoggerUtils.showInfo(logger, "[FACHADA-SRV_PORTAFIRMAS] Ejecutando el metodo 'permitirVisualizarEstado' ...", backofficeId, usuarioSolicitante);	

		HashMap<String, Object> result = new HashMap<String, Object>();

		String numIdentificacion = null;
		try {
			HashMap<String, Object> userInfo = AuthenticationHelper.getUserInfoByDisplayName(null, null, usuarioSolicitante);
			if (userInfo != null && userInfo.containsKey(LoginModuleConstants.PRINCIPAL_NAME)) {
				numIdentificacion = (String)userInfo.get(LoginModuleConstants.PRINCIPAL_NAME);
			}
		} catch (Exception e) {
			String error = "[FACHADA-SRV_PORTAFIRMAS] No se ha podido recuperar la informacion del usuario solicitante necesaria";
			LoggerUtils.showError(logger, error, e, backofficeId, usuarioSolicitante);
			result.put("acceso", false);
			result.put("mensaje", "No se ha podido determinar si la persona tiene acceso al documento");
			return result;
		}
		
		try {
			List<String> whiteList = portafirmasDao.findWhiteListByURI(uri);
			for (String whiteDNI : whiteList) {
				if (whiteDNI.equals(numIdentificacion)) {
					String msg = "[FACHADA-SRV_PORTAFIRMAS] La persona tiene acceso al documento con uri '" + uri + "'";
					LoggerUtils.showInfo(logger, msg, backofficeId, usuarioSolicitante);
					result.put("acceso", true);
					result.put("mensaje", "La persona tiene acceso al documento");
					return result;
				}
			}
			
			// Si la persona no tiene acceso porque no forma parte del circuito, comprobar si es un documento de expediente y el usuario tiene permiso sobre el mismo
			es.apt.ae.facade.entities.Documento documento = null;// expedientesDao.getDocumentoExpedienteByUri(uri);
			// Si es un documento de expediente 
			if (documento != null) {
				try {
					IRepositoryService ars = AlfRepositoryService.getInstance(FacadeBean.RUTA_FICHERO_CONFIGURACION);
					Parameters params = new Parameters();
					params.setUserConsult(usuarioSolicitante);
					params.setPermission(Parameters.LECTOR);
					params.setUuids(Arrays.asList(uri));
					HashMap<String, Boolean> urisAutorizadas = ars.hasPermissions(params);
					if (null != urisAutorizadas && Boolean.TRUE.equals(urisAutorizadas.get(uri))) {
						result.put("acceso", true);
						result.put("mensaje", "La persona tiene acceso al documento");
						return result;
					}
				} catch (RepositoryException e) {
					LoggerUtils.showError(logger, "Error comprobando si el usuario " + usuarioSolicitante + " tiene permisos sobre el documento con uri " + uri, e, 
							backofficeId, usuarioSolicitante);
				}	
			}
			String error = "[FACHADA-SRV_PORTAFIRMAS] La persona no tiene acceso al documento con uri '" + uri + "'";
			LoggerUtils.showError(logger, error, null, backofficeId, usuarioSolicitante);
			result.put("acceso", false);
			result.put("mensaje", "La persona no tiene acceso al documento");
		} catch (Exception e) {
			String error = "[FACHADA-SRV_PORTAFIRMAS] No se ha encontrado el documento con uri '" + uri + "'";
			LoggerUtils.showError(logger, error, e, backofficeId, usuarioSolicitante);
			result.put("acceso", false);
			result.put("mensaje", "No se ha encontrado el documento");
		}
		return result;
	}

	private void eliminarDocumentoPortafirmas(String usuarioSolicitante, List<DocumentoPortafirmas> listDocumentos, String username, String password) throws PortafirmasFacadeException {
		try {
			ParametrosSGD params = new ParametrosSGD();

			Credenciales credenciales = new Credenciales();
			credenciales.setUsuario(username);
			credenciales.setPassword(password);
			params.setCredenciales(credenciales);

			for (DocumentoPortafirmas documento : listDocumentos) {
				DatosNodo nodo = new DatosNodo();
				nodo.setUri(documento.getUri());
				params.getDatosNodo().add(nodo);
			}
			FacadeMarshallUnMarshallUtils<ParametrosSGD> paramsConverter = new FacadeMarshallUnMarshallUtils<ParametrosSGD>(
					FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_IN_PACKAGE,FacadeMarshallUnMarshallUtils.URI_GESTOR_DOCUMENTAL_OUT_PACKAGE);
//			gestorDocumental.eliminarNodo(paramsConverter.marshall_IN(params));

			portafirmasDao.removeListDocumento(listDocumentos);
//		} catch (GestorDocumentalFacadeException e) {
//			String error = "[FACHADA-SRV_PORTAFIRMAS] Error al eliminar el documento/s de Alfresco";
//			LoggerUtils.showError(logger, error, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioSolicitante);
//			throw new PortafirmasFacadeException(error, e);
//		} catch (JAXBException e) {
//			String error = "[FACHADA-SRV_PORTAFIRMAS] Error al parsear los datos";
//			LoggerUtils.showError(logger, error, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioSolicitante);
//			throw new PortafirmasFacadeException(error, e);			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	private CircuitoEntity duplicarCircuito(CircuitoEntity circuitoEntity) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
		CircuitoEntity entity = new CircuitoEntity();

		List<Grupo> listGrupos = new ArrayList<Grupo>();
		int orden = 1;
		for (Grupo grupo : circuitoEntity.getListGrupo()) {
			Grupo grupoEntidad = new Grupo();
			grupoEntidad.setFirmantesRequeridos(new Integer(grupo.getFirmantesRequeridos()).intValue());
			grupoEntidad.setTipoGrupo(portafirmasDao.getTipoGrupobyCod(grupo.getTipoGrupo().getCodigo()));

			for (GrupoPersona grupoPersonaAux : grupo.getGrupoPersona()) {
				Persona personaEntidad = portafirmasDao.getPersonaByDNI(grupoPersonaAux.getPersona().getNumIdentificacion());
				GrupoPersona grupoPersona = new GrupoPersona();
				grupoPersona.setPersona(personaEntidad);
				grupoPersona.setGrupo(grupoEntidad);
				grupoEntidad.getGrupoPersona().add(grupoPersona);
			}

			grupoEntidad.setOrden(orden);
			orden++;
			listGrupos.add(grupoEntidad);
		}

		entity.setListGrupo(listGrupos);
		TipoCircuito tipoCircuito = portafirmasDao.getTipoCircuitobyCod(es.apt.ae.facade.ws.params.portafirmas.common.TipoCircuito.GNRD.toString());

		entity.setTipoCircuito(tipoCircuito);

		return entity;
	}

	private Persona comprobarSustitucion(Persona persona, DocumentoPortafirmas documento) {
		if (documento.getCircuito() != null) {
			for (Grupo grupo : documento.getCircuito().getListGrupo()) {
				if (grupo.getOrden() == documento.getCircuito().getOrdenActivo()) {
					boolean encontrado = false;
					for (GrupoPersona grupoPersona : grupo.getGrupoPersona()) {
						// TODO: REVISAR si se compara correctamente
						if (grupoPersona.getPersona() == persona) {
							encontrado = true;
							break;
						}
					}
					if (!encontrado) {// Buscamos a quien esta sustitullendo y si dicha persona esta
						List<Persona> listPersona = portafirmasDao.getSustitucionByDNI(persona.getNumIdentificacion());
						for (Persona persAux : listPersona) {
							for (GrupoPersona grupoPersona : grupo.getGrupoPersona()) {
								// TODO: REVISAR si se compara correctamente
								if (persAux.getNumIdentificacion().equals(grupoPersona.getPersona().getNumIdentificacion())) {
									return persAux;
								}
							}
						}
					}
					break;
				}
			}
		}
		return null;
	}

	private boolean validarDatos(List<DocumentoPortafirmas> documentosPortafirmas) {
		boolean resultado = true;

		for (DocumentoPortafirmas documentoPortafirmas:documentosPortafirmas) {
			if (documentoPortafirmas.getId() != null) {
				String estado = documentoPortafirmas.getEstadoDocumento().getCodigo();
				if (estado.equalsIgnoreCase("TRAM")) {
					resultado = false;
					break;
				}
			}
		}
		return resultado;
	}
	
	@WebMethod(exclude = true)
	@Override
	public List<BackOffice> getAllBackoffice(){
		return portafirmasDao.getAllBackoffice();
	}
	
	@WebMethod(exclude = true)
	@Override
	public List<CatPropiedadesConfiguracion> getPropiedadesConfiguracion(){
		return portafirmasDao.getPropiedadesConfiguracion();
	}

	@Override
	public Map<String, String> consultarDescripcionesBackoffices() {
		Map<String, String> backofficesMap = new HashMap<String, String>();
		List<BackOffice> backoffices = portafirmasDao.consultarDescripcionesBackoffices();
		if (null != backoffices) {
			for (BackOffice backoffice:backoffices) {
				backofficesMap.put(backoffice.getUsername(), backoffice.getDescripcion());
			}
		}
		return backofficesMap;
	}
	
	public Map<String, String> consultarUsuariosDepartamentosMap(List<String> departamentos, boolean incluirUsuariosPrueba) {
		Map<String, String> usuariosDepartamentosMap = new HashMap<String, String>();
		List<UsuarioItem> usuariosDepartamentosList = null;
		try {
			usuariosDepartamentosList = GenericServiceCore.consultarUsuariosDepartamentos(departamentos, incluirUsuariosPrueba);
		} catch (GenericFacadeException e) {
			logger.error("Error consultando listado de usuarios de departamentos", e);
		}
		if (null != usuariosDepartamentosList) {
			for (UsuarioItem usuarioItem:usuariosDepartamentosList) {
				usuariosDepartamentosMap.put(usuarioItem.getUsername().toUpperCase(), usuarioItem.getNombreApellidos());
			}
		}
		return usuariosDepartamentosMap;
	}

	@Override
	public HashMap<String, HashMap<String, Object>> getUsersAE(List<String> departamentos) {
		List<DataBaseUser> users = portafirmasDao.obtenerUsuarios(departamentos);
		HashMap<String, HashMap<String, Object>> resultMap = new HashMap<String, HashMap<String,Object>>();
		for (DataBaseUser dataBaseUser : users) {
					HashMap<String, Object> result = new HashMap<String, Object>();
					result.put(LoginModuleConstants.PRINCIPAL_NAME, dataBaseUser.getId());
					result.put(LoginModuleConstants.PRINCIPAL_NOMBRE, dataBaseUser.getName());
					result.put(LoginModuleConstants.PRINCIPAL_SOLO_NOMBRE, dataBaseUser.getOnlyName());
					result.put(LoginModuleConstants.PRINCIPAL_APELLIDO, dataBaseUser.getSurname());
					result.put(LoginModuleConstants.PRINCIPAL_USERNAME, dataBaseUser.getUser());
					result.put(LoginModuleConstants.PRINCIPAL_EMAIL, dataBaseUser.getEmail());
					resultMap.put(dataBaseUser.getId(), result);
		}
		return resultMap;
	}

	@Override
	public User doLogin(String username, String password) {
		return (User) portafirmasDao.doLogin(username,password);
	}
}
