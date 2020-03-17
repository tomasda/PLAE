package es.apt.ae.facade.entities.portafirmas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = "Departamento.getDepartamentoById", query = "SELECT dpt FROM Departamento dpt WHERE dpt.id = :id"),
})
@Table(name = "PF_CAT_DEPARTAMENTO")
public class Departamento implements Serializable {
	private static final long serialVersionUID = 8583673722566645895L;

	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "ACRONIMO")
	private String acronimo;	
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

	public String getAcronimo() {
		return acronimo;
	}

	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}

}
