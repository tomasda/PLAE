package es.apt.ae.facade.entities.callback;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "CallbackTimer.findById", query = "SELECT ct FROM CallbackTimer ct WHERE ct.id = :id"), 
	@NamedQuery(name = "CallbackTimer.getAll", query = "SELECT ct FROM CallbackTimer ct"),
	@NamedQuery(name = "CallbackTimer.getAllByTipo", query = "SELECT ct FROM CallbackTimer ct WHERE ct.tipo = :tipo"),
})
@Table(name = "CALLBACK_TIMER")
public class CallbackTimer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;
	@Column(name = "ENTIDAD_ID")
	private String entidadId;
	@Column(name = "HASH")
	private String hash;
	@Column(name = "INTENTOS")
	private int intentos;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "DNI")
	private String dni;
	@Column(name = "ACTOR_DNI")
	private String actorDni;
	
	@Column(name = "ESTADO")
	private String estado;
	@Column(name = "OBSERVACIONES")
	private String observaciones;
	@Column(name = "FECHA")
	private Date fecha;
	@Column(name="ALERTA_ENVIADA")
	private boolean alertaEnviada;
	@Column(name="ESTADO_INTERMEDIO")
	private boolean estadoIntermedio;	
	
	@Column(name = "WSDL")
	private String wsdl;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@Transient
	private String documentUri;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEntidadId() {
		return entidadId;
	}

	public void setEntidadId(String entidadId) {
		this.entidadId = entidadId;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int getIntentos() {
		return intentos;
	}

	public void setIntentos(int intentos) {
		this.intentos = intentos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getWsdl() {
		return wsdl;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}

	public boolean isAlertaEnviada() {
		return alertaEnviada;
	}

	public void setAlertaEnviada(boolean alertaEnviada) {
		this.alertaEnviada = alertaEnviada;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getActorDni() {
		return actorDni;
	}

	public void setActorDni(String actorDni) {
		this.actorDni = actorDni;
	}

	public boolean isEstadoIntermedio() {
		return estadoIntermedio;
	}

	public void setEstadoIntermedio(boolean estadoIntermedio) {
		this.estadoIntermedio = estadoIntermedio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDocumentUri() {
		return documentUri;
	}

	public void setDocumentUri(String documentUri) {
		this.documentUri = documentUri;
	}

}
