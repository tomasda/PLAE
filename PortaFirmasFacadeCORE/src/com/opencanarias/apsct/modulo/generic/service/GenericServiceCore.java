package com.opencanarias.apsct.modulo.generic.service;

import java.io.IOException;
import java.io.StringReader;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.xml.namespace.QName;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.rpc.ServiceException;

import org.apache.axis.Handler;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.codec.binary.Base64;
import org.jboss.logging.Logger;
import org.xml.sax.InputSource;

import com.opencan.repository.exceptions.RepositoryException;
import com.opencan.repository.implementations.alfresco.AlfRepositoryService;
import com.opencan.repository.interfaces.IRepositoryService;
import com.opencanarias.api.security.utils.LoginModuleConstants;
import com.opencanarias.apsct.modulo.generic.service.certificate.utils.AFirmaValidationResult;
import com.opencanarias.apsct.modulo.generic.service.certificate.utils.AFirmaValidationResultParser;
import com.opencanarias.apsct.modulo.generic.service.certificate.utils.AFirmaVerificationMethodError;
import com.opencanarias.apsct.modulo.generic.service.certificate.utils.ClientHandler;
import com.opencanarias.apsct.modulo.generic.service.client.EJBClient;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.exceptions.GenericFacadeException;
import com.opencanarias.security.authentication.AuthenticationHelper;
import com.opencanarias.utils.ConfigUtils;
import com.opencanarias.utils.Constantes;
import com.opencanarias.utils.DateUtils;
import com.opencanarias.utils.StringUtils;

import es.apt.ae.facade.dto.DepartamentoItem;
import es.apt.ae.facade.dto.ElementoItem;
import es.apt.ae.facade.dto.ProcedimientoItem;
import es.apt.ae.facade.dto.Resultado;
//import es.apt.ae.facade.dto.TipoActuacionItem;
import es.apt.ae.facade.dto.UsuarioItem;
import es.apt.ae.facade.entities.CatPropiedadesConfiguracion;
import es.apt.ae.facade.entities.Familia;
import es.apt.ae.facade.entities.Procedimiento;
import es.apt.ae.facade.entities.Rol;
//import es.apt.ae.facade.entities.actuaciones.CatActuaciones;
import es.apt.ae.facade.entities.utils.EntitiesUtils;

public class GenericServiceCore {

	private static Logger logger = Logger.getLogger(GenericServiceCore.class);
	
	private static final String	AFIRMA_WS_TIMEOUT 		= "afirma.ws.timeout";
	private static final String	AFIRMA_WS_CERTIFICATE_URL 		= "afirma.ws.certificate.url";
	private static final String AFIRMA_WS_CERTIFICATE_NS 		= "afirma.ws.certificate.ns";
	private static final String AFIRMA_WS_CERTIFICATE_XSI 		= "afirma.ws.certificate.xsi";
	private static final String AFIRMA_WS_CERTIFICATE_XSD 		= "afirma.ws.certificate.xsd";
	private static final String AFIRMA_WS_CERTIFICATE_REQUEST	= "afirma.ws.certificate.request";
	private static final String AFIRMA_WS_CERTIFICATE_VERSION	= "afirma.ws.certificate.version";
	private static final String AFIRMA_WS_CERTIFICATE_APPID	= "afirma.ws.certificate.appid";
	private static final String AFIRMA_WS_AUTHORIZATION_METHOD	= "afirma.ws.authorization.method";
	private static final String AFIRMA_WS_AUTHORIZATION_NAME	= "afirma.ws.authorization.name";
	private static final String AFIRMA_WS_AUTHORIZATION_PASSWORD	= "afirma.ws.authorization.password";
	private static final String AFIRMA_WS_AUTHORIZATION_PASSWORD_TYPE	= "afirma.ws.authorization.passwordType";
//	// Almacen de confianza para conexiones seguras
//	private static final String	TRUSTEDSTORE_PATH = "com.trustedstore.path";
//	private static final String	TRUSTEDSTORE_PASSWORD = "com.trustedstore.password";
//	private static final String	TRUSTEDSTORE_TYPE = "com.trustedstore.type";
	
	
	public static List<UsuarioItem> consultarUsuariosDepartamento(String departamento, boolean incluirUsuariosPrueba) throws GenericFacadeException {
		List<UsuarioItem> usuarios = new ArrayList<UsuarioItem>();
		
		try {
			HashMap<String, HashMap<String, Object>> usersInfo = AuthenticationHelper.getDepartamentUsers(departamento, null);
			if (usersInfo != null) {
				Iterator<Entry<String, HashMap<String, Object>>> itUsers = usersInfo.entrySet().iterator();
				while (itUsers.hasNext()) {
					Entry<String, HashMap<String, Object>> entryUser = itUsers.next();
					UsuarioItem usuario = new UsuarioItem();
					String currentUser = entryUser.getKey();
					usuario.setIdentificacion(currentUser);
					usuario.setNombreApellidos(currentUser);
					HashMap<String, Object> currentUserInfo = entryUser.getValue();
					boolean incluir = true;
					if (currentUserInfo != null && !currentUserInfo.isEmpty()) {
						usuario.setNombreApellidos((String)currentUserInfo.get(LoginModuleConstants.PRINCIPAL_NOMBRE));
						usuario.setCorreoElectronico((String)currentUserInfo.get(LoginModuleConstants.PRINCIPAL_EMAIL));
						if (!incluirUsuariosPrueba) {
							String[] roles = (String[])currentUserInfo.get("roles");
							if (roles != null && Arrays.asList(roles).contains(AuthenticationHelper.ROL_USUARIO_PRUEBAS)) {
								incluir = false;
							}
						}
					}
					if (incluir) {
						usuarios.add(usuario);
					}
				}
			}
			logger.info("El listado de usuarios del departamento '" + departamento + "' se ha obtenido con exito");
		} catch(GenericFacadeException e) {
			logger.error("Error al consultar el listado de usuarios en el LDAP.");
			throw e;
		} catch(NamingException e) {
			logger.error("Error al consultar el listado de usuarios en el LDAP.");
			throw new GenericFacadeException(e);	
		}
		
	   Collections.sort(usuarios, new Comparator<UsuarioItem>() {
	        public int compare(UsuarioItem u1, UsuarioItem u2) {
	            return u1.getNombreApellidos().compareTo(u2.getNombreApellidos());
	        }
	    });
		
		return usuarios;
	}
	
