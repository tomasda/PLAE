package com.opencanarias.apsct.portafirmas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.portafirmas.bean.ColaboradoresBean;
import com.opencanarias.apsct.portafirmas.bean.UsuarioBean;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

import es.apt.ae.facade.entities.portafirmas.Persona;

@FacesConverter("personaConverter")
public class PersonaConverter implements Converter{

	protected static final Logger logger = Logger.getLogger(PersonaConverter.class);
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String codigo) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
        try{
        	ColaboradoresBean colaboradoresBean = (ColaboradoresBean) FacesUtils.resolveVariable("colaboradoresBean");
        	if (colaboradoresBean != null){
	        	for (Persona persona : colaboradoresBean.getListPersonas()){
	        		if (persona.getNumIdentificacion().equals(codigo))
	        			return persona;
	        	}  	
        	} else {
        		LoggerUtils.showError(logger, "No se ha podido recuperar la lista del bean", null, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
        		throw new Exception();
        	}
        }
        catch (Exception ex){
        	LoggerUtils.showError(logger, "Error while fetching Persona for " + codigo, ex, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
        }
        return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        if(obj instanceof Persona){
            Persona persona = (Persona)obj;
            return persona.getNumIdentificacion();
        }
        else{
            throw new ClassCastException("No se ha podido convertir el tipo correctamente");
        }
	}

}
