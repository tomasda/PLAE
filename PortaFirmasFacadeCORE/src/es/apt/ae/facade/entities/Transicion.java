package es.apt.ae.facade.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "OC3F", name = "CAT_TRANSICIONES")
public class Transicion {
	
	@EmbeddedId
	private TransicionPk pk;
	@Column(name = "CAMPOS_DECISION", nullable=true, length=500)
	private String camposDecision;
	
	
	public TransicionPk getPk() {
		return pk;
	}
	public void setPk(TransicionPk pk) {
		this.pk = pk;
	}
	public String getCamposDecision() {
		return camposDecision;
	}
	public void setCamposDecision(String camposDecision) {
		this.camposDecision = camposDecision;
	}
	
}
