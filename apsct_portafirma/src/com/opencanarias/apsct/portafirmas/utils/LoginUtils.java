package com.opencanarias.apsct.portafirmas.utils;

import javax.servlet.http.Cookie;

import com.opencanarias.apsct.portafirmas.bean.LoginBean;
import com.opencanarias.utils.StringUtils;

import es.opencanarias.security.authenticators.CustomExternalAuthenticator;
import es.opencanarias.security.authenticators.exceptions.JwtException;

public class LoginUtils {

	public static LoginBean completeWithCookieValues(LoginBean loginBean) throws JwtException {
		// Leer cookie, completar valores - si no hay cookie, mostrar error
		if (loginBean == null || StringUtils.isNullOrEmpty(loginBean.getUsername()) || StringUtils.isNullOrEmpty(loginBean.getPassword())){
			loginBean = new LoginBean();
			Cookie[] cookies = FacesUtils.getRequest().getCookies();
			CustomExternalAuthenticator customExternalAuthenticator = new CustomExternalAuthenticator("portafirmas");
			String[] result = customExternalAuthenticator.getAuthenticationValues(cookies);
			if (result != null && result.length != 0){
				loginBean.setUsername(result[0]);
				loginBean.setPassword(result[1]);
			}
		}
		return loginBean;
	}

}