	public static List<UsuarioItem> consultarUsuariosDepartamentos(List<String> departamentos, boolean incluirUsuariosPrueba) throws GenericFacadeException {
		List<UsuarioItem> usuarios = new ArrayList<UsuarioItem>();
		HashMap<String, HashMap<String, Object>> usersInfo = null;
		/**
		 * 2019-12-19
		 * Validación contra el LDAP
		 * Configurar la opción validación contra BBDD
		 */
		List<CatPropiedadesConfiguracion> properties = EJBClient.getSrvGenericFacadeRemote().getPropiedadesConfiguracion();
		boolean dbOrLdap = false;
		for (CatPropiedadesConfiguracion catPropiedadesConfiguracion : properties) {
			if(catPropiedadesConfiguracion.getId().equals("portafirmas.config.users")){
				dbOrLdap=Boolean.valueOf(catPropiedadesConfiguracion.getValor());
			}
		}
		if (dbOrLdap) {
			/*
			 * Database
			 */
			logger.info("Obteniendo los usuarios de Base de Datos");
				usersInfo = EJBClient.getSrvGenericFacadeRemote().getUsersAE(departamentos);
				if (usersInfo != null) {
					Iterator<Entry<String, HashMap<String, Object>>> itUsers = usersInfo.entrySet().iterator();
					while (itUsers.hasNext()) {
						Entry<String, HashMap<String, Object>> entryUser = itUsers.next();
						UsuarioItem usuario = new UsuarioItem();
						String currentUser = entryUser.getKey();
						usuario.setIdentificacion(currentUser);
						usuario.setNombreApellidos(currentUser);
						HashMap<String, Object> currentUserInfo = entryUser.getValue();
						boolean incluir = true;
						if (currentUserInfo != null && !currentUserInfo.isEmpty()) {
							usuario.setNombreApellidos((String)currentUserInfo.get(LoginModuleConstants.PRINCIPAL_NOMBRE));
							usuario.setUsername((String)currentUserInfo.get(LoginModuleConstants.PRINCIPAL_USERNAME));
							usuario.setCorreoElectronico((String)currentUserInfo.get(LoginModuleConstants.PRINCIPAL_EMAIL));
							if (!incluirUsuariosPrueba) {
								String[] roles = (String[])currentUserInfo.get("roles");
								if (roles != null && Arrays.asList(roles).contains(AuthenticationHelper.ROL_USUARIO_PRUEBAS)) {
									incluir = false;
								}
							}
						}
						if (incluir) {
							usuarios.add(usuario);
						}
					}
				}
		}else {
			/*
			 * LDAP
			 */
			logger.info("Obteniendo los usuarios de LDAP");
			try {
				usersInfo = AuthenticationHelper.getUsersAE(departamentos);
				if (usersInfo != null) {
					Iterator<Entry<String, HashMap<String, Object>>> itUsers = usersInfo.entrySet().iterator();
					while (itUsers.hasNext()) {
						Entry<String, HashMap<String, Object>> entryUser = itUsers.next();
						UsuarioItem usuario = new UsuarioItem();
						String currentUser = entryUser.getKey();
						usuario.setIdentificacion(currentUser);
						usuario.setNombreApellidos(currentUser);
						HashMap<String, Object> currentUserInfo = entryUser.getValue();
						boolean incluir = true;
						if (currentUserInfo != null && !currentUserInfo.isEmpty()) {
							usuario.setNombreApellidos((String)currentUserInfo.get(LoginModuleConstants.PRINCIPAL_NOMBRE));
							usuario.setUsername((String)currentUserInfo.get(LoginModuleConstants.PRINCIPAL_USERNAME));
							usuario.setCorreoElectronico((String)currentUserInfo.get(LoginModuleConstants.PRINCIPAL_EMAIL));
							if (!incluirUsuariosPrueba) {
								String[] roles = (String[])currentUserInfo.get("roles");
								if (roles != null && Arrays.asList(roles).contains(AuthenticationHelper.ROL_USUARIO_PRUEBAS)) {
									incluir = false;
								}
							}
						}
						if (incluir) {
							usuarios.add(usuario);
						}
					}
				}
				logger.info("El listado de usuarios se ha obtenido con exito");
			} catch(GenericFacadeException e) {
				logger.error("Error al consultar el listado de usuarios en el LDAP.");
				throw e;
			} catch(NamingException e) {
				logger.error("Error al consultar el listado de usuarios en el LDAP.");
				throw new GenericFacadeException(e);	
			}
		}
		
	   Collections.sort(usuarios, new Comparator<UsuarioItem>() {
	        public int compare(UsuarioItem u1, UsuarioItem u2) {
	            return u1.getNombreApellidos().compareTo(u2.getNombreApellidos());
	        }
	    });
		
		return usuarios;
	}
	
