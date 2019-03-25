package com.opencanarias.consola.registro.terceros.direcciones;

import java.io.Serializable;

public class DireccionesBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private int ID; // int(19) DEFAULT NULL,
	private String DIRECCION; // varchar(160) DEFAULT NULL,
	private String CODIGO_POSTAL; // varchar(5) DEFAULT NULL,
	private String OBSERVACIONES; // varchar(160) DEFAULT NULL,
	private String PAIS_ID; // varchar(4) DEFAULT NULL,
	private int TERCERO_ID; // int(19) DEFAULT NULL,
	private String MUNICIPIO_ID; // varchar(5) DEFAULT NULL,
	private String PROVINCIA_ID; // varchar(2) DEFAULT NULL
	
	public DireccionesBean() {}

	public DireccionesBean(int iD, String dIRECCION, String cODIGO_POSTAL, String oBSERVACIONES, String pAIS_ID, int tERCERO_ID, String mUNICIPIO_ID,
			String pROVINCIA_ID) {
		super();
		ID = iD;
		DIRECCION = dIRECCION;
		CODIGO_POSTAL = cODIGO_POSTAL;
		OBSERVACIONES = oBSERVACIONES;
		PAIS_ID = pAIS_ID;
		TERCERO_ID = tERCERO_ID;
		MUNICIPIO_ID = mUNICIPIO_ID;
		PROVINCIA_ID = pROVINCIA_ID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getDIRECCION() {
		return DIRECCION;
	}

	public void setDIRECCION(String dIRECCION) {
		DIRECCION = dIRECCION;
	}

	public String getCODIGO_POSTAL() {
		return CODIGO_POSTAL;
	}

	public void setCODIGO_POSTAL(String cODIGO_POSTAL) {
		CODIGO_POSTAL = cODIGO_POSTAL;
	}

	public String getOBSERVACIONES() {
		return OBSERVACIONES;
	}

	public void setOBSERVACIONES(String oBSERVACIONES) {
		OBSERVACIONES = oBSERVACIONES;
	}

	public String getPAIS_ID() {
		return PAIS_ID;
	}

	public void setPAIS_ID(String pAIS_ID) {
		PAIS_ID = pAIS_ID;
	}

	public int getTERCERO_ID() {
		return TERCERO_ID;
	}

	public void setTERCERO_ID(int tERCERO_ID) {
		TERCERO_ID = tERCERO_ID;
	}

	public String getMUNICIPIO_ID() {
		return MUNICIPIO_ID;
	}

	public void setMUNICIPIO_ID(String mUNICIPIO_ID) {
		MUNICIPIO_ID = mUNICIPIO_ID;
	}

	public String getPROVINCIA_ID() {
		return PROVINCIA_ID;
	}

	public void setPROVINCIA_ID(String pROVINCIA_ID) {
		PROVINCIA_ID = pROVINCIA_ID;
	}
}
