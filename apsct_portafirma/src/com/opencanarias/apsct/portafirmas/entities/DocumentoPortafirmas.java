package com.opencanarias.apsct.portafirmas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import es.apt.ae.facade.entities.BackOffice;

@Entity
@Table(name = "PF_DOCUMENTOS")
@NamedQueries(value = {
		@NamedQuery(name = "findDocumentoPortafirmasbyId", query = "Select dp FROM DocumentoPortafirmas dp WHERE dp.id LIKE :Id"),
		@NamedQuery(name = "findDocumentoPortafirmasbyUri", query = "Select dp FROM DocumentoPortafirmas dp WHERE dp.uri LIKE :uri"),
		@NamedQuery(name = "findAllStructureDocumentoPortafirmasbyUri", query="SELECT distinct d FROM DocumentoPortafirmas d JOIN d.circuito"
				+ " c JOIN c.listGrupo g JOIN g.grupoPersona gp JOIN gp.persona p WHERE d.uri LIKE :uri"),
				
		@NamedQuery(name = "findDocumentosPendientesColaboradoresbyDNI", query = ""
				+ "SELECT distinct d FROM Persona p "
				+ "	JOIN p.grupoPersona gp"
				+ "	JOIN gp.grupo g"
				+ "	JOIN g.circuito f"
				+ " JOIN f.documento"
				+ " d WHERE "
				+ "		(p.numIdentificacion = :usuarioDNI "
				+ "		OR p.numIdentificacion =("//Los del usuario o los de la persona a la que sustituye
				+ " 		SELECT pa.numIdentificacion FROM Persona pa "
				+ "			JOIN pa.listAusencia a "
				+ "			JOIN a.sustituto s "
				+ "			JOIN s.persona p"
				+ " 		WHERE p.numIdentificacion = :usuarioDNI "
				+ "			AND a.fechaFin is null "
				+ "			group by pa.numIdentificacion)"//Ausencia no terminada
				+ "		) "
				+ " AND d.id IN "
				+ "		(SELECT d.id FROM Persona p " //Los que han sido revisados por alguno de mis revisores
				+ "		JOIN p.revision r JOIN r.listRevisor lr "
				+ "		JOIN lr.persona pr JOIN pr.accionSobreDocumento a "
				+ "		JOIN a.documento d "
				+ "		WHERE p.numIdentificacion = :usuarioDNI) "//COMPLETAR
				+ " AND d.id NOT IN ( "
				+ "		SELECT d.id FROM Persona p" //Y si el documento no esta en la lista de acciones que he realizado
				+ " 	JOIN p.accionSobreDocumento a "
				+ "		JOIN a.documento d "
				+ "		JOIN d.circuito f "
				+ "		WHERE (p.numIdentificacion = :usuarioDNI "
				+ "		OR p.numIdentificacion =("
				+ " 		SELECT pa.numIdentificacion FROM Persona pa "
				+ "			JOIN pa.listAusencia a "
				+ "			JOIN a.sustituto s "
				+ "			JOIN s.persona p"
				+ " 		WHERE p.numIdentificacion = :usuarioDNI "
				+ "			AND a.fechaFin is null group by pa.numIdentificacion)"
				+ " 	)"
				+ " 	AND f.fechaCreacion < a.fechaAccion) "
				+ "AND g.orden = f.ordenActivo order by d.fechaSubidaPortafirmas desc"),
				
		@NamedQuery(name = "findDocumentosTramitadosbyDNI" , query="SELECT d FROM DocumentoPortafirmas d inner JOIN d.accionSobreDocumento"
				+ " a inner JOIN a.persona p WHERE p.numIdentificacion LIKE :usuarioDNI order by d.fechaSubidaPortafirmas desc"),
		@NamedQuery(name = "findDocumentosEnviadosbyDNI", query="SELECT d FROM DocumentoPortafirmas d WHERE (d.subidoPorDNI LIKE :usuarioDNI)"
				+ " AND (d.estadoDocumento.codigo <> 'PEND') AND (d.estadoDocumento.codigo <> 'RECP') order by d.fechaSubidaPortafirmas desc"),
		@NamedQuery(name = "findDocumentosPersonalesbyDNI" , query="SELECT d FROM DocumentoPortafirmas d LEFT JOIN d.sistemaOrigen s WHERE (d.subidoPorDNI LIKE :usuarioDNI)"
				+ " AND (d.estadoDocumento.codigo IN ('PEND','RECP') and s is null) order by d.fechaSubidaPortafirmas desc"),
		@NamedQuery(name = "findListDocumentoPortafirmasbyURI", query="SELECT d FROM DocumentoPortafirmas d WHERE d.uri in (:uris)"),
		/*SubConsultas*/
		@NamedQuery(name="findDocumentosRealizados", query=	"SELECT distinct d.id FROM Persona p" //Y si el documento no esta en la lista de acciones que he realizado
				+ " 	JOIN p.accionSobreDocumento a JOIN a.documento d JOIN d.circuito f"
				+ "		WHERE (p.numIdentificacion = :usuarioDNI"
				+ "		OR p.numIdentificacion =("
				+ " 		SELECT pa.numIdentificacion"
				+ "			FROM Persona pa JOIN pa.listAusencia a JOIN a.sustituto s JOIN s.persona p"
				+ " 		WHERE p.numIdentificacion = :usuarioDNI"
				+ "			AND a.fechaFin is null"
				+ "			group by pa.numIdentificacion)"
				+ " 	)"
				+ " 	AND f.fechaCreacion < a.fechaAccion "	),
				
		@NamedQuery(name="findDocumentosPendientesSustitutoRevisados", query=" SELECT d.id FROM DocumentoPortafirmas d "
				+ "			JOIN d.accionSobreDocumento a"
				+ "			WHERE a.persona.id IN ("
				+ "				SELECT distinct rr.persona.id FROM Persona p"
				+ "				JOIN p.revision rn JOIN rn.listRevisor rr"
				+ "				WHERE rn.persona.id IN ("
				+ "					SELECT distinct p.id FROM Persona p"
				+ "					JOIN p.listAusencia a JOIN a.sustituto s"
				+ "					WHERE s.persona.numIdentificacion = :usuarioDNI AND a.fechaFin is null) "
				+ "			)"),
				
		@NamedQuery(name="findDocumentosPendientesSustitutoSinRevision", query=""
				+ "SELECT d.id FROM DocumentoPortafirmas d "
				+ "			JOIN d.circuito c JOIN c.listGrupo g JOIN g.grupoPersona gp JOIN gp.persona p"
				+ "			WHERE p.id IN ("
				+ "				SELECT distinct p.id FROM Persona p"
				+ "				JOIN p.listAusencia a JOIN a.sustituto s"
				+ "				WHERE s.persona.numIdentificacion = :usuarioDNI "
				+ "				AND a.fechaFin is null"
				+ "			) AND d.id NOT IN ("/*NO ESTA LOS DOCUMENTOS SOBRE LOS QUE HA ACTUADO DICHA PERSONA*/
				+ "				SELECT d.id FROM DocumentoPortafirmas d"
				+ "				JOIN d.accionSobreDocumento a JOIN a.persona p"
				+ "				LEFT JOIN p.revision rn"
				+ "				WHERE p.id IN("
				+ "					SELECT distinct p.id FROM Persona p"
				+ "					JOIN p.listAusencia a JOIN a.sustituto s"
				+ "					WHERE s.persona.numIdentificacion = :usuarioDNI AND a.fechaFin is null"
				+ "				) AND rn.persona is null"
				+ "			) AND g.orden = c.ordenActivo"),
				
			@NamedQuery(name="findDocumentosPendientesSustituto", query=""
					+"		SELECT d.id FROM DocumentoPortafirmas d	"
					+ "		WHERE d.id NOT IN ("/*Que no esté en la lista de documentos sobre los que ya he actuado*/
					+ "			SELECT d.id FROM DocumentoPortafirmas d"
					+ "			JOIN d.accionSobreDocumento a JOIN a.persona p"
					+ "			WHERE p.numIdentificacion = :usuarioDNI"
					+ "		) AND d.id IN ("/*Los Documentos de quien estoy sustituyendo, que no tienen revision*/
					+ "			:docsSustituto"	
					+ "		)"),	
								
			@NamedQuery (name="findDocumentosPendientesColaborador", query=" SELECT p.numIdentificacion "
					+ "		FROM Persona p JOIN p.revision r JOIN r.listRevisor lr JOIN lr.persona ps"
					+ "		WHERE ps.numIdentificacion = :usuarioDNI"),//Los de quien soy colaborador
			@NamedQuery (name="findDocumentosPendientesPersonales", query=""
					+ " SELECT d.id FROM Persona p"
					+ " JOIN p.grupoPersona gp JOIN gp.grupo g JOIN g.circuito f JOIN f.documento d"
					+ " WHERE g.orden = f.ordenActivo AND p.numIdentificacion = :usuarioDNI"),
		
			@NamedQuery(name="findDocumentosPendientesBigQuery", query=""	
					+ "SELECT distinct d FROM Persona p"
					+ " JOIN p.grupoPersona gp JOIN gp.grupo g JOIN g.circuito f JOIN f.documento d"
					+ " WHERE (d.id NOT IN (:listDocumentosNotIN))"
					+ " AND  (d.id IN (:listDocumentosIN))"
					+ "")
		
})
public class DocumentoPortafirmas implements Serializable{
	
