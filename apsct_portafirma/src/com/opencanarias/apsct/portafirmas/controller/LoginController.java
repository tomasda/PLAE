package com.opencanarias.apsct.portafirmas.controller;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;

import com.opencanarias.api.security.UserPrincipal;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;
import com.opencanarias.apsct.portafirmas.bean.LoginBean;
import com.opencanarias.apsct.portafirmas.bean.UsuarioBean;
import com.opencanarias.apsct.portafirmas.bean.ValidacionUsuarioBean;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.apsct.portafirmas.utils.LoginUtils;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.utils.StringUtils;


@Named(value = "loginController")
//@SessionScoped
@ApplicationScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 8283956837518605488L;
	protected static final Logger logger = Logger.getLogger(LoginController.class);
	private String userFromForm;
	private String passwordFromFrom;
	
	@Inject
	UsuarioBean usuarioBean;
	@Inject
	LoginBean loginBean;
	
	public String login() {
		String result = null;
//		UsuarioBean usuarioBean = (UsuarioBean)FacesUtils.resolveVariable(Constantes.USUARIO_BEAN);
//		LoginBean loginBean = (LoginBean) FacesUtils.getSessionBean(Constantes.LOGIN_BEAN);
//		try {
//			loginBean = LoginUtils.completeWithCookieValues(loginBean);
//		} catch (JwtException e) {
//			logger.error("Error al obtener usuario y password de la cookie", e);
//		}
		
		if (StringUtils.isNullOrEmpty(loginBean.getUsername()) || StringUtils.isNullOrEmpty(loginBean.getPassword())) {
			loginBean.setError(true);
			result = Constantes.LOGINERROR;
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			LoggerUtils.showInfo(logger, "Accediendo a la aplicacion...", FacadeBean.USUARIO_PORTAFIRMAS, loginBean.getUsername());
			try {
				/*
				 *2019-12-20
				 *	Validación del LOGIN
				 *	Si se ha de realizar la validación del usuario contra el LDAP se ha utilizar el mógulo LoginUtils ubicado en com.opencanarias.api.security.utils
				 *	LDAP LoginModule
				 *
				 * 	Para esta versión la validación del usuario se realiza contra Base de Datos.
				 */
				//request.login(loginBean.getUsername(), loginBean.getPassword());
				LoggerUtils log = new LoggerUtils();
				Object login = log.doLogin(loginBean.getUsername(), loginBean.getPassword());
				
				
				//Object login = com.opencanarias.api.security.utils.LoginUtils.doLogin(loginBean.getUsername(), loginBean.getPassword());
				if (null!=login) {

				Principal principal =  setPrincipal(login);//(Principal) context.getExternalContext().getUserPrincipal();

//				if (null != principal.getName()){
					result = Constantes.INDEX;
					LoggerUtils.showInfo(logger, "Acceso correcto", FacadeBean.USUARIO_PORTAFIRMAS, loginBean.getUsername());
					usuarioBean.setUsername(loginBean.getUsername());
					
					// En caso de estar logueado se debería generar la cookie
					ValidacionUsuarioBean validationUserBean = (ValidacionUsuarioBean) FacesUtils.resolveVariable(Constantes.VALIDACION_USUARIO_BEAN);
					if (validationUserBean == null){
						validationUserBean = new ValidacionUsuarioBean();
					}
					if (!validationUserBean.getValid()){
						logger.error("Error al intentar loguear en la aplicación");
						loginBean.setError(true);
						result = Constantes.LOGINERROR;
					} else {
						loginBean.setError(false);
						// Comprobar si se va a la página de consulta de estado
						String uri = (String)FacesUtils.getSessionAttribute(Constantes.ESTADO_URI);
						if (uri != null && !uri.trim().equals("")) {
							result = "estado";
							FacesUtils.getExternalContext().redirect("estado.xhtml?uri=" + uri);
						}
					}
				} else {
					loginBean.setError(true);
					result = Constantes.LOGINERROR;
				}
			} catch (Exception e) {
				LoggerUtils.showInfo(logger, "Error de autenticacion", FacadeBean.USUARIO_PORTAFIRMAS, loginBean.getUsername());
				LoggerUtils.showError(logger, e.toString());
				loginBean.setError(true);
				result = Constantes.LOGINERROR;
			}
		}

		return result;
	}

	private Principal setPrincipal(Object login) {
		String name = "";
		String nombre = "";
		String apellido = "";
		String username = "";
		String email = "";
		
		Principal user = new UserPrincipal(name, nombre, apellido, username, email);
		return user;
	}

	public String getUserFromForm() {
		return userFromForm;
	}

	public void setUserFromForm(String userFromForm) {
		this.userFromForm = userFromForm;
	}

	public String getPasswordFromFrom() {
		return passwordFromFrom;
	}

	public void setPasswordFromFrom(String passwordFromFrom) {
		this.passwordFromFrom = passwordFromFrom;
	}

}
