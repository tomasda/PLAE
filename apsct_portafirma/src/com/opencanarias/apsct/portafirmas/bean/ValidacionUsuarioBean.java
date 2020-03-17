package com.opencanarias.apsct.portafirmas.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;
import com.opencanarias.apsct.portafirmas.controller.LoginController;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.utils.StringUtils;

import es.opencanarias.security.authenticators.CustomExternalAuthenticator;
import es.opencanarias.security.authenticators.dto.ExternalJbossAuthenticateResult;
import es.opencanarias.security.authenticators.dto.ICredentialBean;
import es.opencanarias.security.authenticators.exceptions.JwtException;

@ManagedBean
@SessionScoped
public class ValidacionUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ValidacionUsuarioBean.class);
	@Inject
	UsuarioBean usuarioBean;
	
	private Boolean valid = null;
	
	public boolean getValid() {
		if(valid == null){
			valid = calcular();
		}
		return (null==valid?false:valid.booleanValue());
	}
	
	public void reload() {
		valid = calcular();
	} 
	
	private Boolean calcular() {
		ICredentialBean loginUsuarioBean = (ICredentialBean) FacesUtils.resolveVariable(Constantes.LOGIN_BEAN);
		//UsuarioBean usuarioBean = (UsuarioBean)FacesUtils.resolveVariable(Constantes.USUARIO_BEAN);
		HttpServletRequest request = FacesUtils.getRequest();
		HttpServletResponse response = FacesUtils.getResponse();
		try {
			CustomExternalAuthenticator customAuthenticator = new CustomExternalAuthenticator("portafirmas");
			ExternalJbossAuthenticateResult externalJbossAuthenticateResult = customAuthenticator.externalJbossAuthenticate(request.getCookies(), loginUsuarioBean);
			
			if (externalJbossAuthenticateResult.isResult()){//Es correcto
				//Comprobamos que el usuario se ha seteado en la sesion
				if (StringUtils.isNullOrEmpty(usuarioBean.getUsername())){
					LoginController usuariosControlador = new LoginController();
					String result = usuariosControlador.login();
					if (null == result || result.isEmpty()){//Si no se ha seteado no es valido
						return false;
					}
				}
				if (null != externalJbossAuthenticateResult.getCookie()){//Sereamos la cookie si todo ha sido correcto
					response.addCookie(externalJbossAuthenticateResult.getCookie());
				}
				return true;
			} else {
				StringBuilder messageBuilder = new StringBuilder();
				if (null != loginUsuarioBean && !StringUtils.isNullOrEmpty(loginUsuarioBean.getUsername())) {
					messageBuilder.append("El usuario [");
					messageBuilder.append(loginUsuarioBean.getUsername());
					messageBuilder.append("] no ha sido capaz de acceder a la aplicacion debido a: " );
					messageBuilder.append(externalJbossAuthenticateResult.getMessage());
				} else {
					messageBuilder.append("Se va a hacer login en la aplicacion");
				}
				LoggerUtils.showInfo(logger, messageBuilder.toString());
				if (null == loginUsuarioBean || null == loginUsuarioBean.getUsername()) {
					return null;
				}
				return false;
			}	
		} catch (JwtException e) {
			StringBuilder messageBuilder = new StringBuilder();
			messageBuilder.append("El usuario [");
			messageBuilder.append(loginUsuarioBean.getUsername());
			messageBuilder.append("] no ha sido capaz de acceder a la aplicacion debido a: " );
			messageBuilder.append("Error al validar la cookie");
			LoggerUtils.showError(logger, messageBuilder.toString(), e);
			return false;
		}
	}
}
