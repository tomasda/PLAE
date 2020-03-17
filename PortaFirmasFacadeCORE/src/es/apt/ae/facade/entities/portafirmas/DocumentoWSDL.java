package es.apt.ae.facade.entities.portafirmas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries(value = {
		@NamedQuery(name="DocumentoWSDL.findWSDLByURI", query="SELECT dw FROM DocumentoWSDL dw WHERE dw.documento.uri = :uri")
})

@Entity
@Table(name = "PF_DOCUMENTOS_WSDL")
public class DocumentoWSDL implements Serializable{

	private static final long serialVersionUID = -8180643857514672335L;
	
	@Id @GeneratedValue
	@Column(name = "ID")
	private Long id;	
	@ManyToOne
	@JoinColumn(name = "ID_DOCUMENTO")
	private DocumentoPortafirmas documento;
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
	public DocumentoPortafirmas getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoPortafirmas documento) {
		this.documento = documento;
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
