package com.opencanarias.consola.tabla.cat_procedimientos_circuitos;

import java.io.Serializable;

public class CatProcedimientoCircuitoBean implements Serializable {	
	
	private static final long serialVersionUID = 1L;
// TABLA CAT_PROCEDIMIENTOS_CIRCUITOS
	
	private String ID;					//	ID	VARCHAR2(200 CHAR)
	private String Procedimiento_ID;	//	PROC_ID	VARCHAR2(200 CHAR)
	private String Actividad_ID;		//	ACTIVIDAD_ID	VARCHAR2(200 CHAR)
	private String Departamento_ID;		//	DEPARTAMENTO_ID	VARCHAR2(64 CHAR)
	private int CircuitoID;				//	CIRCUITO_ID	NUMBER(19,0)

	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getProcedimiento_ID() {
		return Procedimiento_ID;
	}
	public void setProcedimiento_ID(String procedimiento_ID) {
		Procedimiento_ID = procedimiento_ID;
	}
	public String getActividad_ID() {
		return Actividad_ID;
	}
	public void setActividad_ID(String actividad_ID) {
		Actividad_ID = actividad_ID;
	}
	public String getDepartamento_ID() {
		return Departamento_ID;
	}
	public void setDepartamento_ID(String departamento_ID) {
		Departamento_ID = departamento_ID;
	}
	public int getCircuitoID() {
		return CircuitoID;
	}
	public void setCircuitoID(int circuitoID) {
		CircuitoID = circuitoID;
	}
	
	public CatProcedimientoCircuitoBean(String ID_,String Procedimiento_ID_,String Actividad_ID_,String Departamento_ID_,int CircuitoID_){
		ID=ID_;
		Procedimiento_ID=Procedimiento_ID_;
		Actividad_ID=Actividad_ID_;
		Departamento_ID=Departamento_ID_;
		CircuitoID=CircuitoID_;
	}
}
