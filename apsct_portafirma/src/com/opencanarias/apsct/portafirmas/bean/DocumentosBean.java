package com.opencanarias.apsct.portafirmas.bean;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.opencanarias.apsct.plae.dto.Document;
import com.opencanarias.apsct.portafirmas.helpers.DocumentosHelper;
import com.opencanarias.apsct.portafirmas.utils.CatalogosUtils;
import com.opencanarias.apsct.portafirmas.utils.ConfigUtils;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.apsct.portafirmas.utils.Services;
import com.opencanarias.ejb.common.FacadeBean;
import com.opencanarias.ejb.portafirmas.PortafirmasFacadeRemote;
import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;

import es.apt.ae.facade.entities.BackOffice;
import es.apt.ae.facade.entities.CatPropiedadesConfiguracion;
import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;


@ManagedBean
@SessionScoped
public class DocumentosBean implements Serializable {

	private static final long serialVersionUID = -934772464009690234L;

	private static Logger logger = Logger.getLogger(DocumentosBean.class);
	
	@Inject
	UsuarioBean usuarioBean;

	private List<DocumentoPortafirmas> documentosList;
	private String comentario = "";
	private List<DocumentoPortafirmas> selectedDocs = new ArrayList<DocumentoPortafirmas>();
	private DocumentoPortafirmas documentoSeleccionado = new DocumentoPortafirmas();
	private String titulo;
	private String userName = "";
	private String dummie = "";
	//Filtros:
	private String usuarioRemitente = null;
	private String fechaSolicitud = null;
	private String estadoDocumento = null;
	private String numeroExpediente = null;
	private String nombreDocumento = null;
	private String asuntoDocumento = null;
	private String origenDocumento = null;
	private List<BackOffice> listBackoffice = new ArrayList<BackOffice>();
	//Orden:
	private String campoOrden = null;
	private String tipoOrden = "";
	
	//Firmas:
	private List<Document> documentosFirma = new ArrayList<Document>();
	
	// Config values BBDD
	private boolean clienteFirmaWeb; 
	
