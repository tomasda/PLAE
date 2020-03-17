package es.apt.ae.facade.entities.portafirmas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PF_GRUPO_PERSONAS")
public class GrupoPersona implements Serializable{

	private static final long serialVersionUID = 5908528668349891046L;
	@Id @GeneratedValue
	@Column(name = "ID_GRUPO_PERSONA")
	private Long id;
	@ManyToOne
	@JoinColumn(name="ID_GRUPO")
	private Grupo grupo;
	@ManyToOne
	@JoinColumn(name="ID_PERSONA")
	private Persona persona;
	@Column(name="PERSONA_OBLIGATORIA")
	private Boolean obligatorio;
	@Column(name="REALIZADO")
	private Boolean realizado;
	
	public GrupoPersona clone(GrupoPersona grupoPersonaOrigen){
		this.obligatorio = new Boolean(grupoPersonaOrigen.isObligatorio());
		this.persona = grupoPersonaOrigen.getPersona();
		this.realizado = new Boolean(grupoPersonaOrigen.isRealizado());
		return this;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public boolean isObligatorio() {
		if(obligatorio == null){
			obligatorio = false;
		}
		return obligatorio;
	}
	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}
	public boolean isRealizado() {
		if(realizado == null){
			realizado = false;
		}
		return realizado;
	}
	public void setRealidazo(boolean realizado) {
		this.realizado = realizado;
	}

	
}
