package com.opencanarias.apsct.portafirmas.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PF_TM_TIPO_FLUJO")

@NamedQueries(value = { 
		@NamedQuery(name = "findTipoCircuitobyCod", query = "Select tc FROM TipoCircuito tc WHERE tc.codigo LIKE :Codigo"),
		@NamedQuery(name = "findListTipoCircuito", query = "Select tc FROM TipoCircuito tc")
})
public class TipoCircuito implements Serializable{
	
	private static final long serialVersionUID = -6397378092948513116L;
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
