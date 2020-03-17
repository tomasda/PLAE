package com.opencanarias.apsct.portafirmas.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "PF_PERSONAS")

@NamedQueries(value = { 
		@NamedQuery(name=Persona.getPersonaById, query="SELECT p FROM Persona p WHERE p.id = :Id"), 
		@NamedQuery(name=Persona.getPersonaByDNI, query="SELECT p FROM Persona p WHERE p.numIdentificacion = :dni"),
		@NamedQuery(name=Persona.getListPersona, query="SELECT p FROM Persona p ORDER BY CONCAT(p.nombre, p.apellido1) ASC"),
		@NamedQuery(name=Persona.findRevisoresByDNI, query="SELECT re.persona FROM Persona p JOIN p.revision r JOIN r.listRevisor re WHERE p.numIdentificacion = :dni"),
		@NamedQuery(name=Persona.findRevisoresDeByDNI, query="SELECT lr.revision.persona FROM Persona p JOIN p.listRevisorDe lr WHERE p.numIdentificacion = :dni"),
		@NamedQuery(name=Persona.getSustitucionByDNI, query="SELECT s.ausencia.ausentado FROM Sustitucion s WHERE s.persona.numIdentificacion = :dni and s.ausencia.fechaFin is null"),
		@NamedQuery(name=Persona.getListFirmantes, query="SELECT p FROM Persona p WHERE p.firmante = true ORDER BY CONCAT(p.nombre, p.apellido1) ASC"),
		@NamedQuery(name=Persona.getListFirmantesNoPrueba, query="SELECT p FROM Persona p WHERE p.firmante = true AND (p.usuarioPruebas is null OR p.usuarioPruebas = 0) ORDER BY CONCAT(p.nombre, p.apellido1) ASC"),
		@NamedQuery(name=Persona.findListFirmanteByDNISolicitante, query="SELECT p FROM Persona p JOIN p.listSolicitantesPermitidos s WHERE p.firmante = true and s.solicitanteDNI = :dni ORDER BY CONCAT(p.nombre, p.apellido1) ASC"),
		@NamedQuery(name=Persona.getListPersonaVALD, query="SELECT p FROM Persona p WHERE p.validador = true ORDER BY CONCAT(p.nombre, p.apellido1) ASC"),
		/*Validacion de permisos*/
		@NamedQuery(name=Persona.findPersonasEnCircuitoByURI, query="SELECT p.numIdentificacion FROM Persona p JOIN p.grupoPersona gp JOIN gp.grupo g JOIN g.circuito c JOIN c.documento d WHERE d.uri = :uri "),
		@NamedQuery(name=Persona.findPersonasEnProcesoByURI, query="SELECT p.numIdentificacion FROM Persona p JOIN p.accionSobreDocumento a JOIN a.documento d WHERE d.uri = :uri "),
		@NamedQuery(name=Persona.findPersonasAusenciaEnCircuitoByURI, query="SELECT p2.persona.numIdentificacion FROM Persona p JOIN p.grupoPersona gp JOIN gp.grupo g "
				+ " JOIN g.circuito c JOIN c.documento d JOIN p.listAusencia la JOIN la.sustituto p2 WHERE d.uri = :uri and la.fechaFin is null"),
		@NamedQuery(name=Persona.findPersonasRevisoresEnCircuitoByURI, query="SELECT p2.numIdentificacion FROM Persona p JOIN p.grupoPersona gp JOIN gp.grupo g "
				+ " JOIN g.circuito c JOIN c.documento d JOIN p.revision rn JOIN rn.listRevisor rr JOIN rr.persona p2 WHERE d.uri = :uri ")
})
public class Persona implements Serializable{
	
	private static final long serialVersionUID = 8542075154756531254L;
	
