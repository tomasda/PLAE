package es.apt.ae.facade.ejb.autenticacion;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import com.opencanarias.api.security.utils.LoginModuleConstants;
//import com.opencanarias.ejb.terceros.TercerosFacadeLocal;
import com.opencanarias.exceptions.GenericFacadeException;
//import com.opencanarias.exceptions.TercerosFacadeException;
import com.opencanarias.security.authentication.AuthenticationHelper;
import com.opencanarias.utils.ConfigUtils;
import com.opencanarias.utils.Constantes;
import com.opencanarias.utils.constants.ConstantesAutenticacion;

//import es.apt.ae.facade.dto.certification.BaseCertUserPrincipal;
import es.apt.ae.facade.ejb.autenticacion.exceptions.AutenticacionFacadeException;
import es.apt.ae.facade.ejb.autenticacion.utils.DatosJWT;
import es.apt.ae.facade.ejb.autenticacion.utils.JJWTService;
import es.apt.ae.facade.ws.params.autenticacion.in.Login;
import es.apt.ae.facade.ws.params.autenticacion.out.Resultado;
import es.apt.ae.facade.ws.params.commons.out.Usuario;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Stateless
public class AutenticacionFacadeBean implements AutenticacionFacadeLocal, AutenticacionFacadeRemote{

	private static Logger logger = Logger.getLogger(AutenticacionFacadeBean.class);
	
	private static final java.lang.String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
	private static final java.lang.String END_CERT = "-----END CERTIFICATE-----";

//	@EJB
//	TercerosFacadeLocal tercerosFacade;
//	@EJB
//	GenericFacadeServiceLocal genericFacade;
	
	
	@WebMethod(exclude = true)
	@Override
	public Resultado identificarPorCertificadoTercero(X509Certificate[] certs, String headerCert) throws AutenticacionFacadeException {
		try {
			X509Certificate cert = null;
			if (null != certs && certs.length > 0) {
				cert = certs[0];
			} else {
                if (headerCert != null && !headerCert.equals("(null)")) {
	                headerCert = headerCert.replaceAll(BEGIN_CERT, "").replaceAll(END_CERT, "");
	            	InputStream is = new ByteArrayInputStream(Base64.getMimeDecoder().decode(headerCert));
	            	CertificateFactory cf = CertificateFactory.getInstance("X.509");
	            	cert = (X509Certificate) cf.generateCertificate(is);
                } else {
                	throw new AutenticacionFacadeException(Constantes.VALIDAR_CERT_ERROR_NO_LEIDO);
                }
			}
			return identificarPorCertificadoTercero(cert);
		} catch (CertificateException e) {
			throw new AutenticacionFacadeException(e);
		}
	}
	
	@WebMethod(exclude = true)
	@Override
	public Resultado identificarPorCertificadoTercero(byte[] certificado) throws AutenticacionFacadeException {
		try {
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			InputStream in = new ByteArrayInputStream(certificado);
			X509Certificate cert = (X509Certificate) certFactory.generateCertificate(in);
			return identificarPorCertificadoTercero(cert);
		} catch (CertificateException e) {
			throw new AutenticacionFacadeException(e);
		}
	}
	
