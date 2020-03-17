package es.apt.ae.facade.entities;

import java.io.Serializable;

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

@NamedQueries(value = {
		@NamedQuery(name="FirmaManuscritaDocumentoWSDL.findWSDLByFMDocId", query="SELECT fmw FROM FirmaManuscritaDocumentoWSDL fmw WHERE fmw.firmaManuscritaDoc.id = :fmDocId"),
		@NamedQuery(name="FirmaManuscritaDocumentoWSDL.findWSDLByURI", query="SELECT fmw FROM FirmaManuscritaDocumentoWSDL fmw WHERE fmw.firmaManuscritaDoc.uriDocRepositorio = :uri"),
		@NamedQuery(name="FirmaManuscritaDocumentoWSDL.findWSDLByDocGui", query="SELECT fmw FROM FirmaManuscritaDocumentoWSDL fmw WHERE fmw.firmaManuscritaDoc.docGui = :docGui"),
})

@Entity
@Table(schema = "OC3F", name = "FIRMA_MANUSCRITA_DOC_WSDL")
public class FirmaManuscritaDocumentoWSDL implements Serializable{

	private static final long serialVersionUID = -8180643857514672335L;
	
	@Id
    @GeneratedValue(generator="SEQ_FIRMA_MANUSC_DOC_WSDL")
    @SequenceGenerator(
    		name="SEQ_FIRMA_MANUSC_DOC_WSDL", sequenceName="SEQ_FIRMA_MANUSC_DOC_WSDL", allocationSize=1
    )
	@Column(name = "ID")
	private Long id;
	@ManyToOne (targetEntity=FirmaManuscritaDocumento.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "ID_FIRMA_MANUSCRITA_DOC", nullable=false, updatable = true, insertable = true)
	private FirmaManuscritaDocumento firmaManuscritaDoc;
	@Column(name="URL", unique=true)
	private String url;
	@Column(name= "INFORMAR_ESTADO_INTERMEDIO")
	private Boolean informarEstadoIntermedio;	
	
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getInformarEstadoIntermedio() {
		return informarEstadoIntermedio;
	}
	public void setInformarEstadoIntermedio(Boolean informarEstadoIntermedio) {
		this.informarEstadoIntermedio = informarEstadoIntermedio;
	}
	
}