	private static final long serialVersionUID = 904084320431067604L;
	
	@Id @GeneratedValue
	@Column(name = "ID_DOCUMENTO")
	private Long id;	
	@Column(name="FECHA_SUBIDA_PORTAFIRMAS")
	private Date fechaSubidaPortafirmas;
	@Column(name="URI", unique=true)
	private String uri;
	@Column(name="DNI_SUBIDO_POR")
	private String subidoPorDNI;
	@Column(name="NOMBRE_SUBIDO_POR")
	private String subidoPorNombre;
	@Column(name="TIPO_FIRMA")
	private String tipoFirma;
	@Column(name="COMUNICADO")
	private Boolean comunicado;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "ID_FLUJO")
	private CircuitoEntity circuito;
	@ManyToOne
	@JoinColumn(name = "ID_ESTADO")
	private EstadoDocumento estadoDocumento;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="documento",fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ProcesoFirma> accionSobreDocumento;
	@ManyToOne
	@JoinColumn(name = "ID_BACKOFFICE_ORIGEN")
	private BackOffice sistemaOrigen;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPARTAMENTO")
	private Departamento departamento;	
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="documento")
	private List<DocumentoWSDL> listUrl;
	
	@Column(name="ASUNTO")
	private String asunto;
	@Column(name="DOC_REL")
	private String docRelacionada;
	@Column(name="URL_DETALLE_ORIGEN")
	private String urlDetalleOrigen;
	@Column(name="NOMBRE_DOCUMENTO")
	private String nombre;
	@Column(name = "HASH")
	private String hash;
	@Column(name="MAIL_CREADOR")
	private String mailCreador;
	@Transient
	private String aliasNombre;
	@Transient
	private String metadatoFirma;
	@Transient
	private String contenido;

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
		if (nombre.length() > 20){
			this.aliasNombre = nombre.substring(0, 14) +"[...]";
		} else{
			this.aliasNombre = nombre;
		}
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}	
	public String getAliasNombre() {
		if (nombre.length() > 20){
			return nombre.substring(0, 14)+"[...]";
		}
		return nombre;
	}
	public Date getFechaSubidaPortafirmas() {
		return fechaSubidaPortafirmas;
	}
	public void setFechaSubidaPortafirmas(Date fechaSubidaPortafirmas) {
		this.fechaSubidaPortafirmas = fechaSubidaPortafirmas;
	}
	public String getSubidoPorDNI() {
		return subidoPorDNI;
	}
	public void setSubidoPorDNI(String subidoPorDNI) {
		this.subidoPorDNI = subidoPorDNI;
	}
	public String getSubidoPorNombre() {
		return subidoPorNombre;
	}
	public void setSubidoPorNombre(String subidoPorNombre) {
		this.subidoPorNombre = subidoPorNombre;
	}
	public void setEstadoDocumento(EstadoDocumento estadoDocumento) {
		this.estadoDocumento = estadoDocumento;
	}
	public EstadoDocumento getEstadoDocumento() {
		return estadoDocumento;
	}
	public Boolean isComunicado() {
		return comunicado;
	}
	public void setComunicado(Boolean comunicado) {
		this.comunicado = comunicado;
	}
	public CircuitoEntity getCircuito() {
		return circuito;
	}
	public void setCircuito(CircuitoEntity circuito) {
		this.circuito = circuito;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getDocRelacionada() {
		return docRelacionada;
	}
	public void setDocRelacionada(String docRelacionada) {
		this.docRelacionada = docRelacionada;
	}
	public String getUrlDetalleOrigen() {
		return urlDetalleOrigen;
	}
	public void setUrlDetalleOrigen(String urlDetalleOrigen) {
		this.urlDetalleOrigen = urlDetalleOrigen;
	}

	@Override
	public boolean equals(Object object) {
		if(object instanceof DocumentoPortafirmas){
			return ((DocumentoPortafirmas)object).getId().equals(this.id);
		}
		return false;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public void setAliasNombre(String aliasNombre) {
		if (nombre.length() > 20){
			aliasNombre = nombre.substring(0, 14)+"[...]";
		}
	}
	public String getMetadatoFirma() {
		return metadatoFirma;
	}
	public void setMetadatoFirma(String metadatoFirma) {
		this.metadatoFirma = metadatoFirma;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	public BackOffice getSistemaOrigen() {
		return sistemaOrigen;
	}
	public void setSistemaOrigen(BackOffice sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}
	public List<ProcesoFirma> getAccionSobreDocumento() {
		if(accionSobreDocumento == null){
			accionSobreDocumento = new ArrayList<ProcesoFirma>();
		}
		return accionSobreDocumento;
	}
	public void setAccionSobreDocumento(List<ProcesoFirma> accionSobreDocumento) {
		this.accionSobreDocumento = accionSobreDocumento;
	}
	public List<DocumentoWSDL> getListUrl() {
		return listUrl;
	}
	public void setListUrl(List<DocumentoWSDL> listUrl) {
		this.listUrl = listUrl;
	}
	public Boolean getComunicado() {
		return comunicado;
	}
	public String getTipoFirma() {
		return tipoFirma;
	}
	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}
	public String getMailCreador() {
		return mailCreador;
	}
	public void setMailCreador(String mailCreador) {
		this.mailCreador = mailCreador;
	}

	
}
