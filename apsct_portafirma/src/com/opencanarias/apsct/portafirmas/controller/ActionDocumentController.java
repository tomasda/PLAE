package com.opencanarias.apsct.portafirmas.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import java.util.Base64;
import org.jboss.logging.Logger;

import com.opencanarias.apsct.plae.dto.Document;
import com.opencanarias.apsct.plae.exceptions.SignBatchHelperException;
import com.opencanarias.apsct.plae.utils.SignbatchUtils;
import com.opencanarias.apsct.portafirmas.bean.CommonBean;
import com.opencanarias.apsct.portafirmas.bean.DocumentosBean;
import com.opencanarias.apsct.portafirmas.bean.FirmaBean;
import com.opencanarias.apsct.portafirmas.bean.UsuarioBean;
import com.opencanarias.apsct.portafirmas.exception.PortafirmasWebException;
import com.opencanarias.apsct.portafirmas.helpers.PortafirmasHelper;
import com.opencanarias.apsct.portafirmas.utils.BandejaUtils;
import com.opencanarias.apsct.portafirmas.utils.Constantes;
import com.opencanarias.apsct.portafirmas.utils.FacesUtils;
import com.opencanarias.apsct.portafirmas.utils.Functions;
import com.opencanarias.apsct.portafirmas.utils.Services;
import com.opencanarias.apsct.portafirmas.utils.SignBatchConfigCreatorUtil;
import com.opencanarias.apsct.portafirmas.utils.SignPAdESUtil;
import com.opencanarias.ejb.common.FacadeBean;
//import com.opencanarias.apsct.portafirmas.utils.SignbatchUtils;
import com.opencanarias.exceptions.PortafirmasFacadeException;
import com.opencanarias.apsct.modulo.generic.service.utils.LoggerUtils;
import com.opencanarias.utils.StringUtils;

import es.apt.ae.facade.entities.portafirmas.DocumentoPortafirmas;
import es.apt.ae.facade.entities.portafirmas.Persona;
import es.apt.ae.facade.repository.dto.DocumentoRepositorioItem;
import es.apt.ae.facade.ws.params.commons.in.Credenciales;
import es.apt.ae.facade.ws.params.commons.in.ListaUris;
import es.apt.ae.facade.ws.params.commons.out.Respuesta;
import es.apt.ae.facade.ws.params.portafirmas.common.InformacionSolicitante;
import es.apt.ae.facade.ws.params.portafirmas.common.Solicitante;
import es.apt.ae.facade.ws.params.portafirmas.in.Parametros;
import es.apt.ae.facade.ws.params.portafirmas.in.recuperar.RecuperarDocsPortafirmas;
import es.apt.ae.facade.ws.params.portafirmas.out.Resultado;

@ManagedBean
public class ActionDocumentController implements Serializable{

	private static final long serialVersionUID = 5654095853118515243L;
	protected static final Logger logger = Logger.getLogger(ActionDocumentController.class);

	public void comprobarRechazarDocumentos(ActionEvent event) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		Boolean valido = true;
		LoggerUtils.showInfo(logger, "Se llama al metodo validar documentos para rechazar", 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		documentosBean.setComentario(null);
		String info = null;
		if (documentosBean.getSelectedDocs().size() <= 0) {
			info = "Debe seleccionar al menos un documento";
			FacesUtils.verDialog("messageDialog");
			valido = false;
		}  else {	
			info = "Los siguientes documentos no pueden ser rechazados: ";
			for (DocumentoPortafirmas documento : documentosBean.getSelectedDocs()) {
				if (!documento.getEstadoDocumento().getCodigo().equals("TRAM") && !documento.getEstadoDocumento().getCodigo().equals(Constantes.ENVD)) {
					info += documento.getNombre() + "\n";
					FacesUtils.verDialog("messageDialog");
					valido = false;
					break;
				}
			}
		}
		if (valido) {
			FacesUtils.verDialog("confirmationDialog");
		}
		commonBean.setMessage(info);
	}
	
