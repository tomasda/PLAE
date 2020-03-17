package es.apt.ae.facade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = "Actividad.findByPk", query = "SELECT a FROM Actividad a WHERE a.id = :id AND a.wfId = :wfId AND a.wfVersion = :wfVersion"),
})
@Table(schema = "OC3F", name = "ACTIVITY_")
public class Actividad implements Serializable {
	private static final long serialVersionUID = 3689426230943420416L;

	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "WF_ID_")
	private String wfId;
	@Column(name = "WF_VERSION_")
	private String wfVersion;		
	@Column(name = "DESCRIPTION_")
	private String descripcion;
	@Column(name = "EXPIRATION_DAYS_")
	private String plazo;
	@Column(name = "ALERT_")
	private String alerta;
	@Column(name = "ALERT_CONDITION_")
	private String alertaCondicion;	
	@Column(name = "ALERT_DPTOS_")
	private String alertaDepartamentos;	
	@Column(name = "NOTIFICATIONS_")
	private String notificaciones;		
	

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "PHASE_")
	// El name tiene que coincidir con el identificador real en bbdd de la columna en base de datos que hace de clave ajena de la tabla relacionada.
	private Fase fase;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}

	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public String getWfVersion() {
		return wfVersion;
	}

	public void setWfVersion(String wfVersion) {
		this.wfVersion = wfVersion;
	}

	public String getPlazo() {
		return plazo;
	}

	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}

	public String getAlertaCondicion() {
		return alertaCondicion;
	}

	public void setAlertaCondicion(String alertaCondicion) {
		this.alertaCondicion = alertaCondicion;
	}

	public String getAlertaDepartamentos() {
		return alertaDepartamentos;
	}

	public void setAlertaDepartamentos(String alertaDepartamentos) {
		this.alertaDepartamentos = alertaDepartamentos;
	}

	public String getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(String notificaciones) {
		this.notificaciones = notificaciones;
	}

}
