package com.opencanarias.apsct.portafirmas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.portafirmas.utils.CatalogosUtils;

import es.apt.ae.facade.entities.portafirmas.TipoGrupo;

@FacesConverter("tipoGrupoConverter")
public class TipoGrupoConverter implements Converter{

	protected static final Logger logger = Logger.getLogger(TipoGrupoConverter.class);
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String codigo) {
        try{
            return CatalogosUtils.getTipoGrupoByCodigo(codigo);
        }
        catch (Exception ex){
            logger.error("Error while fetching TipoGrupo for " + codigo, ex);
        }
        return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        if(obj instanceof TipoGrupo){
            TipoGrupo tipoGrupo = (TipoGrupo)obj;
            return tipoGrupo.getCodigo();
        }
        else{
            throw new ClassCastException("No se ha podido convertir el tipo correctamente");
        }
	}

}