	private Resultado identificarPorCertificadoTercero(X509Certificate cert) throws AutenticacionFacadeException {
		Resultado resultado = new Resultado();
//		try {
//			if(cert != null) {
//				byte[] certBytes = cert.getEncoded();
//
//	            es.apt.ae.facade.dto.Resultado resultadoValidacionCert = genericFacade.validarCertificado(certBytes, Constantes.VALIDAR_CERT_SERVICIOS_SEDE);
//	            if (!es.apt.ae.facade.dto.Resultado.SUCCESS.equals(resultadoValidacionCert.getCode())) {
//	            	throw new AutenticacionFacadeException(resultadoValidacionCert.getMessage());
//	            }
//	            BaseCertUserPrincipal baseCert = (BaseCertUserPrincipal)resultadoValidacionCert.getData();
//				CertificadoInfo certificadoInfo = new CertificadoInfo();
//				certificadoInfo.setPersonaId(baseCert.getPersonIdentifier());
//				certificadoInfo.setPersonaNombre(baseCert.getPersonName());
//				certificadoInfo.setEntidadId(baseCert.getEntityIdentifier());
//				certificadoInfo.setEntidadNombre(baseCert.getEntityName());
//				certificadoInfo.setRepresentante(baseCert.isRepresentative());
//				try {
//					certificadoInfo.setFechaVigenciaDesde(DateUtils.dateToXMLGregorianCalendar(baseCert.getValidityPeriodFrom()));
//					certificadoInfo.setFechaVigenciaHasta(DateUtils.dateToXMLGregorianCalendar(baseCert.getValidityPeriodTo()));
//				} catch (DatatypeConfigurationException e) {
//					logger.error("No se ha podido convertir las fechas de validez del certificado a XMLGregorianCalendar");
//				}
//				certificadoInfo.setCertBase64(baseCert.getCertBase64());
//				certificadoInfo.setCertNumeroSerieHex(baseCert.getCertSerialNumberHex());
//				resultado.setCertificadoInfo(certificadoInfo);
//	            String personaId = baseCert.getPersonIdentifier();
//	            String entidadId = baseCert.getEntityIdentifier();
//				es.apt.ae.facade.ws.params.terceros.in.Parametros params = new es.apt.ae.facade.ws.params.terceros.in.Parametros();
//				FiltroBusquedaTerceroExtendido filtroBusquedaTercero = new FiltroBusquedaTerceroExtendido();
//				Credenciales credenciales = new Credenciales();
//				params.setFiltroBusquedaExtendido(filtroBusquedaTercero);
//				params.setCredenciales(credenciales);		
//				credenciales.setUsuario(FacadeBean.USUARIO_GESTOR_EXPEDIENTES);
//				credenciales.setPassword(FacadeBean.USUARIO_GESTOR_EXPEDIENTES);
//				ParametroConConcordancia paramNumDoc = new ParametroConConcordancia();
//				paramNumDoc.setConcordancia(ConcordanciaEnum.EXACTA);
//				paramNumDoc.setValue(personaId);
//				filtroBusquedaTercero.setNumeroDocumento(paramNumDoc);
//				String jwtSubject = null;
//				es.apt.ae.facade.ws.params.terceros.out.Resultado tercerosResultado = tercerosFacade.buscarTercerosEJB(params);
//				if (tercerosResultado.getRespuesta().getCodigo().equalsIgnoreCase(Constantes.FWS_TERCEROS_COD_SUCCESSFUL)) {
//					if (tercerosResultado.getTerceros() != null && tercerosResultado.getTerceros().getTercero() != null && 
//							!tercerosResultado.getTerceros().getTercero().isEmpty()) {	
//						Tercero tercero = tercerosResultado.getTerceros().getTercero().get(0);
//						jwtSubject = personaId;
//						Representados representados = tercero.getRepresentados();
//						// Si el certificado es de persona vinculada a entidad/representante, retornar únicamente el representado indicado en el certificado.
//						if (!StringUtils.isNullOrEmpty(entidadId) && null != representados && null != representados.getRepresentado() && 
//								!representados.getRepresentado().isEmpty()) {
//							tercero.setRepresentados(new Representados());
//							for (Tercero representado:representados.getRepresentado()) {
//								if (!StringUtils.isNullOrEmpty(representado.getNumeroDocumento()) && entidadId.toUpperCase().equals(representado.getNumeroDocumento().toUpperCase())) {
//									tercero.getRepresentados().getRepresentado().add(representado);
//									jwtSubject += "|" + entidadId;
//									break;
//								}
//							}
//						}
//						String jwt = JJWTService.getInstance().generarJWTTercero(jwtSubject);
//						resultado.setToken(jwt);
//						resultado.setTercero(tercero);					
//					}
//				} else {
//					throw new AutenticacionFacadeException(tercerosResultado.getRespuesta().getDescripcion());
//				}
//			} else { 
//				throw new AutenticacionFacadeException(Constantes.VALIDAR_CERT_ERROR_NO_ESPECIFICADO);
//			}
//		} catch (TercerosFacadeException e) {
//			throw new AutenticacionFacadeException(e);
//		} catch (CertificateException e) {
//			throw new AutenticacionFacadeException(e);
//		} catch (NoSuchAlgorithmException e) {
//			throw new AutenticacionFacadeException(e);
//		} catch (IOException e) {
//			throw new AutenticacionFacadeException(e);
//		} catch (AutenticacionFacadeException e) {
//			throw e;	
//		} catch (GenericFacadeException e) {
//			throw new AutenticacionFacadeException(e);
//		}
		
		return resultado;
	}
	
