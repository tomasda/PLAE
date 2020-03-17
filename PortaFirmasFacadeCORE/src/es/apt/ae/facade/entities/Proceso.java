package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries ({ 
	@NamedQuery(name = "Proceso.findById", query = "SELECT w FROM Proceso w WHERE w.id = :id"),
	@NamedQuery(name = "Proceso.findByPrimaryKey", query = "SELECT w FROM Proceso w WHERE w.id = :id AND w.version = :version"),
	@NamedQuery(name = "Proceso.findByIdProcedimiento", query="SELECT w FROM Proceso w WHERE w.id LIKE :idProcedimiento"),
	@NamedQuery(name = "Proceso.findByMaxVersion2", query = "SELECT w FROM Proceso w WHERE w.id LIKE :id AND w.version = (SELECT MAX(y.version) FROM Proceso y WHERE y.id LIKE :id)"),
})

@NamedNativeQueries ({ 
	@NamedNativeQuery(name = "Proceso.findByMaxVersion", 
					  query = "SELECT * FROM WORKFLOW_ a " +
							  "WHERE a.ID_ LIKE :id AND a.version_ = (SELECT MAX(TO_NUMBER(b.version_)) FROM WORKFLOW_ b WHERE b.ID_ LIKE :id)",
				      resultClass=Proceso.class),
})

@Entity
@Table(schema = "OC3F", name = "WORKFLOW_")
public class Proceso implements Serializable {
	private static final long serialVersionUID = -1827939039361760131L;

	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "VERSION_")
	private Double version;
	@Column(name = "DESCRIPTION_")
	private String descripcion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getVersion() {
		return version;
	}

	public void setVersion(Double version) {
		this.version = version;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
