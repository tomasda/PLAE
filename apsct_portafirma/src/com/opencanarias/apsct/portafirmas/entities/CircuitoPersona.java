package com.opencanarias.apsct.portafirmas.entities;

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
@Table(name = "PF_CIRCUITO_POR_PERSONA")
@NamedQueries(value = { 
		@NamedQuery(name="CircuitoPersona.findByDNISolicitante", query="SELECT cp FROM CircuitoPersona cp WHERE cp.solicitanteDNI = :dni"),
})

public class CircuitoPersona {
	@Id @GeneratedValue
	@Column(name="ID_REGISTRO")
	private Long id;
	@ManyToOne
	@JoinColumn(name="ID_CIRCUITO")
	private CircuitoEntity circuito;
	@Column(name="DNI_SOLICITANTE")
	private String solicitanteDNI;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CircuitoEntity getCircuito() {
		return circuito;
	}
	public void setCircuito(CircuitoEntity circuito) {
		this.circuito = circuito;
	}
	public String getSolicitanteDNI() {
		return solicitanteDNI;
	}
	public void setSolicitanteDNI(String solicitanteDNI) {
		this.solicitanteDNI = solicitanteDNI;
	}
}
