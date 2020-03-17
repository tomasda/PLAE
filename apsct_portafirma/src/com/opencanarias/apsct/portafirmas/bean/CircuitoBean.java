package com.opencanarias.apsct.portafirmas.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.portafirmas.utils.CatalogosUtils;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.apsct.portafirmas.utils.Services;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

import es.apt.ae.facade.entities.portafirmas.CircuitoEntity;
import es.apt.ae.facade.entities.portafirmas.Grupo;
import es.apt.ae.facade.entities.portafirmas.Persona;
import es.apt.ae.facade.entities.portafirmas.TipoGrupo;

@Named
@FacesConfig
@SessionScoped
public class CircuitoBean implements Serializable{

	private static final long serialVersionUID = 8558744138104190052L;
	private CircuitoEntity circuito = new CircuitoEntity();
	private List<CircuitoEntity> listCircuitosPredefinidos = new ArrayList<CircuitoEntity>();
	private List<Persona> listPersonas = new ArrayList<Persona>();
	private List<Persona> listPersonasSeleccionadas = new ArrayList<Persona>();
	private List<TipoGrupo> tiposGrupo = new ArrayList<TipoGrupo>();
	private TipoGrupo tipoGrupoTry = new TipoGrupo();

	protected static final Logger logger = Logger.getLogger(CircuitoBean.class);

	
	public CircuitoBean() {
	}
	
	// TODO: Revisar los update y ciclos de vida por las recursivas cargas de
	// elementos
	public CircuitoEntity getCircuito() {
		if (circuito.getListGrupo() == null) {
			circuito.setListGrupo(new ArrayList<Grupo>());
			Grupo grupo = new Grupo();
			grupo.setOrden(0);
			UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
			
			try {
				grupo.setTipoGrupo(CatalogosUtils.getTipoGrupoByCodigo("FIRM"));
				circuito.getListGrupo().add(grupo);
				circuito.setTipoCircuito(CatalogosUtils.getTipoCircuitoByCodigo("GNRD"));
			} catch (PortafirmasFacadeException e) {
				LoggerUtils.showError(logger, Constantes.ERROR_GET_TYPE_GROUP, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			}
		}
		return circuito;
	}

	public void setCircuito(CircuitoEntity circuito) {
		this.circuito = circuito;
	}

	public void setListCircuitosPredefinidos(
			List<CircuitoEntity> listCircuitosPredefinidos) {
		this.listCircuitosPredefinidos = listCircuitosPredefinidos;
	}

	public List<CircuitoEntity> getListCircuitosPredefinidos() {
		if (listCircuitosPredefinidos.size() == 0) {
			UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
			try {
				listCircuitosPredefinidos = Services.getSrvPortafirmasFacadeRemote()
						.consultaCircuitosPorTipoYSolicitante("PRDF", usuarioBean.getNumIdentificacion());
			} catch (PortafirmasFacadeException e) {
				LoggerUtils.showError(logger, Constantes.ERROR_GET_LIST_CIRCUITS, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			}
		}
		return listCircuitosPredefinidos;
	}

	public List<Persona> getListPersonas() {
		if (listPersonas.size() == 0) {
			UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
			try {
				listPersonas = Services.getSrvPortafirmasFacadeRemote().consultarFirmantesEJB(usuarioBean.getNumIdentificacion(), usuarioBean.isUsuarioPruebas());
			} catch (PortafirmasFacadeException e) {
				LoggerUtils.showError(logger, Constantes.ERROR_GET_LIST_PERSONS, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			}
		}
		return listPersonas;
	}

	public void setListPersonas(List<Persona> listPersona) {
		this.listPersonas = listPersona;
	}

	public List<Persona> getListPersonasSeleccionadas() {
		return listPersonasSeleccionadas;
	}

	public void setListPersonasSeleccionadas(
			List<Persona> listPersonasSeleccionadas) {
		this.listPersonasSeleccionadas = listPersonasSeleccionadas;
	}

	public TipoGrupo getTipoGrupoTry() {
		return tipoGrupoTry;
	}

	public void setTipoGrupoTry(TipoGrupo tipoGrupoTry) {
		this.tipoGrupoTry = tipoGrupoTry;
	}

	/**
	 * @param tiposGrupo
	 *            the tiposGrupo to set
	 */
	public void setTiposGrupo(List<TipoGrupo> tiposGrupo) {
		this.tiposGrupo = tiposGrupo;
	}

	public List<TipoGrupo> getTiposGrupo() {

		if (tiposGrupo.size() == 0) {
			UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);

			try {
				return CatalogosUtils.getListTipoGrupo();
			} catch (PortafirmasFacadeException e) {
				LoggerUtils.showError(logger, Constantes.ERROR_GET_TYPE_GROUP, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			}
		}
		return tiposGrupo;
	}

}
