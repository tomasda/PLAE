package es.apt.ae.facade.entities.portafirmas;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PF_SUSTITUCION")
public class Sustitucion implements Serializable{
	
	private static final long serialVersionUID = -281622019571285345L;
	@Id @GeneratedValue
	@Column(name="ID_SUSTITUCION")
	private Long id;
	@ManyToOne
	@JoinColumn(name="ID_PERSONA")
	private Persona persona;
	@OneToOne (cascade=CascadeType.ALL)
	@JoinColumn(name="ID_AUSENCIA")
	private Ausencia ausencia;
	
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
	public Ausencia getAusencia() {
		return ausencia;
	}
	public void setAusencia(Ausencia ausencia) {
		this.ausencia = ausencia;
	}
}
