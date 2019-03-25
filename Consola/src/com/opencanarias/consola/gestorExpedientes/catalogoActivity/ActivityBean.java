package com.opencanarias.consola.gestorExpedientes.catalogoActivity;

import java.io.Serializable;

public class ActivityBean implements Serializable {

	private static final long serialVersionUID = 1L;
	String ID; // VARCHAR2(200 BYTE)
	String WF_ID; // VARCHAR2(200 BYTE)
	String WF_VERSION; // VARCHAR2(5 BYTE)
	String DESCRIPTION; // VARCHAR2(200 BYTE)
	String ROLE_ID; // NUMBER(15,0)
	int PHASE; // NUMBER(15,0)
	int RESOLUTION_ACTIVITY; // NUMBER(1,0)
	int NOTIFICATIONS; // NUMBER(5,0)
	int EXPIRATION_DAYS; // NUMBER(10,0)
	String ALERT; // VARCHAR2(200 CHAR)
	String ALERT_CONDITION; // VARCHAR2(200 BYTE)
	String ALERT_DPTOS; // VARCHAR2(255 BYTE)


	public ActivityBean(){
		
	}
	
	public ActivityBean(String ID_, String WF_ID_, String WF_VERSION_, String DESCRIPTION_, String ROLE_ID_, int PHASE_, int RESOLUTION_ACTIVITY_,
			int NOTIFICATIONS_, int EXPIRATION_DAYS_, String ALERT_, String ALERT_CONDITION_, String ALERT_DPTOS_) {
		ID = ID_;
		WF_ID = WF_ID_;
		WF_VERSION = WF_VERSION_;
		DESCRIPTION = DESCRIPTION_;
		ROLE_ID = ROLE_ID_;
		PHASE = PHASE_;
		RESOLUTION_ACTIVITY = RESOLUTION_ACTIVITY_;
		NOTIFICATIONS = NOTIFICATIONS_;
		EXPIRATION_DAYS = EXPIRATION_DAYS_;
		ALERT = ALERT_;
		ALERT_CONDITION = ALERT_CONDITION_;
		ALERT_DPTOS = ALERT_DPTOS_;

	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getWF_ID() {
		return WF_ID;
	}

	public void setWF_ID(String wF_ID) {
		WF_ID = wF_ID;
	}

	public String getWF_VERSION() {
		return WF_VERSION;
	}

	public void setWF_VERSION(String wF_VERSION) {
		WF_VERSION = wF_VERSION;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getROLE_ID() {
		return ROLE_ID;
	}

	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}

	public int getPHASE() {
		return PHASE;
	}

	public void setPHASE(int pHASE) {
		PHASE = pHASE;
	}

	public int getRESOLUTION_ACTIVITY() {
		return RESOLUTION_ACTIVITY;
	}

	public void setRESOLUTION_ACTIVITY(int rESOLUTION_ACTIVITY) {
		RESOLUTION_ACTIVITY = rESOLUTION_ACTIVITY;
	}

	public int getNOTIFICATIONS() {
		return NOTIFICATIONS;
	}

	public void setNOTIFICATIONS(int nOTIFICATIONS) {
		NOTIFICATIONS = nOTIFICATIONS;
	}

	public int getEXPIRATION_DAYS() {
		return EXPIRATION_DAYS;
	}

	public void setEXPIRATION_DAYS(int eXPIRATION_DAYS) {
		EXPIRATION_DAYS = eXPIRATION_DAYS;
	}

	public String getALERT() {
		return ALERT;
	}

	public void setALERT(String aLERT) {
		ALERT = aLERT;
	}

	public String getALERT_CONDITION() {
		return ALERT_CONDITION;
	}

	public void setALERT_CONDITION(String aLERT_CONDITION) {
		ALERT_CONDITION = aLERT_CONDITION;
	}

	public String getALERT_DPTOS() {
		return ALERT_DPTOS;
	}

	public void setALERT_DPTOS(String aLERT_DPTOS) {
		ALERT_DPTOS = aLERT_DPTOS;
	}
}
