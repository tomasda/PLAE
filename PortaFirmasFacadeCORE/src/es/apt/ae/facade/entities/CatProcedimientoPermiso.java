package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = "CatProcedimientoPermiso.findByProcedimientoId", query = "SELECT cpp FROM CatProcedimientoPermiso cpp WHERE cpp.procedimientoId = :procId"),
	@NamedQuery(name = "CatProcedimientoPermiso.findByProcedimientoIdAndIniciador", query = "SELECT cpp FROM CatProcedimientoPermiso cpp WHERE cpp.procedimientoId = :procId AND (cpp.dptIniciadorId IS NULL OR cpp.dptIniciadorId = :dptIniciador)")
})
@Table(schema="OC3F", name = "WORKFLOW_PERMISSION")
public class CatProcedimientoPermiso implements Serializable {
	
	private static final long serialVersionUID = 8583673722566645895L;
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "PROC_ID")
	private String procedimientoId;

	@Column(name = "STARTER_ROLE_ID")
	private String dptIniciadorId;	

	@Column(name = "ROLE_ID")
	private String dptId;	
	
	@Column(name = "PERMISSION")
	private Short tipoPermiso;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcedimientoId() {
		return procedimientoId;
	}
	public void setProcedimientoId(String procedimientoId) {
		this.procedimientoId = procedimientoId;
	}
	public String getDptIniciadorId() {
		return dptIniciadorId;
	}
	public void setDptIniciadorId(String dptIniciadorNombre) {
		this.dptIniciadorId = dptIniciadorNombre;
	}
	public String getDptId() {
		return dptId;
	}
	public void setDptId(String dptNombre) {
		this.dptId = dptNombre;
	}
	public Short getTipoPermiso() {
		return tipoPermiso;
	}
	public void setTipoPermiso(Short tipoPermiso) {
		this.tipoPermiso = tipoPermiso;
	}
	
}
