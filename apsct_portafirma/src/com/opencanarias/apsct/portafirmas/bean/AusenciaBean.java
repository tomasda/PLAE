package com.opencanarias.apsct.portafirmas.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
//import javax.faces.annotation.FacesConfig;

import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.apsct.portafirmas.utils.Services;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

import es.apt.ae.facade.entities.portafirmas.Ausencia;
import es.apt.ae.facade.entities.portafirmas.Persona;
import es.apt.ae.facade.entities.portafirmas.Sustitucion;

@Named
@SessionScoped
public class AusenciaBean implements Serializable{

	private static final long serialVersionUID = -8469474035439711189L;
	private Boolean ausenciaActiva;
	private List<Persona> listPersonas = new ArrayList<Persona>();
	private String seleccionPersona;
	private Ausencia ausenciaEntity;

	protected static final Logger logger = Logger.getLogger(AusenciaBean.class);
	
	public Boolean getAusenciaActiva() {
		return ausenciaActiva;
	}

	public void setAusenciaActiva(Boolean ausenciaActiva) {
		this.ausenciaActiva = ausenciaActiva;
	}

	public List<Persona> getListPersonas() {
		UsuarioBean usuarioBean = (UsuarioBean)FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		try {
			listPersonas = Services.getSrvPortafirmasFacadeRemote().consultarPersonasEJB();
		} catch (PortafirmasFacadeException e) {
			LoggerUtils.showError(logger, Constantes.ERROR_GET_LIST_PERSONS, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		}
		return listPersonas;
	}

	public void setListPersonas(List<Persona> listPersonas) {
		this.listPersonas = listPersonas;
	}

	public String getSeleccionPersona() {
		return seleccionPersona;
	}

	public void setSeleccionPersona(String seleccionPersona) {
		this.seleccionPersona = seleccionPersona;
	}
	
	public Ausencia getAusenciaEntity() {
		return ausenciaEntity;
	}

	public void setAusenciaEntity(Ausencia ausenciaEntity) {
		this.ausenciaEntity = ausenciaEntity;
	}
	
	public void cambio(ActionEvent event){
		
		CommonBean commonBean = (CommonBean)FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		UsuarioBean usuarioBean = (UsuarioBean)FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		if (usuarioBean == null){
			usuarioBean = new UsuarioBean();
			usuarioBean.create();
		}
		
		if (seleccionPersona.isEmpty()){
			commonBean.setMessage(Constantes.WARN_MUST_SELECT_PERSONS_AUSENCIA);
			FacesUtils.verDialog("messageDialog");
			this.ausenciaActiva=false;
		} else if(seleccionPersona.equals(usuarioBean.getNumIdentificacion())){ 
			String info = "No se puede seleccionar a usted mismo como colaborador, usted ha sido omitido.\n";
			commonBean.setMessage(info);
			FacesUtils.verDialog("messageDialog");
		}else if (ausenciaActiva == null || ausenciaActiva == false){//Si se activa, da de alta nuevo registro
			ausenciaActiva = true;
			Ausencia ausencia = new Ausencia();
			ausencia.setAusentado(usuarioBean.getEntidadPersona());
			ausencia.setFechaInicio(new Date());
			Sustitucion sustitucion = new Sustitucion();
			for (Persona pers : listPersonas){
				if (pers.getNumIdentificacion().equals(seleccionPersona)){
					sustitucion.setPersona(pers);
					break;
				}
			}
			sustitucion.setAusencia(ausencia);
			ausencia.setSustitucion(sustitucion);
			ausenciaEntity = ausencia;
			try {
				ausenciaEntity = Services.getSrvPortafirmasFacadeRemote().saveAusencia(ausenciaEntity);//Debe devolver la ausencia y es lo que seteamos en nuestra ausencia entity con el id generado
			} catch (PortafirmasFacadeException e) {
				LoggerUtils.showError(logger, Constantes.ERROR_PERSIST_AUSENT, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
				commonBean.setMessage(Constantes.ERROR_PERSIST_AUSENT);
				FacesUtils.verDialog("messageDialog");
				LoggerUtils.showInfo(logger, Constantes.INFO_AUSENT_CORRECT, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			}
		} else {//Si se desactiva, pone fecha de fin a la ausencia
			ausenciaActiva = false;
			ausenciaEntity.setFechaFin(new Date());
			try {
				Services.getSrvPortafirmasFacadeRemote().saveAusencia(ausenciaEntity);
				LoggerUtils.showInfo(logger, Constantes.INFO_AUSENT_CORRECT, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			} catch (PortafirmasFacadeException e) {
				LoggerUtils.showError(logger, Constantes.ERROR_PERSIST_AUSENT, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
				commonBean.setMessage(Constantes.ERROR_PERSIST_AUSENT);
				FacesUtils.verDialog("messageDialog");
			}
			ausenciaEntity = null;
			seleccionPersona = null;
		}
	}
}