	public static List<DepartamentoItem> consultarDepartamentos(EntityManager em) throws GenericFacadeException {
		List<DepartamentoItem> departamentos  = new ArrayList<DepartamentoItem>();
		List<Rol> departamentosBD = EntitiesUtils.getRolesDecretables(em);
		if (departamentosBD != null) {
			for (Rol dptBD:departamentosBD) {
				departamentos.add(new DepartamentoItem(dptBD.getId(), dptBD.getDescripcion()));
			}
		}
		
		return departamentos;
	}
	
	public static List<DepartamentoItem> consultarSubDepartamentos(String departamento, EntityManager em) throws GenericFacadeException {
		List<DepartamentoItem> gruposDepartamento  = new ArrayList<DepartamentoItem>();
		String nombreRolAlfresco = "";
		try {
			IRepositoryService ars = AlfRepositoryService.getInstance(FacadeBean.RUTA_FICHERO_CONFIGURACION);
			List<Rol> roles = EntitiesUtils.getRolesDecretables(em);
			HashMap<String, Rol> gruposAlfDptosMap = new HashMap<String, Rol>();
			if (roles != null) {
				for (Rol rol:roles) {
					if (rol.getGrupoAlfresco() != null && !rol.getGrupoAlfresco().trim().equals("")) {
						gruposAlfDptosMap.put(rol.getGrupoAlfresco(), rol);
						if (departamento.equalsIgnoreCase(rol.getId())) {
							nombreRolAlfresco = rol.getGrupoAlfresco();
						}
					}
				}
				if(!nombreRolAlfresco.isEmpty() && ars != null) {
					String[] grupos = ars.getGroups(nombreRolAlfresco);
					for (String grupo : grupos) {
						Rol rol = gruposAlfDptosMap.get(grupo.split("GROUP_")[1]);
						if(rol != null) {
							DepartamentoItem dptoItem = new DepartamentoItem(rol.getId(), rol.getDescripcion());
							gruposDepartamento.add(dptoItem);
						}
					}
				}
			}
		} catch (RepositoryException e) {
			logger.error("No se ha podido recuperar el listado de grupos a los que pertenece el departamento ' " + departamento + "'.");
		}
			
		return gruposDepartamento;
	}
	
