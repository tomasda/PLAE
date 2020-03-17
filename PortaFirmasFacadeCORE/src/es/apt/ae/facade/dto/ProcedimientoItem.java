package es.apt.ae.facade.dto;

import java.io.Serializable;

public class ProcedimientoItem extends ElementoItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String familiaId = null;
	private String familiaDescripcion = null;
	
	public ProcedimientoItem() {
		super();
	}
	public ProcedimientoItem(String id, String descripcion, String familiaId) {
		super(id, descripcion);
		this.familiaId = familiaId;
	}
	public ProcedimientoItem(String id, String descripcion, String familiaId, String familiaDescripcion) {
		super(id, descripcion);
		this.familiaId = familiaId;
		this.familiaDescripcion = familiaDescripcion;
	}
	
	public String getFamiliaId() {
		return familiaId;
	}
	public void setFamiliaId(String familiaId) {
		this.familiaId = familiaId;
	}
	public String getFamiliaDescripcion() {
		return familiaDescripcion;
	}
	public void setFamiliaDescripcion(String familiaDescripcion) {
		this.familiaDescripcion = familiaDescripcion;
	}

}
