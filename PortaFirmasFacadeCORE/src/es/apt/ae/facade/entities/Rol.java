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
	@NamedQuery(name = Rol.findAll, query = "SELECT r FROM Rol r ORDER BY r.descripcion ASC"),
	@NamedQuery(name = Rol.findById, query = "SELECT r FROM Rol r WHERE r.id = :id"),
	@NamedQuery(name = Rol.findDecretables, query = "SELECT r FROM Rol r WHERE r.decretable = 1 ORDER BY r.descripcion ASC"),
	@NamedQuery(name = Rol.findDecretablesRegistro, query = "SELECT r FROM Rol r WHERE r.decretableRegistro = 1 ORDER BY r.descripcion ASC"),
	@NamedQuery(name = Rol.findHabilitados, query = "SELECT r FROM Rol r WHERE r.habilitado = 1 ORDER BY r.descripcion ASC"),	
})
@Table(schema="OC3F", name = "ROLE_")
public class Rol implements Serializable {
	private static final long serialVersionUID = 3067841736341473070L;

	public static final String findAll = "Rol.findAll";
	public static final String findById = "Rol.findById";
	public static final String findDecretables = "Rol.findDecretables";
	public static final String findDecretablesRegistro = "Rol.findDecretablesRegistro";
	public static final String findHabilitados = "Rol.findHabilitados";
	
	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "DESCRIPTION_")
	private String descripcion;
	@Column(name = "ALF_GROUP_")
	private String grupoAlfresco;
	@Column(name = "DECRETABLE_")
	private Boolean decretable;
	@Column(name = "DECRETABLE_REGISTRO_")
	private Boolean decretableRegistro;
	@Column(name = "HABILITADO_")
	private Boolean habilitado;
	

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

	public String getGrupoAlfresco() {
		return grupoAlfresco;
	}

	public void setGrupoAlfresco(String grupoAlfresco) {
		this.grupoAlfresco = grupoAlfresco;
	}

	public Boolean getDecretable() {
		return decretable;
	}

	public void setDecretable(Boolean decretable) {
		this.decretable = decretable;
	}
	
	public Boolean getDecretableRegistro() {
		return decretableRegistro;
	}

	public void setDecretableRegistro(Boolean decretableRegistro) {
		this.decretableRegistro = decretableRegistro;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}	
	
}
