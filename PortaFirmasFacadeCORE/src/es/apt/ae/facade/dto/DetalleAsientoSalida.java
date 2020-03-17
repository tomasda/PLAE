package es.apt.ae.facade.dto;

import java.io.Serializable;
import java.util.Date;

public class DetalleAsientoSalida implements Serializable {

	private static final long serialVersionUID = 1L;

    private String identificacion;
    private String asunto;
    private Date fechaCreacion;
    private String departamentoId;
    private String destinatarioIdentificacion;
    private String destinatarioNombreCompleto;
    private String destinatarioCanalNotificacion;
	
    
    public DetalleAsientoSalida(String identificacion, String asunto, Date fechaCreacion, String departamentoId,
			String destinatarioIdentificacion,  String destinatarioNombreCompleto, String destinatarioCanalNotificacion) {
		super();
		this.identificacion = identificacion;
		this.asunto = asunto;
		this.fechaCreacion = fechaCreacion;
		this.departamentoId = departamentoId;
		this.destinatarioIdentificacion = destinatarioIdentificacion;
		this.destinatarioNombreCompleto = destinatarioNombreCompleto;
		this.destinatarioCanalNotificacion = destinatarioCanalNotificacion;
	}

    
    public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getDepartamentoId() {
		return departamentoId;
	}
	public void setDepartamentoId(String departamentoId) {
		this.departamentoId = departamentoId;
	}
	public String getDestinatarioIdentificacion() {
		return destinatarioIdentificacion;
	}
	public void setDestinatarioIdentificacion(String destinatarioIdentificacion) {
		this.destinatarioIdentificacion = destinatarioIdentificacion;
	}
	public String getDestinatarioNombreCompleto() {
		return destinatarioNombreCompleto;
	}
	public void setDestinatarioNombreCompleto(String destinatarioNombreCompleto) {
		this.destinatarioNombreCompleto = destinatarioNombreCompleto;
	}
	public String getDestinatarioCanalNotificacion() {
		return destinatarioCanalNotificacion;
	}
	public void setDestinatarioCanalNotificacion(String destinatarioCanalNotificacion) {
		this.destinatarioCanalNotificacion = destinatarioCanalNotificacion;
	}
    
}
