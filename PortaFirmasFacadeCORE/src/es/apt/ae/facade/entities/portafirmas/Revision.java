package es.apt.ae.facade.entities.portafirmas;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries(value = { 
		@NamedQuery(name="getRevision",query="SELECT r FROM Revision r WHERE r.persona.numIdentificacion = :numIdentificacion")
})
@Entity
@Table(name = "PF_REVISION")
public class Revision implements Serializable{

	private static final long serialVersionUID = 7014607228946246421L;
	@Id @GeneratedValue
	@Column(name="ID_REVISION")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="ID_PERSONA")
	private Persona persona;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="revision")
	private List<Revisor> listRevisor;

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

	public List<Revisor> getListRevisor() {
		return listRevisor;
	}

	public void setListRevisor(List<Revisor> listRevisor) {
		this.listRevisor = listRevisor;
	}
	
}
