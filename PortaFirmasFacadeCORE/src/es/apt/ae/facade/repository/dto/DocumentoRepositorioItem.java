package es.apt.ae.facade.repository.dto;

import java.io.Serializable;

import javax.activation.DataHandler;


public class DocumentoRepositorioItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String nombre = null;
	private String descripcion = null;
	private String uri = null;
	private String rutaRepositorio = null;
	private String rutaCIFS = null;
	private byte[] contenido = null;
	private String contenidoStr = null;
	private DataHandler contenidoComplejo = null;
	private String[] firmas = null;
	private Float escala = null;
	private Boolean invertirMarcaAgua = null;
	private Integer rotacion = null;
	private Integer paginaInicial = null;
	private Integer paginaFinal = null;
	
	
	public DocumentoRepositorioItem() {
		super();
	}
	
	public DocumentoRepositorioItem(String nombre, String descripcion,
			String uri, String rutaRepositorio, String rutaCIFS,
			byte[] contenido) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.uri = uri;
		this.rutaRepositorio = rutaRepositorio;
		this.rutaCIFS = rutaCIFS;
		this.contenido = contenido;
	}
	
	public DocumentoRepositorioItem(String nombre, String uri, byte[] contenido) {
		this.nombre = nombre;
		this.uri = uri;
		this.contenido = contenido;
	}
	
	public DocumentoRepositorioItem(String uri) {
		this.uri = uri;
	}
	
	public DocumentoRepositorioItem(String uri, Integer paginaInicial,
			Integer paginaFinal) {
		super();
		this.uri = uri;
		this.paginaInicial = paginaInicial;
		this.paginaFinal = paginaFinal;
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
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getRutaRepositorio() {
		return rutaRepositorio;
	}
	public void setRutaRepositorio(String rutaRepositorio) {
		this.rutaRepositorio = rutaRepositorio;
	}
	public String getRutaCIFS() {
		return rutaCIFS;
	}
	public void setRutaCIFS(String rutaCIFS) {
		this.rutaCIFS = rutaCIFS;
	}
	public byte[] getContenido() {
		return contenido;
	}
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
	public String getContenidoStr() {
		return contenidoStr;
	}
	public void setContenidoStr(String contenidoStr) {
		this.contenidoStr = contenidoStr;
	}
	public DataHandler getContenidoComplejo() {
		return contenidoComplejo;
	}
	public void setContenidoComplejo(DataHandler contenidoComplejo) {
		this.contenidoComplejo = contenidoComplejo;
	}
	public String[] getFirmas() {
		return firmas;
	}
	public void setFirmas(String[] firmas) {
		this.firmas = firmas;
	}
	public Float getEscala() {
		return escala;
	}
	public void setEscala(Float escala) {
		this.escala = escala;
	}
	public Boolean getInvertirMarcaAgua() {
		return invertirMarcaAgua;
	}
	public void setInvertirMarcaAgua(Boolean invertirMarcaAgua) {
		this.invertirMarcaAgua = invertirMarcaAgua;
	}
	public Integer getRotacion() {
		return rotacion;
	}
	public void setRotacion(Integer rotacion) {
		this.rotacion = rotacion;
	}
	public Integer getPaginaInicial() {
		return paginaInicial;
	}
	public void setPaginaInicial(Integer paginaInicial) {
		this.paginaInicial = paginaInicial;
	}
	public Integer getPaginaFinal() {
		return paginaFinal;
	}
	public void setPaginaFinal(Integer paginaFinal) {
		this.paginaFinal = paginaFinal;
	}
}
