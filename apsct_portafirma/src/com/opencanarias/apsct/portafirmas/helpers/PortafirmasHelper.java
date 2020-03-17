package com.opencanarias.apsct.portafirmas.helpers;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.portafirmas.bean.CommonBean;
import com.opencanarias.apsct.portafirmas.bean.UsuarioBean;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

public class PortafirmasHelper {
	
	public static void mostrarMensajeError(String info, Logger logger, Exception e){
		if (null != logger) {
			UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
			LoggerUtils.showError(logger, info, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		}
		CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		commonBean.setMessage(info);
		FacesUtils.verDialog("messageDialog");
	}
	
	public static void mostrarMensajeInfo(String info, Logger logger){
		if (null != logger) {
			UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
			LoggerUtils.showInfo(logger, info, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		}
		CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		commonBean.setMessage(info);
		FacesUtils.verDialog("messageDialog");
	}
	
	public static void mostrarMensajeWarning(String info, Logger logger){
		if (null != logger) {
			UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
			LoggerUtils.showWarning(logger, info, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		}
		CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		commonBean.setMessage(info);
		FacesUtils.verDialog("messageDialog");
	}
	
}
