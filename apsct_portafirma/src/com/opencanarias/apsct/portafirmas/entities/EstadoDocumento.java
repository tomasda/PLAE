package com.opencanarias.apsct.portafirmas.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PF_TM_ESTADO_DOCUMENTO")
@NamedQueries(value = { 
		@NamedQuery(name = "findEstadoDocumentobyCod", query = "Select ed FROM EstadoDocumento ed WHERE ed.codigo LIKE :Codigo"),
		@NamedQuery(name = "findListEstadoDocumento", query = "Select ed FROM EstadoDocumento ed")
})
public class EstadoDocumento implements Serializable {

	private static final long serialVersionUID = 3418978499318248596L;
	@Id
	@Column(name = "ID")
	private Long id;
	@Column(name = "CODIGO")
	private String codigo;
	@Column(name = "DESCRIPCION")
	private String descripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