	public void comprobarBorrarDocumentos(ActionEvent event) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Se verifica la cantidad de documentos a eliminar", 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		String info = new String();
		if (documentosBean.getSelectedDocs().size() <= 0) {
			info = "Debe seleccionar al menos un documento";
			commonBean.setMessage(info);
			FacesUtils.verDialog("messageDialog");
		}  else {
			FacesUtils.verDialog("removeDialog");
		}
	}
	
	public void comprobarRecuperarDocumentos(ActionEvent event) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Se verifica la cantidad de documentos a recuperar", 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		String info = new String();
		if (documentosBean.getSelectedDocs().size() <= 0) {
			info = "Debe seleccionar al menos un documento";
			commonBean.setMessage(info);
			FacesUtils.verDialog("messageDialog");
		}  else {
			FacesUtils.verDialog("recoverDialog");
		}
	}
	
	public void comprobarValidarDocumentos(ActionEvent event) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Se verifica la cantidad de documentos a validar", 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		String info = new String();
		if (documentosBean.getSelectedDocs().size() <= 0) {
			info = "Debe seleccionar al menos un documento";
			commonBean.setMessage(info);
			FacesUtils.verDialog("messageDialog");
		}  else {
			FacesUtils.verDialog("validateDialog");
		}
	}
	
	public String recuperarDocumentoDePortafirmas(ActionEvent event){
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		
		Resultado resultado = new Resultado();
		Respuesta respuesta = new Respuesta();
		Parametros parametros = new Parametros();
		Credenciales credenciales = new Credenciales();
		credenciales.setUsuario(Constantes.APPLICATION_USERNAME);
		credenciales.setPassword(Constantes.APPLICATION_PASSWORD);
		RecuperarDocsPortafirmas recuperarDocsPortafirmas = new RecuperarDocsPortafirmas();
		parametros.setParametrosRecuperar(recuperarDocsPortafirmas);
		ListaUris listaUris = new ListaUris();
		recuperarDocsPortafirmas.setListaUris(listaUris);
		Solicitante solicitante = new Solicitante();
		recuperarDocsPortafirmas.setUsuarioSolicitante(solicitante);
		InformacionSolicitante informacionSolicitante = new InformacionSolicitante();
		solicitante.setInformacionSolicitante(informacionSolicitante);
		
		informacionSolicitante.setNombre(usuarioBean.getNombre());
		informacionSolicitante.setApellido1(usuarioBean.getApellido1());
		informacionSolicitante.setApellido2(usuarioBean.getApellido2());
		informacionSolicitante.setDNINIE(usuarioBean.getNumIdentificacion());
		informacionSolicitante.setCorreoNotificacion(usuarioBean.getMail());
		
		ArrayList<DocumentoPortafirmas> docsDeseleccionar = new ArrayList<DocumentoPortafirmas>();
		for (DocumentoPortafirmas documento : documentosBean.getSelectedDocs()) {
			if (!documento.getEstadoDocumento().getCodigo().equals(Constantes.ENVD)) {
				docsDeseleccionar.add(documento);
				String info = "Solamente se pueden recuperar documentos en estado enviado, se han desmarcado autom�ticamente aquellos documentos que no se pueden recuperar";
				PortafirmasHelper.mostrarMensajeError(info, logger, null);
			} else if(!documento.getSistemaOrigen().getCodigo().equals(Constantes.PRTF)) {
				docsDeseleccionar.add(documento);
				String info = "Solamente se pueden recuperar documentos enviados desde el portafirmas, utilice el sistema origen para recuperar dicho documento";
				PortafirmasHelper.mostrarMensajeError(info, logger, null);
			} else {
				listaUris.getUri().add(documento.getUri());
			}
		}
		
		if (!docsDeseleccionar.isEmpty()) {
			for (DocumentoPortafirmas documento:docsDeseleccionar) {
				documentosBean.getSelectedDocs().remove(documento);
			}
		}
		
		Respuesta result = Services.getSrvPortafirmasFacadeRemote().recuperarPortafirmasEJB(resultado, respuesta, parametros);
		if (result.getCodigo().equals("FWS-PF-00000")){
			System.out.println("Todo bien");
		} else {
			System.out.println("C�digo de error");
		}
		
		BandejaUtils.refresh();
		
		return null;
	}
	
	public String borrarDocumento(ActionEvent event) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		for (DocumentoPortafirmas documento : documentosBean.getSelectedDocs()) {
			if (!documento.getEstadoDocumento().getCodigo().equals(Constantes.PEND) && !documento.getEstadoDocumento().getCodigo().equals(Constantes.RECP)) {
				// Se eliminan los que no se pueden eliminar
				documentosBean.getSelectedDocs().remove(documento);
				String info = "Existen uno o varios documentos que no pueden ser borrados, se han deseleccionado autom�ticamente";
				PortafirmasHelper.mostrarMensajeError(info, logger, null);
			}
		}
		try {
			HashMap<String, Object> result = Services.getSrvPortafirmasFacadeRemote().actuarSobreListaDocumento(usuarioBean.getNumIdentificacion(),
					documentosBean.getSelectedDocs(), Constantes.BORR, null);
			actuarMessage(result, null);

			BandejaUtils.refresh();
		} catch (PortafirmasFacadeException e) {
			String info = "Error al eliminar el listado de documentos indicado";
			PortafirmasHelper.mostrarMensajeError(info, logger, e);
		}
		return null;
	}

	public void recuperarDocumentosFirmar(ActionEvent event) {
		recuperarDocumentosFirmarComun(false);
	}
	
	public void recuperarDocumentosFirmarClienteWeb(ActionEvent event) {
		recuperarDocumentosFirmarComun(true);
	}
	
	@SuppressWarnings("unchecked")
	public void recuperarDocumentosFirmarComun(boolean clienteFirmaWeb) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
		FirmaBean firmaBean = (FirmaBean)FacesUtils.getSessionBean(Constantes.FIRMA_BEAN);

		String info = new String();
		
		// Inicio del bean de firma
		if (null == firmaBean) {
			firmaBean = new FirmaBean(null, clienteFirmaWeb);
		}
		commonBean.setMessage(info);

		// Almacenamos en el bean el documento seleccionado, en caso de que lo haya
		BandejaUtils.setDocumentoSeleccionado(documentosBean);
		List<DocumentoPortafirmas> listAux = new ArrayList<DocumentoPortafirmas>();
		if (null == documentosBean.getDocumentoSeleccionado()) {
			listAux.addAll(documentosBean.getSelectedDocs());
		} else {
			listAux.add(documentosBean.getDocumentoSeleccionado());
		}

		LoggerUtils.showInfo(logger, "Se verifica la cantidad de documentos a firmar: " + listAux.size(), FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		
		List<DocumentoPortafirmas> listDocumentosError = validarDocumentosAccion(listAux, Constantes.FIRM, usuarioBean.getEntidadPersona());
		
		if (!listDocumentosError.isEmpty()) {
			if (listAux.size() == listDocumentosError.size()) {
				if (listAux.size() == 1) {
					info = "Acci�n equivocada para documento seleccionado";
				} else {
					info = "Acci�n equivocada para documentos seleccionados";
				}
			} else {
				info = "Acci�n equivocada para alguno de los documentos seleccionados";
			}
			mostrarInfoDocumentos(listDocumentosError, info, 1, usuarioBean, commonBean, logger, null);
		} else if (listAux.size() <= 0) {
			info = "Debe seleccionar al menos un documento";
			PortafirmasHelper.mostrarMensajeWarning(info, logger);	
		} else {
			try {
				es.apt.ae.facade.dto.Resultado resultado = Services.getSrvPortafirmasFacadeRemote().recuperarDocumentosFirmar(listAux, usuarioBean.getUsername(),
						usuarioBean.getEntidadPersona(), clienteFirmaWeb);
				if (null != resultado) {
					if (null != resultado.getData()) {
						Map<String, DocumentoRepositorioItem> docsOkMap = (Map<String, DocumentoRepositorioItem>) resultado.getData();
						documentosBean.getDocumentosFirma().clear();
						documentosBean.setDocumentoSeleccionado(null);
						List<DocumentoPortafirmas> listDocumentosRecuperados = new ArrayList<DocumentoPortafirmas>();
						List<DocumentoPortafirmas> listDocumentosNoRecuperados = new ArrayList<DocumentoPortafirmas>();
						for (DocumentoPortafirmas documentoPortafirmas:listAux) {
							if (docsOkMap.containsKey(documentoPortafirmas.getUri())) {
								Document documentoItem = new Document();
								DocumentoRepositorioItem docRepoItem = docsOkMap.get(documentoPortafirmas.getUri());
								documentoItem.setUri(docRepoItem.getUri());
								documentoItem.setSignType(documentoPortafirmas.getTipoFirma());
								if (!clienteFirmaWeb) {
									documentoItem.setContent(docRepoItem.getContenidoStr());
									if (documentoItem.getSignType().equalsIgnoreCase(Constantes.PADES)) {
										documentoItem.setSignaturesNumber(SignPAdESUtil.getNumberSigners(Base64.getDecoder().decode(documentoItem.getContent())));
									}
								}
								documentosBean.getDocumentosFirma().add(documentoItem);
								listDocumentosRecuperados.add(documentoPortafirmas);
							} else {
								documentosBean.getSelectedDocs().remove(documentoPortafirmas);
								listDocumentosNoRecuperados.add(documentoPortafirmas);
								LoggerUtils.showError(logger, info, null, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
							}
						}
						
						if (es.apt.ae.facade.dto.Resultado.SUCCESS.equals(resultado.getStatus())) {
							info = "Se ha recuperado la informaci�n de todos los documentos a firmar";
							mostrarInfoDocumentos(listDocumentosRecuperados, info, 0, usuarioBean, null, logger, null);
							LoggerUtils.showInfo(logger, info, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
						} else if (es.apt.ae.facade.dto.Resultado.WARN.equals(resultado.getStatus())) {
							info = resultado.getMessage();
							commonBean.setMessage(resultado.getMessage());
							FacesUtils.verDialog("messageDialog");
							mostrarInfoDocumentos(listDocumentosNoRecuperados, resultado.getMessage(), 1, usuarioBean, null, logger, null);
						}
						if (!documentosBean.getDocumentosFirma().isEmpty()){
							firmaBean.init(documentosBean.getDocumentosFirma(), resultado.getStatus(), clienteFirmaWeb);
						}
					} else {
						setTodosDocumentosError(clienteFirmaWeb, listAux, resultado.getMessage(), resultado, usuarioBean, documentosBean, commonBean, firmaBean);
					}
				} else {
					info = "No ha sido posible recuperar la informaci�n de ninguno de los documentos a firmar";
					setTodosDocumentosError(clienteFirmaWeb, listAux, info, resultado, usuarioBean, documentosBean, commonBean, firmaBean);
				}
			} catch (PortafirmasFacadeException e) {
				info = "Se ha producido un error al recuperar los documentos a firmar";
				PortafirmasHelper.mostrarMensajeError(info, logger, e);
			} catch (PortafirmasWebException e) {
				info = "Se ha producido un error procesando la solicitud de firma";
				PortafirmasHelper.mostrarMensajeError(info, logger, e);
			}
		}
	}
	
	private void mostrarInfoDocumentos(List<DocumentoPortafirmas> listDocumentos, String mensaje, int tipo, UsuarioBean usuarioBean, CommonBean commonBean, 
			Logger logger, Exception e) {
		StringBuilder infoLogBuilder = new StringBuilder();
		StringBuilder infoHtmlBuilder = new StringBuilder();
		infoLogBuilder.append(mensaje + ":");
		infoHtmlBuilder.append(mensaje + ":");
		infoHtmlBuilder.append("<ul>");
		String prefixLog = "";
		for (DocumentoPortafirmas documentoPortafirmas : listDocumentos){
			infoLogBuilder.append(prefixLog);
			infoHtmlBuilder.append("<li>");
			if(null != documentoPortafirmas.getAsunto() && !documentoPortafirmas.getAsunto().isEmpty()){
				infoLogBuilder.append(documentoPortafirmas.getAsunto());
				infoHtmlBuilder.append(documentoPortafirmas.getAsunto());
				infoLogBuilder.append(" - ");
				infoHtmlBuilder.append(" - ");
			}
			infoLogBuilder.append(documentoPortafirmas.getNombre());
			infoHtmlBuilder.append(documentoPortafirmas.getNombre());
			infoHtmlBuilder.append("</li>");
			prefixLog = ",";
		}
		infoHtmlBuilder.append("</ul>");
		if (null != logger) {
			if (tipo == 0) {
				LoggerUtils.showInfo(logger, infoLogBuilder.toString(), FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			} else if (tipo == 1) {
				LoggerUtils.showWarning(logger, infoLogBuilder.toString(), FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			} else if (tipo == 2) {
				LoggerUtils.showError(logger, infoLogBuilder.toString(), e, FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			}
		}
		if (null != commonBean) {
			commonBean.setMessage(infoHtmlBuilder.toString());
			FacesUtils.verDialog("messageDialog");
		}
	}
	
	private void setTodosDocumentosError(boolean clienteFirmaWeb, List<DocumentoPortafirmas> listDocumentos, String mensaje, es.apt.ae.facade.dto.Resultado resultado, 
			UsuarioBean usuarioBean, DocumentosBean documentosBean, CommonBean commonBean, FirmaBean firmaBean) {
		documentosBean.getSelectedDocs().clear();
		documentosBean.getDocumentosFirma().clear();
		commonBean.setMessage(mensaje);
		FacesUtils.verDialog("messageDialog");
		mostrarInfoDocumentos(listDocumentos, mensaje, 2, usuarioBean, null, logger, null);
		String status = ((resultado != null && !resultado.getStatus().isEmpty())?resultado.getStatus():es.apt.ae.facade.dto.Resultado.FAIL);
		firmaBean.setSignValidationStatus(status);
		firmaBean.reset(clienteFirmaWeb);
	}
	
	public void rechazarDocumentos(ActionEvent event) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Se llama al metodo rechazar documento", 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		try {
			String comentario = documentosBean.getComentario();
			documentosBean.setComentario(null);
			HashMap<String, Object> result = Services.getSrvPortafirmasFacadeRemote().actuarSobreListaDocumento(usuarioBean.getNumIdentificacion(),
					documentosBean.getSelectedDocs(), Constantes.RECH, comentario);
			actuarMessage(result, null);
			BandejaUtils.refresh();
		} catch (PortafirmasFacadeException e) {
			String info = "Error, no se ha podido rechazar la lista de documentos";
			PortafirmasHelper.mostrarMensajeError(info, logger, e);
		}
	}
	
	public void validarDocumentos(ActionEvent event) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Se llama al metodo validar documento", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());

		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);

		BandejaUtils.setDocumentoSeleccionado(documentosBean);
		List<DocumentoPortafirmas> listAux = new ArrayList<DocumentoPortafirmas>();
		if (documentosBean.getDocumentoSeleccionado() == null) {
			listAux = documentosBean.getSelectedDocs();
		} else {
			listAux.add(documentosBean.getDocumentoSeleccionado());
		}

		try {
			HashMap<String, Object> result = Services.getSrvPortafirmasFacadeRemote().actuarSobreListaDocumento(usuarioBean.getNumIdentificacion(),
					listAux, Constantes.VALD, null);
			actuarMessage(result, null);
			BandejaUtils.refresh();
			documentosBean.setSelectedDocs(new ArrayList<DocumentoPortafirmas>());
		} catch (PortafirmasFacadeException e) {
			String info = "No se ha podido llevar a cabo la accion indicada";
			PortafirmasHelper.mostrarMensajeError(info, logger, e);
		}
	}
	
	private void actuarMessage(HashMap<String, Object> result, String adicionalMsg) {
		String msg = "";
		if ((Boolean) result.get("result") != null && (Boolean) result.get("result") != true) {
			msg += (((String) result.get("message")) != null?((String) result.get("message")):"");
		}
		if (adicionalMsg != null && !adicionalMsg.trim().equals("")) {
			msg += ((msg != null && !msg.trim().equals(""))?". ":"") + adicionalMsg;
		}
		if (msg != null && !msg.trim().equals("")) {
			CommonBean commonBean = (CommonBean) FacesUtils.getSessionBean(Constantes.COMMON_BEAN);
			commonBean.setMessage(msg);
			FacesUtils.verDialog("messageDialog");
		}
	}

	private List<DocumentoPortafirmas> validarDocumentosAccion(List<DocumentoPortafirmas> listaDocumentos, String codAccion, Persona persona) {
		List<DocumentoPortafirmas> docsError = new ArrayList<DocumentoPortafirmas>();
		for (DocumentoPortafirmas documentoPortafirmas : listaDocumentos) {
			if (!Functions.containEntityInList(documentoPortafirmas.getCircuito(), persona) && codAccion.equals(Constantes.FIRM)) {
				//Validar que este intentando firmar un documento que esté en la bandeja personal
				if (!documentoPortafirmas.getEstadoDocumento().getCodigo().equals(Constantes.PEND)){
					docsError.add(documentoPortafirmas);
				}
			}
		}
		return docsError;
	}
	
	private List<DocumentoPortafirmas> validarFormatoDocumentosAccion (List<DocumentoPortafirmas> listaDocumentos){
		List<DocumentoPortafirmas> docsError = new ArrayList<DocumentoPortafirmas>();
		if (listaDocumentos.size() > 1){
			for (DocumentoPortafirmas documentoPortafirmas : listaDocumentos) {
				if (documentoPortafirmas.getTipoFirma().equalsIgnoreCase(Constantes.PADES)){
					docsError.add(documentoPortafirmas);
				}
			}
		}
		return docsError;
	}
	
	private void clasificarTiposDeSolicitudFirmas(DocumentosBean documentosBean, HashMap<String, List<String>> solicitudXADES, HashMap<String, List<String>> solicitudPADES, 
			List<String> actualizadosOK, List<String> actualizadosError){
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		
		List<Document> actualizadosOKDocs = new ArrayList<Document>();
		List<Document> actualizadosErrorDocs = new ArrayList<Document>();
		
		for (Document documentoItem : documentosBean.getDocumentosFirma()) {
			String tipoFirma = documentoItem.getSignType().toUpperCase();
			if (Constantes.XADES.equalsIgnoreCase(tipoFirma) || Constantes.PADES.equalsIgnoreCase(tipoFirma)){
				documentoItem.getMetadataSignature().add(documentoItem.getSignResult());
				if (documentoItem.getSignResult() != null && !documentoItem.getSignResult().trim().equals("")) {
					if (Constantes.XADES.equalsIgnoreCase(tipoFirma)) {
						solicitudXADES.put(documentoItem.getUri(), documentoItem.getMetadataSignature());
					} else if (Constantes.PADES.equalsIgnoreCase(tipoFirma)){
						solicitudPADES.put(documentoItem.getUri(), documentoItem.getMetadataSignature());
					}
					actualizadosOK.add(documentoItem.getUri());
					actualizadosOKDocs.add(documentoItem);
				} else {
					actualizadosError.add(documentoItem.getUri());
					actualizadosErrorDocs.add(documentoItem);
				}				
			}
		}

		if (!actualizadosOKDocs.isEmpty()) {
			mostrarInfoDocumentos(actualizadosOKDocs, "Se ha retornado una firma correcta para los documentos", 0, 
					FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername(), logger, null);
		}
		if (!actualizadosErrorDocs.isEmpty()) {
			mostrarInfoDocumentos(actualizadosErrorDocs, "No se ha retornado una firma correcta para los documentos", 2, 
					FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername(), logger, null);
		}
	}
	
	public static void mostrarInfoDocumentos(List<Document> listDocumentos, String mensaje, int tipo, String usuarioBackoffice, String usuarioSolicitante, 
			Logger logger, Exception e) {
		StringBuilder infoLogBuilder = new StringBuilder();
		infoLogBuilder.append(mensaje + ":");
		for (Document document : listDocumentos){
			infoLogBuilder.append("[");
			infoLogBuilder.append("URI:" + document.getUri());
			if (!StringUtils.isNullOrEmpty(document.getSignError())) {
				infoLogBuilder.append(",");
				infoLogBuilder.append("ERROR:" + document.getSignError());
			}
			infoLogBuilder.append("]");
		}
		if (null != logger) {
			if (tipo == 0) {
				LoggerUtils.showInfo(logger, infoLogBuilder.toString(), usuarioBackoffice, usuarioSolicitante);
			} else if (tipo == 1) {
				LoggerUtils.showWarning(logger, infoLogBuilder.toString(), usuarioBackoffice, usuarioSolicitante);
			} else if (tipo == 2) {
				LoggerUtils.showError(logger, infoLogBuilder.toString(), e, usuarioBackoffice, usuarioSolicitante);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void persistirMetadatosFirma(HashMap<String, List<String>>solicitudXADES, HashMap<String, List<String>>solicitudPADES, List<String> actualizadosOK, 
			List<String> actualizadosError) throws PortafirmasFacadeException{
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		String usuarioSolicitante = usuarioBean.getUsername();
		
		if (solicitudXADES.size() > 0){
			HashMap<String, Object> result = Services.getSrvPortafirmasFacadeRemote().almacenarFirmaXADES(usuarioSolicitante, solicitudXADES);
			actualizadosOK.addAll((List<String>)result.get("actualizadosOK"));
			actualizadosError.addAll((List<String>)result.get("actualizadosError"));
		} 
		//Actualiza el contenido de firma
		if (solicitudPADES.size() > 0){
			HashMap<String, Object> result = Services.getSrvPortafirmasFacadeRemote().almacenarFirmaPADES(usuarioSolicitante, solicitudPADES);
			actualizadosOK.addAll((List<String>)result.get("actualizadosOK"));
			actualizadosError.addAll((List<String>)result.get("actualizadosError"));
		}
	}
	
	private void actualizaCircuitoPorFirma(List<String> actualizadosOK, List<String> actualizadosError, DocumentosBean documentosBean) throws PortafirmasFacadeException {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		
		if (actualizadosOK != null && !actualizadosOK.isEmpty()) {
			LoggerUtils.showInfo(logger, "Se ha actualizado correctamente la firma de alg�n documento", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			if(!documentosBean.getTitulo().equals(Constantes.PERSONAL)){ // Si no se ha firmado en la bandeja de personales
				LoggerUtils.showInfo(logger, "Se ha firmado desde fuera de la bandeja PERSONAL", 
						FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
				List<DocumentoPortafirmas> docsActualizados = new ArrayList<DocumentoPortafirmas>();
				for (String uri : actualizadosOK) {
					for (DocumentoPortafirmas doc : documentosBean.getDocumentosList()) {
						if (doc.getUri().equals(uri)) {
							docsActualizados.add(doc);
						}
					}
				}
				HashMap<String, Object> result = Services.getSrvPortafirmasFacadeRemote().actuarSobreListaDocumento(usuarioBean.getNumIdentificacion(), docsActualizados, 
						Constantes.FIRM, null);
				String msgAdicional = null;
				if (!actualizadosError.isEmpty()) {
					LoggerUtils.showInfo(logger, "No se ha podido firmar alguno de los documentos seleccionados", 
							FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
					msgAdicional = "" + Constantes.msgAlgunoNoFirmado;
				} else {
					LoggerUtils.showInfo(logger, "Los documentos seleccionados se han firmado correctamente", 
							FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
					if ((Boolean) result.get("result") != null && (Boolean) result.get("result") == true) {
						PortafirmasHelper.mostrarMensajeInfo(Constantes.msgTodosFirmados, logger);
					}
				}
				actuarMessage(result, msgAdicional);
				BandejaUtils.refresh();
			} else {
				LoggerUtils.showInfo(logger, "Se ha firmado en la bandeja PERSONAL", FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
				if (actualizadosError.isEmpty()) {
					LoggerUtils.showInfo(logger, "Los documentos seleccionados se han firmado correctamente", 
							FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
					PortafirmasHelper.mostrarMensajeInfo(Constantes.msgTodosFirmados, logger);
				} else {
					LoggerUtils.showInfo(logger, "No se ha podido firmar alguno de los documentos seleccionados", 
							FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
					PortafirmasHelper.mostrarMensajeError(Constantes.msgAlgunoNoFirmado, logger, null);
				}
			}
			documentosBean.getDocumentosFirma().clear();
		} else {
			LoggerUtils.showError(logger, "No se ha podido firmar ninguno de los documentos seleccionados", null, 
					FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			PortafirmasHelper.mostrarMensajeError(Constantes.msgNingunoFirmado, logger, null);
		}
	}
	
	public void firmarDocumentos(ActionEvent event) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		LoggerUtils.showInfo(logger, "Se verifica la cantidad de documentos a firmar", 
				FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		FirmaBean firmaBean = (FirmaBean) FacesUtils.getSessionBean(Constantes.FIRMA_BEAN);
		
		HashMap<String, List<String>> solicitudXADES = new HashMap<String, List<String>>();
		HashMap<String, List<String>> solicitudPADES = new HashMap<String, List<String>>();
		List<String> actualizadosOK = new ArrayList<String>();
		List<String> actualizadosError = new ArrayList<String>();
		
		try{
			if(firmaBean.getSignBatch()){
				//TODO: leer el fichero y setearlo en documentoItem para mantener la l�gica antigua??
				SignbatchUtils signbatchUtils = new SignbatchUtils(SignBatchConfigCreatorUtil.getConfig());
				String signResultDecode = new String(Base64.getDecoder().decode(firmaBean.getSignResult()));
				Map<String, Document> documentosFirmaMap = documentosBean.getDocumentosFirmaMap();
				signbatchUtils.retrieveDocumentsSigned(signResultDecode, documentosFirmaMap);
				documentosBean.setDocumentosFirma(new ArrayList<Document>(documentosFirmaMap.values()));
				//TODO: revisar que se han seteado los metadatos y est�n accesibles desde aqu�
			} else {
				Document documentoItem = documentosBean.getDocumentosFirma().get(0);
				documentoItem.setSignResult(firmaBean.getSignResult()); 
			}
			clasificarTiposDeSolicitudFirmas(documentosBean, solicitudXADES, solicitudPADES, actualizadosOK, actualizadosError);
		
			persistirMetadatosFirma(solicitudXADES, solicitudPADES, actualizadosOK, actualizadosError);
			actualizaCircuitoPorFirma(actualizadosOK, actualizadosError,documentosBean);
			// TODO: El documentosBean debe modificarse ya que no son los seleccionados, sino a los que se les ha subido la firma
		} catch (PortafirmasFacadeException e) {
			PortafirmasHelper.mostrarMensajeError(Constantes.msgNingunoFirmado, logger, e);
		} catch (SignBatchHelperException e) {
			PortafirmasHelper.mostrarMensajeError(Constantes.ERROR_RETRIEVE_SIGNBATCH, logger, e);
		}
	}
	
	public void actualizarFirmaClienteWeb(ActionEvent event) {
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		DocumentosBean documentosBean = (DocumentosBean) FacesUtils.getSessionBean(Constantes.DOCUMENTOS_BEAN);
		FirmaBean firmaBean = (FirmaBean) FacesUtils.getSessionBean(Constantes.FIRMA_BEAN);
		
		String docsOkFirma = firmaBean.getSignResultDocsOk();

		List<String> actualizadosOK = new ArrayList<String>();
		List<String> actualizadosError = new ArrayList<String>();
		
		try {
			if (docsOkFirma != null && !docsOkFirma.trim().equals("")) {
				Map<String, String> docsOkMap = getFirmasInfo(docsOkFirma);
				actualizadosOK = new ArrayList<String>(docsOkMap.keySet());
				if (!(actualizadosOK.size() == documentosBean.getDocumentosFirma().size())) {
					for (Document docFirma: documentosBean.getDocumentosFirma()) {
						if (!actualizadosOK.contains(docFirma.getUri())) {
							actualizadosError.add(docFirma.getUri());
						}
					}
				}
				actualizaCircuitoPorFirma(actualizadosOK, actualizadosError,documentosBean);
			} else {
				LoggerUtils.showError(logger, "No se ha podido firmar ninguno de los documentos seleccionados", null, 
						FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
				PortafirmasHelper.mostrarMensajeError(Constantes.msgNingunoFirmado, logger, null);
			}
		} catch (PortafirmasFacadeException e) {
			LoggerUtils.showError(logger, "No se ha podido firmar ninguno de los documentos seleccionados", null, 
					FacadeBean.USUARIO_PORTAFIRMAS, usuarioBean.getUsername());
			PortafirmasHelper.mostrarMensajeError(Constantes.msgNingunoFirmado, logger, e);
		}
	}
	
	public static Map<String,String> getFirmasInfo(String firmasInfoStr) {
		Map<String, String> firmasInfoMap = new HashMap<String, String>();
		
		if (firmasInfoStr != null && !firmasInfoStr.trim().equals("")) {
			String[] docs = firmasInfoStr.split("\\,");
			for (int i = 0; i < docs.length; i++) {
				String[] infoDoc = docs[i].split("\\|");
				String uri = infoDoc[0];
				String csv = infoDoc[1];
				firmasInfoMap.put(uri, csv);				
			}
		}		
		return firmasInfoMap;
	}
}
