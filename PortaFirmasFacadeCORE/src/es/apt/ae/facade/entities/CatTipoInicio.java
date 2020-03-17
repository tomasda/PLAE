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
	@NamedQuery(name = "CatTipoInicio.findById", query = "SELECT cti FROM CatTipoInicio cti WHERE cti.id = :id"),
	@NamedQuery(name = "CatTipoInicio.findAll", query = "SELECT cti FROM CatTipoInicio cti ORDER BY cti.descripcion ASC"),
})
@Table(schema="OC3F", name = "CAT_TIPOS_INICIO")
public class CatTipoInicio implements Serializable {
	private static final long serialVersionUID = 3689426230943420416L;

	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "DESCRIPCION")
	private String descripcion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
