package com.opencanarias.apsct.portafirmas.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.opencanarias.api.security.UserPrincipal;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;
import com.opencanarias.apsct.portafirmas.utils.CatalogosUtils;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.apsct.portafirmas.utils.Services;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.exceptions.GenericFacadeException;
import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.utils.StringUtils;

import es.apt.ae.facade.dto.UsuarioItem;
import es.apt.ae.facade.entities.portafirmas.Ausencia;
import es.apt.ae.facade.entities.portafirmas.Persona;

@Named
@SessionScoped
public class UsuarioBean implements Serializable{
	
	private static final long serialVersionUID = -796489608974652537L;

	private static Logger logger = Logger.getLogger(UsuarioBean.class);
	
	private Long id;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String numIdentificacion;
	private String username;
	private String mail;
	private Persona entidadPersona;
	private boolean usuarioPruebas;
	private boolean administrador;
	private boolean usuarioConsulta;
	private List<Persona> listRevisiones;
	private List<Persona> listSustitucion;

	public void create() {
		
		if (StringUtils.isNullOrEmpty(username)) {
			ValidacionUsuarioBean validationUserBean = (ValidacionUsuarioBean) FacesUtils.resolveVariable(Constantes.VALIDACION_USUARIO_BEAN);
			if (validationUserBean == null){
				validationUserBean = new ValidacionUsuarioBean();
			}
			validationUserBean.getValid();
		}
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		UserPrincipal principal = (UserPrincipal) context.getUserPrincipal();
		
		if (principal != null) {
			numIdentificacion = principal.getName();
			usuarioConsulta = true;
			
			try {
				entidadPersona = Services.getSrvPortafirmasFacadeRemote().getPersona(numIdentificacion);//Services.getSrv_portafirmas_facade().getPersona(numIdentificacion);
				if (entidadPersona != null) {
					nombre = entidadPersona.getNombre();
					id = entidadPersona.getId();
					apellido1 = entidadPersona.getApellido1();
					apellido2 = entidadPersona.getApellido2();
					mail = entidadPersona.getMail();
					usuarioPruebas = entidadPersona.getUsuarioPruebas();
					username = principal.getUsername();
					setListRevisiones(Services.getSrvPortafirmasFacadeRemote().getListRevisoresDe(numIdentificacion));
					setListSustitucion(Services.getSrvPortafirmasFacadeRemote().getSustitucionByDNI(numIdentificacion));
					AusenciaBean ausenciaBean = (AusenciaBean) FacesUtils.getSessionBean(Constantes.AUSENCIA_BEAN);
					Ausencia ausencia = Services.getSrvPortafirmasFacadeRemote().getPersonaAusencia(numIdentificacion);
//					setListRevisiones(Services.getSrv_portafirmas_facade().getListRevisoresDe(numIdentificacion));
//					setListSustitucion(Services.getSrv_portafirmas_facade().getSustitucionByDNI(numIdentificacion));
//					AusenciaBean ausenciaBean = (AusenciaBean) FacesUtils.getSessionBean(Constantes.AUSENCIA_BEAN);
//					Ausencia ausencia = Services.getSrv_portafirmas_facade().getPersonaAusencia(numIdentificacion);
					if (ausencia != null) {
						ausenciaBean.setSeleccionPersona(ausencia.getSustitucion().getPersona().getNumIdentificacion());
						ausenciaBean.setAusenciaActiva(true);
						ausenciaBean.setAusenciaEntity(ausencia);
					} else {
						ausenciaBean = new AusenciaBean();
						ausenciaBean.setSeleccionPersona(null);
						ausenciaBean.setAusenciaActiva(false);
						ausenciaBean.setAusenciaEntity(null);
					}
				} else {
					id = null;
					nombre = principal.getNombre();
					//El nombre ya trae los apellidos
					apellido1 = null;//principal.getApellido(); 
					mail = principal.getEmail();
					usuarioPruebas = principal.isUsuarioPruebas();
					username = principal.getUsername();
				}
				
				// Determinar si el usuario es Administrador.
				if (principal.getRoles() != null) {
					for (String rol:principal.getRoles()) {
						if ("SuperAdministrador".equals(rol)) {
							username = null;
							administrador = true;
							usuarioConsulta = false;
							break;
						}
					}
				}
				CatalogosUtils.resetListPropiedadesConfiguracion();
			} catch (PortafirmasFacadeException e) {
				LoggerUtils.showError(logger, Constantes.ERROR_LOAD_USER, e, FacadeBean.USUARIO_PORTAFIRMAS, principal.getUsername());
			}
		}
	}
	