	@WebMethod(exclude = true)
	@Override
	public Resultado seleccionarAccesoTercero(String personaId, String entidadId) throws AutenticacionFacadeException {
		Resultado resultado = new Resultado();
//		try {
//			es.apt.ae.facade.ws.params.terceros.in.Parametros params = new es.apt.ae.facade.ws.params.terceros.in.Parametros();
//			FiltroBusquedaTerceroExtendido filtroBusquedaTercero = new FiltroBusquedaTerceroExtendido();
//			Credenciales credenciales = new Credenciales();
//			params.setFiltroBusquedaExtendido(filtroBusquedaTercero);
//			params.setCredenciales(credenciales);		
//			credenciales.setUsuario(FacadeBean.USUARIO_GESTOR_EXPEDIENTES);
//			credenciales.setPassword(FacadeBean.USUARIO_GESTOR_EXPEDIENTES);
//			ParametroConConcordancia paramNumDoc = new ParametroConConcordancia();
//			paramNumDoc.setConcordancia(ConcordanciaEnum.EXACTA);
//			paramNumDoc.setValue(personaId);
//			filtroBusquedaTercero.setNumeroDocumento(paramNumDoc);
//			String jwtSubject = null;
//			es.apt.ae.facade.ws.params.terceros.out.Resultado tercerosResultado = tercerosFacade.buscarTercerosEJB(params);
//			if (tercerosResultado.getRespuesta().getCodigo().equalsIgnoreCase(Constantes.FWS_TERCEROS_COD_SUCCESSFUL)) {
//				if (tercerosResultado.getTerceros() != null && tercerosResultado.getTerceros().getTercero() != null && 
//						!tercerosResultado.getTerceros().getTercero().isEmpty()) {	
//					Tercero tercero = tercerosResultado.getTerceros().getTercero().get(0);
//					jwtSubject = personaId;
//					Representados representados = tercero.getRepresentados();
//					// Si el certificado es de persona vinculada a entidad/representante, retornar únicamente el representado indicado en el certificado.
//					if (!StringUtils.isNullOrEmpty(entidadId) && null != representados && null != representados.getRepresentado() && 
//							!representados.getRepresentado().isEmpty()) {
//						tercero.setRepresentados(new Representados());
//						for (Tercero representado:representados.getRepresentado()) {
//							if (!StringUtils.isNullOrEmpty(representado.getNumeroDocumento()) && entidadId.toUpperCase().equals(representado.getNumeroDocumento().toUpperCase())) {
//								tercero.getRepresentados().getRepresentado().add(representado);
//								jwtSubject += "|" + entidadId;
//								break;
//							}
//						}
//					} else {
//						tercero.setRepresentados(null);
//					}
//					String jwt = JJWTService.getInstance().generarJWTTercero(jwtSubject);
//					resultado.setToken(jwt);
//					resultado.setTercero(tercero);					
//				}
//			} else {
//				throw new AutenticacionFacadeException(tercerosResultado.getRespuesta().getDescripcion());
//			}
//		} catch (TercerosFacadeException e) {
//			throw new AutenticacionFacadeException(e);
//		} catch (NoSuchAlgorithmException e) {
//			throw new AutenticacionFacadeException(e);
//		} catch (IOException e) {
//			throw new AutenticacionFacadeException(e);
//		} catch (AutenticacionFacadeException e) {
//			throw e;	
//		} catch (GenericFacadeException e) {
//			throw new AutenticacionFacadeException(e);
//		}
		
		return resultado;
	}

