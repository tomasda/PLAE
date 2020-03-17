package com.opencanarias.ejb.expedientes.dto;

import java.util.Date;
import java.util.List;

import es.apt.ae.facade.dto.DepartamentoItem;

public class FiltrosConsulta extends FiltrosExpedientes {

	private static final long serialVersionUID = 1L;

	public static final Short ESTADO_EXPEDIENTE_ACTIVO 	= 0;
    public static final Short ESTADO_EXPEDIENTE_FINALIZADO	= 1;
    public static final Short ESTADO_EXPEDIENTE_CANCELADO	= 2;
    
    private String usuarioSolicitante = null;
    private Date fechaFinDesde = null;
    private Date fechaFinHasta = null;
    private List<DepartamentoItem> departamentosAutorizados = null;
    private List<Short> estadosExpedientes = null;
    
	public FiltrosConsulta(){

	}
	
	public FiltrosConsulta(String usuarioSolicitante, String numExpediente, String asuntoExpediente, String interesadoId, 
			String interesadoNombreApellidosORazonSocial, String familiaId, String procedimientoId, String numRegistroEntrada,
			Date fechaCreacionDesde, Date fechaCreacionHasta, Date fechaInicioDesde, Date fechaInicioHasta, 
			Date fechaFinDesde, Date fechaFinHasta, List<DepartamentoItem> departamentosAutorizados, List<Short> estadosExpedientes) {
		super(numExpediente, asuntoExpediente, interesadoId, interesadoNombreApellidosORazonSocial, familiaId, procedimientoId,
				numRegistroEntrada, fechaCreacionDesde, fechaCreacionHasta, fechaInicioDesde, fechaInicioHasta);
		this.usuarioSolicitante = usuarioSolicitante;
		this.fechaFinDesde = fechaFinDesde;
		this.fechaFinHasta = fechaFinHasta;
		this.departamentosAutorizados = departamentosAutorizados;
		this.estadosExpedientes = estadosExpedientes;
	}
	
	
	public String getUsuarioSolicitante() {
		return usuarioSolicitante;
	}

	public void setUsuarioSolicitante(String usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}
	
	public Date getFechaFinDesde() {
		return fechaFinDesde;
	}

	public void setFechaFinDesde(Date fechaFinDesde) {
		this.fechaFinDesde = fechaFinDesde;
	}

	public Date getFechaFinHasta() {
		return fechaFinHasta;
	}

	public void setFechaFinHasta(Date fechaFinHasta) {
		this.fechaFinHasta = fechaFinHasta;
	}

	public List<DepartamentoItem> getDepartamentosAutorizados() {
		return departamentosAutorizados;
	}

	public void setDepartamentosAutorizados(List<DepartamentoItem> departamentosAutorizados) {
		this.departamentosAutorizados = departamentosAutorizados;
	}

	public List<Short> getEstadosExpedientes() {
		return estadosExpedientes;
	}

	public void setEstadosExpedientes(List<Short> estadosExpedientes) {
		this.estadosExpedientes = estadosExpedientes;
	}

}