	public void setUserToConsult(UsuarioItem usuarioItem) throws PortafirmasFacadeException {
		usuarioConsulta = false;
		
		if (usuarioItem != null) {
			numIdentificacion = usuarioItem.getIdentificacion();

			try {
				entidadPersona = Services.getSrvPortafirmasFacadeRemote().getPersona(numIdentificacion);
				if (entidadPersona != null) {
					nombre = entidadPersona.getNombre();
					id = entidadPersona.getId();
					apellido1 = entidadPersona.getApellido1();
					apellido2 = entidadPersona.getApellido2();
					mail = entidadPersona.getMail();
					usuarioPruebas = entidadPersona.getUsuarioPruebas();
					username = usuarioItem.getUsername();
	
					setListRevisiones(Services.getSrvPortafirmasFacadeRemote().getListRevisoresDe(numIdentificacion));
					setListSustitucion(Services.getSrvPortafirmasFacadeRemote().getSustitucionByDNI(numIdentificacion));
					AusenciaBean ausenciaBean = (AusenciaBean) FacesUtils.getSessionBean(Constantes.AUSENCIA_BEAN);
					Ausencia ausencia = Services.getSrvPortafirmasFacadeRemote().getPersonaAusencia(numIdentificacion);
					if (ausencia != null) {
						ausenciaBean.setSeleccionPersona(ausencia.getSustitucion().getPersona().getNumIdentificacion());
						ausenciaBean.setAusenciaActiva(true);
						ausenciaBean.setAusenciaEntity(ausencia);
					} else {
						ausenciaBean = new AusenciaBean();
						ausenciaBean.setSeleccionPersona(null);
						ausenciaBean.setAusenciaActiva(false);
						ausenciaBean.setAusenciaEntity(null);
					}
					usuarioConsulta = true;
				} else {
					id = null;
					nombre = usuarioItem.getNombreApellidos();
					apellido1 = null;
					apellido2 = null;
					mail = usuarioItem.getCorreoElectronico();
					// TODO: Es usuario pruebas.
					//usuarioPruebas = principal.isUsuarioPruebas();
					username = usuarioItem.getUsername();
					usuarioConsulta = true;
				}
			} catch (PortafirmasFacadeException e) {
				throw e;
			}
		} else {
			throw new PortafirmasFacadeException();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		if (nombre == null) {
			create();
		}
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

	public String getNumIdentificacion() {
		return numIdentificacion;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setNumIdentificacion(String numIdentificacion) {
		this.numIdentificacion = numIdentificacion;
	}

	public Persona getEntidadPersona() {
		return entidadPersona;
	}

	public void setEntidadPersona(Persona entidadPersona) {
		this.entidadPersona = entidadPersona;
	}
	
	public boolean isUsuarioPruebas() {
		return usuarioPruebas;
	}

	public void setUsuarioPruebas(boolean usuarioPruebas) {
		this.usuarioPruebas = usuarioPruebas;
	}
	
	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}
	
	public boolean isUsuarioConsulta() {
		return usuarioConsulta;
	}

	public void setUsuarioConsulta(boolean usuarioConsulta) {
		this.usuarioConsulta = usuarioConsulta;
	}

	public List<Persona> getListRevisiones() {
		return listRevisiones;
	}

	public void setListRevisiones(List<Persona> listRevisiones) {
		this.listRevisiones = listRevisiones;
	}

	public List<Persona> getListSustitucion() {
		return listSustitucion;
	}

	public void setListSustitucion(List<Persona> listSustitucion) {
		this.listSustitucion = listSustitucion;
	}

	public void destroy() {
		id = null;
		nombre = null;
		apellido1 = null;
		apellido2 = null;
		numIdentificacion = null;
		entidadPersona = null;
		listRevisiones = null;
		listSustitucion = null;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public List<UsuarioItem> getListUsuariosAE() {
		try {
			return CatalogosUtils.getListUsuariosAE();
		} catch (GenericFacadeException e){
			LoggerUtils.showError(logger, "No se ha podido recuperar el listado de usuarios de AE", e, 
					FacadeBean.USUARIO_PORTAFIRMAS, username);
		}
		return null;
	}

}
