package es.apt.ae.facade.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries(value = {
		@NamedQuery(name="FirmaManuscritaDocumentoDestinatario.findByDocUriAndFirmanteId", query="SELECT fmd FROM FirmaManuscritaDocumentoDestinatario fmd " + 
				"WHERE fmd.firmaManuscritaDoc.uriDocRepositorio = :uri AND fmd.identificadorFirmante = :identificadorFirmante ORDER BY fmd.firmaManuscritaDoc.fechaEnvio DESC"),
})
@Table(schema = "OC3F", name = "FIRMA_MANUSCRITA_DOC_DEST")
public class FirmaManuscritaDocumentoDestinatario implements Serializable {
	private static final long serialVersionUID = 8221667377753640712L;

	@Id
    @GeneratedValue(generator="SEQ_FIRMA_MANUSC_DOC_DEST")
    @SequenceGenerator(
    		name="SEQ_FIRMA_MANUSC_DOC_DEST", sequenceName="SEQ_FIRMA_MANUSC_DOC_DEST", allocationSize=1
    )
	@Column(name = "ID")
	private Long id;
	@ManyToOne (targetEntity=FirmaManuscritaDocumento.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "ID_FIRMA_MANUSCRITA_DOC", nullable=false, updatable = true, insertable = true)
	private FirmaManuscritaDocumento firmaManuscritaDoc;
	@Column(name = "ID_DISPOSITIVO")	
	private String idDispositivo;
	@Column(name = "IDENTIFICADOR_FIRMANTE")
	private String identificadorFirmante;
	@Column(name = "NOMBRE_FIRMANTE")
	private String nombreFirmante;		
	@Column(name = "ESTADO")		
	private String estado;
	@Column(name = "FECHA_ACCION")		
	private Date fechaAccion;
	@Column(name = "OBSERVACIONES")		
	private String observaciones;
	@Column(name = "ORDEN")	
	private short orden;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public FirmaManuscritaDocumento getFirmaManuscritaDoc() {
		return firmaManuscritaDoc;
	}
	public void setFirmaManuscritaDoc(FirmaManuscritaDocumento firmaManuscritaDoc) {
		this.firmaManuscritaDoc = firmaManuscritaDoc;
	}
	public String getIdDispositivo() {
		return idDispositivo;
	}
	public void setIdDispositivo(String idDispositivo) {
		this.idDispositivo = idDispositivo;
	}
	public String getIdentificadorFirmante() {
		return identificadorFirmante;
	}
	public void setIdentificadorFirmante(String identificadorFirmante) {
		this.identificadorFirmante = identificadorFirmante;
	}
	public String getNombreFirmante() {
		return nombreFirmante;
	}
	public void setNombreFirmante(String nombreFirmante) {
		this.nombreFirmante = nombreFirmante;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
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
	public short getOrden() {
		return orden;
	}
	public void setOrden(short orden) {
		this.orden = orden;
	}

}
