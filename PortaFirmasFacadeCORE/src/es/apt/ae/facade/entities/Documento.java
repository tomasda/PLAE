package es.apt.ae.facade.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@NamedQueries({
	@NamedQuery(name = Documento.findByPk, query = "SELECT d FROM Documento d WHERE d.pk.id = :docName AND d.pk.expediente = :numExp"),
	@NamedQuery(name = Documento.findByUri, query = "SELECT d FROM Documento d WHERE d.uri = :uri AND d.pk.id NOT LIKE '%_signature%'"),
	@NamedQuery(name = Documento.findDocumentosExp, query="SELECT d FROM Documento d WHERE d.pk.expediente = :expediente AND d.pk.id NOT LIKE '%_signature%' AND type IS NOT NULL"),
	@NamedQuery(name = Documento.findDocumentosExpNoCancelados, query="SELECT d FROM Documento d WHERE d.pk.expediente = :expediente AND d.pk.id NOT LIKE '%_signature%' AND d.type IS NOT NULL AND d.type != 'canceled' ORDER BY d.elaborationDate asc"),
	@NamedQuery(name = Documento.findDocumentosExpByUris, query="SELECT d FROM Documento d WHERE d.uri in (:uris) AND d.pk.expediente = :expediente AND d.pk.id NOT LIKE '%_signature%'"),
	@NamedQuery(name = Documento.findDocumentosByUris, query="SELECT d FROM Documento d WHERE d.uri in (:uris) AND d.pk.id NOT LIKE '%_signature%'"),
	@NamedQuery(name = Documento.findDocumentosFirmaById, query="SELECT d FROM Documento d WHERE d.pk.id LIKE :signDocName AND d.pk.expediente = :expediente ORDER BY d.creationDate asc"),
	@NamedQuery(name = Documento.findDocumentoYFirmasById, query="SELECT d FROM Documento d WHERE (d.pk.id = :docName OR d.pk.id LIKE :signDocName) AND d.pk.expediente = :expediente ORDER BY d.creationDate asc"),
	@NamedQuery(name = Documento.findDocumentosActuacion, query = "SELECT d FROM Documento d WHERE d.actuacionId = :actuacionId AND d.pk.id NOT LIKE '%_signature%' and d.type != 'canceled'"),
	@NamedQuery(name = Documento.findDocumentosActuacionByUris, query="SELECT d FROM Documento d WHERE d.uri in (:uris) AND d.actuacionId = :actuacionId AND d.pk.id NOT LIKE '%_signature%'"),
	@NamedQuery(name = Documento.findDocumentosExpByRegistroAndUris, query="SELECT d FROM Documento d WHERE d.pk.expediente = :expediente AND d.uri in (:uris) AND d.recordNumber = :numRegistro AND d.pk.id NOT LIKE '%_signature%'"),
	@NamedQuery(name = Documento.findDocumentosExpByRegistro, query="SELECT d FROM Documento d WHERE d.pk.expediente = :expediente AND d.recordNumber = :numRegistro AND d.pk.id NOT LIKE '%_signature%'"),
	@NamedQuery(name = Documento.findDocumentosConRegistroByUris, query="SELECT d FROM Documento d WHERE d.uri in (:uris) AND d.recordNumber IS NOT NULL AND d.recordType = :tipoRegistro AND d.pk.id NOT LIKE '%_signature%'"),
	@NamedQuery(name = Documento.findDocumentoAuto, query="SELECT d FROM Documento d WHERE d.type = 'auto' AND d.pk.id = :docName AND d.pk.expediente = :numExp AND d.activity = :activityName"),
	@NamedQuery(name = Documento.findDocumentosRefActuacion, query="SELECT d FROM Documento d WHERE d.pk.expediente = :numExp AND d.pk.id NOT LIKE '%_signature%' AND (d.type = 'auto' OR d.type = 'attachment' OR d.type = 'registro') AND d.actuacionId is null"),
	@NamedQuery(name = Documento.findJustificanteByAsiento, query="SELECT d FROM Documento d WHERE d.recordNumber = :recordNumber AND d.documentType = 'TD09'"),

})
@Table(schema = "OC3F", name = "DOCUMENT_")
public class Documento implements Serializable {
	
