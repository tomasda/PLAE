package com.opencanarias.consola.sede.tercerosrepresentantes;

import java.io.Serializable;
import java.sql.Timestamp;

public class ThirdPartyReportBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * select THI.ID, THI.COMMON_NAME_,THI.NIF_, THI.CIF_,THI.NOTIFICATION_TYPE_,ADM.START_DATE_
		from THIRD_PARTY_ THI
		join ADMINISTRATIVE_FILE_ ADM on ADM.ID_ = THI.ADMINISTRATIVE_FILE_ID_
		order by ADM.START_DATE_
		;
	 */
	private int id_;
	private String commonName_;
	private String NIF_;
	private String CIF_;
	private String notificationTipe;
	private Timestamp startDate;
	
	public int getId_() {
		return id_;
	}
	public void setId_(int id_) {
		this.id_ = id_;
	}
	public String getCommonName_() {
		return commonName_;
	}
	public void setCommonName_(String commonName_) {
		this.commonName_ = commonName_;
	}
	public String getNIF_() {
		return NIF_;
	}
	public void setNIF_(String nIF_) {
		NIF_ = nIF_;
	}
	public String getCIF_() {
		return CIF_;
	}
	public void setCIF_(String cIF_) {
		CIF_ = cIF_;
	}
	public String getNotificationTipe() {
		return notificationTipe;
	}
	public void setNotificationTipe(String notificationTipe) {
		this.notificationTipe = notificationTipe;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	
	

}
