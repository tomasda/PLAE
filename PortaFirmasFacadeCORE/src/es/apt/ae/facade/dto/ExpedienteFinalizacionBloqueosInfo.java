package es.apt.ae.facade.dto;

import java.io.Serializable;

public class ExpedienteFinalizacionBloqueosInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean bloqueoPortafirmas;
	private boolean bloqueoActuaciones;
	private boolean bloqueoRequerimientos;
	private boolean finalizacion;
	
	
	public ExpedienteFinalizacionBloqueosInfo() {
		this.bloqueoPortafirmas = false;
		this.bloqueoPortafirmas = false;
		this.bloqueoRequerimientos = false;
		this.finalizacion = false;
	}

	public ExpedienteFinalizacionBloqueosInfo(boolean bloqueoPortafirmas, boolean bloqueoActuaciones,
			boolean bloqueoRequerimientos, boolean finalizacion) {
		this.bloqueoPortafirmas = bloqueoPortafirmas;
		this.bloqueoActuaciones = bloqueoActuaciones;
		this.bloqueoRequerimientos = bloqueoRequerimientos;
		this.finalizacion = finalizacion;
	}
	
	
	public boolean isBloqueoPortafirmas() {
		return bloqueoPortafirmas;
	}
	public void setBloqueoPortafirmas(boolean bloqueoPortafirmas) {
		this.bloqueoPortafirmas = bloqueoPortafirmas;
	}
	public boolean isBloqueoActuaciones() {
		return bloqueoActuaciones;
	}
	public void setBloqueoActuaciones(boolean bloqueoActuaciones) {
		this.bloqueoActuaciones = bloqueoActuaciones;
	}
	public boolean isBloqueoRequerimientos() {
		return bloqueoRequerimientos;
	}
	public void setBloqueoRequerimientos(boolean bloqueoRequerimientos) {
		this.bloqueoRequerimientos = bloqueoRequerimientos;
	}
	public boolean isFinalizacion() {
		return finalizacion;
	}
	public void setFinalizacion(boolean finalizacion) {
		this.finalizacion = finalizacion;
	}
	
	public boolean isBloqueo() {
		return (this.bloqueoActuaciones || this.bloqueoPortafirmas || this.bloqueoRequerimientos);
	}
	
}
