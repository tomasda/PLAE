package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "OC3F", name = "INDICE_CAPITULO")
public class IndiceCapitulo implements Serializable {
	private static final long serialVersionUID = 9096923428790682453L;

	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "TITULO")
	private String titulo;
	@Column(name = "SECCION_ID")
	private Integer idSeccion;
	@Column(name = "ORDEN_EN_SECCION")	
	private short ordenEnSeccion;	
	
	
	public IndiceCapitulo() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getIdSeccion() {
		return idSeccion;
	}

	public void setIdSeccion(Integer idSeccion) {
		this.idSeccion = idSeccion;
	}

	public short getOrdenEnSeccion() {
		return ordenEnSeccion;
	}

	public void setOrdenEnSeccion(short ordenEnSeccion) {
		this.ordenEnSeccion = ordenEnSeccion;
	}
	
}
