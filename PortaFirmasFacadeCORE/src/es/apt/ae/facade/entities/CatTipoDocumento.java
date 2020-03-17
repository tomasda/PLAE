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
	@NamedQuery(name = "CatTipoDocumento.findById", query = "SELECT ctd FROM CatTipoDocumento ctd WHERE ctd.id = :id"),
	@NamedQuery(name = "CatTipoDocumento.findAll", query = "SELECT ctd FROM CatTipoDocumento ctd ORDER BY ctd.descripcion ASC"),
})
@Table(schema="OC3F", name = "CAT_DOCUMENT_TYPE_")
public class CatTipoDocumento implements Serializable {
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