	@Override
	public Resultado identificarPorCertificadoInterno(byte[] certificate) throws AutenticacionFacadeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validarToken(String token) {
		try {
			JJWTService.getInstance().validarJWT(token);
			return true;
		} catch (ExpiredJwtException e) {
			return false;
		} catch (UnsupportedJwtException e) {
			return false;
		} catch (MalformedJwtException e) {
			return false;
		} catch (SignatureException e) {
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		} catch (IOException e) {
			return false;
		} catch (GenericFacadeException e) {
			return false;
		}
	}

	@Override
	public Resultado getTerceroByToken(String token) throws AutenticacionFacadeException {
		try {
			DatosJWT datos = JJWTService.getInstance().obtenerDatosJWT(token);
			String jwtSubject = datos.getUsuario();
			String[] tercerosIds = jwtSubject.split("\\|");
			String personaId = tercerosIds[0];
			String entidadId = null;
			if (tercerosIds.length == 2) {
				entidadId = tercerosIds[1];
			}
			return seleccionarAccesoTercero(personaId, entidadId);
		} catch ( ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException | IOException | GenericFacadeException e) {
			logger.error(e);
			throw new AutenticacionFacadeException(e);
		}
	}


	@Override
	public Resultado loginByToken(String token) throws AutenticacionFacadeException {
		try {
			Resultado resultado = new Resultado();
			if (token != null && !token.isEmpty()) {
				JJWTService.getInstance().validarJWT(token);
				DatosJWT datos = JJWTService.getInstance().obtenerDatosJWT(token);
				Usuario usuario = buildUser(datos.getUsuario());
				resultado.setToken(token);
				resultado.setUsuario(usuario);
			}
			return resultado;
		} catch (Exception e) {
			throw new AutenticacionFacadeException(e);
		}
	}
	
	@Override
	public Resultado login(Login login) throws AutenticacionFacadeException {
		Resultado resultado = new Resultado();
		try {
			if (!(login.getUsername() == null || login.getUsername().isEmpty() || login.getPassword() == null || login.getPassword().isEmpty()) 
					&& AuthenticationHelper.authenticateUser(login.getUsername(), login.getPassword())) {		
				String jjwt = JJWTService.getInstance().generarJWT(login.getUsername(), login.getPassword());	
				resultado.setToken(jjwt);
				Usuario usuario = buildUser(login.getUsername());
				resultado.setUsuario(usuario);
				return resultado;			
			}
		} catch (NamingException | NoSuchAlgorithmException | IOException | GenericFacadeException e) {
			logger.error(e);
			throw new AutenticacionFacadeException(e);
		}
		throw new AutenticacionFacadeException("Datos incorrectos");
	}
	
	private Usuario buildUser(String username) throws GenericFacadeException, NamingException {
		String ldapUsername = ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USERNAME);
		String ldapPassword = ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_PASSWORD);
		HashMap<String, Object> usuarioInfoMap = AuthenticationHelper.getUserInfoByDisplayName(ldapUsername, ldapPassword, username);
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setIdentificacion((String)usuarioInfoMap.get(LoginModuleConstants.PRINCIPAL_NAME));
		usuario.setNombre((String)usuarioInfoMap.get(LoginModuleConstants.PRINCIPAL_NOMBRE));
		usuario.setApellido1((String)usuarioInfoMap.get(LoginModuleConstants.PRINCIPAL_APELLIDO));
		usuario.setNombreApellidos((String)usuarioInfoMap.get(LoginModuleConstants.PRINCIPAL_NOMBRE));
		String[] rolesArr = (String[])usuarioInfoMap.get(LoginModuleConstants.PRINCIPAL_ROLES);
		if (rolesArr != null && rolesArr.length > 0) {
			usuario.getRoles().addAll(Arrays.asList(rolesArr));
		}
		return usuario;
	} 
	
}
