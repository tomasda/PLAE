package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "OC3F", name = "ACTIVITY_PHASE_")
public class Fase implements Serializable {
	private static final long serialVersionUID = 2418178910953666590L;

	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "DESCRIPTION_")
	private String descripcion;

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

}
