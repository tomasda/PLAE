package com.opencanarias.ejb.expedientes.dto;

import java.io.Serializable;
import java.util.Date;

public class ExpedienteSede implements Serializable{

	private static final long serialVersionUID = 3465470223704414069L;
	private String asunto;
	private String fase;
	private String numeroExpediente;
	private String procedimiento;
	private Date fechaInicio;
	private Date fechaFinalizacion;
	private Integer requerimientosTotales;
	private Integer requerimientosContestados;
	
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getFase() {
		return fase;
	}
	public void setFase(String fase) {
		this.fase = fase;
	}
	public String getNumeroExpediente() {
		return numeroExpediente;
	}
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}
	public String getProcedimiento() {
		return procedimiento;
	}
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}
	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}
	public Integer getRequerimientosTotales() {
		return requerimientosTotales;
	}
	public void setRequerimientosTotales(Integer requerimientosTotales) {
		this.requerimientosTotales = requerimientosTotales;
	}
	public Integer getRequerimientosContestados() {
		return requerimientosContestados;
	}
	public void setRequerimientosContestados(Integer requerimientosContestados) {
		this.requerimientosContestados = requerimientosContestados;
	}
	
}
