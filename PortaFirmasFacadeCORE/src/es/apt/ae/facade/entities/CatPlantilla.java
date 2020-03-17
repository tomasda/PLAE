package es.apt.ae.facade.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@NamedQueries({
	@NamedQuery(name = CatPlantilla.findById, query = "SELECT cp FROM CatPlantilla cp WHERE cp.id = :id"),
	@NamedQuery(name = CatPlantilla.findByActividad, query = "SELECT cp FROM CatPlantilla cp WHERE cp.procedimientoId =:procedimientoId and cp.actividadId = :actividadId"),
	@NamedQuery(name = CatPlantilla.findByActividadAndAuto, query = "SELECT cp FROM CatPlantilla cp WHERE cp.procedimientoId =:procedimientoId and cp.actividadId = :actividadId and cp.automatica = :auto"),
})
@Table(schema="OC3F", name = "CAT_TEMPLATES")
public class CatPlantilla implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String findById = "CatPlantilla.findById";
	public static final String findByActividad = "CatPlantilla.findByActividad";
	public static final String findByActividadAndAuto = "CatPlantilla.findByActividadAndAuto";
	
	@Id
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "ACTIVITY_ID")	
	private String actividadId;
	
	@Column(name = "PROC_ID")	
	private String procedimientoId;
	
	@Column(name = "TEMPLATE_NAME")	
	private String nombrePlantilla;
	
	@Column(name = "TEMPLATE_DESCRIPTION")
	private String descripcionPlantilla;
	
	@Column(name = "CONDITION")
	private String condicion;
	
	@Column(name = "AUTO")
	private Boolean automatica;
	
	@Column(name = "REQSIGN")
	private Boolean requiereFirma;
	
	@Column(name = "REQ_EXIT_RECORD")
	private Boolean requiereRegistroSalida;
	
	@Column(name = "REQ_NOTIFICATION")
	private Boolean requiereNotificacion;
	
//	@Column(name = "REPOSITORY_URI")
//	private String uri;
	
	@Column(name = "REPOSITORY_PATH")	
	private String rutaRepositorio;
	
//	@Column(name = "DOCUMENT_TYPE")
//	private String tipoDocumentoId;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "DOCUMENT_TYPE")
	private CatTipoDocumento tipoDocumento;
	
	@ManyToMany(cascade = { 
			CascadeType.PERSIST, 
			CascadeType.MERGE
	})
    @JoinTable(name = "CAT_TEMPLATE_TAG",
    	joinColumns = @JoinColumn(name = "TEMPLATE_ID"),
    	inverseJoinColumns = @JoinColumn(name = "TAG_ID")
    )
	private List<CatEtiqueta> etiquetas = new ArrayList<CatEtiqueta>();
	
	//private Integer uploadTemplateEpediente;
	
	@Column(name = "TEMPLATE_OUTPUT_TYPE")
	private String formatoDocSalida;
	
	@Transient
	private String departamento;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActividadId() {
		return actividadId;
	}

	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}

	public String getProcedimientoId() {
		return procedimientoId;
	}

	public void setProcedimientoId(String procedimientoId) {
		this.procedimientoId = procedimientoId;
	}

	public String getNombrePlantilla() {
		return nombrePlantilla;
	}

	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}

	public String getDescripcionPlantilla() {
		return descripcionPlantilla;
	}

	public void setDescripcionPlantilla(String descripcionPlantilla) {
		this.descripcionPlantilla = descripcionPlantilla;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public Boolean getAutomatica() {
		return automatica;
	}

	public void setAutomatica(Boolean automatica) {
		this.automatica = automatica;
	}

	public Boolean getRequiereFirma() {
		return requiereFirma;
	}

	public void setRequiereFirma(Boolean requiereFirma) {
		this.requiereFirma = requiereFirma;
	}

	public Boolean getRequiereRegistroSalida() {
		return requiereRegistroSalida;
	}

	public void setRequiereRegistroSalida(Boolean requiereRegistroSalida) {
		this.requiereRegistroSalida = requiereRegistroSalida;
	}

	public Boolean getRequiereNotificacion() {
		return requiereNotificacion;
	}

	public void setRequiereNotificacion(Boolean requiereNotificacion) {
		this.requiereNotificacion = requiereNotificacion;
	}

//	public String getUri() {
//		return uri;
//	}
//
//	public void setUri(String uri) {
//		this.uri = uri;
//	}

	public String getRutaRepositorio() {
		return rutaRepositorio;
	}

	public void setRutaRepositorio(String rutaRepositorio) {
		this.rutaRepositorio = rutaRepositorio;
	}

//	public String getTipoDocumentoId() {
//		return tipoDocumentoId;
//	}
//
//	public void setTipoDocumentoId(String tipoDocumentoId) {
//		this.tipoDocumentoId = tipoDocumentoId;
//	}

	public CatTipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(CatTipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<CatEtiqueta> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(List<CatEtiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public String getFormatoDocSalida() {
		return formatoDocSalida;
	}

	public void setFormatoDocSalida(String formatoDocSalida) {
		this.formatoDocSalida = formatoDocSalida;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
}

