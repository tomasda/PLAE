package com.opencanarias.apsct.portafirmas.entities;

import java.io.Serializable;
import java.util.List;

public class PersonaRevisores implements Serializable{

	private static final long serialVersionUID = -6120225095690915015L;
	
	private Persona persona;
	private List<Persona> listColaboradores;
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public List<Persona> getListColaboradores() {
		return listColaboradores;
	}
	public void setListColaboradores(List<Persona> listColaboradores) {
		this.listColaboradores = listColaboradores;
	}

}
