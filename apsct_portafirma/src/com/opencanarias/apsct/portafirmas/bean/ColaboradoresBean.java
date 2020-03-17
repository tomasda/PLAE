package com.opencanarias.apsct.portafirmas.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.apsct.portafirmas.utils.Services;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

import es.apt.ae.facade.entities.portafirmas.Persona;


@ManagedBean(name=Constantes.COLABORADORES_BEAN)
@SessionScoped
public class ColaboradoresBean implements Serializable{

	private static final long serialVersionUID = 1570854753817410641L;

	protected static final Logger logger = Logger.getLogger(ColaboradoresBean.class);
	
	private Persona[] listPersonas;
	private Persona[] listPersonasSelecionadas;
	
	public Persona[] getListPersonas() {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		List<Persona> listPers = new ArrayList<Persona>();
		try {
			listPers = Services.getSrvPortafirmasFacadeRemote().consultarValidadoresEJB();
		} catch (PortafirmasFacadeException e) {
			LoggerUtils.showError(logger, Constantes.ERROR_GET_LIST_PERSONS, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			CommonBean commonBean = (CommonBean) FacesUtils.resolveVariable(Constantes.COMMON_BEAN);
			commonBean.setMessage(Constantes.ERROR_GET_LIST_PERSONS);
			FacesUtils.verDialog("messageDialog");
		}
		listPersonas = listPers.toArray(new Persona[listPers.size()]);
		return listPersonas;
	}
	
	public void setListPersonas(Persona[] listPersonas) {
		this.listPersonas = listPersonas;
	}

	public Persona[] getListPersonasSelecionadas() {
		
		if(listPersonasSelecionadas == null){//No se puede cachear porque los estados se actualizan?
			UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);		
			List<Persona> listPers = new ArrayList<Persona>();
			try {
				listPers = Services.getSrvPortafirmasFacadeRemote().consultarRevisoresByDNI(usuarioBean.getNumIdentificacion());
			} catch (PortafirmasFacadeException e) {
				LoggerUtils.showError(logger, Constantes.ERROR_GET_SELECTED_PERSONS, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
				CommonBean commonBean = (CommonBean) FacesUtils.resolveVariable(Constantes.COMMON_BEAN);
				commonBean.setMessage(Constantes.ERROR_GET_SELECTED_PERSONS);
				FacesUtils.verDialog("messageDialog");
			}	
			listPersonasSelecionadas = listPers.toArray(new Persona[listPers.size()]);
			
		}
		
		return listPersonasSelecionadas;
	}

	public void setListPersonasSelecionadas(Persona[] listPersonasSelecionadas) {
		this.listPersonasSelecionadas = listPersonasSelecionadas;
	}	
}
