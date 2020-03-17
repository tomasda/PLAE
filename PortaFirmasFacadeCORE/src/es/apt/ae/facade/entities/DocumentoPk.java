package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocumentoPk implements Serializable {
	private static final long serialVersionUID = 7608279801899841257L;

	@Column(name = "ADMINISTRATIVE_FILE_ID_", nullable = false)
	private String expediente;
	@Column(name = "ID_", nullable = false)
	private String id;

	
	public DocumentoPk() {
		super();
	}

	public DocumentoPk(String expediente, String id) {
		super();
		this.expediente = expediente;
		this.id = id;
	}

	
	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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