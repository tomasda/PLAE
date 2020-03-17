package com.opencanarias.ejb.expedientes.dto;

import java.io.Serializable;

public class ContadorRequerimientosSede implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer resueltos;
	private Integer totales;
	
	public Integer getResueltos() {
		return resueltos;
	}
	public void setResueltos(Integer resueltos) {
		this.resueltos = resueltos;
	}
	public Integer getTotales() {
		return totales;
	}
	public void setTotales(Integer totales) {
		this.totales = totales;
	}
	
}
