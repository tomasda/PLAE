package com.opencanarias.consola.gestorExpedientes.gestionExpedientes;

import java.io.Serializable;
import java.sql.Timestamp;

public class TrackBean implements Serializable {
	private static final long serialVersionUID = 1L;
	int numReg;
	private String estado_;
	private  String ACTIVITY_;//	VARCHAR2(200 BYTE)
	private  String WF_ID_;//	VARCHAR2(200 BYTE)
	private  String WF_VERSION_;//	VARCHAR2(5 BYTE)
	private  String ADMINISTRATIVE_FILE_ID_;//	VARCHAR2(64 BYTE)
	int  ITERATION_;//	NUMBER(15,0)
	private  String OWNER_;//	VARCHAR2(200 BYTE)
	private  Timestamp START_DATE_;//	TIMESTAMP(6)
	private  Timestamp END_DATE_;//	TIMESTAMP(6)
	private  String ROLE_NAME_;//	VARCHAR2(64 CHAR)
	
	
	public TrackBean setValues(int num,String estado,String ACTIVITY,String	WFID,String WFVERSION,String	ADMINISTRATIVEFILEID,int	ITERATION,String	OWNER,Timestamp	STARTDATE,Timestamp	ENDDATE,String	ROLENAME){
		numReg=num;
		estado_=estado;
		ACTIVITY_=ACTIVITY;
		WF_ID_=WFID;
		WF_VERSION_=WFVERSION;
		ADMINISTRATIVE_FILE_ID_=ADMINISTRATIVEFILEID;
		ITERATION_=ITERATION;
		OWNER_=OWNER;
		START_DATE_=STARTDATE;
		END_DATE_=ENDDATE;
		ROLE_NAME_=ROLENAME;
		return null;
	}
	
	public int getNumReg() {
		return numReg;
	}

	public void setNumReg(int numReg) {
		this.numReg = numReg;
	}

	public String getEstado() {
		return estado_;
	}

	public void setEstado(String estado) {
		this.estado_ = estado;
	}

	public String getACTIVITY_() {
		return ACTIVITY_;
	}
	public void setACTIVITY_(String aCTIVITY_) {
		ACTIVITY_ = aCTIVITY_;
	}
	public String getWF_ID_() {
		return WF_ID_;
	}
	public void setWF_ID_(String wF_ID_) {
		WF_ID_ = wF_ID_;
	}
	public String getWF_VERSION_() {
		return WF_VERSION_;
	}
	public void setWF_VERSION_(String wF_VERSION_) {
		WF_VERSION_ = wF_VERSION_;
	}
	public String getADMINISTRATIVE_FILE_ID_() {
		return ADMINISTRATIVE_FILE_ID_;
	}
	public void setADMINISTRATIVE_FILE_ID_(String aDMINISTRATIVE_FILE_ID_) {
		ADMINISTRATIVE_FILE_ID_ = aDMINISTRATIVE_FILE_ID_;
	}
	public int getITERATION_() {
		return ITERATION_;
	}
	public void setITERATION_(int iTERATION_) {
		ITERATION_ = iTERATION_;
	}
	public String getOWNER_() {
		return OWNER_;
	}
	public void setOWNER_(String oWNER_) {
		OWNER_ = oWNER_;
	}
	public Timestamp getSTART_DATE_() {
		return START_DATE_;
	}
	public void setSTART_DATE_(Timestamp sTART_DATE_) {
		START_DATE_ = sTART_DATE_;
	}
	public Timestamp getEND_DATE_() {
		return END_DATE_;
	}
	public void setEND_DATE_(Timestamp eND_DATE_) {
		END_DATE_ = eND_DATE_;
	}
	public String getROLE_NAME_() {
		return ROLE_NAME_;
	}
	public void setROLE_NAME_(String rOLE_NAME_) {
		ROLE_NAME_ = rOLE_NAME_;
	}


	
}
