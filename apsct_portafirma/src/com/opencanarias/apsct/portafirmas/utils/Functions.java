package com.opencanarias.apsct.portafirmas.utils;

import java.util.Date;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.portafirmas.bean.UsuarioBean;

import es.apt.ae.facade.entities.portafirmas.CircuitoEntity;
import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;
import es.apt.ae.facade.entities.portafirmas.Grupo;
import es.apt.ae.facade.entities.portafirmas.GrupoPersona;
import es.apt.ae.facade.entities.portafirmas.Persona;
import es.apt.ae.facade.entities.portafirmas.ProcesoFirma;

public final class Functions {

	protected static final Logger logger = Logger.getLogger(Functions.class);

	private Functions() {
		// Hide constructor.
	}

	// public static boolean containEntityInList(CircuitoEntity circuito, Persona persona) {
	// if (circuito != null){
	// for ( Grupo grupo :circuito.getListGrupo() ){
	// if (grupo.getOrden() == circuito.getOrdenActivo()){
	// if(grupo.getGrupoPersona() != null){
	// for (GrupoPersona grupoPersona : grupo.getGrupoPersona()){
	// if(grupoPersona.getPersona().equals(persona)){
	// if (grupo.getTipoGrupo().getCodigo().equals(Constantes.FIRM)){
	// return true;
	// }
	// return false;
	// }
	// }
	// }
	// }
	// }
	// }
	// return false;
	// }

	public static boolean containEntityInList(CircuitoEntity circuito, Persona persona) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);

		if (circuito != null) {
			for (Grupo grupo : circuito.getListGrupo()) {
				if (grupo.getOrden() == circuito.getOrdenActivo()) {
					if (grupo.getGrupoPersona() != null) {
						for (GrupoPersona grupoPersona : grupo.getGrupoPersona()) {
							if (grupoPersona.getPersona().equals(persona)) {// Se comprueba a la persona
								if (grupo.getTipoGrupo().getCodigo().equals(Constantes.FIRM)) {
									return true;
								}
								return false;
							} else if (usuarioBean.getListSustitucion() != null) {
								for (Persona personaSustituir : usuarioBean.getListSustitucion()) {
									if (grupoPersona.getPersona().equals(personaSustituir)) {// Se comprueba a la persona
										if (grupo.getTipoGrupo().getCodigo().equals(Constantes.FIRM)) {
											return true;
										}
										return false;
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	public static Date dateOfLastAction(DocumentoPortafirmas documento, String numIdentificacionUsuario) {
		Date fechaUltimaAccion = new Date();
		if (documento != null) {
			fechaUltimaAccion = documento.getFechaSubidaPortafirmas();
			for (ProcesoFirma accion : documento.getAccionSobreDocumento()) {
				if (accion.getPersona() != null && accion.getPersona().getNumIdentificacion().equals(numIdentificacionUsuario)) {
					fechaUltimaAccion = accion.getFechaAccion();
				}
			}
		}
		// return false;
		return fechaUltimaAccion;
	}

}
