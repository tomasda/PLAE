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
	@NamedQuery(name = "Familia.findAll", query = "SELECT f FROM Familia f"),
})
@Table(schema = "OC3F", name = "CAT_FAMILIAS")
public class Familia implements Serializable {
	private static final long serialVersionUID = 3689426230943420416L;

	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "DESCRIPCION")
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
