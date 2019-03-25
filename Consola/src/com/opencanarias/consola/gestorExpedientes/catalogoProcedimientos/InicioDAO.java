package com.opencanarias.consola.gestorExpedientes.catalogoProcedimientos;

import java.util.ArrayList;
import java.util.List;

import com.opencanarias.consola.commons.LoadProperties;

public class InicioDAO {

	public InicioDAO() {

	}

	// Lista generada desde un fichero properties porque no existe en BBDD.
	public List<InicioBean> getListaTipoInicio() {
		LoadProperties lp = new LoadProperties();
		List<InicioBean> listaTiposInicio = new ArrayList<InicioBean>();
		String fichero = "inicioProcedimientoOptions";
		int index_0 = 0;
		while (lp.getParameter(fichero, Integer.toString(index_0)) != null) {
			listaTiposInicio.add(new InicioBean(lp.getParameter(fichero, Integer.toString(index_0)), index_0));
			index_0++;
		}
		return listaTiposInicio;
	}
}
