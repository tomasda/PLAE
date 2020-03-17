package es.apt.ae.facade.dto;

import java.io.Serializable;
import java.util.Date;

public class DocumentoRegistroInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String descripcion;
	private String numRegistro;
	private Short tipoRegistro; 
	private Date fechaRegistro;
	private String uri;
	private String csv;
	private Float escala;
	private boolean invertirMarcaAgua;
	private Integer rotacion;
	private String tipoDocId;
	private String expediente;
	
	
	public DocumentoRegistroInfo(String nombre, String numRegistro, Date fechaRegistro, 
			String csv, Float escala, boolean invertirMarcaAgua, Integer rotacion) {
		super();
		this.nombre = nombre;
		this.numRegistro = numRegistro;
		this.fechaRegistro = fechaRegistro;
		this.csv = csv;
		this.escala = escala;
		this.invertirMarcaAgua = invertirMarcaAgua;
		this.rotacion = rotacion;
	}

	public DocumentoRegistroInfo(String nombre, String descripcion, String numRegistro, Short tipoRegistro,
			Date fechaRegistro, String uri, String csv, Float escala, boolean invertirMarcaAgua, Integer rotacion, 
			String tipoDocId, String expediente) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.numRegistro = numRegistro;
		this.tipoRegistro = tipoRegistro;
		this.fechaRegistro = fechaRegistro;
		this.uri = uri;
		this.csv = csv;
		this.escala = escala;
		this.invertirMarcaAgua = invertirMarcaAgua;
		this.rotacion = rotacion;
		this.tipoDocId = tipoDocId;
		this.expediente = expediente;
	}



	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getNumRegistro() {
		return numRegistro;
	}


	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
	}


	public Short getTipoRegistro() {
		return tipoRegistro;
	}


	public void setTipoRegistro(Short tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}


	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public String getUri() {
		return uri;
	}


	public void setUri(String uri) {
		this.uri = uri;
	}


	public String getCsv() {
		return csv;
	}


	public void setCsv(String csv) {
		this.csv = csv;
	}


	public Float getEscala() {
		return escala;
	}


	public void setEscala(Float escala) {
		this.escala = escala;
	}


	public boolean isInvertirMarcaAgua() {
		return invertirMarcaAgua;
	}
	

	public Integer getRotacion() {
		return rotacion;
	}

	
	public void setRotacion(Integer rotacion) {
		this.rotacion = rotacion;
	}

	
	public void setInvertirMarcaAgua(boolean invertirMarcaAgua) {
		this.invertirMarcaAgua = invertirMarcaAgua;
	}


	public String getTipoDocId() {
		return tipoDocId;
	}


	public void setTipoDocId(String tipoDocId) {
		this.tipoDocId = tipoDocId;
	}


	public String getExpediente() {
		return expediente;
	}


	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}


}