	public static List<DepartamentoItem> consultarGruposAutorizadosDepartamento(String departamento, EntityManager em) throws GenericFacadeException {
		List<DepartamentoItem> gruposDepartamento  = new ArrayList<DepartamentoItem>();
		String nombreRolAlfresco = "";
		try {
			IRepositoryService ars = AlfRepositoryService.getInstance(FacadeBean.RUTA_FICHERO_CONFIGURACION);
			List<Rol> roles = EntitiesUtils.getRolesDecretables(em);
			HashMap<String, Rol> gruposAlfDptosMap = new HashMap<String, Rol>();
			if (roles != null) {
				for (Rol rol:roles) {
					if (rol.getGrupoAlfresco() != null && !rol.getGrupoAlfresco().trim().equals("")) {
						gruposAlfDptosMap.put(rol.getGrupoAlfresco(), rol);
						if (departamento.equalsIgnoreCase(rol.getId())) {
							nombreRolAlfresco = rol.getGrupoAlfresco();
						}
					}
				}
				if(!nombreRolAlfresco.isEmpty() && ars != null) {
					List<String> grupos = ars.getAuthorizedGroups(nombreRolAlfresco);
					for (String grupo : grupos) {
						Rol rol = gruposAlfDptosMap.get(grupo.split("GROUP_")[1]);
						if(rol != null) {
							DepartamentoItem dptoItem = new DepartamentoItem(rol.getId(), rol.getDescripcion());
							gruposDepartamento.add(dptoItem);
						}
					}
				}
			}
		} catch (RepositoryException e) {
			logger.error("No se ha podido recuperar el listado de grupos a los que pertenece el departamento ' " + departamento + "'.");
		}
			
		return gruposDepartamento;
	}
	
	public static List<DepartamentoItem> consultarGruposAutorizadosUsuario(String usuario, EntityManager em) throws GenericFacadeException {
		List<DepartamentoItem> gruposUsuario  = new ArrayList<DepartamentoItem>();
		List<String> gruposAlfUsuario = null;
		
		try {
			List<Rol> roles = EntitiesUtils.getRolesDecretables(em);
			HashMap<String, Rol> gruposAlfDptosMap = new HashMap<String, Rol>();
			if (roles != null) {
				for (Rol rol:roles) {
					if (rol.getGrupoAlfresco() != null && !rol.getGrupoAlfresco().trim().equals("")) {
						gruposAlfDptosMap.put(rol.getGrupoAlfresco(), rol);
					}
				}
				gruposAlfUsuario = AuthenticationHelper.getUserAllGroups(new ArrayList<String>(gruposAlfDptosMap.keySet()), usuario);
				if (gruposAlfUsuario != null && !gruposAlfUsuario.isEmpty()) {
					for (String grupoUsuario:gruposAlfUsuario) {
						Rol rol = gruposAlfDptosMap.get(grupoUsuario);
						DepartamentoItem dptoItem = new DepartamentoItem(rol.getId(), rol.getDescripcion(), rol.getGrupoAlfresco());
						gruposUsuario.add(dptoItem);
					}
				}
			}
		} catch (GenericFacadeException e) {
			logger.error("No se ha podido recuperar el listado de grupos a los que pertenece el usuario ' " + usuario + "'.", e);
		} catch (NamingException e) {
			logger.error("No se ha podido recuperar el listado de grupos a los que pertenece el usuario ' " + usuario + "'.", e);
		}			
		
		Collections.sort(gruposUsuario, new Comparator<DepartamentoItem>() {
	        public int compare(DepartamentoItem d1, DepartamentoItem d2) {
	            return d1.getDescripcion().compareTo(d2.getDescripcion());
	        }
	    });
	   
		return gruposUsuario;
	}
	
//	public static List<TipoActuacionItem> consultarTiposActuaciones(EntityManager em) throws GenericFacadeException {
//		List<TipoActuacionItem> tiposActuaciones  = new ArrayList<TipoActuacionItem>();
//		List<CatActuaciones> tiposActuacionesBD = EntitiesUtils.getListTipoActuacion(em);
//		if (tiposActuacionesBD != null) {
//			for (CatActuaciones tipoActBD:tiposActuacionesBD) {
//				tiposActuaciones.add(new TipoActuacionItem(tipoActBD.getId(), tipoActBD.getDescripcion(), tipoActBD.getProcesoId()));
//			}
//		}
//		
//		return tiposActuaciones;
//	}
	
