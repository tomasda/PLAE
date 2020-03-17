package es.apt.ae.facade.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = "GestionPortafirmasDocumento.findByUri", query = "SELECT d FROM GestionPortafirmasDocumento d WHERE d.uriDocRepository = :uri order by d.fechaEnvioPortafirmas desc"),
})
@Table(schema = "OC3F", name = "GESTION_PORTAFIRMAS_DOC")
public class GestionPortafirmasDocumento implements Serializable {
	private static final long serialVersionUID = 8221667377753640712L;

	@Id
    @GeneratedValue(generator="SEQ_GESTION_PORTAFIRMAS_DOC")
    @SequenceGenerator(
    		name="SEQ_GESTION_PORTAFIRMAS_DOC", sequenceName="SEQ_GESTION_PORTAFIRMAS_DOC", allocationSize=1
    )
	@Column(name = "ID_PORTAFIRMAS")
	private Long id;
	@Column(name = "ADMINISTRATIVE_FILE_ID")	
	private String numExpediente;
	@Column(name = "ACTIVIDAD")		
	private String actividad;
	@Column(name = "PROCEDIMIENTO")	
	private String procedimientoId;
	@Column(name = "PROCESO")		
	private String proceso;
	@Column(name = "OBSERVACIONES")		
	private String observaciones;
	@Column(name = "DESCRIPCION")		
	private String descripcion;
	@Column(name = "ID_FIRMANTE")	
	private Long idFirmante;
	@Column(name = "ID_CIRCUITO")	
	private Long idCircuito;	
	@Column(name = "URI_DOC_REPOSITORY")		
	private String uriDocRepository;
	@Column(name = "DOC_ID")		
	private String docId;
	@Column(name = "ESTADO_FIRMA")		
	private String estadoFirma;
	@Column(name = "ASUNTO_EXPEDIENTE")		
	private String asuntoExpediente;
	@Column(name = "SOLICITANTE")		
	private String solicitante;
	@Column(name = "FECHA_ENVIO_PORTAFIRMAS")		
	private Date fechaEnvioPortafirmas;
	@Column(name = "FECHA_RESPUESTA")		
	private Date fechaRespuesta;
	@Column(name = "PF_CORPORATIVO")		
	private Boolean pfCorporativo;
	@Column(name = "BACKOFFICE")		
	private String backoffice;	
	@Column(name = "ACTUACION_ID", length=64)		
	private String numActuacion;	
	
	
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
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getProcedimientoId() {
		return procedimientoId;
	}
	public void setProcedimientoId(String procedimientoId) {
		this.procedimientoId = procedimientoId;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getIdFirmante() {
		return idFirmante;
	}
	public void setIdFirmante(Long idFirmante) {
		this.idFirmante = idFirmante;
	}
	public Long getIdCircuito() {
		return idCircuito;
	}
	public void setIdCircuito(Long idCircuito) {
		this.idCircuito = idCircuito;
	}
	public String getUriDocRepository() {
		return uriDocRepository;
	}
	public void setUriDocRepository(String uriDocRepository) {
		this.uriDocRepository = uriDocRepository;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getEstadoFirma() {
		return estadoFirma;
	}
	public void setEstadoFirma(String estadoFirma) {
		this.estadoFirma = estadoFirma;
	}
	public String getAsuntoExpediente() {
		return asuntoExpediente;
	}
	public void setAsuntoExpediente(String asuntoExpediente) {
		this.asuntoExpediente = asuntoExpediente;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public Date getFechaEnvioPortafirmas() {
		return fechaEnvioPortafirmas;
	}
	public void setFechaEnvioPortafirmas(Date fechaEnvioPortafirmas) {
		this.fechaEnvioPortafirmas = fechaEnvioPortafirmas;
	}
	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}
	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}
	public Boolean getPfCorporativo() {
		return pfCorporativo;
	}
	public void setPfCorporativo(Boolean pfCorporativo) {
		this.pfCorporativo = pfCorporativo;
	}
	public String getBackoffice() {
		return backoffice;
	}
	public void setBackoffice(String backoffice) {
		this.backoffice = backoffice;
	}
	public String getNumActuacion() {
		return numActuacion;
	}
	public void setNumActuacion(String numActuacion) {
		this.numActuacion = numActuacion;
	}
	
}
