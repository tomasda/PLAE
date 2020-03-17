package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "IndiceCapituloDocumento.findByIdCapitulo", 
				query = "SELECT icd FROM IndiceCapituloDocumento icd WHERE icd.capitulo.id = :idCapitulo"), 
	@NamedQuery(name = "IndiceCapituloDocumento.findByPk", 
				query = "SELECT icd FROM IndiceCapituloDocumento icd WHERE icd.pk.idExpediente = :idExpediente AND icd.pk.idDocumento = :idDocumento ORDER BY icd.ordenEnCapitulo ASC"), 				
})
@Table(schema = "OC3F", name = "INDICE_CAPITULO_DOCUMENTO")
public class IndiceCapituloDocumento implements Serializable {
	private static final long serialVersionUID = 9096923428790682453L;

	@EmbeddedId
	private DocumentoIndicePk pk;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CAPITULO_ID")	
	private IndiceCapitulo capitulo;
	@Column(name = "ORDEN_EN_CAPITULO", nullable = false, length = 5)
	private short ordenEnCapitulo;
	
	
	public IndiceCapituloDocumento() {
		super();
	}
	
	public IndiceCapituloDocumento(DocumentoIndicePk pk, IndiceCapitulo capitulo,
			short ordenEnCapitulo) {
		super();
		this.pk = pk;
		this.capitulo = capitulo;
		this.ordenEnCapitulo = ordenEnCapitulo;
	}


	public DocumentoIndicePk getPk() {
		return pk;
	}
	public void setPk(DocumentoIndicePk pk) {
		this.pk = pk;
	}
	public IndiceCapitulo getCapitulo() {
		return capitulo;
	}
	public void setCapitulo(IndiceCapitulo capitulo) {
		this.capitulo = capitulo;
	}
	public short getOrdenEnCapitulo() {
		return ordenEnCapitulo;
	}
	public void setOrdenEnCapitulo(short ordenEnCapitulo) {
		this.ordenEnCapitulo = ordenEnCapitulo;
	}
	
}
