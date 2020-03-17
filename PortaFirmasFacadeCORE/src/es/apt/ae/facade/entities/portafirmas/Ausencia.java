package es.apt.ae.facade.entities.portafirmas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PF_AUSENCIA")

@NamedQueries(value = { 
		@NamedQuery(name="findAusenciasByDNI",query="SELECT pa FROM Ausencia pa WHERE pa.ausentado.numIdentificacion = :dni and pa.fechaFin is null" )
})

public class Ausencia implements Serializable{

	private static final long serialVersionUID = -8858889865840914574L;

	@Id @GeneratedValue
	@Column(name = "ID_AUSENCIA")
	private Long id;
	@Column(name="FECHA_INICIO")
	private Date fechaInicio;
	@Column(name="FECHA_FIN")
	private Date fechaFin;
	
	@ManyToOne
	@JoinColumn(name="ID_PERSONA")
	private Persona ausentado;
	
	@OneToOne(mappedBy="ausencia", cascade=CascadeType.ALL)
	private Sustitucion sustituto;
	
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
	public Sustitucion getSustitucion() {
		return sustituto;
	}
	public void setSustitucion(Sustitucion sustituto) {
		this.sustituto = sustituto;
	}

}
