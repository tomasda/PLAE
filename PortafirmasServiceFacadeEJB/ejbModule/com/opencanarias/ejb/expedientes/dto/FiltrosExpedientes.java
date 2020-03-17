package com.opencanarias.ejb.expedientes.dto;

import java.io.Serializable;
import java.util.Date;

public class FiltrosExpedientes implements Serializable {

	protected static final long serialVersionUID = 1L;

    protected String numExpediente = null;
    protected String asuntoExpediente = null;
    protected String interesadoId = null;
    protected String interesadoNombreApellidosORazonSocial = null;
    protected String familiaId = null;
    protected String procedimientoId = null;
    protected String numRegistroEntrada = null;
    protected Date fechaCreacionDesde = null;
    protected Date fechaCreacionHasta = null;    
    protected Date fechaInicioDesde = null;
    protected Date fechaInicioHasta = null;
	
	public FiltrosExpedientes(){

	}
	
	public FiltrosExpedientes(String numExpediente, String asuntoExpediente, String interesadoId, String interesadoNombreApellidosORazonSocial, 
			String familiaId, String procedimientoId, String numRegistroEntrada, Date fechaCreacionDesde, Date fechaCreacionHasta, 
			Date fechaInicioDesde, Date fechaInicioHasta) {
		super();
		this.numExpediente = numExpediente;
		this.asuntoExpediente = asuntoExpediente;
		this.interesadoId = interesadoId;
		this.interesadoNombreApellidosORazonSocial = interesadoNombreApellidosORazonSocial;
		this.familiaId = familiaId;
		this.procedimientoId = procedimientoId;
		this.numRegistroEntrada = numRegistroEntrada;
		this.fechaCreacionDesde = fechaCreacionDesde;
		this.fechaCreacionHasta = fechaCreacionHasta;
		this.fechaInicioDesde = fechaInicioDesde;
		this.fechaInicioHasta = fechaInicioHasta;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getAsuntoExpediente() {
		return asuntoExpediente;
	}

	public void setAsuntoExpediente(String asuntoExpediente) {
		this.asuntoExpediente = asuntoExpediente;
	}

	public String getInteresadoId() {
		return interesadoId;
	}

	public void setInteresadoId(String interesadoId) {
		this.interesadoId = interesadoId;
	}

	public String getInteresadoNombreApellidosORazonSocial() {
		return interesadoNombreApellidosORazonSocial;
	}

	public void setInteresadoNombreApellidosORazonSocial(String interesadoNombreApellidosORazonSocial) {
		this.interesadoNombreApellidosORazonSocial = interesadoNombreApellidosORazonSocial;
	}

	public String getFamiliaId() {
		return familiaId;
	}

	public void setFamiliaId(String familiaId) {
		this.familiaId = familiaId;
	}

	public String getProcedimientoId() {
		return procedimientoId;
	}

	public void setProcedimientoId(String procedimientoId) {
		this.procedimientoId = procedimientoId;
	}
	
	public String getNumRegistroEntrada() {
		return numRegistroEntrada;
	}

	public void setNumRegistroEntrada(String numRegistroEntrada) {
		this.numRegistroEntrada = numRegistroEntrada;
	}

	public Date getFechaCreacionDesde() {
		return fechaCreacionDesde;
	}

	public void setFechaCreacionDesde(Date fechaCreacionDesde) {
		this.fechaCreacionDesde = fechaCreacionDesde;
	}

	public Date getFechaCreacionHasta() {
		return fechaCreacionHasta;
	}

	public void setFechaCreacionHasta(Date fechaCreacionHasta) {
		this.fechaCreacionHasta = fechaCreacionHasta;
	}

	public Date getFechaInicioDesde() {
		return fechaInicioDesde;
	}

	public void setFechaInicioDesde(Date fechaInicioDesde) {
		this.fechaInicioDesde = fechaInicioDesde;
	}

	public Date getFechaInicioHasta() {
		return fechaInicioHasta;
	}

	public void setFechaInicioHasta(Date fechaInicioHasta) {
		this.fechaInicioHasta = fechaInicioHasta;
	}

	
}
