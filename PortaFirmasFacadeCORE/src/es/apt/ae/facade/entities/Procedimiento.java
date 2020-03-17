package es.apt.ae.facade.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@NamedQueries({
	@NamedQuery(name = Procedimiento.findById, query = "SELECT p FROM Procedimiento p WHERE p.id = :id"),
	@NamedQuery(name = Procedimiento.findByFamilia, query = "SELECT p FROM Procedimiento p WHERE p.familia.id = :id"),
	@NamedQuery(name = Procedimiento.findAll, query = "SELECT p FROM Procedimiento p"),
	@NamedQuery(name = Procedimiento.findActivoRegistroById, query = "SELECT p FROM Procedimiento p WHERE p.id = :id and (p.activo = 1 or p.activo = 2)"),
	@NamedQuery(name = Procedimiento.findActivoRegistroByFamilia, query = "SELECT p FROM Procedimiento p WHERE p.familia.id = :id and (p.activo = 1 or p.activo = 2)"),
	@NamedQuery(name = Procedimiento.findAllActivoRegistro, query = "SELECT p FROM Procedimiento p WHERE (p.activo = 1 or p.activo = 2)"),
	@NamedQuery(name = Procedimiento.findByFamiliaAndTipoInicio, query = "SELECT p FROM Procedimiento p WHERE p.familia.id = :familiaId AND p.activo IN (:tiposInicioIds)"),
	@NamedQuery(name = Procedimiento.findByTipoInicio, query = "SELECT p FROM Procedimiento p WHERE p.activo IN (:tiposInicioIds)"),
})
@Table(schema="OC3F", name = "PENDING_WORKFLOW_")
public class Procedimiento implements Serializable {
	private static final long serialVersionUID = 3689426230943420416L;

	public static final String findById = "Procedimiento.findById";
	public static final String findByFamilia = "Procedimiento.findByFamilia";
	public static final String findAll = "Procedimiento.findAll";
	public static final String findActivoRegistroById = "Procedimiento.findActivoRegistroById";
	public static final String findActivoRegistroByFamilia = "Procedimiento.findActivoRegistroByFamilia";
	public static final String findAllActivoRegistro = "Procedimiento.findAllActivoRegistro";
	public static final String findByFamiliaAndTipoInicio = "Procedimiento.findByFamiliaAndTipoInicio";
	public static final String findByTipoInicio = "Procedimiento.findByTipoInicio";
	
	@Id
	@Column(name = "IDPROC")
	private String id;
	@Column(name = "DESCPROC")
	private String descripcion;
	@Column(name = "IDPROCPPAL")
	private String wfId;
	@Column(name = "ACTIVO")
    private Integer activo; 	// 0: No permite el inicio del expediente desde ningún origen.
							// 1: Permite el inicio del expediente tanto de oficio como desde fuera.
							// 2: Permite el inicio del expediente sólo desde fuera.
							// 3: Permite el inicio del expediente de oficio.	
	@Column(name = "MODIFICAR_INTERESADO")
	private Boolean modificarInteresado;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "FAMILIA_ID")
	// El name tiene que coincidir con el identificador real en bbdd de la columna en base de datos que hace de clave ajena de la tabla relacionada.
	private Familia familia;
	
    @Transient
    List<String> dptosInicio;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}
	
	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Boolean getModificarInteresado() {
		return modificarInteresado;
	}

	public void setModificarInteresado(Boolean modificarInteresado) {
		this.modificarInteresado = modificarInteresado;
	}

	public Familia getFamilia() {
		return familia;
	}

	public void setFamilia(Familia familia) {
		this.familia = familia;
	}
	
	public List<String> getDptosInicio() {
		if (this.dptosInicio == null) {
			this.dptosInicio = new ArrayList<String>();
		}
		return this.dptosInicio;
	}

	public void setDptosInicio(List<String> dptosInicio) {
		this.dptosInicio = dptosInicio;
	}
}
