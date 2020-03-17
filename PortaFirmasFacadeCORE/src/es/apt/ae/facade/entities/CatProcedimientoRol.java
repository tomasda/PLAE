package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = "CatProcedimientoRol.findByProcedimientoId", query = "SELECT cpr FROM CatProcedimientoRol cpr WHERE cpr.procedimiento.id = :procId"),
	@NamedQuery(name = "CatProcedimientoRol.findByDepartamentosIds", query = "SELECT cpr FROM CatProcedimientoRol cpr WHERE cpr.departamentoId IN (:departamentosIds) ORDER BY cpr.procedimiento.id ASC"),
	@NamedQuery(name = "CatProcedimientoRol.findByDepartamentosIdsAndFamiliaId", query = "SELECT cpr FROM CatProcedimientoRol cpr WHERE cpr.departamentoId IN (:departamentosIds) AND " + 
			" ((cpr.familiaId IS NULL AND cpr.procedimiento IS NULL) OR cpr.familiaId = :familiaId OR cpr.procedimiento.id LIKE :procFamiliaId) ORDER BY cpr.procedimiento.id ASC")
})
@Table(schema="OC3F", name = "CAT_PROCEDIMIENTOS_ROLES")
public class CatProcedimientoRol implements Serializable {
	
	private static final long serialVersionUID = 8583673722566645895L;
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "DEPARTAMENTO_ID")
	private String departamentoId;

	@Column(name = "FAMILIA_ID")
	private String familiaId;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "PROCEDIMIENTO_ID")
	private Procedimiento procedimiento;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartamentoId() {
		return departamentoId;
	}

	public void setDepartamentoId(String departamentoId) {
		this.departamentoId = departamentoId;
	}

	public String getFamiliaId() {
		return familiaId;
	}

	public void setFamiliaId(String familiaId) {
		this.familiaId = familiaId;
	}

	public Procedimiento getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(Procedimiento procedimiento) {
		this.procedimiento = procedimiento;
	}

}
