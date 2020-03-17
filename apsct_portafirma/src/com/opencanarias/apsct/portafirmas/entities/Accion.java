package com.opencanarias.apsct.portafirmas.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PF_TM_ACCION")

@NamedQueries(value = {
		@NamedQuery (name="findAccionbyCodigo" , query ="select a from Accion a where a.codigo like :codigo"),
		@NamedQuery (name="findListAccion" , query ="select a from Accion a")
})

public class Accion implements Serializable{

	private static final long serialVersionUID = 1839018515860614652L;
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
