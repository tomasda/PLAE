package com.opencanarias.apsct.portafirmas.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.portafirmas.bean.DocumentosBean;
import com.opencanarias.apsct.portafirmas.bean.UsuarioBean;
import com.opencanarias.apsct.portafirmas.helpers.PortafirmasHelper;
import com.opencanarias.apsct.portafirmas.utils.CatalogosUtils;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

import es.apt.ae.facade.dto.UsuarioItem;
import es.opencanarias.security.authenticators.CustomAuthenticator;
import es.opencanarias.security.authenticators.CustomExternalAuthenticator;
import es.opencanarias.security.authenticators.exceptions.JwtException;

@ManagedBean
public class GeneralController implements Serializable{

	private static final long serialVersionUID = 7662839067611444861L;
	protected static final Logger logger = Logger.getLogger(GeneralController.class);

	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		//TODO: aplicar una interfaz a los bean con el metodo destroy, recorrer los bean y destruirlos todos
		DocumentosBean documentosBean = (DocumentosBean)FacesUtils.resolveVariable(Constantes.DOCUMENTOS_BEAN);
		documentosBean.destroy();
		HttpServletRequest request =(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		try {
			CustomExternalAuthenticator customExternalAuthenticator = new CustomExternalAuthenticator("portafirmas");
			customExternalAuthenticator.closeSession(request.getCookies(), response);
		} catch (JwtException e) {
			return null;
		}
		return Constantes.LOGIN;
	}
	
	public void changeUserToConsult() {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		try {
			String username = usuarioBean.getUsername();
			UsuarioItem usuarioConsulta = CatalogosUtils.getUsuarioByUsername(username);
			usuarioBean.setUserToConsult(usuarioConsulta);
		} catch (Exception e) {
			LoggerUtils.showError(logger, "No se ha podido recuperar la informacion del usuario a consultar", null, 
					FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			PortafirmasHelper.mostrarMensajeError("No se ha podido recuperar la información del usuario a consultar.", logger, null);
		}
	}
	
	public String goBack() {
		return Constantes.BACK;
	}
	
	public String goHelp(){
		return Constantes.HELP;
	}
}
