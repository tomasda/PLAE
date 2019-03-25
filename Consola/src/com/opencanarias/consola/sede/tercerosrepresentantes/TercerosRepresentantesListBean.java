package com.opencanarias.consola.sede.tercerosrepresentantes;

import java.io.Serializable;
import java.util.List;

public class TercerosRepresentantesListBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int terceroID;
	private String terceroName;
	private String terceroIdentification;
	private List<RepresentantesBean> repList;
	
	
	public TercerosRepresentantesListBean() {}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTerceroID() {
		return terceroID;
	}
	public void setTerceroID(int terceroID) {
		this.terceroID = terceroID;
	}
	public String getTerceroName() {
		return terceroName;
	}
	public void setTerceroName(String terceroName) {
		this.terceroName = terceroName;
	}
	public List<RepresentantesBean> getRepList() {
		return repList;
	}
	public void setRepList(List<RepresentantesBean> repList2) {
		this.repList = repList2;
	}
	public String getTerceroIdentification() {
		return terceroIdentification;
	}
	public void setTerceroIdentification(String terceroIdentification) {
		this.terceroIdentification = terceroIdentification;
	}
	
	

}
