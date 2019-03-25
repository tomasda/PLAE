package com.opencanarias.consola.registro.tablasComunes;

public class IdDescripcionBean {

	String ID;
	String DESCRIPTION;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public IdDescripcionBean(String ID_, String DESCRIPCION_) {
		ID = ID_;
		DESCRIPTION = DESCRIPCION_;
	}

}
