package es.apt.ae.facade.dto;

import java.util.Date;

public class NotificacionDocumentoInfo {

	public static final int TIPO_COMUNICACION = 2;
	public static final int TIPO_NOTIFICACION = 3;
	
	private int tipo;  // 2: comunicación
	                   // 3 notificación
	private String numIdentificacion;
	private Date fecha;
	
	public NotificacionDocumentoInfo(int tipo, String numIdentificacion, Date fecha) {
		super();
		this.tipo = tipo;
		this.numIdentificacion = numIdentificacion;
		this.fecha = fecha;
	}

	
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getNumIdentificacion() {
		return numIdentificacion;
	}
	public void setNumIdentificacion(String numIdentificacion) {
		this.numIdentificacion = numIdentificacion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
}
