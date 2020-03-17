package com.opencanarias.apsct.portafirmas.utils;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

public class FacesUtils {

	public static Object getSessionBean(String beanName) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(beanName);
	}
	
	/**
	 * Permite recuperar la request desde el FacesContext.
	 * 
	 * @return Retorna la request desde el FacesContext.
	 */
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) getContext().getExternalContext().getRequest();
	}

	/**
	 * Permite recuperar la response desde el FacesContext.
	 * 
	 * @return Retorna la response desde el FacesContext.
	 */
	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) getContext().getExternalContext().getResponse();
	}

	public static String getParam(String paramString) {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String param = params.get(paramString);
		return param;
	}

	public static void verDialog(String dialogName) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('" + dialogName + "').show();");
		context.execute("PF('statusDialog').hide();");
	}

	public static void updateComponent(String componentId) {
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(componentId);
	}

	public static Object resolveVariable(final String name) {
		return FacesContext.getCurrentInstance().getELContext().getELResolver()
				.getValue(FacesContext.getCurrentInstance().getELContext(), null, name);
	}

	public static void deleteSessionBean(String beanName) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(beanName);
	}

	@SuppressWarnings("rawtypes")
	public static Object getRequestParameterValue(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		return map.get(key);
	}

	/**
	 * Permite acceder al contexto actual de FacesContext.
	 * 
	 * @return contexto actual de FacesContext.
	 */
	public static FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * Permite recuperar la el objeto <code>HttpSession</code> desde el
	 * FacesContext.
	 * 
	 * @param create Un valor a <b>true</b> indicaría que si la sesión no estaba
	 *               creada, esta será creada en esta llamada al método.
	 * @return Objeto <code>HttpSession</code> con la sesion para la aplicación.
	 */
	public static HttpSession getSession(final boolean create) {
		return (HttpSession) getContext().getExternalContext().getSession(create);
	}

	/**
	 * Permite eliminar un atributo de la sesión del FacesContext.
	 * 
	 * @param name Nombre del atributo que deseamos eliminar.
	 */
	public static void removeSessionAttribute(String name) {
		getSession(true).removeAttribute(name);
	}

	/**
	 * Permite extraer un atributo de la sesión del FacesContext. Este método es
	 * similar al de eliminar un atributo del FacesContext, con la salvedad que
	 * antes de eliminarlo, este es devuelto al método invocante.
	 * 
	 * @param name Nombre del atributo que deseamos eliminar.
	 * @return
	 */
	public static Object extractSessionAttribute(String name) {
		Object session = getSessionAttribute(name);
		removeSessionAttribute(name);
		return session;
	}

	/**
	 * Permite recuperar un atributo de la sesión del FacesContext.
	 * 
	 * @param name Nombre del atributo que deseamos recuperar.
	 * @return Atributo recuperado.
	 */
	public static Object getSessionAttribute(String name) {
		return getSession(true).getAttribute(name);
	}

	/**
	 * Permite insertar un atributo en la sesión de FacesContext.
	 * 
	 * @param name  Nombre del atributo.
	 * @param value Valor del atributo.
	 */
	public static void setSessionAttribute(String name, Object value) {
		getSession(true).setAttribute(name, value);
	}
	
	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public static String getSchemeProtocol() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return request.getScheme();
	}

	public static String getServerName() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return request.getServerName();
	}

	public static String getServerPort() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return String.valueOf(request.getServerPort());
	}

}
