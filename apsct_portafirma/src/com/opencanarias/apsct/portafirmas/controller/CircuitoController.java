package com.opencanarias.apsct.portafirmas.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jboss.logging.Logger;
import org.primefaces.event.DragDropEvent;

import com.opencanarias.apsct.portafirmas.bean.CircuitoBean;
import com.opencanarias.apsct.portafirmas.bean.CommonBean;
import com.opencanarias.apsct.portafirmas.bean.DocumentosBean;
import com.opencanarias.apsct.portafirmas.bean.UsuarioBean;
import com.opencanarias.apsct.portafirmas.utils.CatalogosUtils;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.apsct.portafirmas.utils.Services;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

import es.apt.ae.facade.entities.portafirmas.CircuitoEntity;
import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;
import es.apt.ae.facade.entities.portafirmas.Grupo;
import es.apt.ae.facade.entities.portafirmas.GrupoPersona;
import es.apt.ae.facade.entities.portafirmas.Persona;
import es.apt.ae.facade.ws.params.commons.in.Credenciales;
import es.apt.ae.facade.ws.params.commons.in.ListaDocumentos;
import es.apt.ae.facade.ws.params.commons.out.Respuesta;
import es.apt.ae.facade.ws.params.portafirmas.common.InformacionSolicitante;
import es.apt.ae.facade.ws.params.portafirmas.common.Solicitante;
import es.apt.ae.facade.ws.params.portafirmas.common.TipoCircuito;
import es.apt.ae.facade.ws.params.portafirmas.common.TipoGrupo;
import es.apt.ae.facade.ws.params.portafirmas.in.Parametros;
import es.apt.ae.facade.ws.params.portafirmas.in.alta.AltaDocsPortafirmas;
import es.apt.ae.facade.ws.params.portafirmas.in.alta.Circuito;
import es.apt.ae.facade.ws.params.portafirmas.in.alta.TipoFirmaEnum;
import es.apt.ae.facade.ws.params.portafirmas.out.Resultado;

@ManagedBean
public class CircuitoController implements Serializable{

	private static final long serialVersionUID = 1767648367835344512L;
	protected static final Logger logger = Logger.getLogger(CircuitoController.class);

	private Persona buscarPersonaEnLista(String numIdentificacion, List<Persona> list) {
		for (Persona personaAux : list) {
			if (personaAux.getNumIdentificacion().equals(numIdentificacion)) {
				return personaAux;
			}
		}
		return null;
	}

	private GrupoPersona buscarPersonaEnGrupo(String numIdentificacion, List<GrupoPersona> list) {
		for (GrupoPersona grupoPersonaAux : list) {
			if (grupoPersonaAux.getPersona().getNumIdentificacion().equals(numIdentificacion)) {
				return grupoPersonaAux;
			}
		}
		return null;
	}

	private boolean buscarPersonaGrupo(String idPersona) {
		CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		CircuitoBean circuitoBean = (CircuitoBean) FacesUtils.getSessionBean(Constantes.CIRCUITO_BEAN);
		for (Grupo grupoAux : circuitoBean.getCircuito().getListGrupo()) {
			for (GrupoPersona gruoPersona : grupoAux.getGrupoPersona()) {
				Persona personaAux = gruoPersona.getPersona();
				if (idPersona.equals(personaAux.getId().toString())) {
					String info = Constantes.WARN_SELECTED_PERSON_EXIST;
					commonBean.setMessage(info);
					FacesUtils.verDialog("messageDialog");
					return true;
				}
			}
		}
		return false;
	}

	private int getFirmantesRequeridos(Grupo grupo) {
		int cantidadFirmantes = 0;
		for (GrupoPersona grupoPersona : grupo.getGrupoPersona())
			if (grupoPersona.isObligatorio()) {
				cantidadFirmantes++;
			}
		return cantidadFirmantes;
	}

	private Grupo buscarGrupo(int orden, List<Grupo> listGrupo) {
		for (Grupo grupo : listGrupo) {
			if (orden == grupo.getOrden()) {
				return grupo;
			}
		}
		return null;
	}

