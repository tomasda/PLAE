package com.opencanarias.consola.registro.tablasComunes;

public class MunicipiosBean {
	private String ID; 			//	VARCHAR2(5 BYTE)
	private String DESCRIPCION;					//	VARCHAR2(50 BYTE)
	private String PROVINCIA_ID;	 			//  VARCHAR2(2 BYTE)
	
	
	public MunicipiosBean(String ID_, String DESCRIPCION_) {
		ID = ID_;
		DESCRIPCION = DESCRIPCION_;
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
	public String getPROVINCIA_ID() {
		return PROVINCIA_ID;
	}
	public void setPROVINCIA_ID(String pROVINCIA_ID) {
		PROVINCIA_ID = pROVINCIA_ID;
	}
}
