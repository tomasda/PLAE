package com.opencanarias.consola.tabla.activity;

import java.io.Serializable;

public class Activity_ implements Serializable{
	private static final long serialVersionUID = 1L;
	private String ID_;
	private String WF_ID_;
	private String WF_VERSION_;
	private String DESCRIPTION_;
	//ROLE_ID_2
	private int PHASE_;
	private int RESOLUTION_ACTIVITY_;
	private int NOTIFICATIONS_;
	private int EXPIRATION_DAYS_;
	private String ALERT_;
	private String ALERT_CONDITION_;
	private String ALERT_DPTOS_;
	private String ROLE_ID_;
	
	public Activity_() {
		
	}

	public String getID_() {
		return ID_;
	}

	public void setID_(String iD_) {
		ID_ = iD_;
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

	public String getDESCRIPTION_() {
		return DESCRIPTION_;
	}

	public void setDESCRIPTION_(String dESCRIPTION_) {
		DESCRIPTION_ = dESCRIPTION_;
	}

	public int getPHASE_() {
		return PHASE_;
	}

	public void setPHASE_(int pHASE_) {
		PHASE_ = pHASE_;
	}

	public int getRESOLUTION_ACTIVITY_() {
		return RESOLUTION_ACTIVITY_;
	}

	public void setRESOLUTION_ACTIVITY_(int rESOLUTION_ACTIVITY_) {
		RESOLUTION_ACTIVITY_ = rESOLUTION_ACTIVITY_;
	}

	public int getNOTIFICATIONS_() {
		return NOTIFICATIONS_;
	}

	public void setNOTIFICATIONS_(int nOTIFICATIONS_) {
		NOTIFICATIONS_ = nOTIFICATIONS_;
	}

	public int getEXPIRATION_DAYS_() {
		return EXPIRATION_DAYS_;
	}

	public void setEXPIRATION_DAYS_(int eXPIRATION_DAYS_) {
		EXPIRATION_DAYS_ = eXPIRATION_DAYS_;
	}

	public String getALERT_() {
		return ALERT_;
	}

	public void setALERT_(String aLERT_) {
		ALERT_ = aLERT_;
	}

	public String getALERT_CONDITION_() {
		return ALERT_CONDITION_;
	}

	public void setALERT_CONDITION_(String aLERT_CONDITION_) {
		ALERT_CONDITION_ = aLERT_CONDITION_;
	}

	public String getALERT_DPTOS_() {
		return ALERT_DPTOS_;
	}

	public void setALERT_DPTOS_(String aLERT_DPTOS_) {
		ALERT_DPTOS_ = aLERT_DPTOS_;
	}

	public String getROLE_ID_() {
		return ROLE_ID_;
	}

	public void setROLE_ID_(String rOLE_ID_) {
		ROLE_ID_ = rOLE_ID_;
	}

	@Override
	public String toString() {
		return "Activity_ [ID_=" + ID_ + ", WF_ID_=" + WF_ID_ + ", WF_VERSION_=" + WF_VERSION_ + ", DESCRIPTION_="
				+ DESCRIPTION_ + ", PHASE_=" + PHASE_ + ", RESOLUTION_ACTIVITY_=" + RESOLUTION_ACTIVITY_
				+ ", NOTIFICATIONS_=" + NOTIFICATIONS_ + ", EXPIRATION_DAYS_=" + EXPIRATION_DAYS_ + ", ALERT_=" + ALERT_
				+ ", ALERT_CONDITION_=" + ALERT_CONDITION_ + ", ALERT_DPTOS_=" + ALERT_DPTOS_ + ", ROLE_ID_=" + ROLE_ID_
				+ "]";
	}

	
}
