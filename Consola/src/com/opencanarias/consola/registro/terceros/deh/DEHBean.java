package com.opencanarias.consola.registro.terceros.deh;

import java.io.Serializable;

public class DEHBean implements Serializable{


	private static final long serialVersionUID = 1L;
	//	ID	NUMBER(19,0)
	//	DEH	VARCHAR2(160 BYTE)
	//	TERCERO_ID	NUMBER(19,0)
	private int ID;
	private String DEH;
	private int terceroID;
	
	public DEHBean() {}
	
	public DEHBean(int iD, String dEH, int terceroID) {
		super();
		ID = iD;
		DEH = dEH;
		this.terceroID = terceroID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}

	public String getDEH() {
		return DEH;
	}
	public void setDEH(String deh) {
		DEH = deh;
	}
	
	public int getTerceroID() {
		return terceroID;
	}
	public void setTerceroID(int terceroID) {
		this.terceroID = terceroID;
	}
		   
	
}
