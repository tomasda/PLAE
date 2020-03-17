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
@Table(name = "PF_FIRMANTE_POR_PERSONA")
@NamedQueries(value = { 
		@NamedQuery(name=FirmantePersona.findByDNISolicitante, query="SELECT fp FROM FirmantePersona fp WHERE fp.solicitanteDNI = :dni"),
})

public class FirmantePersona {
	
	public static final String findByDNISolicitante = "FirmantePersona.findByDNISolicitante";
	
	@Id @GeneratedValue
	@Column(name="ID_REGISTRO")
	private Long id;
	@ManyToOne
	@JoinColumn(name="ID_PERSONA")
	private Persona persona;
	@Column(name="DNI_SOLICITANTE")
	private String solicitanteDNI;
	
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getSolicitanteDNI() {
		return solicitanteDNI;
	}
	public void setSolicitanteDNI(String solicitanteDNI) {
		this.solicitanteDNI = solicitanteDNI;
	}
}
