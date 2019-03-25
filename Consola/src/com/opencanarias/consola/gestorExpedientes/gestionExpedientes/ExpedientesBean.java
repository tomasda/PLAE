package com.opencanarias.consola.gestorExpedientes.gestionExpedientes;

import java.io.Serializable;
import java.sql.Timestamp;


public class ExpedientesBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String ID_;	//	ID_	VARCHAR2(64 BYTE)
	private String OWNER_;	//	OWNER_	VARCHAR2(200 BYTE)
	private String WF_INSTANCE_;	//	WF_INSTANCE_	VARCHAR2(200 BYTE)
	private String WF_ID_;	//	WF_ID_	VARCHAR2(200 BYTE)
	private String WF_VERSION_;	//	WF_VERSION_	VARCHAR2(5 BYTE)
	private int STATUS_;	//	STATUS_	NUMBER(5,0)
	private Timestamp START_DATE_;	//	START_DATE_	TIMESTAMP(6)
	private Timestamp END_DATE_;	//	END_DATE_	TIMESTAMP(6)
	private String ASUNTO_;	//	ASUNTO_	VARCHAR2(240 BYTE)
	private String INTERESADO_;	//	INTERESADO_	VARCHAR2(100 BYTE)
	private String PENDING_WORKFLOW_ID_;	//	PENDING_WORKFLOW_ID_	VARCHAR2(200 BYTE)
	private String REPOSITORY_URI_;	//	REPOSITORY_URI_	VARCHAR2(160 BYTE)
	private String RECORD_NUMBER_;	//	RECORD_NUMBER_	VARCHAR2(30 BYTE)
	private int MANDATORY_NOTIFICATION_TYPE_;	//	MANDATORY_NOTIFICATION_TYPE_	NUMBER(1,0)
	private String NOTIFICATION_TYPE_;	//	NOTIFICATION_TYPE_	VARCHAR2(15 BYTE)
	private Timestamp RESOLUTION_NOTIF_DATE_;	//	RESOLUTION_NOTIF_DATE_	TIMESTAMP(6)
	private Timestamp RESOLUTION_EFFECT_NOTIF_DATE_;	//	RESOLUTION_EFFECT_NOTIF_DATE_	TIMESTAMP(6)
	
	
	
	public ExpedientesBean() {
		super();
	}


	public ExpedientesBean(String iD_, String oWNER_, String wF_INSTANCE_, String wF_ID_, String wF_VERSION_, int sTATUS_, Timestamp sTART_DATE_,
			Timestamp eND_DATE_, String aSUNTO_, String iNTERESADO_, String pENDING_WORKFLOW_ID_, String rEPOSITORY_URI_, String rECORD_NUMBER_,
			int mANDATORY_NOTIFICATION_TYPE_, String nOTIFICATION_TYPE_, Timestamp rESOLUTION_NOTIF_DATE_, Timestamp rESOLUTION_EFFECT_NOTIF_DATE_) {
		super();
		ID_ = iD_;
		OWNER_ = oWNER_;
		WF_INSTANCE_ = wF_INSTANCE_;
		WF_ID_ = wF_ID_;
		WF_VERSION_ = wF_VERSION_;
		STATUS_ = sTATUS_;
		START_DATE_ = sTART_DATE_;
		END_DATE_ = eND_DATE_;
		ASUNTO_ = aSUNTO_;
		INTERESADO_ = iNTERESADO_;
		PENDING_WORKFLOW_ID_ = pENDING_WORKFLOW_ID_;
		REPOSITORY_URI_ = rEPOSITORY_URI_;
		RECORD_NUMBER_ = rECORD_NUMBER_;
		MANDATORY_NOTIFICATION_TYPE_ = mANDATORY_NOTIFICATION_TYPE_;
		NOTIFICATION_TYPE_ = nOTIFICATION_TYPE_;
		RESOLUTION_NOTIF_DATE_ = rESOLUTION_NOTIF_DATE_;
		RESOLUTION_EFFECT_NOTIF_DATE_ = rESOLUTION_EFFECT_NOTIF_DATE_;
	}


	public String getID_() {
		return ID_;
	}


	public void setID_(String iD_) {
		ID_ = iD_;
	}


	public String getOWNER_() {
		return OWNER_;
	}


	public void setOWNER_(String oWNER_) {
		OWNER_ = oWNER_;
	}


	public String getWF_INSTANCE_() {
		return WF_INSTANCE_;
	}


	public void setWF_INSTANCE_(String wF_INSTANCE_) {
		WF_INSTANCE_ = wF_INSTANCE_;
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


	public int getSTATUS_() {
		return STATUS_;
	}


	public void setSTATUS_(int sTATUS_) {
		STATUS_ = sTATUS_;
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


	public String getASUNTO_() {
		return ASUNTO_;
	}


	public void setASUNTO_(String aSUNTO_) {
		ASUNTO_ = aSUNTO_;
	}


	public String getINTERESADO_() {
		return INTERESADO_;
	}


	public void setINTERESADO_(String iNTERESADO_) {
		INTERESADO_ = iNTERESADO_;
	}


	public String getPENDING_WORKFLOW_ID_() {
		return PENDING_WORKFLOW_ID_;
	}


	public void setPENDING_WORKFLOW_ID_(String pENDING_WORKFLOW_ID_) {
		PENDING_WORKFLOW_ID_ = pENDING_WORKFLOW_ID_;
	}


	public String getREPOSITORY_URI_() {
		return REPOSITORY_URI_;
	}


	public void setREPOSITORY_URI_(String rEPOSITORY_URI_) {
		REPOSITORY_URI_ = rEPOSITORY_URI_;
	}


	public String getRECORD_NUMBER_() {
		return RECORD_NUMBER_;
	}


	public void setRECORD_NUMBER_(String rECORD_NUMBER_) {
		RECORD_NUMBER_ = rECORD_NUMBER_;
	}


	public int getMANDATORY_NOTIFICATION_TYPE_() {
		return MANDATORY_NOTIFICATION_TYPE_;
	}


	public void setMANDATORY_NOTIFICATION_TYPE_(int mANDATORY_NOTIFICATION_TYPE_) {
		MANDATORY_NOTIFICATION_TYPE_ = mANDATORY_NOTIFICATION_TYPE_;
	}


	public String getNOTIFICATION_TYPE_() {
		return NOTIFICATION_TYPE_;
	}


	public void setNOTIFICATION_TYPE_(String nOTIFICATION_TYPE_) {
		NOTIFICATION_TYPE_ = nOTIFICATION_TYPE_;
	}


	public Timestamp getRESOLUTION_NOTIF_DATE_() {
		return RESOLUTION_NOTIF_DATE_;
	}


	public void setRESOLUTION_NOTIF_DATE_(Timestamp rESOLUTION_NOTIF_DATE_) {
		RESOLUTION_NOTIF_DATE_ = rESOLUTION_NOTIF_DATE_;
	}


	public Timestamp getRESOLUTION_EFFECT_NOTIF_DATE_() {
		return RESOLUTION_EFFECT_NOTIF_DATE_;
	}


	public void setRESOLUTION_EFFECT_NOTIF_DATE_(Timestamp rESOLUTION_EFFECT_NOTIF_DATE_) {
		RESOLUTION_EFFECT_NOTIF_DATE_ = rESOLUTION_EFFECT_NOTIF_DATE_;
	}
	

	

}
