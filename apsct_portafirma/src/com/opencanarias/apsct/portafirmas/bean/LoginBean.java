package com.opencanarias.apsct.portafirmas.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import es.opencanarias.security.authenticators.dto.ICredentialBean;


@Named(value="loginBean")
@SessionScoped
public class LoginBean implements Serializable, ICredentialBean {

	private static final long serialVersionUID = -8985251408794276542L;
	
	private String username;
	private String password;
	private boolean error;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}	
}
