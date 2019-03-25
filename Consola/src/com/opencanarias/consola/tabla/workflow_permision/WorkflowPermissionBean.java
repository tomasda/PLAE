package com.opencanarias.consola.tabla.workflow_permision;

import java.io.Serializable;

public class WorkflowPermissionBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int ID_;	//	ID	NUMBER(10,0)
	private String PROC_ID_;	//	PROC_ID	VARCHAR2(200 CHAR)
	//	ROLE2	NUMBER(10,0)
	private int PERMISSION_;	//	PERMISSION	NUMBER(5,0)
	private String ROLE_ID_;//	ROLE_ID	VARCHAR2(64 BYTE)
	private String STARTER_ROLE_ID_;//	STARTER_ROLE_ID	VARCHAR2(64 BYTE)
	
	
	
	public WorkflowPermissionBean(int iD_, String pROC_ID_, int pERMISSION_, String rOLE_ID_, String sTARTER_ROLE_ID_) {
		super();
		ID_ = iD_;
		PROC_ID_ = pROC_ID_;
		PERMISSION_ = pERMISSION_;
		ROLE_ID_ = rOLE_ID_;
		STARTER_ROLE_ID_ = sTARTER_ROLE_ID_;
	}
	
	public int getID_() {
		return ID_;
	}
	public void setID_(int iD_) {
		ID_ = iD_;
	}
	public String getPROC_ID_() {
		return PROC_ID_;
	}
	public void setPROC_ID_(String pROC_ID_) {
		PROC_ID_ = pROC_ID_;
	}
	public int getPERMISSION_() {
		return PERMISSION_;
	}
	public void setPERMISSION_(int pERMISSION_) {
		PERMISSION_ = pERMISSION_;
	}
	public String getROLE_ID_() {
		return ROLE_ID_;
	}
	public void setROLE_ID_(String rOLE_ID_) {
		ROLE_ID_ = rOLE_ID_;
	}
	public String getSTARTER_ROLE_ID_() {
		return STARTER_ROLE_ID_;
	}
	public void setSTARTER_ROLE_ID_(String sTARTER_ROLE_ID_) {
		STARTER_ROLE_ID_ = sTARTER_ROLE_ID_;
	}
	@Override
	public String toString() {
		return "WorkflowPermisionBean [ID_=" + ID_ + ", PROC_ID_=" + PROC_ID_ + ", PERMISSION_=" + PERMISSION_
				+ ", ROLE_ID_=" + ROLE_ID_ + ", STARTER_ROLE_ID_=" + STARTER_ROLE_ID_ + "]";
	}
}
