package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TransicionPk implements Serializable {
	private static final long serialVersionUID = 7608279801899841257L;

	@Column(name = "WF_ID", nullable = false, length = 200)
	private String wfId;
	@Column(name = "ACTIVIDAD_ORIGEN_ID", nullable = false, length = 200)
	private String actividadOrigenId;
	@Column(name = "ACTIVIDAD_DESTINO_ID", nullable = false, length = 200)
	private String actividadDestinoId;
	@Column(name = "NUM_TRANSICION", nullable = false, length = 2)
	private short numTransicion;

	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public String getActividadOrigenId() {
		return actividadOrigenId;
	}

	public void setActividadOrigenId(String actividadOrigenId) {
		this.actividadOrigenId = actividadOrigenId;
	}

	public String getActividadDestinoId() {
		return actividadDestinoId;
	}

	public void setActividadDestinoId(String actividadDestinoId) {
		this.actividadDestinoId = actividadDestinoId;
	}

	public short getNumTransicion() {
		return numTransicion;
	}

	public void setNumTransicion(short numTransicion) {
		this.numTransicion = numTransicion;
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