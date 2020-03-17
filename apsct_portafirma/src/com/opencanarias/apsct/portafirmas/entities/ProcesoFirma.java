package com.opencanarias.apsct.portafirmas.entities;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "PF_PROCESO_FIRMA")

@NamedQueries(value = { 
		@NamedQuery(name="findProcesoByUri",query="SELECT a FROM DocumentoPortafirmas d JOIN d.accionSobreDocumento a WHERE d.uri LIKE :uri ORDER BY a.fechaAccion"),
		@NamedQuery(name="findProcesoFirmadoByUri",query="SELECT a FROM DocumentoPortafirmas d JOIN d.accionSobreDocumento a WHERE d.uri LIKE :uri and a.accion.codigo = 'FIRM' ORDER BY a.fechaAccion")
})
public class ProcesoFirma implements Serializable {
	
	private static final long serialVersionUID = 6582372669003403565L;
	@Id @GeneratedValue
	@Column(name = "ID_PROCESO")
	private Long id;
	@Column(name="FECHA_ACCION")
	private Date fechaAccion;
	@Column(name="OBSERVACIONES", length=500)
	private String observaciones;
	
	@ManyToOne
	@JoinColumn(name="ID_PERSONA")
	private Persona persona;
	
	@ManyToOne
	@JoinColumn(name="ID_DOCUMENTO")
	private DocumentoPortafirmas documento;
	
	@ManyToOne
	@JoinColumn(name = "ID_ACCION")
	private Accion accion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaAccion() {
		return fechaAccion;
	}

	public void setFechaAccion(Date fechaAccion) {
		this.fechaAccion = fechaAccion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public DocumentoPortafirmas getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoPortafirmas documento) {
		this.documento = documento;
	}

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}
}
