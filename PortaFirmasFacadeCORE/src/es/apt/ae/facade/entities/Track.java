package es.apt.ae.facade.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = "Track.findById", query = "SELECT t FROM Track t WHERE t.pk.idEntidad = :idEntidad AND t.pk.actividad = :actividadId AND t.pk.wfId = :wfId AND t.pk.wfVersion = :wfVersion AND t.pk.iteracion = :iteracion"),
	@NamedQuery(name = "Track.findByIdEntidad", query = "SELECT t FROM Track t WHERE t.pk.idEntidad = :idEntidad ORDER BY t.fechaCreacion ASC"),
	@NamedQuery(name = "Track.findIterations", query = "SELECT t FROM Track t WHERE t.pk.idEntidad = :idEntidad AND t.pk.actividad = :actividadId AND t.pk.wfId = :wfId AND t.pk.wfVersion = :wfVersion ORDER BY t.pk.iteracion DESC")
})
@Table(schema = "OC3F", name = "TRACK_")
public class Track implements Serializable {
	private static final long serialVersionUID = 3689426230943420416L;

	@EmbeddedId
	private TrackPk pk;
	@Column(name = "OWNER_")
	private String tramitador;
	@Column(name = "ASSIGNER_")
	private String asignador;	
	@Column(name = "ROLE_NAME_")
	private String departamento;
	@Column(name = "CREATION_DATE_")
	private Date fechaCreacion;	
	@Column(name = "START_DATE_")
	private Date fechaInicio;
	@Column(name = "END_DATE_")
	private Date fechaFin;

	
	public Track() {
		super();
	}

	public Track(TrackPk pk, String tramitador, String departamento, Date fechaCreacion,
			Date fechaInicio, Date fechaFin) {
		super();
		this.pk = pk;
		this.tramitador = tramitador;
		this.departamento = departamento;
		this.fechaCreacion = fechaCreacion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}
	
	public Track(TrackPk pk, String tramitador, String asignador, String departamento,
			Date fechaCreacion, Date fechaInicio, Date fechaFin) {
		super();
		this.pk = pk;
		this.tramitador = tramitador;
		this.asignador = asignador;
		this.departamento = departamento;
		this.fechaCreacion = fechaCreacion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}
	
	public TrackPk getPk() {
		return pk;
	}
	public void setPk(TrackPk pk) {
		this.pk = pk;
	}
	public String getTramitador() {
		return tramitador;
	}
	public void setTramitador(String tramitador) {
		this.tramitador = tramitador;
	}
	public String getAsignador() {
		return asignador;
	}
	public void setAsignador(String asignador) {
		this.asignador = asignador;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
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
	
	
}