	private static final long serialVersionUID = 8221667377753640712L;

	public static final String findByPk = "Documento.findByPk";
	public static final String findByUri = "Documento.findByUri";
	public static final String findDocumentosExp = "Documento.findDocumentosExp";
	public static final String findDocumentosExpNoCancelados = "Documento.findDocumentosExpNoCancelados";
	public static final String findDocumentosExpByUris = "Documento.findDocumentosExpByUris";
	public static final String findDocumentosByUris = "Documento.findDocumentosByUris";
	public static final String findDocumentosFirmaById = "Documento.findDocumentosFirmaById";
	public static final String findDocumentoYFirmasById = "Documento.findDocumentoYFirmasById";
	public static final String findDocumentosActuacion = "Documento.findDocumentosActuacion";
	public static final String findDocumentosActuacionByUris = "Documento.findDocumentosActuacionByUris";
	public static final String findDocumentosExpByRegistroAndUris = "Documento.findDocumentosExpByRegistroAndUris";	
	public static final String findDocumentosExpByRegistro = "Documento.findDocumentosExpByRegistro";	
	public static final String findDocumentosConRegistroByUris = "Documento.findDocumentosConRegistroByUris";
	public static final String findDocumentoAuto = "Documento.findDocumentoAuto";
	public static final String findDocumentosRefActuacion = "Documento.findDocumentosRefActuacion";
	public static final String findJustificanteByAsiento = "Documento.findJustificanteByAsiento";
	
	
	@EmbeddedId
	private DocumentoPk pk;
	@Column(name = "CONTENT_TYPE_", nullable = false)
	private String contentType;
	@Column(name = "DESCRIPTION_")
	private String descripcion;
	@Column(name = "REPOSITORY_URI_")
	private String uri;
	@Column(name = "HAS_DOCUMENT_", nullable = false)
	private Long hasDocument;
	@Column(name = "ACTIVITY_")
	private String activity;
	@Column(name = "CREATION_DATE_")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@Column(name = "SIGN_REFERENCE_")
	private String signReference;
	@Column(name = "SIGN_ACTIVITY_")
	private String signActivity;
	@Column(name = "RECORD_NUMBER_")
	private String recordNumber;
	@Column(name = "RECORD_DATE_")
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordDate;
	@Column(name = "RECORD_TYPE_")
	private String recordType;
	@Column(name = "TYPE_")
	private String type;
	@Column(name = "CURRENT_", nullable = false)
	private Long current;
	@Column(name = "STATE_")
	private String state;
	@Column(name = "DOCUMENT_TYPE")
	private String documentType;
	@Column(name = "ELABORATION_DATE_")
	@Temporal(TemporalType.TIMESTAMP)
	private Date elaborationDate;
	@Column(name = "SCALE_", nullable = false)
	private Double scale;
	@Column(name = "INVERT_WATERMARK_", nullable = false)
	private Long invertWatermark;
	@Column(name = "ROTATION_", nullable = false)
	private Integer rotation;
	@Column(name = "ACTUACION_ID_")
	private String actuacionId;
	@Column(name = "CANCEL_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cancelDate;
	@Column(name = "CANCEL_USER_ID")
	private String cancelUserId;
	@Column(name = "DOC_ORIGINAL_URI")
	private String docOriginalUri;	
	
	
	@Transient
	private byte[] content; 
	
	
	public Documento() {

	}

	// Para documento
	public Documento(DocumentoPk pk, String contentType, String descripcion, String uri, Long hasDocument,
			String activity, Date creationDate, String signReference, String recordNumber,
			Date recordDate, String recordType, String type, Long current, String state, String documentType,
			Date elaborationDate, Double scale, Long invertWatermark, Integer rotation, String actuacionId, Date cancelDate,
			String cancelUserId) {
		this.pk = pk;
		this.contentType = contentType;
		this.descripcion = descripcion;
		this.uri = uri;
		this.hasDocument = hasDocument;
		this.activity = activity;
		this.creationDate = creationDate;
		this.signReference = signReference;
		this.recordNumber = recordNumber;
		this.recordDate = recordDate;
		this.recordType = recordType;
		this.type = type;
		this.current = current;
		this.state = state;
		this.documentType = documentType;
		this.elaborationDate = elaborationDate;
		this.scale = scale;
		this.invertWatermark = invertWatermark;
		this.rotation = rotation;
		this.actuacionId = actuacionId;
		this.cancelDate = cancelDate;
		this.cancelUserId = cancelUserId;
	}
	
	// Para documento XML
	public Documento(DocumentoPk pk, String contentType, String uri, Long hasDocument,
			String activity, Date creationDate, Long current, Date elaborationDate, 
			Double scale, Long invertWatermark, Integer rotation) {
		this.pk = pk;
		this.contentType = contentType;
		this.uri = uri;
		this.hasDocument = hasDocument;
		this.activity = activity;
		this.creationDate = creationDate;
		this.current = current;
		this.elaborationDate = elaborationDate;
		this.scale = scale;
		this.invertWatermark = invertWatermark;
		this.rotation = rotation;
	}
	
	// Para documento de firma
	public Documento(DocumentoPk pk, String contentType, String descripcion, Long hasDocument,
			String activity, Date creationDate, String signReference, Long current, 
			Date elaborationDate, Double scale, Long invertWatermark, Integer rotation) {
		this.pk = pk;
		this.contentType = contentType;
		this.descripcion = descripcion;
		this.hasDocument = hasDocument;
		this.activity = activity;
		this.creationDate = creationDate;
		this.signReference = signReference;
		this.current = current;
		this.elaborationDate = elaborationDate;
		this.scale = scale;
		this.invertWatermark = invertWatermark;
		this.rotation = rotation;
	}

	public DocumentoPk getPk() {
		if (pk == null)
			pk = new DocumentoPk();
		return pk;
	}

	public void setPk(DocumentoPk pk) {
		this.pk = pk;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Long getHasDocument() {
		return hasDocument;
	}

	public void setHasDocument(Long hasDocument) {
		this.hasDocument = hasDocument;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getSignReference() {
		return signReference;
	}

	public void setSignReference(String signReference) {
		this.signReference = signReference;
	}

	public String getSignActivity() {
		return signActivity;
	}

	public void setSignActivity(String signActivity) {
		this.signActivity = signActivity;
	}

	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCurrent() {
		return current;
	}

	public void setCurrent(Long current) {
		this.current = current;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public Date getElaborationDate() {
		return elaborationDate;
	}

	public void setElaborationDate(Date elaborationDate) {
		this.elaborationDate = elaborationDate;
	}

	public Double getScale() {
		return scale;
	}

	public void setScale(Double scale) {
		this.scale = scale;
	}

	public Long getInvertWatermark() {
		return invertWatermark;
	}

	public void setInvertWatermark(Long invertWatermark) {
		this.invertWatermark = invertWatermark;
	}
	
	public Integer getRotation() {
		return rotation;
	}

	public void setRotation(Integer rotation) {
		this.rotation = rotation;
	}

	public String getActuacionId() {
		return actuacionId;
	}

	public void setActuacionId(String actuacionId) {
		this.actuacionId = actuacionId;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelUserId() {
		return cancelUserId;
	}

	public void setCancelUserId(String cancelUserId) {
		this.cancelUserId = cancelUserId;
	}
	
	public String getDocOriginalUri() {
		return docOriginalUri;
	}

	public void setDocOriginalUri(String docOriginalUri) {
		this.docOriginalUri = docOriginalUri;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
