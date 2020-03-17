package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "CAT_BACKOFFICE")

@NamedQueries(value = {
		@NamedQuery (name="findAllBackoffice", query="select b from BackOffice b where b.username <> 'admin'"),
		@NamedQuery (name="findBackofficeByUsername" , query ="select b from BackOffice b where b.username = :username")
})
public class BackOffice implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID_BACKOFFICE")
	private Long id;
	@Column(name = "CODIGO")
	private String codigo;
	@Column(name = "DESCRIPCION")
	private String descripcion;
	@Column(name= "USERNAME")
	private String username;
	@Column(name= "INFORMAR_ESTADO_INTERMEDIO_PF")
	private Boolean informarEstadoIntermedioPF;
	@Column(name= "INFORMAR_ESTADO_INTERMEDIO_FM")
	private Boolean informarEstadoIntermedioFM;		
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Boolean getInformarEstadoIntermedioPF() {
		return informarEstadoIntermedioPF;
	}
	public void setInformarEstadoIntermedioPF(Boolean informarEstadoIntermedioPF) {
		this.informarEstadoIntermedioPF = informarEstadoIntermedioPF;
	}
	public Boolean getInformarEstadoIntermedioFM() {
		return informarEstadoIntermedioFM;
	}
	public void setInformarEstadoIntermedioFM(Boolean informarEstadoIntermedioFM) {
		this.informarEstadoIntermedioFM = informarEstadoIntermedioFM;
	}
}
