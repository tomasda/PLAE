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
	@NamedQuery(name = "CatPermisoDispositivo.findPermisosDispositivoByDestinatarioAndTipo", query = "SELECT cpd FROM CatPermisoDispositivo cpd WHERE "
			+ "(cpd.destinatarioTipo = '" + CatPermisoDispositivo.TIPO_DESTINATARIO_USUARIO + "' and cpd.destinatarioId =:usuarioId) or "
			+ "(cpd.destinatarioTipo = '" + CatPermisoDispositivo.TIPO_DESTINATARIO_DEPARTAMENTO + "' and cpd.destinatarioId in :departamentosIds) and cpd.permisoTipo = :permisoTipo"),
	@NamedQuery(name = "CatPermisoDispositivo.findPermisosDispositivoByTipo", query = "SELECT cpd FROM CatPermisoDispositivo cpd WHERE cpd.permisoTipo = :permisoTipo"),
})
@Table(schema="OC3F", name = "CAT_PERMISOS_DISPOSITIVOS")
public class CatPermisoDispositivo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String TIPO_DESTINATARIO_DEPARTAMENTO = "DEPARTAMENTO";
	public static final String TIPO_DESTINATARIO_USUARIO = "USUARIO";
	public static final String TIPO_PERMISO_FIRMA_MANUSCRITA = "FIRMA_MANUSCRITA";
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "DESTINATARIO_ID")
	private String destinatarioId;
	
	@Column(name = "DESTINATARIO_TIPO")
	private String destinatarioTipo;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "DISPOSITIVO_ID")
	private CatDispositivo dispositivo;
	
	@Column(name = "PERMISO_TIPO")
	private String permisoTipo;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDestinatarioId() {
		return destinatarioId;
	}
	public void setDestinatarioId(String destinatarioId) {
		this.destinatarioId = destinatarioId;
	}
	public String getDestinatarioTipo() {
		return destinatarioTipo;
	}
	public void setDestinatarioTipo(String destinatarioTipo) {
		this.destinatarioTipo = destinatarioTipo;
	}
	public String getPermisoTipo() {
		return permisoTipo;
	}
	public void setPermisoTipo(String permisoTipo) {
		this.permisoTipo = permisoTipo;
	}
	public CatDispositivo getDispositivo() {
		return dispositivo;
	}
	public void setDispositivo(CatDispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}
	
}
