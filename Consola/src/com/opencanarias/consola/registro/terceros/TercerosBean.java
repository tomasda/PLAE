package com.opencanarias.consola.registro.terceros;

import java.io.Serializable;

public class TercerosBean implements Serializable {


	private static final long serialVersionUID = 1L;
	private int ID; // ID NUMBER(19,0)
	private String IDENTIFICACION; // IDENTIFICACION VARCHAR2(17 BYTE)
	private String RAZON_SOCIAL; // RAZON_SOCIAL VARCHAR2(80 BYTE)
	private String NOMBRE; // NOMBRE VARCHAR2(30 BYTE)
	private String APELLIDO_1; // APELLIDO_1 VARCHAR2(30 BYTE)
	private String APELLIDO_2; // APELLIDO_2 VARCHAR2(30 BYTE)
	private String OBSERVACIONES; // OBSERVACIONES VARCHAR2(160 BYTE)
	private String TIPO_IDENTIFICACION_ID; // TIPO_IDENTIFICACION_ID CHAR(1 BYTE)
	private String CANAL_PREFERENTE_ID; // CANAL_PREFERENTE_ID VARCHAR2(2 BYTE)
	private int NOTIF_ELECT_OBLIGATORIA; // NOTIF_ELECT_OBLIGATORIA NUMBER(1,0)
	private int REGISTRO_ELECTRONICO; // REGISTRO_ELECTRONICO NUMBER(1,0)

	public TercerosBean(){

	}
	
	
	public TercerosBean(int ID_, String IDENTIFICACION_, String RAZON_SOCIAL_, String NOMBRE_, String APELLIDO_1_, String APELLIDO_2_, String OBSERVACIONES_,
			String TIPO_IDENTIFICACION_ID_, String CANAL_PREFERENTE_ID_, int NOTIF_ELECT_OBLIGATORIA_, int REGISTRO_ELECTRONICO_) {
		ID=ID_;
		IDENTIFICACION=IDENTIFICACION_;
		RAZON_SOCIAL=RAZON_SOCIAL_;
		NOMBRE=NOMBRE_;
		APELLIDO_1=APELLIDO_1_;
		APELLIDO_2=APELLIDO_2_;
		OBSERVACIONES=OBSERVACIONES_;
		TIPO_IDENTIFICACION_ID=TIPO_IDENTIFICACION_ID_;
		CANAL_PREFERENTE_ID=CANAL_PREFERENTE_ID_;
		NOTIF_ELECT_OBLIGATORIA=NOTIF_ELECT_OBLIGATORIA_;
		REGISTRO_ELECTRONICO=REGISTRO_ELECTRONICO_;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getIDENTIFICACION() {
		return IDENTIFICACION;
	}

	public void setIDENTIFICACION(String iDENTIFICACION) {
		IDENTIFICACION = iDENTIFICACION;
	}

	public String getRAZON_SOCIAL() {
		return RAZON_SOCIAL;
	}

	public void setRAZON_SOCIAL(String rAZON_SOCIAL) {
		RAZON_SOCIAL = rAZON_SOCIAL;
	}

	public String getNOMBRE() {
		return NOMBRE;
	}

	public void setNOMBRE(String nOMBRE) {
		NOMBRE = nOMBRE;
	}

	public String getAPELLIDO_1() {
		return APELLIDO_1;
	}

	public void setAPELLIDO_1(String aPELLIDO_1) {
		APELLIDO_1 = aPELLIDO_1;
	}

	public String getAPELLIDO_2() {
		return APELLIDO_2;
	}

	public void setAPELLIDO_2(String aPELLIDO_2) {
		APELLIDO_2 = aPELLIDO_2;
	}

	public String getOBSERVACIONES() {
		return OBSERVACIONES;
	}

	public void setOBSERVACIONES(String oBSERVACIONES) {
		OBSERVACIONES = oBSERVACIONES;
	}

	public String getTIPO_IDENTIFICACION_ID() {
		return TIPO_IDENTIFICACION_ID;
	}

	public void setTIPO_IDENTIFICACION_ID(String tIPO_IDENTIFICACION_ID) {
		TIPO_IDENTIFICACION_ID = tIPO_IDENTIFICACION_ID;
	}

	public String getCANAL_PREFERENTE_ID() {
		return CANAL_PREFERENTE_ID;
	}

	public void setCANAL_PREFERENTE_ID(String cANAL_PREFERENTE_ID) {
		CANAL_PREFERENTE_ID = cANAL_PREFERENTE_ID;
	}

	public int getNOTIF_ELECT_OBLIGATORIA() {
		return NOTIF_ELECT_OBLIGATORIA;
	}

	public void setNOTIF_ELECT_OBLIGATORIA(int nOTIF_ELECT_OBLIGATORIA) {
		NOTIF_ELECT_OBLIGATORIA = nOTIF_ELECT_OBLIGATORIA;
	}

	public int getREGISTRO_ELECTRONICO() {
		return REGISTRO_ELECTRONICO;
	}

	public void setREGISTRO_ELECTRONICO(int rEGISTRO_ELECTRONICO) {
		REGISTRO_ELECTRONICO = rEGISTRO_ELECTRONICO;
	}




	public boolean getNOTIF_ELECT_OBLIGATORIA_Boolean() {
		if (NOTIF_ELECT_OBLIGATORIA == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void setNOTIF_ELECT_OBLIGATORIA_Boolean(boolean dato_TMP) {
		if (dato_TMP == false) {
			NOTIF_ELECT_OBLIGATORIA = 0;
		} else {
			NOTIF_ELECT_OBLIGATORIA = 1;
		}
	}

	public boolean getREGISTRO_ELECTRONICO_Boolean() {
		if (REGISTRO_ELECTRONICO == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void setREGISTRO_ELECTRONICO_Boolean(boolean dato_TMP) {
		if (dato_TMP == false) {
			REGISTRO_ELECTRONICO = 0;
		} else {
			REGISTRO_ELECTRONICO = 1;
		}
	}
}
