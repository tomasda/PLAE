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
	@NamedQuery(name = "CatProcedimientoCircuito.findByProcedimientoActividadDepartamentoIds", query = "SELECT cpc FROM CatProcedimientoCircuito cpc WHERE "
			+ "cpc.procedimientoId =:procedimientoId  and " 
			+ "(cpc.actividadId =:actividadId or cpc.actividadId is null) and "
			+ "(cpc.departamentoId =:departamentoId or cpc.departamentoId is null)"),
	@NamedQuery(name = "CatProcedimientoCircuito.findAll", query = "SELECT cpc FROM CatProcedimientoCircuito cpc"),
})
@Table(schema="OC3F", name = "CAT_PROCEDIMIENTOS_CIRCUITOS")
public class CatProcedimientoCircuito implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "PROC_ID")
	private String procedimientoId;
	
	@Column(name = "ACTIVIDAD_ID")
	private String actividadId;
	
	@Column(name = "DEPARTAMENTO_ID")
	private String departamentoId;
	
	@Column(name = "CIRCUITO_ID")
	private String circuitoId;
	
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
	public String getActividadId() {
		return actividadId;
	}
	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}
	public String getDepartamentoId() {
		return departamentoId;
	}
	public void setDepartamentoId(String departamentoId) {
		this.departamentoId = departamentoId;
	}
	public String getCircuitoId() {
		return circuitoId;
	}
	public void setCircuitoId(String circuitoId) {
		this.circuitoId = circuitoId;
	}
	
}