	public DocumentosBean(){
		//UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		String uri = FacesUtils.getParam("uri");
		if (uri != null){
			FacesUtils.setSessionAttribute(Constantes.ESTADO_URI, uri);
			if (usuarioBean == null){
				usuarioBean = new UsuarioBean();
				usuarioBean.create();
			}
			if (usuarioBean.getNumIdentificacion() != null) {
				HashMap<String, Object> result = Services.getSrvPortafirmasFacadeRemote().getDocumentoByUri(usuarioBean.getNumIdentificacion(), uri);
				if ((Boolean)result.get("resultado")){
					documentoSeleccionado = (DocumentoPortafirmas)result.get("documento");
				} else {
					LoggerUtils.showError(logger, "No se pudo recuperar el documento", null, 
							FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
				}
			} 
		}
		try {
			CatPropiedadesConfiguracion propClienteFirmaWeb = CatalogosUtils.getPropConfById("portafirmas.clienteFirmaWeb.habilitado");
			if (null != propClienteFirmaWeb) {
				clienteFirmaWeb = ("true".equalsIgnoreCase(propClienteFirmaWeb.getValor())?true:false);
			}
		} catch (PortafirmasFacadeException e) {
			clienteFirmaWeb = false;
		}
	}
	
	public List<DocumentoPortafirmas> getDocumentosList() {
		if (documentosList == null) {
			documentosList = new ArrayList<DocumentoPortafirmas>();
			//UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
			String bandeja = Constantes.PENDIENTES.toUpperCase();
			if (usuarioBean.getEntidadPersona() == null) {
				bandeja = Constantes.PERSONAL.toUpperCase();
			}
			PortafirmasFacadeRemote service = (PortafirmasFacadeRemote) Services
						.getSrvPortafirmasFacadeRemote();
				documentosList = service
						.buscarPorBandeja(((UsuarioBean) FacesUtils
								.getSessionBean(Constantes.USUARIO_BEAN))
								.getNumIdentificacion(), bandeja, null);
				LoggerUtils.showInfo(logger, Constantes.INFO_TOTAL_DOCUMENTS + documentosList.size(), 
						FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername()); 
				DocumentosHelper.ordenarListaDocumentos(Constantes.ORDER_FECHA);
		}
		return documentosList;
	}

	public void setDocumentosList(List<DocumentoPortafirmas> documentosList) {
		this.documentosList = documentosList;
	}

	public List<DocumentoPortafirmas> getSelectedDocs() {
		return selectedDocs;
	}

	public void setSelectedDocs(List<DocumentoPortafirmas> selectedDocs) {
		this.selectedDocs = selectedDocs;
	}

	public DocumentoPortafirmas getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(
			DocumentoPortafirmas documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}

	public String getTitulo() {
		if (titulo == null) {
			//UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
			if(usuarioBean.getNombre() == null){
				usuarioBean.create();
			}
			if (usuarioBean.isAdministrador()) {
				titulo = Constantes.INICIO;
			} else if (usuarioBean.getEntidadPersona() == null) {
				titulo = Constantes.PERSONAL;
			} else {
				titulo = Constantes.PENDIENTES;
			}
		}

		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getEntorno() {
		String hostInfo = "";
	    String host = null;
		try {
			InetAddress ip = InetAddress.getLocalHost();
			host = ip.getHostName();
		} catch (UnknownHostException e) {
			LoggerUtils.showInfo(logger, "No se ha podido obtener el nombre de la m�quina");
		}
    	if (host != null && !host.trim().equals("")) {
    		if (host.endsWith(".aptf.local")) {
    			host = host.substring(0, host.indexOf('.'));
    		}
    		hostInfo += " (" + host + ")";
    	}	
    	
		return (ConfigUtils.getParametro("entorno.descripcion") + hostInfo);
	}
	
	public String getVersion() {
		return ConfigUtils.getParametro("version");
	}	
	
	public String getClienteFirmaWebUrl() {
		return ConfigUtils.getParametro("sign.web.client.url");
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getUserName() {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		userName = context.getUserPrincipal().getName();
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDummie() {
		return dummie;
	}

	public void setDummie(String dummie) {
		this.dummie = dummie;
	}

	public void destroy() {
		documentosList = null;
		comentario = null;
		selectedDocs = null;
		documentoSeleccionado = null;
		titulo = null;
		userName = null;
		dummie = null;
	}

	public String getUsuarioRemitente() {
		return usuarioRemitente;
	}

	public void setUsuarioRemitente(String usuarioRemitente) {
		this.usuarioRemitente = usuarioRemitente;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getEstadoDocumento() {
		return estadoDocumento;
	}

	public void setEstadoDocumento(String estadoDocumento) {
		this.estadoDocumento = estadoDocumento;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getAsuntoDocumento() {
		return asuntoDocumento;
	}

	public void setAsuntoDocumento(String asuntoDocumento) {
		this.asuntoDocumento = asuntoDocumento;
	}

	public List<Document> getDocumentosFirma() {
		return documentosFirma;
	}
	
	public Map<String, Document> getDocumentosFirmaMap() {
		Map<String, Document> documentosFirmaMap = null;
		if (!documentosFirma.isEmpty()) {
			documentosFirmaMap = new HashMap<String, Document>();
			for (Document docFirma:documentosFirma) {
				documentosFirmaMap.put(docFirma.getUri(), docFirma);
			}
		}
		return documentosFirmaMap;
	}

	public void setDocumentosFirma(List<Document> documentosFirma) {
		this.documentosFirma = documentosFirma;
	}

	public String getCampoOrden() {
		return campoOrden;
	}

	public void setCampoOrden(String campoOrden) {
		this.campoOrden = campoOrden;
	}

	public String getTipoOrden() {
		return tipoOrden;
	}

	public void setTipoOrden(String tipoOrden) {
		this.tipoOrden = tipoOrden;
	}

	public String getOrigenDocumento() {
		return origenDocumento;
	}

	public void setOrigenDocumento(String origenDocumento) {
		this.origenDocumento = origenDocumento;
	}
	
	public boolean isClienteFirmaWeb() {
		return clienteFirmaWeb;
	}

	public void setClienteFirmaWeb(boolean clienteFirmaWeb) {
		this.clienteFirmaWeb = clienteFirmaWeb;
	}

	public List<BackOffice> getListBackoffice() {
		if (listBackoffice == null || listBackoffice.isEmpty()){
			//UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
			try {
				BackOffice cabecera = new BackOffice();
				cabecera.setDescripcion("-- Seleccione un Sistema Origen --");
				cabecera.setCodigo("");
				listBackoffice.add(cabecera);
				listBackoffice.addAll(CatalogosUtils.getListBackOffices());
			} catch (PortafirmasFacadeException e){
				LoggerUtils.showError(logger, "No se ha podido recuperar el cat�logo de Backoffices", e, 
						FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			}
		}
		return listBackoffice;
	}

}