	public void createGroup(ActionEvent ev) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, Constantes.INFO_CREATE_GROUP, 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		CircuitoBean circuitoBean = (CircuitoBean) FacesUtils.getSessionBean(Constantes.CIRCUITO_BEAN);
		Grupo grupoAux = new Grupo();
		try {
			grupoAux.setTipoGrupo(CatalogosUtils.getTipoGrupoByCodigo(Constantes.FIRM));
		} catch (PortafirmasFacadeException e) {
			String message = Constantes.ERROR_GET_TYPE_GROUP;
			CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
			commonBean.setMessage(message);
			FacesUtils.verDialog("messageDialog");
			LoggerUtils.showError(logger, message, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		}
		grupoAux.setOrden(circuitoBean.getCircuito().getListGrupo().size());
		circuitoBean.getCircuito().getListGrupo().add(grupoAux);
		LoggerUtils.showInfo(logger, Constantes.INFO_CREATE_GROUP_SUCCESS, 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
	}

	public void onDrop(DragDropEvent event) {
		String id = event.getDropId();
		String[] idList = id.split(":");
		int grupoPosicion = Integer.parseInt(idList[2]);

		CircuitoBean circuitoBean = (CircuitoBean) FacesUtils.getSessionBean(Constantes.CIRCUITO_BEAN);
		circuitoBean.getListPersonasSeleccionadas().clear();
		Persona persona = (Persona) event.getData();
		if (!buscarPersonaGrupo(persona.getId().toString())) {
			for (Grupo grupo : circuitoBean.getCircuito().getListGrupo()) {
				Integer orden;
				if (circuitoBean.getCircuito().getTipoCircuito().getCodigo().equals(Constantes.PRDF.toString())) {
					orden = grupo.getOrden() - 1;
				} else {
					orden = grupo.getOrden();
				}
				if (orden == grupoPosicion) {
					GrupoPersona grupoPersona = new GrupoPersona();
					grupoPersona.setPersona(persona);
					grupo.getGrupoPersona().add(grupoPersona);
					grupo.setFirmantesRequeridos(grupo.getGrupoPersona().size());
					break;
				}
			}
		}
	}

	public void borrarGrupo(ActionEvent ev) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, Constantes.INFO_DELETE_GROUP, 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		CircuitoBean circuitoBean = (CircuitoBean) FacesUtils.getSessionBean(Constantes.CIRCUITO_BEAN);
		int orden = Integer.parseInt(FacesUtils.getParam(Constantes.GROUP_ID_DELETE));
		Grupo grupo = buscarGrupo(orden, circuitoBean.getCircuito().getListGrupo());
		circuitoBean.getCircuito().getListGrupo().remove(grupo);
		LoggerUtils.showInfo(logger, Constantes.INFO_DELETE_GROUP_SUCCESS, 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
	}

	public void addPersonasSeleccionadas(ActionEvent ev) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "A�adiendo personas seleccionadas a un grupo", 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		CircuitoBean circuitoBean = (CircuitoBean) FacesUtils.getSessionBean(Constantes.CIRCUITO_BEAN);
		int orden = Integer.parseInt(FacesUtils.getParam("grupoIdAddPersonas"));
		Grupo grupo = buscarGrupo(orden, circuitoBean.getCircuito().getListGrupo());
		for (Persona persona : circuitoBean.getListPersonasSeleccionadas()) {
			GrupoPersona grupoPersonaAux = new GrupoPersona();
			grupoPersonaAux.setPersona(persona);
			grupo.getGrupoPersona().add(grupoPersonaAux);
		}

		LoggerUtils.showInfo(logger, "Persona a�adida correctamente", 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		circuitoBean.getListPersonasSeleccionadas().clear();
		LoggerUtils.showInfo(logger, "Se editan los l�mites del grupo", 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		grupo.setFirmantesRequeridos(grupo.getGrupoPersona().size());
	}

	public void eliminarPersonaGrupo(ActionEvent ev) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Eliminando de un grupo", 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		CircuitoBean circuitoBean = (CircuitoBean) FacesUtils.getSessionBean(Constantes.CIRCUITO_BEAN);
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String personaSeleccionada = params.get(Constantes.SELECTED_PERSON);
		int orden = Integer.parseInt(FacesUtils.getParam("grupoIdEliminarPersona"));
		Grupo grupo = buscarGrupo(orden, circuitoBean.getCircuito().getListGrupo());

		for (GrupoPersona grupoPersona : grupo.getGrupoPersona()) {
			if (grupoPersona.getPersona().getNumIdentificacion().equals(personaSeleccionada)) {
				grupo.getGrupoPersona().remove(grupoPersona);
				LoggerUtils.showInfo(logger, "Persona eliminada correctamente", 
						FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
				grupo.setFirmantesRequeridos(grupo.getGrupoPersona().size());
				LoggerUtils.showInfo(logger, "Limites del grupo editados", 
						FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
				break;
			}
		}
		grupo.setFirmantesRequeridos(grupo.getGrupoPersona().size());

	}

	/*
	 * NOTA IMPORTANTE PARA seleccionarPersona:
	 * 
	 * Se comprueba que esta persona no exista en ningun grupo aqui para optimizar rendimiento y porque solo se seleccionan personas para a�adir a un grupo, de modificarse la funcionalidad, se debe
	 * realizar esta misma comprobaci�n posteriormente en los m�todos espec�ficos
	 */
	public void seleccionarPersona(ActionEvent ev) {
		CircuitoBean circuitoBean = (CircuitoBean) FacesUtils.getSessionBean(Constantes.CIRCUITO_BEAN);
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String personaSeleccionada = params.get(Constantes.SELECTED_PERSON);
		Persona personaAux = buscarPersonaEnLista(personaSeleccionada, circuitoBean.getListPersonas());

		if (!buscarPersonaGrupo(personaAux.getId().toString())) {
			if (personaAux != null) {
				if (buscarPersonaEnLista(personaSeleccionada, circuitoBean.getListPersonasSeleccionadas()) == null) {
					circuitoBean.getListPersonasSeleccionadas().add(personaAux);
				} else {
					circuitoBean.getListPersonasSeleccionadas().remove(personaAux);
				}
			}
		} else {
			circuitoBean.getListPersonasSeleccionadas().remove(personaAux);
			System.out.println("La persona seleccionada se encuentra en un grupo");
		}
	}

	public void liberarPersonaGrupo(ActionEvent ev) {
		CircuitoBean circuitoBean = (CircuitoBean) FacesUtils.getSessionBean(Constantes.CIRCUITO_BEAN);
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String personaSeleccionada = params.get(Constantes.SELECTED_PERSON);
		int orden = Integer.parseInt(FacesUtils.getParam("grupoIdLiberarPersona"));
		Grupo grupo = buscarGrupo(orden, circuitoBean.getCircuito().getListGrupo());

		GrupoPersona grupoPersona = buscarPersonaEnGrupo(personaSeleccionada, grupo.getGrupoPersona());
		grupoPersona.setObligatorio(false);

		if (grupo.getGrupoPersona().size() <= 1) {
			grupo.setMinimoFirmantes(1);
		} else {
			grupo.setFirmantesRequeridos(getFirmantesRequeridos(grupo));
			grupo.setMinimoFirmantes(getFirmantesRequeridos(grupo));
		}
	}

	public void fijarPersonaGrupo(ActionEvent ev) {
		CircuitoBean circuitoBean = (CircuitoBean) FacesUtils.getSessionBean(Constantes.CIRCUITO_BEAN);
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String personaSeleccionada = params.get(Constantes.SELECTED_PERSON);

		int orden = Integer.parseInt(FacesUtils.getParam("grupoIdFijarPersona"));
		Grupo grupo = buscarGrupo(orden, circuitoBean.getCircuito().getListGrupo());

		GrupoPersona grupoPersona = buscarPersonaEnGrupo(personaSeleccionada, grupo.getGrupoPersona());
		grupoPersona.setObligatorio(true);
		if (grupo.getGrupoPersona().size() <= 1) {
			grupo.setMinimoFirmantes(1);
		} else {
			grupo.setFirmantesRequeridos(getFirmantesRequeridos(grupo));
			grupo.setMinimoFirmantes(getFirmantesRequeridos(grupo));
		}
	}

	public String enviarFlujo() {
		CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		CircuitoBean circuitoBean = (CircuitoBean) FacesUtils.getSessionBean(Constantes.CIRCUITO_BEAN);
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Se procede a asignar circuito a documento", 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		int contador = 0;
		// Si el �ltimo estado es de firma
		if (circuitoBean.getCircuito().getListGrupo().get(circuitoBean.getCircuito().getListGrupo().size() - 1).getTipoGrupo().getCodigo().equals("FIRM")) {
			for (Grupo grupo : circuitoBean.getCircuito().getListGrupo()) {
				if (grupo.getGrupoPersona().size() == 0) {
					contador++;
				}
			}
			// Se comprueban los grupos vac�os
			if (contador == circuitoBean.getCircuito().getListGrupo().size()) {
				commonBean.setMessage("No se puede definir un flujo vac�o");
				FacesUtils.verDialog("messageDialog");
			} else if (contador > 0) {
				for (int i = 0; i < circuitoBean.getCircuito().getListGrupo().size(); i++) {
					Grupo grupo = circuitoBean.getCircuito().getListGrupo().get(i);
					if (grupo.getGrupoPersona().size() == 0) {
						circuitoBean.getCircuito().getListGrupo().remove(i);
						i--;
					}
				}
				commonBean.setMessage("Se han eliminado los grupos vac�os, por favor, env�e otra vez");
				FacesUtils.verDialog("messageDialog");
			} else {

				// Generamos los parametros necesarios
				Parametros parametros = new Parametros();
				Credenciales credenciales = new Credenciales();

				credenciales.setUsuario(Constantes.APPLICATION_USERNAME);
				credenciales.setPassword(Constantes.APPLICATION_PASSWORD);
				parametros.setCredencial(credenciales);
				AltaDocsPortafirmas parametrosAlta = new AltaDocsPortafirmas();
				parametrosAlta.setListaDocumentos(new ListaDocumentos());
				// parametrosAlta.setListaUris(new ListaUris());
				for (DocumentoPortafirmas documento : documentosBean.getSelectedDocs()) {
					es.apt.ae.facade.ws.params.commons.in.Documento docAux = new es.apt.ae.facade.ws.params.commons.in.Documento();
					docAux.setUri(documento.getUri());
					docAux.setNombre(documento.getAsunto());
					parametrosAlta.getListaDocumentos().getDocumento().add(docAux);
					// parametrosAlta.getListaUris().getUri().add(documento.getUri());
				}
				// parametrosAlta.setURLComunicacion(ConfigUtils.getParametro(Constantes.URL_NOTIFICACION));
				Circuito parametrosCircuito = new Circuito();
				// Recorremos el circuito del bean para construir la solicitud
				CircuitoEntity entityCircuito = circuitoBean.getCircuito();

				for (TipoCircuito tipo : TipoCircuito.values()) {
					if (tipo.toString().equals(entityCircuito.getTipoCircuito().getCodigo())) {
						parametrosCircuito.setTipoCircuito(tipo);
						break;
					}
				}
				for (Grupo grupoEntity : entityCircuito.getListGrupo()) {
					es.apt.ae.facade.ws.params.portafirmas.in.alta.Grupo paramsGrupo = new es.apt.ae.facade.ws.params.portafirmas.in.alta.Grupo();
					paramsGrupo.setFirmantesRequeridos(Integer.toString(grupoEntity.getFirmantesRequeridos()));
					paramsGrupo.setOrden(Integer.toString(grupoEntity.getOrden()));
					for (TipoGrupo tipo : TipoGrupo.values()) {
						if (tipo.toString().equals(grupoEntity.getTipoGrupo().getCodigo())) {
							paramsGrupo.setTipoGrupo(tipo);
							break;
						}
					}

					for (GrupoPersona grupoPersonaEntity : grupoEntity.getGrupoPersona()) {
						es.apt.ae.facade.ws.params.portafirmas.in.alta.Persona paramsPersona = new es.apt.ae.facade.ws.params.portafirmas.in.alta.Persona();
						paramsPersona.setObligatorio(grupoPersonaEntity.isObligatorio());
						paramsPersona.setDni(grupoPersonaEntity.getPersona().getNumIdentificacion());
						paramsGrupo.getPersonas().add(paramsPersona);
					}
					parametrosCircuito.getGrupo().add(paramsGrupo);
				}
				if (entityCircuito.getId() != null) {// Solo si es predefinido
					parametrosCircuito.setIdCircuito(entityCircuito.getId().toString());
				}

				// Indicamos el solicitante:
				Solicitante solicitante = new Solicitante();
				InformacionSolicitante informacionSolicitante = new InformacionSolicitante();
				informacionSolicitante.setNombre(usuarioBean.getNombre());
				informacionSolicitante.setApellido1(usuarioBean.getApellido1());
				informacionSolicitante.setApellido2(usuarioBean.getApellido2());
				informacionSolicitante.setDNINIE(usuarioBean.getNumIdentificacion());
				if (usuarioBean.getMail() != null) {
					informacionSolicitante.setCorreoNotificacion(usuarioBean.getMail());
				}
				solicitante.setInformacionSolicitante(informacionSolicitante);
				parametrosAlta.setUsuarioSolicitante(solicitante);

				parametrosAlta.setTipoFirmaEnum(TipoFirmaEnum.XADES);
				parametrosAlta.setCircuito(parametrosCircuito);
				parametros.setParametrosAlta(parametrosAlta);

				Resultado resultado = new Resultado();
				Respuesta respuesta = new Respuesta();

				try {
					respuesta = Services.getSrvPortafirmasFacadeRemote().enviarPortafirmasEJB(resultado, respuesta, parametros);
					if (respuesta.getCodigo().equals("FWS-PF-0000")) {
						LoggerUtils.showInfo(logger, "Circuito dado de alta correctamente", 
								FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
						documentosBean.getDocumentosList().removeAll(documentosBean.getSelectedDocs());
						documentosBean.getSelectedDocs().clear();
						return Constantes.BACK;
					} else {
						LoggerUtils.showError(logger, respuesta.getDescripcion(), null, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
						commonBean.setMessage(respuesta.getDescripcion());
						FacesUtils.verDialog("messageDialog");
					}
				} catch (PortafirmasFacadeException e) {
					LoggerUtils.showError(logger, Constantes.ERROR_PERSIST_CIRCUIT, e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
					commonBean.setMessage(Constantes.ERROR_PERSIST_CIRCUIT);
					FacesUtils.verDialog("messageDialog");
				}

				documentosBean.getSelectedDocs().clear();
				return null;
			}
		} else {
			commonBean.setMessage("El �ltimo paso del circuito debe ser siempre una firma");
			FacesUtils.verDialog("messageDialog");
		}
		return null;
	}

	public void cargarPredefinido() {
		CircuitoBean circuitoBean = (CircuitoBean) FacesUtils.getSessionBean(Constantes.CIRCUITO_BEAN);
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		Long circuitoID = new Long(params.get(Constantes.CIRCUITO_ID));
		for (CircuitoEntity circuito : circuitoBean.getListCircuitosPredefinidos()) {
			if (circuito.getId().equals(circuitoID)) {
				try {
					circuito.setTipoCircuito(CatalogosUtils.getTipoCircuitoByCodigo(Constantes.GNRD));
				} catch (PortafirmasFacadeException e) {
					// TODO Auto-generated catch block
				}
				circuitoBean.setCircuito(new CircuitoEntity().clone(circuito));
			}
		}
	}

}