	public static List<ElementoItem> consultarTiposDocumentos(EntityManager em) throws GenericFacadeException {
		List<ElementoItem> tiposDocumentos  = new ArrayList<ElementoItem>();
		HashMap<String, String> tiposDocumentosBDMap = EntitiesUtils.getTiposDocumentos(em);
		if (tiposDocumentosBDMap != null) {
			Iterator<Entry<String,String>> it = tiposDocumentosBDMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String,String> entry = it.next();
				tiposDocumentos.add(new ElementoItem(entry.getKey(), entry.getValue()));
			}
		}
		
		return tiposDocumentos;
	}
	
	public static List<ElementoItem> consultarFamilias(EntityManager em) throws GenericFacadeException {
		List<ElementoItem> familias  = new ArrayList<ElementoItem>();
		
		TreeMap<String, String> familiasMap = new TreeMap<String, String>();
		List<Familia> familiasBD = EntitiesUtils.getFamilias(em);
		if (familias != null) {
			for (Familia familiaBD:familiasBD) {
				familiasMap.put(familiaBD.getId(), familiaBD.getDescripcion());
			}
			Iterator<Entry<String,String>> it = familiasMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String,String> entry = it.next();
				familias.add(new ElementoItem(entry.getKey(), entry.getValue()));
			}
		}
		
		return familias;
	}
	
	public static List<ProcedimientoItem> consultarProcedimientos(String familiaId, EntityManager em) throws GenericFacadeException {
		List<ProcedimientoItem> procedimientos  = new ArrayList<ProcedimientoItem>();
		
		TreeMap<String, Procedimiento> procedimientosMap = new TreeMap<String, Procedimiento>();
		List<Procedimiento> procedimientosBD = EntitiesUtils.getProcedimientos(em);
		if (procedimientos != null) {
			for (Procedimiento procedimientoBD:procedimientosBD) {
				if (StringUtils.isNullOrEmpty(familiaId) || familiaId.equals(procedimientoBD.getFamilia().getId())) {
					procedimientosMap.put(procedimientoBD.getId(), procedimientoBD);
				}
			}
			Iterator<Entry<String,Procedimiento>> it = procedimientosMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String,Procedimiento> entry = it.next();
				Procedimiento procedimiento = entry.getValue();
				String procDescripcion = procedimiento.getDescripcion();
				Pattern pattern = Pattern.compile("^\\(([^)]+)\\)\\s+-\\s+(.*)");
				Matcher matcher = pattern.matcher(procDescripcion);
				if (matcher.find() && matcher.groupCount() == 2) {
					procDescripcion = matcher.group(2); 
				}
				procedimientos.add(new ProcedimientoItem(entry.getKey(), procDescripcion, procedimiento.getFamilia().getId()));
			}
		}
		
		return procedimientos;
	}
	
	/**
	 * Verifica un certificado obteniendo informacion detallada, mediante una llamada al servicio web de <code>@Firma</code>.<br>
	 * 
	 * @param X509Certificate
	 * @return AFirmaResultadoValidacionCertificado
	 * @throws GenericFacadeException 
	 */
	public static Resultado validateCertificateWithDetail(X509Certificate certificate) {		 
		Resultado resultado = new Resultado();

		long tiempoTotal = 0; 
		try {	
//			ConfigUtils.resetearConfiguracion();
//			ConfigUtils.setNombreFicheroConfiguracion("webservices.properties");
			String targetNamespaceValidarCert = ConfigUtils.getParametro(AFIRMA_WS_CERTIFICATE_NS);
			String xmlnsXsi = ConfigUtils.getParametro(AFIRMA_WS_CERTIFICATE_XSI);
			String xsdValidarCert = ConfigUtils.getParametro(AFIRMA_WS_CERTIFICATE_XSD);
			String servicio = ConfigUtils.getParametro(AFIRMA_WS_CERTIFICATE_REQUEST);
			String versionMsg = ConfigUtils.getParametro(AFIRMA_WS_CERTIFICATE_VERSION);
			String idAplicacion = ConfigUtils.getParametro(AFIRMA_WS_CERTIFICATE_APPID);
			String endpoint = ConfigUtils.getParametro(AFIRMA_WS_CERTIFICATE_URL);

			byte[] certBytes = certificate.getEncoded();
        	String certB64 = new String(Base64.encodeBase64(certBytes, true));
        	
			Properties clientHandlerInitProperties = generateHandlerProperties();
			Handler reqHandler = new ClientHandler(clientHandlerInitProperties);

			// Construimos el xml que se envia como parametro al servicio web de @firma
			String xmlEntrada = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><mensajeEntrada " +
							"xmlns=\"" + targetNamespaceValidarCert + "\" " +
							"xmlns:xsi=\"" + xmlnsXsi + "\" " + 
							"xsi:SchemaLocation=\"" + xsdValidarCert + "\">" + 
								"<peticion>" + servicio + "</peticion>" +
								"<versionMsg>" + versionMsg + "</versionMsg>" +
								"<parametros>" +
									"<certificado><![CDATA[" + certB64 + "]]></certificado>" +
									"<idAplicacion>" + idAplicacion + "</idAplicacion>" +
									"<modoValidacion>1</modoValidacion>" +
									"<obtenerInfo>false</obtenerInfo>" +
								"</parametros>" +
							"</mensajeEntrada>";

			// Se crea el servicio
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName("http://soapinterop.org/", servicio));
			call.setTimeout(new Integer(ConfigUtils.getParametro(AFIRMA_WS_TIMEOUT)));
			call.setClientHandlers(reqHandler, null);

 	        long tiempoInicio = System.currentTimeMillis();
			String xmlResultado = (String) call.invoke(new Object [] { xmlEntrada });
 			tiempoTotal = System.currentTimeMillis() - tiempoInicio;

 			AFirmaValidationResult resultValidacionCert = parseResultadoValidacionCertificado(xmlResultado);
			if (resultValidacionCert != null) {
				if (resultValidacionCert.getResult()) {
					resultado.setCode(Resultado.SUCCESS);
					if (resultValidacionCert.getSimpleValidation() != null) {
						resultado.setMessage(resultValidacionCert.getSimpleValidation().getResult());
					} else {
						resultado.setMessage(resultValidacionCert.getDescription());
					}
				} else {
					resultado.setCode(Resultado.FAIL);
					if (resultValidacionCert.getStateValidation() != null) {
						String msg = resultValidacionCert.getStateValidation().getDescription();
						List<AFirmaVerificationMethodError> methods = resultValidacionCert.getStateValidation().getMethods();
						if (methods != null && !methods.isEmpty()) {
							if (methods.get(0).getRevocationDate() != null) {
								msg += ": " + DateUtils.getDate(methods.get(0).getRevocationDate(), DateUtils.dateTimeFormat);
							}
						}
						resultado.setMessage(msg);
					} else {
						resultado.setMessage(resultValidacionCert.getDescription());
					}
				}
			} else {
				resultado.setCode(Resultado.FAIL);
				resultado.setMessage("No se ha podido validar el certificado.");
			}
		} catch (IOException e) {
			logger.error(Constantes.VALIDAR_CERT_ERROR_GENERAL, e);
			resultado.setCode(Resultado.FAIL);
			resultado.setMessage(Constantes.VALIDAR_CERT_ERROR_GENERAL);
		} catch (ServiceException e) {
			logger.error(Constantes.VALIDAR_CERT_ERROR_GENERAL, e);
			resultado.setCode(Resultado.FAIL);
			resultado.setMessage(Constantes.VALIDAR_CERT_ERROR_GENERAL);
		} catch (Exception e) {
			logger.error(Constantes.VALIDAR_CERT_ERROR_GENERAL, e);
			resultado.setCode(Resultado.FAIL);
			resultado.setMessage(Constantes.VALIDAR_CERT_ERROR_GENERAL);
		}
		
		logger.info("Tiempo Respuesta de peticion de validacion de certificado a @Firma [" + tiempoTotal + "]");
		return resultado;
	}
	
	private static Properties generateHandlerProperties() throws GenericFacadeException {
		Properties config = new Properties();
		config.setProperty("security.mode", ConfigUtils.getParametro(AFIRMA_WS_AUTHORIZATION_METHOD));
		config.setProperty("security.usertoken.user", ConfigUtils.getParametro(AFIRMA_WS_AUTHORIZATION_NAME));
		config.setProperty("security.usertoken.password", ConfigUtils.getParametro(AFIRMA_WS_AUTHORIZATION_PASSWORD));
		config.setProperty("security.usertoken.passwordType", ConfigUtils.getParametro(AFIRMA_WS_AUTHORIZATION_PASSWORD_TYPE));
		return config;
	}
	
	/*
	 * Parsea el xml resultado devuelto por el servicio web de @Firma de la validaciÃ³n de un certificado.
	 */
	private static AFirmaValidationResult parseResultadoValidacionCertificado(String xmlResultado) throws GenericFacadeException {
		AFirmaValidationResult result = null;
		StringReader stringReader = new StringReader(xmlResultado);
		InputSource iSource = new InputSource(stringReader);
		
		AFirmaValidationResultParser parser = new AFirmaValidationResultParser();
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		
		SAXParser saxParser;
		try {
			saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(iSource, parser);
			result = parser.getResultadoValidacionCert();
		} catch (Exception e) {
			throw new GenericFacadeException(Constantes.VALIDAR_CERT_ERROR_GENERAL, e);
		}
		
		return result;
	}
	
