package es.apt.ae.facade.dto;

import java.io.Serializable;

public class TipoActuacionItem extends ElementoItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String procesoId = null;

	
	public TipoActuacionItem() {
		super();
	}
	public TipoActuacionItem(String id, String descripcion, String procesoId) {
		super(id, descripcion);
		this.procesoId = procesoId;
	}

	
	public String getProcesoId() {
		return procesoId;
	}
	public void setProcesoId(String procesoId) {
		this.procesoId = procesoId;
	}
	
}
