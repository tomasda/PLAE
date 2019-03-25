package com.opencanarias.consola.gestorExpedientes.catalogoProcedimientos;

import java.io.Serializable;

import javax.inject.Named;

@Named
public class InicioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public String tipoInicioLabel;
	public int tipoInicioValue;

	public InicioBean() {
	}

	
	
	public InicioBean(String tipoInicioLabel, int tipoInicioValue) {
		super();
		this.tipoInicioLabel = tipoInicioLabel;
		this.tipoInicioValue = tipoInicioValue;
	}



	public String getTipoInicioLabel() {
		return tipoInicioLabel;
	}

	public void setTipoInicioLabel(String tipoInicioLabel) {
		this.tipoInicioLabel = tipoInicioLabel;
	}

	public int getTipoInicioValue() {
		return tipoInicioValue;
	}

	public void setTipoInicioValue(int tipoInicioValue) {
		this.tipoInicioValue = tipoInicioValue;
	}


	
}
