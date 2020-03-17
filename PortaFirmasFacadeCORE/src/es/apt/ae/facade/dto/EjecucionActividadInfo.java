package es.apt.ae.facade.dto;

import java.io.Serializable;


public class EjecucionActividadInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String instancia;
	private boolean actividadFinalizada;
	private String mensaje;
		
	public EjecucionActividadInfo(String instancia, boolean actividadFinalizada) {
		super();
		this.instancia = instancia;
		this.actividadFinalizada = actividadFinalizada;
	}
	
	public EjecucionActividadInfo(String instancia, boolean actividadFinalizada, String mensaje) {
		super();
		this.instancia = instancia;
		this.actividadFinalizada = actividadFinalizada;
		this.mensaje = mensaje;
	}

	public String getInstancia() {
		return instancia;
	}
	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}
	public boolean isActividadFinalizada() {
		return actividadFinalizada;
	}
	public void setActividadFinalizada(boolean actividadFinalizada) {
		this.actividadFinalizada = actividadFinalizada;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
