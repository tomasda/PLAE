package com.opencanarias.consola.gestorExpedientes.catalogoDepartamentos;

import java.io.Serializable;

public class DepartamentosBean implements Serializable {
	//TABLA ROLE_
	private static final long serialVersionUID = 1L;
	String ID_; 				//	ID_	VARCHAR2(64 BYTE)
	String DESCRIPTION_;	//	DESCRIPTION_	VARCHAR2(127 BYTE)
	String ALF_GROUP_;		//	ALF_GROUP_	VARCHAR2(127 CHAR)
	int DECRETABLE_; 		//DECRETABLE_	NUMBER(1,0)
	int HABILITADO_;		//HABILITADO_	NUMBER(1,0)
	int DECRETABLE_REGISTRO_; //DECRETABLE_REGISTRO_	NUMBER(1,0)
	
	public DepartamentosBean(){
		
	}

	public DepartamentosBean(String iD_, String dESCRIPTION_, String aLF_GROUP_, int dECRETABLE_, int hABILITADO_,
			int dECRETABLE_REGISTRO_) {
		super();
		ID_ = iD_;
		DESCRIPTION_ = dESCRIPTION_;
		ALF_GROUP_ = aLF_GROUP_;
		DECRETABLE_ = dECRETABLE_;
		HABILITADO_ = hABILITADO_;
		DECRETABLE_REGISTRO_ = dECRETABLE_REGISTRO_;
	}

	public String getID_() {
		return ID_;
	}

	public void setID_(String iD_) {
		ID_ = iD_;
	}

	public String getDESCRIPTION_() {
		return DESCRIPTION_;
	}

	public void setDESCRIPTION_(String dESCRIPTION_) {
		DESCRIPTION_ = dESCRIPTION_;
	}

	public String getALF_GROUP_() {
		return ALF_GROUP_;
	}

	public void setALF_GROUP_(String aLF_GROUP_) {
		ALF_GROUP_ = aLF_GROUP_;
	}

	public int getDECRETABLE_() {
		return DECRETABLE_;
	}

	public void setDECRETABLE_(int dECRETABLE_) {
		DECRETABLE_ = dECRETABLE_;
	}

	public int getHABILITADO_() {
		return HABILITADO_;
	}

	public void setHABILITADO_(int hABILITADO_) {
		HABILITADO_ = hABILITADO_;
	}

	public int getDECRETABLE_REGISTRO_() {
		return DECRETABLE_REGISTRO_;
	}

	public void setDECRETABLE_REGISTRO_(int dECRETABLE_REGISTRO_) {
		DECRETABLE_REGISTRO_ = dECRETABLE_REGISTRO_;
	}

	@Override
	public String toString() {
		return "DepartamentosBean [ID_=" + ID_ + ", DESCRIPTION_=" + DESCRIPTION_ + ", ALF_GROUP_=" + ALF_GROUP_
				+ ", DECRETABLE_=" + DECRETABLE_ + ", HABILITADO_=" + HABILITADO_ + ", DECRETABLE_REGISTRO_="
				+ DECRETABLE_REGISTRO_ + "]";
	}
	
}
