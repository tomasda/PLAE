package es.apt.ae.facade.entities.portafirmas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "PF_REVISOR")

@NamedQueries(value = { 
		@NamedQuery(name="getRevisoresByDNIPersona",query="SELECT rr FROM Persona p JOIN p.revision r JOIN r.listRevisor rr WHERE p.numIdentificacion = :dni") 
})

public class Revisor implements Serializable{
	private static final long serialVersionUID = 7014607228946246421L;
	@Id @GeneratedValue
	@Column(name="ID_REVISOR")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="ID_PERSONA")
	private Persona persona;
	
	@ManyToOne
	@JoinColumn(name="ID_REVISION")
	private Revision revision;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public Revision getRevision() {
		return revision;
	}
	public void setRevision(Revision revision) {
		this.revision = revision;
	}
	
}
