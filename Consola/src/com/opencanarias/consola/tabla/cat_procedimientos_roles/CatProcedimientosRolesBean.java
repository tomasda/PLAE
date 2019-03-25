package com.opencanarias.consola.tabla.cat_procedimientos_roles;

import java.io.Serializable;

public class CatProcedimientosRolesBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int ID_;//	ID	NUMBER(15,0)
	private String DEPARTAMENTO_ID_;//	DEPARTAMENTO_ID	VARCHAR2(64 BYTE)
	private String FAMILIA_ID_;//	FAMILIA_ID	VARCHAR2(200 BYTE)
	private String PROCEDIMIENTO_ID_;//	PROCEDIMIENTO_ID	VARCHAR2(200 BYTE)
	

	public CatProcedimientosRolesBean(int iD_, String dEPARTAMENTO_ID_, String fAMILIA_ID_, String pROCEDIMIENTO_ID_) {
		ID_ = iD_;
		DEPARTAMENTO_ID_ = dEPARTAMENTO_ID_;
		FAMILIA_ID_ = fAMILIA_ID_;
		PROCEDIMIENTO_ID_ = pROCEDIMIENTO_ID_;
	}
	public int getID_() {
		return ID_;
	}
	public void setID_(int iD_) {
		ID_ = iD_;
	}
	public String getDEPARTAMENTO_ID_() {
		return DEPARTAMENTO_ID_;
	}
	public void setDEPARTAMENTO_ID_(String dEPARTAMENTO_ID_) {
		DEPARTAMENTO_ID_ = dEPARTAMENTO_ID_;
	}
	public String getFAMILIA_ID_() {
		return FAMILIA_ID_;
	}
	public void setFAMILIA_ID_(String fAMILIA_ID_) {
		FAMILIA_ID_ = fAMILIA_ID_;
	}
	public String getPROCEDIMIENTO_ID_() {
		return PROCEDIMIENTO_ID_;
	}
	public void setPROCEDIMIENTO_ID_(String pROCEDIMIENTO_ID_) {
		PROCEDIMIENTO_ID_ = pROCEDIMIENTO_ID_;
	}
	@Override
	public String toString() {
		return "CatProcedimientosRolesBean [ID_=" + ID_ + ", DEPARTAMENTO_ID_=" + DEPARTAMENTO_ID_ + ", FAMILIA_ID_="
				+ FAMILIA_ID_ + ", PROCEDIMIENTO_ID_=" + PROCEDIMIENTO_ID_ + "]";
	}



}
