package es.apt.ae.facade.dto;

import java.io.Serializable;
import java.util.Date;

import javax.activation.DataHandler;


public class DocumentoItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String nombre = null;
	private String descripcion = null;
	private String observaciones = null;
	private String extension = null;
	private String uri = null;
	private String docOriginalUri = null;
	private byte[] contenido = null;
	private DataHandler contenidoDH = null;
	private String tipoContenido = null;
	private String tipoId = null;	
	private String tipo = null;
	private String numRegistro = null;
	private Date fechaElaboracion = null;	
	private Date fechaRegistro = null;
	private Date fechaFin = null;
	private String estadoFirma = null;
	private String estadoNotificacion = null;
	private Double escala = null;
	private boolean invertirMarcaAgua = false;
	private Integer rotacion = null;	
	private String csv = null;
	private String firma = null;
	private boolean automatico = false;
	private boolean descargar = false;
	private boolean eliminar = false;
	private boolean verDetallesPortafirmas = false;
	private boolean verMetadatos = false;
	private boolean editarMetadatos = false;
	private boolean editarPropsVisualizacion = false;
	private boolean actualizar = false;
	private boolean actualizado = false;
	private String urlDescarga;
	
	
	public DocumentoItem() {
		super();
	}
	
	public DocumentoItem(String nombre, String descripcion, String observaciones, String extension,
			String uri, String docOriginalUri, String tipoId, String tipo, String numRegistro,
			Date fechaElaboracion, Date fechaRegistro, Date fechaFin, String estadoFirma,
			String estadoNotificacion, Double escala, boolean invertirMarcaAgua, Integer rotacion, String csv) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.observaciones = observaciones;
		this.extension = extension;
		this.uri = uri;
		this.docOriginalUri = docOriginalUri;
		this.tipoId = tipoId;
		this.tipo = tipo;
		this.numRegistro = numRegistro;
		this.fechaElaboracion = fechaElaboracion;
		this.fechaRegistro = fechaRegistro;
		this.fechaFin = fechaFin;
		this.estadoFirma = estadoFirma;
		this.estadoNotificacion = estadoNotificacion;
		this.escala = escala;
		this.invertirMarcaAgua = invertirMarcaAgua;
		this.rotacion = rotacion;
		this.csv = csv;
	}


	public DocumentoItem(String nombre, String descripcion, String observaciones, String extension,
			String uri, String docOriginalUri, String tipoId, String tipo, String numRegistro,
			Date fechaElaboracion, Date fechaRegistro, Date fechaFin, String estadoFirma,
			String estadoNotificacion, Double escala, boolean invertirMarcaAgua, Integer rotacion, 
			String csv, boolean descargar, boolean eliminar, boolean verDetallesPortafirmas, 
			boolean verMetadatos, boolean editarMetadatos, boolean editarPropsVisualizacion,
			boolean actualizar, boolean actualizado) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.observaciones = observaciones;
		this.extension = extension;
		this.uri = uri;
		this.docOriginalUri = docOriginalUri;
		this.tipoId = tipoId;
		this.tipo = tipo;
		this.numRegistro = numRegistro;
		this.fechaElaboracion = fechaElaboracion;
		this.fechaRegistro = fechaRegistro;
		this.fechaFin = fechaFin;
		this.estadoFirma = estadoFirma;
		this.estadoNotificacion = estadoNotificacion;
		this.escala = escala;
		this.invertirMarcaAgua = invertirMarcaAgua;
		this.rotacion = rotacion;
		this.csv = csv;
		this.descargar = descargar;
		this.eliminar = eliminar;
		this.verDetallesPortafirmas = verDetallesPortafirmas;
		this.verMetadatos = verMetadatos;
		this.editarMetadatos = editarMetadatos;
		this.editarPropsVisualizacion = editarPropsVisualizacion;
		this.actualizar = actualizar;
		this.actualizado = actualizado;
	}
	
	public DocumentoItem(String nombre, String descripcion, String observaciones, String extension, 
			String uri, String docOriginalUri, String tipoId, String tipo, String numRegistro,
			Date fechaElaboracion, Date fechaRegistro, Date fechaFin, String estadoFirma, 
			String estadoNotificacion, Double escala, boolean invertirMarcaAgua, Integer rotacion,
			boolean descargar, boolean eliminar, boolean verDetallesPortafirmas, boolean verMetadatos,
			boolean editarMetadatos, boolean editarPropsVisualizacion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.observaciones = observaciones;
		this.extension = extension;
		this.uri = uri;
		this.docOriginalUri = docOriginalUri;
		this.tipoId = tipoId;
		this.tipo = tipo;
		this.numRegistro = numRegistro;
		this.fechaElaboracion = fechaElaboracion;
		this.fechaRegistro = fechaRegistro;
		this.fechaFin = fechaFin;
		this.estadoFirma = estadoFirma;
		this.estadoNotificacion = estadoNotificacion;
		this.escala = escala;
		this.invertirMarcaAgua = invertirMarcaAgua;
		this.rotacion = rotacion;
		this.descargar = descargar;
		this.eliminar = eliminar;
		this.verDetallesPortafirmas = verDetallesPortafirmas;
		this.verMetadatos = verMetadatos;
		this.editarMetadatos = editarMetadatos;
		this.editarPropsVisualizacion = editarPropsVisualizacion;
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
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getDocOriginalUri() {
		return docOriginalUri;
	}
	public void setDocOriginalUri(String docOriginalUri) {
		this.docOriginalUri = docOriginalUri;
	}
	public byte[] getContenido() {
		return contenido;
	}
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
	public DataHandler getContenidoDH() {
		return contenidoDH;
	}
	public void setContenidoDH(DataHandler contenidoDH) {
		this.contenidoDH = contenidoDH;
	}
	public String getTipoContenido() {
		return tipoContenido;
	}
	public void setTipoContenido(String tipoContenido) {
		this.tipoContenido = tipoContenido;
	}
	public String getTipoId() {
		return tipoId;
	}
	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNumRegistro() {
		return numRegistro;
	}
	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
	}
	public Date getFechaElaboracion() {
		return fechaElaboracion;
	}
	public void setFechaElaboracion(Date fechaElaboracion) {
		this.fechaElaboracion = fechaElaboracion;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getEstadoFirma() {
		return estadoFirma;
	}
	public void setEstadoFirma(String estadoFirma) {
		this.estadoFirma = estadoFirma;
	}
	public String getEstadoNotificacion() {
		return estadoNotificacion;
	}
	public void setEstadoNotificacion(String estadoNotificacion) {
		this.estadoNotificacion = estadoNotificacion;
	}
	public Double getEscala() {
		return escala;
	}
	public void setEscala(Double escala) {
		this.escala = escala;
	}
	public boolean isInvertirMarcaAgua() {
		return invertirMarcaAgua;
	}
	public void setInvertirMarcaAgua(boolean invertirMarcaAgua) {
		this.invertirMarcaAgua = invertirMarcaAgua;
	}
	public Integer getRotacion() {
		return rotacion;
	}
	public void setRotacion(Integer rotacion) {
		this.rotacion = rotacion;
	}
	public boolean isAutomatico() {
		return automatico;
	}
	public void setAutomatico(boolean automatico) {
		this.automatico = automatico;
	}
	public String getCsv() {
		return csv;
	}
	public void setCsv(String csv) {
		this.csv = csv;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public boolean isDescargar() {
		return descargar;
	}
	public void setDescargar(boolean descargar) {
		this.descargar = descargar;
	}
	public boolean isEliminar() {
		return eliminar;
	}
	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}
	public boolean isVerDetallesPortafirmas() {
		return verDetallesPortafirmas;
	}
	public void setVerDetallesPortafirmas(boolean verDetallesPortafirmas) {
		this.verDetallesPortafirmas = verDetallesPortafirmas;
	}
	public boolean isVerMetadatos() {
		return verMetadatos;
	}
	public void setVerMetadatos(boolean verMetadatos) {
		this.verMetadatos = verMetadatos;
	}
	public boolean isEditarMetadatos() {
		return editarMetadatos;
	}
	public void setEditarMetadatos(boolean editarMetadatos) {
		this.editarMetadatos = editarMetadatos;
	}
	public boolean isEditarPropsVisualizacion() {
		return editarPropsVisualizacion;
	}
	public void setEditarPropsVisualizacion(boolean editarPropsVisualizacion) {
		this.editarPropsVisualizacion = editarPropsVisualizacion;
	}
	public boolean isActualizar() {
		return actualizar;
	}
	public void setActualizar(boolean actualizar) {
		this.actualizar = actualizar;
	}
	public boolean isActualizado() {
		return actualizado;
	}
	public void setActualizado(boolean actualizado) {
		this.actualizado = actualizado;
	}
	public String getUrlDescarga() {
		return urlDescarga;
	}
	public void setUrlDescarga(String urlDescarga) {
		this.urlDescarga = urlDescarga;
	}
	
}
