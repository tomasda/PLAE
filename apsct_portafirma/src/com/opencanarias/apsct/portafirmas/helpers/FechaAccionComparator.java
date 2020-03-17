package com.opencanarias.apsct.portafirmas.helpers;

import java.util.Comparator;

import com.opencanarias.apsct.portafirmas.bean.UsuarioBean;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.apsct.portafirmas.utils.Functions;

import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;

/*
 * Solo puede ser usado desde aplicacion web de portafirmas
 * */
public class FechaAccionComparator implements Comparator<DocumentoPortafirmas> {
	@Override
	public int compare(DocumentoPortafirmas doc1, DocumentoPortafirmas doc2) {
		UsuarioBean usuarioBean = (UsuarioBean)FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		if (usuarioBean.getEntidadPersona() == null){
			return doc1.getFechaSubidaPortafirmas().getTime() < doc2.getFechaSubidaPortafirmas().getTime() ? -1 : doc1.getFechaSubidaPortafirmas().getTime() == doc2.getFechaSubidaPortafirmas().getTime() ? 0 : 1;
		}
		Long date1 = Functions.dateOfLastAction(doc1, usuarioBean.getEntidadPersona().getNumIdentificacion()).getTime();
		Long date2 = Functions.dateOfLastAction(doc2, usuarioBean.getEntidadPersona().getNumIdentificacion()).getTime();
		return date1 < date2 ? -1 : date1 == date2 ? 0 : 1;
	}
}