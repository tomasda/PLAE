package com.opencanarias.consola.registro.tablasComunes;

public class CanalesBean {
	
	private String ID;//	ID	VARCHAR2(2 BYTE)
	private String DESCRIPCION;//	DESCRIPCION	VARCHAR2(50 BYTE)
	
	private String ID_TMP;
	private String DESCRIPCION_TMP;
	
	
	public CanalesBean(String ID_, String DESCRIPCION_) {
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
	public String getID_TMP() {
		return ID_TMP;
	}
	public void setID_TMP(String iD_TMP) {
		ID_TMP = iD_TMP;
	}
	public String getDESCRIPCION_TMP() {
		return DESCRIPCION_TMP;
	}
	public void setDESCRIPCION_TMP(String dESCRIPCION_TMP) {
		DESCRIPCION_TMP = dESCRIPCION_TMP;
	}

}
