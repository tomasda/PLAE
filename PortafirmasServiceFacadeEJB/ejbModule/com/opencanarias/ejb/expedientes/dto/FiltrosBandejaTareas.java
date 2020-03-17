package com.opencanarias.ejb.expedientes.dto;

import java.util.Date;
import java.util.List;

public class FiltrosBandejaTareas extends FiltrosExpedientes {

	private static final long serialVersionUID = 1L;
	
    public static final Short BANDEJA_MIS_TAREAS 		= 1;
    public static final Short BANDEJA_TAREAS_PENDIENTES = 2;
    public static final Short BANDEJAS_TAREAS_DPTO		= 3;
    
    public static final Short ESTADO_TAREAS_ASIGNADAS 	= 1;
    public static final Short ESTADO_TAREAS_PENDIENTES	= 2;
    
    public static final Short PETICIONES_PENDIENTES_NINGUNA	= 1;
    public static final Short PETICIONES_PENDIENTES_ALGUNA	= 2;
    
	private Short tipoBandeja = null;
	private List<String> listDepartamentos;
	private String instancia = null;
	private String tramiteId = null;
	private String tramiteDesc = null;
	private String tramitadorId = null;
	private Short estadoTarea = null;
	private Short peticionesPendientes = null;
	private Boolean peticionesFinalizadas = null;
	private Date peticionesFinalizadasDesde = null;
	private Date peticionesFinalizadasHasta = null;
	
	public FiltrosBandejaTareas(){

	}
	
	public FiltrosBandejaTareas(Short tipoBandeja, String numExpediente, String asuntoExpediente, 
			String interesadoId, String interesadoNombreApellidosORazonSocial, String familiaId, String procedimientoId,
			List<String> listDepartamentos, String tramiteDesc, String tramitadorId, String numRegistroEntrada, 
			Short estadoTarea, Date fechaCreacionDesde, Date fechaCreacionHasta, Date fechaInicioDesde, Date fechaInicioHasta,
			Short peticionesPendientes,  Boolean peticionesFinalizadas, Date peticionesFinalizadasDesde, Date peticionesFinalizadasHasta) {
		super(numExpediente, asuntoExpediente, interesadoId, interesadoNombreApellidosORazonSocial, familiaId, procedimientoId,
				numRegistroEntrada, fechaCreacionDesde, fechaCreacionHasta, fechaInicioDesde, fechaInicioHasta);
		this.tipoBandeja = tipoBandeja;
		this.listDepartamentos = listDepartamentos;
		this.tramiteDesc = tramiteDesc;
		this.tramitadorId = tramitadorId;
		this.estadoTarea = estadoTarea;
		this.peticionesPendientes = peticionesPendientes;
		this.peticionesFinalizadas = peticionesFinalizadas;
		this.peticionesFinalizadasDesde = peticionesFinalizadasDesde;
		this.peticionesFinalizadasHasta = peticionesFinalizadasHasta;
	}
	
	public FiltrosBandejaTareas(Short tipoBandeja, List<String> listDepartamentos, String instancia, String tramiteId, String tramitadorId) {
		this.tipoBandeja = tipoBandeja;
		this.listDepartamentos = listDepartamentos;
		this.instancia = instancia;
		this.tramiteId = tramiteId;
		this.tramitadorId = tramitadorId;
	}

	public Short getTipoBandeja() {
		return tipoBandeja;
	}

	public void setTipoBandeja(Short tipoBandeja) {
		this.tipoBandeja = tipoBandeja;
	}

	public List<String> getListDepartamentos() {
		return listDepartamentos;
	}

	public void setListDepartamentos(List<String> listDepartamentos) {
		this.listDepartamentos = listDepartamentos;
	}
	
	public String getInstancia() {
		return instancia;
	}

	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}

	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}

	public String getTramiteDesc() {
		return tramiteDesc;
	}

	public void setTramiteDesc(String tramiteDesc) {
		this.tramiteDesc = tramiteDesc;
	}

	public String getTramitadorId() {
		return tramitadorId;
	}

	public void setTramitadorId(String tramitadorId) {
		this.tramitadorId = tramitadorId;
	}

	public Short getEstadoTarea() {
		return estadoTarea;
	}

	public void setEstadoTarea(Short estadoTarea) {
		this.estadoTarea = estadoTarea;
	}

	public Short getPeticionesPendientes() {
		return peticionesPendientes;
	}

	public void setPeticionesPendientes(Short peticionesPendientes) {
		this.peticionesPendientes = peticionesPendientes;
	}

	public Boolean getPeticionesFinalizadas() {
		return peticionesFinalizadas;
	}

	public void setPeticionesFinalizadas(Boolean peticionesFinalizadas) {
		this.peticionesFinalizadas = peticionesFinalizadas;
	}

	public Date getPeticionesFinalizadasDesde() {
		return peticionesFinalizadasDesde;
	}

	public void setPeticionesFinalizadasDesde(Date peticionesFinalizadasDesde) {
		this.peticionesFinalizadasDesde = peticionesFinalizadasDesde;
	}

	public Date getPeticionesFinalizadasHasta() {
		return peticionesFinalizadasHasta;
	}

	public void setPeticionesFinalizadasHasta(Date peticionesFinalizadasHasta) {
		this.peticionesFinalizadasHasta = peticionesFinalizadasHasta;
	}

}
