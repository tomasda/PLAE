package com.opencanarias.consola.informes;

import java.io.Serializable;
import java.sql.Timestamp;

public class OldExpBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String department;
	private Timestamp startDate;
	private String expNumber;
	private String matter; //asunto
	private String interested;
	private String activiti;
	private String handler; // tramitador.
	private String creator;
	
	
	public OldExpBean(String department, Timestamp startDate, String expNumber, String matter, String interested,
			String activiti, String handler, String creator) {
		super();
		this.department = department;
		this.startDate = startDate;
		this.expNumber = expNumber;
		this.matter = matter;
		this.interested = interested;
		this.activiti = activiti;
		this.handler = handler;
		this.creator = creator;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public String getExpNumber() {
		return expNumber;
	}
	public void setExpNumber(String expNumber) {
		this.expNumber = expNumber;
	}
	public String getMatter() {
		return matter;
	}
	public void setMatter(String matter) {
		this.matter = matter;
	}
	public String getInterested() {
		return interested;
	}
	public void setInterested(String interested) {
		this.interested = interested;
	}
	public String getActiviti() {
		return activiti;
	}
	public void setActiviti(String activiti) {
		this.activiti = activiti;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Override
	public String toString() {
		return "OldExpListBean [department=" + department + ", startDate=" + startDate + ", expNumber=" + expNumber
				+ ", matter=" + matter + ", interested=" + interested + ", activiti=" + activiti + ", handler="
				+ handler + ", creator=" + creator + "]";
	}

}