//	public static void main(String[] args) {
//		//String certBase64 = "MIIFSDCCBLGgAwIBAgIEPHuEVzANBgkqhkiG9w0BAQUFADA2MQswCQYDVQQGEwJFUzENMAsGA1UEChMERk5NVDEYMBYGA1UECxMPRk5NVCBDbGFzZSAyIENBMB4XDTA0MDUyMTE0MDYxM1oXDTA3MDUyMTE0MDYxM1owgYIxCzAJBgNVBAYTAkVTMQ0wCwYDVQQKEwRGTk1UMRgwFgYDVQQLEw9GTk1UIENsYXNlIDIgQ0ExEjAQBgNVBAsTCTUwMDA1MjE3NDE2MDQGA1UEAxMtTk9NQlJFIFlVQkVSTyBTQUxNT1JBTCBSQVFVRUwgLSBOSUYgMzA4MjIyNjlTMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDR7uVtr71haRsJ0+h1WR1zrNzGTU1VlgXwZJEPOzhbRjDrAUKhBOTGcX2HV53Y0WqIyTMphBJ3k38Tyc2c+boBHfUle/tCE4t2qM0TBMa3zc1ugrvoujdkdchoBQSIyaJHBSAVLZIlLYCTSogZIqAH8F+Lcro2lb8EpxuXk8cdPwIDAQABo4IDFDCCAxAwgZMGA1UdEQSBizCBiIEhUkFRVUVMLllVQkVST0BURUxWRU5ULkFCRU5HT0EuQ09NpGMwYTEYMBYGCSsGAQQBrGYBBBMJMzA4MjIyNjlTMRcwFQYJKwYBBAGsZgEDEwhTQUxNT1JBTDEVMBMGCSsGAQQBrGYBAhMGWVVCRVJPMRUwEwYJKwYBBAGsZgEBEwZSQVFVRUwwCQYDVR0TBAIwADArBgNVHRAEJDAigA8yMDA0MDUyMTE0MDYxM1qBDzIwMDcwNTIxMTQwNjEzWjALBgNVHQ8EBAMCBaAwEQYJYIZIAYb4QgEBBAQDAgWgMB0GA1UdDgQWBBTzt6auzjoUzctxJjR28t9/AH4ctzAfBgNVHSMEGDAWgBRAmnZEl3QHxKwUyx6NTzpFfDDXYTCCATEGA1UdIASCASgwggEkMIIBIAYJKwYBBAGsZgMFMIIBETA0BggrBgEFBQcCARYoaHR0cDovL3d3dy5jZXJ0LmZubXQuZXMvY29udmVuaW8vZHBjLnBkZjCB2AYIKwYBBQUHAgIwgcsagchDZXJ0aWZpY2FkbyBSZWNvbm9jaWRvIGV4cGVkaWRvIHNlZ/puIGxlZ2lzbGFjafNuIHZpZ2VudGUuVXNvIGxpbWl0YWRvIGEgbGEgQ29tdW5pZGFkIEVsZWN0cvNuaWNhIHBvciB2YWxvciBt4XhpbW8gZGUgMTAwIGUgc2Fsdm8gZXhjZXBjaW9uZXMgZW4gRFBDLkNvbnRhY3RvIEZOTVQ6Qy9Kb3JnZSBKdWFuIDEwNi0yODAwOS1NYWRyaWQtRXNwYfFhLjAdBgkrBgEEAaxmASEEEBYOUEVSU09OQSBGSVNJQ0EwLwYIKwYBBQUHAQMEIzAhMAgGBgQAjkYBATAVBgYEAI5GAQIwCxMDRVVSAgFkAgEAMFsGA1UdHwRUMFIwUKBOoEykSjBIMQswCQYDVQQGEwJFUzENMAsGA1UEChMERk5NVDEYMBYGA1UECxMPRk5NVCBDbGFzZSAyIENBMRAwDgYDVQQDEwdDUkwxNDEzMA0GCSqGSIb3DQEBBQUAA4GBAIFDkTk3y0V7i2j7DO747+OdZJodF+zWgXvqEzDq/kU8BEA62fxeo+bnVDbOjR1Ol9yDv3JhW7+Tlnmi+ZTRyOhZEtCYMaSX+09YqsB3YBusyUB46NzBkLA7fd1zk51zOlFBmBik0UosE6/jZths1T9iBKU/IYJspybcGe26U2Rq";
//		String certBase64 = "MIIEqzCCA5OgAwIBAgIQFt/K0GwzjUxaxzgA//160jANBgkqhkiG9w0BAQsFADBcMQswCQYDVQQGEwJFUzEoMCYGA1UECgwfRElSRUNDSU9OIEdFTkVSQUwgREUgTEEgUE9MSUNJQTENMAsGA1UECwwERE5JRTEUMBIGA1UEAwwLQUMgRE5JRSAwMDEwHhcNMTgwNDA2MDkwMzU5WhcNMjEwMjI2MjI1OTU5WjCBgDELMAkGA1UEBhMCRVMxEjAQBgNVBAUTCTk5OTk5MDE4RDERMA8GA1UEBAwIRmljdGljaW8xEjAQBgNVBCoMCUNpdWRhZGFubzE2MDQGA1UEAwwtRmljdGljaW8gUmV2b2NhZG8sIENpdWRhZGFubyAoQVVURU5USUNBQ0nDk04pMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApGxKynE6HxIu6l7mbz0vc/AA6z/NlxXIUmZXXf0aPkMEx9PLRDfjOPQnyaxZ3c6mVqrIff6aPjv6OO5F6eSjyzTLM8MF36yMekhkZEbGquDFS7N94SaJvh7rYYITVyDsDCcJg/pi+IGewH1WDG7vaaBnFJfColZM0MTp/CKwG1V1OoxOwYfzAHzTQwG+9TlJv3NO+CmlrdfMJJRnrxp4CTaMwnpo2nRmoi/VN0usFrZ/FqaCZtV5L5de3WrI5Js8DJETiCHmeLB0gTcmnzIBZg0unudQLASi8Hm8MNSiXeTSUZYBEJhXiyW+a/53mZ3jtsWi6+BweEPNgXXWhYTeuQIDAQABo4IBQjCCAT4wDAYDVR0TAQH/BAIwADAOBgNVHQ8BAf8EBAMCB4AwHQYDVR0OBBYEFF08070xYpkiCpm5/RGnFpyk3I8FMB8GA1UdIwQYMBaAFBqJqMXuj3ZdVXGJ8zs1vaoFAJVvMF8GCCsGAQUFBwEBBFMwUTAfBggrBgEFBQcwAYYTaHR0cDovL29jc3AuZG5pZS5lczAuBggrBgEFBQcwAoYiaHR0cDovL3d3dy5kbmllLmVzL2NlcnRzL0FDMDAxLmNydDB9BgNVHSAEdjB0MHIGCGCFVAECAgIEMGYwIgYIKwYBBQUHAgEWFmh0dHA6Ly93d3cuZG5pZS5lcy9kcGMwQAYIKwYBBQUHAgIwNAwyRElSRUNDScOTTiBHRU5FUkFMIERFIExBIFBPTElDw41BLCBWQVRFUy1TMjgxNjAxNUgwDQYJKoZIhvcNAQELBQADggEBAF/VwtvIizVHuVL/4j26UkHUsqQXurwy9tsOwnqOnxVgESJiapU5H3R5E84bPExhgNwh7isOaCkLLD+RsKfkroaQshyxDnRROyYCI4v1yii4wClT6oSeObheeA7w8FsAW/yHHyhZqPlwhI1kKYAfqdbTq2/F4kUcpcsyVB3heWzO+/05pgThvtBfbuTHbZX8r5ktl637lCsB0ARJZ9M83Qx3GmjR4TfP66ESU+WA9zf47leFriMHxm7RtQk2Oh0GKWz8PFnlaQP1SK/pg4TEFQAuyzBlmFwboe6fKn17f+ypsQKAoTn3FV7YkbUuW7Kl1Ih35oEWUAZurXIGT95F0EQ=";
//
//		try {
//			validateCertificateWithDetail(certBase64);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
}
