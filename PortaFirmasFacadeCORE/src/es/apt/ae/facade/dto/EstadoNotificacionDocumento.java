package es.apt.ae.facade.dto;

import java.io.Serializable;
import java.util.Date;

public class EstadoNotificacionDocumento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Short estadoId;
	private String estado;
	private Date fecha;
	private String medio;
	private Boolean firmaManuscritaDigital;
	
	public EstadoNotificacionDocumento(Short estadoId, String estado, Date fecha, String medio) {
		super();
		this.estadoId = estadoId;
		this.estado = estado;
		this.fecha = fecha;
		this.medio = medio;
	}
	
	public EstadoNotificacionDocumento(Short estadoId, String estado, Date fecha, String medio, Boolean firmaManuscritaDigital) {
		super();
		this.estadoId = estadoId;
		this.estado = estado;
		this.fecha = fecha;
		this.medio = medio;
		this.firmaManuscritaDigital = firmaManuscritaDigital;
	}
	
	public EstadoNotificacionDocumento(String estado, Date fecha) {
		super();
		this.estado = estado;
		this.fecha = fecha;
	}	

	
	public Short getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(Short estadoId) {
		this.estadoId = estadoId;
	}

	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMedio() {
		return medio;
	}

	public void setMedio(String medio) {
		this.medio = medio;
	}

	public Boolean getFirmaManuscritaDigital() {
		return firmaManuscritaDigital;
	}

	public void setFirmaManuscritaDigital(Boolean firmaManuscritaDigital) {
		this.firmaManuscritaDigital = firmaManuscritaDigital;
	}

}
