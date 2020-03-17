package com.opencanarias.security.authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;

import com.opencanarias.api.security.utils.LoginModuleConstants;
import com.opencanarias.api.security.utils.LoginUtils;
import com.opencanarias.exceptions.GenericFacadeException;
import com.opencanarias.utils.AuthenticationSettings;
import com.opencanarias.utils.ConfigUtils;
import com.opencanarias.utils.StringUtils;
import com.opencanarias.utils.constants.ConstantesAutenticacion;

import es.apt.ae.facade.dto.UsuarioItem;
import es.apt.ae.facade.ws.params.commons.in.Credenciales;
import es.apt.ae.facade.ws.params.portafirmas.common.InformacionSolicitante;
import es.opencanarias.managers.workflow.common.User;


public class AuthenticationHelper {
	
	public static final String ROL_OPERADOR_REGISTRO = "ResponsableRegistro";
	public static final String ROL_SECRETARIA_GRAL = "dptSecretariaGeneral";
	public static final String ROL_USUARIO_PRUEBAS = "rolPruebas";
	
	public static boolean authenticateUser (String username, String password) throws GenericFacadeException, NamingException{
		HashMap<String, String> options = new HashMap<String, String>();
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USER_BASE, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USER_BASE));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_OU, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_OU));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_LDAP_URL, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_LDAP_URL));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_EMAIL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_EMAIL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USERNAME, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USERNAME));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_PASSWORD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_PASSWORD));
		
		HashMap<String, Object> result = LoginUtils.doLogin(username, password, options);
		
		if (result != null && result.size() > 0){
			return true;
		}
		return false;
		
	}
	
	public static HashMap<String, Object> validateUser(String username, String password) throws GenericFacadeException, NamingException{
		HashMap<String, String> options = new HashMap<String, String>();
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USER_BASE, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USER_BASE));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_OU, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_OU));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_LDAP_URL, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_LDAP_URL));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_EMAIL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_EMAIL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_ROL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_ROL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USERNAME, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USERNAME));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_PASSWORD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_PASSWORD));
		
		HashMap<String, Object> result = LoginUtils.doLogin(username, password, options);
		
		return result;
	}	
	
	public static HashMap<String, HashMap<String, Object>> getDepartamentUsers(String departamentConsult, String userConsult) throws GenericFacadeException, NamingException{
		HashMap<String, String> options = new HashMap<String, String>();
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USER_BASE, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USER_BASE));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_OU, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_OU));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_LDAP_URL, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_LDAP_URL));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_EMAIL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_EMAIL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_ROL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_ROL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USERNAME, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USERNAME));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_PASSWORD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_PASSWORD));
		
		HashMap<String, HashMap<String, Object>> result = LoginUtils.getDepartamentUsers(departamentConsult, userConsult, options);
		
		return result;
	}
	
	public static HashMap<String, HashMap<String, Object>> getUsersAE(List<String> dptosList) throws GenericFacadeException, NamingException{
		HashMap<String, String> options = new HashMap<String, String>();
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USER_BASE, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USER_BASE));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_OU, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_OU));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_LDAP_URL, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_LDAP_URL));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_EMAIL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_EMAIL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_ROL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_ROL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USERNAME, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USERNAME));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_PASSWORD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_PASSWORD));
		
		HashMap<String, HashMap<String, Object>> result = LoginUtils.getUsersAE(options, dptosList);
		
		return result;
	}
	
	public static InformacionSolicitante getInformationByDisplayName(String username,String password, 
			String usernameConsult) throws GenericFacadeException, NamingException{
		HashMap<String, String> options = new HashMap<String, String>();
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USER_BASE, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USER_BASE));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_OU, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_OU));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_LDAP_URL, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_LDAP_URL));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_EMAIL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_EMAIL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USERNAME, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USERNAME));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_PASSWORD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_PASSWORD));
		
		HashMap<String, Object> result = LoginUtils.getInformationByDisplayName(username, password, usernameConsult, options);
		
		if (result != null && result.size() > 0){
			InformacionSolicitante informacionSolicitante = new InformacionSolicitante();
			// En principalNombre se traen nombres y apellidos
			informacionSolicitante.setNombre((String)result.get(LoginModuleConstants.PRINCIPAL_NOMBRE));
			//informacionSolicitante.setApellido1((String)result.get("principalApellido"));
			informacionSolicitante.setApellido1(null);
			informacionSolicitante.setApellido2(null);
			informacionSolicitante.setCorreoNotificacion((String)result.get(LoginModuleConstants.PRINCIPAL_EMAIL));
			informacionSolicitante.setDNINIE((String)result.get(LoginModuleConstants.PRINCIPAL_NAME));
			return informacionSolicitante;
		}
		
		return null;
	}
	
	public static HashMap<String, Object> getUserInfoByDisplayName(String username, String password, 
			String usernameConsult) throws GenericFacadeException, NamingException{
		HashMap<String, String> options = new HashMap<String, String>();
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USER_BASE, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USER_BASE));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_OU, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_OU));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_LDAP_URL, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_LDAP_URL));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_EMAIL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_EMAIL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_ROL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_ROL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USERNAME, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USERNAME));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_PASSWORD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_PASSWORD));
		
		HashMap<String, Object> result = LoginUtils.getInformationByDisplayName(username, password, usernameConsult, options);
		
		return result;
	}	
	
	public static HashMap<String, Object> getUserInfoByDisplayName(HashMap<String, String> options, String username, 
			String password, String usernameConsult) throws GenericFacadeException, NamingException {
		if (StringUtils.isNullOrEmpty(username)) {
			username = options.get("username");
		}
		if (StringUtils.isNullOrEmpty(password)) {
			username = options.get("password");
		}
		HashMap<String, Object> result = LoginUtils.getInformationByDisplayName(username, password, usernameConsult, options);
		
		return result;
	}
	
	public static InformacionSolicitante getInformationByNumIdentification(String username,String password, 
			String usernameConsult) throws GenericFacadeException, NamingException{
		HashMap<String, String> options = new HashMap<String, String>();
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_NUM_IDENTIFICATION, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_NUM_IDENTIFICATION));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USER_BASE, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USER_BASE));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_OU, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_OU));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_LDAP_URL, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_LDAP_URL));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_EMAIL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_EMAIL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USERNAME, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USERNAME));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_PASSWORD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_PASSWORD));
		
		HashMap<String, Object> result = LoginUtils.getInformationByNumIdentification(username, password, usernameConsult, options);
		
		if (result != null && result.size() > 0){
			InformacionSolicitante informacionSolicitante = new InformacionSolicitante();
			// En principalNombre se traen nombres y apellidos
			informacionSolicitante.setNombre((String)result.get(LoginModuleConstants.PRINCIPAL_NOMBRE));
			//informacionSolicitante.setApellido1((String)result.get("principalApellido"));
			informacionSolicitante.setApellido1(null);
			informacionSolicitante.setApellido2(null);
			informacionSolicitante.setCorreoNotificacion((String)result.get(LoginModuleConstants.PRINCIPAL_EMAIL));
			informacionSolicitante.setDNINIE((String)result.get(LoginModuleConstants.PRINCIPAL_NAME));
			return informacionSolicitante;
		}
		
		return null;
	}
	
	public static HashMap<String, Object> getUserInfoByNumIdentification(String username, String password, 
			String usernameConsult) throws GenericFacadeException, NamingException{
		HashMap<String, String> options = new HashMap<String, String>();
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_NUM_IDENTIFICATION, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_NUM_IDENTIFICATION));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USER_BASE, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USER_BASE));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_OU, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_OU));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_LDAP_URL, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_LDAP_URL));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_EMAIL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_EMAIL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_ROL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_ROL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USERNAME, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USERNAME));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_PASSWORD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_PASSWORD));
		
		if (username == null) {
			username =  ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USERNAME);
		}
		if (password == null) {
			password = ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_PASSWORD);
		}
		HashMap<String, Object> result = LoginUtils.getInformationByNumIdentification(username, password, usernameConsult, options);
		
		return result;
	}	
	
	public static Boolean isUserPruebas(String username, String password, String usernameConsultId) throws GenericFacadeException, NamingException{
		HashMap<String, Object> userProperties = getUserInfoByNumIdentification(username, password, usernameConsultId);
		if (userProperties != null) {
			String[] roles = (String[])userProperties.get(LoginModuleConstants.PRINCIPAL_ROLES);
			if (roles != null && Arrays.asList(roles).contains(ROL_USUARIO_PRUEBAS)) {
				return true;
			}
		}
		return false;
	}
	
	public static String getAuthenticatedUserId(String username, String password) throws GenericFacadeException, NamingException{
		HashMap<String, String> options = new HashMap<String, String>();
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USER_BASE, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USER_BASE));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_OU, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_OU));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_LDAP_URL, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_LDAP_URL));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_IDENTIFICATION_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_EMAIL_FIELD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_EMAIL_FIELD));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_USERNAME, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_USERNAME));
		options.put(ConstantesAutenticacion.FIELD_SECURITY_AUTHENTICATION_PASSWORD, ConfigUtils.getParametro(ConstantesAutenticacion.CONFIG_SECURITY_AUTHENTICATION_PASSWORD));
		HashMap<String, Object> result = LoginUtils.doLogin(username, password, options);
		
		if (result != null && result.size() > 0){
			return (String)result.get(LoginModuleConstants.PRINCIPAL_NAME);
		}
		
		return null;
	}	
	
	/**
	 * Funcion que se encarga de la autenticacion por usuario/contraseña de acceso a los servicios.
	 * 
	 * @param credenciales
	 * @see Credenciales
	 * @return TRUE=permitido, FALSE=denegado
	 */
	public static Boolean validateUser(Credenciales credenciales) {
		if (credenciales != null) {
			String user = credenciales.getUsuario();
			String pass = credenciales.getPassword();

			try {
				return authenticateUser(user, pass);
			} catch (GenericFacadeException e) {
				e.printStackTrace();
				return false;
			} catch (NamingException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}	
	
	/**
	 * Obtiene el identificador de usuario desde el LDAP
	 * 
	 * @param credenciales
	 * @see Credenciales
	 * @return Identificador de usuario desde el LDAP
	 */
	public static String getUserId(Credenciales credenciales) {
		if (credenciales != null) {
			String user = credenciales.getUsuario();
			String pass = credenciales.getPassword();

			try {
				return getAuthenticatedUserId(user, pass);
			} catch (GenericFacadeException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}	
	
	public static User retrieveUserInformationWF(String usuarioConsulta) throws NamingException, GenericFacadeException {
		if (usuarioConsulta != null && !usuarioConsulta.isEmpty()){
			User user = new User();
			HashMap<String,?> options = AuthenticationSettings.getOptions();
			String ldapUser = (String)options.get(LoginModuleConstants.OPTIONS_USERNAME);
			String password = (String)options.get(LoginModuleConstants.OPTIONS_PASSWORD);
			HashMap<String, Object> userPrincipal = LoginUtils.getInformationByNumIdentification(ldapUser, password, usuarioConsulta, options);
			user.setId(usuarioConsulta);
			user.setCommonName((String)userPrincipal.get(LoginModuleConstants.PRINCIPAL_NOMBRE));
			user.setLogin((String)userPrincipal.get(LoginModuleConstants.PRINCIPAL_USERNAME));
			String[] roles = (String[])userPrincipal.get(LoginModuleConstants.PRINCIPAL_ROLES);
			for (String rol : roles){
				user.getRoleNames().add(rol);
			}
			return user;
		}
		throw new GenericFacadeException("El usuario para realizar la consulta no ha sido indicado");
	}
	
	public static User retrieveUserInformationWFByLogin(String usuarioConsulta) throws NamingException, GenericFacadeException {
		if (usuarioConsulta != null && !usuarioConsulta.isEmpty()){
			User user = new User();
			HashMap<String,?> options = AuthenticationSettings.getOptions();
			String ldapUser = (String)options.get(LoginModuleConstants.OPTIONS_USERNAME);
			String password = (String)options.get(LoginModuleConstants.OPTIONS_PASSWORD);
			HashMap<String, Object> userPrincipal = LoginUtils.getInformationByDisplayName(ldapUser, password, usuarioConsulta, options);
			user.setId((String)userPrincipal.get(LoginModuleConstants.PRINCIPAL_NAME));
			user.setCommonName((String)userPrincipal.get(LoginModuleConstants.PRINCIPAL_NOMBRE));
			user.setLogin((String)userPrincipal.get(LoginModuleConstants.PRINCIPAL_USERNAME));
			String[] roles = (String[])userPrincipal.get(LoginModuleConstants.PRINCIPAL_ROLES);
			for (String rol : roles){
				user.getRoleNames().add(rol);
			}
			return user;
		}
		throw new GenericFacadeException("El usuario para realizar la consulta no ha sido indicado");
	}
	
	public static User retrieveUserInformationWF(HashMap<String, Object> usuarioConsultaInfo) throws NamingException, GenericFacadeException {
		if (usuarioConsultaInfo != null && !usuarioConsultaInfo.isEmpty()){
			User user = new User();
			user.setId((String)usuarioConsultaInfo.get(LoginModuleConstants.PRINCIPAL_NAME));
			user.setCommonName((String)usuarioConsultaInfo.get(LoginModuleConstants.PRINCIPAL_NOMBRE));
			user.setLogin((String)usuarioConsultaInfo.get(LoginModuleConstants.PRINCIPAL_USERNAME));
			String[] roles = (String[])usuarioConsultaInfo.get(LoginModuleConstants.PRINCIPAL_ROLES);
			for (String rol : roles){
				user.getRoleNames().add(rol);
			}
			return user;
		}
		throw new GenericFacadeException("El usuario para realizar la consulta no ha sido indicado");
	}
	
	public static UsuarioItem retrieveUserInformation(String usuarioConsulta) throws NamingException, GenericFacadeException {
		if (usuarioConsulta != null && !usuarioConsulta.isEmpty()){
			UsuarioItem user = new UsuarioItem();
			HashMap<String,?> options = AuthenticationSettings.getOptions();
			String ldapUser = (String)options.get(LoginModuleConstants.OPTIONS_USERNAME);
			String password = (String)options.get(LoginModuleConstants.OPTIONS_PASSWORD);
			HashMap<String, Object> userPrincipal = LoginUtils.getInformationByNumIdentification(ldapUser, password, usuarioConsulta, options);
			user.setIdentificacion(usuarioConsulta);
			user.setNombreApellidos((String)userPrincipal.get(LoginModuleConstants.PRINCIPAL_NOMBRE));
			user.setNombre((String)userPrincipal.get(LoginModuleConstants.PRINCIPAL_SOLO_NOMBRE));
			user.setApellido((String)userPrincipal.get(LoginModuleConstants.PRINCIPAL_APELLIDO));
			user.setCorreoElectronico((String)userPrincipal.get(LoginModuleConstants.PRINCIPAL_EMAIL));
			String[] roles = (String[])userPrincipal.get(LoginModuleConstants.PRINCIPAL_ROLES);
			for (String rol : roles){
				user.getListRoles().add(rol);
			}
			return user;
		}
		throw new GenericFacadeException("El usuario para realizar la consulta no ha sido indicado");
	}
	
	public static UsuarioItem retrieveUserInformationByLogin(String usuarioConsulta) throws NamingException, GenericFacadeException {
		if (usuarioConsulta != null && !usuarioConsulta.isEmpty()){
			UsuarioItem user = new UsuarioItem();
			HashMap<String,?> options = AuthenticationSettings.getOptions();
			String ldapUser = (String)options.get(LoginModuleConstants.OPTIONS_USERNAME);
			String password = (String)options.get(LoginModuleConstants.OPTIONS_PASSWORD);
			HashMap<String, Object> userPrincipal = LoginUtils.getInformationByDisplayName(ldapUser, password, usuarioConsulta, options);
			user.setIdentificacion(usuarioConsulta);
			user.setNombreApellidos((String)userPrincipal.get(LoginModuleConstants.PRINCIPAL_NOMBRE));
			user.setNombre((String)userPrincipal.get(LoginModuleConstants.PRINCIPAL_SOLO_NOMBRE));
			user.setApellido((String)userPrincipal.get(LoginModuleConstants.PRINCIPAL_APELLIDO));
			user.setCorreoElectronico((String)userPrincipal.get(LoginModuleConstants.PRINCIPAL_EMAIL));
			String[] roles = (String[])userPrincipal.get(LoginModuleConstants.PRINCIPAL_ROLES);
			for (String rol : roles){
				user.getListRoles().add(rol);
			}
			return user;
		}
		throw new GenericFacadeException("El usuario para realizar la consulta no ha sido indicado");
	}
	
	public static boolean validarUsuarioDepartamento(HashMap<String, Object> usuarioSolicitanteInfo, String dptoResponsable){
		if (dptoResponsable != null) {
			String[] roles = (String[])usuarioSolicitanteInfo.get("roles");
			if (roles != null) {
				for (String rolUsuario:roles) {
					if (rolUsuario.equals(dptoResponsable)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static List<String> getUserAllGroups(List<String> groups, String usuarioConsulta) throws NamingException, GenericFacadeException {
		if (usuarioConsulta != null && !usuarioConsulta.isEmpty()) {
			List<String> addedGroups = new ArrayList<String>();
			HashMap<String,?> options = AuthenticationSettings.getOptions();
			String ldapUser = (String)options.get(LoginModuleConstants.OPTIONS_USERNAME);
			String password = (String)options.get(LoginModuleConstants.OPTIONS_PASSWORD);
			HashMap<String, Object> userProperties = LoginUtils.getInformationByDisplayName(ldapUser, password, usuarioConsulta, options);
			if (userProperties != null) {
				String[] result = (String[])userProperties.get(LoginModuleConstants.PRINCIPAL_MEMBERS_OF);
				getAllGroups(ldapUser, password, options, groups, addedGroups, result);
			}
			return addedGroups;
		}
		throw new GenericFacadeException("El usuario para realizar la consulta no ha sido indicado");
	}
	
	public static List<String> getDepartmentAllGroups(List<String> groups, String departamento) throws NamingException, GenericFacadeException {
		if (departamento != null && !departamento.isEmpty()) {
			List<String> addedGroups = new ArrayList<String>();
			HashMap<String,?> options = AuthenticationSettings.getOptions();
			String ldapUser = (String)options.get(LoginModuleConstants.OPTIONS_USERNAME);
			String password = (String)options.get(LoginModuleConstants.OPTIONS_PASSWORD);
			String[] departments = {departamento};
			getAllGroups(ldapUser, password, options, groups, addedGroups, departments);
			return addedGroups;
		}
		throw new GenericFacadeException("El usuario para realizar la consulta no ha sido indicado");
	}
	
	
	private static String[] getGroups(String ldapUser, String password, HashMap<String,?> options, String groupDistinguishedName) throws NamingException, GenericFacadeException {
		HashMap<String, Object> groupProperties = LoginUtils.getInformationByDistinguisedName(ldapUser, password, groupDistinguishedName, options);
		return (String[])groupProperties.get("memberOfList");
	}
	
	private static void getAllGroups(String ldapUser, String password, HashMap<String,?> options, List<String> groups, List<String> addedGroups, String[] result)  
			throws NamingException, GenericFacadeException {
		
		if (result != null && result.length > 0) {
			for (String current: result) {
				int indexBegin = current.indexOf("=") + 1;
				int indexEnd = current.indexOf(",");
				String group;
				if(indexBegin != 0 && indexEnd != -1) {//si se encuentran separadores
					group = current.substring(indexBegin, indexEnd);
				} else {//si no se entiende que se ha pasado el valor en si
					group = current;
				}
				if (groups.contains(group) && !addedGroups.contains(group)) {
					addedGroups.add(group);
					getAllGroups(ldapUser, password, options, groups, addedGroups, getGroups(ldapUser, password, options, current));
				}
			}
		}
	}
	
}
