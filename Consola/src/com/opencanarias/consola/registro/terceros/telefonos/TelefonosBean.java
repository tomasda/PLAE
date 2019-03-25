package com.opencanarias.consola.registro.terceros.telefonos;

import java.io.Serializable;

public class TelefonosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int ID;
	private String telefono;
	private int terceroID;
	
	
	public TelefonosBean() {}
	
	public TelefonosBean(int iD, String telefono, int terceroID) {
		super();
		ID = iD;
		this.telefono = telefono;
		this.terceroID = terceroID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public int getTerceroID() {
		return terceroID;
	}
	public void setTerceroID(int terceroID) {
		this.terceroID = terceroID;
	}
}
