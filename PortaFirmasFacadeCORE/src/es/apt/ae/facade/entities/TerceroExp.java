package es.apt.ae.facade.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@NamedQueries({
	@NamedQuery(name = "TerceroExp.findById", query = "SELECT t FROM TerceroExp t WHERE t.id = :id")
})
@Table(schema = "OC3F", name = "THIRD_PARTY_")
public class TerceroExp implements Serializable {
	private static final long serialVersionUID = -853997522325662636L;

	/*************************************
	 * Tipo de persona
	 *************************************/
	public static final String PERSONA_FISICA = "1";
	public static final String PERSONA_JURIDICA = "2";

	/*************************************
	 * Tipo de relación con el expediente
	 *************************************/
	public static final String INTERESADO = "1";
	public static final String REPRESENTANTE = "2";
	
	/*************************************
	 * Tipo de notificación
	 *************************************/
	public static final HashMap<String, String> TIPO_NOTIFICACION_MAP = new HashMap<String, String>();
    static {
    	TIPO_NOTIFICACION_MAP.put("01", "papel");
    	TIPO_NOTIFICACION_MAP.put("02", "deh");
    	TIPO_NOTIFICACION_MAP.put("03", "telematica");
    }
	
	
	@Id
    @GeneratedValue(generator="SEQ_THIRDPARTY")
    @SequenceGenerator(schema="OC3F",
    		name="SEQ_THIRDPARTY", sequenceName="SEQ_THIRDPARTY", allocationSize=1
    )
	private Long id;
	@Column(name = "COMMON_NAME_")
	private String identificacion;	
	@Column(name = "TYPE_")
	private String tipoTercero;
	@Column(name = "RELATION_TYPE_")
	private String tipoRelacion;	
	@Column(name = "DOCUMENT_TYPE_")
	private String tipoDocumento;		
	@Column(name = "NAME_")
	private String nombre;
	@Column(name = "SURNAME1_")
	private String apellido1;
	@Column(name = "SURNAME2_")
	private String apellido2;
	@Column(name = "EMAIL_")
	private String email;
	@Column(name = "NIF_")
	private String nif;
	@Column(name = "CIF_")
	private String cif;	
	@Column(name = "BUSINESS_NAME_")
	private String razonSocial;
	@Column(name = "TELEPHONE_")
	private String telefono;
	@Column(name = "STREET_")
	private String calle;
	@Column(name = "ZIP_")
	private String codigoPostal;
	@Column(name = "LOCALITY_")
	private String localidad;
	@Column(name = "PROVINCE_")
	private String provincia;	
	@Column(name = "ADDRESS_")
	private String direccion;
	@Column(name = "COUNTRY_")
	private String pais;
	@Column(name = "MANDATORY_NOTIFICATION_TYPE_")
	private Boolean notificacionesRecaudatorias;
	@Column(name = "NOTIFICATION_TYPE_")
	private String tipoNotificacion;	
	@Column(name = "DELETED_")
	private Boolean cancelado;
	@Column(name = "DELETE_DATE_")
	private Date fechaCancelacion;
	@Column(name = "ADMINISTRATIVE_FILE_ID_")
	private String numExpediente;
	
	
	
	public TerceroExp() {
		super();
	}

	public TerceroExp(String identificacion, String tipoTercero,
			String tipoRelacion, String tipoDocumento, String nombre, String apellido1,
			String apellido2, String email, String nif, String cif,
			String razonSocial, String telefono, String calle,
			String codigoPostal, String localidad, String provincia,
			String direccion, String pais, Boolean notificacionesRecaudatorias,
			String tipoNotificacion, Boolean cancelado, Date fechaCancelacion,
			String numExpediente) {
		super();
		this.identificacion = identificacion;
		this.tipoTercero = tipoTercero;
		this.tipoRelacion = tipoRelacion;
		this.tipoDocumento = tipoDocumento;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.email = email;
		this.nif = nif;
		this.cif = cif;
		this.razonSocial = razonSocial;
		this.telefono = telefono;
		this.calle = calle;
		this.codigoPostal = codigoPostal;
		this.localidad = localidad;
		this.provincia = provincia;
		this.direccion = direccion;
		this.pais = pais;
		this.notificacionesRecaudatorias = notificacionesRecaudatorias;
		this.tipoNotificacion = tipoNotificacion;
		this.cancelado = cancelado;
		this.fechaCancelacion = fechaCancelacion;
		this.numExpediente = numExpediente;
	}
	
	
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getTipoTercero() {
		return tipoTercero;
	}
	public void setTipoTercero(String tipoTercero) {
		this.tipoTercero = tipoTercero;
	}
	public String getTipoRelacion() {
		return tipoRelacion;
	}
	public void setTipoRelacion(String tipoRelacion) {
		this.tipoRelacion = tipoRelacion;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public Boolean getNotificacionesRecaudatorias() {
		return notificacionesRecaudatorias;
	}
	public void setNotificacionesRecaudatorias(Boolean notificacionesRecaudatorias) {
		this.notificacionesRecaudatorias = notificacionesRecaudatorias;
	}
	public String getTipoNotificacion() {
		return tipoNotificacion;
	}
	public void setTipoNotificacion(String tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}
	public Boolean getCancelado() {
		return cancelado;
	}
	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}
	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}
	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}
	public String getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}	
	
	@Transient
	public String getNombreCompleto() {
		String nombreCompleto = "";
		if (this.nombre != null) {
			nombreCompleto = this.nombre + ((this.apellido1!=null)?(" " + this.apellido1):"") + 
				((this.apellido2!=null)?(" " + this.apellido2):"");
		} else {
			nombreCompleto = this.razonSocial;
		}
		
		return nombreCompleto;
	}
}
