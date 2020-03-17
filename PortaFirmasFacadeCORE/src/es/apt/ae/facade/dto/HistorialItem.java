package es.apt.ae.facade.dto;

import java.io.Serializable;
import java.util.Date;

import com.opencanarias.utils.DateUtils;


public class HistorialItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String ESTADO_SIN_INICIAR = "Sin iniciar";
	private static final String ESTADO_EN_TRAMITE = "En trámite";
	private static final String ESTADO_COMPLETADO = "Completado";

	private String tramiteId;
	private String tramiteDescripcion;
	private String tramitadorId;
	private String tramitadorNombreApellidos;	
	private String asignadorId;
	private String asignadorNombreApellidos;
	private String departamentoId;
	private String departamentoDescripcion;
	private Date fechaCreacion;	
	private Date fechaInicio;
	private Date fechaFin;
	private String estado;

	
	public HistorialItem() {
	}
	
	public HistorialItem(String tramiteId, String tramiteDescripcion, String tramitadorId,
			String tramitadorNombreApellidos, String asignadorId, String asignadorNombreApellidos,
			String departamentoId, String departamentoDescripcion, Date fechaCreacion, Date fechaInicio, Date fechaFin) {
		this.tramiteId = tramiteId;
		this.tramiteDescripcion = tramiteDescripcion;
		this.tramitadorId = tramitadorId;
		this.tramitadorNombreApellidos = tramitadorNombreApellidos;
		this.asignadorId = asignadorId;
		this.asignadorNombreApellidos = asignadorNombreApellidos;
		this.departamentoId = departamentoId;
		this.departamentoDescripcion = departamentoDescripcion;
		this.fechaCreacion = fechaCreacion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		calcularEstado();
	}


	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}

	public String getTramiteDescripcion() {
		return tramiteDescripcion;
	}

	public void setTramiteDescripcion(String tramiteDescripcion) {
		this.tramiteDescripcion = tramiteDescripcion;
	}

	public String getTramitadorId() {
		return tramitadorId;
	}

	public void setTramitadorId(String tramitadorId) {
		this.tramitadorId = tramitadorId;
	}

	public String getTramitadorNombreApellidos() {
		return tramitadorNombreApellidos;
	}

	public void setTramitadorNombreApellidos(String tramitadorNombreApellidos) {
		this.tramitadorNombreApellidos = tramitadorNombreApellidos;
	}

	public String getAsignadorId() {
		return asignadorId;
	}

	public void setAsignadorId(String asignadorId) {
		this.asignadorId = asignadorId;
	}

	public String getAsignadorNombreApellidos() {
		return asignadorNombreApellidos;
	}

	public void setAsignadorNombreApellidos(String asignadorNombreApellidos) {
		this.asignadorNombreApellidos = asignadorNombreApellidos;
	}

	public String getDepartamentoId() {
		return departamentoId;
	}

	public void setDepartamentoId(String departamentoId) {
		this.departamentoId = departamentoId;
	}

	public String getDepartamentoDescripcion() {
		return departamentoDescripcion;
	}

	public void setDepartamentoDescripcion(String departamentoDescripcion) {
		this.departamentoDescripcion = departamentoDescripcion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public String getFechaCreacionStr() {
		if (fechaCreacion != null) {
			return DateUtils.getDate(fechaCreacion, DateUtils.dateTimeFormat);
		}
		return null;
	}	

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
		calcularEstado();
	}
	
	public String getFechaInicioStr() {
		if (fechaInicio != null) {
			return DateUtils.getDate(fechaInicio, DateUtils.dateTimeFormat);
		}
		return null;
	}
	
	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
		calcularEstado();
	}
	
	public String getFechaFinStr() {
		if (fechaFin != null) {
			return DateUtils.getDate(fechaFin, DateUtils.dateTimeFormat);
		}
		return null;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getInfoAsignacionIcono() {
		return "infoAsignacion";
	}
	
	public String getInfoAsignacion() {
		if (asignadorNombreApellidos != null && fechaCreacion != null) {
			return "Asignado por " + asignadorNombreApellidos + " en fecha " + DateUtils.getDate(fechaCreacion, DateUtils.dateTimeFormat);
		}
		return "";
	}
	
	private void calcularEstado() {
		if (this.fechaInicio == null && this.fechaFin == null) {
			this.estado = ESTADO_SIN_INICIAR;
		} else if (fechaInicio != null && fechaFin == null) {
			this.estado = ESTADO_EN_TRAMITE;
		} else if (fechaInicio != null && fechaFin != null) {
			this.estado = ESTADO_COMPLETADO;
		}
	}
}
