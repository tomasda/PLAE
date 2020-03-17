package com.opencanarias.utils;

import java.util.HashMap;

import com.opencanarias.api.security.utils.LoginModuleConstants;
import com.opencanarias.exceptions.GenericFacadeException;

public class AuthenticationSettings {

	private static HashMap<String, Object> options;
	
	public static HashMap<String, Object> getOptions() throws GenericFacadeException{
		
		if(options == null){
			options = new HashMap<String, Object>();
			options.put(LoginModuleConstants.OPTIONS_BASE_USERS, ConfigUtils.getParametro("security.authentication.users.base"));
			options.put(LoginModuleConstants.OPTIONS_BASE_ALG_GROUPS, ConfigUtils.getParametro("security.authentication.alfGroups.base"));
			options.put(LoginModuleConstants.OPTIONS_OU, ConfigUtils.getParametro("security.authentication.ou"));
			options.put(LoginModuleConstants.OPTIONS_LDAP_URL, ConfigUtils.getParametro("security.authentication.ldapURL"));
			options.put(LoginModuleConstants.OPTIONS_IDENTIFICATION_FIELD, ConfigUtils.getParametro("security.authentication.identificationField"));
			options.put(LoginModuleConstants.OPTIONS_NUM_IDENTIFICATION_FIELD, ConfigUtils.getParametro("security.authentication.numIdentificationField"));
			options.put(LoginModuleConstants.OPTIONS_EMAIL_FIELD, ConfigUtils.getParametro("security.authentication.emailField"));
			options.put(LoginModuleConstants.OPTIONS_ROL_FIELD, ConfigUtils.getParametro("security.authentication.rolField"));
			options.put(LoginModuleConstants.OPTIONS_MEMBER_OF_FIELD, ConfigUtils.getParametro("security.authentication.memberOfField"));
			options.put(LoginModuleConstants.OPTIONS_MEMBER_FIELD, ConfigUtils.getParametro("security.authentication.memberField"));		
			options.put(LoginModuleConstants.OPTIONS_DISTINGUISHED_NAME_FIELD, ConfigUtils.getParametro("security.authentication.distinguishedNameField"));
			options.put(LoginModuleConstants.OPTIONS_CERTIFICATE_FIELD, ConfigUtils.getParametro("security.authentication.certificate"));
			options.put(LoginModuleConstants.OPTIONS_USERNAME, ConfigUtils.getParametro("security.authentication.username"));
			options.put(LoginModuleConstants.OPTIONS_PASSWORD, ConfigUtils.getParametro("security.authentication.password"));
		}
		return options;
	}
	
	
	
}
