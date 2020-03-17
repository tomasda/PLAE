package es.apt.ae.facade.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.transaction.Transactional;

@Entity
@Transactional
@NamedQueries({ 
	@NamedQuery(name = "IdsExpedientes.findByCodigo",  query = "SELECT ie FROM IdsExpedientes ie WHERE ie.codigo = :codigo")
})	
@Table(schema = "OC3F", name = "ADMINISTRATIVE_FILE_ID_")
public class IdsExpedientes implements Serializable {
	private static final long serialVersionUID = -2797030310776885526L;

	@Id
	@Column(name = "CODE_")
	private String codigo;
	@Column(name = "SEQUENCE_")
	private int secuencia;
	@Column(name = "FORMAT_")	
	private String formato;
	@Column(name = "RESTARTABLE_")	
	private boolean reiniciable;
	@Column(name = "INITIAL_SEQUENCE_")	
	private int secuenciaInicial;
	@Column(name = "LAST_ACCESS_")	
	private Date ultimoAcceso;
	
	
	public IdsExpedientes() {
		super();
	}
	public IdsExpedientes(int secuencia, String formato) {
		super();
		this.secuencia = secuencia;
		this.formato = formato;
	}

	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public int getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public boolean isReiniciable() {
		return reiniciable;
	}
	public void setReiniciable(boolean reiniciable) {
		this.reiniciable = reiniciable;
	}
	public int getSecuenciaInicial() {
		return secuenciaInicial;
	}
	public void setSecuenciaInicial(int secuenciaInicial) {
		this.secuenciaInicial = secuenciaInicial;
	}
	public Date getUltimoAcceso() {
		return ultimoAcceso;
	}
	public void setUltimoAcceso(Date ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}
	

}
