package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TrackPk implements Serializable {
	private static final long serialVersionUID = 7608279801899841257L;

	@Column(name = "ADMINISTRATIVE_FILE_ID_", nullable = false)
	private String idEntidad;
	@Column(name = "ACTIVITY_", nullable = false)
	private String actividad;
	@Column(name = "WF_ID_", nullable = false)
	private String wfId;	
	@Column(name = "WF_VERSION_", nullable = false)
	private String wfVersion;	
	@Column(name = "ITERATION_", nullable = false)	
	private Integer iteracion;
	

	public TrackPk() {
		super();
	}

	public TrackPk(String idEntidad, String actividad, String wfId,
			String wfVersion, Integer iteracion) {
		super();
		this.idEntidad = idEntidad;
		this.actividad = actividad;
		this.wfId = wfId;
		this.wfVersion = wfVersion;
		this.iteracion = iteracion;
	}

	
	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public String getWfVersion() {
		return wfVersion;
	}

	public void setWfVersion(String wfVersion) {
		this.wfVersion = wfVersion;
	}
	
	public Integer getIteracion() {
		return iteracion;
	}

	public void setIteracion(Integer iteracion) {
		this.iteracion = iteracion;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}