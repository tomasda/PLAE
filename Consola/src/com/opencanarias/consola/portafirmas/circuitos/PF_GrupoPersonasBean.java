/**
 * 
 */
package com.opencanarias.consola.portafirmas.circuitos;

import java.io.Serializable;

/**
 * @author Tomás Delgado Acosta
 *  ID_GRUPO_PERSONA	NUMBER(16,0)	No	
	ID_GRUPO	NUMBER(16,0)	Yes	
	ID_PERSONA	NUMBER(16,0)	Yes	
	PERSONA_OBLIGATORIA	NUMBER(1,0)	Yes	0
	REALIZADO	NUMBER(1,0)	Yes	0
 *
 */
public class PF_GrupoPersonasBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int ID_GRUPO_PERSONA;
	private int ID_GRUPO;
	private int ID_PERSONA;
	private int PERSONA_OBLIGATORIA;
	private int REALIZADO;
	
	public int getID_GRUPO_PERSONA() {
		return ID_GRUPO_PERSONA;
	}
	public void setID_GRUPO_PERSONA(int iD_GRUPO_PERSONA) {
		ID_GRUPO_PERSONA = iD_GRUPO_PERSONA;
	}
	public int getID_GRUPO() {
		return ID_GRUPO;
	}
	public void setID_GRUPO(int iD_GRUPO) {
		ID_GRUPO = iD_GRUPO;
	}
	public int getID_PERSONA() {
		return ID_PERSONA;
	}
	public void setID_PERSONA(int iD_PERSONA) {
		ID_PERSONA = iD_PERSONA;
	}
	public int getPERSONA_OBLIGATORIA() {
		return PERSONA_OBLIGATORIA;
	}
	public void setPERSONA_OBLIGATORIA(int pERSONA_OBLIGATORIA) {
		PERSONA_OBLIGATORIA = pERSONA_OBLIGATORIA;
	}
	public int getREALIZADO() {
		return REALIZADO;
	}
	public void setREALIZADO(int rEALIZADO) {
		REALIZADO = rEALIZADO;
	}
}
