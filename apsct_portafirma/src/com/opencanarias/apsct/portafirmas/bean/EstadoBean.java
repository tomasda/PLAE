package com.opencanarias.apsct.portafirmas.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.apsct.portafirmas.utils.Services;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

import es.apt.ae.facade.entities.portafirmas.CircuitoEntity;
import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;
import es.apt.ae.facade.entities.portafirmas.Grupo;
import es.apt.ae.facade.entities.portafirmas.ProcesoFirma;

@ManagedBean
@RequestScoped
public class EstadoBean implements Serializable {

	private static final long serialVersionUID = 5761550534459019380L;
	private DocumentoPortafirmas documento = new DocumentoPortafirmas();
	private List<ProcesoFirma> listProcesoRealizado = new ArrayList<ProcesoFirma>();
	private List<Grupo> listGruposPorRealizar = new ArrayList<Grupo>();
	private String mensajeAcceso = null;

	protected static final Logger logger = Logger.getLogger(EstadoBean.class);

	public EstadoBean() {
		String uri = FacesUtils.getParam("uri");
		if (uri == null) {
			uri = (String)FacesUtils.extractSessionAttribute(Constantes.ESTADO_URI);
		}
		FacesUtils.deleteSessionBean(Constantes.ESTADO_BEAN);
		this.clean();
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		if (usuarioBean == null){
			usuarioBean = new UsuarioBean();
			usuarioBean.create();
		}
		
		String usuarioNumIdent = usuarioBean.getNumIdentificacion();
		String usuarioSolicitante = usuarioBean.getUsername();
				
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		if (documentosBean == null){
			documentosBean = new DocumentosBean();
		}
		if (uri != null) {
			if (documentosBean.getDocumentoSeleccionado() == null || documentosBean.getDocumentoSeleccionado().getUri() == null ||
					!documentosBean.getDocumentoSeleccionado().getUri().equals(uri)) {
				HashMap<String, Object> result = Services.getSrvPortafirmasFacadeRemote().getDocumentoByUri(usuarioNumIdent, uri);
				if ((Boolean) result.get("resultado")) {
					documento = (DocumentoPortafirmas) result.get("documento");
					documentosBean.setDocumentoSeleccionado(documento);
				}
			}
		}
		documento = documentosBean.getDocumentoSeleccionado();
		if (documento.getUri() != null){
			/*
			 * 2019-08-28 No se permite ver el estado del documento porque se le está pasando el nif en vez del username
			 * Tomás.
			 */
			HashMap<String, Object> resultadoAcceso = Services.getSrvPortafirmasFacadeRemote().permitirVisualizarEstado(documento.getUri(), usuarioSolicitante);//usuarioNumIdent);
			if ((Boolean)resultadoAcceso.get("acceso")){
				try {
					List<ProcesoFirma> historial = Services.getSrvPortafirmasFacadeRemote().getHistorialDocumento(usuarioNumIdent, documentosBean.getDocumentoSeleccionado().getUri());
					this.setListProcesoRealizado(historial);
					if (!documentosBean.getDocumentoSeleccionado().getEstadoDocumento().getCodigo().equals(Constantes.FIRM) && !documentosBean.getDocumentoSeleccionado().getEstadoDocumento().getCodigo().equals(Constantes.RECH)
							&& !documentosBean.getDocumentoSeleccionado().getEstadoDocumento().getCodigo().equals(Constantes.PEND)) {
						CircuitoEntity circuito = Services.getSrvPortafirmasFacadeRemote().consultarCircuitoByUri(documentosBean.getDocumentoSeleccionado().getUri());
		
						if (circuito.getId() != null) {
							List<Grupo> listGrupo = new ArrayList<Grupo>();
							for (Grupo grupo : circuito.getListGrupo()) {
								if (grupo.getCerrado() == null || !grupo.getCerrado()) {
									listGrupo.add(grupo);
									// ordenar para visualizar
								}
							}
							Collections.sort(listGrupo, new Comparator<Grupo>() {
										@Override
										public int compare(Grupo grupo1, Grupo grupo2) {
											return Integer.valueOf(grupo1.getOrden()).compareTo(Integer.valueOf(grupo2.getOrden()));
										}
									});
							this.setListGruposPorRealizar(listGrupo);
						}
					}
				} catch (PortafirmasFacadeException e) {
					LoggerUtils.showError(logger, "No se ha podido recuperar el historial del documento", e, 
							FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
				}
			}else {
				mensajeAcceso = (String)resultadoAcceso.get("mensaje");
			}
		} else{
			//TODO: El mensaje
			mensajeAcceso = "No se encuentra el documento indicado";
		}
	}

	
	public List<ProcesoFirma> getListProcesoRealizado() {
		return listProcesoRealizado;
	}

	public void setListProcesoRealizado(List<ProcesoFirma> listProcesoRealizado) {
		this.listProcesoRealizado = listProcesoRealizado;
	}

	public List<Grupo> getListGruposPorRealizar() {
		return listGruposPorRealizar;
	}

	public void setListGruposPorRealizar(List<Grupo> listGruposPorRealizar) {
		this.listGruposPorRealizar = listGruposPorRealizar;
	}

	public DocumentoPortafirmas getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoPortafirmas documento) {
		this.documento = documento;
	}

	public void clean() {
		documento = new DocumentoPortafirmas();
		listProcesoRealizado = new ArrayList<ProcesoFirma>();
		listGruposPorRealizar = new ArrayList<Grupo>();
	}


	public String getMensajeAcceso() {
		return mensajeAcceso;
	}


	public void setMensajeAcceso(String mensajeAcceso) {
		this.mensajeAcceso = mensajeAcceso;
	}

}
