package com.opencanarias.consola.registro.tablasComunes;

public class PaisesBean {
	private String ID; 			//	VARCHAR2(4 BYTE)
	private String DESCRIPCION; 	//	VARCHAR2(30 BYTE)
	
	public PaisesBean(String ID_, String DESCRIPCION_) {
		ID=ID_;
		DESCRIPCION=DESCRIPCION_;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDESCRIPCION() {
		return DESCRIPCION;
	}
	public void setDESCRIPCION(String dESCRIPCION) {
		DESCRIPCION = dESCRIPCION;
	}
}
