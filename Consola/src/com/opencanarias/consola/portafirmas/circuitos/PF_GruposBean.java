/**
 * 
 */
package com.opencanarias.consola.portafirmas.circuitos;

import java.io.Serializable;

import com.opencanarias.consola.portafirmas.CatalogoFirmantes.FirmanteBean;

/**
 * @author Tomás Delgado Acosta
 * 		ID_GRUPO	NUMBER(16,0)	No	
		FIRMANTES_REQUERIDOS	NUMBER(2,0)	Yes	
		ORDEN	NUMBER(2,0)	Yes	
		ID_FLUJO	NUMBER(16,0)	Yes	
		ID_TIPO_GRUPO	NUMBER(16,0)	Yes	
		CERRADO	NUMBER(1,0)	Yes	0
 *
 */
public class PF_GruposBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private int ID_GRUPO;
	private int FIRMANTES_REQUERIDOS;
	private int ORDEN;
	private int ID_FLUJO;
	private int ID_TIPO_GRUPO;
	private int CERRADO;
	private PF_GrupoPersonasBean grupoPersonas;
	private FirmanteBean firmante;
	private String TIPO_GRUPO;
	
	public int getID_GRUPO() {
		return ID_GRUPO;
	}
	public void setID_GRUPO(int iD_GRUPO) {
		ID_GRUPO = iD_GRUPO;
	}
	public int getFIRMANTES_REQUERIDOS() {
		return FIRMANTES_REQUERIDOS;
	}
	public void setFIRMANTES_REQUERIDOS(int fIRMANTES_REQUERIDOS) {
		FIRMANTES_REQUERIDOS = fIRMANTES_REQUERIDOS;
	}
	public int getORDEN() {
		return ORDEN;
	}
	public void setORDEN(int oRDEN) {
		ORDEN = oRDEN;
	}
	public int getID_FLUJO() {
		return ID_FLUJO;
	}
	public void setID_FLUJO(int iD_FLUJO) {
		ID_FLUJO = iD_FLUJO;
	}
	public int getID_TIPO_GRUPO() {
		return ID_TIPO_GRUPO;
	}
	public void setID_TIPO_GRUPO(int iD_TIPO_GRUPO) {
		ID_TIPO_GRUPO = iD_TIPO_GRUPO;
	}
	public int getCERRADO() {
		return CERRADO;
	}
	public void setCERRADO(int cERRADO) {
		CERRADO = cERRADO;
	}
	public PF_GrupoPersonasBean getGrupoPersonas() {
		return grupoPersonas;
	}
	public void setGrupoPersonas(PF_GrupoPersonasBean grupoPersonas) {
		this.grupoPersonas = grupoPersonas;
	}
	public FirmanteBean getFirmante() {
		return firmante;
	}
	public void setFirmante(FirmanteBean firmante) {
		this.firmante = firmante;
	}
	public String getTIPO_GRUPO() {
		return TIPO_GRUPO;
	}
	public void setTIPO_GRUPO(String tIPO_GRUPO) {
		TIPO_GRUPO = tIPO_GRUPO;
	}
	@Override
	public String toString() {
		return "PF_GruposBean [ID_GRUPO=" + ID_GRUPO + ", FIRMANTES_REQUERIDOS=" + FIRMANTES_REQUERIDOS + ", ORDEN=" + ORDEN + ", ID_FLUJO=" + ID_FLUJO
				+ ", ID_TIPO_GRUPO=" + ID_TIPO_GRUPO + ", CERRADO=" + CERRADO + ", grupoPersonas=" + grupoPersonas + ", firmante=" + firmante + ", TIPO_GRUPO="
				+ TIPO_GRUPO + "]";
	}
	
}
