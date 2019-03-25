package com.opencanarias.consola.gestorExpedientes.documentos;

import java.io.Serializable;
import java.sql.Timestamp;

public class DocumentBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String ADMINISTRATIVE_FILE_ID_;//	VARCHAR2(64 BYTE)
	private String ID_;//	VARCHAR2(160 BYTE)
	private String ACTIVITY_;//	VARCHAR2(200 BYTE)
	private String CONTENT_TYPE_;//	VARCHAR2(96 BYTE)
	private int HAS_DOCUMENT_;//	NUMBER(1,0)
	private Timestamp CREATION_DATE_;//	TIMESTAMP(6)
	private String DESCRIPTION_	;//VARCHAR2(200 BYTE)
	private String SIGN_REFERENCE_;//	VARCHAR2(40 BYTE)
	private String SIGN_ACTIVITY_;//	VARCHAR2(30 BYTE)
	private String RECORD_NUMBER_;//	VARCHAR2(20 BYTE)
	private Timestamp RECORD_DATE_;//	TIMESTAMP(6)
	private String RECORD_TYPE_;//	VARCHAR2(10 BYTE)
	private String TYPE_;//	VARCHAR2(15 BYTE)
	private int CURRENT_;//	NUMBER(1,0)
	private String REPOSITORY_URI_;//	VARCHAR2(160 CHAR)
	private String STATE_;//	VARCHAR2(96 CHAR)
	private String DOCUMENT_TYPE;//	VARCHAR2(4 CHAR)
	private Timestamp ELABORATION_DATE_;//	TIMESTAMP(6)
	private int SCALE_;//	NUMBER(3,2)
	private int  INVERT_WATERMARK_;//	NUMBER(1,0)
	private String ACTUACION_ID_;//	VARCHAR2(64 BYTE)
	private Timestamp CANCEL_DATE;//	TIMESTAMP(6)
	private String CANCEL_USER_ID;//	VARCHAR2(15 BYTE)
	private String DOC_ORIGINAL_URI;//	VARCHAR2(160 BYTE)
	private int ROTATION_;//	NUMBER(10,0)

	
	public String getADMINISTRATIVE_FILE_ID_() {
		return ADMINISTRATIVE_FILE_ID_;
	}
	public void setADMINISTRATIVE_FILE_ID_(String aDMINISTRATIVE_FILE_ID_) {
		ADMINISTRATIVE_FILE_ID_ = aDMINISTRATIVE_FILE_ID_;
	}
	public String getID_() {
		return ID_;
	}
	public void setID_(String iD_) {
		ID_ = iD_;
	}
	public String getACTIVITY_() {
		return ACTIVITY_;
	}
	public void setACTIVITY_(String aCTIVITY_) {
		ACTIVITY_ = aCTIVITY_;
	}
	public String getCONTENT_TYPE_() {
		return CONTENT_TYPE_;
	}
	public void setCONTENT_TYPE_(String cONTENT_TYPE_) {
		CONTENT_TYPE_ = cONTENT_TYPE_;
	}
	public int getHAS_DOCUMENT_() {
		return HAS_DOCUMENT_;
	}
	public void setHAS_DOCUMENT_(int hAS_DOCUMENT_) {
		HAS_DOCUMENT_ = hAS_DOCUMENT_;
	}
	public Timestamp getCREATION_DATE_() {
		return CREATION_DATE_;
	}
	public void setCREATION_DATE_(Timestamp cREATION_DATE_) {
		CREATION_DATE_ = cREATION_DATE_;
	}
	public String getDESCRIPTION_() {
		return DESCRIPTION_;
	}
	public void setDESCRIPTION_(String dESCRIPTION_) {
		DESCRIPTION_ = dESCRIPTION_;
	}
	public String getSIGN_REFERENCE_() {
		return SIGN_REFERENCE_;
	}
	public void setSIGN_REFERENCE_(String sIGN_REFERENCE_) {
		SIGN_REFERENCE_ = sIGN_REFERENCE_;
	}
	public String getSIGN_ACTIVITY_() {
		return SIGN_ACTIVITY_;
	}
	public void setSIGN_ACTIVITY_(String sIGN_ACTIVITY_) {
		SIGN_ACTIVITY_ = sIGN_ACTIVITY_;
	}
	public String getRECORD_NUMBER_() {
		return RECORD_NUMBER_;
	}
	public void setRECORD_NUMBER_(String rECORD_NUMBER_) {
		RECORD_NUMBER_ = rECORD_NUMBER_;
	}
	public Timestamp getRECORD_DATE_() {
		return RECORD_DATE_;
	}
	public void setRECORD_DATE_(Timestamp rECORD_DATE_) {
		RECORD_DATE_ = rECORD_DATE_;
	}
	public String getRECORD_TYPE_() {
		return RECORD_TYPE_;
	}
	public void setRECORD_TYPE_(String rECORD_TYPE_) {
		RECORD_TYPE_ = rECORD_TYPE_;
	}
	public String getTYPE_() {
		return TYPE_;
	}
	public void setTYPE_(String tYPE_) {
		TYPE_ = tYPE_;
	}
	public int getCURRENT_() {
		return CURRENT_;
	}
	public void setCURRENT_(int cURRENT_) {
		CURRENT_ = cURRENT_;
	}
	public String getREPOSITORY_URI_() {
		return REPOSITORY_URI_;
	}
	public void setREPOSITORY_URI_(String rEPOSITORY_URI_) {
		REPOSITORY_URI_ = rEPOSITORY_URI_;
	}
	public String getSTATE_() {
		return STATE_;
	}
	public void setSTATE_(String sTATE_) {
		STATE_ = sTATE_;
	}
	public String getDOCUMENT_TYPE() {
		return DOCUMENT_TYPE;
	}
	public void setDOCUMENT_TYPE(String dOCUMENT_TYPE) {
		DOCUMENT_TYPE = dOCUMENT_TYPE;
	}
	public Timestamp getELABORATION_DATE_() {
		return ELABORATION_DATE_;
	}
	public void setELABORATION_DATE_(Timestamp eLABORATION_DATE_) {
		ELABORATION_DATE_ = eLABORATION_DATE_;
	}
	public int getSCALE_() {
		return SCALE_;
	}
	public void setSCALE_(int sCALE_) {
		SCALE_ = sCALE_;
	}
	public int getINVERT_WATERMARK_() {
		return INVERT_WATERMARK_;
	}
	public void setINVERT_WATERMARK_(int iNVERT_WATERMARK_) {
		INVERT_WATERMARK_ = iNVERT_WATERMARK_;
	}
	public String getACTUACION_ID_() {
		return ACTUACION_ID_;
	}
	public void setACTUACION_ID_(String aCTUACION_ID_) {
		ACTUACION_ID_ = aCTUACION_ID_;
	}
	public Timestamp getCANCEL_DATE() {
		return CANCEL_DATE;
	}
	public void setCANCEL_DATE(Timestamp cANCEL_DATE) {
		CANCEL_DATE = cANCEL_DATE;
	}
	public String getCANCEL_USER_ID() {
		return CANCEL_USER_ID;
	}
	public void setCANCEL_USER_ID(String cANCEL_USER_ID) {
		CANCEL_USER_ID = cANCEL_USER_ID;
	}
	public String getDOC_ORIGINAL_URI() {
		return DOC_ORIGINAL_URI;
	}
	public void setDOC_ORIGINAL_URI(String dOC_ORIGINAL_URI) {
		DOC_ORIGINAL_URI = dOC_ORIGINAL_URI;
	}
	public int getROTATION_() {
		return ROTATION_;
	}
	public void setROTATION_(int rOTATION_) {
		ROTATION_ = rOTATION_;
	}
	@Override
	public String toString() {
		return "DocumentBean [ADMINISTRATIVE_FILE_ID_=" + ADMINISTRATIVE_FILE_ID_ + ", ID_=" + ID_ + ", ACTIVITY_="
				+ ACTIVITY_ + ", CONTENT_TYPE_=" + CONTENT_TYPE_ + ", HAS_DOCUMENT_=" + HAS_DOCUMENT_
				+ ", CREATION_DATE_=" + CREATION_DATE_ + ", DESCRIPTION_=" + DESCRIPTION_ + ", SIGN_REFERENCE_="
				+ SIGN_REFERENCE_ + ", SIGN_ACTIVITY_=" + SIGN_ACTIVITY_ + ", RECORD_NUMBER_=" + RECORD_NUMBER_
				+ ", RECORD_DATE_=" + RECORD_DATE_ + ", RECORD_TYPE_=" + RECORD_TYPE_ + ", TYPE_=" + TYPE_
				+ ", CURRENT_=" + CURRENT_ + ", REPOSITORY_URI_=" + REPOSITORY_URI_ + ", STATE_=" + STATE_
				+ ", DOCUMENT_TYPE=" + DOCUMENT_TYPE + ", ELABORATION_DATE_=" + ELABORATION_DATE_ + ", SCALE_=" + SCALE_
				+ ", INVERT_WATERMARK_=" + INVERT_WATERMARK_ + ", ACTUACION_ID_=" + ACTUACION_ID_ + ", CANCEL_DATE="
				+ CANCEL_DATE + ", CANCEL_USER_ID=" + CANCEL_USER_ID + ", DOC_ORIGINAL_URI=" + DOC_ORIGINAL_URI
				+ ", ROTATION_=" + ROTATION_ + "]";
	}
}
