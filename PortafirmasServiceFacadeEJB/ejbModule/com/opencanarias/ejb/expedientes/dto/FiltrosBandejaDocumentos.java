package com.opencanarias.ejb.expedientes.dto;

import java.util.Date;
import java.util.List;

public class FiltrosBandejaDocumentos extends FiltrosExpedientes {

	private static final long serialVersionUID = 1L;
	
    public static final Short ESTADO_DOC_SIN_FIRMA = 0;
    public static final Short ESTADO_DOC_FIRMADO = 1;
    public static final Short ESTADO_DOC__COMPULSADO = 2;
    public static final Short ESTADO_DOC_PENDIENTE_PORTAFIRMAS = 3;
    public static final Short ESTADO_DOC_FIRMADO_PORTAFIRMAS = 4;
    public static final Short ESTADO_DOC_RECHAZADO_PORTAFIRMAS = 5;
    
    public static final Short ESTADO_DOC_SIN_REGISTRO = 0;
    public static final Short ESTADO_DOC_CON_REGISTRO_SALIDA = 1;
    public static final Short ESTADO_DOC_CON_REGISTRO_ENTRADA = 2;
    
    public static final Short ESTADO_DOC_SIN_NOTIFICACION = 0;
    public static final Short ESTADO_DOC_COMUNICADO = 1;
    public static final Short ESTADO_DOC_ENVIADO_NOTIFICAR = 2;
    public static final Short ESTADO_DOC_NOTIFICADO = 3;
    
	
	private String tramitadorId = null;
	private List<String> listDepartamentos;
	private String tipoDocId;
	private Date fechaElaboracionDocDesde;
	private Date fechaElaboracionDocHasta;
	private Short estadoFirmaDoc;
	private Short estadoRegistroDoc;
	private String numRegistroDoc;
	private Short estadoNotificacionDoc;
	
	
	public FiltrosBandejaDocumentos(){

	}
	
	public FiltrosBandejaDocumentos(String numExpediente, String asuntoExpediente, String interesadoId, String interesadoNombreApellidosORazonSocial, 
			String familiaId, String procedimientoId, String numRegistroEntrada, Date fechaCreacionDesde, Date fechaCreacionHasta, 
			Date fechaInicioDesde, Date fechaInicioHasta, List<String> listDepartamentos, String tramitadorId, String tipoDocId, 
			Date fechaElaboracionDocDesde, Date fechaElaboracionDocHasta, Short estadoFirmaDoc, Short estadoRegistroDoc, 
			String numRegistroDoc, Short estadoNotificacionDoc) {
		super(numExpediente, asuntoExpediente, interesadoId, interesadoNombreApellidosORazonSocial, familiaId, procedimientoId,
				numRegistroEntrada, fechaCreacionDesde, fechaCreacionHasta, fechaInicioDesde, fechaInicioHasta);
		this.tramitadorId = tramitadorId;
		this.listDepartamentos = listDepartamentos;
		this.tipoDocId = tipoDocId;
		this.fechaElaboracionDocDesde = fechaElaboracionDocDesde;
		this.fechaElaboracionDocHasta = fechaElaboracionDocHasta;
		this.estadoFirmaDoc = estadoFirmaDoc;
		this.estadoRegistroDoc = estadoRegistroDoc;
		this.numRegistroDoc = numRegistroDoc;
		this.estadoNotificacionDoc = estadoNotificacionDoc;
	}
	

	public String getTramitadorId() {
		return tramitadorId;
	}

	public void setTramitadorId(String tramitadorId) {
		this.tramitadorId = tramitadorId;
	}

	public List<String> getListDepartamentos() {
		return listDepartamentos;
	}

	public void setListDepartamentos(List<String> listDepartamentos) {
		this.listDepartamentos = listDepartamentos;
	}

	public String getTipoDocId() {
		return tipoDocId;
	}

	public void setTipoDocId(String tipoDocId) {
		this.tipoDocId = tipoDocId;
	}

	public Date getFechaElaboracionDocDesde() {
		return fechaElaboracionDocDesde;
	}

	public void setFechaElaboracionDocDesde(Date fechaElaboracionDocDesde) {
		this.fechaElaboracionDocDesde = fechaElaboracionDocDesde;
	}

	public Date getFechaElaboracionDocHasta() {
		return fechaElaboracionDocHasta;
	}

	public void setFechaElaboracionDocHasta(Date fechaElaboracionDocHasta) {
		this.fechaElaboracionDocHasta = fechaElaboracionDocHasta;
	}

	public Short getEstadoFirmaDoc() {
		return estadoFirmaDoc;
	}

	public void setEstadoFirmaDoc(Short estadoFirmaDoc) {
		this.estadoFirmaDoc = estadoFirmaDoc;
	}

	public Short getEstadoRegistroDoc() {
		return estadoRegistroDoc;
	}

	public void setEstadoRegistroDoc(Short estadoRegistroDoc) {
		this.estadoRegistroDoc = estadoRegistroDoc;
	}

	public String getNumRegistroDoc() {
		return numRegistroDoc;
	}

	public void setNumRegistroDoc(String numRegistroDoc) {
		this.numRegistroDoc = numRegistroDoc;
	}

	public Short getEstadoNotificacionDoc() {
		return estadoNotificacionDoc;
	}

	public void setEstadoNotificacionDoc(Short estadoNotificacionDoc) {
		this.estadoNotificacionDoc = estadoNotificacionDoc;
	}
	
}
