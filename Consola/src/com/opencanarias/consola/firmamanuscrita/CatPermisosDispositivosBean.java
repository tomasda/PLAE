package com.opencanarias.consola.firmamanuscrita;

import java.io.Serializable;

public class CatPermisosDispositivosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ID;
	private String DESTINATARIO_ID;
	private String DESTINATARIO_TIPO;
	private String DISPOSITIVO_ID;
	private String PERMISO_TIPO;
	
	public CatPermisosDispositivosBean(String iD, String dESTINATARIO_ID, String dESTINATARIO_TIPO,
			String dISPOSITIVO_ID, String pERMISO_TIPO) {
		super();
		ID = iD;
		DESTINATARIO_ID = dESTINATARIO_ID;
		DESTINATARIO_TIPO = dESTINATARIO_TIPO;
		DISPOSITIVO_ID = dISPOSITIVO_ID;
		PERMISO_TIPO = pERMISO_TIPO;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDESTINATARIO_ID() {
		return DESTINATARIO_ID;
	}
	public void setDESTINATARIO_ID(String dESTINATARIO_ID) {
		DESTINATARIO_ID = dESTINATARIO_ID;
	}
	public String getDESTINATARIO_TIPO() {
		return DESTINATARIO_TIPO;
	}
	public void setDESTINATARIO_TIPO(String dESTINATARIO_TIPO) {
		DESTINATARIO_TIPO = dESTINATARIO_TIPO;
	}
	public String getDISPOSITIVO_ID() {
		return DISPOSITIVO_ID;
	}
	public void setDISPOSITIVO_ID(String dISPOSITIVO_ID) {
		DISPOSITIVO_ID = dISPOSITIVO_ID;
	}
	public String getPERMISO_TIPO() {
		return PERMISO_TIPO;
	}
	public void setPERMISO_TIPO(String pERMISO_TIPO) {
		PERMISO_TIPO = pERMISO_TIPO;
	}
	@Override
	public String toString() {
		return "CatPermisosDispositivosBean [ID=" + ID + ", DESTINATARIO_ID=" + DESTINATARIO_ID + ", DESTINATARIO_TIPO="
				+ DESTINATARIO_TIPO + ", DISPOSITIVO_ID=" + DISPOSITIVO_ID + ", PERMISO_TIPO=" + PERMISO_TIPO + "]";
	}
	
	
}
