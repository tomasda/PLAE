package es.apt.ae.facade.entities.portafirmas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PF_TM_TIPO_GRUPO")

@NamedQueries(value = { 
		@NamedQuery(name = "findTipoGrupobyCod", query = "Select tg FROM TipoGrupo tg WHERE tg.codigo LIKE :Codigo"),
		@NamedQuery(name = "findListTipoGrupo", query = "Select tg FROM TipoGrupo tg")
})
public class TipoGrupo implements Serializable{

	private static final long serialVersionUID = -8485637947136168497L;
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
