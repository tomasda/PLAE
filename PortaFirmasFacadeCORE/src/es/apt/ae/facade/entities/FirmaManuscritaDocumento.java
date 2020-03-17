package es.apt.ae.facade.entities;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = "FirmaManuscritaDocumento.findByUri", query = "SELECT d FROM FirmaManuscritaDocumento d WHERE d.uriDocRepositorio = :uri order by d.fechaEnvio desc"),
	@NamedQuery(name = "FirmaManuscritaDocumento.findByDocGui", query = "SELECT d FROM FirmaManuscritaDocumento d WHERE d.docGui = :docGui"),
	@NamedQuery(name = "FirmaManuscritaDocumento.findByUris", query="SELECT d FROM FirmaManuscritaDocumento d WHERE d.uriDocRepositorio in (:uris)"),
	@NamedQuery(name = "FirmaManuscritaDocumento.findEnviadosByUris", query="SELECT d FROM FirmaManuscritaDocumento d WHERE d.uriDocRepositorio in (:uris) AND d.estado = '" + FirmaManuscritaDocumento.ESTADO_ENVIADO + "'")
})
@Table(schema = "OC3F", name = "FIRMA_MANUSCRITA_DOC")
public class FirmaManuscritaDocumento implements Serializable {
	private static final long serialVersionUID = 8221667377753640712L;

	public static final String ESTADO_ENVIADO = "ENVIADO";
	public static final String ESTADO_FIRMADO = "FIRMADO";
	public static final String ESTADO_RECHAZADO = "RECHAZADO";
	public static final String ESTADO_RECUPERADO = "RECUPERADO";
	public static final String ESTADO_RECUPERADO_OBSERVACIONES = "--DOCUMENTO RECUPERADO DE TERMINAL DE FIRMA MANUSCRITA-- " +
			   "Documento recuperado por el Tramitador del Expediente de manera voluntaria";
	
	@Id
    @GeneratedValue(generator="SEQ_FIRMA_MANUSC_DOC")
    @SequenceGenerator(
    		name="SEQ_FIRMA_MANUSC_DOC", sequenceName="SEQ_FIRMA_MANUSC_DOC", allocationSize=1
    )
	@Column(name = "ID")
	private Long id;
	@Column(name = "NUM_EXPEDIENTE")	
	private String numExpediente;
	@Column(name = "ACTUACION_ID")		
	private String numActuacion;
	@Column(name = "NOMBRE_DOCUMENTO")		
	private String docNombre;
	@Column(name = "URI_DOC_REPOSITORIO")		
	private String uriDocRepositorio;
	@Column(name = "ACTIVIDAD")		
	private String actividad;
	@Column(name = "DOC_GUI")		
	private String docGui;	
	@Column(name = "ESTADO")		
	private String estado;
	@Column(name = "SOLICITANTE_DNI")		
	private String solicitanteDni;
	@Column(name = "SOLICITANTE_EMAIL")		
	private String solicitanteEmail;	
	@Column(name = "BACKOFFICE_ID")		
	private Long idBackoffice;	
	@Column(name = "FECHA_ENVIO")		
	private Date fechaEnvio;
	@Column(name = "FECHA_FIN")		
	private Date fechaFin;
	@Column(name = "OBSERVACIONES")		
	private String observaciones;
	@Column(name = "DOC_ACTUALIZADO_REPOSITORIO")		
	private Boolean docActualizadoRepositorio;	
	@Column(name = "NOTIFICACION")		
	private Boolean notificacion;	
	@OneToMany(targetEntity=FirmaManuscritaDocumentoDestinatario.class, fetch = FetchType.LAZY, mappedBy="firmaManuscritaDoc", 
			cascade=CascadeType.ALL, orphanRemoval=true)
	private List<FirmaManuscritaDocumentoDestinatario> destinatarios;
	@OneToMany(targetEntity=FirmaManuscritaDocumentoWSDL.class, fetch = FetchType.LAZY, mappedBy="firmaManuscritaDoc", 
			cascade=CascadeType.ALL, orphanRemoval=true)
	private List<FirmaManuscritaDocumentoWSDL> wsdls;	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getNumActuacion() {
		return numActuacion;
	}
	public void setNumActuacion(String numActuacion) {
		this.numActuacion = numActuacion;
	}
	public String getDocNombre() {
		return docNombre;
	}
	public void setDocNombre(String docNombre) {
		this.docNombre = docNombre;
	}
	public String getUriDocRepositorio() {
		return uriDocRepositorio;
	}
	public void setUriDocRepositorio(String uriDocRepositorio) {
		this.uriDocRepositorio = uriDocRepositorio;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getDocGui() {
		return docGui;
	}
	public void setDocGui(String docGui) {
		this.docGui = docGui;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getSolicitanteDni() {
		return solicitanteDni;
	}
	public void setSolicitanteDni(String solicitanteDni) {
		this.solicitanteDni = solicitanteDni;
	}
	public String getSolicitanteEmail() {
		return solicitanteEmail;
	}
	public void setSolicitanteEmail(String solicitanteEmail) {
		this.solicitanteEmail = solicitanteEmail;
	}
	public Long getIdBackoffice() {
		return idBackoffice;
	}
	public void setIdBackoffice(Long idBackoffice) {
		this.idBackoffice = idBackoffice;
	}
	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Boolean getDocActualizadoRepositorio() {
		return docActualizadoRepositorio;
	}
	public void setDocActualizadoRepositorio(Boolean docActualizadoRepositorio) {
		this.docActualizadoRepositorio = docActualizadoRepositorio;
	}
	public Boolean getNotificacion() {
		return notificacion;
	}
	public void setNotificacion(Boolean notificacion) {
		this.notificacion = notificacion;
	}
	public List<FirmaManuscritaDocumentoDestinatario> getDestinatarios() {
		if (destinatarios == null) {
			destinatarios = new ArrayList<FirmaManuscritaDocumentoDestinatario>();
		}
		return destinatarios;
	}
	public void setDestinatarios(
			List<FirmaManuscritaDocumentoDestinatario> destinatarios) {
		this.destinatarios = destinatarios;
	}
	public List<FirmaManuscritaDocumentoWSDL> getWsdls() {
		if (wsdls == null) {
			wsdls = new ArrayList<FirmaManuscritaDocumentoWSDL>();
		}
		return wsdls;
	}
	public void setWsdls(List<FirmaManuscritaDocumentoWSDL> wsdls) {
		this.wsdls = wsdls;
	}
	
}
