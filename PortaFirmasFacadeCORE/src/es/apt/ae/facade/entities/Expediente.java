package es.apt.ae.facade.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import es.apt.ae.facade.entities.workflow.Act_ru_execution;
import es.apt.ae.facade.entities.workflow.Act_ru_task;
//import es.apt.ae.facade.expedientes.dto.TareaItem;

@Entity
@Transactional
@NamedQueries({ @NamedQuery(name = Expediente.findById, query = "SELECT e FROM Expediente e WHERE e.id = :id"),
		@NamedQuery(name = Expediente.findByIdAndActivo, query = "SELECT e FROM Expediente e WHERE e.id = :id AND e.estado = '0'"),
		@NamedQuery(name = Expediente.findByUri, query = "SELECT e FROM Expediente e WHERE e.uri = :uri"),
		@NamedQuery(name = Expediente.findByUrisGroup, query = "SELECT e FROM Expediente e WHERE e.uri IN (:urisGroup)"),
		@NamedQuery(name = Expediente.findByWfInstances, query = "SELECT e FROM Expediente e WHERE e.wfInstance.id IN (:instances)"),
		@NamedQuery(name = Expediente.findActivesByWfInstances, query = "SELECT e FROM Expediente e WHERE e.wfInstance.id IN (:instances) AND e.estado = '0'"),
		@NamedQuery(name = Expediente.findActivesByNumRegistro, query = "SELECT e FROM Expediente e WHERE e.numRegEnt = :numRegistro AND e.estado = '0' ORDER BY e.fechaInicio DESC") })
@Table(schema = "OC3F", name = "ADMINISTRATIVE_FILE_")
public class Expediente implements Serializable {
	private static final long serialVersionUID = -5092785111074045321L;

	public static final String findById = "Expediente.findById";
	public static final String findByIdAndActivo = "Expediente.findByIdAndActivo";
	public static final String findByUri = "Expediente.findByUri";
	public static final String findByUrisGroup = "Expediente.findByUrisGroup";
	public static final String findByWfInstances = "Expediente.findByWfInstances";
	public static final String findActivesByWfInstances = "Expediente.findActivesByWfInstances";
	public static final String findActivesByNumRegistro = "Expediente.findActivesByNumRegistro";

	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "NOTIFICATION_TYPE_")
	private String tipoNotificacion;
	@Column(name = "MANDATORY_NOTIFICATION_TYPE_")
	private Boolean notificacionesRecaudatorias;
	@Column(name = "CREATION_DATE_")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;	// En el caso de que provenga de un asiento es la fecha del mismo
	@Column(name = "START_DATE_")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;
	@Column(name = "END_DATE_")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;
	@Column(name = "RESOLUTION_NOTIF_DATE_")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaResolucion;
	@Column(name = "RESOLUTION_EFFECT_NOTIF_DATE_")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEfectivaResolucion;
	@Column(name = "ASUNTO_")
	private String asunto;
	@Column(name = "RECORD_NUMBER_")
	private String numRegEnt;
	@Column(name = "REPOSITORY_URI_")
	private String uri;
	@Column(name = "WF_ID_")
	private String idWf;
	@Column(name = "WF_VERSION_")
	private String workflowVersion;

	@Column(name = "OWNER_")
	private String dniInteresado;
	@Column(name = "INTERESADO_")
	private String nombreInteresado;

	@Column(name = "STATUS_")
	private Short estado;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "PENDING_WORKFLOW_ID_")
	// El name tiene que coincidir con el identificador real en bbdd de la
	// columna en base de datos que hace de clave ajena de la tabla relacionada.
	private Procedimiento procedimiento;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WF_INSTANCE_")
	private Act_ru_execution wfInstance;

	@OneToMany(targetEntity=TerceroExp.class, fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "ADMINISTRATIVE_FILE_ID_")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TerceroExp> terceros;

	@OneToMany(targetEntity=Documento.class)
	@JoinColumn(name = "ADMINISTRATIVE_FILE_ID_", insertable = false, updatable = false)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Documento> documentos;

	@Transient
	private String rutaRepositorio;
	
	@Transient
	private HashMap<String, Object> usuarioTramitadorInfo;