	public static final String getPersonaById = "Persona.getPersonaById";
	public static final String getPersonaByDNI = "Persona.getPersonaByDNI";
	public static final String getListPersona = "Persona.getListPersona";
	public static final String findRevisoresByDNI = "Persona.findRevisoresByDNI";
	public static final String findRevisoresDeByDNI = "Persona.findRevisoresDeByDNI";
	public static final String getSustitucionByDNI = "Persona.getSustitucionByDNI";
	public static final String getListFirmantes = "Persona.getListFirmantes";
	public static final String getListFirmantesNoPrueba = "Persona.getListFirmantesNoPrueba";
	public static final String findListFirmanteByDNISolicitante = "Persona.findListFirmanteByDNISolicitante";
	public static final String getListPersonaVALD = "Persona.getListPersonaVALD";
	public static final String findPersonasEnCircuitoByURI = "Persona.findPersonasEnCircuitoByURI";
	public static final String findPersonasEnProcesoByURI = "Persona.findPersonasEnProcesoByURI";
	public static final String findPersonasAusenciaEnCircuitoByURI = "Persona.findPersonasAusenciaEnCircuitoByURI";
	public static final String findPersonasRevisoresEnCircuitoByURI = "Persona.findPersonasRevisoresEnCircuitoByURI";

	
	@Id @GeneratedValue
	@Column(name="ID_PERSONA")
	private Long id;
	@Column(name="NOMBRE")
	private String nombre;
	@Column (name="APELLIDO1")
	private String apellido1;
	@Column (name="APELLIDO2")
	private String apellido2;
	@Column (name="DNI_NIE")
	private String numIdentificacion;
	@Column(name="CARGO")
	private String cargo;
	@Column(name="MAIL")
	private String mail;
	@Column(name="ES_FIRMANTE")
	private Boolean firmante;
	@Column(name="ES_VALIDADOR")
	private Boolean validador;
	@Column(name="ES_USUARIO_PRUEBAS")
	private Boolean usuarioPruebas;
	@Column(name="ADJUNTOS_ALERTAS")
	private Boolean adjuntosAlertas;	
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="ausentado")
	private List<Ausencia> listAusencia;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="persona")
	private List<GrupoPersona> grupoPersona;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="persona")
	private List<ProcesoFirma> accionSobreDocumento;
	
	@OneToOne (mappedBy="persona")
	private Revision revision;
	
	@OneToMany(mappedBy="persona")
	private List<Revisor> listRevisorDe;
	
	@OneToMany(mappedBy="persona")
	private List<FirmantePersona> listSolicitantesPermitidos;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNumIdentificacion() {
		return numIdentificacion;
	}
	public void setNumIdentificacion(String numIdentificacion) {
		this.numIdentificacion = numIdentificacion;
	}

	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public void setAccionSobreDocumento(List<ProcesoFirma> accionSobreDocumento) {
		this.accionSobreDocumento = accionSobreDocumento;
	}

	public List<ProcesoFirma> getAccionSobreDocumento() {
		return accionSobreDocumento;
	}

	public List<GrupoPersona> getGrupoPersona() {
		return grupoPersona;
	}
	public void setGrupoPersona(List<GrupoPersona> grupoPersona) {
		this.grupoPersona = grupoPersona;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public List<Ausencia> getListAusencia() {
		return listAusencia;
	}
	public void setListAusencia(List<Ausencia> listAusencia) {
		this.listAusencia = listAusencia;
	}

	public Revision getRevision(){
		return revision;
	}
	
	public void setRevision(Revision revision){
		this.revision = revision;
	}
	public List<Revisor> getListRevisorDe() {
		return listRevisorDe;
	}
	public void setListRevisorDe(List<Revisor> listRevisorDe) {
		this.listRevisorDe = listRevisorDe;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Boolean getFirmante() {
		return firmante;
	}
	public void setFirmante(Boolean firmante) {
		this.firmante = firmante;
	}
	public Boolean getValidador() {
		return validador;
	}
	public void setValidador(Boolean validador) {
		this.validador = validador;
	}
	public Boolean getUsuarioPruebas() {
		return usuarioPruebas;
	}
	public void setUsuarioPruebas(Boolean usuarioPruebas) {
		this.usuarioPruebas = usuarioPruebas;
	}
	public Boolean getAdjuntosAlertas() {
		return adjuntosAlertas;
	}
	public void setAdjuntosAlertas(Boolean adjuntosAlertas) {
		this.adjuntosAlertas = adjuntosAlertas;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof Persona){
			return this.id.equals( ((Persona)object).getId() );
		}
		return false;
	}
	
	@Transient
	public String getNombreYApellidos() {
		return this.nombre + ((this.apellido1!=null)?(" " + this.apellido1):"") + 
				((this.apellido2!=null)?(" " + this.apellido2):"");
	}

	
}
