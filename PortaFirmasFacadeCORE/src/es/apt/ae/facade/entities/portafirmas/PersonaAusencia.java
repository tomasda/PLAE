package es.apt.ae.facade.entities.portafirmas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PF_PERSONA_AUSENCIA")
public class PersonaAusencia implements Serializable{

	private static final long serialVersionUID = -8858889865840914574L;

	@Id @GeneratedValue
	@Column(name = "ID")
	private Long id;
	@Column(name="FECHA_INICIO")
	private Date fechaInicio;
	@Column(name="FECHA_FIN")
	private Date fechaFin;
//	@OneToOne()
//	@JoinColumn(name="ID_PERSONA")
	private Persona ausentado;
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="ID_PERSONA")
	private Persona sustituto;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Persona getAusentado() {
		return ausentado;
	}
	public void setAusentado(Persona ausentado) {
		this.ausentado = ausentado;
	}
	public Persona getSustituto() {
		return sustituto;
	}
	public void setSustituto(Persona sustituto) {
		this.sustituto = sustituto;
	}

}
