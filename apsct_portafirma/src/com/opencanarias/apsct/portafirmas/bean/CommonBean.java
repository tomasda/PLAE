package com.opencanarias.apsct.portafirmas.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.opencanarias.apsct.portafirmas.utils.ConfigUtils;
import com.opencanarias.apsct.portafirmas.utils.Constantes;

@ManagedBean
@SessionScoped
public class CommonBean implements Serializable{

	private static final long serialVersionUID = 7389068163318819916L;

	//Constantes
	private String PENDIENTES = Constantes.PENDIENTES;
	private String PERSONAL = Constantes.PERSONAL;
	private String TRAMITADOS = Constantes.TRAMITADOS;
	private String ENVIADOS = Constantes.ENVIADOS;
	private String PEND = Constantes.PEND;
	private String RECP = Constantes.RECP;

	// Mensaje
	private String message = "";
	
	// Config values
	private String version = "";
	private String logoLogin;
	private String logoHeader;
	private String logoLoading;
	private String urlAutofirma;
	private String urlManualUsuario;
	private String applicationUsername;
	private String applicationPassword;
	
	// Credenciales
	private String pass;
	private String userName;
	
	
	public CommonBean(){
		message = "";
		version = ConfigUtils.getParametro("version");
		logoLogin = ConfigUtils.getParametro("logo.login");
		logoHeader = ConfigUtils.getParametro("logo.headder");
		logoLoading = ConfigUtils.getParametro("logo.loadding");
		urlAutofirma = ConfigUtils.getParametro("url.autofirma");
		urlManualUsuario = ConfigUtils.getParametro("url.manualUsuario");
		applicationUsername = ConfigUtils.getParametro("username");
		applicationPassword = ConfigUtils.getParametro("password");
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPENDIENTES() {
		return PENDIENTES;
	}

	public void setPENDIENTES(String pENDIENTES) {
		PENDIENTES = pENDIENTES;
	}

	public String getPERSONAL() {
		return PERSONAL;
	}

	public void setPERSONAL(String pERSONAL) {
		PERSONAL = pERSONAL;
	}

	public String getTRAMITADOS() {
		return TRAMITADOS;
	}

	public void setTRAMITADOS(String tRAMITADOS) {
		TRAMITADOS = tRAMITADOS;
	}

	public String getENVIADOS() {
		return ENVIADOS;
	}

	public void setENVIADOS(String eNVIADOS) {
		ENVIADOS = eNVIADOS;
	}

	public String getPEND() {
		return PEND;
	}

	public void setPEND(String pEND) {
		PEND = pEND;
	}

	public String getVersion() {
		return version;
	}

	public String getLogoLogin() {
		return logoLogin;
	}

	public String getLogoHeader() {
		return logoHeader;
	}

	public String getLogoLoading() {
		return logoLoading;
	}

	public String getRECP() {
		return RECP;
	}

	public void setRECP(String rECP) {
		RECP = rECP;
	}

	public String getUrlAutofirma() {
		return urlAutofirma;
	}

	public void setUrlAutofirma(String urlAutofirma) {
		this.urlAutofirma = urlAutofirma;
	}

	public String getUrlManualUsuario() {
		return urlManualUsuario;
	}

	public void setUrlManualUsuario(String urlManualUsuario) {
		this.urlManualUsuario = urlManualUsuario;
	}

	public String getApplicationUsername() {
		return applicationUsername;
	}

	public void setApplicationUsername(String applicationUsername) {
		this.applicationUsername = applicationUsername;
	}

	public String getApplicationPassword() {
		return applicationPassword;
	}

	public void setApplicationPassword(String applicationPassword) {
		this.applicationPassword = applicationPassword;
	}

}
