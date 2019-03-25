package com.opencanarias.consola.autenticacion;

import java.io.Serializable;

import javax.faces.application.FacesMessage; 
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.opencanarias.consola.commons.Constantes;
import com.opencanarias.consola.utilidades.SessionUtils; 

//@ManagedBean(name = "LoginMB") 
//@ViewScoped 
public class LoginManagedBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private LoginDao userDAO = new LoginDao();
	private LoginBean user;
	
	public LoginManagedBean() {
		user = new LoginBean();
	}
	
	public String send() {
		user = userDAO.getUser(user.getNameUser(), user.getPassword());
		HttpSession session = SessionUtils.getSession();
		if (user == null) { 
			user = new LoginBean();
			FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constantes.LOGIN_INCORRECTO, Constantes.LOGIN_INCORRECTO_SQL)); 
			return null; 
			} else { 
				session.setAttribute("username", user.getNameUser());
				return "/menu";
				} 
		} 
	
	public LoginBean getUser() {
		return user; 
		} 
	
	public void setUser(LoginBean user) {
		this.user = user; 
		}
	
	public String logout() {
		HttpSession session =  SessionUtils.getSession();
		session.invalidate();		
		user = null;
		return "/login";
	}
}
