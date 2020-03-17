package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries ({ 
	@NamedQuery(name = "CatDispositivo.findById", query = "SELECT d FROM CatDispositivo d WHERE d.id = :id"),
	@NamedQuery(name = "CatDispositivo.findByIdFirmaManuscrita", query = "SELECT d FROM CatDispositivo d WHERE d.idFirmaManuscrita = :idFirmaManuscrita"),
})
@Table(schema = "OC3F", name = "CAT_DISPOSITIVO")
public class CatDispositivo implements Serializable {
	
	private static final long serialVersionUID = -1827939039361760131L;

	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "DESCRIPCION")
	private String descripcion;
	@Column(name = "ID_FIRMA_MANUSCRITA")
	private String idFirmaManuscrita;
	
	
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
	public String getIdFirmaManuscrita() {
		return idFirmaManuscrita;
	}
	public void setIdFirmaManuscrita(String idFirmaManuscrita) {
		this.idFirmaManuscrita = idFirmaManuscrita;
	}

}
