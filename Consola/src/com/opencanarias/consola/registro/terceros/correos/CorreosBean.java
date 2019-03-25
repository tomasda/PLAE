package com.opencanarias.consola.registro.terceros.correos;

import java.io.Serializable;

public class CorreosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int ID;
	private String email;
	private int terceroID;
	
	public CorreosBean() {}
	
	public CorreosBean(int iD, String email, int terceroID) {
		super();
		ID = iD;
		this.email = email;
		this.terceroID = terceroID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTerceroID() {
		return terceroID;
	}
	public void setTerceroID(int terceroID) {
		this.terceroID = terceroID;
	}
}