//	@Transient
//	private TareaItem tareaActual;

	public Expediente() {
	}

	public Expediente(String id, Procedimiento procedimiento, Date fechaCreacion, Date fechaInicio, String asunto, String numRegEnt,
			String uri, String idWf, String workflowVersion, Act_ru_execution wfInstance, String dniInteresado,
			String nombreInteresado, Short estado, String tipoNotificacion, Boolean notificacionesRecaudatorias) {
		super();
		this.id = id;
		this.tipoNotificacion = tipoNotificacion;
		this.notificacionesRecaudatorias = notificacionesRecaudatorias;
		this.fechaCreacion = fechaCreacion;
		this.fechaInicio = fechaInicio;
		this.asunto = asunto;
		this.numRegEnt = numRegEnt;
		this.uri = uri;
		this.idWf = idWf;
		this.workflowVersion = workflowVersion;
		this.dniInteresado = dniInteresado;
		this.nombreInteresado = nombreInteresado;
		this.estado = estado;
		this.procedimiento = procedimiento;
		this.wfInstance = wfInstance;
	}

	public String getIdWf() {
		return idWf;
	}

	public void setIdWf(String idWf) {
		this.idWf = idWf;
	}

	public String getWorkflowVersion() {
		return workflowVersion;
	}

	public void setWorkflowVersion(String workflowVersion) {
		this.workflowVersion = workflowVersion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TerceroExp> getTerceros() {
		return terceros;
	}

	public void setTerceros(List<TerceroExp> terceros) {
		this.terceros = terceros;
	}

	public String getTipoNotificacion() {
		return tipoNotificacion;
	}

	public void setTipoNotificacion(String tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}

	public Boolean getNotificacionesRecaudatorias() {
		return notificacionesRecaudatorias;
	}

	public void setNotificacionesRecaudatorias(Boolean notificacionesRecaudatorias) {
		this.notificacionesRecaudatorias = notificacionesRecaudatorias;
	}
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(Date fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public Date getFechaEfectivaResolucion() {
		return fechaEfectivaResolucion;
	}

	public void setFechaEfectivaResolucion(Date fechaEfectivaResolucion) {
		this.fechaEfectivaResolucion = fechaEfectivaResolucion;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getNumRegEnt() {
		return numRegEnt;
	}

	public void setNumRegEnt(String numRegEnt) {
		this.numRegEnt = numRegEnt;
	}

	public Procedimiento getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(Procedimiento procedimiento) {
		this.procedimiento = procedimiento;
	}

	public Act_ru_execution getWfInstance() {
		return wfInstance;
	}

	public void setWfInstance(Act_ru_execution wfInstance) {
		this.wfInstance = wfInstance;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDniInteresado() {
		return dniInteresado;
	}

	public void setDniInteresado(String dniInteresado) {
		this.dniInteresado = dniInteresado;
	}

	public String getNombreInteresado() {
		return nombreInteresado;
	}

	public void setNombreInteresado(String nombreInteresado) {
		this.nombreInteresado = nombreInteresado;
	}

	public Short getEstado() {
		return estado;
	}

	public void setEstado(Short estado) {
		this.estado = estado;
	}

	public String getRutaRepositorio() {
		return rutaRepositorio;
	}

	public void setRutaRepositorio(String rutaRepositorio) {
		this.rutaRepositorio = rutaRepositorio;
	}
	
	public HashMap<String, Object> getUsuarioTramitadorInfo() {
		return usuarioTramitadorInfo;
	}

	public void setUsuarioTramitadorInfo(HashMap<String, Object> usuarioTramitadorInfo) {
		this.usuarioTramitadorInfo = usuarioTramitadorInfo;
	}
	
//	public TareaItem getTareaActual() {
//		return tareaActual;
//	}
//
//	public void setTareaActual(TareaItem tareaActual) {
//		this.tareaActual = tareaActual;
//	}

	@Transient
	public void copy(Expediente exp) {
		this.id = exp.getId();
		this.tipoNotificacion = exp.getTipoNotificacion();
		this.notificacionesRecaudatorias = exp.getNotificacionesRecaudatorias();
		this.fechaCreacion = exp.getFechaCreacion();
		this.fechaInicio = exp.getFechaInicio();
		this.asunto = exp.getAsunto();
		this.numRegEnt = exp.getNumRegEnt();
		this.uri = exp.getUri();
		this.idWf = exp.getIdWf();
		this.workflowVersion = exp.getWorkflowVersion();
		this.dniInteresado = exp.getDniInteresado();
		this.nombreInteresado = exp.getNombreInteresado();
		this.estado = exp.getEstado();
		this.procedimiento = exp.getProcedimiento();
		this.wfInstance = exp.getWfInstance();
	}
	
	@Transient
	public Fase getFase() {
		Fase fase = null;
		if (estado.shortValue() != 1) { // Expediente no finalizado
			int faseId = -1;
			for (Act_ru_task task:wfInstance.getTasks()) {
				Actividad actividadActual = task.getActivity();
				int actividadActualFaseId = new Integer(actividadActual.getFase().getId()).intValue();
				if (actividadActualFaseId > faseId) {
					fase = actividadActual.getFase();
					faseId = actividadActualFaseId;
				}
			}
			
		}
		return fase;
	}

}
