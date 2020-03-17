package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocumentoIndicePk implements Serializable {
	private static final long serialVersionUID = 7608279801899841257L;

	@Column(name = "ADMINISTRATIVE_FILE_ID", nullable = false)
	private String idExpediente;
	@Column(name = "DOCUMENTO_ID", nullable = false)
	private String idDocumento;


	public DocumentoIndicePk() {
		super();
	}

	public DocumentoIndicePk(String idExpediente, String idDocumento) {
		super();
		this.idExpediente = idExpediente;
		this.idDocumento = idDocumento;
	}


	public String getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}