package com.opencanarias.consola.sede.tercerosrepresentantes;

import java.io.Serializable;

public class RepresentantesBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id_;
	private String name_;
	private int regisrto_;
	private String identificacion_;
	public RepresentantesBean() {}
	public RepresentantesBean(int id_, String name_,int registro_) {
		super();
		this.id_ = id_;
		this.name_ = name_;
		this.regisrto_ = registro_;
	}
	public int getId_() {
		return id_;
	}
	public void setId_(int id_) {
		this.id_ = id_;
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public int getRegisrto_() {
		return regisrto_;
	}
	public void setRegisrto_(int regisrto_) {
		this.regisrto_ = regisrto_;
	}
	public String getIdentificacion_() {
		return identificacion_;
	}
	public void setIdentificacion_(String identificacion_) {
		this.identificacion_ = identificacion_;
	}
	
}
