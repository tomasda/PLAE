package com.opencanarias.apsct.portafirmas.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.portafirmas.bean.ColaboradoresBean;
import com.opencanarias.apsct.portafirmas.bean.CommonBean;
import com.opencanarias.apsct.portafirmas.bean.DocumentosBean;
import com.opencanarias.apsct.portafirmas.bean.UsuarioBean;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.apsct.portafirmas.utils.Services;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.ejb.portafirmas.PortafirmasFacadeRemote;
import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

import es.apt.ae.facade.entities.portafirmas.Persona;

@ManagedBean
public class ColaboradoresController implements Serializable {

	private static final long serialVersionUID = 1759888537592924593L;
	private static Logger logger = Logger.getLogger(ColaboradoresController.class);
	
	@SuppressWarnings("unchecked")
	public String save(){
		
		ColaboradoresBean colaboradoresBean = (ColaboradoresBean) FacesUtils.resolveVariable(Constantes.COLABORADORES_BEAN);
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.resolveVariable(Constantes.USUARIO_BEAN);
		colaboradoresBean.getListPersonasSelecionadas();
		
		List<Persona> listPersonasDefinidas = new ArrayList<Persona>();
		//Comprobamos que en la lista de revisores no esta el propio usuario
		StringBuilder stringBuilder = new StringBuilder();
		for(Persona persona : colaboradoresBean.getListPersonasSelecionadas()){//De la lista de personas buscamos las seleccionadas
			if (!persona.getNumIdentificacion().equals(usuarioBean.getNumIdentificacion())){
				listPersonasDefinidas.add(persona);
			} else {
				stringBuilder.append("No se puede seleccionar a usted mismo como colaborador, usted ha sido omitido.\n");
				CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
				commonBean.setMessage(stringBuilder.toString());
				FacesUtils.verDialog("messageDialog");
			}
		}
		CommonBean commonBean = (CommonBean)FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		HashMap<String, Object> resultado;
		try {
			resultado = ((PortafirmasFacadeRemote) Services.getSrvPortafirmasFacadeRemote()).saveColaboradores(usuarioBean.getEntidadPersona(),listPersonasDefinidas);
		
			usuarioBean.setEntidadPersona((Persona)resultado.get("persona"));
			List<Persona> personasNoAsignadas = new ArrayList<Persona>();
			personasNoAsignadas = (List<Persona>)resultado.get("noAlmacenados");
			if (personasNoAsignadas != null && personasNoAsignadas.size() > 0){
				
				
				stringBuilder.append("Las siguientes personas no fueron a�adidas puesto que poseen revisores:\n");
				for (Persona persona : personasNoAsignadas){
					stringBuilder.append(persona.getNombre() + " " + persona.getApellido1() + " " + persona.getApellido2() + " - " +persona.getCargo()+"\n");
				}
				commonBean.setMessage(stringBuilder.toString());
				FacesUtils.verDialog("messageDialog");
				return null;
			}
		} catch (PortafirmasFacadeException e) {
			LoggerUtils.showError(logger, Constantes.ERROR_PERSIST_REVISION, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		}
		DocumentosBean documentosBean = (DocumentosBean)FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		documentosBean.setDocumentosList(Services.getSrvPortafirmasFacadeRemote().buscarPorBandeja(usuarioBean.getNumIdentificacion(), documentosBean.getTitulo().toUpperCase(), null));
		commonBean.setMessage("Se ha actualizado su listado de colaboradores");
		FacesUtils.verDialog("messageDialog");
		return null;
	}
	
}
