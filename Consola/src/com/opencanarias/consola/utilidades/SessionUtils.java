/**
 * @author Admin-AE
 *
 */
package com.opencanarias.consola.utilidades;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class SessionUtils {
	
	public static HttpSession getSession() {
		HttpSession session =(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return session;
	}
	public static HttpServletRequest getRequest(){
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public static String idSesion(){
		HttpSession session = getSession();
		return (String) session.getId();
	}

	public static String getUserName() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return session.getAttribute("UserName").toString();
	}

	public static String getUserId() {
		HttpSession session = getSession();
		if (session != null){
			return (String) session.getAttribute("userid");
		}else{
			return null;
		}
	}
}
